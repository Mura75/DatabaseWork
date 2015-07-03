package example.murager.com.contactbookdb.activities.activies;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;

import example.murager.com.contactbookdb.R;
import example.murager.com.contactbookdb.activities.classes.DatabaseConnector;

public class AddContact extends ActionBarActivity {

    private long rowID;

    EditText editTextName;
    EditText editTextSurname;
    EditText editTextPhone;
    EditText editTextEmail;

    Button buttonCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        editTextName = (EditText)findViewById(R.id.editTextName);
        editTextSurname = (EditText)findViewById(R.id.editTextSurname);
        editTextPhone = (EditText)findViewById(R.id.editTextPhone);
        editTextEmail = (EditText)findViewById(R.id.editTextEmail);

        buttonCreate = (Button)findViewById(R.id.buttonAddContact);


        if (getIntent().getExtras() != null){
            rowID = getIntent().getExtras().getLong("row_id");
            editTextName.setText(getIntent().getExtras().getString("name"));
            editTextSurname.setText(getIntent().getExtras().getString("surname"));
            editTextPhone.setText(getIntent().getExtras().getString("phone"));
            editTextEmail.setText(getIntent().getExtras().getString("email"));
        }


        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextName.getText().length() != 0){
                    AsyncTask<Objects, Objects, Objects> saveContactTask = new
                            AsyncTask<Objects, Objects, Objects>() {
                                @Override
                                protected Objects doInBackground(Objects... params) {
                                    saveContact();
                                    return null;
                                }
                                @Override
                                protected void onPostExecute(Objects objects) {
                                    finish();
                                }
                            };
                        saveContactTask.execute((Objects[]) null);
                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(AddContact.this);
                    alert.setTitle("Error");
                    alert.setMessage("No Person name");
                    alert.setPositiveButton("OK", null);
                    alert.show();
                }
            }
        });



    }


    protected void saveContact(){
        DatabaseConnector databaseConnector = new DatabaseConnector(AddContact.this);

        if (getIntent().getExtras() == null){
            databaseConnector.insertContact(editTextName.getText().toString(),
                                            editTextSurname.getText().toString(),
                                            editTextPhone.getText().toString(),
                                            editTextEmail.getText().toString());
        } else {
            databaseConnector.updateContact(rowID,
                                            editTextName.getText().toString(),
                                            editTextSurname.getText().toString(),
                                            editTextPhone.getText().toString(),
                                            editTextEmail.getText().toString());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_contact, menu);
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
