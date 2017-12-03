package me.sr1.omanyte.protocol;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Blah.me website api
 * @author SR1
 */

public interface BlahMeWebSiteApi {

    @GET("/")
    Call<ResponseBody> getBooks(@Query("p") int page);

    @GET("/category/{categoryId}")
    Call<ResponseBody> getCategoryBooks(@Path("categoryId") String categoryId, @Query("p") int page);

    @GET("/")
    Call<ResponseBody> getCategories();

    @GET("/book/{bookId}")
    Call<ResponseBody> getBookDetail(@Path("bookId") String bookId);

    @GET("/fragment/{bookId}/tocs/")
    Call<ResponseBody> getCatalogOfBook(@Path("bookId") String bookId);

    @GET("/search")
    Call<ResponseBody> search(@Query("q") String keyword, @Query("p") int page);
}
