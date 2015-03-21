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

public class mmlink2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html; charset=utf-8");

		
		String subj = "subj";
		if (req.getParameter("subj") != null)
			subj = req.getParameter("subj");

		 
		String sdata = "data null";
		if (req.getParameter("data") != null) {
			sdata = req.getParameter("data");
			byte[] data = Base64Utils.fromBase64(sdata);
			sdata = new String(data, Charset.forName("UTF-8"));
			//smhtm2a(subj, sdata);
		}
 
		
		String sh = req.getScheme() + "://" + req.getServerName() + ":"	+ req.getServerPort() + req.getContextPath();
		String s= stkl.rfu_utf(sh+"/tmpl_mmlink2.html");	
				
				s=s.replace("qqq_sdata_qqq",sdata);
		
		PrintWriter out = resp.getWriter();
		out.write(s);
		out.flush();
		out.close();

		
		//resp.sendRedirect("http://forum.ddtor.com");
		//resp.sendRedirect("https://groups.google.com/forum/embed/?place=forum%2Ffansport&showsearch=true&showtabs=false&parenturl=http%3A%2F%2Fforum.ddtor.com%2F&theme=default#!forum/fansport");
		
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	public static void smhtm2a(String subject, String body){
		Properties props = System.getProperties();

		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);

		
			try {
				message.setFrom(new InternetAddress("admin@ddtor.com", "forum.ddtor.com"));
				
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					"admins"));
			
			subject="Избранные сообщения для просмотра и комментариев " + stkl.get_date_rus() + "года";;
				
			message.setSubject(subject,"UTF-8");
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