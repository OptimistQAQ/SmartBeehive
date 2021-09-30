package cn.nuc.edu.smartbeehive;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;

import cn.nuc.edu.smartbeehive.utils.CurrentUser;

public class AddDialog extends Dialog {

    MyApplication myApplication = new MyApplication();
    private String url=myApplication.http_ip;
    public EditText et_address;

    public Button btn_add = null;
    public Button btn_cancel = null;



    public AddDialog(Context context, int themeResId) {
        super(context, R.style.AddDialog);
        setContentView(R.layout.dialog_add);
        setTitle("添加蜂箱");
        setCancelable(true);//可以用返回键，管用

        btn_add = findViewById(R.id.btn_add1);
        btn_cancel = findViewById(R.id.btn_cancel1);
        et_address = findViewById(R.id.et_address);

        Button.OnClickListener buttonListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btn_add1:
                        OkGo.<String>post(url+"addbeehive")
                                .params("uname", CurrentUser.uname)
                                .params("baddress",et_address.getText().toString())
                                .tag(this)
                                .cacheKey("cachePostKey")
                                .cacheMode(CacheMode.NO_CACHE)
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                                        // User user = JSONObject.parseObject(response.body(), User.class);
                                        Log.i("data", response.body());
                                        System.out.println(response.body());
                                    }


                                });
                        CurrentUser.bcount = CurrentUser.bcount+1;
                        break;

                    case R.id.btn_cancel1:
                        break;

                }dismiss();
            }
        };

        btn_add.setOnClickListener(buttonListener);
        btn_cancel.setOnClickListener(buttonListener);
    }
}
