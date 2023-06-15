package ru.horn.todoshka.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.horn.todoshka.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

}
