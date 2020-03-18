package com.eric.daydayexpress;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eric.daydayexpress.Util.KdniaoTrackQueryAPI;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DrawerLayout mdrawerLayout;
    private EditText expCode;
    private EditText expNu;
    final String TAG = "MAinActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //实例化各种控件
        Button submit = (Button)findViewById(R.id.submit);
        submit.setOnClickListener(this);

       expCode = (EditText)findViewById(R.id.edit_expCode);
       expNu =  (EditText)findViewById(R.id.edit_expNu);

       androidx.appcompat.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);

        mdrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout) ;

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
          actionBar.setDisplayHomeAsUpEnabled(true);
          //Todo change icon later
          actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        }


    }


@Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.submit:
                 final String code = expCode.getText().toString();
                 final String num = expNu.getText().toString();
                Log.d(TAG, "code = "+ code);
                Log.d(TAG, "num = " + num);
                 if(!(code.equals("STO") || code.equals("YTO")|| code.equals("ZTO")) ){
                     Toast.makeText(this, "仅支持 申通 圆通 中通", Toast.LENGTH_SHORT).show();
                 }else{
                     Toast.makeText(this, "查询成功", Toast.LENGTH_SHORT).show();
                 }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("MainActivity", "run:begin ");
                        KdniaoTrackQueryAPI api = new KdniaoTrackQueryAPI();
                        try {
                            //TODO  打开另一个activity
                            String result = api.getOrderTracesByJson(code, num);
                            Log.d("MainActivity", result);
                        }catch(Exception e){
                            e.printStackTrace();
                        }

                    }
                }).start();
        }

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

            case android.R.id.home:
                Log.d("Mainactovity", "onOptionsItemSelected: open_drawer");
                mdrawerLayout.openDrawer(GravityCompat.START);
                break;
                default:
                    break;
        }
        return true;

    }
}
