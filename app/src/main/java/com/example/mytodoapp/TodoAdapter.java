package com.example.mytodoapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoHolder> {
    private List<Todo> todos = new ArrayList<>();

    @NonNull
    @NotNull
    @Override
    public TodoHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_item,parent,false);
        return new TodoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TodoHolder holder, int position) {
        Todo currentTodo = todos.get(position);
        holder.textViewTitle.setText(currentTodo.getTitle());
        holder.textViewDescription.setText(currentTodo.getDescription());
        holder.textViewProirity.setText(String.valueOf(currentTodo.getPriority()));

    }
    //Display item in recycler view
    @Override
    public int getItemCount() {
        return todos.size();
    }
    public void setTodos(List<Todo> todos){
        this.todos = todos;
        notifyDataSetChanged();

    }

    class TodoHolder extends RecyclerView.ViewHolder{
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewProirity;

        public TodoHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewProirity = itemView.findViewById(R.id.text_view_priority);
        }
    }
}
