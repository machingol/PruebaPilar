package im.proyectoEntrevista.controllers;


import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.swing.text.html.HTML;
import javax.xml.soap.Text;
import javax.xml.ws.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import im.proyectoEntrevista.config.H2Config;
import im.proyectoEntrevista.entity.ToDo;
import im.proyectoEntrevista.services.ToDoServiceApiExtern;
import im.proyectoEntrevista.services.ToDoServiceH2;
import im.proyectoEntrevista.utils.UtilsOdilo;

@RestController
@RequestMapping("/odilo/tests")



public class ToDoController {
	
	@Autowired
	ToDoServiceH2 toDoService;
	
	@Autowired
	ToDoServiceApiExtern toDoServiceExtern;
	
	@Autowired
	H2Config h2con;

	
//	//Ver con que perfil se ha iniciado la aplicacion
	@Value("${spring.profiles.active}")
	String perfil;


	/*
	 * Realimos la distribucion de controladores segun tipo necesario
	 * 
	 * 	TIPO 1 - GET
	 * 	TIPO 2 - POST
	 *  TIPO 3 - PUT
	 *  TIPO 4 - DELETE
	 * 
	 */


//TIPO 1 - GET
	@GetMapping("/1")
	public ResponseEntity<List<String>>  findJobsExe() {
		
		List<String> listThreads = new ArrayList<String>();

		try {
			for (int idx = 0; idx < Thread.activeCount(); idx++) {
				listThreads.add(Thread.getAllStackTraces().get(idx).toString());
			}
			
			return new ResponseEntity<>(listThreads,HttpStatus.OK);

		}catch (Exception e) {
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);

		}


	}
	
	@GetMapping("/2")
	public ResponseEntity<Collection<ToDo>>  getToDo(@RequestParam(value = "findOnlyCompleted", required = false) String busquedaCompletados) {

		
		try {
		
			Collection ToDo = null;
			
			if (busquedaCompletados != null && "S".equals(busquedaCompletados)) {
				
			}else {
				if (UtilsOdilo.deDondeLeer(perfil)) {
					ToDo = 	toDoService.getAll();

				}else {
					ToDo =	toDoServiceExtern.getAll();
				}
					
			}
			if (ToDo != null && !ToDo.isEmpty()) {
				return new ResponseEntity<>(ToDo,HttpStatus.OK);
	
			}else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	
		
	}
	

	@GetMapping("/2/user/{userId}")
	public ResponseEntity<Collection<ToDo>>  getToDoFromUser(@PathVariable Integer userId) {
		try {		

			Collection ToDo = null;
			
			if (UtilsOdilo.deDondeLeer(perfil)) {
				ToDo = 	toDoService.getByUserId(userId);

			}else {
				ToDo =	toDoServiceExtern.getByUserId(userId);
			}
			
			
			if (ToDo != null && !ToDo.isEmpty()) {
				return new ResponseEntity<>(ToDo,HttpStatus.OK);
	
			}else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}

		
	}
	
	@GetMapping("/2/stats")
	public ResponseEntity<Map<Boolean,Long>>  getStatsFromToDo() {
		
		try {		
			
			Map<Boolean,Long> ToDo = null;
			
			if (UtilsOdilo.deDondeLeer(perfil)) {
				ToDo = 	toDoService.getStats();

			}else {
				ToDo =	toDoServiceExtern.getStats();
			}

			
			if (ToDo != null && !ToDo.isEmpty()) {
				return new ResponseEntity<>(ToDo,HttpStatus.OK);
	
			}else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}

		
	}
	
	@GetMapping("/2/titles")
	public ResponseEntity<Collection<String>>  getTitlesFromToDo() {
		
		try {
			
			Collection ToDo = null;
			
			if (UtilsOdilo.deDondeLeer(perfil)) {
				ToDo = 	toDoService.getTitles();

			}else {
				ToDo = 	toDoServiceExtern.getTitles();
			}			
			
			if (ToDo != null && !ToDo.isEmpty()) {					
				return new ResponseEntity<>(ToDo,HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);	
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
	}


//TIPO 2 - POST
	@PostMapping("/2")
	public ResponseEntity<String>  postToDo(
			@RequestParam(value = "userId", required = true) Integer userId,
			@RequestParam(value = "id", required = true) Integer id,
			@RequestParam(value = "title", required = true) String title,
			@RequestParam(value = "completed", required = true) Boolean completed) {
		
		if (UtilsOdilo.deDondeLeer(perfil)) {
			
			try {
				ToDo beanEntrada = new ToDo();
					beanEntrada.setUserId(userId);
					beanEntrada.setId(id);
					beanEntrada.setTitle(title);
					beanEntrada.setCompleted(completed)	;
				toDoService.create(beanEntrada);

				return new ResponseEntity<>("OK",HttpStatus.CREATED);

			
			}catch(Exception e){
				return new ResponseEntity<>("NO creado",HttpStatus.BAD_REQUEST);
				//aqui ya habria que mirar porque no se ha creado (en la entidad y traspasarlo aqui)
				//Sobretodo tema del id.. de mirar como se quiere tratar, ya que es unico
			}


		}else {
			return new ResponseEntity<>("NO autorizado",HttpStatus.UNAUTHORIZED);
		}
	}

	//TIPO 3 - PUT
	@PutMapping("1")
	public ResponseEntity<String>  cancelJobs() {
		H2Config.scheduleDelayTask1(true);
		return new ResponseEntity<>("PARADOS",HttpStatus.OK); 
	}
	
	//TIPO 4 - DELETE
	@DeleteMapping("/2/{toDoId}")
	public ResponseEntity<String>  deleteToId(@PathVariable Long toDoId) {

		if (UtilsOdilo.deDondeLeer(perfil)) {	
			try {
				if (toDoService.delete(toDoId)) {
					return new ResponseEntity<>("OK",HttpStatus.CREATED);
				}else {
					return new ResponseEntity<>("NO borrado",HttpStatus.BAD_REQUEST);
				}
			}catch(Exception e){
				return new ResponseEntity<>("NO creado",HttpStatus.BAD_REQUEST);
			}
		}else {
			return new ResponseEntity<>("NO autorizado",HttpStatus.UNAUTHORIZED);
		}
	}

}
