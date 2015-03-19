package dd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;



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

public class ddf extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		doPost(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String s = req.getParameter("qq");
		if (s.equals("1")) {
			s = req.getParameter("text");
			s = "text=" + s + "&solo=owlxml";
			s = get_ape("http://attempto.ifi.uzh.ch/ws/ape/apews.perl?", s);
			s = get_ape(
					"http://attempto.ifi.uzh.ch/service/owl_verbalizer/owl_to_ace",
					"xml=" + s + "&format=ace");	
		
		}
		if (s.equals("2")) {
			s = req.getParameter("text");
			s=stkl.rfu_utf(s);
			
			
s="<html>Converter <form action=\"http://mowl-power.cs.man.ac.uk:8080/converter/convert\" method=post>" 
		+"<pre><textarea rows=\"44\" cols=\"111\" name=\"ontology\">"+s+"</textarea></pre>"
		+"<input type=hidden name=\"format\" value=\"OWL/XML\"/><br/>"
		+ "<input type='submit' value='Convert'/></form><html>";		
		}
		
		
		//s="<html><input type=button onClick=\"location.href='/Ddtor3.html'\" value='Back'><br/><pre>"+s + "</pre><br/><br/></html>";
		
		PrintWriter out = resp.getWriter();
		out.write(s);
		out.flush();
		out.close();
	}

	public String get_ape(String location, String s) {

		String charEncoding = "iso-8859-1";

		try {
			HttpURLConnection conn;
			conn = (HttpURLConnection) new URL(location).openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Length",
			Integer.toString(s.length()));
			conn.getOutputStream().write(s.getBytes(charEncoding));
			conn.connect();
			conn.getResponseCode();
			InputStream is = conn.getInputStream();
			s = get_is(is);

		} catch (Exception e) {
			s = e.toString();
		}
		return s;
	}
	
	public static String post(String cc, String s){
		
	     try {
	         // Construct data
	         //s = URLEncoder.encode("fName", "UTF-8") + "=" + URLEncoder.encode("value1", "UTF-8");
	         //s += "&" + URLEncoder.encode("lName", "UTF-8") + "=" + URLEncoder.encode("value2", "UTF-8");
	  
	         // Send data
	         URL url = new URL(cc);
	         URLConnection conn = url.openConnection();
	         conn.setDoOutput(true);
	         OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	         wr.write(s);
	         wr.flush();
	  
	         // Get the response
	         BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	         String line;
	         while ((line = rd.readLine()) != null) {
	             s=s+line+"\r\n";
	         }
	         wr.close();
	         rd.close();
	     } catch (Exception e) {
	    	 s=e.toString();
	     }
	     
	     return s;
		
		
	};

	static String get_is(InputStream is) throws Exception {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		while ((i = is.read()) != -1)
			sb.append((char) i);
		return sb.toString();
	}
	
    public static String rfu(String url) {
        StringBuffer s = new StringBuffer();
        try {
                URL u = new URL(url);
                InputStream in = u.openConnection().getInputStream();
                for (int ch = in.read(); ch > 0; ch = in.read()) {
                        s.append((char) ch);
                }
                in.close();
        } catch (IOException e) {
                return e.toString();
        }
        return s.toString();
}
    
}
