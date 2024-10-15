package miniproject.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "notes";
    public static String TABLE_NAME = "note";
    public static String COL_ID = "id";
    public static String COL_TIME = "dateAndTime";
    public static String COL_TITLE = "title";
    public static String COL_BODY = "body";
    public static String CREATE_QUERY = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT)", TABLE_NAME, COL_ID, COL_TIME, COL_TITLE, COL_BODY);

    //CREATE TABLE TABLE_NAME (
    //    COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
    //    COL_TIME TEXT,
    //    COL_TITLE TEXT,
    //    COL_BODY TEXT
    //);

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long insertNote(NoteModel note){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TIME, note.getTime());
        values.put(COL_BODY, note.getBody());
        values.put(COL_TITLE, note.getTitle());
        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result;
    }

    public long updateNote(NoteModel note){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TIME, note.getTime());
        values.put(COL_BODY, note.getBody());
        values.put(COL_TITLE, note.getTitle());
        long result = db.update(TABLE_NAME, values, COL_ID+"= ?", new String[]{String.valueOf(note.getId())});
        db.close();
        return result;
    }

    public long deleteNote(int id){
        SQLiteDatabase db = getWritableDatabase();
        long result = db.delete(TABLE_NAME, COL_ID+"=?", new String[]{String.valueOf(id)});
        db.close();
        return result;
    }

    public ArrayList<NoteModel> getAllNotes(){
        ArrayList<NoteModel> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor != null) {
            // Move to the first row of the result
            if (cursor.moveToFirst()) {
                do {
                    // Extract data from the cursor and create a NoteModel object
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
                    String time = cursor.getString(cursor.getColumnIndexOrThrow(COL_TIME));
                    String title = cursor.getString(cursor.getColumnIndexOrThrow(COL_TITLE));
                    String body = cursor.getString(cursor.getColumnIndexOrThrow(COL_BODY));
                    // Add the new NoteModel object to the list
                    list.add(new NoteModel(id, time, title, body));
                } while (cursor.moveToNext()); // Move to the next row
            }
            // Close the cursor to release resources
            cursor.close();
        }
        db.close();
        return list;
    }
}
