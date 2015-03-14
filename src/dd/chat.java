package dd;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class chat extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String sh = req.getScheme() + "://" + req.getServerName() + ":"
				+ req.getServerPort() + req.getContextPath();

			req.setCharacterEncoding("UTF-8");
			String s = req.getParameter("kk1");
			String s3 = req.getParameter("who");

			if (s3 == null)
				s3="-";

				if (s != null) 
					{
				if (s.length() > 0) 
					add_datastore("<i>"+s3+"</i>: <b>"+s+"</b>");
				}
			else
				s="";
			
			resp.setCharacterEncoding("UTF8");
			resp.setContentType("text/html");
			PrintWriter out = resp.getWriter();
			s = stkl.rfu_utf(sh + "/chat.html").replace("<!-- kk -->", s);		
			out.write(s);
			out.flush();
			out.close();

	}
	void add_datastore(String s ) {
		try {
			s = URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Key kk = KeyFactory.createKey("Guestbook", "guestbookName");
		Date date = new Date();
		Entity greeting = new Entity("Greeting", kk);
		greeting.setProperty("user", "user");
		greeting.setProperty("date", date);
		greeting.setProperty("content", s);

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		datastore.put(greeting);
	}
	
	
	String get_datastore() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Key guestbookKey = KeyFactory.createKey("Guestbook", "guestbookName");

		Query query = new Query("Greeting", guestbookKey).addSort("date",
				Query.SortDirection.DESCENDING);
		List<Entity> greetings = datastore.prepare(query).asList(
				FetchOptions.Builder.withLimit(25));

		// s = String.valueOf(greetings.size()) + "";
		String s = "";
		for (Entity greeting : greetings) {
			s = greeting.getProperty("content") + "<br/>" + s;
		}
		return s;
	}
}
