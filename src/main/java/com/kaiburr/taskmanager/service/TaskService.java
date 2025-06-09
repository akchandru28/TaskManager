package com.kaiburr.taskmanager.service;

import com.kaiburr.taskmanager.model.Task;
import com.kaiburr.taskmanager.model.TaskExecution;
import com.kaiburr.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repository;

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public Task getTaskById(String id) {
        return repository.findById(id).orElse(null);
    }

    public Task createOrUpdateTask(Task task) throws Exception {
        if (isUnsafe(task.getCommand())) {
            throw new Exception("Unsafe command detected!");
        }
        return repository.save(task);
    }

    public void deleteTask(String id) {
        repository.deleteById(id);
    }

    public List<Task> searchByName(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

    public Task executeTask(String id) throws java.io.IOException {
    Optional<Task> optional = repository.findById(id);
    if (optional.isEmpty()) return null;

    Task task = optional.get();

    TaskExecution exec = new TaskExecution();
    exec.setStartTime(new Date());

    StringBuilder output = new StringBuilder();
    try {
        
        ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", task.getCommand());
        builder.redirectErrorStream(true);

        Process process = builder.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }

        int exitCode = process.waitFor();
        output.append("\nExited with code: ").append(exitCode);
    } catch (Exception e) {
        output.append("Error during execution: ").append(e.getMessage());
    }

    exec.setEndTime(new Date());
    exec.setOutput(output.toString());

    task.getTaskExecutions().add(exec);
    return repository.save(task);
}


    private boolean isUnsafe(String command) {
       
        String[] blackList = {"rm", "shutdown", "reboot", "mkfs", "dd", ":(){", "fork"};
        for (String keyword : blackList) {
            if (command.contains(keyword)) return true;
        }
        return false;
    }
}
