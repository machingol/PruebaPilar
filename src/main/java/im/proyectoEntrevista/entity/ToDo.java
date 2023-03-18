package im.proyectoEntrevista.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="TODOS")
public class ToDo {
	
	@Id
	@Column(name = "ID")
	public int id;
	
	@Column(name = "USERID")
	public int userId;


	@Column(name = "TITLE")
	public String title;	
	
	@Column(name = "COMPLETED")
	public Boolean completed;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Boolean getCompleted() {
		return completed;
	}

	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
