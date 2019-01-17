package org.pursuit.dogbreeds.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.pursuit.dogbreeds.model.DogImage;

import java.util.ArrayList;
import java.util.List;

public class DogDatabase extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "Dogs";
    private static final String DATABASE_NAME = "dogs.db";
    private static final int SCHEMA_VERSION = 1;
    private static DogDatabase dogDatabaseInstance;

    //this method is getting the one instance of the DogDatabase
    public  static synchronized  DogDatabase getInstance(Context context){
        if(dogDatabaseInstance == null){
            dogDatabaseInstance = new DogDatabase(context.getApplicationContext());
        }
        return dogDatabaseInstance;
    }

    public DogDatabase(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE " + TABLE_NAME +
                        " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "breed_name TEXT, breed_url TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //No-operation
    }

    //all key words (such as SELECT, FROM, WHERE) are wrapped in double quotes ("") with one space on both sides of the word
    //says SELECT breed_url FROM TABLE NAME WHERE the breed name equals the breed you pass in from the method when you call it
    //this method is returning the first breed in the row
    public DogImage getDogImage(String breed) {
        DogImage dogImage = null;
        Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT breed_url" + " FROM " + TABLE_NAME + " WHERE  breed_name " + "= '" + breed + "' ;", null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                dogImage = new DogImage(
                        cursor.getString(cursor.getColumnIndex("breed_name")),
                        cursor.getString(cursor.getColumnIndex("breed_name")));
            }
        }
        return dogImage;
    }

    //selet all from dog where the breed_name is equal to the breedName and the breed_url is equal to the breedUrl
    public void addDogImage( String breedName, String breedUrl){
        Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT * FROM " + TABLE_NAME + " WHERE breed_name = '" + breedName +
                        "' AND breed_url = '" + breedUrl + "';", null);

        if (cursor.getCount() == 0) {
            getWritableDatabase().execSQL("INSERT INTO " + TABLE_NAME +
                    "(breed_name, breed_url) VALUES('" +
                    breedName + "', '" +
                    breedUrl + "');");
        }
        cursor.close();
    }

    //gets all the rows, converts them to objects and returns the Object back
    public List<DogImage> getDogImageList(){
        List<DogImage> dogImageList = new ArrayList<>();
        DogImage dogImage = null;
        Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT * FROM " + TABLE_NAME + ";", null);
        if(cursor != null) {
            if(cursor.moveToFirst()) {
                do {
                    dogImage = new DogImage(
                            cursor.getString(cursor.getColumnIndex("breed_name")),
                            cursor.getString(cursor.getColumnIndex("breed_url")));
                    dogImageList.add(dogImage);
                } while (cursor.moveToNext());
            }
        }
        return dogImageList;
    }
}
