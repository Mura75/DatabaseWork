package example.murager.com.contactbookdb.activities.activies;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Objects;

import example.murager.com.contactbookdb.R;
import example.murager.com.contactbookdb.activities.adapters.DatabaseAdapter;
import example.murager.com.contactbookdb.activities.classes.DatabaseConnector;


public class MainActivity extends ActionBarActivity {

    ListView listViewContact;

    DatabaseAdapter adapter;

    Cursor cursor;

    public static final String ROW_ID = "row_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewContact = (ListView)findViewById(R.id.listViewContact);

        String[] from = new String[]{"name", "surname"};

        int[] to = new int[]{R.id.textViewShowName, R.id.textViewShowSurname};

        listViewContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, PersonContact.class);
                intent.putExtra(ROW_ID, id);
                startActivity(intent);
            }
        });

        adapter = new DatabaseAdapter(MainActivity.this,
                              R.layout.contact_item_row, null, from, to);

        listViewContact.setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
        if (id == R.id.add_contact) {
            Intent intent = new Intent(MainActivity.this, AddContact.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.delete_allcontact){
            AsyncTask<Long, Object, Object> deleteTask = new AsyncTask<Long, Object, Object>(){
                @Override
                protected Object doInBackground(Long... params){
                    DatabaseConnector databaseConnector = new DatabaseConnector(MainActivity.this);
                    databaseConnector.deleteAllContact();
                    return null;
                }
                @Override
                protected void onPostExecute(Object result)  {
                    adapter.swapCursor(cursor);
                }
            };

            deleteTask.execute();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected  void onResume(){
        super.onResume();
        new GetContactTask().execute((Object[]) null);
    }

    @Override
    protected void onStop(){
        Cursor cursor = adapter.getCursor();
        if (cursor != null)
            cursor.deactivate();
        adapter.changeCursor(null);
        super.onStop();
    }

    private class GetContactTask extends AsyncTask<Object, Object, Cursor>{

        DatabaseConnector databaseConnector = new DatabaseConnector(MainActivity.this);

        @Override
        protected Cursor doInBackground(Object... params) {
            databaseConnector.open();
            return databaseConnector.getAllContact();
        }

        @Override
        protected void onPostExecute(Cursor result){
            adapter.changeCursor(result);
            databaseConnector.close();
        }
    }
}
