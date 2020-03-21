package com.eric.daydayexpress;

import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eric.daydayexpress.Gson.logistics;
import com.eric.daydayexpress.Trace.LoadTrace;
import com.eric.daydayexpress.Trace.LoadTraceAdapter;
import com.eric.daydayexpress.Util.KdniaoTrackQueryAPI;
import com.eric.daydayexpress.Util.utility;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class TraceActivity extends AppCompatActivity {


    //private logistics logisticsInfo;
    private String TAG = "TraceActivity";
    private String LogisticCode;
    private String ShipperCode;
    private String State;
    private String Reason = null;//判断符
    private int StateToInt;
    private Boolean Success;
    private List<String> stations;
    private List<String>times;


    private List<LoadTrace>loadTraces =new ArrayList<>();

    private RecyclerView recyclerView;

    private TextView tv_logisticsCode;
    private ImageView iv_companyImage;
    private TextView tv_state;
    private TextView tv_showNumber;
    private TextView tv_time_total;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_trace);
        //实例化控件 以及数组
       // tv_logisticsCode = (TextView)findViewById(R.id.tv);
        iv_companyImage = (ImageView)findViewById(R.id.iv_companyImage);
        tv_state = (TextView)findViewById(R.id.text_state);
        tv_showNumber = (TextView)findViewById(R.id.text_showNumber);
        tv_time_total = (TextView)findViewById(R.id.tv_time_total);
        stations = new ArrayList<>();
        times = new ArrayList<>();

        //得到toolbar_trace
        androidx.appcompat.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_trace);
        setSupportActionBar(toolbar);

        //recycleView实例化
        recyclerView  =(RecyclerView)findViewById(R.id.recycle_view_trace);

        SharedPreferences preferences = getSharedPreferences("data",MODE_PRIVATE);
        final String code = preferences.getString("code","NUll");
        final String num = preferences.getString("number","Null");
        Log.d(TAG, "code  "+ code);
        Log.d(TAG, "number  " + num);

       new Thread(new Runnable() {


            @Override
           public void run() {
                try {
                    Log.d(TAG, "run: 进入run");
                    KdniaoTrackQueryAPI api = new KdniaoTrackQueryAPI();
                    String respond = api.getOrderTracesByJson(code, num);
                    //String respond = new KdniaoTrackQueryAPI().getOrderTracesByJson(code,num);
                    Log.d(TAG, "respond:  " + respond);
                    JSONObject jsonObject = new JSONObject(respond);
                    JSONArray array = jsonObject.getJSONArray("Traces");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        String AcceptStation = object.getString("AcceptStation");
                        stations.add(AcceptStation);
                        String AcceptTime = object.getString("AcceptTime");
                        times.add(AcceptTime);
                        Log.d(TAG, "AcceptStation:" + stations.get(i));
                        Log.d(TAG, "AcceptTime : " + times.get(i));
                        Log.d(TAG, "AcceptTime size: " + times.size());
                    }

                    LogisticCode = jsonObject.getString("LogisticCode");
                    Log.d(TAG, "LogisticCode: " + LogisticCode);

                    ShipperCode = jsonObject.getString("ShipperCode");
                    Log.d(TAG, "ShipperCode: " + ShipperCode);

                    State = jsonObject.getString("State");
                    Log.d(TAG, "State: " + State);
                    try {
                        StateToInt = Integer.parseInt(State);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        Reason = jsonObject.getString("Reason");
                        Log.d(TAG, "Reason: " + Reason);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d(TAG, "Reason: 正常查询无Reason 现在Reason：" + Reason);

                    }

                    Success = jsonObject.getBoolean("Success");
                    Log.d(TAG, "Success : " + Success);

                    //logisticsInfo = utility.handleJsonFromKdNiao(respond);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(   Reason==null  ){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        showInfo();
                    }
                });

            }//is
                else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showDefaultInfo();
                        }
                    });


                }

           }
       }).start();//runable


     /*   //TODO 更新界面 只要进来的就都是成功地
      //主线程更新界面
      // runOnUiThread(new Runnable() {
          //  @Override
          //  public void run() {
                Log.d(TAG, "run: 进去更新界面的Run");

                if(   Reason==null  ){    //输入正确Reason为默认值null
                    
                    tv_showNumber.setText(code);
                   // tv_time_total.setText(times.get(times.size()));
                    showTraceInfo();

                }else  {//TODO 默认输错的情况下下 分两种情况 公司编号没对（Success返回false） 单号没对返回true 但返回reason


                }





          //  }//run
       // });*/


    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.scan:
                Toast.makeText(this, "你点击了扫一扫按钮", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;

    }



    public void initLoadTrace(){

        for(int i =0;i < times.size();i++){

            LoadTrace loadTraceItem = new LoadTrace(stations.get(i),times.get(i));
            loadTraces.add(loadTraceItem);

        }

    }

    public void showTraceInfo(){
        initLoadTrace();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        LoadTraceAdapter adapter = new LoadTraceAdapter(loadTraces);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

    }


    public void showInfo(){
        showTraceInfo();
        String first  = times.get(0);
        String last = times.get(times.size()-1);




        tv_showNumber.setText(LogisticCode);

    }
    public void showDefaultInfo(){
        tv_showNumber.setText("暂无");
        Toast.makeText(this, "请输入正确信息", Toast.LENGTH_SHORT).show();
    }

}
