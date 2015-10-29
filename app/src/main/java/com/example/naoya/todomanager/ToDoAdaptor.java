package com.example.naoya.todomanager;

import android.content.Context;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.exceptions.RealmMigrationNeededException;

public class ToDoAdaptor {
    private Realm realm;
    static final String ID_COLUMN_NAME = "id";


    private ToDoAdaptor(){}

    private static class SingletonHolder {
        private static final ToDoAdaptor instance = new ToDoAdaptor();
    }

    public static ToDoAdaptor getInstance() {
        return SingletonHolder.instance;
    }

    public void getRealmInstance(Context context, String fileName){
        try {
            realm = Realm.getInstance(context, fileName);
        } catch (RealmMigrationNeededException r) {
            Realm.deleteRealmFile(context, fileName);
            realm = Realm.getInstance(context, fileName);
        }
    }

    public void remove(Class clazz, int id) {
        realm.beginTransaction();
        realm.where(clazz).equalTo(ID_COLUMN_NAME, id).findFirst().removeFromRealm();
        realm.commitTransaction();
    }

    public int getResultSize(){
        return realm.where(ToDoData.class).findAll().size();
    }

    public RealmResults<ToDoData> findToDoByName(String title){
        return realm.where(ToDoData.class).equalTo("title", title).findAll();
    }

    public ToDoData getToDoData(int index) {
        return realm.where(ToDoData.class).equalTo(ID_COLUMN_NAME, index).findFirst();
    }

    public void setToDoData(String title, String place, String comment, int imageResourceId,
                            int importance, Date dueDate, Date reminderDate,
                            boolean repeatFrag, boolean finishFrag, String tag) {

        ToDoData toDoData = new ToDoData();

        toDoData.setId((int) realm.where(ToDoData.class).maximumInt(ID_COLUMN_NAME) + 1);
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
        toDoData.setTag(tag);

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(toDoData);
        realm.commitTransaction();
    }

    public void setToDoData(int id, String title, String place, String comment, int imageResourceId,
                                 int importance, Date dueDate, Date reminderDate,
                                 boolean repeatFrag, boolean finishFrag, String tag){

        ToDoData toDoData = new ToDoData();

        toDoData.setId(id);
        toDoData.setTitle(title);
        toDoData.setPlace(place);
        toDoData.setComment(comment);
        toDoData.setImageResourceId(imageResourceId);
        toDoData.setImportance(importance);
        toDoData.setRepeatFlag(repeatFrag);
        toDoData.setEditedDate(ToDoAdaptor.getInstance().getToDoData(id).getEditedDate());
        toDoData.setDueDate(dueDate);
        toDoData.setReminderDate(reminderDate);
        toDoData.setFinishFlag(finishFrag);
        toDoData.setTag(tag);

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(toDoData);
        realm.commitTransaction();
    }

    public void setRealmToCellDataList(List<CellData> list){
        for (ToDoData toDoData : realm.where(ToDoData.class).findAll()) {
            CellData cellData = new CellData(toDoData.getId(), toDoData.getImageResourceId(),
                    toDoData.getDueDate(), toDoData.getTitle());
            list.add(cellData);
            Log.d("myApp", "RW index = " + toDoData.getId());
        }
    }

    public void setRealmToCellDataList(List<CellData> list, String string){
        for (ToDoData toDoData : realm.where(ToDoData.class).contains("title", string).findAll()) {
            CellData cellData = new CellData(toDoData.getId(), toDoData.getImageResourceId(),
                    toDoData.getDueDate(), toDoData.getTitle());
            list.add(cellData);
            Log.d("myApp", "RW index = " + toDoData.getId());
        }
    }

    public void setRealmTagToCellDataList(List<TagCellData> list){
        for (ToDoTag toDoTag : realm.where(ToDoTag.class).findAll()) {
            TagCellData tagCellData= new TagCellData(toDoTag.getId(), toDoTag.getName());
            list.add(tagCellData);
            Log.d("myApp", "RW index = " + toDoTag.getId());
        }
    }

    public void setRealmTagToCellDataList(List<TagCellData> list, String string){
        for (ToDoTag toDoTag : realm.where(ToDoTag.class).contains("title", string).findAll()) {
            TagCellData tagCellData = new TagCellData(toDoTag.getId(), toDoTag.getName());
            list.add(tagCellData);
            Log.d("myApp", "RW index = " + toDoTag.getId());
        }
    }

    public void setTag(int id, String name){
        ToDoTag toDoTag = new ToDoTag();
        toDoTag.setId(id);
        toDoTag.setName(name);
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(toDoTag);
        realm.commitTransaction();
    }

    public void setTag(String name){
        ToDoTag toDoTag = new ToDoTag();
        toDoTag.setId((int) realm.where(ToDoTag.class).maximumInt(ID_COLUMN_NAME) + 1);
        toDoTag.setName(name);
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(toDoTag);
        realm.commitTransaction();
    }

    public String getTag(int id){
        return realm.where(ToDoTag.class).equalTo("id", id).findFirst().getName();
    }

    public String[] getTagStringArray(){
        RealmResults<ToDoTag> results = realm.where(ToDoTag.class).findAll();
        String[] string = new String[results.size()];
        for(int i = 0; i < string.length; i++){
            string[i] = results.get(i).getName();
        }
        return string;
    }

    public boolean[] getTagFlagArray(String tagString){
        RealmResults<ToDoTag> results = realm.where(ToDoTag.class).findAll();
        boolean[] chkTagFlag = new boolean[results.size()];
        for(int i = 0; i < chkTagFlag.length; i++){
            chkTagFlag[i] = tagString.contains(results.get(i).getName());
        }
        return chkTagFlag;
    }

}