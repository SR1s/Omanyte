package me.sr1.omanyte.ui.binding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.roslab.devkit.ui.ViewBinding;

import me.sr1.omanyte.R;
import me.sr1.omanyte.enity.BookDetail;
import me.sr1.omanyte.ui.widget.RoundImageView;

/**
 * 书籍详情部分绑定
 * @author SR1
 */

public class BookDetailInfoBinding extends ViewBinding {

    public final RoundImageView Cover;

    public final TextView Title;

    public final TextView Author;

    public final TextView Description;

    public final ViewGroup LabelLayout;

    public BookDetailInfoBinding(LayoutInflater inflater, ViewGroup parent) {
        super(inflater, parent, R.layout.item_book_info_detail);

        Cover       = $(R.id.book_cover);
        Title       = $(R.id.book_title);
        Author      = $(R.id.book_author);
        Description = $(R.id.book_description);
        LabelLayout = $(R.id.book_category_layout);
    }

    public void setBookDetail(BookDetail bookDetail) {
        Glide.with(Cover).load(bookDetail.BookInfo.getCoverUrl()).into(Cover);
        Title.setText(bookDetail.BookInfo.Title);
        Author.setText(bookDetail.BookInfo.Author);
        Description.setText(bookDetail.Description);

        if (bookDetail.Catalogs == null || bookDetail.Catalogs.isEmpty()) {
            LabelLayout.setVisibility(View.GONE);
        } else {
            LabelLayout.setVisibility(View.VISIBLE);
        }
    }
}
