package dd;

import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.jsoup.Jsoup;

import com.google.gwt.user.server.Base64Utils;

// GAE ddtor3 admin at ddtor quick-0
// github.com/YuriyMilov
// bb.ddtor.com
// bb.feofan.com

public class _info {

	public static String semailto = "k9992.tverskoy@blogger.com";
	public static String semailfrom = "k999.quicklydone@gmail.com";

	public static void main(String[] args) throws Exception {

		String s = "http://novorossia-novosti.com/feed/";
		//s="http://www.kp.ru/rss/msk-politics.xml";
		//s = get_new_rss(s);

	//	s = get_all_new_rss();
		System.out.println(s);
	}

	public static String get_all_new_rss() {

		String s = stkl.get_first(
				stkl.rfu_utf("http://www.ddtor.com/p/blog-page_21.html"),
				"<!-- qqq_rss1_qqq -->", "<!-- qqq_rss2_qqq -->").replace(
				"<br />", "");

		int i = s.indexOf("http");

		if (i < 0)
			return "";

		String[] sss = s.substring(i + 4).split("http");
		s = "";
		for (String ss : sss) {
			s = s + get_new_rss("http" + ss) + "\r\n";
		}
		return s;
	}

	public static String get_new_rss(String s) {
		String s3 = "", s4 = "", tit = "", dat = "", title = "", link = "", description = "";
		try {

			s = stkl.rfu_utf(s);

			int i = s.indexOf("<title>");
			if (i < 0)
				return "";
			tit = s.substring(i + 7);
			i = tit.indexOf("</title>");
			tit = tit.substring(0, i);

			i = s.indexOf("<item>");
			if (i < 0)
				return "";

			String[] sss = s.substring(i + 6).split("<item>");

			for (String s2 : sss) {

				int k = s2.indexOf("<pubDate>");
				if (k > -1)
					dat = s2.substring(k + 9);
				k = dat.indexOf("</pubDate>");
				if (k > -1)
					dat = dat.substring(0, k);

				
				
				
				boolean bb = date_old(dat);

				// System.out.println(bb);

				if (!bb) {
					if (s2.contains("<title>")) {
						i = s2.indexOf("<title>");
						title = s2.substring(i + 7);
					}

					if (title.contains("</title>")) {
						i = title.indexOf("</title>");
						title = title.substring(0, i);
					}

					// System.out.println(" --------->>>>      "+title + " ");

					if (s2.contains("<link>")) {
						i = s2.indexOf("<link>");
						link = s2.substring(i + 6);
						i = link.indexOf("</link>");
						link = link.substring(0, i);
					}

					if (s2.contains("<description>")) {
						i = s2.indexOf("<description>");
						description = s2.substring(i + 13);
						i = description.indexOf("</description>");
						description = description.substring(0, i);

						description = description.replace("<![CDATA[", "");
						description = description.replace("]]>", "");
						description = description.replace("&lt;", "<")
								.replace("&gt;", ">").replace("&quot;", "\"");

						description = Jsoup.parse(description).text();

						if (description.length() > 444)
							description = description.substring(0, 444) + "...";

						i = description.indexOf("Запись ");
						if (i > 0)
							description = description.substring(0, i) + "...";

					}

					s3 = "<div><table><tr><td valign='top'>"
							+ "<br/><a href='"	+ link + "' target='_blank'><img src='http://bb.ddtor.com/rss2.png' /></a></td>"
							+ "<td>&nbsp;</td>"
							+ "<td valign='top'><div style=\"color:#aaaaaa;font-family: Arial;font-size:13px;text-decoration:none;\">"
							+ "<i>"
							+ tit
							+ "</i><br/><a href='"	+ link 
							+ "' style=\"color:#0044bb;font-family: Arial;font-size:14px;text-decoration:none;\" target=\"_blank\"><b>"
							+ title	+ "</b></a></div>"
							+ "<div style=\"color:#222222;font-family: Arial;font-size:13px;\">&nbsp;&nbsp;&nbsp;&nbsp;"
							+ Jsoup.parse(description).text()
							+ "</div></td></tr></table></div><hr/>";

			
					if(!stkl.mail.contains(link))
						s4 = s4 + s3;

				}

			}

		} catch (Exception e) {
		}

		return s4;
	}

	@SuppressWarnings("unused")
	public static boolean date_old(String dt) {
		// dt = "Thu, 19 Mar 2015 19:41:42 GMT";
		// dt = "20 Mar 2015 19:53:19 +0300";
		boolean bb = false;
		Calendar cc = Calendar.getInstance();
		cc.add(Calendar.HOUR, -1);
		Date dc = cc.getTime();
		Date d2 = new Date();
		try {
			d2 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz")
					.parse(dt);
		} catch (ParseException e) {
			try {
				d2 = new SimpleDateFormat("dd MMM yyyy HH:mm:ss zzz").parse(dt);
			} catch (ParseException e1) {
				System.err.println(e1.toString());
			}
		}
		int n = d2.compareTo(dc);
		if (n == -1)
			bb = true;
		return bb;
	}

	public static String get_rrss(String s) {

		s = stkl.rfu_utf(s);
		int i = 0;

		String tit = "", dat = "", title = "", link = "", description = "";

		String[] sss = s.split("<title>");

		try {

			i = s.indexOf("<title>");
			tit = s.substring(i + 7);
			i = tit.indexOf("</title>");
			tit = tit.substring(0, i);

			if (sss.length - 1 > 5)
				i = stkl.get_random_number(2, 7);
			s = sss[i];

			if (s.contains("</title>")) {
				i = s.indexOf("</title>");
				title = s.substring(0, i);
			}

			if (s.contains("<link>")) {
				i = s.indexOf("<link>");
				link = s.substring(i + 6);
				i = link.indexOf("</link>");
				link = link.substring(0, i);
			}

			if (s.contains("<description>")) {
				i = s.indexOf("<description>");
				description = s.substring(i + 13);
				i = description.indexOf("</description>");
				description = description.substring(0, i);

				description = description.replace("<![CDATA[", "");
				description = description.replace("]]>", "");

				description = Jsoup.parse(description).text();

				if (description.length() > 444)
					description = description.substring(0, 444) + "...";

				i = description.indexOf("Запись ");
				if (i > 0)
					description = description.substring(0, i) + "...";

			}

			if (s.contains("<pubDate>")) {
				i = s.indexOf("<pubDate>");
				dat = s.substring(i + 9);
				i = dat.indexOf("</pubDate>");
				dat = dat.substring(0, i);

			}
			s = "<div><table align=\"center\" style=\"width: 100%;\"><tr style=\"width: 555px;\" align=\"center\"><td align=\"left\">"
					+ "<br/><b><a href=\""
					+ link
					+ "\">"
					+ title
					+ "</a></b><br/><br/>"
					+ description
					+ "<br/><br/> <i><a href=\""
					+ link
					+ "\" target=\"_blank\">"
					+ tit
					+ "</a></i> &nbsp;&nbsp; "
					+ dat + "<br/></td></tr></table></div>";

		} catch (Exception e) {
			s = e.toString();
			// stkl.sposled = stkl.sposled + sa + "\r\n" + tit + dat +
			// " (не добавлен - exception)\r\n";
		}

		byte[] data = s.getBytes(Charset.forName("UTF-8"));
		String scoded = Base64Utils.toBase64(data);
		String sfid = String.valueOf(Math.random());

		s = "<a href=\""
				+ link
				+ "\" style=\"color:#005599;font-family: Arial;font-size:13px;text-decoration:none;\" target=\"_blank\"><b>"
				+ title + "</b> <br/>&nbsp;&nbsp;&nbsp;"
				+ Jsoup.parse(description).text() + "</a>";

		String s_button = "<a href=\"javascript: document.getElementById('myform"
				+ sfid
				+ "').submit();\" style=\"background-color:#75A3A3;border:solid 1px #dfdfdf;border-radius:3px;color:#fff;display:inline-block;font-family: Arial;font-size:12px;height:14px;min-width:15px;padding:2px 2px;text-align:center;text-decoration:none;white-space:nowrap;\" target=\"_blank\">"
				+ " " + tit + "&nbsp; </a>";

		String s_end = " <a href=\"javascript: document.getElementById('myform"
				+ sfid
				+ "').submit();\" style=\"color:#005599;font-family: Arial;font-size:13px;text-decoration:none;\" target=\"_blank\">"
				+ s
				+ "</a><form id=\"myform"
				+ sfid
				+ "\" action=\"http://bb.ddtor.com/mmlink2\" method=\"post\"  target=\"_blank\">"
				+ "<input type=hidden value=\"" + scoded
				+ "\" name=data></form> ";

		s = s + "<hr/>";// _button +

		return s;
	}

	@SuppressWarnings("unused")
	public static int compare_date(String s1, String f1, String s2, String f2) {
		s1 = "Thu, 19 Mar 2015 19:41:42 GMT";
		f1 = "EEE, dd MMM yyyy HH:mm:ss zzz";
		s2 = "20 Mar 2015 19:53:19 +0300";
		f2 = "dd MMM yyyy HH:mm:ss zzz";
		int i = 0;
		Date d1 = null, d2 = null;
		try {
			d1 = new SimpleDateFormat(f1).parse(s1);
			d2 = new SimpleDateFormat(f2).parse(s2);

			if (d2.compareTo(d1) == -1)
				i = 1;
			else
				i = -1;
		} catch (ParseException e) {
			System.err.print(e.toString());
			i = 0;
		}

		return i;
	}

	@SuppressWarnings("unused")
	public static boolean date_old2(String dt) {
		// dt = "Thu, 19 Mar 2015 19:41:42 GMT";
		// dt = "20 Mar 2015 19:53:19 +0300";
		boolean bb = false;
		Calendar cc = Calendar.getInstance();
		cc.add(Calendar.HOUR, -1);
		Date dc = cc.getTime();
		Date d2 = new Date();
		try {
			d2 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz")
					.parse(dt);
		} catch (ParseException e) {
			try {
				d2 = new SimpleDateFormat("dd MMM yyyy HH:mm:ss zzz").parse(dt);
			} catch (ParseException e1) {
				System.err.println(e1.toString());
			}
		}
		int n = d2.compareTo(dc);
		if (n == -1)
			bb = true;
		return bb;
	}

	public static String get_date_rus2(String dt) {

		Date dd = new Date();
		try {
			dd = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz")
					.parse(dt);
		} catch (ParseException e) {
			try {
				dd = new SimpleDateFormat("dd MMM yyyy HH:mm:ss zzz").parse(dt);
			} catch (ParseException e1) {
				System.err.println(e1.toString());
			}
		}
		TimeZone tz = TimeZone.getTimeZone("Europe/Moscow");
		Calendar cc = Calendar.getInstance(tz);
		cc.setTime(dd);
		cc.add(Calendar.HOUR, -1);

		String s_hh = String.valueOf(cc.get(Calendar.HOUR_OF_DAY));
		String s_mm = String.valueOf(String.format("%02d",
				cc.get(Calendar.MINUTE)));
		String s_dofm = String.valueOf(cc.get(Calendar.DAY_OF_MONTH));
		String s_dow = String.valueOf(cc.get(Calendar.DAY_OF_WEEK))
				.replace("1", "воскресенье").replace("2", "понедельник")
				.replace("3", "вторник").replace("4", "среда")
				.replace("5", "четверг").replace("6", "пятница")
				.replace("7", "суббота");

		String s = String.valueOf(cc.get(Calendar.MONTH));
		s = s.replace("0", " января ").replace("1", " февраля ")
				.replace("2", " марта ").replace("3", " апреля ")
				.replace("4", " мая ").replace("5", " июня ")
				.replace("6", " июня ").replace("7", " августа ")
				.replace("8", " сентября ").replace("9", " октября ")
				.replace("10", " ноября ").replace("11", " декабря ");

		s = "в Москве " + s_hh + ":" + s_mm + " " + s_dow + " " + s_dofm + s;

		return s;
	}

	public static String get_date_rus3() {

		Date dd = new Date();

		TimeZone tz = TimeZone.getTimeZone("Europe/Moscow");
		Calendar cc = Calendar.getInstance(tz);
		cc.setTime(dd);
		cc.add(Calendar.HOUR, -1);

		String s_hh = String.valueOf(cc.get(Calendar.HOUR_OF_DAY));
		String s_mm = String.valueOf(String.format("%02d",
				cc.get(Calendar.MINUTE)));
		String s_dofm = String.valueOf(cc.get(Calendar.DAY_OF_MONTH));
		String s_dow = String.valueOf(cc.get(Calendar.DAY_OF_WEEK))
				.replace("1", "воскресенье").replace("2", "понедельник")
				.replace("3", "вторник").replace("4", "среда")
				.replace("5", "четверг").replace("6", "пятница")
				.replace("7", "суббота");

		String s = String.valueOf(cc.get(Calendar.MONTH));
		s = s.replace("10", " ноября ").replace("11", " декабря ")
				.replace("0", " января ").replace("1", " февраля ")
				.replace("2", " марта ").replace("3", " апреля ")
				.replace("4", " мая ").replace("5", " июня ")
				.replace("6", " июля ").replace("7", " августа ")
				.replace("8", " сентября ").replace("9", " октября ");

		s = s_hh + ":" + s_mm + " " + s_dow + " " + s_dofm + s;

		return s;
	}

	public static String get_pix(String url, String tit){
		
		//url="http://novorossia-novosti.com/kontakt-mejdy-opolcheniem-dnr-i-vsy-pod-shirokino-ystanovlen/";
		//tit="Контакт между ополчением ДНР и ВСУ под Широкино установлен";
		
	
		String s=stkl.rfu_utf(url);
		s=s.substring(s.indexOf(tit));
		s=s.substring(s.indexOf("<img"));
		s=s.substring(0,s.indexOf(">")+1);
		
		System.out.println(s);
		
		return s;
		}
}
