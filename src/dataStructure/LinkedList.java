package dataStructure;

public class LinkedList {
    private NodeTask head;
    private NodeTask tail;

    //Auto implementing the Task ID
    private int assignID = 1;

    // Stack for undo operation
    CustomStack actionStack = new CustomStack();

    // Queue to manage tasks to display them according to their priority.
    CustomPriorityQueue taskCustomPriorityQueue = new CustomPriorityQueue();

    // Insert a task into the linked list
    public void insert(String title, String priority) {
        NodeTask task = new NodeTask(assignID++,title, priority);

        if (head == null) {
            head = tail = task;
        } else {
            tail.next = task;
            task.prev = tail;
            tail = task;
        }
        tail.next = null;

        // Push action to stack(last-performed action) so it's easy to undo the action.
        actionStack.push(task);

        // Add the task for the queue for the priority based sorting
        taskCustomPriorityQueue.enqueue(task);
    }

    // Remove task by ID
    public boolean remove(int id) {
        NodeTask current = head;

        while (current != null) {
            if (id == current.id) {

                // Task removed from the LinkedList
                actionStack.push(current);

                // Task removed from the queue
                taskCustomPriorityQueue.removeFromQueue(current);

                if (current == head) {
                    head = current.next;
                    if (head != null)
                        head.prev = null;
                } else if (current == tail) {
                    tail = current.prev;
                    if (tail != null) {
                        tail.next = null;
                    } else {
                        head = null;
                    }
                } else {
                    if (current.prev != null) {
                        current.prev.next = current.next;
                    }
                    if (current.next != null) {
                        current.next.prev = current.prev;
                    }
                }
                return true;
            }
            current = current.next;
        }
        return false;
    }

    // Mark task as complete
    public boolean markAsComplete(int id) {
        NodeTask task = searchById(id);
        if (task != null && !task.completed) {
            task.completed = true;
            actionStack.push(task);
            taskCustomPriorityQueue.removeFromQueue(task);
            return true;
        }
        return false;
    }

    // Undo last action ( Undo add, undo remove, undo mark as complete)
    public boolean undoLastAction() {
        if (actionStack.isEmpty()) {
            System.out.println("No actions to undo!");
            return false;
        }
        NodeTask lastTask = actionStack.pop();

        if (lastTask == null) {
            System.out.println("No task to undo!");
            return false;
        }

        if (lastTask.completed) {
            lastTask.completed = false;
        }
        else if (searchById(lastTask.id) != null) {
            remove(lastTask.id);
        }
        else {
            restoreTask(lastTask);
        }
        return true;
    }

    // restore a deleted task
    private void restoreTask(NodeTask task) {
        if (head == null) {
            // If the list is empty, restore as the first node
            head = tail = task;
            task.next = null;
            task.prev = null;
        } else {
            // if task was at the head
            if (task.id < head.id) {
                task.next = head;
                head.prev = task;
                head = task;
                head.prev = null;
            }
            // if task was at the tail
            else  if (task.id > tail.id) {
                tail.next = task;
                task.prev = tail;
                tail = task;
                task.next = null;
            }
            // if task was in the middle
            else {
                NodeTask current = head;
                while (current != null && current.id < task.id) {
                    current = current.next;
                }
                if (current != null) {
                    task.prev = current.prev;
                    task.next = current;

                    if (current.prev != null) {
                       current.prev.next = task;
                    }
                    current.prev = task;
                }
            }
        }
        taskCustomPriorityQueue.enqueue(task);
    }

    // Search task by ID
    public NodeTask searchById(int id) {
        NodeTask current = head;
        while (current != null) {
            if (id == current.id) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    // Search task by title
    public NodeTask searchByTitle(String title) {
        NodeTask current = head;
        while (current != null) {
            if (title.equalsIgnoreCase(current.title)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    // Display tasks
    public void display() {
        NodeTask task = head;
        boolean hasUncompleted = false;

        if (task == null) {
            System.out.println("No Tasks in the Task Management System");
            return;
        }
        while(task!= null) {
            if (!task.completed) {
                System.out.println("Task ID - " + task.id + ", Task title - " + task.title + ", Task priority - " + task.priority + ", Completed: " + task.completed);
                hasUncompleted = true;
            }
            task = task.next;
        }
        if (!hasUncompleted) {
            System.out.println("No uncompleted tasks in the Task Management System");
        }
    }

    // Displaying tasks according to their priority level
    public void displayAccordingToPriority() {
        taskCustomPriorityQueue.displayAccordingToPriority();
    }

    // Display tasks according to their completion status
    public void displayCompletedTasks   () {
        if (head == null) {
            System.out.println("No tasks found in the system!");
            return;
        }

        boolean HasCompletedTasks = false;
        NodeTask current = head;

        System.out.println("Completed task list: ");
        while (current != null) {
            if (current.completed) {
                System.out.println("Task ID - " + current.id + ", Task title - " + current.title + ", Task priority - " + current.priority + ", Completed: " + current.completed);
                HasCompletedTasks = true;
            }
            current = current.next;
        }

        if (!HasCompletedTasks) {
            System.out.println("No tasks found in the system!");
        }
    }
}