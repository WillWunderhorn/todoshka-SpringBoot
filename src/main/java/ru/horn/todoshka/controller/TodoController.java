package ru.horn.todoshka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ru.horn.todoshka.dto.TaskDto;
import ru.horn.todoshka.entity.Task;
import ru.horn.todoshka.repo.TaskRepository;
import ru.horn.todoshka.service.ToDoServiceInt;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class TodoController {
    private TaskRepository taskRepository;
    private ToDoServiceInt todoService;

    @Autowired
    public TodoController(TaskRepository taskRepository, ToDoServiceInt todoService) {
        this.taskRepository = taskRepository;
        this.todoService = todoService;
    }

    @GetMapping("/")
    public String getMainPage(Model model) {
        List<TaskDto> tasks = todoService.getTasks();
        model.addAttribute("tasks", tasks);
        return "index";
    }

    @GetMapping("/add")
    public String getAddPage() {
        return "addPage";
    }

    @PostMapping("/add")
    public String addNewTaskFromForm(
            @RequestParam(defaultValue = "New task") String name,
            @RequestParam String description,
            @RequestParam(defaultValue = "false") boolean done
    ) {
        todoService.addNewTask(name, description, done);
        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String removeTask(@PathVariable(value = "id") long id) {
        todoService.removeTask(id);
        return "redirect:/";
    }

    @GetMapping("/{id}/edit")
    public String editTask(@PathVariable(value = "id") long id, Model model) {
        if (!taskRepository.existsById(id)) {
            return "redirect:/";
        }
        Optional<Task> task = taskRepository.findById(id);
        ArrayList<Task> tasks = new ArrayList<>();
        task.ifPresent(tasks::add);
        model.addAttribute("task", tasks);
        return "edit";
    }

    @PostMapping("/{id}/edit")
    public String updateTask(
            @PathVariable(value = "id") long id,
            @RequestParam(defaultValue = "New task") String name,
            @RequestParam String description,
            @RequestParam(defaultValue = "false") boolean done
    ) {
        todoService.editTask(id, name, description, done);
        return "redirect:/";
    }
}
