<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%
String id =request.getParameter("id")==null?"":request.getParameter("id");

request.getSession().setAttribute("UserID", id);

request.getSession().setAttribute("AwaID", "-1");

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">	
	<jsp:include page="/head.jsp"/>
	<script type="text/javascript" src="<%=request.getContextPath() %>/res/js/jquery.tmpl.js"></script>
	<script type="text/javascript">		
		$(function(){
			$.ajax({
				url:"/Nanhu/GetDateInfo",
				type:'get',
				dataType:'Json',
				success:function(data){
					if (data.MsgID==1)
					{
						$("#ID").html(data.ID);
						$("#UserName").html(data.UserName);
						$("#Date").html("日期："+data.Date);
						$("#Now").html("("+data.Now+")");
						$("#Begin").html("上班时间："+data.Begin);
						$("#End").html("下班时间："+data.End);
						$("#On").html("打卡时间："+data.On);
						if (data.On!="")
						{
							$("#hitbtn").html("");
						}
						$("#Off").html("打卡时间："+data.Off);
						$("#Late").html("迟到："+data.Late);
						$("#Early").html("早退："+data.Early);

						alert("刷新成功！");
					}
					else
					{
						$("#UserName").html(data.MsgText);
						$("#hitbtn").html("")
						$("#hitoffbtn").html("")
					}
					//console.log(data);
					var $loadingToast = $('#loadingToast');
					$loadingToast.fadeOut(100);
				}
			});
			


			for (var c=0;c<35;c++)
			{
				var TemplateData={'c':c};
				$("#DataTableTemplate").tmpl(TemplateData).appendTo('#monthDiv');
			}
			$.ajax({
				url:"/Nanhu/GetMonthDateInfo",
				type:'get',
				dataType:'Json',
				success:function(data){

						var row=0;
						for (d in data.Data)
						{
							console.log(data.Data[d]);
							var day=data.Data[d].week+row-1;
							if (data.Data[d].Begin!="")
							{
								$(".day"+day+">div").addClass("daydiv2");
							}
							
							var div=".day"+day+" .day>span";
							//console.log(div);
							if (data.Data[d].week==7)
							{
								row=row+7;
							}

							var div=".day"+day+" .day>span";
							$(div).html(data.Data[d].Date);
							var div=".day"+day+" .on>span";
							$(div).html(data.Data[d].On+"&nbsp;");
							$(div).addClass('early'+data.Data[d].Late);

							var div=".day"+day+" .late>span";
							$(div).html(data.Data[d].Late+"&nbsp;");
							$(div).addClass('early'+data.Data[d].Late);
							
							if (data.Data[d].On=="")
							{
								$(".day"+day+">div").addClass("daydiv3");
								$(div).addClass("null");
							}
							
							var div=".day"+day+" .off>span";
							$(div).html(data.Data[d].Off+"&nbsp;");
							$(div).addClass('early'+data.Data[d].Early);
							var div=".day"+day+" .early>span";
							$(div).html(data.Data[d].Early+"&nbsp;");
							$(div).addClass('early'+data.Data[d].Early);
							if (data.Data[d].Off=="")
							{
								$(".day"+day+">div").addClass("daydiv3");
								$(div).addClass("null");
								
							}
							
						}
				}
			});
			
			
		});
		function Hit()
		{
			$.ajax({
				url:"/Nanhu/HitDateInfo",
				type:'get',
				dataType:'Json',
				success:function(data){
					alert(data.MsgText);
					if (data.MsgID==1)
					{
						var url=window.location+"&d=<%=request.getSession().getLastAccessedTime()%>"
						window.location.href=url;   
					}
				}
			});
		}
		function HitOff()
		{
			$.ajax({
				url:"/Nanhu/HitOffDateInfo",
				type:'get',
				dataType:'Json',
				success:function(data){
					alert(data.MsgText);
					if (data.MsgID==1)
					{
						var url=window.location+"&d=<%=request.getSession().getLastAccessedTime()%>"
						window.location.href=url;	
					}
				}
			});
		}
	</script>
	<style type="text/css">
		.on>span.early0,.off>span.early0,.late>span.early0,.early>span.early0{
			color: black;
			font-weight: 100;
		}
		.on>span,.off>span,.late>span,.early>span{
			color: red;
			font-weight: 800;
		}
		.day{
			font-weight: 800;
		}
		.daydiv{
			border: 1px solid silver;
			border-radius: 5px;
			padding: 1px;
			height: 100%;
			width: 100%;
		}
		.daydiv2.daydiv3{
			border-color:  red;
		}
		.daydiv2.daydiv3 .null{
			background-color: red;
			display: inline-block;
			width: 90%;
			border-radius: 5px;
		}
		.daydiv2{
			border-color:  #749dff;
		}


	</style>
				
</head>
<body>
<script id="DataTableTemplate" type="text/x-jquery-tmpl">
<div style="float:left;width:14%;padding:1px;height:105px;font-size: 12px;" class="day${c}"  >
	<div class="daydiv" >
		<div class="day" style="text-align: center;"><span></span></div>
		<div class="on" style="text-align: center;"><span></span></div>
		<div class="late" style="text-align: center;"><span></span></div>
		<div class="off" style="text-align: center;"><span></span></div>
		<div class="early" style="text-align: center;"><span></span></div>
	</div>
</div>
</script>
<div >
	<div id="loadingToast" style=" display: none1;">
		<div class="weui-mask_transparent"></div>
		<div class="weui-toast">
			<i class="weui-loading weui-icon_toast"></i>
			<p class="weui-toast__content">数据加载中</p>
		</div>
	</div>
	
<div style="display: none"><span id="ID"></span></div>
<div style="font-size:2em" ><span id="UserName"></span></div>
<div style="font-size:2em"  ><span id="Date"></span></div>
<div style="font-size:1em;color:gray;"><span id="Begin"></span ><span  id="hitbtn"><a href="javascript:void(0);" onclick="Hit()" style="line-height: 16px;" class="weui-btn weui-btn_mini weui-btn_primary">打卡<span id="Now"></span></a></span></div>
<div ><span id="On"></span><span id="Late"></span></div>
<div style="font-size:1em;color:gray;"><span id="End"></span><span  id="hitoffbtn"><a href="javascript:void(0);" onclick="HitOff()" style="line-height: 16px;" class="weui-btn weui-btn_mini weui-btn_primary">打卡<span id="Now"></span></a></span></div>
<div ><span id="Off"></span><span id="Early"></span></div>
</div>
<div id ="monthDiv"  style="margin-top: 20px;">
	
</div>
	
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>