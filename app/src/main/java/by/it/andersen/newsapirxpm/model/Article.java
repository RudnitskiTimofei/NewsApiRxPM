package by.it.andersen.newsapirxpm.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "articles", indices = {@Index(value = {"description"}, unique = true)})
public class Article {

    @PrimaryKey(autoGenerate = true)
    private Long idArticle;

    @ColumnInfo(name = "theme")
    private String theme;

    @ColumnInfo(name = "time")
    private long time;

    public long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    @Embedded
    @SerializedName("source")
    @Expose
    private Source source;

    @SerializedName("author")
    @Expose
    @ColumnInfo(name = "author")
    private String author;

    @SerializedName("title")
    @Expose
    @ColumnInfo(name = "title")
    private String title;

    @SerializedName("description")
    @Expose
    @ColumnInfo(name = "description")
    private String description;

    @SerializedName("url")
    @Expose
    @ColumnInfo(name = "url")
    private String url;

    @SerializedName("urlToImage")
    @Expose
    @ColumnInfo(name = "urlToImage")
    private String urlToImage;

    @SerializedName("publishedAt")
    @Expose
    @ColumnInfo(name = "publishedAt")
    private String publishedAt;

    public Article(Article article) {
    }

    public Article(Source source, String author, String title, String description, String url, String urlToImage, String publishedAt){
        this.source=source;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public void setIdArticle(Long idArticle) {
        this.idArticle = idArticle;
    }

    public Long getIdArticle() {
        return idArticle;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
