package example.murager.com.contactbookdb.activities.classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Murager on 12.02.2015.
 */
public class DatabaseConnector {

    public static final String DATABASE_NAME = "UserContacts";
    public static final String TABLE_NAME = "contacts";

    private SQLiteDatabase database;

    private DatabaseOpenHelper databaseOpenHelper;


    public DatabaseConnector(Context context){
        databaseOpenHelper = new DatabaseOpenHelper(context, DATABASE_NAME, null, 1);
    }




    public void insertContact(String name, String surname, String phone, String email){
        ContentValues newContact = new ContentValues();
        newContact.put("name", name);
        newContact.put("surname", surname);
        newContact.put("phone", phone);
        newContact.put("email", email);

        open();
        database.insert(TABLE_NAME, null, newContact);
        close();
    }

    public void updateContact(long id, String name, String surname, String phone, String email){
        ContentValues editContact = new ContentValues();
        editContact.put("name", name);
        editContact.put("surname", surname);
        editContact.put("phone", phone);
        editContact.put("email", email);

        open();
        database.update(TABLE_NAME, editContact, "_id=" + id, null);
        close();
    }


    public Cursor getOneContact(long id){
        return database.query(TABLE_NAME, null, "_id=" + id,
                              null, null, null, null);
    }

    public void deleteContact(long id){
        open();
        database.delete(TABLE_NAME, "_id=" + id, null);
        close();
    }

    public Cursor getAllContact(){
        return database.query(TABLE_NAME, new String[]{"_id", "name", "surname"},
                              null, null, null, null, "name");
    }


    public void deleteAllContact(){
        open();
        database.delete(TABLE_NAME, null, null);
        close();
    }


    public void open(){
        database = databaseOpenHelper.getWritableDatabase();
    }

    public void close(){
        if (database != null){
            database.close();
        }
    }


    private class DatabaseOpenHelper extends SQLiteOpenHelper{

        public DatabaseOpenHelper(Context context, String name,
                                  SQLiteDatabase.CursorFactory factory,
                                  int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String createQuery = "CREATE TABLE contacts" +
                                 "(_id integer primary key autoincrement," +
                                 "name TEXT, surname TEXT, phone TEXT," +
                                 "email TEXT);";

            db.execSQL(createQuery);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
