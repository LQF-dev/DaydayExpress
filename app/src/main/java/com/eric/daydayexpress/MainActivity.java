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
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mdrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
