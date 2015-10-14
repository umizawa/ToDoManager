package com.example.naoya.todomanager;

import android.content.Context;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.exceptions.RealmMigrationNeededException;

public class RealmWrapper {
    private Realm realm;
    private RealmQuery<ToDoData> query;
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
        realm.where(ToDoData.class).equalTo("index", id).findFirst().removeFromRealm();
        realm.commitTransaction();
    }

    public int getResultSize(){
        return result.size();
    }

    public ToDoData getToDoData(int index){
        return realm.where(ToDoData.class).equalTo("index", index).findFirst();
    }

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
    }
    public void setRealmToCellDataList(List<CellData> list){

        for (ToDoData toDoData : result) {
            CellData cellData = new CellData(toDoData.getIndex(),toDoData.getImageResourceId(),
                    toDoData.getDueDate(), toDoData.getTitle());
            list.add(cellData);
            Log.d("myApp", "RW index = " + toDoData.getIndex());
        }
    }
}