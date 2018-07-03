<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>



            </div>
            <div class="weui-tabbar-tx">
                <a href="/WeiXin/tx/TabbarMenu.jsp?id=1" class="weui-tabbar__item">
                    <img src="/css/images/icon_nav_article.png" alt="" class="weui-tabbar__icon">
                    <p class="weui-tabbar__label">首页</p>
                </a>
                <a href="/WeiXin/tx/TabbarMenu.jsp?id=2" class="weui-tabbar__item">
                    <img src="/css/images/icon_nav_icons.png" alt="" class="weui-tabbar__icon">
                    <p class="weui-tabbar__label">预约</p>
                </a>
                <a href="/WeiXin/tx/TabbarMenu.jsp?id=3" class="weui-tabbar__item">
                    <img src="/css/images/icon_nav_panel.png" alt="" class="weui-tabbar__icon">
                    <p class="weui-tabbar__label">我</p>
                </a>
                <a href="/WeiXin/tx/TabbarMenu.jsp?id=4" class="weui-tabbar__item">
                    <img src="/css/images/icon_nav_panel.png" alt="" class="weui-tabbar__icon">
                    <p class="weui-tabbar__label">次卡</p>
                </a>

				<% 	
					String groupid=request.getSession().getAttribute("groupid")==null?"":(String)request.getSession().getAttribute("groupid");
					if (groupid.equals("100") || groupid.equals("101") )
					{
						%>
			                <a href="/WeiXin/tx/TabbarMenu.jsp?id=5" class="weui-tabbar__item">
			                    <img src="/css/images/icon_nav_search_bar.png" alt="" class="weui-tabbar__icon">
			                    <p class="weui-tabbar__label">管理</p>
			                </a>
						<%
					}
 				%>
            </div>
        </div>
    </div>
</div>