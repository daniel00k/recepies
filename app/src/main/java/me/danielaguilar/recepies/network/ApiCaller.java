package me.danielaguilar.recepies.network;

import android.content.Context;
import android.widget.Toast;

import java.util.List;

import me.danielaguilar.recepies.config.Constants;
import me.danielaguilar.recepies.models.Recipe;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by danielaguilar on 27-01-18.
 */

public class ApiCaller {
    public interface OnFinishCall<T>{
        void onSuccess(Call<T> call, Response<T> response);
        void onFailure(Call<T> call, Throwable t);
    }

    public static ApiCaller instance;
    private Retrofit retrofit;
    private Context context;

    public ApiCaller(){};

    public ApiCaller(Context context){
        this.context    = context;
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ApiCaller getInstance(Context context){
        if(instance == null){
            return new ApiCaller(context);
        }
        return instance;
    }

    public Retrofit getRetrofit() {
        if(!NetworkState.isConnectionAvailable(context)){
            Toast.makeText(context, "No hay coneccion", Toast.LENGTH_SHORT).show();
            return null;
        }
        return retrofit;
    }

    public void setRetrofit(Retrofit retrofit) {
        this.retrofit = retrofit;
    }
}
