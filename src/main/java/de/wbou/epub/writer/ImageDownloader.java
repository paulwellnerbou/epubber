package de.wbou.epub.writer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.Closeables;


public class ImageDownloader extends Thread {

	private static transient Logger LOG = LoggerFactory.getLogger(ImageDownloader.class);

	private final String url;
	private final String targetFile;
	private final CloseableHttpClient httpClient;

	public ImageDownloader(String url, String targetFile) {
		this.url = url;
		this.targetFile = targetFile;
		this.httpClient = HttpClientBuilder.create().build();
	}

	private void download() throws ClientProtocolException, IOException {
		LOG.info("Downloading image from {} to {}...", url, targetFile);
		HttpGet request = new HttpGet(this.url);
		HttpResponse response = httpClient.execute(request);

		FileOutputStream fos = new FileOutputStream(targetFile);
		InputStream is = response.getEntity().getContent();
		IOUtils.copyLarge(is, fos);
		fos.flush();
		IOUtils.closeQuietly(is);
		IOUtils.closeQuietly(fos);
	}

	@Override
	public void run() {
		try {
			download();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Closeables.closeQuietly(httpClient);
	}
}
