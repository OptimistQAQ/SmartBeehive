    package cn.nuc.edu.smartbeehive;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;

import java.util.List;

import cn.nuc.edu.smartbeehive.bean.Beehive;
import cn.nuc.edu.smartbeehive.bean.PasswordStatus;
import cn.nuc.edu.smartbeehive.bean.User;
import cn.nuc.edu.smartbeehive.utils.CurrentUser;

public class ChangePassword extends AppCompatActivity {

    MyApplication myApplication = new MyApplication();
    private String url=myApplication.http_ip;

    private EditText yuanmima;
    private EditText xinmima;
    private EditText querenmima;
    private ImageButton change_ok;
    private ImageButton change_cancel;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        MyApplication myApplication = (MyApplication) getApplicationContext();
        MyUser myUser = new MyUser();

        yuanmima = findViewById(R.id.yuanmima);
        xinmima = findViewById(R.id.xinmima);
        querenmima = findViewById(R.id.querenmima);
        change_cancel = findViewById(R.id.change_cancel);
        change_ok = findViewById(R.id.change_ok);



        System.out.println(myApplication.user.Upassword+"123");


//        OkGo.<String>post(url+"query")
//                .params("uname", CurrentUser.uname)
//                .tag(this)
//                .cacheKey("cachePostKey")
//                .cacheMode(CacheMode.NO_CACHE)
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
//                        // User user = JSONObject.parseObject(response.body(), User.class);
//                        Log.i("data", response.body());
//
//
//                    }
//
//
//                });

        change_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String yuan = yuanmima.getText().toString().trim();
                String xin = xinmima.getText().toString().trim();
                String queren = querenmima.getText().toString().trim();
                Log.e("123", CurrentUser.uname);
                OkGo.<String>post(url+"changepassword")
                        .params("Uname", CurrentUser.uname)
                        .params("Upassword",xin)
                        .params("confirmPassword",queren)
                        .tag(this)
                        .cacheKey("cachePostKey")
                        .cacheMode(CacheMode.NO_CACHE)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                                PasswordStatus status = JSONObject.parseObject(response.body(), PasswordStatus.class);
                                // User user1 = JSONArray.parseArray(response.body());
                                //JSONObject result_json=new JSONObject(Boolean.parseBoolean(response.body()));
//                                JSONArray uname=result_json.getJSONArray("uname");
//                                JSONArray upassword=result_json.getJSONArray( "upassword");
                                // JSONArray uphone=result_json.getJSONArray("uphone");
//                                JSONArray UserNotExits=result_json.getJSONArray("UserNotExits");
                                System.out.println("修改");
                                System.out.println(status.getStatus());
                                System.out.println(CurrentUser.upassword);
                                //CurrentUser.upassword = user.getUpassword();
                                Log.i("data", response.body());
                                if ("PasswordChangeSuccess".equals(status.getStatus())&&yuan.equals(CurrentUser.upassword)) {
                                    // System.out.println(user.getUname());
                                    System.out.println("修改成功");
                                    //Uphone=user.getUphone();
                                    Toast.makeText(ChangePassword.this, "修改成功", Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(UserInfoActivity.this,FragmentMy.class);
//                                    startActivity(intent);
                                }else if("PasswordChangeNotSuccess".equals(status.getStatus())){
                                    System.out.println("修改不成功1");
                                    Toast.makeText(ChangePassword.this, "两次密码输入不正确", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(ChangePassword.this, "原密码不正确", Toast.LENGTH_SHORT).show();
                                }
                            }


                        });
                finish();
            }
        });

        change_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}