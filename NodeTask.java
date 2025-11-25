package dataStructure;

public class NodeTask {
    int id;
    String title;
    String priority;
    boolean completed;
    NodeTask next;
    NodeTask prev;
    NodeTask stackNext; // for the stack

    // Constructor
    public NodeTask(int id, String title, String priority) {
        this.id = id;
        this.title = title;
        this.priority = priority;
        this.completed = false;
        this.next = null;
        this.prev = null;
        this.stackNext = null;
    }
}

// Data is stored in the nodeTask in the form of a Linked List.