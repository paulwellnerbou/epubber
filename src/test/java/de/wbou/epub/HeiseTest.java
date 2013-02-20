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
import de.wbou.epub.book.Image;
import de.wbou.epub.writer.BookWriter;
import de.wbou.epub.writer.EpubLibBookWriter;
import de.wbou.epub.writer.HtmlFileBookWriter;
import de.wbou.epub.writer.ImageDownloader;
import de.wbou.html.ChapterExtractor;
import de.wbou.html.LinkExtractor;
import de.wbou.html.util.HrefAbsolutizer;

public class HeiseTest {

	@Test
	public void test() throws XPathExpressionException, URISyntaxException, SAXException, IOException,
			ParserConfigurationException {
		LinkExtractor linkExtractor = new LinkExtractor();
		// String url = "http://www.heise.de/developer/artikel/Modularitaet-und-Liskovsches-Prinzip-in-komplexen-Systemen-1746936.html";
		String url = "http://www.heise.de/developer/artikel/Reisetipps-fuer-Software-Architekten-227172.html";
		URI uri = new URI(url);
		linkExtractor.setUri(uri);
		linkExtractor.setSelector("div.navi_toc ol li a");
		List<String> links = linkExtractor.extract();

		ChapterExtractor chapterExtractor = new ChapterExtractor();
		chapterExtractor.setSelectors(new ArrayList<String>());
		//chapterExtractor.getSelectors().add("div#mitte_artikel");
		chapterExtractor.getSelectors().add("body");
		chapterExtractor.setAppendToUrl("?view=print");

		chapterExtractor.setSelectorsToRemove(new ArrayList<String>());

		chapterExtractor.getSelectorsToRemove().add("div#social_bookmarks");
		chapterExtractor.getSelectorsToRemove().add("div.related_items");
		chapterExtractor.getSelectorsToRemove().add("div.heisetopnavi");
		chapterExtractor.getSelectorsToRemove().add("ul#navi_bottom");
		chapterExtractor.getSelectorsToRemove().add("p.link_forum_thema");
		chapterExtractor.getSelectorsToRemove().add("div.link_forum_beitrag");
		chapterExtractor.getSelectorsToRemove().add("div#navi_toc");
		chapterExtractor.getSelectorsToRemove().add("div.navi_toc");
		chapterExtractor.getSelectorsToRemove().add("p.kapitel");
		chapterExtractor.getSelectorsToRemove().add("p.seitennavigation");
		chapterExtractor.getSelectorsToRemove().add("ul.optionen_beitrag");
		chapterExtractor.getSelectorsToRemove().add("script");

		Book book = new Book();
		book.setChapters(new ArrayList<BookChapter>());
		for (String link : links) {
			BookChapter chapter = chapterExtractor.extractChapter(new URI(link));
			book.getChapters().add(chapter);
		}

		String targetDirectory = "/tmp/book/";

		List<Image> images = new ArrayList<Image>();
		for (String imgUrl : chapterExtractor.getImgUrls()) {
			String baseImageFilename = imgUrl.substring(imgUrl.lastIndexOf('/') + 1);
			String imageFilename = targetDirectory + baseImageFilename;
			images.add(new Image(baseImageFilename, imageFilename));
			String absoluteImgUrl = HrefAbsolutizer.absolutize(imgUrl, url);
			new ImageDownloader(absoluteImgUrl, imageFilename).start();
		}
		book.setImages(images);

		BookWriter bookWriter = new HtmlFileBookWriter(targetDirectory);
		bookWriter.write(book);

		BookWriter epubBookWriter = new EpubLibBookWriter(targetDirectory);
		epubBookWriter.write(book);
	}
}
