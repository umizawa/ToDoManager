package com.example.naoya.todomanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SearchViewActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    List<CellData> cellDataList;
    ListView listView;
    AlertDialog.Builder alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);
        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar_main);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setSearchListView("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        final MenuItem item = menu.add("Search");
        item.setIcon(android.R.drawable.ic_menu_search);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        final SearchView sv = new SearchView(this);
        sv.setOnQueryTextListener(this);
        sv.setQueryHint("検索文字を入力してください");
        item.setActionView(sv);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_settings:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setSearchListView(String title) {
        List<CellData> cellDataList = new ArrayList<>();
        if(title != null) {
            if (title.equals("")) {
                ToDoAdaptor.getInstance().setRealmToCellDataList(cellDataList);
            } else {
                ToDoAdaptor.getInstance().setRealmToCellDataList(cellDataList, title);
            }
        }
        CellAdapter cellAdapter = new CellAdapter(this,cellDataList);
        listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(cellAdapter);
        alertDialog = new AlertDialog.Builder(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CellData cellData = (CellData)listView.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("id", cellData.getId());
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                CellData cellData = (CellData)listView.getItemAtPosition(position);
                initDeleteDialog(cellData.getId());
                alertDialog.show();
                return true;
            }
        });
    }

    public void initDeleteDialog(final int id){

        alertDialog.setTitle("削除");
        alertDialog.setMessage(ToDoAdaptor.getInstance().getToDoData(id).getTitle() + " を削除しますか？");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                ToDoAdaptor.getInstance().remove(id);
                setSearchListView("");
                toast("削除しました");
            }
        });
        alertDialog.setNeutralButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

    }

    @Override
    public boolean onQueryTextSubmit(String searchWord) {
        if (searchWord == null) {
            ToDoAdaptor.getInstance().setRealmToCellDataList(cellDataList);
        } else {
            setSearchListView(searchWord);
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText == null){
            ToDoAdaptor.getInstance().setRealmToCellDataList(cellDataList);
        } else {
            setSearchListView(newText);
        }
        return false;
    }

    private void toast(String text){
        if(text == null) text = "";
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}