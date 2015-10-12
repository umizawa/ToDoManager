package com.example.naoya.todomanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<CellData> cellDataList;
    CellAdapter cellAdapter;
    ListView listView;
    AlertDialog.Builder alertDialog;

    static RealmWrapper realmWrapper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar_main);
        setSupportActionBar(toolbar);

        realmWrapper = new RealmWrapper(this,"test.realm");

        setListView();
        toast(realmWrapper.getResultSize() + "個の項目があります。");

    }

    public void setListView(){
        cellDataList = new ArrayList<>();
        setRealmToCellDataList();

        cellAdapter = new CellAdapter(this,cellDataList);

        listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(cellAdapter);
        alertDialog = new AlertDialog.Builder(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                ToDoDataAdaptor toDoDataAdaptor = new ToDoDataAdaptor();
                toDoDataAdaptor.ToDoDataAdaptor(realmWrapper.getToDoData((int)id));
                intent.putExtra("toDoDataAdaptor", toDoDataAdaptor);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                initDeleteDialog(position);
                alertDialog.show();
                return true;
            }
        });
    }

    public void initDeleteDialog(final int id){

        alertDialog.setTitle("削除");
        alertDialog.setMessage(realmWrapper.getToDoData(id).getTitle() + "を削除しますか？");

        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                realmWrapper.remove(id);
                setListView();
                toast("削除しました");
            }
        });
        alertDialog.setNeutralButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

    }

    public void setRealmToCellDataList(){
        for (int i = 0; i < realmWrapper.getResultSize(); i++) {
            ToDoData toDoData = realmWrapper.getToDoData(i);
            CellData cellData = new CellData(toDoData.getIndex(),toDoData.getImageResourceId(),
                    toDoData.getDueDate(), toDoData.getTitle());
            cellDataList.add(cellData);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }
    @Override
    protected void onResume(){
        super.onResume();
        setListView();
        toast(realmWrapper.getResultSize() + "個の項目があります。");
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
                Intent intent = new Intent(this, EditActivity.class);
                startActivity(intent);
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
