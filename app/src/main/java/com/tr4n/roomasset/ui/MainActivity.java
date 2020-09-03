package com.tr4n.roomasset.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.tr4n.roomasset.R;
import com.tr4n.roomasset.data.base.BaseAsyncTask;
import com.tr4n.roomasset.data.base.ExecuteAsync;
import com.tr4n.roomasset.data.db.AppDatabase;
import com.tr4n.roomasset.data.db.StoryDao;
import com.tr4n.roomasset.data.model.Story;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
    }

    private void initData(){
        AppDatabase database = AppDatabase.getInstance(this);
        final StoryDao storyDao = database.storyDao();
       new BaseAsyncTask<Void, List<Story>>()
               .execute(s -> storyDao.getStories())
               .onSuccess(stories -> {
                   Log.d(TAG, "onExecute: " + stories);
                   return null;
               })
               .onFailure(e -> {
                   Log.e(TAG, "onExecute: ", e);
                   return null;
               })
               .execute();
        ExecuteAsync.OnDataLoadedListener<List<Story>> callback = new ExecuteAsync.OnDataLoadedListener<List<Story>>() {
            @Override
            public void onSuccess(List<Story> data) {
                Log.d(TAG, "onExecute: " + data);
            }

            @Override
            public void onFailure(Exception e) {
                Log.e(TAG, "onExecute: ", e);
            }
        };
       new ExecuteAsync<Integer, List<Story>>()
               .setOnDataLoadedListener(callback)
               .onExecute(new ExecuteAsync.OnExecuteListener<Integer, Story>() {
                   @Override
                   public Story onExecute(Integer integer) {
                       return storyDao.getStoryById(integer);
                   }
               })
               .execute(2);
    }
}
