package com.madhavanmalolan.bucketkeyvaluedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by madhavan on 3/21/18.
 */

public class KeyValDbHelper {
    public static class KeyVal{
        long dbId;
        String key;
        String val;
        public KeyVal(Cursor cursor){
            dbId = cursor.getLong(cursor.getColumnIndex(KeyValEntry._ID));
            key = cursor.getString(cursor.getColumnIndex(KeyValEntry.COLUMN_NAME_KEY));
            val = cursor.getString(cursor.getColumnIndex(KeyValEntry.COLUMN_NAME_VAL));
        }
    }

    Context mContext;

    public KeyValDbHelper(Context context){
        mContext = context;
    }

    class KeyValEntry implements BaseColumns {
        public final static String TABLE_NAME = "table_keyval";
        public final static String COLUMN_NAME_BUCKET = "bucket";
        public final static String COLUMN_NAME_KEY = "keyval_key";
        public final static String COLUMN_NAME_VAL = "keyval_val";
    }

    public static String getCreateTableSQL(){
        return String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT)",
                KeyValEntry.TABLE_NAME,
                KeyValEntry._ID,
                KeyValEntry.COLUMN_NAME_BUCKET,
                KeyValEntry.COLUMN_NAME_KEY,
                KeyValEntry.COLUMN_NAME_VAL
                );
    }

    public static String getDropTableSQL(){
        return String.format("DROP TABLE IF EXISITS %s", KeyValEntry.TABLE_NAME);
    }

    String[] projection = {
            KeyValEntry._ID,
            KeyValEntry.COLUMN_NAME_BUCKET,
            KeyValEntry.COLUMN_NAME_KEY,
            KeyValEntry.COLUMN_NAME_VAL
    };

    public static class KeyValDb extends SQLiteOpenHelper {

        static String DB_NAME = "com.madhavanmalolan.keyval.db";
        static int DB_VERSION = 1;

        public KeyValDb(Context context){
            super(context, DB_NAME, null, DB_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(getCreateTableSQL());
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL(getDropTableSQL());
            sqLiteDatabase.execSQL(getCreateTableSQL());
        }
    }

    public void deleteBucket(String bucket){
        SQLiteDatabase db = (new KeyValDb(mContext)).getWritableDatabase();
        String selection = KeyValEntry.COLUMN_NAME_BUCKET + " = \""+bucket+"\"";
        db.delete(KeyValEntry.TABLE_NAME, selection, null);
        db.close();
    }

    public void deleteKey(String bucket, String key){
        SQLiteDatabase db = (new KeyValDb(mContext)).getWritableDatabase();
        String selection = KeyValEntry.COLUMN_NAME_BUCKET + " = \""+bucket+"\" AND "+ KeyValEntry.COLUMN_NAME_KEY+" = \""+key+"\"";
        db.delete(KeyValEntry.TABLE_NAME, selection, null);
        db.close();
    }

    public void put(String bucket, String key){
        put(bucket, key, "");
    }

    public void put(String bucket, String key, String val){
        KeyVal keyVal = get(bucket, key);
        SQLiteDatabase db = (new KeyValDb(mContext)).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KeyValEntry.COLUMN_NAME_BUCKET, bucket);
        contentValues.put(KeyValEntry.COLUMN_NAME_KEY, key);
        contentValues.put(KeyValEntry.COLUMN_NAME_VAL, val);
        if(keyVal != null){
            String selection = KeyValEntry.COLUMN_NAME_BUCKET + " = \""+bucket+"\" AND "+ KeyValEntry.COLUMN_NAME_KEY+" = \""+key+"\"";
            db.update(KeyValEntry.TABLE_NAME,contentValues,selection,null);
        }
        else{
            db.insert(KeyValEntry.TABLE_NAME,null, contentValues);
        }
        db.close();
    }

    public KeyVal get(String bucket, String key){
        String selection = KeyValEntry.COLUMN_NAME_BUCKET + " = \""+bucket+"\" AND "+ KeyValEntry.COLUMN_NAME_KEY+" = \""+key+"\"";
        SQLiteDatabase db = (new KeyValDb(mContext)).getReadableDatabase();
        Cursor cursor = db.query(KeyValEntry.TABLE_NAME,projection,selection,null, null, null, null);
        while (cursor.moveToNext()){
            KeyVal keyVal = new KeyVal(cursor);
            return keyVal;
        }
        cursor.close();
        db.close();


        return null;
    }

    public List<String> getKeys(String bucket){
        List<String> keys = new ArrayList<>();
        String selection = KeyValEntry.COLUMN_NAME_BUCKET+" = \""+bucket+"\"";
        SQLiteDatabase db = (new KeyValDb(mContext)).getReadableDatabase();
        Cursor cursor = db.query(KeyValEntry.TABLE_NAME, projection, selection, null, null , null, null );
        while (cursor.moveToNext()){
            keys.add(new KeyVal(cursor).key);
        }
        cursor.close();
        db.close();

        return keys;
    }







}
