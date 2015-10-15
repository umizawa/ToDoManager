package com.example.naoya.todomanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {
    Intent intent;
    int index;
    static RealmWrapper realmWrapper;
    ToDoData toDoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intent = getIntent();
        index = intent.getIntExtra("index", 0);
        realmWrapper = new RealmWrapper(this, "test.realm");

        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toDoData = realmWrapper.getToDoData(index);
        initDetailActivity();

    }
    public void initDetailActivity(){
        Log.d("myApp", "position = " + index);
        Log.d("myApp", "index = " + toDoData.getIndex());
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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

    private void toast(String text){
        if(text == null) text = "";
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
