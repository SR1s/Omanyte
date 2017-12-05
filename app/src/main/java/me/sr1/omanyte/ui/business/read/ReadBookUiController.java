package me.sr1.omanyte.ui.business.read;

import com.roalab.devkit.ui.UiController;

import me.sr1.omanyte.enity.BookCatalog;
import me.sr1.omanyte.ui.binding.ReadBookPageBinding;

/**
 * 阅读书籍页面Ui控制器
 * @author SR1
 */

public class ReadBookUiController extends UiController<IReadBookUiEvent, IReadBookUiOperation>
        implements IReadBookUiOperation {

    private final ReadBookPageBinding mBinding;

    public ReadBookUiController(ReadBookPageBinding binding) {
        super(IReadBookUiOperation.class);
        mBinding = binding;
    }

    @Override
    public void loadCatalog(BookCatalog catalog) {
        mBinding.setUrl(catalog.Url);
    }
}
