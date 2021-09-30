package cn.nuc.edu.smartbeehive.recycleview;

import android.accounts.NetworkErrorException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;

import org.w3c.dom.Text;

import java.util.List;

import cn.nuc.edu.smartbeehive.AddDialog;
import cn.nuc.edu.smartbeehive.BeehiveInfoActivity;
import cn.nuc.edu.smartbeehive.FragmentMain;
import cn.nuc.edu.smartbeehive.HomeActivity;
import cn.nuc.edu.smartbeehive.LongTouchDialog;
import cn.nuc.edu.smartbeehive.MainActivity;
import cn.nuc.edu.smartbeehive.MainFragment;
import cn.nuc.edu.smartbeehive.MyApplication;
import cn.nuc.edu.smartbeehive.R;
import cn.nuc.edu.smartbeehive.SensorData;
import cn.nuc.edu.smartbeehive.bean.Beehive;
import cn.nuc.edu.smartbeehive.utils.CurrentUser;
import cn.nuc.edu.smartbeehive.utils.SimpleSensor;
import cn.nuc.edu.smartbeehive.utils.Status;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder> {

    private Context context;
    private List<String> list;
    private View inflater;
    MyApplication myApplication = new MyApplication();
    private String url=myApplication.http_ip;
    public RecycleAdapter(Context context ,List<String> list){
        this.context=context;
        this.list = list;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(context).inflate(R.layout.item_recycler_layout,parent,false);

        MyViewHolder myViewHolder = new MyViewHolder(inflater);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.fengxaing1.setText("蜂箱编号："+list.get(position));



        // holder.wendu1.setText(list.get(position));
        inflater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("456888:"+list.get(position));
                SimpleSensor.bbbid = Integer.parseInt(list.get(position));
                System.out.println("456777:"+SimpleSensor.bbbid);
                Intent intent1 = new Intent(context, BeehiveInfoActivity.class);
                context.startActivity(intent1);

            }
        });

        inflater.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                SimpleSensor.bbid = Integer.parseInt(list.get(position));
                //Toast.makeText(context,"长按",Toast.LENGTH_SHORT).show();
                final LongTouchDialog longTouchDialog= new LongTouchDialog(context,R.style.LongTouch);
                longTouchDialog.show();
                return true;
            }
        });


    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView fengxaing1;
        TextView zhaungtai;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Beehive beehive  = new Beehive();
            fengxaing1 = itemView.findViewById(R.id.fengxaing1);
            zhaungtai = itemView.findViewById(R.id.fxzhaungtai);
           // zhaungtai.setText(Status.getStatus());



        }
    }
}
