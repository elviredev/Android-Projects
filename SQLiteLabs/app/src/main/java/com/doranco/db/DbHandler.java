package com.doranco.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

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

    // *** CRUD (Create, Read, Update, Delete)

    // (CREATE) Ajout d'un nouveau user
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

    // (READ) récupérer les utilisateurs
    public ArrayList<HashMap<String, String>> getUsers(){
        // Récupérer le repository en lecture
        SQLiteDatabase db = this.getReadableDatabase();
        // Créer liste des users
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        // Requête
        String query = "SELECT name, location, designation FROM " + TABLE_USERS;
        // Créer curseur et itérer sur les users
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            // Récupérer user
            HashMap<String, String> user = new HashMap<>();
            // Récupérer le nom, location et designation de l'user
            user.put(KEY_NAME, cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            user.put(KEY_LOC, cursor.getString(cursor.getColumnIndex(KEY_LOC)));
            user.put(KEY_DESG, cursor.getString(cursor.getColumnIndex(KEY_DESG)));

            userList.add(user);
        }
        return userList;
    }

    // Récupérer un seul utilisateur à partir de son ID
    public ArrayList<HashMap<String, String>> getUserByUserId (int userId){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<HashMap<String, String>> user = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_USERS;
        Cursor cursor = db.query(TABLE_USERS, new String[]{KEY_NAME, KEY_LOC, KEY_DESG}, KEY_ID + "=?",
                new String[]{String.valueOf(userId)}, null, null, null, null);
        if(cursor.moveToNext()) {
            HashMap<String, String> result = new HashMap<>();
            result.put(KEY_NAME, cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            result.put(KEY_LOC, cursor.getString(cursor.getColumnIndex(KEY_LOC)));
            result.put(KEY_DESG, cursor.getString(cursor.getColumnIndex(KEY_DESG)));

            user.add(result);
        }
        return user;
    }











}
