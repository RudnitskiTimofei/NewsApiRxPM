package by.it.andersen.newsapirxpm.viewModel;

import android.app.Application;
import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import by.it.andersen.newsapirxpm.datasource.ArticleDao;
import by.it.andersen.newsapirxpm.datasource.NewsDatabase;
import by.it.andersen.newsapirxpm.model.Article;
import by.it.andersen.newsapirxpm.repository.NewsRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class NewsViewModelTest {
    private List<Article> testList;
    NewsViewModel viewModel;
    @Mock
    NewsRepository repository;
    @Mock
    NewsDatabase database;
    @Mock
    ArticleDao articleDao;
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    @Rule
    public final ExternalResource resource = new ExternalResource() {
        @Override
        protected void before() {
            MockitoAnnotations.initMocks(this);
            Context context = ApplicationProvider.getApplicationContext();
            database = Room.inMemoryDatabaseBuilder(context, NewsDatabase.class).build();
            articleDao = database.article();
            viewModel = new NewsViewModel((Application) context);
        }
        @Override
        protected void after() {
            database.close();
        }
    };

    @Test
    public void getArticles() {
        assertNotNull(viewModel.getArticles("software"));
        assertEquals(false, viewModel.getArticles("software").hasObservers());
    }

    @Test
    public void getErrorMessage() {
        assertNotNull(viewModel.getErrorMessage());
    }

    @Test
    public void getProcessMessage() {
        assertNotNull(viewModel.getProcessMessage());
    }
}