package de.wbou.html;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.SAXException;

public class LinkExtractor extends BaseExtractor {
	private String selector;

	public List<String> extract() throws URISyntaxException, SAXException,
			IOException, ParserConfigurationException, XPathExpressionException {
		Document doc = Jsoup.connect(uri.toString()).get();
		Elements links = doc.select(selector);
		List<String> linkList = new ArrayList<String>();
		String preLink = uri.getScheme() + "://" + uri.getHost();
		preLink += uri.getPath().substring(0, uri.getPath().lastIndexOf('/'));
		for (Element e : links) {
			String link = e.attr("href");
			if (!link.startsWith("http:")) {
				link = preLink + "/" + link;
			}
			linkList.add(link);
			System.out.println(e.attr("href"));
		}
		return linkList;
	}

	public String getSelector() {
		return selector;
	}

	public void setSelector(String selector) {
		this.selector = selector;
	}
}
