package dd;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.files.AppEngineFile;
import com.google.appengine.api.files.FileReadChannel;
import com.google.appengine.api.files.FileService;
import com.google.appengine.api.files.FileServiceFactory;
import com.google.appengine.api.files.FileWriteChannel;
import com.google.appengine.api.blobstore.*;



public class mail extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String sh = req.getScheme() + "://" + req.getServerName() + ":"	+ req.getServerPort() + req.getContextPath();

	
		String s= new Date().toString();
		stkl.sm2a("test "+s, s);
		//send_admin("send_admin", s);
		
		resp.setCharacterEncoding("UTF8"); 
		resp.setContentType("text/html");
		
		PrintWriter out = resp.getWriter();
		out.write(s);
		out.flush();
		out.close();
		
	}
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String s = req.getParameter("psw");
		String sh = req.getScheme() + "://" + req.getServerName() + ":"
				+ req.getServerPort() + req.getContextPath();
		String stxt = req.getParameter("txt");
		String s3 = "";
		
		if(s!=null)
		if(s.equals("4444") && stxt==null)
			{
			s= stkl.rfu_utf(sh+"/edit.html");
			s3=stkl.blob_r("1.txt");	
			s=s.replace("<!-- qq -->", s3);		
			}
		else
			s= stkl.rfu_utf(sh+"/edit.html");
			
			//s=stkl.posti(sh+"/w2f","w2f_null_query", " Query String - null");
			//s = stkl.rff(s);
		
		
		if(stxt!=null)
			if(stxt.trim().length()>0)
			{	
			
				s3=stkl.blob_w("1.txt",stxt);
				stkl.sm2a("Personal Demons", stxt);
				
				s= stkl.rfu_utf(sh+"/edit.html");
				s=s.replace("<!-- qq -->", stxt);	
			}
		
		
		resp.setCharacterEncoding("UTF8");
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.write(s);
		out.flush();
		out.close();
	}
	
	private void send_admin(String subject, String body)
			 {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		Message msg = new MimeMessage(session);
		// msg.setFrom(new InternetAddress("ymilov@gmail.com",
		// "UFOS Daily Activity"));

		try {
			msg.setFrom(new InternetAddress("ymilov@gmail.com",
					"ddtor admin"));
		

		msg.addRecipient(Message.RecipientType.TO,
				new InternetAddress("admins"));
		msg.setSubject(subject);
		msg.setText(body);
/*
		Multipart mp = new MimeMultipart();

		MimeBodyPart textPart = new MimeBodyPart();

		// textPart.setContent(body, "text/html");

		// textPart.setContent("<b>UFOS</b> <i>Daily Activity Report</i> attached",
		// "text/html");
		textPart.setContent(body, "text/html");

		mp.addBodyPart(textPart);

		MimeBodyPart attachment = new MimeBodyPart();
		String fileName = "UFOS_Daily.txt";
		String filename = URLEncoder.encode(fileName, "UTF-8");
		attachment.setFileName(filename);
		attachment.setDisposition(Part.ATTACHMENT);

		// DataSource src = new ByteArrayDataSource(spreadSheetData,
		// "application/x-ms-excel");

		// DataSource src = new ByteArrayDataSource(body.getBytes(),
		// "plain/text");
		DataSource src = new ByteArrayDataSource(txt.getBytes(), "text/html");

		DataHandler handler = new DataHandler(src);
		attachment.setDataHandler(handler);
		mp.addBodyPart(attachment);

		msg.setContent(mp);
*/
		// msg.setSubject(String.format(subj));

		// Transport.send(msg);

		Transport.send(msg);
		} catch (Exception e) {
		
			e.printStackTrace();
		}
	}

	
}
