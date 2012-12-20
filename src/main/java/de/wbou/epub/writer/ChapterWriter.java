package de.wbou.epub.writer;

import java.io.IOException;
import java.io.OutputStream;

import com.google.common.base.Charsets;

import de.wbou.epub.book.BookChapter;

public class ChapterWriter {

	public void write(OutputStream out, BookChapter chapter) throws IOException {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer
				.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
						+ "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">\n"
						+ "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n"
						+ "<link href=\"../Styles/stylesheet.css\" rel=\"stylesheet\" type=\"text/css\" />"
						+ "<head>\n" + "    <title>");
		stringBuffer.append(chapter.getTitle());
		stringBuffer.append("</title>\n" + "</head>\n" + "<body>\n");
		stringBuffer.append(chapter.getHtml());
		stringBuffer.append("</body>\n" + "</html>");
		out.write(stringBuffer.toString().getBytes(Charsets.UTF_8));
	}
}
