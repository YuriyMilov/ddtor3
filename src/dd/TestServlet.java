package dd;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		response.setContentType("text/event-stream");
		response.setCharacterEncoding("UTF-8");
		
	
		String s = "ййййййй ыне кке ке ";
	
		
		PrintWriter writer = response.getWriter();
		writer.write("data: " + s + "\n\n");
		writer.close();
		response.flushBuffer();
		try {
		Thread.sleep(5000);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
/*
 * <!DOCTYPE HTML> <html> <meta charset="utf-8"> <head> <script
 * type="text/javascript"> function start() { var eventSource = new
 * EventSource("TestServlet"); eventSource.onmessage = function(event) {
 * document.getElementById('foo').innerHTML =
 * document.getElementById('foo').innerHTML + " Ñ�Ñ�<br/>Ñ„Ñ„ "+event.data; }; }
 * </script> </head> <body onload="start()"> Time: <span id="foo"></span>
 * </body> </html>
 */