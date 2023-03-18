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
import im.proyectoEntrevista.utils.UtilsOdilo;

@Service
public class ToDoServiceApiExtern implements ToDoBaseService{


//	//Ver con que perfil se ha iniciado la aplicacion
	@Value("${im.proyectoEntrevista.val.url}")
	String url;
	
	List<ToDo> listaEntrada = UtilsOdilo.consultaApiExterna();



	
	@Override
	public ToDo create(ToDo item) throws Exception{
	
		//No crear en api externa
		return null;
	
	}



	@Override
	public Collection<ToDo> getAll() throws Exception {
		// TODO Auto-generated method stub
		
		if (listaEntrada != null) {
			return listaEntrada;
		}else {
			//por si queremos tratar el null de alguna otra forma
			throw new Exception("NUUUULL");

		}

	}

	@Override
	public Collection<ToDo> getByStatus(boolean isCompleted) throws Exception {
		// TODO Auto-generated method stub
		
		List<ToDo> collecSalida = new ArrayList<ToDo>();
		if (listaEntrada != null && !listaEntrada.isEmpty()) {
			for (int idx = 0; idx < listaEntrada.size(); idx++) {
				if (listaEntrada.get(idx).getCompleted() == isCompleted) {
					collecSalida.add(listaEntrada.get(idx));
				}
			}
			return collecSalida;
			//ojo porque esto tb puede ser null.. mirar
		}else {
			throw new Exception("NUUUULL");

		}

	}

	@Override
	public Collection<ToDo> getByUserId(int userId) throws Exception {
		// TODO Auto-generated method stub
		
		List<ToDo> collecSalida = new ArrayList<ToDo>();
		if (listaEntrada != null && !listaEntrada.isEmpty()) {
			for (int idx = 0; idx < listaEntrada.size(); idx++) {
				if (listaEntrada.get(idx).getUserId() == userId) {
					collecSalida.add(listaEntrada.get(idx));
				}
			}
			return collecSalida;
			//ojo porque esto tb puede ser null.. mirar
		}else {
			throw new Exception("NUUUULL");

		}
	}

	@Override
	public Map<Boolean, Long> getStats() throws Exception {
		// TODO Auto-generated method stub
			
			Map<Boolean, Long> mapStats = new HashMap<Boolean, Long>();
			Long completed = 0L;
			Long uncompleted = 0L;
			
			if (listaEntrada != null && !listaEntrada.isEmpty()) {
				for (int idx= 0; idx<listaEntrada.size(); idx++) {
					if (listaEntrada.get(idx).getCompleted()) {
						completed++;
					}else {
						uncompleted++;
					}
				}
				mapStats.put(false, uncompleted);
				mapStats.put(true, completed);
				
				return mapStats;
			}else {
				throw new Exception("NUUUULL");

			}
			

			

	}

	@Override
	public Collection<String> getTitles() throws Exception {
		// TODO Auto-generated method stub
		
			List<String> salidaColl = new ArrayList<String>();

			if (listaEntrada != null && !listaEntrada.isEmpty()) {
				for (int idx = 0; idx<listaEntrada.size(); idx++) {
					salidaColl.add(listaEntrada.get(idx).getTitle());
				}
			
			
			
			Comparator compareLength = new Comparator<String>()
			    {
			        public int compare(String s1, String s2) {
			            return Integer.compare(s1.length(), s2.length());
			        }
			    };
		    Collections.sort(salidaColl, compareLength);
			
		    return salidaColl;
			}else {
				throw new Exception("NUUUULL");

			}

	}


	

}
