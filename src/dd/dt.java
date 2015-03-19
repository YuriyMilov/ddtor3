package dd;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;


import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Text;
import com.google.gwt.user.server.Base64Utils;

public class dt extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html; charset=utf-8");
		
		String s = "на " + stkl.get_date() + " года"; 
	
		PrintWriter writer = resp.getWriter();
		resp.setContentType("text/event-stream");
		resp.setCharacterEncoding("UTF-8");
		writer.write("йцукент: " + s + "\n\n");
		writer.close();
		resp.flushBuffer();
	}

	public static String get_datastore_rss(int i,String sid) throws IOException {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Key guestbookKey = KeyFactory.createKey(sid, "rss_txt");
		Query query = new Query(sid, guestbookKey).addSort("date",
				Query.SortDirection.DESCENDING);
		List<Entity> greetings = datastore.prepare(query).asList(
				FetchOptions.Builder.withLimit(i));
		String s = "",s2="";
		for (Entity greeting : greetings) {
			byte[] bb = ((Text) greeting.getProperty("content")).getValue()
					.getBytes("utf8");
			s2=pars_rss(new String(bb, "UTF-8"));
			if (!s2.contains("qqq_qqq")) 
				s = s + s2 + "<br/>";
			datastore.delete(greeting.getKey());
		}
		return s;
	}

	public static String pars_rss(String s) {

		byte[] data = s.getBytes(Charset.forName("UTF-8"));

		String scoded = Base64Utils.toBase64(data);

		s = Jsoup.parse(s).text();
		if (s.length() > 444)
			s = s.substring(0,444);
		
		if (s.length() > 55)
			s = "<form action=\"http://bb.ddtor.com/mmlink\" method=\"post\"> "
				+ "<button type=submit name=qq>&nbsp;</button>&nbsp;"
				+ s + "<input type=hidden value=\"" + scoded + "\" name=data>"
				+ "...<button type=submit name=qq>></button></form><hr>";		
			
		else
			s = "qqq_qqq";
		return s;

	}


}