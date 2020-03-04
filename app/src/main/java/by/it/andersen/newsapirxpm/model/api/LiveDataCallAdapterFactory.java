package by.it.andersen.newsapirxpm.model.api;

import androidx.lifecycle.LiveData;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import by.it.andersen.newsapirxpm.model.ApiResponse;
import by.it.andersen.newsapirxpm.model.Article;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;

public class LiveDataCallAdapterFactory extends CallAdapter.Factory {

    private LiveDataCallAdapterFactory(){
    }

    public static LiveDataCallAdapterFactory create(){
        return new LiveDataCallAdapterFactory();
    }

    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        if (getRawType(returnType) != LiveData.class){
            return null;
        }
        Type observableType = getParameterUpperBound(0, (ParameterizedType) returnType);
        Class<?> rawObserwableType = getRawType(observableType);
        if (rawObserwableType != ApiResponse.class){
            throw  new IllegalArgumentException("type most be parametrizes");
        }
        if (!(observableType instanceof ParameterizedType)){
            throw new IllegalArgumentException("resource must be parametrized");
        }
        Type bodyType = getParameterUpperBound(0, (ParameterizedType) observableType);
        return new LiveDataCallAdapter(bodyType);
    }
}
