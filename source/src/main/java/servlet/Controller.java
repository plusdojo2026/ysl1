package servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

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
import action.UsersAction;
import action.WorksAction;
import dto.UsersDTO;

/**
 * -- コントローラ --
 * ボタン押下時など各画面の遷移を処理
 * ページID、ボタンIDは外部設計書の項目定義書を参照
 * 
 * pageId = ボタン処理を機能ごとにわける
 * 	画面分けだけでなくナビゲーションなど共通部分もこれで判別する
 * buttonId = pageIdあたりのボタンの項目を決定する
 * 
 * page = 各action実行における返り値（遷移先jspファイルのリンク）を格納
 * 
 * 
 * @author haruto.tanaka
 */

@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//何もわたってきて無ければログイン画面へ
		String page = "/WEB-INF/jsp/login.jsp";
		//ページIDとボタンIDを取得
		String pageId = request.getParameter("pageId");
		String buttonId = request.getParameter("buttonId");
		//ユーザーのログイン状態をチェック
		HttpSession session = request.getSession();
		UsersDTO usersDTO = (UsersDTO) session.getAttribute("user");
		if (pageId != null && usersDTO != null) {

			//ヘッダー
			if (pageId.equals("header")) {
				if (buttonId.equals("ログアウト")) {
					//ユーザーのセッション情報を破棄
					session = request.getSession();
					session.invalidate();
					//ログイン画面のリンクを渡す
					page = "/WEB-INF/jsp/login.jsp";
				} else if (buttonId.equals("パスワード変更")) {
					//パスワード変更画面へ遷移
					page = "/WEB-INF/jsp/resetPassword.jsp";

				} else if (buttonId.equals("トップへ戻る")) {
					//ダッシュボード画面へ遷移
					page = "/WEB-INF/jsp/home.jsp";

				}
			}
			//メンバー一覧画面 --------------------------------
			else if (pageId.equals("U003")) {
				UsersAction uAction = new UsersAction(request);
				if (buttonId.equals("新規登録")) {
					//新規登録画面へ遷移
					page = "/WEB-INF/jsp/user_regist.jsp";

				}
			}

			//月次集計画面 ------------------------------------
			else if (pageId.equals("M001")) {
				WorksAction wAction = new WorksAction(request);
				if (buttonId.equals("集計")) {
					//選択した月の情報を表示
					page = wAction.initialize();
				} else if (buttonId.equals("工数一覧")) {
					page = wAction.selectByMonth();
				}
			
				//				else if (buttonId.equals("CSV出力")) {
				//				//これから実装(現在は仮でcsvとしときます)
				//				page = wAction.csv();
				//			}

				//					ダッシュボード画面 ------------------------------
				else if (pageId.equals("D001")) {
					if (buttonId.equals("ダッシュボード")) {
						HomeAction hAction = new HomeAction(request);
						//ダッシュボード画面表示[]
						try {
							page = hAction.Intilize();
						} catch (UnsupportedEncodingException | SQLException e) {
							// TODO 自動生成された catch ブロック
							e.printStackTrace();
						}

						//						} else if (buttonId.equals("案件")) {
						//							CasesAction cAction = new CasesAction(request);
						//							//案件一覧画面表示[]
						//							page = cAction.initialize();
						//			
						//						} else if (buttonId.equals("タスク管理")) {
						//							TasksAction tAction = new TasksAction(request);
						//							//タスク一覧画面表示[]
						//							page = tAction.selectAll();
						//			
						//						} else if (buttonId.equals("月次集計")) {
						//							WorksAction wAction = new WorksAction(request);
						//							//月次集計画面表示[]
						//							page = wAction.initialize();

						//						} else if (buttonId.equals("メンバー管理")) {
						//							UsersAction uAction = new UsersAction(request);
						//							//メンバー一覧画面表示[]
						//							page = uAction.selectAll();

						//						}
						//					}

						//案件一覧画面 -----------------------------------
						//		else if (pageId.equals("C001")) {
						//			CasesAction cAction = new CasesAction(request);
						//			if (buttonId.equals("新規登録")) {
						//				//新規登録画面表示[]
						//				page = cAction.casesRegist();
					}
				}
			}
		}
		//ログイン画面へフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher(page);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String page = "/WEB-INF/jsp/login.jsp";

		//ページID、ボタンIDを取得
		String pageId = request.getParameter("pageId");
		String buttonId = request.getParameter("buttonId");

		if (pageId == null) {
			request.setAttribute("message", "pageIdは存在していない！");
		}
		if (buttonId == null) {
			request.setAttribute("message", "buttonIdは存在していない！");
		}
		//ユーザーのログイン状態をチェック
		HttpSession session = request.getSession();
		UsersDTO usersDTO = (UsersDTO) session.getAttribute("user");
		if (pageId != null && (usersDTO != null || pageId.equals("U001"))) {

			//ログイン画面 ------------------------------------
			if (pageId.equals("U001") && buttonId.equals("ログイン")) {

				UsersAction uAction = new UsersAction(request);
				page = uAction.login();

				//ログイン成功後、ホームページ初期設定を行う
				if (request.getSession().getAttribute("user") != null) {
					HomeAction hAction = new HomeAction(request);

					try {
						page = hAction.Intilize();
					} catch (SQLException e) {
						throw new ServletException(
								"ダッシュボードの初期化に失敗しました。",
								e);
					}
				}
			} else if (pageId.equals("U002") && buttonId.equals("保存")) {
				UsersAction uAction = new UsersAction(request);
				//パスワード更新処理[結果:成功メッセージ]
				page = uAction.update();

				//メンバー一覧画面 --------------------------------
			} else if (pageId.equals("U003") && buttonId.equals("編集")) {
				UsersAction uAction = new UsersAction(request);
				//編集画面表示
				page = uAction.update();

				//メンバー新規登録画面 ----------------------------
			} else if (pageId.equals("U004") && buttonId.equals("登録")) {
				UsersAction uAction = new UsersAction(request);
				//メンバー新規登録処理[結果:成功メッセージ]
				page = uAction.insert();

				//メンバー編集画面 --------------------------------
			} else if (pageId.equals("U005") && buttonId.equals("保存")) {
				UsersAction uAction = new UsersAction(request);
				//メンバー更新処理[結果:メンバー一覧画面へ]
				page = uAction.update();
			}
			//案件一覧画面 -----------------------------------
			else if (pageId.equals("C001")) {
				CasesAction cAction = new CasesAction(request);
				if (buttonId.equals("検索")) {
					//案件検索処理[結果:絞り込みしたデータを取得]
					page = cAction.selectAll();

				} else if (buttonId.equals("編集")) {
					//編集画面表示[]
					page = cAction.casesEdit();

				} else if (buttonId.equals("参照")) {
					//案件詳細画面表示[]
					page = cAction.initiCasesDetail();

				}
			}
			//案件登録、編集画面 ------------------------------
			else if (pageId.equals("C002")) {
				CasesAction cAction = new CasesAction(request);
				if (buttonId.equals("登録")) {
					//案件登録処理[結果:Casesテーブルへ登録、案件一覧画面へ]
					page = cAction.insert();

				} else if (buttonId.equals("編集")) {
					//案件編集・更新処理[結果:Cases該当レコードを更新、案件一覧画面へ]
					page = cAction.update();

				}
			}
			//			//案件詳細画面 ------------------------------------
			else if (pageId.equals("C003")) {
				if (buttonId.equals("完了にする")) {

				} else if (buttonId.equals("中止にする")) {

				} else if (buttonId.equals("案件編集")) {
					CasesAction cAction = new CasesAction(request);
					//案件編集画面表示[]
					page = cAction.casesEdit();

					//			} else if (buttonId.equals("タスク追加")) {
					//				TasksAction tAction = new TasksAction(request);
					//				//タスク登録画面表示[]
					//				page = tAction.functions();
					//
					//			} else if (buttonId.equals("タスク編集")) {
					//				TasksAction tAction = new TasksAction(request);
					//				//タスク編集画面表示[]
					//				page = tAction.functions();
					//
					//			} else if (buttonId.equals("タスク削除")) {
					//				TasksAction tAction = new TasksAction(request);
					//				//タスク削除処理[結果:該当タスクと紐づく工数削除、案件詳細画面へ]
					//				page = tAction.delete();
					//
					//			} else if (buttonId.equals("工数入力")) {
					//				WorksAction wAction = new WorksAction(request);
					//				//工数登録画面表示
					//				page = wAction.insert();
					//
					//			} else if (buttonId.equals("すべて見る")) {
					//				WorksAction wAction = new WorksAction(request);
					//				//月次集計画面表示[]
					//				page = wAction.initialize();
					//
					//			}
				}

				//タスク一覧画面 ---------------------------------
				else if (pageId.equals("T001")) {
					TasksAction tAction = new TasksAction(request);
					if (buttonId.equals("検索")) {
						//タスク検索処理[結果:絞り込んだデータを取得して表示]
						page = tAction.selectAll();

					} else if (buttonId.equals("編集")) {
						//タスク編集画面表示[]
						page = tAction.functions();
					}
				}

				//			//タスク新規登録・編集画面 ------------------------
				//		else if (pageId.equals("T002")) {
				//			TasksAction tAction = new TasksAction(request);
				//			if (buttonId.equals("登録")) {
				//				//タスク登録処理[結果:Tasksテーブルにレコードを追加、案件詳細画面へ]
				//				page = tAction.insert();
				//			} else if (buttonId.equals("編集")) {
				//				//タスク更新処理[結果:Tasks該当レコードを更新、案件詳細画面へ]
				//				page = tAction.update();
				//			}
				//		}
				//			//タスク詳細画面 ------------------------------------
				//		 else if (pageId.equals("T003")) {
				//			WorksAction wAction = new WorksAction(request);
				//			if (buttonId.equals("削除")) {
				//				//工数データ削除処理[結果:]
				//				page = wAction.delete();
				//
				//			} else if (buttonId.equals("工数入力")) {
				//				//工数入力モーダル
				//				page = wAction.insert();
				//			}
				//		}
				//月次集計画面 ------------------------------------
				else if (pageId.equals("M001")) {
					WorksAction wAction = new WorksAction(request);
					if (buttonId.equals("集計")) {
						//選択した月の情報を表示
						page = wAction.initialize();
					} else if (buttonId.equals("工数一覧")) {
						page = wAction.selectByMonth();
					}
					//							else if (buttonId.equals("CSV出力")) {
					//							//これから実装(現在は仮でcsvとしときます)
					//							page = wAction.csv();
					//						}
				}
			}
			//pageに格納したリンク先にフォワードする
			RequestDispatcher dispatcher = request.getRequestDispatcher(page);
			dispatcher.forward(request, response);
		}

	}
}