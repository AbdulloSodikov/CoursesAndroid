package com.example.myapplication;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LecturesLoader extends AsyncTask<Void, Void, Void> {

    public LecturesActivity activity;
    String jsonStrLectures;
    String jsonStrCourses;

    public LecturesDatabase db;
    public ArrayList<Lecture> lectures;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        activity.loadDataInDB.setVisibility(View.VISIBLE);
        activity.lectures = new ArrayList<>();
    }

    @Override
    protected Void doInBackground(Void... arg0) {
        HTTPHandler sh = new HTTPHandler();
        jsonStrLectures = sh.makeServiceCall( "http://192.168.1.10:500/lectures?loginToken=" + activity.loginToken);
        if (jsonStrLectures == null) {
        jsonStrLectures = "";
        }

        try {
            JSONArray jsonArray = new JSONArray(jsonStrLectures);
            activity.db.wipeLectures();

            for (Integer i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                Lecture lecture = new Lecture();
                lecture.title = json.getString("title");
                lecture.published = json.getString("published");
                lecture.photo = json.getString("photo");
                lecture.video = json.getString("video");
                lecture.shownotes = json.getString("shownotes");
                activity.db.insertLecture(lecture.title, lecture.published, lecture.photo, lecture.video, lecture.shownotes);
                activity.lectures.add(lecture);
                //Log.e("Lecture " , jsonStrCourses);
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

        if (jsonStrLectures.equals("")) {
            activity.lectures = activity.db.getLectures();
        }

        LecturesAdapter adapter = new LecturesAdapter();
        adapter.activity = activity;
        activity.list.setAdapter(adapter);
        activity.loadDataInDB.setVisibility(View.INVISIBLE);
    }
}
