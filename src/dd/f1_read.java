package dd;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Text;

public class f1_read extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static String sposled = "init";

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String sh = req.getScheme() + "://" + req.getServerName() + ":"
				+ req.getServerPort() + req.getContextPath(), s = stkl
				.rfu_utf(sh + "/tmpl_f1_edit.html"); 
		
		s=s.replace("qqq_sdata_qqq", stkl.f1r());
		
		resp.setContentType("text/html; charset=utf-8");		
		PrintWriter writer = resp.getWriter();
		writer.write(s);
		writer.close();
		resp.flushBuffer();
	}


}
