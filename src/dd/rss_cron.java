package dd;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class rss_cron extends HttpServlet {


	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
			String sh = req.getScheme() + "://" + req.getServerName() + ":"
					+ req.getServerPort() + req.getContextPath();
			
			String s=stkl.rfu_utf(sh+"/rss_add");
			
			resp.setCharacterEncoding("UTF8"); 
			resp.setContentType("text/html");
			
			PrintWriter out = resp.getWriter();
			out.write(s);
			out.flush();
			out.close();
			
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		doGet(req, resp);
	}

	
	private static final long serialVersionUID = 1L;

}