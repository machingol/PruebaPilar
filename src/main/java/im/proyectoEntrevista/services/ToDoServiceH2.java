package im.proyectoEntrevista.services;



import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import im.proyectoEntrevista.entity.ToDo;
import im.proyectoEntrevista.repository.ToDoRepository;

@Service
public class ToDoServiceH2 implements ToDoBaseService{

	@Autowired
	ToDoRepository toDoRepository;
	
//	//Ver con que perfil se ha iniciado la aplicacion
	@Value("${spring.profiles.active}")
	String perfil;
	


	public boolean delete(Long toDoId) {
		
		try {
			toDoRepository.deleteById(toDoId);
			return true;
			
		}catch(Exception e) {
			return false;
		}

	}

	@Override
	public ToDo create(ToDo item) throws Exception{
		// TODO Auto-generated method stub
		try {
			ToDo itemSalvado = toDoRepository.save(item);		
			return itemSalvado;
			
		}catch(Exception e) {
			throw new Exception(e.getMessage());
		}

	}

	@Override
	public Collection<ToDo> getAll() throws Exception {
		// TODO Auto-generated method stub
			
		try {
			List<ToDo> listaToDo = toDoRepository.findAll();			
			return listaToDo;
			
		}catch(Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public Collection<ToDo> getByStatus(boolean isCompleted) throws Exception {
		// TODO Auto-generated method stub
		try {
			Collection<ToDo> collStatus = toDoRepository.findByCompleted(isCompleted);			
			
			return collStatus;
			
		}catch(Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public Collection<ToDo> getByUserId(int userId) throws Exception {
		// TODO Auto-generated method stub
		try {
			Collection<ToDo> collUserId = toDoRepository.findByUserId(userId);
			
			return collUserId;
			
		}catch(Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public Map<Boolean, Long> getStats() throws Exception {
		// TODO Auto-generated method stub
		try {
			List<ToDo> listaToDo  = toDoRepository.findAll();
			
			Map<Boolean, Long> mapStats = new HashMap<Boolean, Long>();
			Long completed = 0L;
			Long uncompleted = 0L;
			
			if (listaToDo != null && !listaToDo.isEmpty()) {
				for (int idx= 0; idx<listaToDo.size(); idx++) {
					if (listaToDo.get(idx).getCompleted()) {
						completed++;
					}else {
						uncompleted++;
					}
				}
			}
			
			mapStats.put(false, uncompleted);
			mapStats.put(true, completed);
			
			return mapStats;
			
		}catch(Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public Collection<String> getTitles() throws Exception {
		// TODO Auto-generated method stub
		try {
			List<ToDo> listaToDo = toDoRepository.findAll();	
			List<String> salidaColl = new ArrayList<String>();

			if (listaToDo != null && !listaToDo.isEmpty()) {
				for (int idx = 0; idx<listaToDo.size(); idx++) {
					salidaColl.add(listaToDo.get(idx).getTitle());
				}
			}
			
			
		 Comparator compareLength = new Comparator<String>()
		    {
		        public int compare(String s1, String s2) {
		            return Integer.compare(s1.length(), s2.length());
		        }
		    };
		    Collections.sort(salidaColl, compareLength);
			
		    return salidaColl;
			
		}catch(Exception e) {
			throw new Exception(e.getMessage());
		}
	}


}
