package me.sr1.omanyte.ui.business.read;

import me.sr1.omanyte.enity.BookCatalog;

/**
 * 阅读书籍页事件处理器
 * @author SR1
 */

public class ReadBookEventHandler implements IReadBookUiEvent {

    private final ReadBookUiController mUiController;

    public ReadBookEventHandler(ReadBookUiController uiController) {
        mUiController = uiController;

        uiController.setEventHandler(this);
    }

    public void loadCatalog(BookCatalog catalog) {
        mUiController.runOnUiThread().loadCatalog(catalog);
    }
}
