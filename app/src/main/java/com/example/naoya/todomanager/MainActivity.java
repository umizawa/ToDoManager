package com.example.naoya.todomanager;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity { //ツールバー

    @Override                                                                                       //アクティビティ起動時に呼び出し
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        item.getItemId();
        switch (item.getItemId()) {
            case R.id.action_settings:                // 設定選択時の処理
                toast("settings");
                break;
            case R.id.menu1:                // メニュー１選択時の処理
                toast("menu1");
                break;
            case R.id.menu2:                // メニュー2選択時の処理
                toast("menu2");
                break;
            case R.id.menu3:                // メニュー3選択時の処理
                toast("menu3");
                break;
            case R.id.menu4:                // メニュー4選択時の処理
                toast("menu4");
                break;
            case R.id.menu5:                // メニュー5選択時の処理
                toast("menu5");
                break;
            case R.id.menu6:                // メニュー6選択時の処理
                toast("menu6");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void toast(String text){
        if(text == null) text = "";
        Toast.makeText(this, text,Toast.LENGTH_SHORT).show();
    }

}
