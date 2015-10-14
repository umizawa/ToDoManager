package com.example.naoya.todomanager;

import android.content.Context;

import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.exceptions.RealmMigrationNeededException;

public class RealmWrapper {
    private Realm realm;
    private RealmQuery<ToDoData> query;
    static int index = 0;
    private RealmResults<ToDoData> result;
    static ToDoData toDoData;

    public RealmWrapper(Context context, String fileName){
        try {
            realm = Realm.getInstance(context,fileName);
        } catch (RealmMigrationNeededException r) {
            Realm.deleteRealmFile(context,fileName);
            realm = Realm.getInstance(context,fileName);
        }
        query = realm.where(ToDoData.class);
        result = query.findAll();
    }

    public void remove(int id){
        realm.beginTransaction();
        result.remove(id);
        realm.commitTransaction();
    }

    public int getResultSize(){ return result.size(); }
    public ToDoData getToDoData(int index){ return query.findAll().get(index); }

    public void setToDoData(String title, String place, String comment, int imageResourceId,
                            int importance, Date dueDate, Date reminderDate,
                            boolean repeatFrag, boolean finishFrag){
        realm.beginTransaction();

        toDoData = realm.createObject(ToDoData.class);
        toDoData.setIndex((int)realm.where(ToDoData.class).maximumInt("index") + 1);
        toDoData.setTitle(title);
        toDoData.setPlace(place);
        toDoData.setComment(comment);
        toDoData.setImageResourceId(imageResourceId);
        toDoData.setImportance(importance);
        toDoData.setRepeatFlag(repeatFrag);
        toDoData.setEditedDate(Calendar.getInstance().getTime());
        toDoData.setDueDate(dueDate);
        toDoData.setReminderDate(reminderDate);
        toDoData.setFinishFlag(finishFrag);

        realm.commitTransaction();
        index++;
    }
}
