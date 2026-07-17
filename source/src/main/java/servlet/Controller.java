package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.UserAction;

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
				
		if(pageId.equals("none") && buttonId.equals("ログアウト")) {
			//ユーザーのセッション情報を破棄
			HttpSession session = request.getSession();
			session.invalidate();
			//ログイン画面のリンクを渡す
			page = "/WEB-INF/jsp/login.jsp";		
		}else if(pageId.equals("U001") && buttonId.equals("ログイン")) {
			UserAction action = new UserAction();
			page = action.login();
			
		}else if(pageId.equals("U002") && buttonId.equals("")) {
			
		}else if(pageId.equals("U003") && buttonId.equals("")) {
			
		}else if(pageId.equals("U004") && buttonId.equals("")) {
			
		}else if(pageId.equals("U005") && buttonId.equals("")) {
			
		}else if(pageId.equals("D001") && buttonId.equals("")) {
			
		}else if(pageId.equals("C001") && buttonId.equals("")) {
			
		}else if(pageId.equals("C002") && buttonId.equals("")) {
			
		}else if(pageId.equals("C003") && buttonId.equals("")) {
			
		}else if(pageId.equals("T001") && buttonId.equals("")) {
			
		}else if(pageId.equals("T002") && buttonId.equals("")) {
			
		}else if(pageId.equals("T003") && buttonId.equals("")) {
			
		}else if(pageId.equals("M001") && buttonId.equals("")) {
			
		}		
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(page);
		dispatcher.forward(request, response);
	}

}

