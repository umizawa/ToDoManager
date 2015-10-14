package com.example.naoya.todomanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;


public class SearchViewActivity extends ActionBarActivity {
    static Realm realm;
    static RealmQuery<ToDoData> query;
    static RealmResults<ToDoData> result;
    List<CellData> cellDataList;
    CellAdapter cellAdapter;
    ListView listView;
    Calendar nowCalendar = Calendar.getInstance();
    AlertDialog.Builder alertDialog;
    private SearchView searchView;
    private String searchWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar_main);
        setSupportActionBar(toolbar);

        realm = Realm.getInstance(this, "test.realm");
        setListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_view, menu);
        MenuItem searchItem = menu.findItem(R.id.searchView);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void setRealmToCellDataList(){
        for (ToDoData toDoData : result) {
            CellData cellData = new CellData(toDoData.getIndex(), R.mipmap.ic_launcher, toDoData.getDueDate(), toDoData.getTitle());
            cellDataList.add(cellData);
        }
    }
    public void setSearchListView(String title){
        query = realm.where(ToDoData.class).equalTo("title",title);
        result = query.findAll();
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
                toDoDataAdaptor.ToDoDataAdaptor(result.get(position));
                intent.putExtra("toDoDataAdaptor", toDoDataAdaptor);
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
    public void setListView(){
        query = realm.where(ToDoData.class);
        result = query.findAll();
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
                toDoDataAdaptor.ToDoDataAdaptor(result.get(position));
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
        alertDialog.setMessage(result.get(id).getTitle() + " を削除しますか？");

        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                realm.beginTransaction();
                result.remove(id);
                realm.commitTransaction();
                setListView();
                toast("削除しました");
            }
        });
        alertDialog.setNeutralButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

    }
    private SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String searchWord) {
            // SubmitボタンorEnterKeyを押されたら呼び出されるメソッド
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            if (newText == null){
                query = realm.where(ToDoData.class);
                setRealmToCellDataList();
                listView = (ListView) findViewById(R.id.list_view);
                listView.setAdapter(cellAdapter);
            }
            else {
                setSearchListView(newText);
            }
            return false;
        }
    };
    private void toast(String text){
        if(text == null) text = "";
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
