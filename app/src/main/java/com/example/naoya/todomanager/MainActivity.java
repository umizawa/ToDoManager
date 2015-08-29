package com.example.naoya.todomanager;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;


public class MainActivity extends ActionBarActivity { //ツールバー

    Realm realm;
    RealmQuery<ToDoData> query;
    RealmResults<ToDoData> result;
    List<CellData> cellDataList;
    CellAdapter cellAdapter;
    ListView listView;
    Calendar nowCalendar = Calendar.getInstance();

    @Override                                                                                       //アクティビティ起動時に呼び出し
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        realm = Realm.getInstance(this, "test.realm");
        query = realm.where(ToDoData.class);
        result = query.findAll();
        cellDataList = new ArrayList<>();
        setRealmToCellDataList();
        cellAdapter = new CellAdapter(this,cellDataList);
        listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(cellAdapter);

        toast(result.size() + "個の項目があります。");

    }
    public void setRealmToCellDataList(){
        for (ToDoData toDoData : result) {
            CellData cellData = new CellData(R.mipmap.ic_launcher, toDoData.getDueDate(), toDoData.getTitle());
            cellDataList.add(cellData);
        }
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
                toast("項目を追加します");
                Intent intent = new Intent(this,EditActivity.class);
                startActivity(intent);
                //toast(result.size() + "個の項目があります。");
                break;
            case R.id.menu2:                // メニュー2選択時の処理
                toast("検索");
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void toast(String text){
        if(text == null) text = "";
        Toast.makeText(this, text,Toast.LENGTH_SHORT).show();
    }

}
