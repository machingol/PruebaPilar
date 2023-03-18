package im.proyectoEntrevista.repository;


import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import im.proyectoEntrevista.entity.ToDo;


@Repository
public interface ToDoRepository extends JpaRepository<ToDo, Long> {

	Collection<ToDo> findByUserId(Integer user_Id);
	Collection<ToDo> findByCompleted(Boolean status);


}
