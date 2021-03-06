package de.wbou.epub.writer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.google.common.io.Files;

import de.wbou.epub.book.Book;
import de.wbou.epub.book.BookChapter;
import de.wbou.html.util.LinkCorrector;

public class HtmlFileBookWriter implements BookWriter {
	
	private final String targetDirectory;

	public HtmlFileBookWriter(String targetDirectory) {
		this.targetDirectory = targetDirectory;
		try {
			prepareDirectories();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private final ChapterWriter chapterWriter = new ChapterWriter();

	private void prepareDirectories() throws IOException {
		File target = new File(targetDirectory + "/tmp.tmp");
		Files.createParentDirs(target);

		File imgDir = new File(targetDirectory + "/Images/tmp.tmp");
		Files.createParentDirs(imgDir);
	}

	public void write(Book book) {
		int index = 1;
		for(BookChapter chapter : book.getChapters()) {
			File chapterFile = new File(targetDirectory+"/"+calculateChapterFilename(index, chapter));
			try {
				writeChapter(chapterFile, chapter);
			} catch (IOException e) {
				e.printStackTrace();
			}
			index++;
		}
	}
	
	public static String calculateChapterFilename(int index, BookChapter chapter) {
		return LinkCorrector.getHref(chapter.getFilename());
	}
	
	private void writeChapter(File file, BookChapter chapter) throws IOException {
		System.out.println(file.getAbsolutePath());
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		chapterWriter.write(fileOutputStream, chapter);
		fileOutputStream.flush();
		fileOutputStream.close();
	}
}
