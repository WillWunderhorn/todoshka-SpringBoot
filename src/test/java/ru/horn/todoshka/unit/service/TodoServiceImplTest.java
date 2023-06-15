package ru.horn.todoshka.unit.service;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.horn.todoshka.dto.TaskDto;
import ru.horn.todoshka.entity.Task;
import ru.horn.todoshka.repo.TaskRepository;
import ru.horn.todoshka.service.impl.TodoServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringJUnitConfig
@SpringBootTest
class TodoServiceImplTest {

    @Captor
    private ArgumentCaptor<Task> taskArgumentCaptor;

    @MockBean
    private TaskRepository taskRepository;

    @Autowired
    private TodoServiceImpl todoService;

    private Task task1 = new Task(1L, "Name1", "Desc1", false);
    private Task task2 = new Task(2L, "Name2", "Desc2", true);

    @Test
    void getTasks() {
        List<Task> mockedTasks = new ArrayList<>();
        mockedTasks.add(task1);

        when(taskRepository.findAll()).thenReturn(mockedTasks);

        List<TaskDto> expectedTasks = mockedTasks.stream()
                .map(task -> new TaskDto(task.getId(), task.getName(), task.getDescription(), task.getDone()))
                .collect(Collectors.toList());

        List<TaskDto> actualTasks = todoService.getTasks();

        assertEquals(expectedTasks.toString(), actualTasks.toString());
    }

    @Test
    void addNewTask() {
        String name = "addName";
        String description = "addDescription";
        Boolean done = true;

        todoService.addNewTask(name, description, done);

        verify(taskRepository).save(taskArgumentCaptor.capture());

        Task capturedTask = taskArgumentCaptor.getValue();
        assertEquals(name, capturedTask.getName());
        assertEquals(description, capturedTask.getDescription());
        assertEquals(done, capturedTask.getDone());
    }

    @Test
    void removeTaskExists() {
        Long id = 1L;
        when(taskRepository.existsById(id)).thenReturn(true);
        todoService.removeTask(id);

        verify(taskRepository, times(1)).existsById(id);
        verify(taskRepository, times(1)).deleteById(id);
    }

    @Test
    void removeTaskNotExists() {
        Long id = 1L;

        when(taskRepository.existsById(id)).thenReturn(false);

        assertThrows(IllegalStateException.class, () -> {
            todoService.removeTask(id);
        });

        verify(taskRepository, times(1)).existsById(id);
        verify(taskRepository, never()).deleteById(id);
    }

    @Test
    void editTask() {
        String newName = "newName";
        String newDesc = "newDesc";
        Boolean newDone = true;

        List<Task> mockedTasks = new ArrayList<>();
        mockedTasks.add(task1);


        when(taskRepository.findAll()).thenReturn(mockedTasks);

        task1.setName(newName);
        task1.setDescription(newDesc);
        task1.setDone(newDone);

        List<TaskDto> expectedTasks = mockedTasks.stream()
                .map(task -> new TaskDto(task.getId(), task.getName(), task.getDescription(), task.getDone()))
                .collect(Collectors.toList());

        List<TaskDto> actualTasks = todoService.getTasks();

        assertEquals(expectedTasks.toString(), actualTasks.toString());
    }
}