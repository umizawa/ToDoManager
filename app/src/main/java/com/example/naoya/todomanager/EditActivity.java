package com.example.naoya.todomanager;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
    private EditText title;
    private EditText place;
    private EditText comment;
    private Spinner importanceSpinner;
    private Spinner repeatSpinner;
    private TextView due_day_picker_text;
    private TextView due_time_picker_text;
    private TextView reminder_day_picker_text;
    private TextView reminder_time_picker_text;
    private TextView tag;
    private Calendar dueDate = Calendar.getInstance();
    private Calendar reminderDate = Calendar.getInstance();
    private Calendar calendarHolder = Calendar.getInstance();

    String tagString = "";

    Intent intent;
    int id;
    boolean addMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initTextViews(this);

        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar_edit);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        intent = getIntent();
        addMode = intent.getBooleanExtra("addMode", true);

        if(!addMode) {
            id = intent.getIntExtra("id", 0);
            setToDoDataAsDefault(ToDoAdaptor.getInstance().getToDoData(id));
            tagString = ToDoAdaptor.getInstance().getToDoData(id).getTag();
        } else {
            setNowTimeAsDefault();
        }



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.due_day_picker_text:
                setDatePickerDialog(R.id.due_day_picker_text);
                break;
            case R.id.due_time_picker_text:
                setTimePickerDialog(R.id.due_time_picker_text);
                break;
            case R.id.reminder_day_picker_text:
                setDatePickerDialog(R.id.reminder_day_picker_text);
                break;
            case R.id.reminder_time_picker_text:
                setTimePickerDialog(R.id.reminder_time_picker_text);
                break;
            case R.id.tag:
                setCheckBoxDialog();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    public void setNowTimeAsDefault(){
        setNowDateOnTextView(R.id.due_day_picker_text);
        setNowDateOnTextView(R.id.reminder_day_picker_text);
        setNowTimeOnTextView(R.id.due_time_picker_text);
        setNowTimeOnTextView(R.id.reminder_time_picker_text);

    }
    public void setToDoDataAsDefault(ToDoData toDoData){
        title.setText(toDoData.getTitle());
        place.setText(toDoData.getPlace());
        due_day_picker_text.setText(dataConverter.getDateString(toDoData.getDueDate()));
        due_time_picker_text.setText(dataConverter.getTimeString(toDoData.getDueDate()));
        dueDate.setTime(toDoData.getDueDate());
        reminder_day_picker_text.setText(dataConverter.getDateString(toDoData.getReminderDate()));
        reminder_time_picker_text.setText(dataConverter.getTimeString(toDoData.getReminderDate()));
        reminderDate.setTime(toDoData.getReminderDate());
        comment.setText(toDoData.getComment());
        importanceSpinner.setSelection(toDoData.getImportance());
        tag.setText(toDoData.getTag());
    }

    public void registerToDoData(){
        int imageResourceId = R.mipmap.ic_launcher;
        int importance = dataConverter.getImportanceInteger((String) importanceSpinner.getSelectedItem());
        boolean repeatFrag = repeatSpinner.getSelectedItem().equals("する");
        if(title.getText().toString().equals("")){
            title.setText("無題");
        }
        if(addMode) {
            ToDoAdaptor.getInstance().setToDoData(title.getText().toString(),
                    place.getText().toString(), comment.getText().toString(),
                    imageResourceId, importance, dueDate.getTime(),
                    reminderDate.getTime(), repeatFrag, false, tagString);
        } else {
            ToDoAdaptor.getInstance().setToDoData(id, title.getText().toString(),
                    place.getText().toString(), comment.getText().toString(),
                    imageResourceId, importance, dueDate.getTime(),
                    reminderDate.getTime(), repeatFrag, false, tagString);
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
        tag = (TextView)findViewById(R.id.tag);
        tag.setClickable(true);
    }

    public void initTextViews(OnClickListener onClickListener){
        title = (EditText)findViewById(R.id.name);
        place = (EditText)findViewById(R.id.place);
        comment = (EditText) findViewById(R.id.comment);
        importanceSpinner = (Spinner)findViewById(R.id.spinner_importance);
        repeatSpinner = (Spinner)findViewById(R.id.spinner_repeat);
        setClickListenerOnTextViews();
        due_day_picker_text.setOnClickListener(onClickListener);
        due_time_picker_text.setOnClickListener(onClickListener);
        reminder_day_picker_text.setOnClickListener(onClickListener);
        reminder_time_picker_text.setOnClickListener(onClickListener);
        tag.setOnClickListener(onClickListener);
    }

    public void setNowDateOnTextView(int id){
        TextView textView = (TextView)findViewById(id);
        textView.setText(dataConverter.getDateString(calendarHolder));
    }

    public void setNowTimeOnTextView(int id){
        TextView textView = (TextView) findViewById(id);
        textView.setText(dataConverter.getTimeString(calendarHolder));
    }

    public void setDatePickerDialog(final int id){
        DatePickerDialog datePickerDialog;
        if (id == R.id.due_day_picker_text) {
            calendarHolder = dueDate;
        } else if (id == R.id.reminder_day_picker_text) {
            calendarHolder = reminderDate;
        }
        datePickerDialog = new DatePickerDialog(
            this,
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    TextView textView = (TextView)findViewById(id);
                    textView.setText(String.valueOf(year) + "年" +
                            String.valueOf(monthOfYear + 1) + "月" +
                            String.valueOf(dayOfMonth) + "日");
                    if (id == R.id.due_day_picker_text) {
                        dueDate.set(Calendar.YEAR, year);
                        dueDate.set(Calendar.MONTH, monthOfYear);
                        dueDate.set(Calendar.DATE, dayOfMonth);
                    } else if (id == R.id.reminder_day_picker_text) {
                        reminderDate.set(Calendar.YEAR, year);
                        reminderDate.set(Calendar.MONTH, monthOfYear);
                        reminderDate.set(Calendar.DATE, dayOfMonth);
                    }
                }
            },
                calendarHolder.get(Calendar.YEAR), calendarHolder.get(Calendar.MONTH), calendarHolder.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
    public void setTimePickerDialog(final int id){
        if (id == R.id.due_day_picker_text) {
            calendarHolder = dueDate;
        } else if (id == R.id.reminder_day_picker_text) {
            calendarHolder = reminderDate;
        }
        TimePickerDialog timePickerDialog;
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
                }, calendarHolder.get(Calendar.HOUR_OF_DAY), calendarHolder.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    public void setCheckBoxDialog() {
        String str = tag.getText().toString();
        final String[] chkItems = ToDoAdaptor.getInstance().getTagStringArray();
        final boolean[] chkSts = ToDoAdaptor.getInstance().getTagFlagArray(str);

        AlertDialog.Builder checkDlg = new AlertDialog.Builder(this);
        checkDlg.setTitle("タグを選択してください");
        checkDlg.setMultiChoiceItems(
                chkItems,
                chkSts,
                new DialogInterface.OnMultiChoiceClickListener() {
                    public void onClick(DialogInterface dialog, int which, boolean flag) {
                        chkSts[which] = flag;
                    }
                });
        checkDlg.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        tagString = getTagString(chkItems, chkSts);
                        tag.setText(tagString);
                    }
                });
        checkDlg.setNeutralButton(
                "CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        checkDlg.show();
    }
    public String getTagString(String[] chkItems, boolean[] chkSts){
        String tagString = "";
        for(int i = 0; i < chkItems.length; i++){
            if(chkSts[i]){
                tagString += chkItems[i] + " ";
            }
        }
        return tagString;
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
