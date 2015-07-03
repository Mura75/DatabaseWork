package example.murager.com.contactbookdb.activities.adapters;

import android.content.Context;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;

/**
 * Created by Murager on 12.02.2015.
 */
public class DatabaseAdapter extends SimpleCursorAdapter{
    public DatabaseAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
        super(context, layout, c, from, to);
    }
}
