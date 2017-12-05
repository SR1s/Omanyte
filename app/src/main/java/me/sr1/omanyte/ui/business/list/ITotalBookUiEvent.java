package me.sr1.omanyte.ui.business.list;

import com.roalab.devkit.ui.UiEvent;

import me.sr1.omanyte.enity.Book;

/**
 * 全部书籍页面Ui事件
 * @author SR1
 */

public interface ITotalBookUiEvent extends UiEvent {

    void onLoadMore();

    void onSelectBook(Book book);
}
