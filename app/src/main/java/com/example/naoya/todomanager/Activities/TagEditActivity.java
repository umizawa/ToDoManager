package com.example.naoya.todomanager.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.Toast;

import com.example.naoya.todomanager.CellAdapters.TagCellAdapter;
import com.example.naoya.todomanager.CellAdapters.TagCellData;
import com.example.naoya.todomanager.R;
import com.example.naoya.todomanager.RealmObjects.ToDoAdaptor;
import com.example.naoya.todomanager.RealmObjects.ToDoTag;

import java.util.ArrayList;
import java.util.List;


public class TagEditActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private AlertDialog.Builder alertDialog;
    private ListView listView;
    private String filterWord = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_edit);

        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar_main);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initEditDialog();
            }
        });

        setListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tag_edit, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView)searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_add:
                initEditDialog();
                break;
            case R.id.action_settings:
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        filterWord = newText;
        Filter filter = ((Filterable) listView.getAdapter()).getFilter();
        if (TextUtils.isEmpty(newText)){
            filter.filter("");
            Log.d("newText", "Empty");
        } else {
            filter.filter(filterWord);
            Log.d("newText", "Not empty");
        }
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String searchWord) {
        return false;
    }

    public void setListView(){
        List<TagCellData> tagCellDataList = new ArrayList<>();
        ToDoAdaptor.getInstance().setRealmTagToCellDataList(tagCellDataList);
        TagCellAdapter tagCellAdapter = new TagCellAdapter(this,tagCellDataList);

        listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(tagCellAdapter);
        alertDialog = new AlertDialog.Builder(TagEditActivity.this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TagCellData tagCellData = (TagCellData)listView.getItemAtPosition(position);
                final int toDoId = tagCellData.getId();
                initEditDialog(toDoId);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TagCellData tagCellData = (TagCellData)listView.getItemAtPosition(position);
                final int toDoId = tagCellData.getId();
                initDeleteDialog(toDoId);
                return true;
            }
        });
    }

    public void initDeleteDialog(final int id){
        alertDialog.setTitle("削除");
        alertDialog.setMessage(ToDoAdaptor.getInstance().getTag(id) + "を削除しますか？");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                ToDoAdaptor.getInstance().remove(ToDoTag.class, id);
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

    public void initEditDialog() {
        final EditText editView = new EditText(this);
        alertDialog.setTitle("タグの名前を入力してください");
        alertDialog.setView(editView);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int idx) {
                ToDoAdaptor.getInstance().setTag(editView.getText().toString());
                setListView();
            }
        });
        alertDialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int idx) {
            }
        });
        alertDialog.show();
    }

    public void initEditDialog(final int id) {
        final EditText editView = new EditText(this);
        editView.setText(ToDoAdaptor.getInstance().getTag(id));
        alertDialog.setTitle("タグの名前を入力してください");
        alertDialog.setView(editView);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int idx) {
                ToDoAdaptor.getInstance().setTag(id, editView.getText().toString());
                setListView();
            }
        });
        alertDialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int idx) {
            }
        });
        alertDialog.show();
    }

    private void toast(String text){
        if(text == null) text = "";
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

}
