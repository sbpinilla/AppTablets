package com.sbpinilla.apptablets;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DataBaseHelper extends SQLiteOpenHelper {


    private static String DB_PATH = "/data/data/com.sbpinilla.apptablets/databases/";
    private static String DB_NAME = "contactos.sqlite";
    private SQLiteDatabase myDataBase;
    private Context context;

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
    }


    public void createDataBase() throws IOException  {
        boolean dbExist = checkDataBase();

        SQLiteDatabase db_Read = null;

        if (dbExist) {

        } else {
            db_Read = this.getReadableDatabase();
            db_Read.close();

            try {

                copyDataBase();
            } catch (IOException E) {
                throw new Error(" Error copiando BD ");
            }

        }

    }

    public void openDataBase() throws SQLException {

        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    public synchronized void close() {
        if (myDataBase != null) {
            myDataBase.close();
        }
        super.close();
    }

    private void copyDataBase() throws IOException {

        InputStream inputStream = context.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream outputStream = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;

        while ((length = inputStream.read(buffer)) != -1) {
            if (length > 0) {
                outputStream.write(buffer, 0, length);
            }
        }
        outputStream.flush();
        inputStream.close();
        outputStream.close();
    }

    private boolean checkDataBase() {

        SQLiteDatabase chkDB = null;

        try {

            String path = DB_PATH + DB_NAME;
            chkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        } catch (Exception e) {
            File dbFile = new File(DB_PATH + DB_NAME);
            return dbFile.exists();
        }

        if (chkDB != null)
            chkDB.close();

        return chkDB != null ? true : false;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try{
            createDataBase();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public Cursor fetchAllLlist() {

        Cursor cursor = myDataBase.rawQuery("SELECT * FROM contactos", null);
        if (cursor != null)
            cursor.moveToFirst();

        return cursor;
    }

    public Cursor fetchItemList(String lista){

        Cursor cursor = myDataBase.rawQuery("SELECT * FROM "+lista,null);
        if (cursor != null)
            cursor.moveToFirst();

        return cursor;

    }

}
