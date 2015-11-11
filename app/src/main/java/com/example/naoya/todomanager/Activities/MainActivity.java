package com.example.naoya.todomanager.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.FloatingActionButton;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.naoya.todomanager.CellAdapters.CellAdapter;
import com.example.naoya.todomanager.CellAdapters.CellData;
import com.example.naoya.todomanager.R;
import com.example.naoya.todomanager.RealmObjects.ToDoAdaptor;
import com.example.naoya.todomanager.RealmObjects.ToDoData;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    static final String REALM_FILE_NAME = "test.realm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar_main);
        setSupportActionBar(toolbar);
        ToDoAdaptor.getInstance().getRealmInstance(this, REALM_FILE_NAME);
        setListView();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                toast("項目を追加します");
                intent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intent);
            }
        });

        toast(ToDoAdaptor.getInstance().getResultSize() + "個の項目があります。");
    }

    public void setListView(){
        List<CellData> cellDataList = new ArrayList<>();
        ToDoAdaptor.getInstance().setRealmToCellDataList(cellDataList);
        CellAdapter cellAdapter = new CellAdapter(this,cellDataList);

        listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(cellAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CellData cellData = (CellData)listView.getItemAtPosition(position);
                final int toDoId = cellData.getId();
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("id", toDoId);
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                CellData cellData = (CellData)listView.getItemAtPosition(position);
                final int toDoId = cellData.getId();
                initDeleteDialog(toDoId);
                return true;
            }
        });
    }

    public void initDeleteDialog(final int id){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("削除");
        alertDialog.setMessage(ToDoAdaptor.getInstance().getToDoData(id).getTitle() + "を削除しますか？");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                ToDoAdaptor.getInstance().remove(ToDoData.class, id);
                setListView();
                toast("削除しました");
            }
        });
        alertDialog.setNeutralButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_settings:
                toast("settings");
                break;
            case R.id.action_add:
                toast("項目を追加します");
                intent = new Intent(this, EditActivity.class);
                startActivity(intent);
                break;
            case R.id.action_search:
                toast("検索");
                intent = new Intent(this, SearchViewActivity.class);
                startActivity(intent);
                break;
            case R.id.edit_tag:
                toast("タグの編集");
                intent = new Intent(this, TagEditActivity.class);
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
