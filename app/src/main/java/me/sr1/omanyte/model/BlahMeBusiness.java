package me.sr1.omanyte.model;

import android.support.v4.util.Pair;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import me.sr1.omanyte.OmanyteApp;
import me.sr1.omanyte.base.util.LogUtil;
import me.sr1.omanyte.enity.Book;
import me.sr1.omanyte.enity.BookDetail;
import me.sr1.omanyte.enity.Category;
import me.sr1.omanyte.protocol.BlahMeWebSiteApi;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Blah.me 网络相关业务
 * @author SR1
 */

public class BlahMeBusiness {

    private static final String TAG = "BlahMeBusiness";

    private final BlahMeWebSiteApi mBlahMeWebSiteApi;

    public BlahMeBusiness() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(OmanyteApp.BASE_URL).build();
        mBlahMeWebSiteApi = retrofit.create(BlahMeWebSiteApi.class);
    }

    public void loadCategories(final BusinessCallback<List<Category>, String> callback) {

        mBlahMeWebSiteApi.getCategories().enqueue(new SimpleCallback() {

            @Override
            void onSuccess(Call<ResponseBody> call, ResponseBody body) {

                String sourceXml = null;
                try {
                    sourceXml = body.string();
                    int indexOfFirst = sourceXml.indexOf("<a href=\"/\" class=\"list-group-item ok-all-book active\">");
                    int indexOfLastPart = sourceXml.indexOf("<a href=\"/feedback\"");
                    if (indexOfFirst > 0 && indexOfLastPart > indexOfFirst) {
                        sourceXml = "<root>" +
                                sourceXml.substring(indexOfFirst, indexOfLastPart) + "</root>";
                    }
                } catch (IOException ex) {
                    LogUtil.i(TAG, "error when get source data", ex);
                    onError(call, null, ex);
                    return;
                }

                LogUtil.i(TAG, sourceXml);

                XPathFactory factory = XPathFactory.newInstance();
                XPath xPath = factory.newXPath();
                try {
                    NodeList categories = (NodeList) xPath.evaluate(
                            "//a[@class='list-group-item ok-category']",
                            new InputSource(new StringReader(sourceXml)),
                            XPathConstants.NODESET
                    );

                    List<Category> categoryList = new ArrayList<>();

                    for(int i = 0; i < categories.getLength(); i++) {
                        Element element = (Element) categories.item(i);
                        String categoryId = element.getAttribute("href");
                        categoryId = categoryId.substring("/category/".length());
                        String categoryName = element.getTextContent();
                        categoryName = categoryName == null ? categoryName : categoryName.trim();

                        categoryList.add(new Category(categoryId, categoryName));
                        LogUtil.i(TAG, "categoryId=" + categoryId + ", categoryName=" + categoryName);
                    }
                    callback.onSuccess(null);

                } catch (XPathExpressionException ex) {
                    LogUtil.e(TAG, "invalid xpath expression", ex);
                    throw new RuntimeException(ex);
                }
            }

            @Override
            void onError(Call<ResponseBody> call, ResponseBody body, Throwable throwable) {
                callback.onError("error");
            }
        });
    }

    public void loadAllBook(final int page, final BusinessCallback<Pair<List<Book>, Integer>, String> callback) {
        mBlahMeWebSiteApi.getBooks(page).enqueue(new SimpleCallback() {
            @Override
            void onSuccess(Call<ResponseBody> call, ResponseBody body) {

                HtmlCleaner htmlCleaner = new HtmlCleaner();
                CleanerProperties properties = new CleanerProperties();
                properties.setAllowMultiWordAttributes(true);
                properties.setAllowHtmlInsideAttributes(true);
                properties.setAllowInvalidAttributeNames(true);
                properties.setOmitComments(true);

                try {
                    List<Book> bookList = new ArrayList<>();

                    TagNode root = htmlCleaner.clean(body.byteStream());
                    Object[] bookItemDoms = root.evaluateXPath("//div[@class='ok-book-item']");
                    for (Object bookItemObj : bookItemDoms) {
                        TagNode bookItemDom = (TagNode) bookItemObj;

                        TagNode authorDom = bookItemDom.findElementByAttValue("class","ok-book-author", true, true);
                        String author = authorDom.getText().toString().trim();

                        TagNode bookInfoDom = bookItemDom.findElementHavingAttribute("data-book-id", true);
                        String id = bookInfoDom.getAttributeByName("data-book-id");
                        String title = bookInfoDom.getAttributeByName("data-book-title");
                        LogUtil.i(TAG, "id=" + id + ", title=" + title + ", author=" + author);

                        bookList.add(new Book(id, title, author));
                    }

                    Object[] pages = root.evaluateXPath("//ul[@class='pagination ok-book-list-init']/li[last()]/a");
                    TagNode page = (TagNode) pages[0];
                    String pageStr = page.getAttributeByName("data-page");
                    int total = Integer.parseInt(pageStr);

                    LogUtil.i(TAG, "total page: " + total);

                    callback.onSuccess(new Pair<>(bookList, total));


                } catch (IOException | XPatherException e) {
                    LogUtil.w(TAG, "clean html error", e);
                    onError(call, body, e);
                }
            }

            @Override
            void onError(Call<ResponseBody> call, ResponseBody body, Throwable throwable) {
                callback.onError("");
            }
        });
    }

    public void loadSpecifiedCategoryBooks(String categoryId, int page,
                                           final BusinessCallback<Pair<List<Book>, Integer>, String> callback) {

        mBlahMeWebSiteApi.getCategoryBooks(categoryId, page).enqueue(new SimpleCallback() {
            @Override
            void onSuccess(Call<ResponseBody> call, ResponseBody body) {

                HtmlCleaner htmlCleaner = new HtmlCleaner();
                CleanerProperties properties = htmlCleaner.getProperties();
                properties.setAllowMultiWordAttributes(true);
                properties.setAllowHtmlInsideAttributes(true);
                properties.setAllowInvalidAttributeNames(true);
                properties.setOmitComments(true);

                try {
                    List<Book> bookList = new ArrayList<>();

                    TagNode root = htmlCleaner.clean(body.byteStream());
                    Object[] bookItemDoms = root.evaluateXPath("//div[@class='ok-book-item']");
                    for (Object bookItemObj : bookItemDoms) {
                        TagNode bookItemDom = (TagNode) bookItemObj;

                        TagNode authorDom = bookItemDom.findElementByAttValue("class","ok-book-author", true, true);
                        String author = authorDom.getText().toString().trim();

                        TagNode bookInfoDom = bookItemDom.findElementHavingAttribute("data-book-id", true);
                        String id = bookInfoDom.getAttributeByName("data-book-id");
                        String title = bookInfoDom.getAttributeByName("data-book-title");
                        LogUtil.i(TAG, "id=" + id + ", title=" + title + ", author=" + author);

                        bookList.add(new Book(id, title, author));
                    }

                    Object[] pages = root.evaluateXPath("//ul[@class='pagination ok-book-list-init']/li[last()]/a");
                    TagNode page = (TagNode) pages[0];
                    String pageStr = page.getAttributeByName("data-page");
                    int total = Integer.parseInt(pageStr);

                    LogUtil.i(TAG, "total page: " + total);

                    callback.onSuccess(new Pair<>(bookList, total));


                } catch (IOException | XPatherException e) {
                    LogUtil.w(TAG, "clean html error", e);
                    onError(call, body, e);
                }
            }

            @Override
            void onError(Call<ResponseBody> call, ResponseBody body, Throwable throwable) {
                callback.onError("");
            }
        });
    }

    public void loadBookDetail(String bookId, final BusinessCallback<BookDetail, String> callback) {
        mBlahMeWebSiteApi.getBookDetail(bookId).enqueue(new SimpleCallback() {
            @Override
            void onSuccess(Call<ResponseBody> call, ResponseBody body) {
                HtmlCleaner htmlCleaner = new HtmlCleaner();
                CleanerProperties properties = htmlCleaner.getProperties();
                properties.setAllowMultiWordAttributes(true);
                properties.setAllowHtmlInsideAttributes(true);
                properties.setAllowInvalidAttributeNames(true);
                properties.setOmitComments(true);

                try {

                    TagNode root = htmlCleaner.clean(body.byteStream());

                    Object[] descriptionDoms = root.evaluateXPath("//div[@itemprop='description']");
                    String description = ((TagNode) descriptionDoms[0]).getText().toString().trim();

                    LogUtil.i(TAG, "description: " + description);

                    Object[] bookInfoDoms = root.evaluateXPath("//div[@id='okBookShow']");
                    TagNode bookInfoDom = (TagNode) bookInfoDoms[0];
                    String title = bookInfoDom.getAttributeByName("data-book-title");
                    String id = bookInfoDom.getAttributeByName("data-book-id");

                    Object[] authorDoms = root.evaluateXPath("//a[@itemprop='author']");
                    String author = ((TagNode) authorDoms[0]).getText().toString().trim();

                    List<String> subjectList = new ArrayList<>();
                    Object[] subjectDoms = root.evaluateXPath("//a[@itemprop='keywords']");
                    for (Object subjectDom : subjectDoms) {
                        subjectList.add(((TagNode) subjectDom).getText().toString());
                    }

                    Book book = new Book(id, title, author);
                    BookDetail bookDetail = new BookDetail(book, description, null, subjectList);

                    LogUtil.i(TAG, "BookDetail=" + bookDetail);
                    callback.onSuccess(bookDetail);

                } catch (IOException | XPatherException e) {
                    LogUtil.w(TAG, "clean html error", e);
                    onError(call, body, e);
                }
            }

            @Override
            void onError(Call<ResponseBody> call, ResponseBody body, Throwable throwable) {
                callback.onError("error");
            }
        });
    }

    private abstract static class SimpleCallback implements Callback<ResponseBody> {

        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            if (isResponseOK(response.code())) {
                onSuccess(call, response.body());
            } else {
                onError(call, response.body(), null);
            }
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            onError(call, null, t);
        }

        private static boolean isResponseOK(int httpCode) {
            return httpCode == HttpURLConnection.HTTP_OK;
        }

        abstract void onSuccess(Call<ResponseBody> call, ResponseBody body);

        abstract void onError(Call<ResponseBody> call, ResponseBody body, Throwable throwable);
    }
}
