package me.sr1.omanyte.enity;

import java.util.List;

/**
 * 书籍目录条目
 * @author SR1
 */

public class BookCatalog {

    public final String Title;

    public final String Url;

    public final List<BookCatalog> SubCatalogs;

    public BookCatalog(String title, String url, List<BookCatalog> subCatalogs) {
        Title = title;
        Url = url;
        SubCatalogs = subCatalogs;
    }

    @Override
    public String toString() {
        return "BookCatalog{" +
                "Title='" + Title + '\'' +
                ", Url='" + Url + '\'' +
                ", SubCatalogs=" + SubCatalogs +
                '}';
    }
}
