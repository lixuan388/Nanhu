package com.java.nanhu;



import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ecity.java.web.WebFunction;
import com.java.sql.table.MySQLTable;
import com.java.web.weixin.base.GlobalVariable;

/**
 * Servlet implementation class PDFSign
 */
@WebServlet("/GetDateInfo")
public class GetDateInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetDateInfoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json;charset=utf-8"); 
		response.setCharacterEncoding("UTF-8");  
		response.setHeader("Cache-Control", "no-cache");

//		String id =request.getParameter("id")==null?"":request.getParameter("id");
		
		HttpSession session = request.getSession();
		String id =session.getAttribute("UserID")==null?"":(String)session.getAttribute("UserID");
		
		
		if (id.equals(""))
		{
			String ErrText="错误的参数，无法操作！(id)";
			WebFunction.WriteMsgText(response, -101, ErrText);
			return;
		}	
	    
		net.sf.json.JSONObject ResultXml = new net.sf.json.JSONObject();     

				
		
		MySQLTable table=new MySQLTable("select awa_id,awa_username,awa_date,awa_begin,awa_end,awa_on,awa_off,awa_late,awa_early,getdate() as Now from attend2015.dbo.awa_work_attendance where awa_status=1 and awa_id_au='"+id+"' and awa_date=CONVERT(nvarchar(10),getdate(),120)");
//		MySQLTable table=new MySQLTable("select awa_id,awa_username,awa_date,awa_begin,awa_end,awa_on,awa_off,awa_late,awa_early,getdate() as Now from awa_work_attendance where awa_id_au='"+id+"' and awa_date='2017-03-25'");
		table.Open();
		if (table.next())
		{

			Date date=null;
			SimpleDateFormat formatter; 
			formatter = new SimpleDateFormat ("HH:mm");
			String dateStr ="";	
			
			ResultXml.put("MsgID","1");     
			ResultXml.put("ID",table.getString("awa_id"));
			session.setAttribute("AwaID",table.getString("awa_id"));
			
			ResultXml.put("UserName",table.getString("awa_username"));
			ResultXml.put("Date",table.getString("awa_date"));
			
			date=table.getDateTime("awa_begin");
			if (date==null)
			{
				dateStr="";
			}
			else
			{
				dateStr=formatter.format(date);
			}
			ResultXml.put("Begin",dateStr);
			date=table.getDateTime("awa_end");
			if (date==null)
			{
				dateStr="";
			}
			else
			{
				dateStr=formatter.format(date);
			}
			ResultXml.put("End",dateStr);
			date=table.getDateTime("awa_on");
			if (date==null)
			{
				dateStr="";
			}
			else
			{
				dateStr=formatter.format(date);
			}
			ResultXml.put("On",dateStr);
			date=table.getDateTime("awa_off");
			if (date==null)
			{
				dateStr="";
			}
			else
			{
				dateStr=formatter.format(date);
			}
			ResultXml.put("Off",dateStr);
			date=table.getDateTime("Now");
			if (date==null)
			{
				dateStr="";
			}
			else
			{
				dateStr=formatter.format(date);
			}
			ResultXml.put("Now",dateStr);
			ResultXml.put("Late",table.getString("awa_late"));
			ResultXml.put("Early",table.getString("awa_early"));
		}
		else
		{
			String ErrText="无记录";
			GlobalVariable.WriteMsgText(response, -1, ErrText);
			table.Close();
			return;
		}

		table.Close();

		response.getWriter().print(ResultXml.toString());
		response.getWriter().flush();
	}


}
