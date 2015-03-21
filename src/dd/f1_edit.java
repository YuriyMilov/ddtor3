package dd;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class f1_edit  extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public static String sposled = "init";

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html; charset=utf-8"); 

		String sh = req.getScheme() + "://" + req.getServerName() + ":"
				+ req.getServerPort() + req.getContextPath(), s = stkl
				.rfu_utf(sh + "/tmpl_f1_edit.html"); 
				
		s=s.replace("qqq_sdata_qqq", stkl.f1r());

		PrintWriter writer = resp.getWriter();
		writer.write(s);
		writer.close();
		resp.flushBuffer();
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html; charset=utf-8");

		String sh = req.getScheme() + "://" + req.getServerName() + ":"
				+ req.getServerPort() + req.getContextPath(), s = req.getParameter("text");
				if(s!=null)
					stkl.f1w(s);
				
					
		s = stkl
				.rfu_utf(sh + "/tmpl_f1_edit.html");
				
		s=s.replace("qqq_sdata_qqq", stkl.f1r());
		PrintWriter writer = resp.getWriter();
		writer.write(s);
		writer.close();
		resp.flushBuffer();
	}


}