package data_structure_project_;

import java.util.Scanner;

public class OrderProcessingSystem {

    static class Order {
        String name;
        String priority; // "normal" or "urgent"
        Order next;

        Order(String name, String priority) {
            this.name = name;
            this.priority = priority;
            this.next = null;
        }
    }

    static Order head = null;
    static Order historyHead = null;

    public static void addOrder(String name, String priority) {
        Order newOrder = new Order(name, priority);
        if (head == null || priority.equals("urgent")) {
            if (head == null || !head.priority.equals("urgent")) {
                newOrder.next = head;
                head = newOrder;
            } else {
                Order current = head;
                while (current.next != null && current.next.priority.equals("urgent")) {
                    current = current.next;
                }
                newOrder.next = current.next;
                current.next = newOrder;
            }
        } else {
            Order current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newOrder;
        }
        System.out.println("Order added: " + name + " (" + priority + ")");
    }

    public static void fulfillOrder() {
        if (head == null) {
            System.out.println("No orders to fulfill.");
            return;
        }
        Order fulfilled = head;
        head = head.next;
        fulfilled.next = historyHead;
        historyHead = fulfilled;
        System.out.println("Order fulfilled: " + fulfilled.name);
    }

    public static void cancelOrder(String name) {
        if (head == null) {
            System.out.println("No orders to cancel.");
            return;
        }
        Order current = head, prev = null;
        while (current != null && !current.name.equals(name)) {
            prev = current;
            current = current.next;
        }
        if (current == null) {
            System.out.println("Order not found.");
        } else {
            if (prev == null) {
                head = current.next;
            } else {
                prev.next = current.next;
            }
            current.next = historyHead;
            historyHead = current;
            System.out.println("Order canceled: " + current.name);
        }
    }

    public static void displayOrders() {
        if (head == null) {
            System.out.println("No current orders.");
            return;
        }
        System.out.println("Current Orders:");
        Order current = head;
        while (current != null) {
            System.out.println("- " + current.name + " (" + current.priority + ")");
            current = current.next;
        }
    }

    public static void displayHistory() {
        if (historyHead == null) {
            System.out.println("No history.");
            return;
        }
        System.out.println("Order History:");
        Order current = historyHead;
        while (current != null) {
            System.out.println("- " + current.name + " (" + current.priority + ")");
            current = current.next;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n1. Add Order\n2. Fulfill Order\n3. Cancel Order\n4. Display Orders\n5. Display History\n6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.print("Enter order name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter priority (normal/urgent): ");
                    String priority = scanner.nextLine().toLowerCase();
                    if (!priority.equals("normal") && !priority.equals("urgent")) {
                        priority = "normal";
                    }
                    addOrder(name, priority);
                    break;
                case 2:
                    fulfillOrder();
                    break;
                case 3:
                    System.out.print("Enter order name to cancel: ");
                    String cancelName = scanner.nextLine();
                    cancelOrder(cancelName);
                    break;
                case 4:
                    displayOrders();
                    break;
                case 5:
                    displayHistory();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
