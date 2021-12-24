package com.example.mytodoapp;

import android.provider.ContactsContract;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TodoDao {

    @Insert
    void insert(Todo todo);

    @Update
    void update (Todo todo);

    @Delete
    void delete (Todo todo);

    @Query("Delete from todo_table")
    void deleteAllTodo();

    @Query("Select * from todo_table order by priority DESC")
    LiveData<List<Todo>> getAllTodo();


}
