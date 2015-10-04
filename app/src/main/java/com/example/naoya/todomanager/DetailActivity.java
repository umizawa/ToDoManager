package com.example.naoya.todomanager;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;


public class DetailActivity extends AppCompatActivity {
    Realm realm;
    RealmResults<ToDoData> result;
    RealmQuery<ToDoData> query;
    Intent intent;
    int position;
    ToDoDataAdaptor toDoDataAdaptor;
    ToDoData toDoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intent = getIntent();
        position = intent.getIntExtra("position", 0);

        realm = Realm.getInstance(this, "test.realm");
        query = realm.where(ToDoData.class);
        result = query.findAll();
        toDoData = result.get(position);

        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Log.d("myApp", "setContentView");
        toDoDataAdaptor = (ToDoDataAdaptor)getIntent().getSerializableExtra("toDoDataAdaptor");
        Log.d("myApp", "setSerializableExtra//");
        //initDetailActivity();
        initDetailActivityProto();

    }
    public void initDetailActivity() {
        TextView textView = (TextView) findViewById(R.id.title);
        textView.setText(toDoDataAdaptor.getTitle());
        textView = (TextView) findViewById(R.id.comment);
        textView.setText(toDoDataAdaptor.getComment());
        textView = (TextView) findViewById(R.id.due_day);
        textView.setText(toDoDataAdaptor.getDueDate().toString());
        textView = (TextView) findViewById(R.id.edited_day);
        textView.setText(toDoDataAdaptor.getEditedDate().toString());
        textView = (TextView) findViewById(R.id.remainder_day);
        textView.setText(toDoDataAdaptor.getReminderDate().toString());
        setImportanceOnDetailView();
        setRepeatFlagOnDetailView();
    }
    public void initDetailActivityProto(){
        TextView textView = (TextView)findViewById(R.id.title);
        textView.setText(toDoData.getTitle());
        textView = (TextView)findViewById(R.id.comment);
        textView.setText(toDoData.getComment());
        textView = (TextView)findViewById(R.id.due_day);
        textView.setText(toDoData.getDueDate().toString());
        textView = (TextView)findViewById(R.id.edited_day);
        textView.setText(toDoData.getEditedDate().toString());
        textView = (TextView)findViewById(R.id.remainder_day);
        textView.setText(toDoData.getReminderDate().toString());
        setImportanceOnDetailView();
        setRepeatFlagOnDetailView();
    }
    public void setImportanceOnDetailView(){
        TextView textView = (TextView)findViewById(R.id.importancee);
        switch (toDoDataAdaptor.getImportance()){
            case 0:
                textView.setText("低");
                break;
            case 1:
                textView.setText("中");
                break;
            case 2:
                textView.setText("高");
                break;
            default:
                break;
        }
    }
    public void setRepeatFlagOnDetailView(){
        TextView textView = (TextView)findViewById(R.id.repeat);
        if (toDoDataAdaptor.getRepeatFlag()){
            textView.setText("する");
        }
        else {
            textView.setText("しない");
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
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
        }
        return super.onOptionsItemSelected(item);
    }

    private void toast(String text){
        if(text == null) text = "";
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
