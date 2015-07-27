package com.example.naoya.todomanager;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    public static final int MENU_ITEM0 = 0;
    public static final int MENU_ITEM1 = 1;
    public static final int MENU_ITEM2 = 2;
    public static final int MENU_ITEM3 = 3;

    @Override                                                                                       //�A�N�e�B�r�e�B�N�����ɌĂяo��
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);

        MenuItem item0 = menu.add(0,MENU_ITEM0,0,"�A�C�e��0");
        item0.setIcon(android.R.drawable.ic_menu_camera);

        MenuItem item1 = menu.add(0,MENU_ITEM1,0,"�A�C�e��1");
        item1.setIcon(android.R.drawable.ic_menu_call);

        MenuItem item2 = menu.add(0,MENU_ITEM2,0,"�A�C�e��2");
        item2.setIcon(android.R.drawable.ic_menu_add);
        item2.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        MenuItem item3 = menu.add(0,MENU_ITEM3,0,"�A�C�e��3");
        item3.setIcon(android.R.drawable.ic_menu_delete);
        item3.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
