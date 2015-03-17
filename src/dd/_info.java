package dd;

import java.nio.charset.Charset;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;
import com.google.gwt.user.server.Base64Utils;

public class _info {
	// GAE ddtor3 admin at ddtor quick-0
	public static String semailto = "k9992.tverskoy@blogger.com";
	public static String semailfrom = "k999.quicklydone@gmail.com";
	public static String blobkey = "";

	public static void main(String[] args) {

		String s = "<p> <span> foo </span> <em> bar <a> foobar </a> baz </em> </p>";
		// s=stkl.rfu_utf("http://www.ddtor.com");
		// s = Jsoup.parse(s).text();
		// s = "на " + stkl.get_date() + "года";
s="Mon, 16 Mar 2015 18:20:39 +0300";

		System.out.println(s);
	}

	public static String rem_all_sub(String s, String s1, String s2) {
		int i = s.indexOf(s1);
		int j = s.indexOf(s2);
		String s3 = "";
		if (i > -1) {
			if (i > 0) {
				s3 = s.substring(0, i);
				s = s.substring(i);
			}

			String[] sss = s.split(s1);
			s = "";
			for (String ss : sss) {
				i = ss.indexOf(s2);
				if(i>-1)
					s = s + ss.substring(i+s2.length());
			}
		}
		return s3+s;
	}

	public static String get_rss(String s) {
		int i = 0;
		String ss = "", title = "", link = "", description = "";

		try {

			if (s.contains("<title>")) {
				i = s.indexOf("<title>");
				s = s.substring(i + 7);
			}

			while (s.contains("<title>")) {
				i = s.indexOf("<title>");
				s = s.substring(i + 7);
				i = s.indexOf("</title>");
				title = s.substring(0, i);
				s = s.substring(i + 8);

				i = s.indexOf("<link>");
				s = s.substring(i + 6);
				i = s.indexOf("</link>");
				link = s.substring(0, i);
				s = s.substring(i + 5);

				i = s.indexOf("<description>");
				s = s.substring(i + 13);
				i = s.indexOf("</description>");
				description = s.substring(0, i);
				s = s.substring(i + 14);

				ss = "<a href=" + link + ">" + title + "</a> " + "<br>\r\n<br>"
						+ description + "\r\n<br>----<br>\r\n";

				ss = ss.replaceAll("&amp;", "&").replaceAll("&lt;", "<")
						.replaceAll("&gt;", ">").replaceAll("&quot;", "\"");

				// add_rss_datastore(ss, "Text");

				System.out.println(ss);

			}

		} catch (Exception e) {

		}

		return s;

	}

	public static void add_rss_datastore(String s, String sid) {
		Key kk = KeyFactory.createKey(sid, "rss_txt");
		Date date = new Date();
		Entity enot = new Entity(sid, kk);
		enot.setProperty("user", "user");
		enot.setProperty("date", date);
		enot.setProperty("content", new Text(s));

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		datastore.put(enot);
	}

}
