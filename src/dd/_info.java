package dd;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.api.client.http.HttpResponse;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;
import com.google.gwt.user.server.Base64Utils;

public class _info {
	// GAE ddtor3 admin at ddtor quick-0
	// github.com/YuriyMilov
	
	public static String semailto = "k9992.tverskoy@blogger.com";
	public static String semailfrom = "k999.quicklydone@gmail.com";
	public static String blobkey = "";

	public static void main(String[] args) throws Exception {
		
		/*
		 * Create the POST request
		 */
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost("http://example.com/");
		// Request parameters and other properties.
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("user", "Bob"));
	
		    httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		
		/*
		 * Execute the HTTP Request
		 */
		
		    org.apache.http.HttpResponse response = httpClient.execute(httpPost);
		    HttpEntity respEntity = response.getEntity();

		    if (respEntity != null) {
		        // EntityUtils to get the response content
		        String content =  EntityUtils.toString(respEntity);
		    }
		

		String s = "<p> <span> foo </span> <em> bar <a> foobar </a> baz </em> </p>";
		// s=stkl.rfu_utf("http://www.ddtor.com");
		// s = Jsoup.parse(s).text();
		// s = "на " + stkl.get_date() + "года";
//s="Mon, 16 Mar 2015 18:20:39 +0300";
		//s="http://news.yandex.ru/politics.rss";
		//s=get_rss(s,5,"qqqqqqqqq");
		s= "Tue, 17 Mar 2015 23:37:29 +0300";
		SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd"); 
	 
		System.out.println(s);
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

	public static String get_rss(String s, int j, String name) {
		String source=s;
		String sid="Text";
		
		s = stkl.rfu_utf(s);		
		int i = 0,k=0;
		
		String sa="", tit="", dat="", ss="", title = "", link = "", description = "";

		try {

			if (s.contains("<title>")) {

				i = s.indexOf("<title>");
				s = s.substring(i + 7);
				i = s.indexOf("</title>");
				tit = s.substring(0, i);

				i = s.indexOf("<pubDate>");
				s = s.substring(i + 9);
				i = s.indexOf("</pubDate>");
				dat = s.substring(0, i);
				
			}

			while (s.contains("<title>") && k++ <j) {
			
				i = s.indexOf("<title>");
				s = s.substring(i + 7);
				i = s.indexOf("</title>");
				title = s.substring(0, i);

				i = s.indexOf("<link>");
				s = s.substring(i + 6);
				i = s.indexOf("</link>");
				link = s.substring(0, i);

				i = s.indexOf("<description>");
				s = s.substring(i + 13);
				i = s.indexOf("</description>");
				description = s.substring(0, i);

				i = s.indexOf("<pubDate>");
				s = s.substring(i + 9);
				i = s.indexOf("</pubDate>");
				dat = s.substring(0, i);	
				
				ss = "<table align=\"center\" style=\"width: 100%;\"><tr style=\"width: 555px;\" align=\"center\"><td align=\"left\">"
						+ "<br/><h3><a href=\""+link+"\" target=\"_blank\">" + title + "</a></h3>"+description 
						+ "<br/><br/> <i><a href=\""+link+"\" target=\"_blank\">"+tit+"</a></i> &nbsp;&nbsp; "+dat+"<br/></td></tr></table>";

				ss = ss.replaceAll("&amp;", "&").replaceAll("&lt;", "<")
						.replaceAll("&gt;", ">").replaceAll("&quot;", "\"");
				
				ss="<div >"+ss+"</div>";
				
				//add_rss_datastore(ss, sid, source,name, title, dat);
				sa=sa+ss;
				ss="";
				dat="";
			}
			

		} catch (Exception e) {
			sa=e.toString();
		}

	
		return sa;

	}
	public static void add_rss_datastore(String s, String sid) {
		Key kk = KeyFactory.createKey(sid, "rss_txt");
		Date date = new Date();
		Entity enot = new Entity(sid, kk);
		enot.setProperty("user", "user");
		enot.setProperty("date", date);
		enot.setProperty("content", new Text(s));

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		datastore.put(enot);
	}

}
