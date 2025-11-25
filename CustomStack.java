// works in a LIFO manner, stacks store and tracks actions of the tasks.

package dataStructure;

public class CustomStack {

    private NodeTask head;

    public CustomStack() {
        this.head = null;
    }

    // Custom exception for stack errors
    static class StackEmptyException extends RuntimeException {
        public StackEmptyException(String message) {
            super(message);
        }
    }

    // Push
    public void push(NodeTask task) {
        task.stackNext = head;
        head = task;
    }

    // Pop
    public NodeTask pop() throws StackEmptyException {
        if (isEmpty()) {
            throw new StackEmptyException("Stack is empty! Nothing to undo!");
        }
        NodeTask poppedTask = head;
        head = head.stackNext;
        poppedTask.stackNext = null;
        return poppedTask;
    }

    public boolean isEmpty() {
        return head == null;
    }
}