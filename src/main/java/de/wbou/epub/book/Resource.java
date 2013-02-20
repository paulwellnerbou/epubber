package de.wbou.epub.book;


public class Resource {

	private String filename;
	private String href;

	public Resource(String href, String filenameOnFilesystem) {
		this.href = href;
		this.filename = filenameOnFilesystem;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}
}
