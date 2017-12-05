package me.sr1.omanyte.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import me.sr1.omanyte.enity.Book;
import me.sr1.omanyte.ui.binding.BookDetailPageBinding;
import me.sr1.omanyte.ui.business.detail.BookDetailEventHandler;
import me.sr1.omanyte.ui.business.detail.BookDetailUiController;

/**
 * 书籍详情页面
 * @author SR1
 */

public class BookDetailActivity extends AppCompatActivity {

    public static void show(Context context, Book book) {
        if (book == null) {
            throw new IllegalArgumentException("book parameter cannot be null");
        }

        Intent intent = new Intent(context, BookDetailActivity.class);
        intent.putExtra(KEY_ARGUMENTS, new Arguments(book));
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        context.startActivity(intent);
    }

    private BookDetailEventHandler mEventHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BookDetailPageBinding binding = new BookDetailPageBinding(getLayoutInflater(), null);
        setContentView(binding.getRoot());

        mEventHandler = new BookDetailEventHandler(new BookDetailUiController(binding));
        mEventHandler.loadBookDetail(getArguments().mBook);
    }

    private Arguments getArguments() {
        return getIntent().getParcelableExtra(KEY_ARGUMENTS);
    }

    private static final String KEY_ARGUMENTS = "KEY_ARGUMENTS";

    public static class Arguments implements Parcelable {

        public final Book mBook;

        public Arguments(Book book) {
            mBook = book;
        }

        protected Arguments(Parcel in) {
            mBook = in.readParcelable(Book.class.getClassLoader());
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable(mBook, i);

        }

        public static final Creator<Arguments> CREATOR = new Creator<Arguments>() {
            @Override
            public Arguments createFromParcel(Parcel in) {
                return new Arguments(in);
            }

            @Override
            public Arguments[] newArray(int size) {
                return new Arguments[size];
            }
        };
    }
}
