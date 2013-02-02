package de.wbou.html.util;


public class LinkCorrector {

	public static String getHref(final String link, final String baseUrl) {
		String returnLink = link;
		if (link.startsWith(baseUrl)) {
			returnLink = link.substring(baseUrl.length());
		}
		return getHref(returnLink);
	}

	public static String getHref(final String link) {
		String returnLink = link;
		if (returnLink.endsWith(".htm")) {
			returnLink += "l";
		}
		if (!returnLink.endsWith(".html") && !returnLink.endsWith(".xhtml")) {
			returnLink += ".html";
		}
		return returnLink;
	}
}
