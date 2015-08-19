package com.example.naoya.todomanager;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;


public class EditActivity extends ActionBarActivity {

    private EditText title;
    private Date dueDay;
    private Spinner importance;
    private Date remindDay;
    private Spinner group;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    public void registerToDoData(){

        realm = Realm.getInstance(this);
        realm.beginTransaction();
        ToDoData toDoData = realm.createObject(ToDoData.class); // Create a new object

        toDoData.setEditDay(Calendar.getInstance().getTime());
        title = (EditText)findViewById(R.id.name);
        toDoData.setTitle(title.toString());

        realm.commitTransaction();
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        boolean editFinished = false;
        //noinspection SimplifiableIfStatement
        item.getItemId();
        switch (item.getItemId()) {
            case R.id.menu1:                // メニュー１選択時の処理
                editFinished = true;
                registerToDoData();
                break;
            default:
                break;
        }
        if(editFinished)finish();
        return super.onOptionsItemSelected(item);
    }
}
