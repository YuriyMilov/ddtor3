package dd;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;
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
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class mm2 extends HttpServlet {
	public static String slog = "";
	public static String slog1 = "";
	public static String semailto = "k9992.tverskoy@blogger.com";
	public static String semailfrom = "k999.quicklydone@gmail.com";

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
			String sh = req.getScheme() + "://" + req.getServerName() + ":"
					+ req.getServerPort() + req.getContextPath();
			String s=stkl.rfu_utf(sh+"/news");
			Date dNow = new Date( );
			
			SimpleDateFormat ft =  new SimpleDateFormat ("HH:mm '(Мск)'");
			ft.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
			String subj=ft.format(dNow);
			
			subj="";
			
			if(s.length()>33 && !s.contains("Error")&& !s.contains("Exception:"))
				sm_htm(new String[]{semailto},subj, s);
			
			
			resp.setCharacterEncoding("UTF8"); 
			resp.setContentType("text/html");
			
			PrintWriter out = resp.getWriter();
			out.write(s);
			out.flush();
			out.close();
			
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		doGet(req, resp);
	}


public static void sm_htm(String[] to, String subject, String body) {

	Properties props = new Properties();
	Session session = Session.getDefaultInstance(props, null);
	Message msg = new MimeMessage(session);


    try {
    	try {
			msg.setFrom(new InternetAddress(semailfrom,
					"Kuka Tverskoy"));
		} catch (UnsupportedEncodingException e1) {
			msg.setFrom(new InternetAddress(semailfrom));
		}
        InternetAddress[] toAddress = new InternetAddress[to.length];
        // To get the array of addresses
        for( int i = 0; i < to.length; i++ ) {
            toAddress[i] = new InternetAddress(to[i]);
        }
        for( int i = 0; i < toAddress.length; i++) {
        	msg.addRecipient(Message.RecipientType.TO, toAddress[i]);
        }
        msg.setSubject(subject);
        MimeBodyPart textPart = new MimeBodyPart();
		textPart.setContent(body, "text/html;charset=utf-8");
		Multipart mp = new MimeMultipart();
		mp.addBodyPart(textPart);
		msg.setContent(mp);
		try {
			msg.setSubject(MimeUtility.encodeText(subject, "utf-8", "B"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Transport.send(msg);
        
    }
    catch (AddressException ae) {
        ae.printStackTrace();
    }
    catch (MessagingException me) {
        me.printStackTrace();
    }
}


	public void send_mail(Multipart mp, String sadr, String subject, String sbody, String stxt)
			throws Exception {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(semailfrom,
				"Kuka Tverskoy"));
		msg.setSubject(subject);
		msg.setText("привет опять\r\n");
		//Multipart mp = new MimeMultipart();
		MimeBodyPart textPart = new MimeBodyPart();
		textPart.setContent(sbody, "text/html");
		mp.addBodyPart(textPart);
		msg.setContent(mp);
		Transport.send(msg);
	}
	
	private static final long serialVersionUID = 1L;

}