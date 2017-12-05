package me.sr1.omanyte.ui.binding;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.roalab.devkit.ui.ViewBinding;

import java.util.List;

import me.sr1.omanyte.R;
import me.sr1.omanyte.enity.BookCatalog;
import me.sr1.omanyte.enity.BookDetail;

/**
 * 书籍详情页
 * @author SR1
 */

public class BookDetailPageBinding extends ViewBinding {

    public final ImageView Cover;

    public final TextView Title;

    public final TextView Author;

    public final TextView Description;

    public final RecyclerView CatalogList;

    public BookDetailPageBinding(LayoutInflater inflater, ViewGroup parent) {
        super(inflater, parent, R.layout.page_book_detail);

        Cover = $(R.id.book_cover);
        Title = $(R.id.book_title);
        Author = $(R.id.book_author);
        Description = $(R.id.book_description);
        CatalogList = $(R.id.book_catalog_list);

        CatalogList.setLayoutManager(new LinearLayoutManager(
                CatalogList.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        ));
    }

    public void setBookDetail(BookDetail bookDetail) {
        Glide.with(Cover).load(bookDetail.BookInfo.getCoverUrl()).into(Cover);
        Title.setText(bookDetail.BookInfo.Title);
        Author.setText(bookDetail.BookInfo.Author);
        Description.setText(bookDetail.Description);
    }
}
