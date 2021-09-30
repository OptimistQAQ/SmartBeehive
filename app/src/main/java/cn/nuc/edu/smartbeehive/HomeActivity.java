package cn.nuc.edu.smartbeehive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import cn.nuc.edu.smartbeehive.recycleview.RecycleAdapter;
import cn.nuc.edu.smartbeehive.utils.CurrentUser;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecycleAdapter recycleAdapter;
    private Context context;
    private List<String> list1;

    private FrameLayout frame;
    private RadioGroup group;
    private RadioButton zhuye;
    private RadioButton kongzhi;
    private RadioButton wode;
    private RadioButton zhuye1;
    private RadioButton kongzhi1;
    private RadioButton wode1;

    private FragmentMain fragmentMain;
    private HistoryFragment historyFragment;
    private MainFragment mainFragment;
    private FragmentControl fragmentControl;
    private FragmentMy fragmentMy;

    private List<Fragment> list;

    public HomeActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();

    }

    public void initView(){
        frame = findViewById(R.id.frame);
        group = findViewById(R.id.group);
        zhuye = findViewById(R.id.zhuye);
        zhuye1 = findViewById(R.id.zhuye1);
        kongzhi = findViewById(R.id.kongzhi);
        kongzhi1 = findViewById(R.id.kongzhi1);
        wode = findViewById(R.id.wode);
        wode1 = findViewById(R.id.wode1);

        fragmentMain = new FragmentMain();
        mainFragment = new MainFragment();
        historyFragment = new HistoryFragment();
        fragmentControl = new FragmentControl();
        fragmentMy = new FragmentMy();

        list = new ArrayList<>();
        list.add(fragmentMain);
        list.add(fragmentControl);
        list.add(fragmentMy);

        group.check(R.id.zhuye1);

        zhuye.setOnClickListener(this::OnClick);
        kongzhi.setOnClickListener(this::OnClick);
        wode.setOnClickListener(this::OnClick);
        addFragment(fragmentMain);


    }

    public void finish(){
        ViewGroup viewGroup =(ViewGroup) getWindow().getDecorView();
        viewGroup.removeAllViews();
        super.finish();
    }

    public void OnClick(View v){
        switch (v.getId()){
            case R.id.zhuye:
                zhuye1.setVisibility(View.VISIBLE);
                zhuye.setVisibility(View.GONE);
                kongzhi.setVisibility(View.VISIBLE);
                kongzhi1.setVisibility(View.GONE);
                wode.setVisibility(View.VISIBLE);
                wode1.setVisibility(View.GONE);
                addFragment(fragmentMain);
                break;
            case R.id.kongzhi:
                kongzhi1.setVisibility(View.VISIBLE);
                kongzhi.setVisibility(View.GONE);
                wode.setVisibility(View.VISIBLE);
                wode1.setVisibility(View.GONE);
                wode.setVisibility(View.VISIBLE);
                zhuye.setVisibility(View.VISIBLE);
                zhuye1.setVisibility(View.GONE);


                addFragment(fragmentControl);

                break;
            case R.id.wode:
                wode1.setVisibility(View.VISIBLE);
                wode.setVisibility(View.GONE);
                zhuye.setVisibility(View.VISIBLE);
                zhuye1.setVisibility(View.GONE);
                kongzhi.setVisibility(View.VISIBLE);
                kongzhi1.setVisibility(View.GONE);
                addFragment(fragmentMy);
                break;



        }
    }

    public void addFragment(Fragment fragment){
        //获得Fragment管理器
        FragmentManager fragmentManager = getSupportFragmentManager();
        //使用管理器开启事务
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //使用事务替换Fragemnt容器中对象
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.commit();
    }
}