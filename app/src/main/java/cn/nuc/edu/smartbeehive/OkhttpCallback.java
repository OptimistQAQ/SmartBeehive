package cn.nuc.edu.smartbeehive;
import android.util.Log;

import com.google.gson.internal.bind.JsonTreeReader;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class OkhttpCallback implements Callback {

    private static String TAG = OkhttpCallback.class.getSimpleName();

    public String url;
    public String result;
    @Override
    public void onFailure(@NotNull Call call, @NotNull IOException e) {
        Log.e(TAG,"url:"+url);
        Log.d(TAG,"请求失败"+result);
       // result = response.body
        onFinish("failure",result);
    }

    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        Log.d(TAG,"url:"+url);
        result = response.body().string().toString();
        Log.d(TAG,"请求成功"+result);
        onFinish("success",result);

    }

    public void onFinish(String status,String msg){
        Log.d(TAG,"url:"+url+",status:"+status);
    }
}
