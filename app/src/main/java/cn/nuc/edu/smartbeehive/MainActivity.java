package cn.nuc.edu.smartbeehive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTabHost;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import cn.nuc.edu.smartbeehive.bean.User;
import cn.nuc.edu.smartbeehive.utils.CurrentUser;
import cn.nuc.edu.smartbeehive.utils.Status;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity {



   private static final OkHttpClient client = new OkHttpClient();



    MyApplication myApplication = new MyApplication();
    private String url=myApplication.http_ip;

    public String Uname = null;
    public String Upassword = null;

    public String rUname = null;
    public String rUpassword = null;
    public String rAUpassword = null;
    public String rUphone = null;

    public FrameLayout fl_container;

    private EditText userName = null;
    private EditText userPassword = null;
    private EditText etRegisterUserName = null;
    private EditText etRegisterUserPassword= null;
    private EditText etRegisterAffirmUserPassword = null;
    private EditText etRegisterUserMobilePhone = null;
    private EditText etRegisterUserAddress = null;

    private ImageView login = null;
    private TextView userRegister = null;

    private FragmentManager fragmentManager;
    private LayoutInflater layoutInflater;
    private FragmentTabHost  mTablehost;



    public MainActivity() {
    }

    public static final MediaType JSON=MediaType.parse("application/json;charset=utf-8");

    public static void get(String url, OkhttpCallback callback ){
        callback.url = url;
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(callback);
    }
    public static void post(String url,String json,OkhttpCallback callback){
        callback.url=url;
        RequestBody body = RequestBody.create(JSON,json);
        Request request = new Request.Builder().url(url).post(body).build();
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        myApplication = (MyApplication) getApplication();
       myApplication.user = new MyUser();
       myApplication.admin = new MyAdmin();
       myApplication.context = getApplicationContext();
//       fl_container = findViewById(R.id.fl_container);

        userName = findViewById(R.id.userName);
        userPassword = findViewById(R.id.userPassword);
        userRegister = findViewById(R.id.userRegister);
        login = findViewById(R.id.login);
//        et_LoginUserId = findViewById(R.id.et_LoginUserId);
//        et_loginUserPassword = findViewById(R.id.et_loginUserPassword);
        etRegisterUserName = findViewById(R.id.etRegisterUserName);
        etRegisterUserPassword = findViewById(R.id.etRegisterUserPassword);
        etRegisterAffirmUserPassword = findViewById(R.id.etRegisterAffirmUserPassword);
        etRegisterUserMobilePhone = findViewById(R.id.et_UserPhone);



        //注册
        userRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActicity.class);
                startActivity(intent);
            }
        });
        //登录、
        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Uname = userName.getText().toString().trim();
                Upassword = userPassword.getText().toString().trim();
//
                OkGo.<String>post(url+"login?Uname="+Uname+"&Upassword="+Upassword)
                        .tag(this)
                        .cacheKey("cachePostKey")
                        .cacheMode(CacheMode.NO_CACHE)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                               User user = JSONObject.parseObject(response.body(), User.class);
//                               Status status = JSONObject.parseObject(response.body(),Status.class);
                                Log.i("data",response.body());
                               // Log.e("status",user.getStatus());
                                if("UserNotExits".equals(user.getStatus())) {
                                   Toast.makeText(MainActivity.this,"用户不存在",Toast.LENGTH_SHORT).show();
                                }else if("PasswordNotCorrect".equals(user.getStatus())){
                                    System.out.println(response.code());
                                    System.out.println("密码错误");
                                    Toast.makeText(MainActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(MainActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
                                    CurrentUser.uname = user.getUname();
                                    CurrentUser.uphone = user.getUphone();
                                    CurrentUser.upassword = user.getUpassword();
                                    CurrentUser.uid =user.getUid();
                                    Intent intent1 = new Intent(MainActivity.this,HomeActivity.class);
                                    startActivity(intent1);
                                }
                            }

                            @Override
                            public void onError(com.lzy.okgo.model.Response<String> response) {
                                super.onError(response);
                                Toast.makeText(MainActivity.this,"连接失败,请检查网络",Toast.LENGTH_SHORT).show();
                            }
                        });


                




            }
        });

    }
}
