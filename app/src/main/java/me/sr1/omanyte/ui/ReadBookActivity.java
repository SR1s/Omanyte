package me.sr1.omanyte.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import me.sr1.omanyte.enity.BookCatalog;
import me.sr1.omanyte.ui.binding.ReadBookPageBinding;
import me.sr1.omanyte.ui.business.read.ReadBookEventHandler;
import me.sr1.omanyte.ui.business.read.ReadBookUiController;

/**
 * 书籍阅读页面
 * @author SR1
 */

public class ReadBookActivity extends AppCompatActivity {

    public static void show(Context context, BookCatalog catalog) {
        if (catalog == null) {
            throw new IllegalArgumentException("catalog parameter cannot be null");
        }

        Intent intent = new Intent(context, ReadBookActivity.class);
        intent.putExtra(KEY_ARGUMENTS, new Arguments(catalog));
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        context.startActivity(intent);
    }

    private ReadBookEventHandler mEventHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ReadBookPageBinding binding = new ReadBookPageBinding(getLayoutInflater(), null);
        setContentView(binding.getRoot());

        mEventHandler = new ReadBookEventHandler(new ReadBookUiController(binding));
        mEventHandler.loadCatalog(getArguments().Catalog);
    }

    private Arguments getArguments() {
        return getIntent().getParcelableExtra(KEY_ARGUMENTS);
    }

    private static final String KEY_ARGUMENTS = "KEY_ARGUMENTS";

    public static class Arguments implements Parcelable {

        public final BookCatalog Catalog;

        public Arguments(BookCatalog catalog) {
            Catalog = catalog;
        }

        protected Arguments(Parcel in) {
            Catalog = in.readParcelable(BookCatalog.class.getClassLoader());
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable(Catalog, i);
        }

        @Override
        public int describeContents() {
            return 0;
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
