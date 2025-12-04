package com.example.demo;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Book {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String title, author, editorial;

	public Book() {}
	public Book(String title, String author, String editorial) {
		this.title = title;
		this.author = author;
		this.editorial = editorial;
	}

	// Getters
	public Long getId() {
		return id;
	}

	public String getName() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public String getEditorial() {
		return editorial;
	}

	// Setters
	public void setName(String name) {
		this.title = name;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", name=" + title + ", author=" + author + ", editorial=" + editorial + "]";
	}

}
