package com.tr4n.roomasset.data.db;

import androidx.room.Dao;
import androidx.room.Query;

import com.tr4n.roomasset.data.model.Story;

import java.util.List;

@Dao
public interface StoryDao {
    @Query("SELECT * FROM tbl_short_story")
    List<Story> getStories();

    @Query("SELECT * FROM tbl_short_story WHERE id = :id")
    Story getStoryById(int id);
}
