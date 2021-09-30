package cn.nuc.edu.smartbeehive;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;

import cn.nuc.edu.smartbeehive.bean.User;
import cn.nuc.edu.smartbeehive.utils.CurrentUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentMy#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class FragmentMy extends Fragment {
    MyApplication myApplication =new MyApplication();
    private String url=myApplication.http_ip;
    private TextView user;
    private TextView settings ;
    private TextView shenqing;
    private TextView lianxi;
    private TextView logout;
    private FragmentManager fragmentManager;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentMy.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentMy newInstance(String param1, String param2) {
        FragmentMy fragment = new FragmentMy();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentMy() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }




       // user.setText("123");


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        user = view.findViewById(R.id.user);
        //user.setText("123");
        settings =view.findViewById(R.id.settings);
        shenqing = view.findViewById(R.id.shenqing);
        lianxi  = view.findViewById(R.id.lianxi);
        logout  = view.findViewById(R.id.logout);


        user.setText("欢迎，"+CurrentUser.uname);


//        OkGo.<String>post(url+"query?Uname="+myApplication.user.Uname)
//                .tag(this)
//                .cacheKey("cachePostKey")
//                .cacheMode(CacheMode.NO_CACHE)
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
//                        User user1 = JSONObject.parseObject(response.body(), User.class);
//                        myApplication.user.Uname=user1.getUname();
//
//                        //JSONObject result_json=new JSONObject(result);
////                                JSONArray uname=result_json.getJSONArray("uname");
////                                JSONArray upassword=result_json.getJSONArray( "upassword");
////                                JSONArray uphone=result_json.getJSONArray("uphone");
////                                JSONArray UserNotExits=result_json.getJSONArray("UserNotExits");
//                       // user.setText(user1.getUname());
//
////                        Log.i("data",response.body());
////                        if(response.body().equals("InfoChangeSuccess")) {
////                            System.out.println("修改成功");
////                            Toast.makeText(UserInfoActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
////                        }
//                    }
//
//                });


        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UserInfoActivity.class);
                startActivity(intent);
            }
        });

        shenqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AskForBeehive.class);
                startActivity(intent);
            }
        });

        lianxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ConnectingUs.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        return view;




    }
}