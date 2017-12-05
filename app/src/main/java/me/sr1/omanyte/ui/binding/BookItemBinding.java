package me.sr1.omanyte.ui.binding;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.roalab.devkit.ui.ViewBinding;

import me.sr1.omanyte.R;
import me.sr1.omanyte.enity.Book;

/**
 * 书籍条目绑定
 * @author SR1
 */

public class BookItemBinding extends ViewBinding {

    public final ImageView Cover;

    public final TextView Title;

    public final TextView Author;

    public BookItemBinding(LayoutInflater inflater, ViewGroup parent) {
        super(inflater, parent, R.layout.item_book);

        Cover = $(R.id.book_cover);
        Title = $(R.id.book_title);
        Author = $(R.id.book_author);
    }

    public void setBook(Book book) {
        Title.setText(book.Title);
        Author.setText(book.Author);
        Glide.with(Cover).load(book.getCoverUrl()).into(Cover);
    }

}
