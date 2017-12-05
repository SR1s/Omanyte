package me.sr1.omanyte.ui.business.booklist;

import android.widget.Toast;

import java.util.List;

import me.sr1.omanyte.OmanyteApp;
import me.sr1.omanyte.enity.Book;
import me.sr1.omanyte.model.TotalBookPagingLoader;
import me.sr1.omanyte.model.base.PagingLoader;

/**
 * 所有书籍页面事件处理器
 * @author SR1
 */

public class TotalBookEventHandler implements ITotalBookUiEvent {

    private final TotalBookUiController mUiController;

    private final TotalBookPagingLoader mLoader;

    public TotalBookEventHandler(TotalBookUiController uiController) {
        mUiController = uiController;
        mUiController.setEventHandler(this);

        mLoader = new TotalBookPagingLoader();
    }

    @Override
    public void onLoadMore() {
        mLoader.load(new PagingLoader.OnPagingCallback<Book>() {
            @Override
            public void onSuccess(List<Book> newDataList, boolean hasMore) {
                mUiController.runOnUiThread().updateBookList(mLoader.getTotalDataList());
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(OmanyteApp.getApp(), "加载失败" + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onSelectBook(Book book) {

    }
}
