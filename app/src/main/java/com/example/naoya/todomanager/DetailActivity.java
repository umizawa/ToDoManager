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
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar_detail);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        initDetailActivity(ToDoAdaptor.getInstance().getToDoData(id));

    }
    public void initDetailActivity(ToDoData toDoData) {
        Log.d("myApp", "position = " + id);
        Log.d("myApp", "id = " + toDoData.getId());
        TextView textView = (TextView)findViewById(R.id.title);
        textView.setText(toDoData.getTitle());
        textView = (TextView)findViewById(R.id.comment);
        textView.setText(toDoData.getComment());
        textView = (TextView)findViewById(R.id.place);
        textView.setText(toDoData.getPlace());
        textView = (TextView)findViewById(R.id.tag);
        textView.setText(toDoData.getTag());
        textView = (TextView)findViewById(R.id.due_day);
        textView.setText(dataConverter.getDateTimeString(toDoData.getDueDate()));
        textView = (TextView)findViewById(R.id.edited_day);
        textView.setText(dataConverter.getDateTimeString(toDoData.getEditedDate()));
        textView = (TextView)findViewById(R.id.remainder_day);
        textView.setText(dataConverter.getDateTimeString(toDoData.getReminderDate()));
        setImportanceOnDetailView(toDoData);
        setRepeatFlagOnDetailView(toDoData);
    }

    public void setImportanceOnDetailView(ToDoData toDoData) {
        TextView textView = (TextView)findViewById(R.id.importance);
        textView.setText(dataConverter.getImportanceString(toDoData.getImportance()));
    }

    public void setRepeatFlagOnDetailView(ToDoData toDoData) {
        TextView textView = (TextView)findViewById(R.id.repeat);
        textView.setText(dataConverter.getRepeatFlagString(toDoData.getRepeatFlag()));
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
                intent.putExtra("id", id);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume(){
        super.onResume();
        initDetailActivity(ToDoAdaptor.getInstance().getToDoData(id));
    }
}
