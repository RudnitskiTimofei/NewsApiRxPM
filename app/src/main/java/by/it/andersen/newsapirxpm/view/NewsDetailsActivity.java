package by.it.andersen.newsapirxpm.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import by.it.andersen.newsapirxpm.R;
import by.it.andersen.newsapirxpm.databinding.ActivityNewsDetailsViewBindingImpl;
import by.it.andersen.newsapirxpm.model.Article;
import by.it.andersen.newsapirxpm.model.Source;
import by.it.andersen.newsapirxpm.viewModel.NewsViewModel;

public class NewsDetailsActivity extends AppCompatActivity {
    ActivityNewsDetailsViewBindingImpl mBinding;
    Article article;
    NewsViewModel newsViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_news_details_view);
        init();
        mBinding.setArticle(article);
    }

    public void init(){
        Intent intent = getIntent();
        String title = intent.getExtras().getString("title");
        String description = intent.getExtras().getString("description");
        String sourceName = intent.getExtras().getString("source:name");
        String imageUrl = intent.getExtras().getString("imageUrl");
        Source source = new Source(null, sourceName);
        article = new Article(source, null, title, description, "", imageUrl, "");
    }
}
