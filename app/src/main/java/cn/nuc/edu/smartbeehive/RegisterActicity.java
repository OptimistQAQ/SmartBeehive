package cn.nuc.edu.smartbeehive;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;

import cn.nuc.edu.smartbeehive.bean.User;

public class RegisterActicity extends AppCompatActivity {


    MyApplication myApplication = new MyApplication();
    private String url=myApplication.http_ip;
    private EditText etRegisterUserName = null;
    private EditText etRegisterUserPassword = null;
    private EditText etRegisterAffirmUserPassword = null;
    private EditText etRegisterUserMobilePhone = null;
    private EditText etRegisterUserAddress = null;
    public ImageButton btnRegister;
    public ImageButton btnCancle;
    private MyBtnListener myBtnListener = null;

    public String rUname = null;
    public String rUpassword = null;
    public String rAUpassword = null;
    public String rUphone = null;
    //RegisterActivity的结果码
//    private static final int RESULT_OK = 2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etRegisterUserName = findViewById(R.id.etRegisterUserName);
        etRegisterUserPassword = findViewById(R.id.etRegisterUserPassword);
        etRegisterAffirmUserPassword = findViewById(R.id.etRegisterAffirmUserPassword);
        etRegisterUserMobilePhone = findViewById(R.id.etRegisterUserMobilePhone);
        //etRegisterUserAddress = findViewById(R.id.etRegisterUserAddress);
        btnCancle = findViewById(R.id.btnCancle);
        btnRegister = findViewById(R.id.btnRegister);

        //将按钮与监听器对象绑定
        myBtnListener = new MyBtnListener();
        btnRegister.setOnClickListener(myBtnListener);
        btnCancle.setOnClickListener(myBtnListener);

    }
        private class MyBtnListener implements View.OnClickListener{

            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btnRegister:
                        rUname = etRegisterUserName.getText().toString().trim();
                        rUpassword = etRegisterUserPassword.getText().toString().trim();
                        rAUpassword = etRegisterAffirmUserPassword.getText().toString().trim();
                        rUphone = etRegisterUserMobilePhone.getText().toString().trim();
//
                        OkGo.<String>post(url+"register?Uname="+rUname+"&Upassword="+rUpassword+"&confirmPassword="+rAUpassword+"&Uphone="+rUphone)
                                .tag(this)
                                .cacheKey("cachePostKey")
                                .cacheMode(CacheMode.NO_CACHE)
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                                         User user = JSONObject.parseObject(response.body(), User.class);
                                        //JSONObject result_json=new JSONObject(result);
//                                JSONArray uname=result_json.getJSONArray("uname");
//                                JSONArray upassword=result_json.getJSONArray( "upassword");
//                                JSONArray uphone=result_json.getJSONArray("uphone");
//                                JSONArray UserNotExits=result_json.getJSONArray("UserNotExits");

                                        Log.i("data",response.body());
                                        if("UserExited".equals(user.getStatus())) {
                                            System.out.println("用户已存在");
                                            Toast.makeText(RegisterActicity.this,"用户已存在",Toast.LENGTH_SHORT).show();
                                        }else if("PasswordsNotSame".equals(user.getStatus())){
                                            System.out.println("密码不一致");
                                            Toast.makeText(RegisterActicity.this,"密码输入不一致",Toast.LENGTH_SHORT).show();
                                        }

                                        else{
                                            Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_SHORT);
                                            finish();
                                        }
                                    }

                                });
                        break;
                    case R.id.btnCancle:
                        finish();
                        break;
                    default:
                        break;
                }
            }
        }

}
