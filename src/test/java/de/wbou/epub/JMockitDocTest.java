package de.wbou.epub;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.junit.Test;
import org.xml.sax.SAXException;

import de.wbou.epub.book.Book;
import de.wbou.epub.book.BookChapter;
import de.wbou.epub.writer.BookWriter;
import de.wbou.html.ChapterExtractor;
import de.wbou.html.LinkExtractor;

public class JMockitDocTest {

	@Test
	public void test() throws XPathExpressionException, URISyntaxException, SAXException, IOException, ParserConfigurationException {
		LinkExtractor linkExtractor = new LinkExtractor();
		String url = "http://jmockit.googlecode.com/svn/trunk/www/tutorial.html";
		URI uri = new URI(url);
		linkExtractor.setUri(uri);
		linkExtractor.setSelector("body > ol > li > a");
		List<String> links = linkExtractor.extract();
		
		ChapterExtractor chapterExtractor = new ChapterExtractor();
		chapterExtractor.setSelectors(new ArrayList<String>());
		//chapterExtractor.getSelectors().add("div#mitte_artikel");
		chapterExtractor.getSelectors().add("body");
		
		chapterExtractor.setSelectorsToRemove(new ArrayList<String>());
		
		chapterExtractor.getSelectorsToRemove().add(".navigation");
		
		Book book = new Book();
		book.setChapters(new ArrayList<BookChapter>());
		for(String link : links) {
			try {
				BookChapter chapter = chapterExtractor.extractChapter(new URI(link));
				book.getChapters().add(chapter);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		BookWriter bookWriter = new BookWriter();
		bookWriter.write("/tmp/jmockit", book);
	}
}
