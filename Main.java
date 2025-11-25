package dataStructure;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        LinkedList list = new LinkedList();
        Scanner scanner = new Scanner(System.in);

        // Options for User
        while (true) {
            System.out.println("~~~ Welcome to Task Management System! ~~~\nPlease Enter an option: ");
            System.out.println("1. Add Task");
            System.out.println("2. Remove Task");
            System.out.println("3. Undo last action");
            System.out.println("4. Search Task by ID");
            System.out.println("5. Search Task by Title");
            System.out.println("6. Mark task as Complete");
            System.out.println("7. Display tasks according to the priority");
            System.out.println("8. Display completed tasks");
            System.out.println("9. Display all Tasks");
            System.out.println("10. Exit Task Management System");
            System.out.print("Enter your choice: ");

            try {
                if (!scanner.hasNextInt()) {
                    throw new IllegalArgumentException("Invalid choice! Please input a number between 1 and 10");
                }


                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("Enter Task Title: ");
                        String title = (scanner.nextLine());
                        System.out.print("Enter Task priority (High, Medium, Low): ");
                        String priority = scanner.nextLine().trim();
                        if (!priority.equalsIgnoreCase("High") &&
                                !priority.equalsIgnoreCase("Medium")
                                && !priority.equalsIgnoreCase("Low")) {
                            throw new IllegalArgumentException("Invalid priority! Please enter High, Medium or Low.");
                        }
                        list.insert(title, priority);
                        System.out.println("Task added successfully!");
                        break;

                    case 2:
                        System.out.print("Enter Task ID to remove Task: ");

                        if (!scanner.hasNextInt()) {
                          throw new IllegalArgumentException("Invalid choice! Please enter a valid Task ID");
                        }
                        int removeId = scanner.nextInt();
                        if (list.remove(removeId)) {
                            System.out.println("Task removed successfully! Task ID:" + removeId);
                        } else {
                            System.out.println("Task not found!");
                        }
                        break;

                    case 3:
                        if (list.undoLastAction()) {
                            System.out.println("Undo successful!");
                        } else {
                            System.out.println("No task to undo!");
                        }
                        break;

                    case 4:
                        System.out.print("Enter Task ID to search Task: ");
                        if (!scanner.hasNextInt()) {
                            throw new IllegalArgumentException("Invalid choice! Please enter a valid Task ID");
                        }
                        int searchId = scanner.nextInt();
                        scanner.nextLine();
                        NodeTask foundTask = list.searchById(searchId);
                        if (foundTask != null) {
                            System.out.println("Task found! Task ID: " + foundTask.id + ", Title: " + foundTask.title + ", Priority: " + foundTask.priority + ", Task Completed: " + foundTask.completed);
                        } else {
                            System.out.println("Task not found!");
                        }
                        break;

                    case 5:
                        System.out.print("Enter Task Title to search Task: ");
                        String searchTitle = scanner.nextLine();
                        NodeTask foundTitle = list.searchByTitle(searchTitle);
                        if (foundTitle != null) {
                            System.out.println("Task found! Task ID: " + foundTitle.id + ", Title: " + foundTitle.title + ", Priority: " + foundTitle.priority + ", Task Completed: " + foundTitle.completed);
                        } else {
                            System.out.println("Task not found!");
                        }
                        break;

                    case 6:
                        System.out.print("Enter Task ID to mark task as Completed: ");
                        if (!scanner.hasNextInt()) {
                            throw new IllegalArgumentException ("Invalid choice! Please enter a valid Task ID");
                        }
                        int markAsComplete = scanner.nextInt();
                        boolean correct = list.markAsComplete(markAsComplete);
                        if (correct) {
                            NodeTask foundPriority = list.searchById(markAsComplete);
                            if (foundPriority != null) {
                                System.out.println("Task marked as completed! Task ID: " + foundPriority.id + ", Title: " + foundPriority.title + ",  Priority: " + foundPriority.priority + ", Task Completed: " + foundPriority.completed);
                            } else {
                                System.out.println("Task not found!");
                            }
                        } else {
                            System.out.println("Task not found or already completed!");
                        }
                        break;

                    case 7:
                        System.out.println("Tasks sorted by priority: ");
                        list.displayAccordingToPriority();
                        break;

                    case 8:
                        list.displayCompletedTasks();
                        break;


                    case 9:
                        System.out.println("All tasks displayed successfully!");
                        list.display();
                        break;

                    case 10:
                        System.out.println("Exiting Task Management System...");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Invalid option! Please enter a value between 1 and 10");
                }
            }
            catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                scanner.nextLine();
            }
            catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
                scanner.nextLine();
            }
        }
    }
}