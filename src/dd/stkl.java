package dd;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.channels.Channels;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;

import javax.mail.internet.MimeUtility;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.files.AppEngineFile;
import com.google.appengine.api.files.FileService;
import com.google.appengine.api.files.FileServiceFactory;
import com.google.appengine.api.files.FileWriteChannel;

import java.nio.charset.Charset;

public class stkl {

	// String sh = req.getScheme() + "://" + req.getServerName() + ":"+
	// req.getServerPort() + req.getContextPath();
	public static int k = 0;
	public static int n_sec = 15;
	public static boolean updated = false;
	public static String sts = "";
	public static int nrt = 0;
	public static ArrayList<String> ar_rt = new ArrayList<String>();
	public static ArrayList<String> ar_tass = new ArrayList<String>();
	public static String rss = "";
	public static String rss_last = "";
	public static String rss_list = "";
	//public static String body = "";
	public static String mail = "";

	private final static Charset UTF8_CHARSET = Charset.forName("UTF-8");

	public static String get_2news() {

		return stkl.mail;
	}



	public static String get_hr2(String s) {
		int i = s.indexOf("<hr/>") + 5;
		if (i > 4)
			s = s.substring(0, i);

		s = s.replace("<b>", "<h4>").replace("</b>", "</h4>")
				.replace("<br/>", "");
		return s;
	}

	public static String get_hr(String s) {
		int i = s.indexOf("<hr/>") + 5;
		if (i > 4)
			s = s.substring(0, i);
		i = Jsoup.parse(s).text().length();
		if (i < 255)
			s = s + get_ad();
		return s;
	}

	public static String add_ad(String s, int i) {

		if (s.length() < i) {
			double dd = Math.random();
			if (dd < 0.33)
				s = "<a href='http://humor.ddtor.com/' ><b> Кляк! - И мы уже расслабляемся и отдыхаем от новостей :) </b></a>";
			else if (dd > 0.3 && dd < 0.66)
				s = "<a href='http://polit.ddtor.com/' ><b> Кляк! - И вы уже смотhите новости в картинках</b></a>";
			else
				s = "<a href='http://forum.ddtor.com/' ><b> Кляк! - И мы уже общаемся на форуме</b></a>";

			s = s.replace(
					"><b>",
					"style=\"color:#005599;font-family: Arial;font-size:13px;text-decoration:none;\" target='_blank'><b>");
		} else
			s = "";
		return s;
	}

	public static String get_ad() {

		String s = "";
		double dd = Math.random();
		if (dd < 0.33)
			s = "<a href='http://humor.ddtor.com/' ><b> Кляк! - И мы уже расслабляемся и отдыхаем от новостей :) </b></a>";
		else if (dd > 0.3 && dd < 0.66)
			s = "<a href='http://polit.ddtor.com/' ><b> Кляк! - И вы уже смотhите новости в картинках</b></a>";
		else
			s = "<a href='http://forum.ddtor.com/' ><b> Кляк! - И мы уже общаемся на форуме</b></a>";

		s = s.replace(
				"><b>",
				"style=\"color:#005599;font-family: Arial;font-size:13px;text-decoration:none;\" target='_blank'><b>");

		return s;
	}

	public static String get_first(String s, String s1, String s2) {
		int i1 = s.indexOf(s1);
		if (i1 > 0)
			s = s.substring(i1);

		int i2 = s.indexOf(s2) + s2.length();
		if (i2 > 0)
			s = s.substring(0, i2);

		s = Jsoup.parse(s).text();

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
				if (i > -1)
					s = s + ss.substring(i + s2.length());
			}
		}
		return s3 + s;
	}

	public static String fir(String sfilename) {
		String sid = "Files", skk = "files", s = "", sfn = "";
		DatastoreService dbf = DatastoreServiceFactory.getDatastoreService();
		Key key = KeyFactory.createKey(sid, skk);
		Query query = new Query(sid, key).addSort("date",
				Query.SortDirection.DESCENDING);
		List<Entity> spisok = dbf.prepare(query).asList(
				FetchOptions.Builder.withLimit(10));
		for (Entity ent : spisok) {
			sfn = ((String) ent.getProperty("file_name")).trim();

			if (sfn.equals(sfilename))
				s = ((Text) ent.getProperty("content")).getValue();
		}
		return s;
	}

	public static void fiw(String sfilename, String s) {
		String sid = "Files", skk = "files";

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Key key = KeyFactory.createKey(sid, skk);
		Query query = new Query(sid, key).addSort("date",
				Query.SortDirection.DESCENDING);
		List<Entity> spisok = datastore.prepare(query).asList(
				FetchOptions.Builder.withLimit(10));
		for (Entity ent : spisok) {
			if (((String) ent.getProperty("file_name")).trim()
					.equals(sfilename))
				datastore.delete(ent.getKey());
		}

		Entity ent = new Entity(sid, key);
		ent.setProperty("file_name", sfilename);
		ent.setProperty("content", new Text(s));
		ent.setProperty("date", new Date());

		datastore.put(ent);
	}

	public static void f1w(String s) {
		String sid = "File1", skk = "file1";

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Key key = KeyFactory.createKey(sid, skk);
		Query query = new Query(sid, key).addSort("date",
				Query.SortDirection.DESCENDING);
		List<Entity> greetings = datastore.prepare(query).asList(
				FetchOptions.Builder.withLimit(10));
		if (greetings != null)
			for (Entity greeting : greetings) {
				datastore.delete(greeting.getKey());
			}

		Entity ent = new Entity(sid, key);
		ent.setProperty("content", new Text(s));
		ent.setProperty("date", new Date());

		datastore.put(ent);
	}

	public static String f1r() {
		String sid = "File1", skk = "file1", s = "";
		DatastoreService dbf = DatastoreServiceFactory.getDatastoreService();
		Key key = KeyFactory.createKey(sid, skk);
		Query query = new Query(sid, key).addSort("date",
				Query.SortDirection.DESCENDING);
		List<Entity> spisok = dbf.prepare(query).asList(
				FetchOptions.Builder.withLimit(1));
		if (spisok.size() > 0)
			s = ((Text) spisok.get(0).getProperty("content")).getValue();
		return s;
	}

	public static boolean ndp(String pub_date, String db_upd) {

		boolean bb = true;
		Date d1 = null, d2 = null;
		try {
			d1 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz")
					.parse(pub_date);
			d2 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz")
					.parse(db_upd);

			if (d2.compareTo(d1) == -1)
				bb = true;
			else
				bb = false;
		} catch (ParseException e) {
			System.err.print(e.toString());
			bb = false;
		}
		return bb;

	}

	public static void upd(String source, String name) {
		Key kk = KeyFactory.createKey("upd", "blog_updated");
		Date date = new Date();
		Entity enot = new Entity("UPD", kk);
		enot.setProperty("date", date);
		// enot.setProperty("text", new Text(s));
		enot.setProperty("source", source);
		enot.setProperty("name", name);
		// enot.setProperty("updated", updated);

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		datastore.put(enot);
	}

	public static String clean_htm(String s) {
		s = s.replace("&amp;", "&").replace("&lt;", "<").replace("&gt;", ">")
				.replace("&quot;", "\"");
		s = s.replace("#036;", "$");
		return s;
	}

	public static int get_random_number(int i, int k) {

		Random rand = new Random();
		k = i + rand.nextInt(k - i + 1);
		return k;
	}

	public static String get_random_color() {
		String s = "", ss = "";
		int i = 0, k = 0, k1 = 0, k2 = 0, k3 = 0;
		while (i++ < 6) {
			k1 = (int) (Math.random() * 2);

			if (k1 != 0)
				k1 = 1;
			k2 = (int) (Math.random() * 15);
			k = k1 * k2;
			s = String.valueOf(k);

			s = s.replace("10", "A").replace("11", "B").replace("12", "C")
					.replace("13", "D").replace("14", "E").replace("15", "F");
			ss = ss + s;
		}

		return ss;
	}

	public static String decodeUTF8(byte[] bytes) {
		return new String(bytes, UTF8_CHARSET);
	}

	public static byte[] encodeUTF8(String string) {
		return string.getBytes(UTF8_CHARSET);
	}

	public static String rfu_utf(String s) {
		try {
			URL url = new URL(s);

			URLConnection conn = url.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "utf8"));
			s = "";
			String thisLine = "";
			while ((thisLine = br.readLine()) != null) { // while loop begins
															// here
				s = s + thisLine + "\r\n";
			}
			br.close();
			return s.toString();

		} catch (Exception e) {
			return e.toString();
		}
	}

	public static String get_post(String surl, String body) {
		String s = "";

		try {

			URL url = new URL(surl);
			URLConnection urlConnection = url.openConnection();
			DataOutputStream outStream;

			// Build request body
			// Create connection

			((HttpURLConnection) urlConnection).setRequestMethod("POST");
			urlConnection.setConnectTimeout(0);
			urlConnection.setReadTimeout(0);
			urlConnection.setDoInput(true);
			urlConnection.setDoOutput(true);
			urlConnection.setUseCaches(false);
			urlConnection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			urlConnection.setRequestProperty("Content-Length",
					"" + body.length());

			// Create I/O streams
			outStream = new DataOutputStream(urlConnection.getOutputStream());

			// Send request
			outStream.writeBytes(body);
			outStream.flush();
			outStream.close();

			// Get Response

			BufferedReader br = new BufferedReader(new InputStreamReader(
					urlConnection.getInputStream(), "utf8"));
			s = "";
			String thisLine = "";
			while ((thisLine = br.readLine()) != null) {
				s = s + thisLine + "\r\n";
			}
			br.close();

		} catch (Exception ex) {
			s = ex.toString();
		}
		return s;
	}

	public static String send_mail(String from_name, String from_address,
			String to_name, String to_address, String subj, String body) {
		String s = "ok";
		try {
			Properties props = new Properties();
			Session session = Session.getDefaultInstance(props, null);
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from_address, MimeUtility
					.encodeText(from_name, "utf-8", "B")));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
					to_address, MimeUtility.encodeText(to_name, "utf-8", "B")));
			msg.setSubject(MimeUtility.encodeText(
					MimeUtility.encodeText(subj, "utf-8", "B"), "utf-8", "B"));

			MimeBodyPart textPart = new MimeBodyPart();
			textPart.setContent(body, "text/html;charset=utf-8");
			Multipart mp = new MimeMultipart();
			mp.addBodyPart(textPart);
			msg.setContent(mp);
			Transport.send(msg);

		} catch (Exception e) {
			s = e.toString();
		}
		return s;
	}

	public static String sm2a(String from_name, String from_address,
			String subject, String body) {
		String s = "ok";
		try {
			Properties props = new Properties();
			Session session = Session.getDefaultInstance(props, null);
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from_address, MimeUtility
					.encodeText(from_name, "utf-8", "B")));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
					"admins"));
			msg.setSubject(MimeUtility.encodeText(subject, "utf-8", "B"));
			msg.setText(body);

			Multipart mp = new MimeMultipart();
			MimeBodyPart textPart = new MimeBodyPart();
			textPart.setContent(body, "text/html;charset=utf-8");
			// textPart.setContent(body, "text/html");
			mp.addBodyPart(textPart);
			Transport.send(msg);
		} catch (Exception e) {
			s = e.toString();
		}
		return s;
	}

	public static void sm_htm(String[] to, String subject, String body) {
		Properties props = System.getProperties();

		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress("admin@ddtor.com"));
			InternetAddress[] toAddress = new InternetAddress[to.length];
			// To get the array of addresses
			for (int i = 0; i < to.length; i++) {
				toAddress[i] = new InternetAddress(to[i]);
			}
			for (int i = 0; i < toAddress.length; i++) {
				message.addRecipient(Message.RecipientType.TO, toAddress[i]);
			}
			message.setSubject(subject);
			MimeBodyPart textPart = new MimeBodyPart();
			textPart.setContent(body, "text/html;charset=utf-8");
			Multipart mp = new MimeMultipart();
			mp.addBodyPart(textPart);
			message.setContent(mp);
			Transport.send(message);

		} catch (AddressException ae) {
			ae.printStackTrace();
		} catch (MessagingException me) {
			me.printStackTrace();
		}
	}

	static void smtxt(String s) throws Exception {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress("admin@ddtor.com", "Example.com Admin"));
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
				"admin@ddtor.com", "Mr. User"));
		msg.setSubject("Your Example.com account has been activated");
		msg.setText("msgBody");

		// Create the message part
		BodyPart messageBodyPart = new MimeBodyPart();

		// Fill the message
		messageBodyPart.setText(s);

		// Create a multipar message
		Multipart multipart = new MimeMultipart();

		// Set text message part
		multipart.addBodyPart(messageBodyPart);

		// Part two is attachment
		messageBodyPart = new MimeBodyPart();
		String filename = "file.txt";

		DataSource source = new ByteArrayDataSource(
				"xmlData".getBytes("utf-8"), "text/xml; charset=utf-8");
		messageBodyPart.setDataHandler(new DataHandler(source));
		messageBodyPart.setFileName(filename);
		messageBodyPart.addHeader("Content-Type", " text/xml; charset=utf-8");
		multipart.addBodyPart(messageBodyPart);

		// Send the complete message parts
		msg.setContent(multipart);

		Transport.send(msg);
	}

	public static void send_file(HttpServletRequest req,
			HttpServletResponse resp, String s) throws IOException {
		ServletOutputStream out = resp.getOutputStream();
		resp.setContentType("text/xml");
		resp.setHeader("Content-Disposition", "attachment; filename=owl.xml");
		byte[] b = s.getBytes("UTF8");
		out.write(b);
	}

	public static String posti(String surl, String sname, String scontent) {

		try {
			// Encode the query
			String postData = "f=" + URLEncoder.encode(sname, "UTF-8") + "&s="
					+ URLEncoder.encode(scontent, "UTF-8");

			URL url = new URL(surl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			connection.setRequestProperty("Content-Length",
					String.valueOf(postData.length()));

			// Write data
			OutputStream os = connection.getOutputStream();
			os.write(postData.getBytes());

			// Read response
			StringBuilder responseSB = new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			String line;
			while ((line = br.readLine()) != null)
				responseSB.append(line);

			// Close streams
			br.close();
			os.close();

			return responseSB.toString();
		} catch (Exception e) {
			return e.toString();
		}
	}

	public static String get_is(InputStream is) throws Exception {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		while ((i = is.read()) != -1)
			sb.append((char) i);
		return sb.toString();
	}

	public static boolean n(String str) {
		for (char c : str.toCharArray()) {
			if (!Character.isDigit(c))
				return false;
		}
		return true;
	}

	public static void sm2a(String subject, String body) {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		Message msg = new MimeMessage(session);

		try {
			msg.setFrom(new InternetAddress("admin@ddtor.com", "Admin"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
					"admins"));
			msg.setSubject(subject);
			msg.setText(body);

			Multipart mp = new MimeMultipart();
			MimeBodyPart textPart = new MimeBodyPart();
			textPart.setContent(body, "text/html;charset=utf-8");
			// textPart.setContent(body, "text/html");
			mp.addBodyPart(textPart);
			Transport.send(msg);
		} catch (Exception e) {
		}
	}

	public static void blob_clear() throws IOException {
		BlobInfoFactory blf = new BlobInfoFactory();
		Iterator<BlobInfo> info = blf.queryBlobInfos();
		BlobInfo bi = null;

		while (info.hasNext()) {
			bi = info.next();
			BlobstoreFS.delete(bi.getBlobKey());
		}
	}

	public static String blob_r(String s) {

		try {
			Query query = new Query("__BlobInfo__");
			query.setFilter(FilterOperator.EQUAL.of("filename", s));

			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			PreparedQuery pq = datastore.prepare(query);
			List<Entity> entList = pq.asQueryResultList(FetchOptions.Builder
					.withLimit(10));
			if (entList.size() > 0) {
				s = entList.get(0).getKey().getName();
				s = BlobstoreFS.readToEnd(new BlobKey(s));
			}
		} catch (Exception ee) {
			s = ee.toString();
		}

		return s;
	}

	public static String blob_w(String sname, String s) {

		try {
			// blob_clear();
			Query query = new Query("__BlobInfo__");
			query.setFilter(FilterOperator.EQUAL.of("filename", sname));

			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			PreparedQuery pq = datastore.prepare(query);
			List<Entity> entList = pq.asQueryResultList(FetchOptions.Builder
					.withLimit(10));

			if (!entList.isEmpty())
				BlobstoreFS.delete(new BlobKey(entList.get(0).getKey()
						.getName()));

			FileService fileService = FileServiceFactory.getFileService();
			AppEngineFile file = fileService.createNewBlobFile("text/plain",
					sname);
			boolean lock = false;
			FileWriteChannel writeChannel = fileService.openWriteChannel(file,
					lock);
			PrintWriter out = new PrintWriter(Channels.newWriter(writeChannel,
					"UTF8"));
			out.println(s);
			out.close();
			file = new AppEngineFile(file.getFullPath());
			lock = true;
			writeChannel = fileService.openWriteChannel(file, lock);
			writeChannel.closeFinally();

		} catch (Exception e) {
			return e.toString();
		}
		return "ok";
	}

	public static void sendFromGMail(String from, String pass, String[] to,
			String subject, String body) {
		Properties props = System.getProperties();
		String host = "smtp.gmail.com";
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.password", pass);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress(from));
			InternetAddress[] toAddress = new InternetAddress[to.length];

			// To get the array of addresses
			for (int i = 0; i < to.length; i++) {
				toAddress[i] = new InternetAddress(to[i]);
			}

			for (int i = 0; i < toAddress.length; i++) {
				message.addRecipient(Message.RecipientType.TO, toAddress[i]);
			}

			message.setSubject(subject);
			message.setText(body);
			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, pass);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (AddressException ae) {
			ae.printStackTrace();
		} catch (MessagingException me) {
			me.printStackTrace();
		}
	}

	public static void sendFromGMail5(String from, String pass, String[] to,
			String subject, String body) {
		Properties props = System.getProperties();
		String host = "smtp.gmail.com";
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.password", pass);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress(from));
			InternetAddress[] toAddress = new InternetAddress[to.length];

			// To get the array of addresses
			for (int i = 0; i < to.length; i++) {
				toAddress[i] = new InternetAddress(to[i]);
			}

			for (int i = 0; i < toAddress.length; i++) {
				message.addRecipient(Message.RecipientType.TO, toAddress[i]);
			}

			message.setSubject(subject);
			message.setText(body);
			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, pass);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (AddressException ae) {
			ae.printStackTrace();
		} catch (MessagingException me) {
			me.printStackTrace();
		}
	}

	public static void sendFromGMail_html(String from, String pass,
			String[] to, String subject, String body) {
		Properties props = System.getProperties();
		String host = "smtp.gmail.com";
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.password", pass);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress(from));
			InternetAddress[] toAddress = new InternetAddress[to.length];
			// To get the array of addresses
			for (int i = 0; i < to.length; i++) {
				toAddress[i] = new InternetAddress(to[i]);
			}
			for (int i = 0; i < toAddress.length; i++) {
				message.addRecipient(Message.RecipientType.TO, toAddress[i]);
			}
			message.setSubject(subject);
			MimeBodyPart textPart = new MimeBodyPart();
			textPart.setContent(body, "text/html;charset=utf-8");
			Multipart mp = new MimeMultipart();
			mp.addBodyPart(textPart);
			message.setContent(mp);
			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, pass);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();

		} catch (AddressException ae) {
			ae.printStackTrace();
		} catch (MessagingException me) {
			me.printStackTrace();
		}
	}

	public static void add_datastore(String s) {
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

	public static String get_datastore() {
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

	public static byte[] get_datastore_b() throws UnsupportedEncodingException {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Key guestbookKey = KeyFactory.createKey("Guestbook", "guestbookName");

		Query query = new Query("Greeting", guestbookKey);// .addSort("date",
		// Query.SortDirection.ASCENDING);
		List<Entity> greetings = datastore.prepare(query).asList(
				FetchOptions.Builder.withLimit(1));

		// s = String.valueOf(greetings.size()) + "";
		String s = "";
		byte[] bb = null;
		for (Entity greeting : greetings) {

			bb = ((Text) greeting.getProperty("content")).getValue().getBytes(
					"utf8");

		}
		// greetings.remove(0);

		return bb;
	}

	public static String get_date_rus() {
		Date dd = new Date();	
		TimeZone tz = TimeZone.getTimeZone("Europe/Moscow");
		Calendar cc = Calendar.getInstance(tz);
		cc.setTime(dd);
		cc.add(Calendar.HOUR, -1);
		
		String s_hh = String.valueOf(cc.get(Calendar.HOUR_OF_DAY));
		String s_mm = String.valueOf(String.format("%02d",cc.get(Calendar.MINUTE)));
		String s_dofm = String.valueOf(cc.get(Calendar.DAY_OF_MONTH));
		String s_dow = String.valueOf(cc.get(Calendar.DAY_OF_WEEK)).replace("1", "воскресенье").replace("2", "понедельник").replace("3", "вторник").replace("4", "среда").replace("5", "четверг").replace("6", "пятница").replace("7", "суббота");
		
		String s = String.valueOf(cc.MONTH);		
		s = s.replace("0", " января ").replace("1", " февраля ")
				.replace("2", " марта ").replace("3", " апреля ")
				.replace("4", " мая ").replace("5", " июня ")
				.replace("6", " июля ").replace("7", " августа ")
				.replace("8", " сентября ").replace("9", " октября ")
				.replace("10", " ноября ").replace("11", " декабря ");

			s = "в Москве " +s_hh + ":"+ s_mm  + " "+s_dow + " "+ s_dofm + s;

		return s;
	}

}