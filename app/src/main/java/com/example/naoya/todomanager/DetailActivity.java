package com.example.naoya.todomanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    int index;
    static ToDoAdaptor toDoAdaptor;
    ToDoData toDoData;
    private final String REALM_FILE_NAME = "test.realm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        index = intent.getIntExtra("index", 0);
        toDoAdaptor = new ToDoAdaptor(this, REALM_FILE_NAME);

        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar_detail);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toDoData = toDoAdaptor.getToDoData(index);
        initDetailActivity();

    }
    public void initDetailActivity(){
        Log.d("myApp", "position = " + index);
        Log.d("myApp", "index = " + toDoData.getIndex());
        TextView textView = (TextView)findViewById(R.id.title);
        textView.setText(toDoData.getTitle());
        textView = (TextView)findViewById(R.id.comment);
        textView.setText(toDoData.getComment());
        textView =(TextView)findViewById(R.id.place);
        textView.setText(toDoData.getPlace());
        textView = (TextView)findViewById(R.id.due_day);
        textView.setText(dateConverter.getDateTimeString(toDoData.getDueDate()));
        textView = (TextView)findViewById(R.id.edited_day);
        textView.setText(dateConverter.getDateTimeString(toDoData.getEditedDate()));
        textView = (TextView)findViewById(R.id.remainder_day);
        textView.setText(dateConverter.getDateTimeString(toDoData.getReminderDate()));
        setImportanceOnDetailView();
        setRepeatFlagOnDetailView();
    }
    public void setImportanceOnDetailView(){
        TextView textView = (TextView)findViewById(R.id.importance);
        switch (toDoData.getImportance()){
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
        if (toDoData.getRepeatFlag()){
            textView.setText("する");
        }
        else {
            textView.setText("しない");
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.getItemId();
        switch (item.getItemId()) {
            case R.id.action_edit:                // 設定選択時の処理
                Intent intent = new Intent(this, EditActivity.class);
                intent.putExtra("addMode", false);
                intent.putExtra("index", index);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume(){
        super.onResume();
        toDoData = toDoAdaptor.getToDoData(index);
        initDetailActivity();
    }
}
