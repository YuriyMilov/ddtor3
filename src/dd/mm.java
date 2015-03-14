package dd;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.Properties;






//import javax.activation.DataHandler;
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
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.BasicConfigurator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;




public class mm extends HttpServlet {
	public static String slog = "";
	public static String slog1 = "";

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		try {
			BasicConfigurator.configure();
			String sh = req.getScheme() + "://" + req.getServerName() + ":"
					+ req.getServerPort() + req.getContextPath();
			Properties props = new Properties();
			Session session = Session.getDefaultInstance(props, null);
			MimeMessage ms1 = new MimeMessage(session, req.getInputStream());
			Object msgContent = ms1.getContent();
			
			String s = "";
			if (msgContent instanceof Multipart) {
				Multipart multipart = (Multipart) msgContent;
					for (int j = 0; j < multipart.getCount(); j++) {
					BodyPart bodyPart = multipart.getBodyPart(j);
					String disposition = bodyPart.getDisposition();
					
					if (disposition != null
							&& (disposition.equalsIgnoreCase("ATTACHMENT"))) {
						//DataHandler handler = bodyPart.getDataHandler();
					} else {
						s = bodyPart.getContent().toString();
					}
				}
			} else
				s = ms1.getContent().toString();	
	
			//if (Jsoup.parse(s).text().length()>99)
				add_datastore(s);
			
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		doGet(req, resp);
	}


	public static void add_datastore(String s ) {
	//	try {
	//		s = URLDecoder.decode(s, "UTF-8");
	//	} catch (UnsupportedEncodingException e) {
	//		// TODO Auto-generated catch block
	//		e.printStackTrace();
	//	}
		Key kk = KeyFactory.createKey("Guestbook", "guestbookName");
		Date date = new Date();
		Entity greeting = new Entity("Greeting", kk);
		greeting.setProperty("user", "user");
		greeting.setProperty("date", date);
		greeting.setProperty("content", new Text(s));

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		datastore.put(greeting);
	}

public static void sm_htm(String[] to, String subject, String body) {

	Properties props = new Properties();
	Session session = Session.getDefaultInstance(props, null);
	Message msg = new MimeMessage(session);


    try {
    	msg.setFrom(new InternetAddress("admin@ddtor.com"));
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
		msg.setFrom(new InternetAddress("admin@ddtor.com",
				"Kuka"));
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