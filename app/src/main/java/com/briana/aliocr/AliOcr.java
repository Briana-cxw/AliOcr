package com.briana.aliocr;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NavUtils;
import androidx.loader.content.AsyncTaskLoader;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

/**
 * @description:
 * @date :2020/10/21 16:57
 */
public class AliOcr  {
    String text="";
    private static final String APPCODE = "你的APPCODE";
    TextView textView;


    interface AliService{
        @POST("/api/predict/ocr_general")
        Call<HttpResult> getText(@Body RequestBody body,@Header("Authorization") String authorization);
    }
    public static String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bos);//参数100表示不压缩
        byte[] bytes = bos.toByteArray();
        //转换来的base64码不需要加前缀，必须是NO_WRAP参数，表示没有空格。
        return Base64.encodeToString(bytes, Base64.NO_WRAP);
        //转换来的base64码需要需要加前缀，必须是NO_WRAP参数，表示没有空格。
        //return "data:image/jpeg;base64," + Base64.encodeToString(bytes, Base64.NO_WRAP);
    }

    public void getText(final Activity activity, Bitmap bitmap){
        textView = activity.findViewById(R.id.tv);


        //自定义retrofit拦截器，方便调试
       /* HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.v("retrofit",message);
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();*/
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tysbgpu.market.alicloudapi.com")
                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
                .build();

        AliService aliService = retrofit.create(AliService.class);


        String body = "{\"image\":\""+bitmapToBase64(bitmap)+"\"," +
                "\"configure\":{\"min_size\":16,\"output_prob\":false,\"output_keypoints\":false,\"skip_detection\":false,\"without_predicting_direction\":false}}";

        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), body);
        Call<HttpResult> call = aliService.getText(requestBody, "APPCODE " + APPCODE);
        call.enqueue(new Callback<HttpResult>() {
            @Override
            public void onResponse(Call<HttpResult> call, Response<HttpResult> response) {
                if (response.body().getRet()!= null){
                    List<Bean> beans = response.body().getRet();
                    for (Bean bean : beans)
                        text += bean.getWord()+"\n";
                    Log.e(TAG, "onResponse: "+response.body().toString());
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(text);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<HttpResult> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.getMessage());
            }
        });
    }
}
