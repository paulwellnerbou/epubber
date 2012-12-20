package de.wbou.html;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.SAXException;


public class ContentExtractor {
	private List<String> selectors;

	public String extract(String url) throws URISyntaxException, SAXException, IOException, ParserConfigurationException, XPathExpressionException {
		Document doc = Jsoup.connect(url).get();
		StringBuffer buffer = new StringBuffer();
		for(String selector : getSelectors()) {
			buffer.append(extractContent(doc, selector));
		}
		return buffer.toString();
	}
	
	private String extractContent(Document doc, String selector) {
		Elements links = doc.select(selector);
		StringBuffer buffer = new StringBuffer();
		for(Element e : links) {
			buffer.append(e.html());
		}
		return buffer.toString();
	}

	public List<String> getSelectors() {
		return selectors;
	}

	public void setSelectors(List<String> selectors) {
		this.selectors = selectors;
	}
	
}
