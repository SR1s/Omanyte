package me.sr1.omanyte.ui.business.read;

import com.roalab.devkit.ui.UiOperation;

import me.sr1.omanyte.enity.BookCatalog;

/**
 * 阅读书籍页Ui操作
 * @author SR1
 */

public interface IReadBookUiOperation extends UiOperation {

    void loadCatalog(BookCatalog url);
}
