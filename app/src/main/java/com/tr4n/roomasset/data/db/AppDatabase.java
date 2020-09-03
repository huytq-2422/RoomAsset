package com.tr4n.roomasset.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.tr4n.roomasset.data.model.Story;

@Database(entities = {Story.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase{

    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if(instance == null){
            instance = Room.databaseBuilder(context, AppDatabase.class, "short_story.db")
                    .createFromAsset("short_story.db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract StoryDao storyDao();
}
