package model.domain;

import java.util.Date;

public class Comment {

	private int id;
	private String content;
	private User author;
	private Date date;
	private static int idCounter=50;
	public Comment(int id, String content, User author, Date date) {
		this.id = id;
		this.content = content;
		this.author = author;
		this.date = date;
	}

	public Comment(String content, User author, Date date) {
		this.id = idCounter;
		idCounter++;
		this.content = content;
		this.author = author;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	

}
