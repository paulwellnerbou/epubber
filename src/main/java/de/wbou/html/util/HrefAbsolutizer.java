package de.wbou.html.util;

import java.net.URI;
import java.net.URISyntaxException;


public class HrefAbsolutizer {

	public static String absolutize(String href, String completeUrl) throws URISyntaxException {
		if (href.startsWith("http:")) {
			return href;
		}

		URI uri = new URI(completeUrl);
		String base = uri.getScheme() + "://" + uri.getHost();

		if (href.startsWith("/")) {
			return base + href;
		}

		String basePath = uri.getPath().substring(0, uri.getPath().lastIndexOf('/'));
		return base + "/" + basePath + "/" + href;
	}
}
