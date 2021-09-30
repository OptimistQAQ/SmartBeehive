package cn.nuc.edu.smartbeehive;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;

import cn.nuc.edu.smartbeehive.bean.User;
import cn.nuc.edu.smartbeehive.utils.CurrentUser;

public class

UserInfoActivity extends AppCompatActivity {

    MyApplication myApplication = new MyApplication();
    private String url=myApplication.http_ip;
    private String Uname;
    private String Uphone;

    private String currentphone;

    private TextView tv_UserName = null;
    private TextView change = null;
    private EditText et_UserPassword = null;
    private EditText et_UerPhone = null;
    private EditText et_UserAddress = null;


    private ImageButton btn_modify = null;
    private ImageButton btn_back = null;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        tv_UserName = findViewById(R.id.tv_UserName);

        change = findViewById(R.id.change);
        et_UerPhone = findViewById(R.id.et_UserPhone);
        btn_modify = findViewById(R.id.btn_modify);
        btn_back = findViewById(R.id.btn_back);

        tv_UserName.setText(CurrentUser.uname);
        et_UerPhone.setText(CurrentUser.uphone);
//        MyApplication myApplication = (MyApplication) getApplication();
//
//        System.out.println(myApplication.user.Uname);
//        OkGo.<String>post(url+"query?Uname="+myApplication.user.Uname)
//                .tag(this)
//                .cacheKey("cachePostKey")
//                .cacheMode(CacheMode.NO_CACHE)
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
//                        User user = JSONObject.parseObject(response.body(), User.class);
//                        //myApplication.user.Uname=user.getUname();
//
//                        //JSONObject result_json=new JSONObject(result);
////                                JSONArray uname=result_json.getJSONArray("uname");
////                                JSONArray upassword=result_json.getJSONArray( "upassword");
////                                JSONArray uphone=result_json.getJSONArray("uphone");
////                                JSONArray UserNotExits=result_json.getJSONArray("UserNotExits");
//                        tv_UserName.setText(CurrentUser.uname);
//                        et_UerPhone.setText(user.getUphone());
//
////                        Log.i("data",response.body());
////                        if(response.body().equals("InfoChangeSuccess")) {
////                            System.out.println("修改成功");
////                            Toast.makeText(UserInfoActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
////                        }
//                    }
//
//                });



        //MyApplication myApplication =(MyApplication) getApplication();
//        tv_UserName.setText(myApplication.user.Uname);//把用户名显示出来
//        et_UserAddress.setText(myApplication.user.Uaddress);//显示用户注册地址
//        et_UerPhone.setText(myApplication.user.Uphone);//显示用户电话
       // et_UerPhone.setText(CurrentUser.uphone);


        //System.out.println(CurrentUser.uname);
        btn_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uphone = et_UerPhone.getText().toString();
                Log.e("123", Uphone);
                OkGo.<String>post(url+"updateinfo")
                        .params("Uname", CurrentUser.uname)
                        .params("Uphone",Uphone)
                        .tag(this)
                        .cacheKey("cachePostKey")
                        .cacheMode(CacheMode.NO_CACHE)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                                User user = JSONObject.parseObject(response.body(), User.class);
                               // User user1 = JSONArray.parseArray(response.body());
                                  //JSONObject result_json=new JSONObject(Boolean.parseBoolean(response.body()));
//                                JSONArray uname=result_json.getJSONArray("uname");
//                                JSONArray upassword=result_json.getJSONArray( "upassword");
                                   // JSONArray uphone=result_json.getJSONArray("uphone");
//                                JSONArray UserNotExits=result_json.getJSONArray("UserNotExits");

                                Log.i("data", response.body());
                                if ("InfoChangeSuccess".equals(user.getStatus())) {
                                   // System.out.println(user.getUname());
                                   // System.out.println(user.getUphone());

                                    currentphone = et_UerPhone.getText().toString();
                                    Log.e("电话",currentphone);
                                    CurrentUser.uphone = currentphone;
                                    et_UerPhone.setText(currentphone);
                                    Toast.makeText(UserInfoActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                    finish();


                                }
                            }


                        });


            }
        });
        //et_UerPhone.setText(currentphone);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInfoActivity.this,ChangePassword.class);
                startActivity(intent);
            }
        });


    }
}