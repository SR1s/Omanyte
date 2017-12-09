package me.sr1.omanyte.ui.business.list;

import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import me.sr1.omanyte.OmanyteApp;
import me.sr1.omanyte.enity.Book;
import me.sr1.omanyte.model.TotalBookPagingLoader;
import me.sr1.omanyte.model.base.PagingLoader;
import me.sr1.omanyte.persistence.OmanyteDatabase;
import me.sr1.omanyte.persistence.entity.BookCacheBean;
import me.sr1.omanyte.ui.BookDetailActivity;

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

    public void initWithCache() {
        OmanyteApp.getThreadPool().submit(new Runnable() {
            @Override
            public void run() {
                List<BookCacheBean> caches = OmanyteDatabase.get().getBookCacheDao().getAll();
                if (caches != null) {
                    List<Book> bookList = new ArrayList<>();
                    Gson gson = new Gson();
                    for(BookCacheBean cache : caches) {
                        bookList.add(gson.fromJson(cache.JsonData, Book.class));
                    }
                    mUiController.runOnUiThread().updateBookList(bookList);

                }
//                onLoadMore();
            }
        });
    }

    @Override
    public void onLoadMore() {
        mLoader.load(new PagingLoader.OnPagingCallback<Book>() {
            @Override
            public void onSuccess(List<Book> newDataList, boolean hasMore) {
                mUiController.runOnUiThread().updateBookList(mLoader.getTotalDataList());

                OmanyteApp.getThreadPool().submit(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson = new Gson();

                        for (Book book : mLoader.getTotalDataList()) {
                            BookCacheBean cache = new BookCacheBean();
                            cache.JsonData = gson.toJson(book);
                            OmanyteDatabase.get().getBookCacheDao().addBookCache(cache);
                        }
                    }
                });
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(OmanyteApp.getApp(), "加载失败" + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onSelectBook(Book book) {
        BookDetailActivity.show(OmanyteApp.getApp(), book);
    }
}
