package com.example.naoya.todomanager;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class EditActivity extends AppCompatActivity implements OnClickListener {
    static EditText title;
    static EditText place;
    static EditText comment;
    static Spinner importanceSpinner;
    static Spinner repeatSpinner;
    TextView due_day_picker_text;
    TextView due_time_picker_text;
    TextView reminder_day_picker_text;
    TextView reminder_time_picker_text;
    static DatePickerDialog datePickerDialog;
    static TimePickerDialog timePickerDialog;
    final Calendar calendar = Calendar.getInstance();
    final Calendar dueDate = Calendar.getInstance();
    final Calendar reminderDate = Calendar.getInstance();
    Intent intent;
    int index;
    boolean addMode;

    ToDoAdaptor toDoAdaptor;

    private final String REALM_FILE_NAME = "test.realm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        toDoAdaptor = new ToDoAdaptor(this,REALM_FILE_NAME);
        initTextViews();
        due_day_picker_text.setOnClickListener(this);
        due_time_picker_text.setOnClickListener(this);
        reminder_day_picker_text.setOnClickListener(this);
        reminder_time_picker_text.setOnClickListener(this);

        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar_edit);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        intent = getIntent();
        addMode = intent.getBooleanExtra("addMode", true);

        if(!addMode) {
            index = intent.getIntExtra("index", 0);
            ToDoData toDoData = toDoAdaptor.getToDoData(index);
            setToDoDataAsDefault(toDoData);
        } else {
            setNowDateOnTextView(R.id.due_day_picker_text);
            setNowDateOnTextView(R.id.reminder_day_picker_text);
            setNowTimeOnTextView(R.id.due_time_picker_text);
            setNowTimeOnTextView(R.id.reminder_time_picker_text);
        }
    }

    @Override
    public void onClick(View v) {//switch
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

    public void setToDoDataAsDefault(ToDoData toDoData){
        title.setText(toDoData.getTitle());
        place.setText(toDoData.getPlace());
        due_day_picker_text.setText(dateConverter.getDateString(toDoData.getDueDate()));
        due_time_picker_text.setText(dateConverter.getTimeString(toDoData.getDueDate()));
        reminder_day_picker_text.setText(dateConverter.getDateString(toDoData.getReminderDate()));
        reminder_time_picker_text.setText(dateConverter.getTimeString(toDoData.getReminderDate()));
        comment.setText(toDoData.getComment());
        importanceSpinner.setSelection(toDoData.getImportance());
    }

    public void registerToDoData(){
        int importance;
        int imageResourceId = R.mipmap.ic_launcher;
        boolean repeatFrag;

        switch ((String)importanceSpinner.getSelectedItem()){
            case "低":
                importance = 0;
                break;
            case "中":
                importance = 1;
                break;
            case "高":
                importance = 2;
                break;
            default:
                importance = 0;
                break;
        }
        repeatFrag = repeatSpinner.getSelectedItem().equals("する");
        if(addMode) {
            toDoAdaptor.setToDoData(title.getText().toString(), place.getText().toString(),
                    comment.getText().toString(), imageResourceId, importance, dueDate.getTime(),
                    reminderDate.getTime(), repeatFrag, false);
        } else {
            toDoAdaptor.setToDoData(index, title.getText().toString(), place.getText().toString(),
                    comment.getText().toString(), imageResourceId, importance, dueDate.getTime(),
                    reminderDate.getTime(), repeatFrag, false);
        }
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

    public void initTextViews(){
        title = (EditText)findViewById(R.id.name);
        place = (EditText)findViewById(R.id.place);
        comment = (EditText) findViewById(R.id.comment);
        importanceSpinner = (Spinner)findViewById(R.id.spinner_importance);
        repeatSpinner = (Spinner)findViewById(R.id.spinner_repeat);
        setClickListenerOnTextViews();
    }

    public void setNowDateOnTextView(int id){
        TextView textView = (TextView)findViewById(id);
        textView.setText(dateConverter.getDateString(calendar));
    }

    public void setNowTimeOnTextView(int id){
        TextView textView = (TextView) findViewById(id);
        textView.setText(dateConverter.getTimeString(calendar));
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
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
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
                        reminderDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        reminderDate.set(Calendar.MINUTE, minute);
                    }
                }
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean editFinished = false;
        switch (item.getItemId()) {
            case R.id.action_add:                // メニュー１選択時の処理
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
