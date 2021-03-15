package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class LecturesDatabase extends SQLiteOpenHelper {

    public LecturesDatabase(Context context) {
        //Подключаемся к базе данных
        super(context, "Ilmhona.Courses", null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Cоздаём таблицу
        db.execSQL("CREATE TABLE lectures (title text, published text, photo text, video text, shownotes text)");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Уничтожаем таблицу
        db.execSQL("DROP TABLE IF EXISTS lectures");
        //Создаём заново
        onCreate(db);
    }

    public void wipeLectures() {
        //Подключаем переменную с БД
        SQLiteDatabase db = this.getWritableDatabase();
        //Отправляем SQL для удаления данных
        db.execSQL("DELETE FROM lectures");
    }

    public void insertLecture(String title, String published, String photo, String video, String shownotes) {
        //Подключаем переменную с БД
        SQLiteDatabase db = this.getWritableDatabase();
        //Отправляем SQL для добавления данных
        db.execSQL("INSERT INTO lectures VALUES('" + title + "', '" + published + "', '" + photo + "', '" + video + "', '" + shownotes + "')");
    }

    public ArrayList<Lecture> getLectures() {
        ArrayList<Lecture> lecturesArray = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM lectures", null);
        result.moveToFirst();
        Integer i = 0;
        while (i < result.getCount()) {
            Lecture lecture = new Lecture();
            lecture.title = result.getString(0);
            lecture.published = result.getString(1);
            lecture.photo = result.getString(2);
            lecture.video = result.getString(3);
            lecture.shownotes = result.getString(4);
            lecturesArray.add(lecture);
            i = i + 1;
            result.moveToNext();
        }
        return lecturesArray;
    }

    public Lecture getLecture(Integer index) {
        Lecture lecture = new Lecture();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM lectures LIMIT " + index.toString() + ", 1", null);
        result.moveToFirst();
        Integer i = 0;

        lecture.title = result.getString(0);
        lecture.published = result.getString(1);
        lecture.photo = result.getString(2);
        lecture.video = result.getString(3);
        lecture.shownotes = result.getString(4);

        return lecture;
    }
}
