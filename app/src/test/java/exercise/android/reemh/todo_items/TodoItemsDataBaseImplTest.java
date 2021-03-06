package exercise.android.reemh.todo_items;

import junit.framework.TestCase;

import org.junit.Test;

public class TodoItemsDataBaseImplTest extends TestCase {
  @Test
  public void when_addingTodoItem_then_callingListShouldHaveThisItem(){
    // setup
    TodoItemsDataBaseImpl dbUnderTest = new TodoItemsDataBaseImpl();
    assertEquals(0, dbUnderTest.getCurrentItems().size());

    // test
    dbUnderTest.addNewInProgressItem("do shopping");

    // verify
    assertEquals(1, dbUnderTest.getCurrentItems().size());
  }

  // TODO: add at least 10 more tests to verify correct behavior of your implementation of `TodoItemsDataBaseImpl` class
}