package example.murager.com.contactbookdb.activities.activies;

import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import example.murager.com.contactbookdb.R;
import example.murager.com.contactbookdb.activities.classes.DatabaseConnector;

public class PersonContact extends ActionBarActivity {

    private long rowID;

    TextView textViewPersonName;
    TextView textViewPersonSurname;
    TextView textViewPersonPhone;
    TextView textViewPersonEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_contact);

        textViewPersonName = (TextView)findViewById(R.id.textViewName);
        textViewPersonSurname = (TextView)findViewById(R.id.textViewSurname);
        textViewPersonPhone = (TextView)findViewById(R.id.textViewPhone);
        textViewPersonEmail = (TextView)findViewById(R.id.textViewEmail);

        rowID = getIntent().getExtras().getLong(MainActivity.ROW_ID);
    }


    @Override
    protected void onResume(){
        super.onResume();
        new loadContactTask().execute(rowID);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_person_contact, menu);
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

    private class loadContactTask extends AsyncTask<Long, Object, Cursor>{

        DatabaseConnector databaseConnector = new DatabaseConnector(PersonContact.this);

        @Override
        protected Cursor doInBackground(Long... params) {
            databaseConnector.open();
            return databaseConnector.getOneContact(params[0]);
        }

        @Override
        protected void onPostExecute(Cursor result){
            super.onPostExecute(result);
            result.moveToFirst();

            int nameIndex = result.getColumnIndex("name");
            int surnameIndex = result.getColumnIndex("surname");
            int phoneIndex = result.getColumnIndex("phone");
            int emailIndex = result.getColumnIndex("email");

            textViewPersonName.setText(result.getString(nameIndex));
            textViewPersonSurname.setText(result.getString(surnameIndex));
            textViewPersonPhone.setText(result.getString(phoneIndex));
            textViewPersonEmail.setText(result.getString(emailIndex));

            result.close();
            databaseConnector.close();;
        }
    }
}
