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

public class news extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html; charset=utf-8");
		String s = get_datastore_s(40);
		
		
		PrintWriter out = resp.getWriter();
		out.write(s);
		out.flush();
		out.close();
	}

	public static String get_datastore_s(int i) throws IOException {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Key guestbookKey = KeyFactory.createKey("Guestbook", "guestbookName");
		Query query = new Query("Greeting", guestbookKey).addSort("date",
				Query.SortDirection.DESCENDING);
		List<Entity> greetings = datastore.prepare(query).asList(
				FetchOptions.Builder.withLimit(i));
		String s = "",s2="";
		for (Entity greeting : greetings) {
			byte[] bb = ((Text) greeting.getProperty("content")).getValue()
					.getBytes("utf8");
			s2=par(new String(bb, "UTF-8"));
			if (!s2.contains("qqq_qqq")) 
				s = s + s2 + "<br/>";
			datastore.delete(greeting.getKey());
		}
		return s;
	}

	public static String par(String s) {
		String slink = "<a href=\"https://plus.google.com";

		if (s.contains(slink)) {
			slink = s.substring(s.indexOf(slink));
			slink = slink.substring(0, slink.indexOf("</a>") + 4);
		} else
			slink = " slink ";

		s = s.replace("&lt;", "<").replace("&gt;", ">");
		s = cut_first(s, "<table", "</table>");
		s = cut_last(s, "<div", "</div>");
		s = cut_last(s, "<div", "</div>");
		s = cut_last(s, "<!--", "-->");

		// String s1[] = s.split("<tbody");
		// int i = s1.length;
		// if (i > 1)
		// s = cut_first(s, "<tbody", "</tbody>");
		String s2[] = s.split("<td");
		int i = s2.length;
		if (i > 1)
			s = cut_first(s, "<td", "</td>");

		s = s.replace("alt=\"картинка не отображается\"", "");
		s = s.replace(">Перейти к записи</a>",
				" target=\"_blank\">Перейти к записи</a>");

		if (s.contains("поделился с вами записью из ленты пользователя"))
			s = cut_first(s, "<table", "</table>");

		
		
		
		if (!s.contains("Перейти к записи")) {
			if (slink.contains(" style=")) {
				slink = slink.substring(0, slink.indexOf(" style="));
				slink = slink
						+ " style=\"background-color:#d44b38;border:solid 1px #dfdfdf;border-radius:3px;color:#fff;display:inline-block;font-family: Arial;font-size:13px;height:30px;line-height:30px;min-width:54px;padding:1px 20px;text-align:center;text-decoration:none;white-space:nowrap;\" target=\"_blank\">Посмотреть на Google+</a>";

			} else
				slink = "<a href=. style=\"background-color:#d44b38;border:solid 1px #dfdfdf;border-radius:3px;color:#fff;display:inline-block;font-family: Arial;font-size:13px;height:30px;line-height:30px;min-width:54px;padding:1px 20px;text-align:center;text-decoration:none;white-space:nowrap;\" target=\"_blank\">Посмотреть в Google+</a>";

			s = s.replace("</table></div></div></div>", "</table>" + slink
					+ "</div></div></div>");
		}

		s = s.replace(
				"<div style=\"margin:20px 0;border-bottom:solid 1px #dfdfdf;width:670px\"></div>",
				"");
		s = s.replace("d44b38", "008DC9");
		s = s.replace("Перейти к записи", "Посмотреть на Google+");

		byte[] data = s.getBytes(Charset.forName("UTF-8"));
		String scoded = Base64Utils.toBase64(data);

		s = Jsoup.parse(s).text();

		s=rem_all_sub(s, "http", " ");
		s=rem_all_sub(s, "//", " ");
		s=rem_all_sub(s, "#", " ");
		s = s.replace("Перейти к записи", "");
		s = s.replace("Посмотреть на Google+", "");
		

		if (s.length() > 55)
			s = " <form action=\"http://ddtor3.appspot.com/mmlink2\" method=\"post\"> "
					+ "<button type=submit name=qq>&nbsp;</button>&nbsp;"
					+ s + "<input type=hidden value=\"" + scoded + "\" name=data>"
					+ "...<button type=submit name=qq>></button></form><hr>";
		else
			s = "qqq_qqq";

		return s;

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

	public static String cut_first(String s, String s1, String s2) {
		int i1 = s.indexOf(s1);
		if (i1 > 0)
			s1 = s.substring(i1);
		int i2 = s1.indexOf(s2) + s2.length();
		if (i1 > 0 && i1 < s.length() && i2 > 0 && i2 < s1.length())
			s = s.substring(0, i1) + s1.substring(i2);
		return s;
	}

	public static String cut_last(String s, String s1, String s2) {
		int i1 = s.lastIndexOf(s1);
		if (i1 > 0)
			s1 = s.substring(i1);
		int i2 = s1.indexOf(s2) + s2.length();
		if (i1 > 0 && i1 < s.length() && i2 > 0 && i2 < s1.length())
			s = s.substring(0, i1) + s1.substring(i2);
		return s;
	}

}