package me.sr1.omanyte.ui.business;

import com.roalab.devkit.ui.UiOperation;

import java.util.List;

import me.sr1.omanyte.enity.Book;

/**
 * 所有书籍页面Ui操作
 * @author SR1
 */

public interface ITotalBookUiOperation extends UiOperation {

    void updateBookList(List<Book> bookList);
}
