package taskmanager.managers;

import taskmanager.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    static class Node<T> {

        public T data;
        public Node<T> next;
        public Node<T> prev;

        public Node(Node<T> prev, T data, Node<T> next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }

    private static Node<Task> head;
    private static Node<Task> tail;
    private int size = 0;

    private final Map<Integer, Node<Task>> hashHistory = new HashMap<>();

    @Override
    public List<Task> getHistory() {
        List<Task> history = new ArrayList<>();
        if (head == null) return null;
        Node<Task> node = head;
        history.add(node.data);
        while (node.next != null) {
            node = node.next;
            history.add(node.data);
        }
        return history;
    }

    @Override
    public <T extends Task> void add(T task) {
        if (hashHistory.containsKey(task.getTaskId())) {
            remove(task.getTaskId());
        }
        linkLast(task);
        hashHistory.put(task.getTaskId(), tail);
    }

    @Override
    public boolean remove(int id) {
        if (hashHistory.get(id) == null) {
            System.out.println("Задача не сохранена в истории.");
            return false;
        }
        removeNode(hashHistory.get(id));
        hashHistory.remove(id);
        return true;

    }

    public int size() {
        return this.size;
    }

    public void clear() {
        head = null;
        tail = null;
        size = 0;
        hashHistory.clear();
    }

    public void linkLast(Task task) {
        Node<Task> oldTail = tail;
        Node<Task> newNode = new Node<>(oldTail, task, null);

        tail = newNode;
        if (oldTail == null) {
            head = newNode;
        } else {
            oldTail.next = newNode;
        }
        size++;
    }

    private void removeNode(Node<Task> node) {
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }
        size--;
    }
}
