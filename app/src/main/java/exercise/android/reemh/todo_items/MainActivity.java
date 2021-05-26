package exercise.android.reemh.todo_items;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public TodoItemsDataBase dataBase = null;
    EditText editText ;
    FloatingActionButton fb ;
    RecyclerView recyclerView ;
    ToDoItemAddapter itemAddapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (dataBase == null) {
            dataBase = new TodoItemsDataBaseImpl();
        }
         editText = findViewById(R.id.editTextInsertTask);
         fb = findViewById(R.id.buttonCreateTodoItem);
         recyclerView = findViewById(R.id.recyclerTodoItemsList);
         itemAddapter=new ToDoItemAddapter();
        recyclerView.setAdapter(itemAddapter);

        fb.setOnClickListener(v ->
        {
            if (!editText.getText().toString().equals("")) {
                if(itemAddapter.get_items() !=null) {
                dataBase.get(itemAddapter.get_items());}
                dataBase.addNewInProgressItem(editText.getText().toString());
                itemAddapter.set_items((ArrayList<TodoItem>) dataBase.getCurrentItems());
                editText.setText("");
            }

        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        // TODO: implement the specs as defined below
        //    (find all UI components, hook them up, connect everything you need)

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("dataBase",dataBase.getCurrentItems());

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        itemAddapter.set_items((ArrayList<TodoItem>) savedInstanceState.getSerializable("dataBase"));

    }
}

class ToDoItemHolder extends RecyclerView.ViewHolder {
    TextView textView;
    CheckBox checkBox;
   ImageView trash  ;
    public ToDoItemHolder(View view) {
        super(view);
        textView = view.findViewById(R.id.textView1);
        checkBox = view.findViewById(R.id.checkBox2);
        trash = view.findViewById(R.id.trash1);
    }
}

class ToDoItemAddapter extends RecyclerView.Adapter<ToDoItemHolder> {
    ArrayList<TodoItem> _items = new ArrayList<>();
   TodoItemsDataBaseImpl a = new TodoItemsDataBaseImpl() ;

    public void set_items(ArrayList<TodoItem> items) {
        _items.clear();
        _items.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ToDoItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.row_todo_item, parent, false);
        return new ToDoItemHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ToDoItemHolder holder, int position) {

        TodoItem item = _items.get(position);
        holder.textView.setText(item.getItem());
        holder.checkBox.setChecked(item.getDone());
        holder.checkBox.setOnClickListener(v->{
            if(holder.checkBox.isChecked())
            {
                _items.get(position).editDone();
                a.get(_items);
                set_items(a.getCurrentItems());
                holder.textView.setPaintFlags(holder.textView.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
                return;
            }
            _items.get(position).editDone();
            a.get(_items);
            set_items(a.getCurrentItems());
            holder.textView.setPaintFlags(holder.textView.getPaintFlags()& (~Paint.STRIKE_THRU_TEXT_FLAG));

        });
        holder.trash.setOnClickListener(v->{
           _items.remove(item);
            a.get(_items);
            this.set_items(a.getCurrentItems());

        });


    }
    public ArrayList<TodoItem> get_items()
    {
        return _items ;
    }
    @Override
    public int getItemCount() {
        return _items.size();
    }
}


/*

SPECS:

- the screen starts out empty (no items shown, edit-text input should be empty)
- every time the user taps the "add TODO item" button:
    * if the edit-text is empty (no input), nothing happens
    * if there is input:
        - a new TodoItem (checkbox not checked) will be created and added to the items list
        - the new TodoItem will be shown as the first item in the Recycler view
        - the edit-text input will be erased
- the "TodoItems" list is shown in the screen
  * every operation that creates/edits/deletes a TodoItem should immediately be shown in the UI
  * the order of the TodoItems in the UI is:
    - all IN-PROGRESS items are shown first. items are sorted by creation time,
      where the last-created item is the first item in the list
    - all DONE items are shown afterwards, no particular sort is needed (but try to think about what's the best UX for the user)
  * every item shows a checkbox and a description. you can decide to show other data as well (creation time, etc)
  * DONE items should show the checkbox as checked, and the description with a strike-through text
  * IN-PROGRESS items should show the checkbox as not checked, and the description text normal
  * upon click on the checkbox, flip the TodoItem's state (if was DONE will be IN-PROGRESS, and vice versa)
  * add a functionality to remove a TodoItem. either by a button, long-click or any other UX as you want
- when a screen rotation happens (user flips the screen):
  * the UI should still show the same list of TodoItems
  * the edit-text should store the same user-input (don't erase input upon screen change)

Remarks:
- you should use the `dataBase` field of the activity
- you will need to create a class extending from RecyclerView.Adapter and use it in this activity
- notice that you have the "row_todo_item.xml" file and you can use it in the adapter
- you should add tests to make sure your activity works as expected. take a look at file `MainActivityTest.java`



(optional, for advanced students:
- save the TodoItems list to file, so the list will still be in the same state even when app is killed and re-launched
)

*/
