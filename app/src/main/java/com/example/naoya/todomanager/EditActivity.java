package com.example.naoya.todomanager;

import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;


public class EditActivity extends ActionBarActivity implements OnClickListener {
    private EditText title;
    private Date dueDay;
    private Spinner importance;
    private Date remindDay;
    private Spinner group;
    private Realm realm;
    TextView due_day_picker_text;
    TextView due_time_picker_text;
    TextView reminder_day_picker_text;
    TextView reminder_time_picker_text;
    private AlertDialog.Builder alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setClickListenerOnTextViews();
        due_day_picker_text.setOnClickListener(this);
        due_time_picker_text.setOnClickListener(this);
        reminder_day_picker_text.setOnClickListener(this);
        reminder_time_picker_text.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        alertDialog = new AlertDialog.Builder(this);
        if (v.getId() == R.id.due_day_picker_text) {
            alertDatePicker(v);
        }
        else if(v.getId() == R.id.due_time_picker_text) {
            alertTimePicker(v);
        }
        else if(v.getId() == R.id.reminder_day_picker_text) {
            alertDatePicker(v);
        }
        else if(v.getId() == R.id.reminder_time_picker_text) {
            alertTimePicker(v);
        }

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
    public void setClickListenerOnTextViews(){
        due_day_picker_text = (TextView)findViewById(R.id.due_day_picker_text);
        due_day_picker_text.setClickable(true);
        due_time_picker_text = (TextView)findViewById(R.id.due_time_picker_text);
        due_time_picker_text.setClickable(true);
        reminder_day_picker_text = (TextView)findViewById(R.id.reminder_day_picker_text);
        reminder_day_picker_text.setClickable(true);
        reminder_time_picker_text = (TextView)findViewById(R.id.reminder_time_picker_text);
        reminder_time_picker_text.setClickable(true);
    }

    public void alertDatePicker(View view){
        // レイアウト設定
        View datePickerView = getLayoutInflater().inflate(R.layout.dialog_datepicker, null);
        alertDialog.setTitle("日付を入力してください");      //タイトル設定
        alertDialog.setView(datePickerView);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Log.d("AlertDialog", "Positive which :" + which);
            }
        });
        alertDialog.setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Log.d("AlertDialog", "Negative which :" + which);
            }
        });
        alertDialog.show();
    }

    public void alertTimePicker(View view){
        // レイアウト設定
        View datePickerView = getLayoutInflater().inflate(R.layout.dialog_timepicker, null);
        alertDialog.setTitle("時刻を入力してください");
        alertDialog.setView(datePickerView);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Log.d("AlertDialog", "Positive which :" + which);
            }
        });
        alertDialog.setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Log.d("AlertDialog", "Negative which :" + which);
            }
        });
        alertDialog.show();
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
