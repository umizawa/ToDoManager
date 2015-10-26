package com.example.naoya.todomanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    AlertDialog.Builder alertDialog;

    static final String REALM_FILE_NAME = "test.realm";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar_main);
        setSupportActionBar(toolbar);

        ToDoAdaptor.getInstance().getRealmInstance(this, REALM_FILE_NAME);

        setListView();
        toast(ToDoAdaptor.getInstance().getResultSize() + "個の項目があります。");

    }

    public void setListView(){
        List<CellData> cellDataList = new ArrayList<>();
        ToDoAdaptor.getInstance().setRealmToCellDataList(cellDataList);
        CellAdapter cellAdapter = new CellAdapter(this,cellDataList);

        listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(cellAdapter);
        alertDialog = new AlertDialog.Builder(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("myApp", "position = " + position);

                CellData cellData = (CellData)listView.getItemAtPosition(position);
                int index = cellData.getIndex();

                Log.d("myApp", "index = " + index);
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("index", index);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("myApp", "position = " + position);

                CellData cellData = (CellData)listView.getItemAtPosition(position);
                int index = cellData.getIndex();

                Log.d("myApp", "index = " + index);
                initDeleteDialog(index);
                alertDialog.show();
                return true;
            }
        });
    }

    public void initDeleteDialog(final int id){

        alertDialog.setTitle("削除");
        alertDialog.setMessage(ToDoAdaptor.getInstance().getToDoData(id).getTitle() + "を削除しますか？");

        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                ToDoAdaptor.getInstance().remove(id);
                setListView();
                toast("削除しました");
            }
        });
        alertDialog.setNeutralButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onResume(){
        super.onResume();
        setListView();
        toast(ToDoAdaptor.getInstance().getResultSize() + "個の項目があります。");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        item.getItemId();
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_settings:                // 設定選択時の処理
                toast("settings");
                break;
            case R.id.action_add:                // メニュー１選択時の処理
                toast("項目を追加します");
                intent = new Intent(this, EditActivity.class);
                startActivity(intent);
                break;
            case R.id.action_search:                // メニュー2選択時の処理
                toast("検索");
                intent = new Intent(this, SearchViewActivity.class);
                startActivity(intent);
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
