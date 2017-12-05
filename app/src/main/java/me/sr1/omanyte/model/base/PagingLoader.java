package me.sr1.omanyte.model.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;

import me.sr1.omanyte.base.util.LogUtil;

/**
 * 分页拉取逻辑封装
 * @author SR1
 */

public class PagingLoader<PassbackType, DataType> {

    private final String TAG = "PagingLoader";

    /**
     * 业务请求委托
     */
    public interface RequestDelegate<PassbackType, DataType> {
        void requestPaging(PassbackType passback, RequestCallback<PassbackType, DataType> callback);
    }

    /**
     * 分页拉取回调
     * @param <T> 数据类型
     */
    public interface OnPagingCallback<T> {

        /**
         * 拉取成功回调
         * @param newDataList 新拉取到的数据列表
         * @param hasMore 是否还有更多
         */
        void onSuccess(List<T> newDataList, boolean hasMore);

        /**
         * 拉取失败回调
         * @param errorMessage 错误信息
         */
        void onError(String errorMessage);
    }

    /**
     * 业务请求委托
     */
    private final RequestDelegate<PassbackType, DataType> mRequestDelegate;

    /**
     * 分页拉取状态
     */
    private final AtomicReference<PagingState<PassbackType>> mPagingState = new AtomicReference<>(null);

    /**
     * 正在拉取过程中的请求回调
     */
    private final CopyOnWriteArrayList<OnPagingCallback<DataType>> mPendingCallbacks = new CopyOnWriteArrayList<>();

    /**
     * 已拉取下来的数据
     */
    private final List<DataType> mDataList = new ArrayList<>();

    public PagingLoader(RequestDelegate<PassbackType, DataType> requestDelegate) {
        mRequestDelegate = requestDelegate;
        mPagingState.set(new PagingState(null, true));
    }

    /**
     * 触发一次加载
     */
    public boolean load(OnPagingCallback<DataType> callback) {
        if (!mPagingState.get().hasMore) {
            LogUtil.i(TAG, "no more to load: hasMore=false");
            return false;
        }

        // isLoading根据mPendingCallbacks的数量辅助判断
        // 所以应该先获取当前状态，然后再添加回调，接着在发起调用
        // 这里表意不清晰，应该换成更清晰的表达方式
        boolean isLoading = isLoading();
        mPendingCallbacks.add(callback);

        if (!isLoading) {
            LogUtil.i(TAG, "invoke request");
            mRequestDelegate.requestPaging(mPagingState.get().passback, new PagingRequestCallback());
        } else {
            LogUtil.i(TAG, "loading request, skip");
        }
        return true;
    }

    /**
     * 是否还有更多
     * @return 还有更多 true，没有更多 false
     */
    public boolean hasMore() {
        return mPagingState.get().hasMore;
    }

    /**
     * @return 获取已拉取到的全部数据
     */
    public List<DataType> getTotalDataList() {
        return Collections.unmodifiableList(mDataList);
    }

    private boolean isLoading() {
        return !
                mPendingCallbacks.isEmpty();
    }

    private void finishLoading() {
        mPendingCallbacks.clear();
    }

    private void updateDataList(List<DataType> newDataList) {
        if (newDataList == null || newDataList.isEmpty()) {
            return;
        }
        mDataList.addAll(newDataList);
    }

    private void updatePagingState(PagingState state) {
        mPagingState.set(state);
    }

    private void notifyLoadSuccess(List<DataType> newDataList, boolean hasMore) {
        LogUtil.d(TAG, "notifyLoadSuccess, callback: " + mPendingCallbacks.size());
        List<OnPagingCallback<DataType>> callbackList = Collections.unmodifiableList(mPendingCallbacks);
        for (OnPagingCallback<DataType> callback : callbackList) {
            if (callback != null) {
                callback.onSuccess(newDataList, hasMore);
            }
        }
    }

    private void notifyLoadError(String errorMessage) {
        LogUtil.d(TAG, "notifyLoadError, callback: " + mPendingCallbacks.size());
        List<OnPagingCallback<DataType>> callbackList = Collections.unmodifiableList(mPendingCallbacks);
        for (OnPagingCallback<DataType> callback : callbackList) {
            if (callback != null) {
                callback.onError(errorMessage);
            }
        }
    }

    /**
     * 分页请求结果回调实现
     */
    private class PagingRequestCallback implements RequestCallback<PassbackType, DataType> {

        private PagingRequestCallback() {}

        @Override
        public void onSuccess(PassbackType passback, boolean hasMore, List<DataType> dataList) {
            PagingState pagingState = new PagingState(passback, hasMore);

            // 这块数据最好一次批量更新，不要分开更新
            updateDataList(dataList);
            updatePagingState(pagingState);

            notifyLoadSuccess(dataList, hasMore);

            finishLoading();
        }

        @Override
        public void onError(String errorMessage) {
            notifyLoadError(errorMessage);
            finishLoading();
        }
    }

    /**
     * 分页状态标记
     */
    private static class PagingState<PassbackType> {

        /* package */ PagingState(PassbackType passback, boolean hasMore) {
            this.hasMore = hasMore;
            this.passback = passback;
        }

        /**
         * 是否还有更多
         */
        public final boolean hasMore;

        /**
         * 分页透传标记
         */
        public final PassbackType passback;
    }

    /**
     * 请求回调接口
     * @param <PassbackType> 分页标识数据类型
     * @param <DataType> 数据类型
     */
    protected interface RequestCallback<PassbackType, DataType> {
        void onSuccess(PassbackType passback, boolean hasMore, List<DataType> dataList);
        void onError(String errorMessage);
    }
}
