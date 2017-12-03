package me.sr1.omanyte.enity;

import java.util.List;

/**
 * 书籍详情
 * @author SR1
 */

public class BookDetail {

    public final Book BookInfo;

    public final String Description;

    public final List<BookCatalog> Catalogs;

    public final List<String> Subjects;

    public BookDetail(Book bookInfo, String description, List<BookCatalog> catalogs, List<String> subjects) {
        BookInfo = bookInfo;
        Description = description;
        Catalogs = catalogs;
        Subjects = subjects;
    }

    @Override
    public String toString() {
        return "BookDetail{" +
                "BookInfo=" + BookInfo +
                ", Description='" + Description + '\'' +
                ", Catalogs=" + Catalogs +
                ", Subjects=" + Subjects +
                '}';
    }
}
