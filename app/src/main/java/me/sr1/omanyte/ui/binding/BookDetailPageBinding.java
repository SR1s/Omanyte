package me.sr1.omanyte.ui.binding;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roslab.devkit.ui.ViewBinding;

import java.util.ArrayList;
import java.util.Arrays;
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

    private RecyclerView.Adapter mDetailAdapter;

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

        mDetailAdapter = new RecyclerView.Adapter<BookDetailInfoBinding>() {
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
        };

        ContainerList.setAdapter(new CombineAdapter(mDetailAdapter));
    }

    public void setCatalog(final List<BookCatalog> bookCatalogs) {
        if (bookCatalogs == null || bookCatalogs.isEmpty()) return;

        RecyclerView.Adapter catalogAdapter = new RecyclerView.Adapter<CatalogItemBinding>() {
            @Override
            public CatalogItemBinding onCreateViewHolder(ViewGroup parent, int viewType) {
                return new CatalogItemBinding(LayoutInflater.from(parent.getContext()), parent);
            }

            @Override
            public void onBindViewHolder(CatalogItemBinding holder, int position) {
                BookCatalog catalog = bookCatalogs.get(position);
                holder.setTitle(catalog.Title);

                if (position + 1 >= getItemCount()) holder.Divider.setVisibility(View.GONE);
                else holder.Divider.setVisibility(View.VISIBLE);
            }

            @Override
            public int getItemCount() {
                return bookCatalogs.size();
            }
        };

        ContainerList.setAdapter(new CombineAdapter(mDetailAdapter, catalogAdapter));

    }

    /**
     * 组合Adapter的Adapter
     * [x] todo 内层adapter notifyDataSetChanged 通知外层
     */
    private static class CombineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<RecyclerView.Adapter<RecyclerView.ViewHolder>> mAdapters = new ArrayList<>();

        public CombineAdapter(RecyclerView.Adapter<RecyclerView.ViewHolder>... adapters) {
            mAdapters.clear();
            mAdapters.addAll(Arrays.asList(adapters));
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return mAdapters.get(viewType).onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            int count = 0;
            for (int i = 0; i < mAdapters.size(); i++) {
                RecyclerView.Adapter<RecyclerView.ViewHolder> adapter = mAdapters.get(i);
                int realPosition = position - count;
                count += adapter.getItemCount();
                if (position < count) {
                    adapter.onBindViewHolder(holder, realPosition);
                    return;
                }
            }
        }

        @Override
        public int getItemViewType(int position) {
            int count = 0;
            for (int i = 0; i < mAdapters.size(); i++) {
                RecyclerView.Adapter<RecyclerView.ViewHolder> adapter = mAdapters.get(i);
                count += adapter.getItemCount();
                if (position < count) {
                    return i;
                }
            }
            return super.getItemViewType(position);
        }

        @Override
        public int getItemCount() {
            int count = 0;
            for (RecyclerView.Adapter adapter : mAdapters) {
                count += adapter.getItemCount();
            }
            return count;
        }
    }
}
