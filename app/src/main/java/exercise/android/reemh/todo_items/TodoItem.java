package exercise.android.reemh.todo_items;

import java.io.Serializable;

public class TodoItem implements Serializable {
  // TODO: edit this class as you want
    private final String item ;
    private boolean done ;
    public  TodoItem (String description)
    {
        this.item = description ;
        this.done = false ;
    }
    public  String getItem()
    {
        return item ;
    }
    public  boolean getDone()
    {
        return done ;
    }
    public  void  editDone()
    {
        this.done = !this.done ;
    }
}
