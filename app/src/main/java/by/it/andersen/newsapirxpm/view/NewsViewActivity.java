package by.it.andersen.newsapirxpm.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;
import java.util.concurrent.TimeUnit;

import by.it.andersen.newsapirxpm.R;
import by.it.andersen.newsapirxpm.model.Article;
import by.it.andersen.newsapirxpm.util.NewsAdapter;
import by.it.andersen.newsapirxpm.viewModel.NewsViewModel;

public class NewsViewActivity extends AppCompatActivity implements NewsAdapter.OnNewsListener, AdapterView.OnItemSelectedListener {
    private static final String TAG = "ACTIVITY";
    private RecyclerView recyclerView;
    private Spinner spinner;
    private LiveData<List<Article>> liveData;
    private NewsAdapter adapter;
    private NewsViewModel model;
    private String choose;
    private List<Article> mArticles;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Toolbar toolbar;
    private LinearLayoutManager manager;
    private AlertDialog alertDialog;
    private LiveData<String> errorMessage;
    private LiveData<String> processMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initRecyclerView();
        pullToRefresh();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        choose = spinner.getSelectedItem().toString();
        showDialog();
        observableData(choose);
    }

    @Override
    public void OnNewsClick(int position) {
        Intent intent  = new Intent(this, NewsDetailsActivity.class);
        intent.putExtra("title", mArticles.get(position).getTitle());
        intent.putExtra("description", mArticles.get(position).getDescription());
        intent.putExtra("source:name", mArticles.get(position).getSource().getName());
        intent.putExtra("imageUrl", mArticles.get(position).getUrlToImage());
        startActivity(intent);
    }

    public void observableData(String string){
        liveData = model.getArticles(string);
        liveData.observe(NewsViewActivity.this, new Observer<List<Article>>() {
            @Override
            public void onChanged(List<Article> list) {
                mArticles = list;
                adapter.setData(mArticles, NewsViewActivity.this);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                toolbar.setTitle(choose);
            }
        });

        errorMessage = model.getErrorMessage();
        errorMessage.observe(NewsViewActivity.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equals("error")){
                    showError();
                }
            }
        });

        processMessage = model.getProcessMessage();
        processMessage.observe(NewsViewActivity.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equals("stop")){
                    hideDialog();
                }
            }
        });
    }
    public void pullToRefresh() {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            showDialog();
            observableData(choose);
            adapter.setData(mArticles, NewsViewActivity.this);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    public void showDialog(){
        alertDialog.setMessage("content downloading");
        alertDialog.show();
    }

    public void showError(){
        alertDialog.setMessage("Something Wrong!");
    }

    public void hideDialog(){
        alertDialog.dismiss();
    }

    public void init(){
        toolbar = findViewById(R.id.toolbar);
        model = ViewModelProviders.of(this).get(NewsViewModel.class);
        spinner = findViewById(R.id.spinner);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        alertDialog = new AlertDialog.Builder(this).setTitle("Process downloading").create();
        spinner.setOnItemSelectedListener(this);
    }

    public void initRecyclerView() {
        recyclerView = findViewById(R.id.recycle_view);
        manager = new LinearLayoutManager(this);
        adapter = new NewsAdapter();
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //Nothing to do
    }
}
