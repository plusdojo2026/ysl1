package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String page = "/WEB-INF/jsp/login.jsp";	
		//ページIDを取得
		String pageId = request.getParameter("page_id");
		//ボタンIDを取得
		String buttonId = request.getParameter("button_id");
		
		//何もわたってきて無ければログイン画面へ
		if(pageId==null && buttonId==null) {
			page = "/WEB-INF/jsp/login.jsp";	
		}//else if(xxxxx)と続いていく
		
		//ログイン画面へフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher(page);
		dispatcher.forward(request, response);	
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String page = null;
		
		//ページIDを取得
		String pageId = request.getParameter("page_id");
		//ボタンIDを取得
		String buttonId = request.getParameter("button_id");
				
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(page);
		dispatcher.forward(request, response);
	}

}

