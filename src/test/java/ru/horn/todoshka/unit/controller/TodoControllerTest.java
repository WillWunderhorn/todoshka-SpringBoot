package ru.horn.todoshka.unit.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.horn.todoshka.controller.TodoController;
import ru.horn.todoshka.dto.TaskDto;
import ru.horn.todoshka.repo.TaskRepository;
import ru.horn.todoshka.service.ToDoServiceInt;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(TodoControllerTest.class)
@ExtendWith(MockitoExtension.class)
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ToDoServiceInt toDoService;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TodoController todoController;

    @Test
    void getMainPage() throws Exception {
        List<TaskDto> tasks = new ArrayList<>();
        tasks.add(new TaskDto(1L, "Name1", "Desc1", false));
        tasks.add(new TaskDto(2L, "Name2", "Desc2", true));

        when(toDoService.getTasks()).thenReturn(tasks);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
//                .andExpect(model().attributeExists("tasks"))
//                .andExpect(model().attribute("tasks", tasks));
    }

//    @Test
//    void getAddPage() throws Exception {
//        mockMvc.perform(get("/add"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("addPage"));
//    }
//
//    @Test
//    void addNewTaskFromForm() {
//    }
//
//    @Test
//    void removeTask() {
//    }
//
//    @Test
//    void editTask() {
//    }
//
//    @Test
//    void updateTask() {
//    }
}