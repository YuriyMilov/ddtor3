package dd;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class news extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String sh = req.getScheme() + "://" + req.getServerName() + ":"
				+ req.getServerPort() + req.getContextPath();
		String s ="";
		  
	if (req.getQueryString().equals("1")) {

			
			s= stkl.rfu_utf(sh+"/news.htm");
			s=s.replace("<!-- qqq -->", stkl.sts);
			res.setContentType("text/html");
			res.setCharacterEncoding("UTF-8");
			PrintWriter w = res.getWriter();
			w.write(s);
			w.flush();

		}
		else	
			if (req.getQueryString().equals("2")) {

			
			s= stkl.rfu_utf(sh+"/news2.htm");
			s=s.replace("<!-- qqq -->", stkl.mail.replace("<b>", "<h4>").replace("</b>", "</h4>").replace("<br/>", ""));
			res.setContentType("text/html");
			res.setCharacterEncoding("UTF-8");
			PrintWriter w = res.getWriter();
			w.write(s);
			w.flush();

		}
		else

		if (req.getQueryString().equals("3")) {

			res.setContentType("text/event-stream");
			res.setCharacterEncoding("UTF-8");
			PrintWriter w = res.getWriter();

			// new rr(w,sh);

			try {

				String s1 = stkl.mail;
				String s2 = "";

				int i = s1.indexOf("<hr/>");
				if (i > 0)
					s1 = s1.substring(i);

				i = stkl.mail.indexOf("<hr/>");
				if (i > 0)
					s2 = stkl.mail.substring(i);

				while (s1.equals(s2)) {
					i = stkl.mail.indexOf("<hr/>");
					if (i > 0)
						s2 = stkl.mail.substring(i);
					try {
						i = ((int) (new Date().getTime() / 1000) - stkl.k);
						if (i > stkl.n_sec) {
							stkl.rfu_utf(sh + "/rss_add");
							break;
						}
					} catch (Exception ex) {
					}
					Thread.sleep(2000);
				}
				w.write("data: " + stkl.get_hr(stkl.mail) + "\n\n");
				w.flush();

				Thread.sleep(2000);
			} catch (InterruptedException iex) {
				iex.printStackTrace();
			}

		} else

		if (req.getQueryString().equals("4")) {
			res.setContentType("text/event-stream");
			res.setCharacterEncoding("UTF-8");
			PrintWriter w = res.getWriter();

			try {

				String s1 = stkl.mail;
				String s2 = "";

				int i = s1.indexOf("<hr/>");
				if (i > 0)
					s1 = s1.substring(i);

				i = stkl.mail.indexOf("<hr/>");
				if (i > 0)
					s2 = stkl.mail.substring(i);

				while (s1.equals(s2)) {
					i = stkl.mail.indexOf("<hr/>");
					if (i > 0)
						s2 = stkl.mail.substring(i);
					try {
						i = ((int) (new Date().getTime() / 1000) - stkl.k);
						if (i > stkl.n_sec) {
							stkl.rfu_utf(sh + "/rss_add");
							break;
						}
					} catch (Exception ex) {
					}
					Thread.sleep(2000);
				}
				w.write("data: " + stkl.get_hr2(stkl.mail) + "\n\n");
				w.flush();

				Thread.sleep(2000);
			} catch (InterruptedException iex) {
				iex.printStackTrace();
			}

		}

	}
}