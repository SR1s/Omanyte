package me.sr1.omanyte.ui.binding;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.roslab.devkit.ui.ViewBinding;

import java.util.List;

import me.sr1.omanyte.R;
import me.sr1.omanyte.enity.BookCatalog;
import me.sr1.omanyte.enity.BookDetail;

/**
 * 书籍详情页
 * @author SR1
 */

public class BookDetailPageBinding extends ViewBinding {

    public final RecyclerView ContainerList;

    public BookDetailPageBinding(LayoutInflater inflater, ViewGroup parent) {
        super(inflater, parent, R.layout.page_book_detail);

        ContainerList = $(R.id.container_list);

        ContainerList.setLayoutManager(new LinearLayoutManager(
                ContainerList.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        ));
    }

    public void setBookDetail(final BookDetail bookDetail) {
        if (bookDetail == null) {
            ContainerList.setAdapter(null);
            return;
        }

        ContainerList.setAdapter(new RecyclerView.Adapter<BookDetailInfoBinding>() {
            @Override
            public BookDetailInfoBinding onCreateViewHolder(ViewGroup parent, int viewType) {
                return new BookDetailInfoBinding(LayoutInflater.from(parent.getContext()), parent);
            }

            @Override
            public void onBindViewHolder(BookDetailInfoBinding holder, int position) {
                holder.setBookDetail(bookDetail);
            }

            @Override
            public int getItemCount() {
                return 1;
            }
        });
    }

    public void setCatalog(List<BookCatalog> bookCatalogs) {

    }
}
