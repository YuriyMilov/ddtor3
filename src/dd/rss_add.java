package dd;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;
import com.google.gwt.user.server.Base64Utils;

public class rss_add extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		String s="";
		
		s = s + get_rss("http://rusvesna.su/rss.xml",1,"Русская весна");
		s = s + get_rss("http://colonelcassad.livejournal.com/data/rss",1,"Colonel Cassad");
		s = s + get_rss("http://news.yandex.ru/politics.rss",2,"Яндекс.Новости: Политика");
		
		resp.setContentType("text/html; charset=utf-8");
		PrintWriter writer = resp.getWriter();
		//resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		writer.write(s);
		writer.close();
		resp.flushBuffer();
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		doGet(req, resp);
	}

	public static String get_rss(String s, int j, String name) {
		String source=s;
		String sid="Text";
		
		s = stkl.rfu_utf(s);		
		int i = 0,k=0;
		
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
				
				
				ss = "<table align=\"center\" style=\"width: 100%;\"><tr style=\"width: 555px;\" align=\"center\"><td align=\"left\">"
						+ "<br/><b><a href=\""+link+"\">" + title + "</a></b><br/><br/>"+description 
						+ "<br/><br/> <i><a href=\""+link+"\" target=\"_blank\">"+tit+"</a></i> &nbsp;&nbsp; "+dat+"<br/></td></tr></table>";

				ss = ss.replaceAll("&amp;", "&").replaceAll("&lt;", "<")
						.replaceAll("&gt;", ">").replaceAll("&quot;", "\"");
				
				ss="<div >"+ss+"</div>";
				
				add_rss_datastore(ss, sid, source,name, title, dat);
				sa=sa+ss;
				ss="";
				dat="";
			}
			

		} catch (Exception e) {
			sa=e.toString();
		}

	
		return sa;

	}

	public static void add_rss_datastore(String s, String sid, String source, String name, String title , String updated) {
		
		
		s=pars_rss(s, name, title);
		
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

	public static String pars_rss(String s,String name,String title) {

		byte[] data = s.getBytes(Charset.forName("UTF-8"));
		String scoded = Base64Utils.toBase64(data);
		s = Jsoup.parse(s).text().trim();
		title=Jsoup.parse(title).text().trim();
		s=s.replace(title, "");
		
		if (s.length() > 444)
			s = s.substring(0,444);
		
		
	String sfid=String.valueOf(Math.random());
		
	s = s + "...<form id=\"myform"+sfid+"\" action=\"http://bb.ddtor.com/mmlink2\" method=\"post\" target=\"_blank\">"
					+ "<input type=hidden value=\""
					+ scoded
					+ "\" name=data></form><hr>";

	s = "<a href=\"javascript: document.getElementById('myform"+sfid+"').submit();\"><b>"+name+"</a><br/>"+title+":</b> " + s;

		return s;

	}
	private static final long serialVersionUID = 1L;

}