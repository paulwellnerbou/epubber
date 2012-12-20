package de.wbou.html;

import java.net.URI;

public abstract class BaseExtractor {
	protected URI uri;

	public URI getUri() {
		return uri;
	}

	public void setUri(URI uri) {
		this.uri = uri;
	}
}
