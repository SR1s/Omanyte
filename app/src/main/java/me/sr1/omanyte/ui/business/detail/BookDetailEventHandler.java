package me.sr1.omanyte.ui.business.detail;

import android.widget.Toast;

import me.sr1.omanyte.OmanyteApp;
import me.sr1.omanyte.enity.Book;
import me.sr1.omanyte.enity.BookDetail;
import me.sr1.omanyte.model.BlahMeBusiness;
import me.sr1.omanyte.model.BusinessCallback;

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

    public void loadBookDetail(Book book) {
        BlahMeBusiness business = new BlahMeBusiness();
        business.loadBookDetail(book.Id, new BusinessCallback<BookDetail, String>() {
            @Override
            public void onSuccess(BookDetail data) {
                mUiController.runOnUiThread().updateDetail(data);
            }

            @Override
            public void onError(String error) {
                Toast.makeText(OmanyteApp.getApp(), "加载错误: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
