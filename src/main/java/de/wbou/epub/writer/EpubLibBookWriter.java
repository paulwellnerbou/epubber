package de.wbou.epub.writer;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.epub.EpubWriter;
import de.wbou.epub.book.Book;
import de.wbou.epub.book.BookChapter;
import de.wbou.epub.book.Image;

public class EpubLibBookWriter implements BookWriter {

	private static Logger LOG = LoggerFactory.getLogger(EpubLibBookWriter.class);

	private final String targetDirectory;

	public EpubLibBookWriter(String targetDirectory) {
		this.targetDirectory = targetDirectory;
	}

	public void write(Book book) {
		try {
			// Create new Book
			nl.siegmann.epublib.domain.Book epub = new nl.siegmann.epublib.domain.Book();

			// Set the title
			epub.getMetadata().addTitle(book.getTitle());

			for (BookChapter chapter : book.getChapters()) {
				int index = 1;
				String filename = targetDirectory + "/" + HtmlFileBookWriter.calculateChapterFilename(index, chapter);
				epub.addSection(chapter.getTitle(), new Resource(new FileInputStream(filename), chapter.getFilename()));
				index++;
			}
			
			for (Image img : book.getImages()) {
				LOG.debug("Adding image {} to resources ", img.getFilename());
				epub.getResources().add(new Resource(new FileInputStream(img.getFilename()), img.getHref()));
			}

			// Create EpubWriter
			EpubWriter epubWriter = new EpubWriter();

			// Write the Book as Epub
			epubWriter.write(epub, new FileOutputStream(targetDirectory + "/test1_book1.epub"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
