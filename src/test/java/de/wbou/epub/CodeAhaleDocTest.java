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
import de.wbou.epub.writer.ImageDownloader;
import de.wbou.html.ChapterExtractor;
import de.wbou.html.LinkExtractor;

public class CodeAhaleDocTest {

	@Test
	public void test() throws XPathExpressionException, URISyntaxException, SAXException, IOException,
			ParserConfigurationException {
		LinkExtractor linkExtractor = new LinkExtractor();
		String url = "http://metrics.codahale.com/getting-started/";
		//String url = "http://dropwizard.codahale.com/getting-started/";
		URI uri = new URI(url);
		linkExtractor.setUri(uri);
		linkExtractor.setSelector("li.toctree-l1 > a");
		List<String> links = linkExtractor.extract();

		ChapterExtractor chapterExtractor = new ChapterExtractor();
		chapterExtractor.setSelectors(new ArrayList<String>());
		chapterExtractor.getSelectors().add("div.section");

		chapterExtractor.setSelectorsToRemove(new ArrayList<String>());

		Book book = new Book();
		book.setChapters(new ArrayList<BookChapter>());
		for (String link : links) {
			try {
				BookChapter chapter = chapterExtractor.extractChapter(new URI(link));
				book.getChapters().add(chapter);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		BookWriter bookWriter = new BookWriter();
		bookWriter.prepareDirectories("/tmp/dropwizard");

		for (String imgUrl : chapterExtractor.getImgUrls()) {
			new ImageDownloader(imgUrl, "/tmp/dropwizard/Images/" + imgUrl.substring(imgUrl.lastIndexOf('/') + 1))
					.start();
		}

		bookWriter.write("/tmp/dropwizard", book);
	}
}
