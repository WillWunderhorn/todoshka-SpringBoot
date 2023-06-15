package ru.horn.todoshka.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.horn.todoshka.dto.TaskDto;
import ru.horn.todoshka.entity.Task;
import ru.horn.todoshka.repo.TaskRepository;
import ru.horn.todoshka.service.ToDoServiceInt;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TodoServiceImpl implements ToDoServiceInt {
    private TaskRepository taskRepository;

    @Autowired
    public TodoServiceImpl(TaskRepository repo) {
        this.taskRepository = repo;
    }

    @Override
    public List<TaskDto> getTasks() {
        List<Task> tasks = new ArrayList<>();
        taskRepository.findAll().forEach(tasks::add);
        return tasks.stream()
                .map(task -> new TaskDto(
                        task.getId(),
                        task.getName(),
                        task.getDescription(),
                        task.getDone()
                )).collect(Collectors.toList());
    }

    @Override
    public void addNewTask(String name, String description, Boolean done) {
        Task task = new Task(name, description, done);
        taskRepository.save(task);
    }

    public void removeTask(Long id) {
        Boolean exists = taskRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("Element does not exists");
        }

        taskRepository.deleteById(id);
    }

    @Override
    public void editTask(Long id, String name, String description, Boolean done) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Task with id " + id + " not found!"));

        if (name != null && name.length() > 0 && !Objects.equals(task.getName(), name)) {
            task.setName(name);
            task.setDescription(description);
            task.setDone(done);

            taskRepository.save(task);

        }
    }
}
