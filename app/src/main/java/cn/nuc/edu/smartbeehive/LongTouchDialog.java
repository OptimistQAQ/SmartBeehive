package cn.nuc.edu.smartbeehive;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleAdapter;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;

import cn.nuc.edu.smartbeehive.utils.CurrentUser;
import cn.nuc.edu.smartbeehive.utils.SimpleSensor;

public class LongTouchDialog extends Dialog {

    MyApplication myApplication = new MyApplication();
    private String url = myApplication.http_ip;

    private Button deleteok;
    private Button deletecancel;


//    public LongTouchDialog(){
//        super(null);
//    }

    public LongTouchDialog(Context context, int themeResId) {

        super(context, R.style.LongTouch);
        setContentView(R.layout.activity_long_touch_dialog);
        setTitle("删除蜂箱");
        setCancelable(true);//可以用返回键，管用
        deleteok = findViewById(R.id.deleteok);
        deletecancel = findViewById(R.id.deletecancel);

        deleteok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkGo.<String>post(url + "deletebeehive")
                        .params("bid", SimpleSensor.bbid)
                        .tag(this)
                        .cacheKey("cachePostKey")
                        .cacheMode(CacheMode.NO_CACHE)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                                // User user = JSONObject.parseObject(response.body(), User.class);
                                Log.i("data", response.body());

                                //System.out.println(response.body());
                            }


                        });dismiss();
            }
        });
        deletecancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

}