package me.sr1.omanyte.ui.binding;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.roalab.devkit.ui.ViewBinding;

import me.sr1.omanyte.R;

/**
 * 书籍列表布局绑定
 * @author SR1
 */

public class BookListPageBinding extends ViewBinding {

    public final RecyclerView BookList;

    public BookListPageBinding(LayoutInflater inflater, ViewGroup parent) {
        super(inflater, parent, R.layout.page_book_list);

        BookList = $(R.id.book_list);

        BookList.setLayoutManager(new LinearLayoutManager(
                BookList.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        ));
    }
}
