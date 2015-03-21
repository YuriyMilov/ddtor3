package dd;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;


public class mm2 extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String sh = req.getScheme() + "://" + req.getServerName() + ":"
				+ req.getServerPort() + req.getContextPath();

		int k = Jsoup.parse(stkl.mail).text().length();
		if(k>2222)	
			{
			stkl.send_mail("Администратор", "admin@ddtor.com", "Админ", _info.semailto, "на "+ _info.get_date_rus3(), stkl.rfu_utf(sh + "/adv.txt") +stkl.mail);
			stkl.mail="";
			}
		resp.setCharacterEncoding("UTF8");
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.write(stkl.mail);
		out.flush();
		out.close();
		

	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		doGet(req, resp);
	}


	private static final long serialVersionUID = 1L;

}

//String subj = MimeUtility.encodeText("Сообщения на " + stkl.get_date() + " года", "utf-8", "B");

