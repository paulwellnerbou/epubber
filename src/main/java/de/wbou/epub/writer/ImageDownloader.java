package de.wbou.epub.writer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.CloseableHttpClient;

import com.google.common.io.Closeables;


public class ImageDownloader extends Thread {

	private final String url;
	private final String targetFile;
	private final CloseableHttpClient httpClient;

	public ImageDownloader(String url, String targetFile) {
		this.url = url;
		this.targetFile = targetFile;
		this.httpClient = HttpClientBuilder.create().build();
	}

	private void download() throws ClientProtocolException, IOException {
		HttpGet request = new HttpGet(this.url);
		HttpResponse response = httpClient.execute(request);

		BufferedInputStream bufferedInputStream = new BufferedInputStream(response.getEntity().getContent());
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(targetFile));
		IOUtils.copyLarge(bufferedInputStream, bufferedOutputStream);
		IOUtils.closeQuietly(bufferedInputStream);
		IOUtils.closeQuietly(bufferedOutputStream);
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
