package me.sr1.omanyte.enity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 书籍目录条目
 * @author SR1
 */

public class BookCatalog implements Parcelable {

    public final String Title;

    public final String Url;

    public final List<BookCatalog> SubCatalogs;

    public BookCatalog(String title, String url, List<BookCatalog> subCatalogs) {
        Title = title;
        Url = url;
        SubCatalogs = subCatalogs;
    }

    protected BookCatalog(Parcel in) {
        Title = in.readString();
        Url = in.readString();
        SubCatalogs = in.createTypedArrayList(BookCatalog.CREATOR);
    }

    @Override
    public String toString() {
        return "BookCatalog{" +
                "Title='" + Title + '\'' +
                ", Url='" + Url + '\'' +
                ", SubCatalogs=" + SubCatalogs +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Title);
        parcel.writeString(Url);
        parcel.writeList(SubCatalogs);
    }

    public static final Creator<BookCatalog> CREATOR = new Creator<BookCatalog>() {
        @Override
        public BookCatalog createFromParcel(Parcel in) {
            return new BookCatalog(in);
        }

        @Override
        public BookCatalog[] newArray(int size) {
            return new BookCatalog[size];
        }
    };
}
