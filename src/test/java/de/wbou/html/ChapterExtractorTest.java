package de.wbou.html;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Test;

import junit.framework.TestCase;


public class ChapterExtractorTest extends TestCase {

	ChapterExtractor chapterExtractor;
	
	@Override
	public void setUp() {
		chapterExtractor = new ChapterExtractor();
	}
	
	@Test
	public void testGetOriginalFilenameWithoutPath() throws URISyntaxException, MalformedURLException {
		URI uri = new URI("http://x.html");
		Assert.assertEquals("x.html", chapterExtractor.getOriginalFilename(uri));
	}
	
	@Test
	public void testGetOriginalFilenameWithPath() throws URISyntaxException, MalformedURLException {
		URI uri = new URI("http://abc/x.html");
		Assert.assertEquals("x.html", chapterExtractor.getOriginalFilename(uri));
	}

	@Test
	public void testGetOriginalFilenameWithPathOnly() throws URISyntaxException, MalformedURLException {
		URI uri = new URI("http://abc/");
		Assert.assertEquals("abc", chapterExtractor.getOriginalFilename(uri));
	}
	
}
