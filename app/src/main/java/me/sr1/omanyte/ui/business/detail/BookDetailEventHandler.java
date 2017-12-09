package me.sr1.omanyte.ui.business.detail;

import android.widget.Toast;

import com.google.gson.Gson;

import me.sr1.omanyte.OmanyteApp;
import me.sr1.omanyte.base.util.LogUtil;
import me.sr1.omanyte.enity.Book;
import me.sr1.omanyte.enity.BookCatalog;
import me.sr1.omanyte.enity.BookDetail;
import me.sr1.omanyte.model.BlahMeBusiness;
import me.sr1.omanyte.model.BusinessCallback;
import me.sr1.omanyte.persistence.OmanyteDatabase;
import me.sr1.omanyte.persistence.entity.BookDetailCacheBean;
import me.sr1.omanyte.ui.ReadBookActivity;

/**
 * 书籍详情页事件处理
 * @author SR1
 */

public class BookDetailEventHandler implements IBookDetailUiEvent {

    private final BookDetailUiController mUiController;

    public BookDetailEventHandler(BookDetailUiController uiController) {
        mUiController = uiController;
        uiController.setEventHandler(this);
    }

    public void initWithCache(final Book book) {
        OmanyteApp.getThreadPool().submit(new Runnable() {
            @Override
            public void run() {
                BookDetailCacheBean cache =
                        OmanyteDatabase.get().getBookDetailCacheDao().getBookDetail(book.Id);
                if (cache != null) {
                    Gson gson = new Gson();
                    BookDetail detail = gson.fromJson(cache.JsonData, BookDetail.class);
                    mUiController.runOnUiThread().updateDetail(detail);
                }
                loadBookDetail(book);
            }
        });
    }

    public void loadBookDetail(Book book) {
        BlahMeBusiness business = new BlahMeBusiness();
        business.loadBookDetail(book.Id, new BusinessCallback<BookDetail, String>() {
            @Override
            public void onSuccess(final BookDetail data) {
                mUiController.runOnUiThread().updateDetail(data);
                OmanyteApp.getThreadPool().submit(new Runnable() {
                    @Override
                    public void run() {
                        BookDetailCacheBean cache = new BookDetailCacheBean();
                        cache.BookId = data.BookInfo.Id;
                        cache.JsonData = new Gson().toJson(data);
                        OmanyteDatabase.get().getBookDetailCacheDao().addBookDetailCache(cache);
                    }
                });
            }

            @Override
            public void onError(String error) {
                Toast.makeText(OmanyteApp.getApp(), "加载错误: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onSelectCatalog(BookCatalog catalog) {
        ReadBookActivity.show(OmanyteApp.getApp(), catalog);
    }
}
