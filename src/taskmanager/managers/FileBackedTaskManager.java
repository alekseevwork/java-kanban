package taskmanager.managers;

import taskmanager.exceptions.ManagerSaveException;
import taskmanager.tasks.Epic;
import taskmanager.tasks.StatusTask;
import taskmanager.tasks.Subtask;
import taskmanager.tasks.Task;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class FileBackedTaskManager extends InMemoryTaskManager {

    private final Path taskFile;

    public FileBackedTaskManager(Path file) {
        this.taskFile = file;
    }

    public static FileBackedTaskManager loadFromFile(Path file) {
        FileBackedTaskManager manager = new FileBackedTaskManager(file);

        try (BufferedReader br = new BufferedReader(new FileReader(file.toFile(), StandardCharsets.UTF_8))) {
            br.readLine();
            while (br.ready()) {
                String line = br.readLine();
                saveTaskFromString(line);
            }
        } catch (IOException e) {
            throw new ManagerSaveException(e.getMessage(), (IOException) e.getCause());
        }
        return manager;
    }

    void save() {
        try (Writer fileWriter = new FileWriter(String.valueOf(taskFile))) {
            fileWriter.write("type,id,title,description,statusTask,epic\n");
            for (Task task : tasks.values()) {
                fileWriter.write(task.toStringToFile());
            }
            for (Epic epic : epics.values()) {
                fileWriter.write(epic.toStringToFile());
            }
            for (Subtask subtask : subtasks.values()) {
                fileWriter.write(subtask.toStringToFile());
            }
        } catch (IOException e) {
            throw new ManagerSaveException(e.getMessage(), (IOException) e.getCause());
        }
    }

    static void saveTaskFromString(String value) {
        String[] args = value.split(",");
        switch (args[0]) {
            case "TASK" -> {
                Task task = new Task(args[2], args[3], StatusTask.valueOf(args[4]));
                tasks.put(task.getTaskId(), task);
            }
            case "EPIC" -> {
                Epic epic = new Epic(args[2], args[3]);
                epics.put(epic.getTaskId(), epic);
            }
            case "SUBTASK" -> {
                int epicId = Integer.parseInt(args[5].strip());
                Subtask subtask = new Subtask(args[2], args[3], StatusTask.valueOf(args[4]), epicId);
                subtasks.put(subtask.getTaskId(), subtask);
            }
        }
    }

    @Override
    public void deleteTypeTasks(Object object) {
        super.deleteTypeTasks(object);
        save();
    }

    @Override
    public void deleteTasksForID(int taskId) {
        super.deleteTasksForID(taskId);
        save();
    }

    @Override
    public void deleteSubTasksForID(int taskId) {
        super.deleteSubTasksForID(taskId);
        save();
    }

    @Override
    public void deleteEpicForID(int taskId) {
        super.deleteEpicForID(taskId);
        save();
    }

    @Override
    public void setTasks(int taskId, Task task) {
        super.setTasks(taskId, task);
        save();
    }

    @Override
    public void setSubtasks(int subtaskId, Subtask task) {
        super.setSubtasks(subtaskId, task);
        save();
    }

    @Override
    public void setEpics(int taskId, Epic epic) {
        super.setEpics(taskId, epic);
        save();
    }
}
