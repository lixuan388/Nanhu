package com.java.nanhu;



import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ecity.java.json.JSONArray;
import com.ecity.java.json.JSONObject;
import com.java.sql.table.MySQLTable;
import com.java.web.weixin.base.GlobalVariable;

/**
 * Servlet implementation class PDFSign
 */
@WebServlet("/GetMonthDateInfo")
public class GetMonthDateInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetMonthDateInfoServlet() {
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
			GlobalVariable.WriteMsgText(response, -101, ErrText);
			return;
		}	

		JSONObject ResultXml = new JSONObject();     
		JSONArray ResultAraay = new JSONArray();     


		SimpleDateFormat fmtHour; 
		fmtHour = new SimpleDateFormat ("HH:mm");

		SimpleDateFormat fmtDay; 
		fmtDay = new SimpleDateFormat ("MM/dd");
		Calendar calendar = Calendar.getInstance();
		
		SimpleDateFormat fmtDay2; 
		fmtDay2 = new SimpleDateFormat ("dd");
		
		MySQLTable table=new MySQLTable("select awa_id,awa_username,awa_date,awa_begin,awa_end,awa_on,awa_off,awa_late,awa_early,getdate() as Now from attend2015.dbo.awa_work_attendance where awa_status=1 and awa_id_au='"+id+"' and awa_date>CONVERT(nvarchar(10),getdate()-30,120) and awa_date<getdate() order by awa_date ");
//		MySQLTable table=new MySQLTable("select awa_id,awa_username,awa_date,awa_begin,awa_end,awa_on,awa_off,awa_late,awa_early,getdate() as Now from awa_work_attendance where awa_id_au='"+id+"' and awa_date='2017-03-25'");
		table.Open();
		while (table.next())
		{

			
			JSONObject DateJson = new JSONObject();     
			Date date=null;
			String dateStr ="";	
			
			DateJson.put("ID",table.getString("awa_id"));
			
			date=table.getDateTime("awa_date");
			calendar.setTime(date);

			DateJson.put("week", calendar.get(Calendar.DAY_OF_WEEK));
			DateJson.put("Date",fmtDay2.format(date));
						
			date=table.getDateTime("awa_begin");
			if (date==null)
			{
				dateStr="";
			}
			else
			{
				dateStr=fmtHour.format(date);
			}
			DateJson.put("Begin",dateStr);
			date=table.getDateTime("awa_end");
			if (date==null)
			{
				dateStr="";
			}
			else
			{
				dateStr=fmtHour.format(date);
			}
			DateJson.put("End",dateStr);
			date=table.getDateTime("awa_on");
			if (date==null)
			{
				dateStr="";
			}
			else
			{
				dateStr=fmtHour.format(date);
			}
			DateJson.put("On",dateStr);
			date=table.getDateTime("awa_off");
			if (date==null)
			{
				dateStr="";
			}
			else
			{
				dateStr=fmtHour.format(date);
			}
			DateJson.put("Off",dateStr);
			date=table.getDateTime("Now");
			if (date==null)
			{
				dateStr="";
			}
			else
			{
				dateStr=fmtHour.format(date);
			}
			DateJson.put("Late",table.getString("awa_late"));
			DateJson.put("Early",table.getString("awa_early"));
			
			ResultAraay.put(DateJson);
		}

		table.Close();

		ResultXml.put("Data", ResultAraay);
		response.getWriter().print(ResultXml.toString());
		response.getWriter().flush();
	}


}
