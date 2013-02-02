package de.wbou.html;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import de.wbou.epub.book.BookChapter;

public class ChapterExtractor {

	private static final String IMAGES = "Images";

	private String appendToUrl = StringUtils.EMPTY;
	private List<String> selectors;
	private List<String> selectorsToRemove;
	private final List<String> imgUrls = new ArrayList<String>();

	private String extractTitle(Document doc) {
		return doc.getElementsByTag("head").get(0).getElementsByTag("title").get(0).text();
	}

	protected String getOriginalFilename(URI uri) throws MalformedURLException {
		String path = uri.toURL().toString();
		if (path.endsWith("/")) {
			path = path.substring(0, path.length() - 1);
		}
		return path.substring(path.lastIndexOf('/') + 1);
	}

	public BookChapter extractChapter(URI uri) throws IOException {
		Document doc = Jsoup.connect(uri.toString() + getAppendToUrl()).get();
		BookChapter bookChapter = new BookChapter();
		bookChapter.setTitle(extractTitle(doc));
		bookChapter.setFilename(getOriginalFilename(uri));

		StringBuffer buffer = new StringBuffer();
		for (String selector : getSelectors()) {
			buffer.append(extractContent(doc, selector, uri));
		}
		bookChapter.setHtml(buffer.toString());
		return bookChapter;
	}

	private String extractContent(Document doc, String selector, URI uri) {
		Elements links = doc.select(selector);
		StringBuffer buffer = new StringBuffer();
		for (Element e : links) {
			removeStuff(e);
			handleImgUrls(e, uri);
			buffer.append(e.html());
		}
		return buffer.toString();
	}

	private void removeStuff(Element e) {
		for (String selector : selectorsToRemove) {
			for (Element toRemove : e.select(selector)) {
				toRemove.remove();
			}
		}
	}

	private void handleImgUrls(Element e, URI uri) {
		List<Element> imgsToCorrect = new ArrayList<Element>();
		for (Element img : e.getElementsByTag("img")) {
			imgsToCorrect.add(img);
		}

		for (Element img : imgsToCorrect) {
			String imgUrl = img.attr("src");
			if (!imgUrls.contains(imgUrl)) {
				imgUrls.add(imgUrl);
			}
			img.attr("src", "/" + IMAGES + "/" + imgUrl.substring(imgUrl.lastIndexOf('/') + 1));
		}
	}

	public List<String> getSelectors() {
		return selectors;
	}

	public void setSelectors(List<String> selectors) {
		this.selectors = selectors;
	}

	public List<String> getSelectorsToRemove() {
		return selectorsToRemove;
	}

	public void setSelectorsToRemove(List<String> selectorsToRemove) {
		this.selectorsToRemove = selectorsToRemove;
	}

	public String getAppendToUrl() {
		return appendToUrl;
	}

	public void setAppendToUrl(String appendToUrl) {
		this.appendToUrl = appendToUrl;
	}

	public List<String> getImgUrls() {
		return imgUrls;
	}

}
