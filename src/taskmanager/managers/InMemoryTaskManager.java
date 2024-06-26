package taskmanager.managers;

import taskmanager.exceptions.ErrorSavingTasksException;
import taskmanager.exceptions.TaskCrossingTimeException;
import taskmanager.tasks.Epic;
import taskmanager.tasks.Subtask;
import taskmanager.tasks.Task;

import java.time.Duration;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    static Map<Integer, Task> tasks = new HashMap<>();
    static Map<Integer, Epic> epics = new HashMap<>();
    static Map<Integer, Subtask> subtasks = new HashMap<>();

    public static TreeSet<Task> treeTasks = new TreeSet<>();

    public static HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public List<Subtask> getSubtasksInEpic(int epicId) {
        return subtasks.values().stream()
                .filter(subtask -> subtask.getEpicId() == epicId)
                .toList();
    }

    @Override
    public void deleteTasks() {
        tasks.clear();
    }

    @Override
    public void deleteEpics() {
        epics.clear();
    }

    @Override
    public void deleteSubtasks() {
        subtasks.clear();
    }

    @Override
    public void deleteTasksForID(int taskId) {
        System.out.println("Удалена задача: " + tasks.get(taskId));

        treeTasks.remove(tasks.get(taskId));
        tasks.remove(taskId);
        historyManager.remove(taskId);
    }

    @Override
    public void deleteSubTasksForID(int taskId) {
        int epicId = getSubTaskForId(taskId).getEpicId();
        Duration subDuration = subtasks.get(taskId).getDuration();
        System.out.println("Удалена задача: " + subtasks.get(taskId));

        Epic epic = epics.get(epicId);
        epic.checkStatusSubtasks(subtasks);
        epic.durationMinusSubtaskDuration(subDuration);
        epic.setLowStartTimeSubtaskForEpic(getSubtaskForEpic(epicId));

        treeTasks.remove(subtasks.get(taskId));
        subtasks.remove(taskId);
        historyManager.remove(taskId);
    }

    @Override
    public void deleteEpicForID(int epicId) {
        if (!epics.isEmpty() && epics.containsKey(epicId)) {
            System.out.println("Удалена задача: " + epics.get(epicId));

            treeTasks.remove(epics.get(epicId));
            epics.remove(epicId);
            historyManager.remove(epicId);

            List<Subtask> subtaskList = getSubtaskForEpic(epicId);
            subtaskList.forEach(subtask -> subtasks.remove(subtask.getTaskId()));

        }
        System.out.println("Связанные подзадачи удалены.");
    }

    @Override
    public void setTasks(int taskId, Task task) {
        checkingTaskIsNull(task);
        if (saveTaskInTreeMap(task)) {
            if (tasks.containsKey(taskId)) {
                tasks.put(taskId, task);
                System.out.println("Задача изменена.");
            } else {
                tasks.put(taskId, task);
                System.out.println("Задача заведена.");
            }
        }
    }

    @Override
    public void setSubtasks(int subtaskId, Subtask subtask) {
        checkingTaskIsNull(subtask);
        if (saveTaskInTreeMap(subtask)) {
            if (subtasks.containsKey(subtaskId)) {
                subtasks.put(subtaskId, subtask);
                System.out.println("Задача изменена.");
            } else {
                subtasks.put(subtaskId, subtask);
                System.out.println("Задача заведена.");
            }
            Epic epic = epics.get(subtask.getEpicId());
            epic.checkStatusSubtasks(subtasks);
            epic.durationPlusSubtaskDuration(subtask.getDuration());
            epic.updateStartTimeTask(subtask.getStartTime());
        }
    }

    @Override
    public void setEpics(int taskId, Epic epic) {
        checkingTaskIsNull(epic);
        if (saveTaskInTreeMap(epic)) {
            if (epics.containsKey(taskId)) {
                epics.put(taskId, epic);
                System.out.println("Задача изменена.");
            } else {
                epics.put(taskId, epic);
                System.out.println("Задача заведена.");
            }
        }
    }

    void checkingTaskIsNull(Task task) {
        if (task == null) {
            throw new ErrorSavingTasksException("Передано null.");
        }
    }

    @Override
    public Task getTasksForId(int taskId) {
        Task copy = Task.copyTask(tasks.get(taskId));
        historyManager.add(tasks.get(taskId));
        return copy;
    }

    @Override
    public Subtask getSubTaskForId(int taskId) {
        Subtask copy = Subtask.copySubTask(subtasks.get(taskId));
        historyManager.add(subtasks.get(taskId));
        return copy;
    }

    @Override
    public Epic getEpicForId(int taskId) {
        Epic copy = Epic.copyEpic(epics.get(taskId));
        historyManager.add(epics.get(taskId));
        for (Subtask subtask : subtasks.values()) {
            if (subtask.getEpicId() == taskId) {
                historyManager.add(subtask);
            }
        }
        return copy;
    }

    public List<Subtask> getSubtaskForEpic(int epicId) {
        return subtasks.values().stream()
                .filter(subtask -> subtask.getEpicId() == epicId)
                .sorted(Task::compareTo)
                .toList();
    }

    @Override
    public Map<Integer, Task> getTasks() {
        return tasks;
    }

    @Override
    public Map<Integer, Epic> getEpics() {
        return epics;
    }

    @Override
    public Map<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    @Override
    public TreeSet<Task> getPrioritizedTasks() {
        return treeTasks;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public void deleteHistory() {
        historyManager.clear();
    }

    @Override
    public void deletePrioritizedList() {
        treeTasks.clear();
    }

    static boolean saveTaskInTreeMap(Task task) {
        if (task == null) {
            throw new ErrorSavingTasksException("Передана пустая задача.");
        }

        treeTasks.add(task);
        Task prev = treeTasks.lower(task);
        Task next = treeTasks.higher(task);

        if (isCrossingTimeTask(prev, task) || isCrossingTimeTask(task, next)) {
            treeTasks.remove(task);
            throw new TaskCrossingTimeException("Задача пересекается по времени с другими.");
        }
        return true;
    }

    static boolean isCrossingTimeTask(Task firstTask, Task secondTask) {
        if (firstTask == null || secondTask == null) {
            return false;
        }
        if (firstTask.getEndTime().equals(secondTask.getStartTime())) {
            return false;
        }
        return firstTask.getEndTime().isAfter(secondTask.getStartTime());
    }
}
