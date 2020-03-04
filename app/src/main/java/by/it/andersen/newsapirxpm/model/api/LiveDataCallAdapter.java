package by.it.andersen.newsapirxpm.model.api;

import androidx.lifecycle.LiveData;

import java.lang.reflect.Type;

import by.it.andersen.newsapirxpm.R;
import by.it.andersen.newsapirxpm.model.News;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveDataCallAdapter implements CallAdapter<R, LiveData<News>> {
    private Type responseType;

    public LiveDataCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public LiveData<News> adapt(Call call) {
        final boolean[] isSuccess = {false};
        return new LiveData<News>() {
            @Override
            protected void onActive() {
                super.onActive();
                if (isSuccess[0]) {
                    enqueue();
                }
            }

            @Override
            protected void onInactive() {
                super.onInactive();
                dequeue();
            }

            private void dequeue() {
                if (call.isExecuted()) {
                    call.cancel();
                }
            }

            private void enqueue() {
                call.enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        postValue(new News());
                        isSuccess[0] = true;
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        postValue(new News());
                    }
                });
            }
        };
    }

    @Override
    public Type responseType() {
        return responseType;
    }

}
