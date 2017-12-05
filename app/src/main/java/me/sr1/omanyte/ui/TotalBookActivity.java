package me.sr1.omanyte.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import me.sr1.omanyte.ui.binding.BookListPageBinding;
import me.sr1.omanyte.ui.business.TotalBookEventHandler;
import me.sr1.omanyte.ui.business.TotalBookUiController;

/**
 * 所有书籍页面
 * @author SR1
 */
public class TotalBookActivity extends AppCompatActivity {

    private TotalBookEventHandler mEventHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BookListPageBinding binding = new BookListPageBinding(getLayoutInflater(), null);
        setContentView(binding.getRoot());

        mEventHandler = new TotalBookEventHandler(new TotalBookUiController(binding));
        mEventHandler.onLoadMore();
    }
}
