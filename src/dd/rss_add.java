package dd;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;

public class rss_add extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		String s = "http://colonelcassad.livejournal.com/data/rss";
		s="http://rusvesna.su/rss.xml";
		
		s = get_rss(s,3,"Text","http://rusvesna.su/rss.xml","Русская весна");

		// //////////////////////////////////////////////////////

		s = "ok ок";
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html; charset=utf-8");
		out.write(s);
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		doGet(req, resp);
	}

	public static String get_rss(String s, int j,String sid,String source, String name) {
		
		s = stkl.rfu_utf(s);		int i = 0,k=0;
		
		String sa="", tit="", dat="", ss="", title = "", link = "", description = "";

		try {

			if (s.contains("<title>")) {

				i = s.indexOf("<title>");
				s = s.substring(i + 7);
				i = s.indexOf("</title>");
				tit = s.substring(0, i);

				i = s.indexOf("<pubDate>");
				s = s.substring(i + 9);
				i = s.indexOf("</pubDate>");
				dat = s.substring(0, i);
				
			}

			while (s.contains("<title>") && k++ <j) {
			
				i = s.indexOf("<title>");
				s = s.substring(i + 7);
				i = s.indexOf("</title>");
				title = s.substring(0, i);

				i = s.indexOf("<link>");
				s = s.substring(i + 6);
				i = s.indexOf("</link>");
				link = s.substring(0, i);

				i = s.indexOf("<description>");
				s = s.substring(i + 13);
				i = s.indexOf("</description>");
				description = s.substring(0, i);

				i = s.indexOf("<pubDate>");
				s = s.substring(i + 9);
				i = s.indexOf("</pubDate>");
				dat = s.substring(0, i);	
				
				ss = "<br/><br/><h3>" + title + "</h3> " + "<br/><br/>"
						+ "<table align=\"center\" style=\"width: 100%;\"><tr style=\"width: 555px;\" align=\"center\"><td align=\"left\">"+description 
						+ "<br/><br/><a href=\""+link+"\" target=\"_blank\"> <i>"+tit+"</i></a> &nbsp;&nbsp; "+dat+"<br/></td></tr></table>";

				ss = ss.replaceAll("&amp;", "&").replaceAll("&lt;", "<")
						.replaceAll("&gt;", ">").replaceAll("&quot;", "\"");
				
				ss="<div >"+ss+"</div>";
				
				add_rss_datastore(ss, sid, source,name,dat);
				sa=sa+ss;
				ss="";
				dat="";
			}
			

		} catch (Exception e) {
			sa=e.toString();
		}

	
		return sa;

	}

	public static void add_rss_datastore(String s, String sid, String source, String name, String updated) {
		Key kk = KeyFactory.createKey(sid, "rss_txt");
		Date date = new Date();
		Entity enot = new Entity(sid, kk);
		enot.setProperty("date", date);
		enot.setProperty("content", new Text(s));
		enot.setProperty("source", source);
		enot.setProperty("name", name);
		enot.setProperty("updated", updated);

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		datastore.put(enot);
	}

	private static final long serialVersionUID = 1L;

}