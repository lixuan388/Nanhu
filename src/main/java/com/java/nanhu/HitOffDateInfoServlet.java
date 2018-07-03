package com.java.nanhu;



import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.java.sql.table.MySQLUpdate;
import com.java.web.weixin.base.GlobalVariable;

/**
 * Servlet implementation class PDFSign
 */
@WebServlet("/HitOffDateInfo")
public class HitOffDateInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HitOffDateInfoServlet() {
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

		HttpSession session = request.getSession();
		String id =session.getAttribute("AwaID")==null?"":(String)session.getAttribute("AwaID");
		
		
		
//		String id =request.getParameter("id")==null?"":request.getParameter("id");
		if (id.equals(""))
		{
			String ErrText="错误的参数，无法操作！(id)";
			GlobalVariable.WriteMsgText(response, -101, ErrText);
			return;
		}	  
		if (id.equals("-1"))
		{
			String ErrText="错误的记录，无法操作！(-1)";
			GlobalVariable.WriteMsgText(response, -101, ErrText);
			return;
		}	    

    	MySQLUpdate SqlUpdate=new MySQLUpdate();
    	SqlUpdate.Update("update attend2015.dbo.awa_work_attendance set awa_Off=getdate(),awa_early=DATEDIFF(MINUTE,isnull(awa_end,getdate()),getdate()) where isnull(awa_end,getdate())>getdate() and  awa_id="+id);
    	SqlUpdate.Update("update attend2015.dbo.awa_work_attendance set awa_Off=getdate(),awa_early=0 where isnull(awa_end,getdate())<=getdate() and  awa_id="+id);
    	SqlUpdate.Close();	  
	
		String ErrText="打卡成功！";
	    GlobalVariable.WriteMsgText(response, 1, ErrText);
		
		
	}


}
