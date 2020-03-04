package by.it.andersen.newsapirxpm.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import by.it.andersen.newsapirxpm.model.Article;
import by.it.andersen.newsapirxpm.repository.NewsRepository;

public class NewsViewModel extends AndroidViewModel {
    private static final String TAG = "MODEL";
    private NewsRepository repository;
    private String mMessage;
    private LiveData<List<Article>> temp = new MutableLiveData<>();

    public NewsViewModel( Application application) {
        super(application);
        repository = new NewsRepository(application);
    }

    public LiveData<List<Article>> getArticles(String string) {
        mMessage = string;
        repository.loadNews(mMessage);
        temp = repository.getAllNews(mMessage);
        return temp;
    }

    public LiveData<String> getErrorMessage() {
        return repository.getErrorMessage();
    }

    public LiveData<String> getProcessMessage() {
        return repository.getProcessMessage();
    }
}


