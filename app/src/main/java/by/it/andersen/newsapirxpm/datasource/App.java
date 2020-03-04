package by.it.andersen.newsapirxpm.datasource;

import android.app.Application;

import androidx.room.Room;

import by.it.andersen.newsapirxpm.view.NewsViewActivity;

public class App extends Application {

    public static App instance;
    private NewsDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        database = Room.databaseBuilder(this, NewsDatabase.class, "database").build();
    }

    public static App getInstance(){
        return instance;
    }

    public NewsDatabase getDatabase(){
        return database;
    }
}
