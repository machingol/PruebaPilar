package im.proyectoEntrevista.utils;

import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import im.proyectoEntrevista.entity.ToDo;

public class UtilsOdilo {
	
	
	@Value("{im.proyectoEntrevista.val.url}")
	static String url;
	
	
	
	public static boolean deDondeLeer(String perfil) {
		/*
		 * Si es true  -> leer H2
		 * Si es false -> leer de externo
		 */
		
		//Hola Jorge
		//Como estas
		//segunda rama
		//Comentario master desde la web
		
		boolean leerH2 = false;
		
		if (perfil != null) {
			if ("H2".equals(perfil)) {
				leerH2 = true;
			}
		}else {		
		}
		
		return leerH2;
	}
	
	public static List<ToDo>  consultaApiExterna(){
		
		if (url == null) {
			url = "https://jsonplaceholder.typicode.com/todos";
		}
		RestTemplate restTemplate = new RestTemplate();
	    HttpHeaders headers = new HttpHeaders();
	    HttpEntity<String> entity = new HttpEntity<String>("body", headers);
	   
	    ResponseEntity<String> responseEntity = restTemplate.exchange(url.toString(), HttpMethod.GET, entity, String.class);

		ObjectMapper mapper = new ObjectMapper();
		try {
			List<ToDo> listaPrueba = (List<ToDo>) mapper.readValue(responseEntity.getBody(), List.class);
		
			return listaPrueba;
		
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
		
	}
	


}
