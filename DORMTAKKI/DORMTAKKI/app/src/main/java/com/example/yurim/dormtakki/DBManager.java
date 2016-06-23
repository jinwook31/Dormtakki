package com.example.yurim.dormtakki;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBManager extends SQLiteOpenHelper {

    public DBManager(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 새로운 테이블을 생성한다.
        // create table 테이블명 (컬럼명 타입 옵션);
        db.execSQL("CREATE TABLE MY_LIST( _id INTEGER PRIMARY KEY AUTOINCREMENT, name INTEGER, price INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insert(String _query) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query);
        db.close();
    }

    public void delete(String _query) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query);
        db.close();
    }

    public int now(){
        SQLiteDatabase db = getWritableDatabase();
        int num=0;
        Cursor cursor = db.rawQuery("select * from MY_LIST", null);

        while(cursor.moveToNext()){
            num=cursor.getInt(1);
        }
        return num;
    }

    public int check(){
        SQLiteDatabase db = getReadableDatabase();
        int num=0;
        Cursor cursor = db.rawQuery("select * from MY_LIST", null);

        while(cursor.moveToNext()) {
        num =  cursor.getInt(0);
        }
        return num;
    }


    public String PrintData() {
        SQLiteDatabase db = getReadableDatabase();
        String str = "";

        Cursor cursor = db.rawQuery("select * from MY_LIST", null);
        while(cursor.moveToNext()) {
            str += cursor.getInt(0)
                    + " : foodName "
                    + cursor.getInt(1)
                    + ", price = "
                    + cursor.getInt(2)
                    + "\n";
        }

        return str;
    }
}