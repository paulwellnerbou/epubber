package de.wbou.epub.book;

import java.util.List;

public class Book {
	private String title;
	private List<BookChapter> chapters;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<BookChapter> getChapters() {
		return chapters;
	}

	public void setChapters(List<BookChapter> chapters) {
		this.chapters = chapters;
	}
}
