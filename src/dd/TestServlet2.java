package dd;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.util.ajax.JSON;
import org.jsoup.Jsoup;

public class TestServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String sh = req.getScheme() + "://" + req.getServerName() + ":"
				+ req.getServerPort() + req.getContextPath();

		String s11 = "<!DOCTYPE html><html><head><meta content='text/html; charset=UTF-8' http-equiv='Content-Type' /></head><script>function qq() {new EventSource('"
				+ sh
				+ "/TestServlet?1').onmessage = function(event) {document.getElementById('output').innerHTML = event.data;};}</script></head><title>Лента ddtor</title><body onload=\"qq();\"><div id='output'><!-- qqq --></div></body></html>";
		String s22 = "<!DOCTYPE html><html><head><meta content='text/html; charset=UTF-8' http-equiv='Content-Type' /></head><script>function qq() {new EventSource('"
				+ sh
				+ "/TestServlet?2').onmessage = function(event) {document.getElementById('output').innerHTML = event.data + document.getElementById('output').innerHTML;};}</script></head><title>Лента ddtor</title><body onload='qq();'><div id='output'><!-- qqq -->"
				+ "</div></body></html>";

		if (req.getQueryString() == null) {
			resp.setContentType("text/html; charset=utf-8");
			PrintWriter writer = resp.getWriter();
			resp.setCharacterEncoding("UTF-8");
			writer.write(s11.replace("<!-- qqq -->", stkl.sts));
			// writer.write(s11);
			writer.close();
			resp.flushBuffer();
		} else

		if (req.getQueryString().equals("1")) {

			String s = "<!-- qqq -->" + stkl.mail;
			String[] sss = s.trim().split("<!-- qqq -->");
			int max = sss.length;
			int n = stkl.get_random_number(1, max);
			String s1 = sss[n];
			s1 = s1.replace("<b>", "qqqb").replace("</b>", "qqq/b");
			s = Jsoup.parse(s1).text();

			// s="max="+max+"n="+n+" "+s+"<hr/>"+s1+"<hr/>";

			s = s.replace("qqqb", "<b>").replace("qqq/b", "</b>");
			s = "<a href='http://www.ddtor.com/p/blog-page_31.html' style=\"color:#005599;font-family: Arial;font-size:13px;text-decoration:none;\" target='_blank'>"
					+ s + "</a>";

			resp.setContentType("text/event-stream");
			resp.setCharacterEncoding("UTF-8");
			PrintWriter writer = resp.getWriter();
			writer.write("data: " + s + " \n\n");
			writer.close();
			resp.flushBuffer();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
			}
		}

		else

		if (req.getQueryString().equals("2")) {
			resp.setContentType("text/event-stream");
			resp.setCharacterEncoding("UTF-8");
			PrintWriter writer = resp.getWriter();
			writer.write("data: " + stkl.mail + " \n\n");
			writer.close();
			resp.flushBuffer();
			try {
				Thread.sleep(33000);
			} catch (InterruptedException e) {
			}
		}

		else

		if (req.getQueryString().equals("22")) {
			resp.setContentType("text/html; charset=utf-8");
			PrintWriter writer = resp.getWriter();
			resp.setCharacterEncoding("UTF-8");
			writer.write(s22.replace("<!-- qqq -->", stkl.mail));
			writer.close();
			resp.flushBuffer();
		}

	}
}

// response.setContentType("text/event-stream");
// writer.write("data: " + i++ + " " + sposled + "\n\n");
/*
 * <!DOCTYPE HTML> <html> <meta charset="utf-8"> <head>
 * 
 * <script> function qq() { new EventSource("/TestServlet").onmessage =
 * function(event) { document.getElementById("output").innerHTML = event.data;
 * }; } </script> </head> <body onload="qq();"> <div id="output"></div> </body>
 * </html>
 */