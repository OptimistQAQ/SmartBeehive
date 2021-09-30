package cn.nuc.edu.smartbeehive;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

import cn.nuc.edu.smartbeehive.utils.CurrentUser;
import cn.nuc.edu.smartbeehive.utils.SimpleSensor;


public class HistoryFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {

    private View root;
    private LineChart lineChart, humChart;
    private List<Entry> list = new ArrayList<>();
    private SeekBar seekBarX, seekBarY;
    private TextView tvX, tvY;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_history, container, false);
        init();
        initView();
        return root;
    }

    private void init() {
        tvX = root.findViewById(R.id.tvXMax);
        tvY = root.findViewById(R.id.tvYMax);
//TODO
        seekBarX = root.findViewById(R.id.seekBar1);
        seekBarY = root.findViewById(R.id.seekBar2);

        lineChart = root.findViewById(R.id.line_tem1);
        lineChart.setViewPortOffsets(0, 0, 0, 0);
        lineChart.setBackgroundColor(Color.rgb(104, 241, 175));

        // no description text
        lineChart.getDescription().setEnabled(false);

        // enable touch gestures
        lineChart.setTouchEnabled(true);

        // enable scaling and dragging
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        lineChart.setPinchZoom(false);

        lineChart.setDrawGridBackground(false);
        lineChart.setMaxHighlightDistance(300);

        XAxis x = lineChart.getXAxis();
//        x.setEnabled(true);
        x.setAxisLineColor(Color.BLACK);
        x.setAxisLineWidth(2);
        x.setDrawLabels(true);
        x.setLabelCount(10);
        x.setTextColor(Color.BLACK);
        x.setPosition(XAxis.XAxisPosition.BOTTOM);        //X轴所在位置   默认为上面
        x.setDrawGridLines(false);
        x.setTextSize(20f);

        YAxis y = lineChart.getAxisLeft();
        y.setLabelCount(6, false);
        y.setTextColor(Color.BLACK);
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        y.setDrawGridLines(false);
        y.setAxisLineColor(Color.BLACK);

        lineChart.getAxisRight().setEnabled(false);

        //TODO
        // add data
        seekBarY.setOnSeekBarChangeListener(this);
        seekBarX.setOnSeekBarChangeListener(this);
        setData();

        lineChart.getLegend().setEnabled(true);
        lineChart.getLegend().setTextSize(20.0f);
        lineChart.getLegend().setXOffset(300.0f);

        lineChart.animateXY(2000, 2000);

        // don't forget to refresh the drawing
        lineChart.invalidate();
    }

    private void setData() {

        ArrayList<Entry> values = new ArrayList<>();

        OkGo.<String>post("http://123.56.86.163:3535/sensorinfo")
                .params("bid", "1")
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("response", response.body());
                        response.toString();
                        JSONArray jsonArray = JSON.parseArray(response.body());
                        for (int i=0; i<jsonArray.size(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            if (!jsonObject.getString("beehiveTem").equals("")) {
                                values.add(new Entry(i, Float.parseFloat(jsonObject.getString("beehiveTem"))));
                            }
                        }


                        LineDataSet set1;

                        seekBarX.setMax(values.size() + 10);
                        seekBarY.setMax((int) values.get(0).getX() + 10);
                        seekBarX.setProgress(seekBarX.getProgress());
                        seekBarY.setProgress(100);

                        if (lineChart.getData() != null &&
                                lineChart.getData().getDataSetCount() > 0) {
                            set1 = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
                            set1.setValues(values);
                            lineChart.getData().notifyDataChanged();
                            lineChart.notifyDataSetChanged();
                        } else {
                            // create a dataset and give it a type
                            set1 = new LineDataSet(values, "温度数据");

                            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                            set1.setCubicIntensity(0.2f);
                            set1.setDrawFilled(true);
                            set1.setDrawCircles(false);
                            set1.setLineWidth(1.8f);
                            set1.setCircleRadius(4f);
                            set1.setCircleColor(Color.WHITE);
                            set1.setHighLightColor(Color.rgb(244, 117, 117));
                            set1.setColor(Color.WHITE);
                            set1.setFillColor(Color.WHITE);
                            set1.setFillAlpha(100);
                            set1.setValueTextSize(16f);
                            set1.setDrawHorizontalHighlightIndicator(false);
                            set1.setFillFormatter(new IFillFormatter() {
                                @Override
                                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                                    return lineChart.getAxisLeft().getAxisMinimum();
                                }

                            });

                            // create a data object with the data sets
                            LineData data = new LineData(set1);
                            data.setValueTextSize(9f);
                            data.setDrawValues(true);

                            // set data
                            lineChart.setData(data);
                        }
                    }
                });
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        tvX.setText(String.valueOf(seekBarX.getProgress()));
        tvY.setText(String.valueOf(seekBarY.getProgress()));

        setData();

        // redraw
        lineChart.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}

    private void initView() {

        humChart = (LineChart) root.findViewById(R.id.line_hum);
        //添加数据

        ArrayList<Entry> values = new ArrayList<>();
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        OkGo.<String>post("http://123.56.86.163:3535/sensorinfo")
                .params("bid", "1")
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("response", response.body());
                        response.toString();
                        JSONArray jsonArray = JSON.parseArray(response.body());
                        for (int i=0; i<jsonArray.size(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            list.add(new Entry(i, Float.parseFloat(jsonObject.getString("beehiveHum"))));
                            values.add(new Entry(i, Float.parseFloat(jsonObject.getString("soilHum"))));
                        }

                        LineDataSet lineDataSet=new LineDataSet(list,"蜂箱湿度");   //list是你这条线的数据  "语文" 是你对这条线的描述
                        LineDataSet lineSoilHum = new LineDataSet(values, "土壤湿度");

                        dataSets.add(lineDataSet);
                        dataSets.add(lineSoilHum);
                        LineData data = new LineData(dataSets);
                        humChart.setData(data);

                        //折线图背景
                        humChart.setBackgroundColor(Color.rgb(104, 241, 175));   //背景颜色
                        humChart.getXAxis().setDrawGridLines(false);  //是否绘制X轴上的网格线（背景里面的竖线）
                        humChart.getAxisLeft().setDrawGridLines(false);  //是否绘制Y轴上的网格线（背景里面的横线）

                        //对于右下角一串字母的操作
                        humChart.getDescription().setEnabled(false);                  //是否显示右下角描述
                        humChart.getDescription().setText("这是修改那串英文的方法");    //修改右下角字母的显示
                        humChart.getDescription().setTextSize(20);                    //字体大小
                        humChart.getDescription().setTextColor(Color.RED);             //字体颜色

                        //图例
                        Legend legend=humChart.getLegend();
                        legend.setEnabled(true);    //是否显示图例
                        legend.setTextSize(20f);
//        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);    //图例的位置

                        //X轴
                        XAxis xAxis=humChart.getXAxis();
                        xAxis.setDrawGridLines(false);  //是否绘制X轴上的网格线（背景里面的竖线）
                        xAxis.setAxisLineColor(Color.RED);   //X轴颜色
                        xAxis.setAxisLineWidth(2);           //X轴粗细
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);        //X轴所在位置   默认为上面
//                        xAxis.setValueFormatter(new ValueFormatter() {   //X轴自定义坐标
//                            @Override
//                            public String getFormattedValue(float v, AxisBase axisBase) {
//                                if (v==1){
//                                    return "第一个";
//                                }
//                                if (v==2){
//                                    return "第二个";
//                                }
//                                if (v==3){
//                                    return "第三个";
//                                }
//                                return "";//注意这里需要改成 ""
//                            }
//                        });
                        xAxis.setAxisMaximum(values.size() + 1);   //X轴最大数值
                        xAxis.setAxisMinimum(0);   //X轴最小数值
                        //X轴坐标的个数    第二个参数一般填false     true表示强制设置标签数 可能会导致X轴坐标显示不全等问题
                        xAxis.setLabelCount(values.size() + 1,false);


                        //Y轴
                        YAxis AxisLeft=humChart.getAxisLeft();
                        AxisLeft.setDrawGridLines(false);  //是否绘制Y轴上的网格线（背景里面的横线）
                        AxisLeft.setAxisLineColor(Color.BLUE);  //Y轴颜色
                        AxisLeft.setAxisLineWidth(2);           //Y轴粗细
//                        AxisLeft.setValueFormatter(new ValueFormatter() {  //Y轴自定义坐标
//                            @Override
//                            public String getFormattedValue(float v, AxisBase axisBase) {
//
//                                for (int a=0;a<16;a++){     //用个for循环方便
//                                    if (a==v){
//                                        return "第"+a+"个";
//                                    }
//                                }
//                                return "";
//                            }
//                        });
                        AxisLeft.setAxisMaximum(100);   //Y轴最大数值
                        AxisLeft.setAxisMinimum(0);   //Y轴最小数值
                        //Y轴坐标的个数    第二个参数一般填false     true表示强制设置标签数 可能会导致X轴坐标显示不全等问题
                        AxisLeft.setLabelCount(100,false);

                        //是否隐藏右边的Y轴（不设置的话有两条Y轴 同理可以隐藏左边的Y轴）
                        humChart.getAxisRight().setEnabled(false);


                        //折线
                        //设置折线的式样   这个是圆滑的曲线（有好几种自己试试）     默认是直线
                        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                        lineDataSet.setColor(Color.RED);  //折线的颜色
                        lineDataSet.setLineWidth(2);        //折线的粗细
                        lineDataSet.setValueTextSize(16f);
                        lineSoilHum.setColor(Color.BLUE);
                        lineSoilHum.setCircleColor(Color.BLACK);
                        lineSoilHum.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                        lineSoilHum.setLineWidth(2);
                        lineSoilHum.setValueTextSize(16f);
                        //是否画折线点上的空心圆  false表示直接画成实心圆
                        lineDataSet.setDrawCircleHole(false);
                        lineDataSet.setCircleHoleRadius(3);  //空心圆的圆心半径
                        lineSoilHum.setDrawCircleHole(false);
                        //圆点的颜色     可以实现超过某个值定义成某个颜色的功能   这里先不讲 后面单独写一篇
                        lineDataSet.setCircleColor(Color.RED);
                        lineDataSet.setCircleRadius(3);      //圆点的半径
                        //定义折线上的数据显示    可以实现加单位    以及显示整数（默认是显示小数）
                        lineDataSet.setValueFormatter(new ValueFormatter() {
                            @Override
                            public String getFormattedValue(float v, Entry entry, int i, ViewPortHandler viewPortHandler) {
                                if (entry.getY()==v){
                                    return v+"℃";
                                }
                                return "";
                            }
                        });

                        //数据更新
                        humChart.notifyDataSetChanged();
                        humChart.invalidate();

                        //动画（如果使用了动画可以则省去更新数据的那一步）
                        humChart.animateY(3000); //折线在Y轴的动画  参数是动画执行时间 毫秒为单位
//        line.animateX(2000); //X轴动画
//        line.animateXY(2000,2000);//XY两轴混合动画
                    }
                });

//        values.add(new Entry(0,10));     //其中两个数字对应的分别是   X轴   Y轴
//        values.add(new Entry(1,7));
//        values.add(new Entry(2,6));
//        values.add(new Entry(3,8));
//        values.add(new Entry(4,12));
//
//
//        list.add(new Entry(0,7));     //其中两个数字对应的分别是   X轴   Y轴
//        list.add(new Entry(1,10));
//        list.add(new Entry(2,12));
//        list.add(new Entry(3,6));
//        list.add(new Entry(4,3));
    }
}
