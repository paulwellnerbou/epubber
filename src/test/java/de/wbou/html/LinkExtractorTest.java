package de.wbou.html;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.junit.Test;
import org.xml.sax.SAXException;

public class LinkExtractorTest {

	@Test
	public void testExtract() throws URISyntaxException, SAXException, IOException, ParserConfigurationException, XPathExpressionException {
		LinkExtractor linkExtractor = new LinkExtractor();
		linkExtractor.setUri(new URI("http://www.heise.de/developer/artikel/Modularitaet-und-Liskovsches-Prinzip-in-komplexen-Systemen-1746936.html"));
		linkExtractor.setSelector("div.navi_toc ol li a");
		linkExtractor.extract();
	}

}
