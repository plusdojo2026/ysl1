package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.CasesAction;
import action.HomeAction;
import action.TasksAction;
import action.UserAction;
import action.WorksAction;

/*
 * -- コントローラ --
 * ボタン押下時など各画面の遷移を処理
 * 
 */

@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String page = "/WEB-INF/jsp/login.jsp";	
		//ページIDとボタンIDを取得
		String pageId = request.getParameter("page_id");
		String buttonId = request.getParameter("button_id");
		
		//何もわたってきて無ければログイン画面へ
		if(pageId==null && buttonId==null) {
			page = "/WEB-INF/jsp/login.jsp";	
		}
		
		//ログイン画面へフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher(page);
		dispatcher.forward(request, response);	
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String page = null;
		
		//ページID、ボタンIDを取得
		String pageId = request.getParameter("page_id");
		String buttonId = request.getParameter("button_id");
		//共通部分(ナビゲーション、ヘッダー、フッター)を押したかどうか
		String nav = request.getParameter("nav");
		String header = request.getParameter("header");
		String hooter = request.getParameter("hooter");

		//ヘッダー ---------------------------------------
		if(pageId.equals("header")) {
			if(buttonId.equals("ログアウト")) {
				//ユーザーのセッション情報を破棄
				HttpSession session = request.getSession();
				session.invalidate();
				//ログイン画面のリンクを渡す
				page = "/WEB-INF/jsp/login.jsp";
				
			}else if(buttonId.equals("パスワード変更")) {
				//パスワード変更画面へ遷移
				page = "/WEB-INF/jsp/resetPassword.jsp";
				
			}else if(buttonId.equals("トップへ戻る")) {
				//ダッシュボード画面へ遷移
				page = "/WEB-INF/jsp/home.jsp";
				
			}
			
		//ログイン画面 ------------------------------------
		}else if(pageId.equals("U001") && buttonId.equals("ログイン")) {
			UserAction action = new UserAction();
			//ログイン処理[結果:ダッシュボード画面へ]
			page = action.login();
		
		//パスワード変更画面
		}else if(pageId.equals("U002") && buttonId.equals("保存")) {
			UserAction action = new UserAction();
			//パスワード更新処理[結果:成功メッセージ]
			page =action.update();
			
		//メンバー一覧画面 --------------------------------
		}else if(pageId.equals("U003")) {
			UserAction action = new UserAction();
			if(buttonId.equals("新規登録")) {
				//新規登録画面へ遷移
				page = "/WEB-INF/jsp/user_regist.jsp";
				
			}else if(buttonId.equals("編集")) {
				//編集画面へ遷移
				page = "/WEB-INF/jsp/user_update.jsp";
			}
			
		//メンバー新規登録画面 ----------------------------
		}else if(pageId.equals("U004") && buttonId.equals("登録")) {
			UserAction action = new UserAction();
			//メンバー新規登録処理[結果:成功メッセージ]
			page = action.insert();
			
		//メンバー編集画面 --------------------------------
		}else if(pageId.equals("U005") && buttonId.equals("保存")) {
			UserAction action = new UserAction();
			//メンバー更新処理[結果:メンバー一覧画面へ]
			page = action.update();
			
		//ダッシュボード画面 ------------------------------
		}else if(pageId.equals("nav")) {
			
			if(buttonId.equals("ダッシュボード")) {
				HomeAction action = new HomeAction();
				//ダッシュボード画面表示[]
				page = action.selectAll();
				
			}else if(buttonId.equals("案件")) {
				CasesAction action = new CasesAction();
				//案件一覧画面表示[]
				page = action.initialize();
				
			}else if(buttonId.equals("タスク管理")) {
				TasksAction action = new TasksAction();
				//タスク一覧画面表示[]
				page = action.selectAll();
				
			}else if(buttonId.equals("月次集計")) {
				WorksAction action = new WorksAction();
				//月次集計画面表示[]
				page = action.initialize();
				
			}else if(buttonId.equals("メンバー管理")) {
				UserAction action = new UserAction();
				//メンバー一覧画面表示[]
				page = action.selectAll();
				
			}
			
		//案件一覧画面 -----------------------------------
		}else if(pageId.equals("C001")) {
			if(buttonId.equals("新規登録")) {
				
			}else if(buttonId.equals("検索")) {
				
			}else if(buttonId.equals("編集")) {
				
			}else if(buttonId.equals("参照")) {
				
			}
			
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

