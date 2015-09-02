package com.example.naoya.todomanager;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;


public class DetailActivity extends ActionBarActivity {

    ToDoDataAdaptor toDoDataAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Log.d("myApp", "setContentView");
        toDoDataAdaptor = (ToDoDataAdaptor)getIntent().getSerializableExtra("toDoDataAdaptor");
        Log.d("myApp", "setSerializableExtra");
        initDetailActivity();

    }
    public void initDetailActivity(){
        TextView textView = (TextView)findViewById(R.id.title);
        textView.setText(toDoDataAdaptor.getTitle());
        textView = (TextView)findViewById(R.id.comment);
        textView.setText(toDoDataAdaptor.getComment());
        textView = (TextView)findViewById(R.id.due_day);
        textView.setText(toDoDataAdaptor.getDueDate().toString());
        textView = (TextView)findViewById(R.id.edited_day);
        textView.setText(toDoDataAdaptor.getEditedDate().toString());
        textView = (TextView)findViewById(R.id.remainder_day);
        textView.setText(toDoDataAdaptor.getReminderDate().toString());
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
        }
        return super.onOptionsItemSelected(item);
    }

    private void toast(String text){
        if(text == null) text = "";
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
