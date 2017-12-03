package me.sr1.omanyte.enity;

/**
 * 书籍目录条目
 * @author SR1
 */

public class BookCatalog {

    public final String Title;

    public final String Url;

    public BookCatalog(String title, String url) {
        Title = title;
        Url = url;
    }

    @Override
    public String toString() {
        return "BookCatalog{" +
                "Title='" + Title + '\'' +
                ", Url='" + Url + '\'' +
                '}';
    }
}
