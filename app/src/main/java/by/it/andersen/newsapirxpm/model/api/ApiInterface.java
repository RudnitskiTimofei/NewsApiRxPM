package by.it.andersen.newsapirxpm.model.api;

import androidx.lifecycle.LiveData;

import java.util.List;

import by.it.andersen.newsapirxpm.model.News;
import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("/v2/everything")
    Single<News> getNews(
            @Query("q") String source,
            @Query("from") String from,
            @Query("sortBy") String sort,
            @Query("apiKey") String apiKey );


    @GET("v2/everything")
    Flowable<News> getNewsFlowable(
            @Query("q") String source,
            @Query("from") String from,
            @Query("sortBy") String sort,
            @Query("apiKey") String apiKey );

    @GET("v2/everything")
    LiveData<List<News>> getNewsLiveData(
            @Query("q") String source,
            @Query("from") String from,
            @Query("sortBy") String sort,
            @Query("apiKey") String apiKey
    );

}
