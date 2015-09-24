package com.example.naoya.todomanager;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import io.realm.Realm;


public class EditActivity extends ActionBarActivity implements OnClickListener {
    private EditText title;
    private EditText place;
    private EditText comment;
    private Spinner importance;
    private Spinner group;
    private Spinner repeat;
    private Realm realm;
    ToDoData toDoData;
    TextView due_day_picker_text;
    TextView due_time_picker_text;
    TextView reminder_day_picker_text;
    TextView reminder_time_picker_text;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    final Calendar calendar = Calendar.getInstance();
    final Calendar dueDate = Calendar.getInstance();
    final Calendar reminderDate = Calendar.getInstance();
    final int year = calendar.get(Calendar.YEAR);
    final int month = calendar.get(Calendar.MONTH);
    final int day = calendar.get(Calendar.DAY_OF_MONTH);
    final int hour = calendar.get(Calendar.HOUR_OF_DAY);
    final int minute = calendar.get(Calendar.MINUTE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit);
        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar_edit);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setNowDateOnTextView(R.id.due_day_picker_text);
        setNowDateOnTextView(R.id.reminder_day_picker_text);
        setNowTimeOnTextView(R.id.due_time_picker_text);
        setNowTimeOnTextView(R.id.reminder_time_picker_text);

        setClickListenerOnTextViews();
        due_day_picker_text.setOnClickListener(this);
        due_time_picker_text.setOnClickListener(this);
        reminder_day_picker_text.setOnClickListener(this);
        reminder_time_picker_text.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.due_day_picker_text) {
            setDatePickerDialog(R.id.due_day_picker_text);
        }
        else if(v.getId() == R.id.due_time_picker_text) {
            setTimePickerDialog(R.id.due_time_picker_text);
        }
        else if(v.getId() == R.id.reminder_day_picker_text) {
            setDatePickerDialog(R.id.reminder_day_picker_text);
        }
        else if(v.getId() == R.id.reminder_time_picker_text) {
            setTimePickerDialog(R.id.reminder_time_picker_text);
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    public void registerToDoData(){
        realm = Realm.getInstance(this,"test.realm");
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                toDoData = realm.createObject(ToDoData.class);
                toDoData.setEditedDate(Calendar.getInstance().getTime());
                toDoData.setDueDate(dueDate.getTime());
                toDoData.setReminderDate(reminderDate.getTime());
                importance = (Spinner)findViewById(R.id.spinner_importance);
                switch (importance.toString()){
                    case "低":
                        toDoData.setImportance(0);
                        break;
                    case "中":
                        toDoData.setImportance(1);
                        break;
                    case "高":
                        toDoData.setImportance(2);
                        break;
                    default:
                        break;
                }
                repeat = (Spinner)findViewById(R.id.spinner_repeat);
                if(repeat.toString().equals("する")){
                    toDoData.setRepeatFlag(true);
                }
                else{
                    toDoData.setRepeatFlag(false);
                }

                title = (EditText)findViewById(R.id.name);
                toDoData.setTitle(title.getText().toString());
                Log.d("MyApp", title.toString());
                place = (EditText)findViewById(R.id.place);
                toDoData.setPlace(place.getText().toString());
                comment = (EditText) findViewById(R.id.comment);
                toDoData.setComment(comment.getText().toString());
                toDoData.setFinishFlag(false);

            }
        });

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
    public void setNowDateOnTextView(int id){
        TextView textView = (TextView)findViewById(id);
        textView.setText(String.valueOf(year) + "年" +
                String.valueOf(month + 1) + "月" +
                String.valueOf(day) + "日");
    }
    public void setNowTimeOnTextView(int id){
        TextView textView = (TextView) findViewById(id);
        textView.setText(String.valueOf(hour) + "時" +
                String.valueOf(minute) + "分");
    }
    public void setDatePickerDialog(final int id){
        datePickerDialog = new DatePickerDialog(
            this,
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    TextView textView = (TextView)findViewById(id);
                    textView.setText(String.valueOf(year) + "年" +
                            String.valueOf(monthOfYear + 1) + "月" +
                            String.valueOf(dayOfMonth) + "日");
                    if(id == R.id.due_day_picker_text) {
                        dueDate.set(Calendar.YEAR, year);
                        dueDate.set(Calendar.MONTH, monthOfYear + 1);
                        dueDate.set(Calendar.DATE, dayOfMonth);
                    }
                    else if (id == R.id.reminder_day_picker_text) {
                        reminderDate.set(Calendar.YEAR, year);
                        reminderDate.set(Calendar.MONTH, monthOfYear + 1);
                        reminderDate.set(Calendar.DATE, dayOfMonth);
                    }
                }
            },
            year, month, day);
        datePickerDialog.show();
    }
    public void setTimePickerDialog(final int id){
        timePickerDialog = new TimePickerDialog(
            this,
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    TextView textView = (TextView) findViewById(id);
                    textView.setText(String.valueOf(hourOfDay) + "時" +
                            String.valueOf(minute) + "分");
                    if(id == R.id.due_time_picker_text) {
                        dueDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        dueDate.set(Calendar.MINUTE, minute);
                    }
                    else if(id == R.id.reminder_time_picker_text) {
                        dueDate.set(Calendar.HOUR_OF_DAY, hour);
                        dueDate.set(Calendar.MINUTE, minute);
                    }
                }
            },
            hour, minute, true);
        timePickerDialog.show();
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
