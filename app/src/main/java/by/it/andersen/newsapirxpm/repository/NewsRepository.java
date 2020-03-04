package by.it.andersen.newsapirxpm.repository;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import by.it.andersen.newsapirxpm.datasource.ArticleDao;
import by.it.andersen.newsapirxpm.datasource.NewsDatabase;
import by.it.andersen.newsapirxpm.model.Article;
import by.it.andersen.newsapirxpm.model.News;
import by.it.andersen.newsapirxpm.model.api.ApiClient;
import by.it.andersen.newsapirxpm.model.api.ApiInterface;
import by.it.andersen.newsapirxpm.util.Util;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class NewsRepository {
    private static final String TAG = "REPOSITORY";
    private static final String APY_KEY = "";
    private static final String PUBLISHED_AT = "publishedAt";
    private static NewsRepository instance;
    private List<Article> articles;
    private MutableLiveData<List<Article>> liveData;
    private String currentDate = Util.getDate();
    private NewsDatabase database;
    private ArticleDao dao;
    private LiveData<List<Article>> allNews;
    private MutableLiveData<String> errorData = new MutableLiveData<>();
    private MutableLiveData<String> process = new MutableLiveData<>();
    private Set<String> themes = new LinkedHashSet<>();
    private int timeToRefresh = 60000;

    public static NewsRepository getInstance(Context context) {
        if (instance == null) {
            instance = new NewsRepository(context);
        }
        return instance;
    }

    public NewsRepository(Context context) {
        database = NewsDatabase.getInstance(context);
        dao = database.article();
    }

    public MutableLiveData<List<Article>> loadNews(final String message) {
        long currentTime = System.currentTimeMillis();
        if (articles == null) {
            getData(message);
        } else {
            if (themes.size() < 5 || (currentTime - articles.get(0).getTime() > timeToRefresh)) {
                getData(message);
            }
        }
        return liveData;
    }

    @SuppressLint("CheckResult")
    public List<Article> getData(String message) {
        if (process.getValue() != null || errorData.getValue() != null) {
            process.setValue("");
            errorData.setValue("");
        }
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.getNews(message, currentDate, PUBLISHED_AT, APY_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<News>() {
                    @SuppressLint("CheckResult")
                    @Override
                    public void onSuccess(News news) {
                        articles = news.getArticles();
                        for (Article article : articles) {
                            article.setTheme(message);
                            article.setTime(System.currentTimeMillis());
                        }
                        themes.add(message);
                        new Thread(() -> {
                            Log.d(TAG, "run: ");
                            dao.insert(articles);
                        }).start();
                    }

                    @Override
                    public void onError(Throwable e) {
                        errorData.setValue("error");
                    }
                });
        return articles;
    }

    public LiveData<List<Article>> getAllNews(String string) {
        if (articles == null) {
            articles = new ArrayList<>();
        }
        allNews = dao.getArticleByTheme(string);
        process.postValue("stop");
        Log.d(TAG, "getAllNews: from database");
        return allNews;
    }

    public LiveData<String> getErrorMessage() {
        return errorData;
    }

    public LiveData<String> getProcessMessage() {
        return process;
    }
}
