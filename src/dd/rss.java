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

public class rss extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	String s = stkl.rss;
		
		if (!s.contains("http"))
			s = stkl.fir("rss");
		
		String[] sss = s.trim().split("http");
		s = "http" + sss[stkl.get_random_number(1, sss.length - 1)];
		String s1=s;
		try{
			s=_info.get_rrss(s);
			stkl.mail = s + "\r\n" + stkl.mail;
		}catch(Exception e){
			s="<a href=\""+s1+"\">"+s1 +" </a> : Не могу прочитать RSS :-( ";
			stkl.mail = s + "\r\n" + stkl.mail;
		}
		
		resp.sendRedirect("http://www.ddtor.com/");
	}


}