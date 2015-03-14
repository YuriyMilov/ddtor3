package dd;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class _info {
// GAE ddtor3 admin at ddtor quick-0
	    
	public static String blobkey="";
	
	public static void main(String[] args){
		
		String s = "<p> <span> foo </span> <em> bar <a> foobar </a> baz </em> </p>";
		//s=stkl.rfu_utf("http://www.ddtor.com");
		s = Jsoup.parse(s).text();

		System.out.println(s);
		
		
		//tets
	}
	
	
	public static String rem_tag(String s){
		
		String s1="";
		int i=0;
		while (s.contains(" #"))
		{
			i=s.indexOf(" #");
		s1=s.substring(0,i);		
		s=s.substring(i+2);
		
		if(s.contains(" "))
			s=s.substring(s.indexOf(" "));
		
		s=s1+s;
		
		}
		
		return s;
	}
	

} 
 