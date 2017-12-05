package me.sr1.omanyte.enity;

import android.os.Parcel;
import android.os.Parcelable;

import me.sr1.omanyte.OmanyteApp;

/**
 * 书籍信息
 * @author SR1
 */

public class Book implements Parcelable {

    public final String Id;

    public final String Title;

    public final String Author;

    public Book(String id, String title, String author) {
        Id = id;
        Title = title;
        Author = author;
    }

    protected Book(Parcel in) {
        Id = in.readString();
        Title = in.readString();
        Author = in.readString();
    }

    public String getCoverUrl() {
        return OmanyteApp.BASE_URL + "/cover/" + Id + "/cover.jpg?ts=" + System.currentTimeMillis();
    }

    public String getReadUrl() {
        return OmanyteApp.BASE_URL + "/read/" + Id + "/";
    }

    public String getTxtDownloadUrl() {
        return OmanyteApp.BASE_URL + "/download/ebook/" + Id + "/txt";
    }

    public String getMobiDownloadUrl() {
        return OmanyteApp.BASE_URL + "/download/ebook/" + Id + "/mobi";
    }

    public String getEpubDownloadUrl() {
        return OmanyteApp.BASE_URL + "/download/ebook/" + Id + "/epub";
    }

    @Override
    public String toString() {
        return "Book{" +
                "Id='" + Id + '\'' +
                ", Title='" + Title + '\'' +
                ", Author='" + Author + '\'' +
                '}';
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Id);
        parcel.writeString(Title);
        parcel.writeString(Author);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
