package me.sr1.omanyte.ui.business.detail;

import com.roalab.devkit.ui.UiController;

import me.sr1.omanyte.enity.BookDetail;
import me.sr1.omanyte.ui.binding.BookDetailPageBinding;

/**
 * 书籍详情页Ui控制器
 * @author SR1
 */

public class BookDetailUiController extends UiController<IBookDetailUiEvent, IBookDetailUiOperation>
        implements IBookDetailUiOperation {

    private final BookDetailPageBinding mBinding;

    public BookDetailUiController(BookDetailPageBinding binding) {
        super(IBookDetailUiOperation.class);

        mBinding = binding;
    }

    @Override
    public void updateDetail(BookDetail detail) {
        mBinding.setBookDetail(detail);
    }
}
