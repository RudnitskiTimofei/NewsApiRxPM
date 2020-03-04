package by.it.andersen.newsapirxpm.datasource;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import by.it.andersen.newsapirxpm.model.Article;
import by.it.andersen.newsapirxpm.model.News;

@Dao
public interface ArticleDao {


//    @Insert
//    void insert(ArticleEntity articleEntity);
//
//    @Query("SELECT article FROM Article ")
//    LiveData<List<Article>> getAllArticles();
//
//    @Query("SELECT article FROM Article WHERE topic IN (topic)  ")
//    LiveData<List<Article>> getArticlesFor(String topic);
//
//    @Query("DELETE FROM Article")
//    void delete();






    @Query("SELECT * FROM articles ORDER BY publishedAt DESC")
    LiveData<List<Article>> getAll();

    @Query("SELECT * FROM articles WHERE theme = :topic  ORDER BY publishedAt DESC ")
    LiveData<List<Article>> getArticleByTheme(String topic);

    @Query(("SELECT * FROM articles ORDER BY publishedAt DESC"))
    List<Article> getAllTest();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List<Article> articles);

//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    void insertFromLiveData(LiveData<News> newsLiveData);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void inserArticle(Article article);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(List<Article> articles);

    @Delete
    void delete(Article article);

    @Query("DELETE FROM articles ")
    void deleteAll();
}
