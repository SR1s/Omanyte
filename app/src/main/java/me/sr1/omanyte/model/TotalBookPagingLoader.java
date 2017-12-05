package me.sr1.omanyte.model;

import android.support.v4.util.Pair;

import java.util.List;

import me.sr1.omanyte.enity.Book;
import me.sr1.omanyte.model.base.PagingLoader;

/**
 * 所有书籍的分页拉取器
 * @author SR1
 */

public class TotalBookPagingLoader extends PagingLoader<Integer, Book> {

    public TotalBookPagingLoader() {
        super(new TotalBookRequestDelegate());
    }

    private static class TotalBookRequestDelegate implements RequestDelegate<Integer, Book> {
        @Override
        public void requestPaging(Integer page, final RequestCallback<Integer, Book> callback) {

            BlahMeBusiness business = new BlahMeBusiness();
            final int loadPage = page == null ? 0 : page;
            business.loadAllBook(loadPage, new BusinessCallback<Pair<List<Book>, Integer>, String>() {
                @Override
                public void onSuccess(Pair<List<Book>, Integer> data) {
                    int nextPage = loadPage + 1;
                    boolean hasMore = loadPage < data.second;
                    callback.onSuccess(nextPage, hasMore, data.first);
                }

                @Override
                public void onError(String error) {
                    callback.onError(error);
                }
            });
        }
    }
}
