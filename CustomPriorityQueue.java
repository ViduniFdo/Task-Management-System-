package dataStructure;

public class CustomPriorityQueue {

    private Node front;
    private Node rear;

    private static class Node {
        NodeTask data;
        Node next;

        Node(NodeTask task) {
            this.data = task;
            this.next = null;
        }
    }

    public CustomPriorityQueue() {
        front = rear = null;
    }

    // Coverts priority levels into numerical values ( High=3, Medium=2, Low=1)
    private int getPriorityLevel(String priority) {
        return priority.equalsIgnoreCase("High") ? 3 :
                priority.equalsIgnoreCase("Medium") ? 2 :
                        priority.equalsIgnoreCase("Low") ? 1 : 0;
    }

    public void enqueue(NodeTask task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }

        Node newNode = new Node(task);
        int newPriority = getPriorityLevel(task.priority);

        if (front == null) {
            // Empty Queue
            front = rear = newNode;
        }
        else {
            Node current = front;
            Node prev = null;

            // Finding the insertion point
            while (current != null && getPriorityLevel(current.data.priority) >= newPriority) {
                prev = current;
                current = current.next;
            }

            if (prev == null) {
                // Insert at the head
                newNode.next = front;
                front = newNode;
            } else {
                // Insert at the middle
                prev.next = newNode;
                newNode.next = current;
            }
            // Update at the tail if inserted at the end
            if (current == null) {
                rear = newNode;
            }

        }
    }

    // Remove task with the highest priority
    public void dequeue() {
        if (isEmpty()) {
            throw new IllegalArgumentException("Queue is empty");
        }
        else {
            front = front.next;
            if (front == null) {
                rear = null;
            }
        }
    }

    // Display tasks according to priority order
    public void displayAccordingToPriority() {
        if (isEmpty()) {
            throw new IllegalArgumentException("No tasks found in the system.");
        }
        Node current = front;
        boolean hasUncompletedTasks = false;

        while (current != null) {
            NodeTask task = current.data;
            if (!task.completed) {
                System.out.println("Task ID - " +task.id + ", Task title - " + task.title + ", Task priority - " + task.priority + ", Completed: " + task.completed);
                hasUncompletedTasks = true;
            }
            current = current.next;
        }
        if (!hasUncompletedTasks) {
            System.out.println("No uncompleted tasks found in the System");
        }
    }

    public boolean isEmpty() {
        return front == null;
    }

    // Remove a specific task
    public void removeFromQueue(NodeTask task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }

        if (front == null) return;

        if (front.data.id == task.id) {
            front = front.next;
            if (front == null) rear = null;
            return;
        }

        Node prev = front;
        Node current = front.next;

        while (current != null && current.data.id != task.id) {
            prev = current;
            current = current.next;
        }
        if (current != null) {
            prev.next = current.next;
            if (current == rear) {
                rear = prev;
            }
        }
    }
}