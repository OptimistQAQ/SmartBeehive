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

public class BeehiveInfoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecycleAdapter recycleAdapter;
    private Context context;
    private List<String> list1;

    private FrameLayout frame;
    private RadioGroup group;
    private RadioButton info;
    private RadioButton history;

    private RadioButton info1;
    private RadioButton history1;


   // private FragmentMain fragmentMain;
    private HistoryFragment historyFragment;
    private MainFragment mainFragment;
    private FragmentControl fragmentControl;
    private FragmentMy fragmentMy;

    private List<Fragment> list;

    public BeehiveInfoActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beehive_info);
        initView();

    }

    public void initView(){
        frame = findViewById(R.id.frame);
        group = findViewById(R.id.group);
        info = findViewById(R.id.info);
        info1 = findViewById(R.id.info1);
        history = findViewById(R.id.history);
        history1 = findViewById(R.id.history1);


        //mainFragment = new FragmentMain();
        mainFragment = new MainFragment();
        historyFragment = new HistoryFragment();
        fragmentControl = new FragmentControl();
        fragmentMy = new FragmentMy();

        list = new ArrayList<>();
        list.add(mainFragment);
        list.add(historyFragment);
       //list.add(fragmentMy);

        group.check(R.id.info1);

        info.setOnClickListener(this::OnClick);
        history.setOnClickListener(this::OnClick);
       
        addFragment(mainFragment);


    }

    public void finish(){
        ViewGroup viewGroup =(ViewGroup) getWindow().getDecorView();
        viewGroup.removeAllViews();
        super.finish();
    }

    public void OnClick(View v){
        switch (v.getId()){
            case R.id.info:
                info1.setVisibility(View.VISIBLE);
                info.setVisibility(View.GONE);
                history.setVisibility(View.VISIBLE);
                history1.setVisibility(View.GONE);
               
                addFragment(mainFragment);
                break;
            case R.id.history:
                history1.setVisibility(View.VISIBLE);
                history.setVisibility(View.GONE);
                info.setVisibility(View.VISIBLE);
                info1.setVisibility(View.GONE);


                addFragment(historyFragment);

                break;


        }
    }

    public void addFragment(Fragment fragment){
        //获得Fragment管理器
        FragmentManager fragmentManager = getSupportFragmentManager();
        //使用管理器开启事务
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //使用事务替换Fragemnt容器中对象
        fragmentTransaction.replace(R.id.beehiveframe,fragment);
        fragmentTransaction.commit();
    }
}