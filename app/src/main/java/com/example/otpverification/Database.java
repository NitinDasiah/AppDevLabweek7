package com.example.otpverification;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "userDatabase";
    private static final String DATABASE_TABLE = "users";

    //columns for table
    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_MOBILE = "mobile";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_OTP = "OTP";
    private static final String KEY_VERIFIED = "verified";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String createDb = "CREATE TABLE "+DATABASE_TABLE+" ("+
                KEY_ID+" INTEGER PRIMARY KEY,"+
                KEY_USERNAME+" TEXT,"+
                KEY_EMAIL+" TEXT,"+
                KEY_MOBILE+" TEXT,"+
                KEY_PASSWORD+" TEXT, "+
                KEY_OTP + " TEXT, "+
                KEY_VERIFIED + " INTEGER "+
                " )";
        db.execSQL(createDb);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        if(oldVersion >= newVersion)
            return;

        db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
        onCreate(db);
    }

    public long addUser(user User){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(KEY_ID, User.UID);
        v.put(KEY_USERNAME, User.username);
        v.put(KEY_EMAIL,User.email);
        v.put(KEY_MOBILE,User.mobile);
        v.put(KEY_PASSWORD, User.password);
        v.put(KEY_OTP, User.otp);
        v.put(KEY_VERIFIED, User.verified);
        long ID = db.insert(DATABASE_TABLE,null,v);
        Log.d("Inserted","ID:"+ID);
        return  ID;
    }

    public long verifyUser(user User){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(KEY_ID, User.UID);
        v.put(KEY_USERNAME, User.username);
        v.put(KEY_EMAIL,User.email);
        v.put(KEY_MOBILE,User.mobile);
        v.put(KEY_PASSWORD, User.password);
        v.put(KEY_OTP, User.otp);
        v.put(KEY_VERIFIED, User.verified);
        long ID = db.update(DATABASE_TABLE, v, "email=?", new String[]{User.verified});
        Log.d("Verified","ID:"+ID);
        return  ID;
    }

    public int checkEmail(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("Select count(*) from "+DATABASE_TABLE+" where email = '"+email+"'",null);
        c.moveToFirst();
        int count = c.getInt(0);
        return count;
    }

}
