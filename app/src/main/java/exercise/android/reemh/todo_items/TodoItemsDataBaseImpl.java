package exercise.android.reemh.todo_items;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// TODO: implement!
public class TodoItemsDataBaseImpl implements TodoItemsDataBase {

  public ArrayList<TodoItem> items = new ArrayList<>();

  public void get(ArrayList<TodoItem> _items)
  {
    items.clear();
    items.addAll(_items);
  }
  @Override
  public ArrayList<TodoItem> getCurrentItems() {
    List<TodoItem> done_items = new ArrayList<>();
    for(Iterator<TodoItem> i =items.iterator() ; i.hasNext();)
    {
      TodoItem x = i.next() ;
      if(x.getDone()){
        i.remove();
        done_items.add(0,x);
      }

    }
     items.addAll(done_items) ;
    return items ;
     }

  @Override
  public void addNewInProgressItem(String description) {
    items.add(0,new TodoItem(description)) ;

  }

  @Override
  public void markItemDone(TodoItem item) {
    item.editDone();
  }
  @Override
  public void deleteItem(TodoItem item) {
    if (items.isEmpty()){return;}
    items.remove(item) ;
  }

  @Override
  public void markItemInProgress(TodoItem item)
  {
     if (item.getDone() == true)
     {
       items.remove(item) ;
       items.add(item) ;
     }

  }

}
