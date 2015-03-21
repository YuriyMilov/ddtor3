package dd;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Properties;

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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gwt.user.server.Base64Utils;

public class mmnov extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html; charset=utf-8");

		String sh = req.getScheme() + "://" + req.getServerName() + ":"
				+ req.getServerPort() + req.getContextPath(), s = stkl
				.rfu_utf(sh + "/tmpl_mmnov.html"), subject = "Предложен RSS";
		
		
		String ss = req.getParameter("rss");
		if (ss == null)
			ss = "";
		
		stkl.rss_list = stkl.fir("nov_rss");



		if (!stkl.rss_list.contains(ss)) {

			stkl.rss_list = stkl.rss_list + " " + ss;
			stkl.fiw("nov_rss", stkl.rss_list);
		
		ss = "<div style=\"align:left\"><br/> <br/> <br/> "
					+ "Предложен адрес:  <a href=\""
					+ ss
					+ "\"><b>"
					+ ss
					+ "</b></a>"
					+ " <br><br>Новости из этого источника будут добавлены в ленту ddtor.com <br/> "
					+ "Детали и вопросы можно обсудить на форуме<div>";

		} else {
			ss = "<div style=\"align:left\"><br/> <br/> <br/> "
					+ "Предложен адрес:  <a href=\""
					+ ss
					+ "\"><b>"
					+ ss
					+ "</b></a>"
					+ " <br><br/> Этот источник уже был ранее добавлен в ленту ddtor.com <br/> "
					+ "Вопросы можно обсудить на форуме<div>";
		}

		s = s.replace("qqq_sdata_qqq", ss);
		smhtm2a(subject, s);

		PrintWriter out = resp.getWriter();
		out.write(s);
		out.flush();
		out.close();

	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	public static void smhtm2a(String subject, String body) {
		Properties props = System.getProperties();

		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress("admin@ddtor.com",
					"forum.ddtor.com"));

			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					"admins"));

			message.setSubject(subject, "UTF-8");
			MimeBodyPart textPart = new MimeBodyPart();
			textPart.setContent(body, "text/html;charset=utf-8");
			Multipart mp = new MimeMultipart();
			mp.addBodyPart(textPart);
			message.setContent(mp);
			Transport.send(message);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void sm2a(String subject, String body) {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		Message msg = new MimeMessage(session);

		try {
			msg.setFrom(new InternetAddress("admin@ddtor.com", "Админ"));
			// msg.setFrom(new InternetAddress("ymilov@gmail.com",
			// "ddtor admin"));
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

}