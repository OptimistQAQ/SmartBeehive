package cn.nuc.edu.smartbeehive;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.nuc.edu.smartbeehive.bean.Beehive;
import cn.nuc.edu.smartbeehive.recycleview.RecycleAdapter;
import cn.nuc.edu.smartbeehive.utils.CurrentUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentControl#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class FragmentControl extends Fragment  {

    MyApplication myApplication =new MyApplication();
    private String url=myApplication.http_ip;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    static List<Map<String,Object>> beehiveinfo;
    static SimpleAdapter mlistItemAdapter;
    private Button addBtn;
    private Button cccBtn;
    private Button shoudong;
    private Button scan;
    private Button shuaxin;
    private ListView listviwe;
    private RecyclerView recyclerView;
    private RecycleAdapter recycleAdapter;
    private Context context;
    private List<String> list;
    int[] j = new int[100];


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentControl.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentControl newInstance(String param1, String param2) {
        FragmentControl fragment = new FragmentControl();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentControl() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }


    /**
     * date:2021/1/11 0011
     * author:wsm (Administrator)
     * funcation:
     */
    public static final class PermissionUtils {
        private PermissionUtils() {
        }

        //需要申请的权限
        private static String[] permissions = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        };

        //检测权限
        public static String[] checkPermission(Context context){
            List<String> data = new ArrayList<>();//存储未申请的权限
            for (String permission : permissions) {
                int checkSelfPermission = ContextCompat.checkSelfPermission(context, permission);
                if(checkSelfPermission == PackageManager.PERMISSION_DENIED){//未申请
                    data.add(permission);
                }
            }
            return data.toArray(new String[data.size()]);
        }
    }



    public void addData(){
        for (int i =0 ;i<CurrentUser.bcount;i++){
        OkGo.<String>post(url+"sensorinfo")
                    .params("bid", CurrentUser.bid[i])
                    .tag(this)
                    .cacheKey("cachePostKey")
                    .cacheMode(CacheMode.NO_CACHE)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                            //List<Beehive> data = JSON.parseArray(response.body().toString(),Beehive.class);
                            // User user = JSONObject.parseObject(response.body(), User.class);
                            Log.i("data", response.body());

                            //System.out.println(i+""response.body());


                        }
                    });
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        List<MySensorBeehive> beehiveList;

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_control, container, false);
        //View view = inflater.inflate(R.layout.activity_controller_view, container, false);
        addBtn = view.findViewById(R.id.addbtn);
        cccBtn = view.findViewById(R.id.cccbtn);
        //listviwe = view.findViewById(R.id.listview);

        shoudong = view.findViewById(R.id.shoudong);
        scan = view.findViewById(R.id.scan);
        shuaxin = view.findViewById(R.id.shuaxin);





        OkGo.<String>post(url+"queryBeehive")
                .params("uname", CurrentUser.uname)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.NO_CACHE)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                       // User user = JSONObject.parseObject(response.body(), User.class);
                        Log.i("data", response.body());
                        List<Beehive> data = JSON.parseArray(response.body().toString(),Beehive.class);
                        CurrentUser.bcount = data.size();
                        for (int i = 0;i<data.size();i++){
                            System.out.println("地址"+i+":"+data.get(i).getBaddress());
                            System.out.println("bid"+i+":"+data.get(i).getBid());
                            System.out.println("uid"+i+":"+data.get(i).getUid());
                            j[i]=data.get(i).getBid();
                            System.out.println("数组"+i+":"+j[i]);
                            CurrentUser.bid[i]=j[i];

                        }

                    }


                });



        shuaxin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //for(int i =0;i<CurrentUser.bcount)
                addData();
                context  = getContext();

                recyclerView = view.findViewById(R.id.recyclerView_two);
                list = new ArrayList<>();
                for (int i =0;i<CurrentUser.bcount;i++){
                    //list.set(1,"蜂箱");
                    list.add(String.valueOf(CurrentUser.bid[i]));



                }
                recycleAdapter = new RecycleAdapter(context,list);
                LinearLayoutManager manager = new LinearLayoutManager(context);
                manager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(recycleAdapter);

            }
        });


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            addBtn.setVisibility(View.GONE);
            cccBtn.setVisibility(View.VISIBLE);
            shoudong.setVisibility(View.VISIBLE);
            scan.setVisibility(View.VISIBLE);


            }
        });

        cccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBtn.setVisibility(View.VISIBLE);
                cccBtn.setVisibility(View.GONE);
                shoudong.setVisibility(View.GONE);
                scan.setVisibility(View.GONE);
            }
        });

        shoudong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("手动添加");
                Toast.makeText(getContext(),"手动添加",Toast.LENGTH_SHORT);
                final AddDialog addDialog = new AddDialog(getContext(),R.style.AddDialog);
                addDialog.show();


            }
        });

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("扫面二维码添加");

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1);
               // Toast.makeText(this,"扫面二维码添加",Toast.LENGTH_SHORT);
            }
        });




        context  = getContext();

        recyclerView = view.findViewById(R.id.recyclerView_two);
        list = new ArrayList<>();
        for (int i =0;i<CurrentUser.bcount;i++){
            //list.set(1,"蜂箱");
            list.add(String.valueOf(+CurrentUser.bid[i]));


        }
        recycleAdapter = new RecycleAdapter(context,list);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(recycleAdapter);


        return view;
    }


}


