package com.doranco.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHandler extends SQLiteOpenHelper {

    private static final String TAG = DbHandler.class.getSimpleName();
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "userdb";
    private static final String TABLE_USERS = "userdetails";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_LOC = "location";
    private static final String KEY_DESG = "designation";

    // constructor
    public DbHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " TEXT,"
                + KEY_LOC + " TEXT,"
                + KEY_DESG + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // delete old table
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        // create the table again
        onCreate(sqLiteDatabase);
    }

    // *** CRUD (Create, read, Update, Delete)

    //Ajout d'un nouveau user
    public void insertUserDetails(String name, String location, String designation){
        // Récupèrer le repository en écriture
        SQLiteDatabase db  = this.getWritableDatabase();

        // Insérer les données en utilisant une Map(ContentValues)
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_NAME, name);
        cValues.put(KEY_LOC, location);
        cValues.put(KEY_DESG, designation);

        // Insérer les données
        long newRowId = db.insert(TABLE_USERS, null, cValues);
        Log.d(TAG, "New user added with primary key " + newRowId);
        db.close();

    }
}
