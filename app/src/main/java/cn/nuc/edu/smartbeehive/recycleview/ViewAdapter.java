package cn.nuc.edu.smartbeehive.recycleview;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.nuc.edu.smartbeehive.MyApplication;
import cn.nuc.edu.smartbeehive.R;
import cn.nuc.edu.smartbeehive.SensorData;
import cn.nuc.edu.smartbeehive.bean.Sensor;
import cn.nuc.edu.smartbeehive.item.RecycleItem;
import cn.nuc.edu.smartbeehive.utils.SimpleSensor;
import me.drakeet.multitype.ItemViewBinder;

public class ViewAdapter extends ItemViewBinder<RecycleItem, ViewAdapter.MyViewHolder> {

    private TextView fengxiang1;
    private TextView wendu1;
    private TextView shidu1;
    private TextView zhongliang1;
    private TextView huoyuedu1;
    private TextView cunhuolv1;

    private Context context;
    private RecycleItem recycleItem;
    private List<String> list;
    private View root;
    MyApplication myApplication = new MyApplication();
    private String url=myApplication.http_ip;
    public ViewAdapter(Context context , List<String> list){
        this.context=context;
        this.list = list;
    }

    @NonNull
    @Override
    protected MyViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {

        root = inflater.inflate(R.layout.item_recycler_layout,parent,false);
        return new MyViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder myViewHolder, @NonNull RecycleItem item) {
        fengxiang1 = root.findViewById(R.id.fengxaing1);


        this.recycleItem = item;

        fengxiang1.setText(item.getBid());
        wendu1.setText((int) item.getBeehive_tem());
        shidu1.setText(item.getBeehive_hum());
        zhongliang1.setText((int) item.getBeehive_weight());
        huoyuedu1.setText(item.getBee_activity());
        cunhuolv1.setText((int) item.getBee_survivalRate());

        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleSensor.bbid = item.getBid();
                Intent intent1 = new Intent(context, SensorData.class);
                context.startActivity(intent1);
            }
        });

    }


    static class MyViewHolder extends RecyclerView.ViewHolder{


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


        }
    }
}
