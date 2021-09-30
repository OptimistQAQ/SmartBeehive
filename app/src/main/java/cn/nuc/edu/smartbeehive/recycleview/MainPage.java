package cn.nuc.edu.smartbeehive.recycleview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.nuc.edu.smartbeehive.MyApplication;
import cn.nuc.edu.smartbeehive.R;
import cn.nuc.edu.smartbeehive.SensorData;
import cn.nuc.edu.smartbeehive.bean.Beehive;
import cn.nuc.edu.smartbeehive.utils.SimpleSensor;

public class MainPage extends  RecyclerView.Adapter<MainPage.MyViewHolder>  {
    private Context context;
    private List<String> list;
    private View root;
    MyApplication myApplication = new MyApplication();
    private String url=myApplication.http_ip;

    public MainPage(Context context ,List<String> list){
        this.context=context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        root = LayoutInflater.from(context).inflate(R.layout.activity_main_page,parent,false);

        MainPage.MyViewHolder myViewHolder = new MainPage.MyViewHolder(root);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainPage.MyViewHolder holder, int position) {
        holder.wenzhang.setText(list.get(position));
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView wenzhang;
        //TextView zhaungtai;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Beehive beehive  = new Beehive();
            wenzhang = itemView.findViewById(R.id.wenzhang);




        }
    }


}