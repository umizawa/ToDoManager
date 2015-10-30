package com.example.naoya.todomanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SearchViewActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    ListView listView;
    Toolbar toolbar;
    List<CellData> cellDataList;
    String filterWord = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);
        toolbar = (Toolbar)findViewById(R.id.tool_bar_main);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_view, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView)searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(false);

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

    public void setListView() {
        cellDataList = new ArrayList<>();
        ToDoAdaptor.getInstance().setRealmToCellDataList(cellDataList);
        CellAdapter cellAdapter = new CellAdapter(this,cellDataList);
        listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(cellAdapter);
        listView.setTextFilterEnabled(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CellData cellData = (CellData) listView.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("id", cellData.getId());
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                CellData cellData = (CellData) listView.getItemAtPosition(position);
                initDeleteDialog(cellData.getId());
                return true;
            }
        });
    }

    public void initDeleteDialog(final int id){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("削除");
        alertDialog.setMessage(ToDoAdaptor.getInstance().getToDoData(id).getTitle() + " を削除しますか？");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                ToDoAdaptor.getInstance().remove(ToDoData.class, id);
                ToDoAdaptor.getInstance().setRealmToCellDataList(cellDataList);
                setListView();
                Filter filter = ((Filterable) listView.getAdapter()).getFilter();
                filter.filter(filterWord);
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
    public boolean onQueryTextChange(String newText) {
        filterWord = newText;
        if (TextUtils.isEmpty(newText)){
            Filter filter = ((Filterable) listView.getAdapter()).getFilter();
            filter.filter("");
            Log.d("newText","Empty");
        } else {
            Filter filter = ((Filterable) listView.getAdapter()).getFilter();
            filter.filter(filterWord);
            Log.d("newText", "Not empty");
        }
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String searchWord) {
        return false;
    }

    private void toast(String text){
        if(text == null) text = "";
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}