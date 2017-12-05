package me.sr1.omanyte.ui.business.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roalab.devkit.ui.UiController;

import java.util.ArrayList;
import java.util.List;

import me.sr1.omanyte.enity.Book;
import me.sr1.omanyte.ui.binding.BookItemBinding;
import me.sr1.omanyte.ui.binding.BookListPageBinding;

/**
 * 全部书籍的Ui控制器
 * @author SR1
 */

public class TotalBookUiController extends UiController<ITotalBookUiEvent, ITotalBookUiOperation>
        implements ITotalBookUiOperation {

    private final BookListPageBinding mBinding;

    private final BookItemAdapter mBookItemAdapter;

    public TotalBookUiController(BookListPageBinding binding) {
        super(ITotalBookUiOperation.class);

        mBinding = binding;

        mBookItemAdapter = new BookItemAdapter();
        mBinding.BookList.setAdapter(mBookItemAdapter);
    }

    @Override
    public void updateBookList(List<Book> bookList) {
        mBookItemAdapter.updateBooks(bookList);
    }

    private class BookItemAdapter extends RecyclerView.Adapter<BookItemBinding> {

        private List<Book> mData = new ArrayList<>();

        public void updateBooks(List<Book> books) {
            mData.clear();
            if (books != null) {
                mData.addAll(books);
            }
            notifyDataSetChanged();
        }

        @Override
        public BookItemBinding onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BookItemBinding(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(BookItemBinding holder, int position) {
            final Book book = mData.get(position);
            holder.setBook(book);
            holder.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isHandlerExist()) getHandler().onSelectBook(book);
                }
            });
            if (position + 1 == mData.size()) if (isHandlerExist()) getHandler().onLoadMore();
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }

}
