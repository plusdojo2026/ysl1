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
	 * @return 
	 */
	public String login() {
		String page = "";
		UsersDTO loginUser = new UsersDTO();
		loginUser.setLoginId(request.getParameter("loginId"));
		loginUser.setLoginPw(request.getParameter("loginPw"));

		HttpSession session = request.getSession();
		UsersDTO user = usersService.login(loginUser);
		if (user != null) {
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
		request.getParameter("loginId");
		return page;
	}

	public String insert() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
