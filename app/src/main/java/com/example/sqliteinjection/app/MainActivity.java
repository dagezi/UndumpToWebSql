package com.example.sqliteinjection.app;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends Activity {

    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new MyDbOpenHelper(this, "test.db", "test", 1).getReadableDatabase();

        Cursor cursor = database.query(
                /* table */ "prefecture",
                /* columns */ new String[]{"capital"},
                /* where */ null,
                /* where args */ null,
                /* group by */ null,
                /* order by */ null,
                /* limit */ null);

        StringBuilder builder = new StringBuilder();
        for (String name : cursor.getColumnNames()) {
            builder.append(name);
            builder.append("\n");
        }
        ((TextView)findViewById(R.id.text_view)).setText(builder);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
