package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class InfoCoursesLoader extends AsyncTask<Void, Void, Void> {

    public LecturesActivity activity;
    String jsonStr;
    String titleCourse;
    String fioMentors;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
      }

    @Override
    protected Void doInBackground(Void... arg0) {
        HTTPHandler sh = new HTTPHandler();
        jsonStr = sh.makeServiceCall( "http://192.168.1.10:500/courses?loginToken=" + activity.loginToken);

        if (jsonStr == null) {
            jsonStr = "";

        } else
             try {

             JSONArray jsonArray = new JSONArray(jsonStr);
            for (Integer i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                titleCourse = json.getString("title");
                fioMentors = json.getString("fio");
                Log.e("Courses" , jsonStr );
            }

        }
        catch (JSONException error) {
            Log.e("Parsing error", error.getMessage());
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
//       Назначение данных из  памяти
        if(jsonStr.equals("")){
            SharedPreferences prefs = activity.getSharedPreferences("courses", 0);
            activity.titleCourse.setText("Курс: "+ prefs.getString("title",""));
            activity.mentors.setText("Преподователь: " + prefs.getString("mentors",""));

        } else {
            //Сохранение новых данных
            SharedPreferences.Editor editor = activity.getSharedPreferences("courses", 0).edit();
            editor.putString("title", titleCourse);
            editor.putString("mentors", fioMentors);
            editor.apply();
            // Назначение данных из  памяти
            SharedPreferences prefs = activity.getSharedPreferences("courses", 0);
            activity.titleCourse.setText("Курс: "+ prefs.getString("title",""));
            activity.mentors.setText("Преподователь: " + prefs.getString("mentors",""));
          }
    }
}
