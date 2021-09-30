package cn.nuc.edu.smartbeehive;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.List;

import cn.nuc.edu.smartbeehive.articel.Articel1;
import cn.nuc.edu.smartbeehive.articel.Articel2;
import cn.nuc.edu.smartbeehive.articel.Articel3;
import cn.nuc.edu.smartbeehive.articel.Articel4;
import cn.nuc.edu.smartbeehive.recycleview.MainPage;
import cn.nuc.edu.smartbeehive.recycleview.RecycleAdapter;
import cn.nuc.edu.smartbeehive.utils.CurrentUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentMain#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMain extends Fragment {


    public static String[] article = new String[]{"蜜蜂传粉好处","什么蜜蜂适合传粉","蜜蜂传粉如何节约时间","蜜蜂传粉如何节约人工成本"};

    private RecyclerView recyclerView;
    private MainPage mainPage;
    private Context context;
    private List<String> list;
    private TextView haochu;
    private TextView why;
    private TextView jiazhi;
    private TextView jieyue;

    private VideoView videoView;
    private RecyclerView recyclerView_3;

   // public VideoView myVideoView;
    private int position = 0;
    private MediaController mediaControls;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static boolean pause = false;


    public FragmentMain() {
        // Required empty public constructor
    }



    public static FragmentMain newInstance(String param1, String param2) {
        FragmentMain fragment = new FragmentMain();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_main, container, false);
        videoView = view.findViewById(R.id.videoview);
        haochu = view.findViewById(R.id.haochu);
        why = view.findViewById(R.id.why);
        jiazhi = view.findViewById(R.id.jiazhi);
        jieyue = view.findViewById(R.id.jieyue);
        //recyclerView_3 = view.findViewById(R.id.recyclerView_3);

       // String videoUrl = "https://cn-gddg-dx-bcache-15.bilivideo.com/upgcxcode/66/56/251935666/251935666-1-208.mp4?e=ig8euxZM2rNcNbhM7WdVhwdlhzKBhwdVhoNvNC8BqJIzNbfq9rVEuxTEnE8L5F6VnEsSTx0vkX8fqJeYTj_lta53NCM=&uipk=5&nbs=1&deadline=1625465553&gen=playurlv2&os=bcache&oi=242540142&trid=00002c0edecb3edb4b92b7c50898f8284059T&platform=html5&upsig=210140f4f6065c7f53c43ea5a6870104&uparams=e,uipk,nbs,deadline,gen,os,oi,trid,platform&cdnid=61315&mid=580104086&bvc=vod&orderid=0,1&logo=80000000";
        String videoUrl = "http://www.mziot.top:8080/videos/bee.flv";
        Uri uri = Uri.parse(videoUrl);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.canPause();


        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.start();
                videoView.setBackground(null);

            }
        });



        haochu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Articel1.class);
                startActivity(intent);
            }
        });

        why.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getContext(), Articel2.class);
                startActivity(intent1);
            }
        });


        jiazhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getContext(), Articel3.class);
                startActivity(intent2);
            }
        });


        jieyue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(getContext(), Articel4.class);
                startActivity(intent3);
            }
        });
//        context  = getContext();
//        recyclerView = view.findViewById(R.id.recyclerView_two);
//        list = new ArrayList<>();
//        for (int i = 0; i< 4; i++){
//            list.add(article[i]);
//        }
//        mainPage = new MainPage(context,list);
//        LinearLayoutManager manager = new LinearLayoutManager(context);
//        manager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(manager);
//        recyclerView.setAdapter(mainPage);


        return view;
    }
}