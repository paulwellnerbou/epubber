package de.wbou.html.modifier;

import java.net.URI;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import de.wbou.html.util.LinkCorrector;


public class RelativeLinkHrefCorrector {

	public void correctLinks(Document doc, URI uri) {
		Elements links = doc.select("a");
		String preLink = uri.getScheme() + "://" + uri.getHost();
		for (Element e : links) {
			String link = e.attr("href");
			if (isRelativeLink(link, preLink)) {
				e.attr("href", LinkCorrector.getHref(link, preLink));
			}
		}
	}

	protected boolean isRelativeLink(final String link, final String preLink) {
		return !link.startsWith("http://") || link.startsWith(preLink);
	}
}
