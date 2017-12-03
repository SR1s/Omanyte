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

    // todo
    @GET("/category/{categoryId}")
    Call<ResponseBody> getCategoryBooks(@Path("categoryId") String categoryId, @Query("p") int page);

    @GET("/")
    Call<ResponseBody> getCategories();

    // todo
    @GET("/")
    Call<ResponseBody> getBook(String id);

    // todo
    @GET("/")
    Call<ResponseBody> search(String keyword);

    // todo
    @GET("/")
    Call<ResponseBody> getUpdateRecord();

    // todo
    @GET("/")
    Call<ResponseBody> about();



}
