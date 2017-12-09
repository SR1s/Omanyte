package me.sr1.omanyte.ui.business.detail;

import com.roslab.devkit.ui.UiEvent;

import me.sr1.omanyte.enity.BookCatalog;

/**
 * 书籍详情页触发的Ui事件
 * @author SR1
 */

public interface IBookDetailUiEvent extends UiEvent {

    void onSelectCatalog(BookCatalog catalog);
}
