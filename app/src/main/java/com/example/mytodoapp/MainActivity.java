package com.example.mytodoapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_NOTE_REQUEST =1;
    public static final int EDIT_NOTE_REQUEST =2;
    private ToDoViewModel toDoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddTodo = findViewById(R.id.button_add_note);
        buttonAddTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(MainActivity.this, AddEditTodoActivity.class);
               startActivityForResult(intent,ADD_NOTE_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

       final TodoAdapter adapter = new TodoAdapter();
        recyclerView.setAdapter(adapter);

        toDoViewModel = ViewModelProviders.of(this).get(ToDoViewModel.class);
        toDoViewModel.getAllTodos().observe(this, new Observer<List<Todo>>() {
            @Override
            public void onChanged(List<Todo> todos) {
                //Update Recycler View
                adapter.setTodos(todos);
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                toDoViewModel.delete(adapter.getTodoAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Task Deleted Sucessfully", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new TodoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Todo todo) {
                Intent intent = new Intent(MainActivity.this, AddEditTodoActivity.class);
                intent.putExtra(AddEditTodoActivity.EXTRA_ID,todo.getId());
                intent.putExtra(AddEditTodoActivity.EXTRA_TITLE, todo.getTitle());
                intent.putExtra(AddEditTodoActivity.EXTRA_DESCRIPTION, todo.getDescription());
                intent.putExtra(AddEditTodoActivity.EXTRA_PRIORITY, todo.getPriority());
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK){
            String title = data.getStringExtra(AddEditTodoActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditTodoActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditTodoActivity.EXTRA_PRIORITY,1);

            Todo todo = new Todo(title, description, priority);
            toDoViewModel.insert(todo);
            Toast.makeText(this, "To do Added", Toast.LENGTH_SHORT).show();


        } else if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK){
            int id = data.getIntExtra(AddEditTodoActivity.EXTRA_ID,-1);

            if (id == -1){

                Toast.makeText(this, "Todo Can't be Updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String title = data.getStringExtra(AddEditTodoActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditTodoActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditTodoActivity.EXTRA_PRIORITY,1);

            Todo todo = new Todo(title, description,priority);
            todo.setId(id);
            toDoViewModel.update(todo);

            Toast.makeText(this, "Todo Updated", Toast.LENGTH_SHORT).show();

        }else{

            Toast.makeText(this, "Todo Not Saved", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater =  getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_all_todos:
                toDoViewModel.deleteAllTodos();
                Toast.makeText(this, "All todo deleted", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}