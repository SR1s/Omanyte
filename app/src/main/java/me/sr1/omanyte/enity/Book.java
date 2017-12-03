package me.sr1.omanyte.enity;

import me.sr1.omanyte.OmanyteApp;

/**
 * 书籍信息
 * @author SR1
 */

public class Book {

    public final String Id;

    public final String Title;

    public final String Author;

    public Book(String id, String title, String author) {
        Id = id;
        Title = title;
        Author = author;
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

}
