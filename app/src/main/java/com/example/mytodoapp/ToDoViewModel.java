package com.example.mytodoapp;

import android.app.Application;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class ToDoViewModel extends AndroidViewModel {

    private TodoRepository repository;
    private LiveData<List<Todo>> allTodos;

    public ToDoViewModel(@NonNull @NotNull Application application) {
        super(application);
        repository = new TodoRepository(application);
        allTodos = repository.getAllTodo();
    }

    public void insert (Todo todo){
        repository.insert(todo);
    }
    public void update (Todo todo){
        repository.update(todo);
    }
    public void delete (Todo todo){
        repository.delete(todo);
    }
    public void deleteAllTodos (){
        repository.deleteAllTodo();
    }
    public LiveData<List<Todo>> getAllTodos(){
        return allTodos;

    }
}
