package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import dto.UsersDTO;
import service.UsersService;

/**
 * 
 * UserActionクラス。
 * <p>
 * TODO ページのデータを取得する。ユーザーDTOの中に保存、Service層を繋がるメソッド
 * </p>
 *
 * @author YSL黄范航
 */
public class UsersAction {
	//serviceを実体化
	UsersService usersService = new UsersService();
	HttpServletRequest request;

	//コンストラクタ
	public UsersAction(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * ログインメソッド
	 * @return  String pageのURL
	 */
	public String login() {
		String page = "";
		UsersDTO loginUser = new UsersDTO();
		//ページから入力値を取得・loginUserに保存
		loginUser.setLoginId(request.getParameter("loginId"));
		loginUser.setLoginPw(request.getParameter("loginPw"));

		HttpSession session = request.getSession();
		//ログイン、成功したらUserDTOにUser情報を保存
		UsersDTO user = usersService.login(loginUser);
		if (user != null) {
			//セッションにユーザーの情報を保存
			session.setAttribute("user", user);
			page = "/WEB-INF/jsp/menu.jsp";
		} else {
			page = "/WEB-INF/jsp/login.jsp";
		}

		return page;

	}

	public String update() {
		String page = "";
		UsersDTO updateUser = new UsersDTO();
		//userid
		updateUser.setUserId(Integer.parseInt(request.getParameter("userId")));
		//userName
		updateUser.setUserName(request.getParameter("userName"));
		//mailAddress
		updateUser.setMailAddress(request.getParameter("mailAdress"));
		//loginPw
		updateUser.setLoginPw(request.getParameter("userName"));
		//authority
		if (request.getParameter("authority").equals(0)) {
			updateUser.setAuthority(false);
		} else {
			updateUser.setAuthority(true);
		}
		//userStatus
		if (request.getParameter("userStatus").equals(0)) {
			updateUser.setActive(false);
		} else {
			updateUser.setActive(true);
		}

		if (usersService.update(updateUser)) {
			page = "/WEB-INF/jsp/users.jsp";
			request.setAttribute("message", "更新成功！");
		} else {
			page = "/WEB-INF/jsp/user_update.jsp";
			request.setAttribute("message", "更新失敗！");
		}

		return page;
	}

	public String insert() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
