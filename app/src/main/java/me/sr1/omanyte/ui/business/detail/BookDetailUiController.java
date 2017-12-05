package me.sr1.omanyte.ui.business.detail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roalab.devkit.ui.UiController;

import java.util.List;

import me.sr1.omanyte.enity.BookCatalog;
import me.sr1.omanyte.enity.BookDetail;
import me.sr1.omanyte.ui.binding.BookDetailPageBinding;
import me.sr1.omanyte.ui.binding.CatalogItemBinding;

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


        final List<BookCatalog> bookCatalogs = detail.Catalogs;

        if (bookCatalogs == null) return;

        mBinding.CatalogList.setAdapter(new RecyclerView.Adapter<CatalogItemBinding>() {
            @Override
            public CatalogItemBinding onCreateViewHolder(ViewGroup parent, int viewType) {
                return new CatalogItemBinding(LayoutInflater.from(parent.getContext()), parent);
            }

            @Override
            public void onBindViewHolder(CatalogItemBinding holder, int position) {
                final BookCatalog catalog = bookCatalogs.get(position);
                holder.setTitle(catalog.Title);
                holder.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isHandlerExist()) getHandler().onSelectCatalog(catalog);
                    }
                });
            }

            @Override
            public int getItemCount() {
                return bookCatalogs.size();
            }
        });
    }
}
