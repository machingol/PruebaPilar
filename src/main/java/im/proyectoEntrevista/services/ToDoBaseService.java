package im.proyectoEntrevista.services;


import java.util.Collection;
import java.util.Map;

import org.springframework.stereotype.Component;

import im.proyectoEntrevista.entity.ToDo;


@Component
public interface ToDoBaseService {
	
	ToDo create(ToDo item) throws Exception;
	Collection<ToDo> getAll() throws Exception;
	Collection<ToDo> getByStatus(boolean isCompleted) throws Exception;
	Collection<ToDo> getByUserId(int userId) throws Exception;
	Map<Boolean,Long> getStats() throws Exception;
	Collection<String> getTitles() throws Exception;
	
	

}
