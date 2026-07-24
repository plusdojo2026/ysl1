package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

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
	 * @return  String pageのURL menu.jsp
	 */
	public String login() {
		//ページを設定
		String page = "/WEB-INF/jsp/login.jsp";
		//IDとPWをもらう
		String loginId = request.getParameter("loginId");
		String password = request.getParameter("loginPw");
		//値あるかを確認
		if (loginId == null || loginId.isBlank()
				|| password == null || password.isBlank()) {
			//ないとエラーメッセージを表示
			request.setAttribute(
					"message",
					"ログインIDとパスワードを入力してください。");
			return page;
		}
		//IDをDTOに設定
		UsersDTO loginUser = new UsersDTO();
		loginUser.setLoginId(loginId);

		//このDTOに対応しているユーザーの全情報をサーチ
		UsersDTO user = usersService.login(loginUser);

		try {
			if (user != null
					&& user.getLoginPw() != null
					&& BCrypt.checkpw(password, user.getLoginPw())) {

				HttpSession session = request.getSession();
				session.setAttribute("user", user);

				page = "/WEB-INF/jsp/home.jsp";

			} else {
				request.setAttribute(
						"message",
						"ログインIDまたはパスワードが正しくありません。");
			}

		} catch (IllegalArgumentException e) {
			// DBのパスワードがBCrypt形式ではない場合
			request.setAttribute(
					"message",
					"ログインIDまたはパスワードが正しくありません。");
		}

		return page;
	}

	/**
	 * 
	 * TODO
	 *
	 * @return String pageのURL 失敗：user_update.jsp 成功：users.jsp
	 */
	public String update() {

		String page = "/WEB-INF/jsp/user_update.jsp";

		UsersDTO loginUser = (UsersDTO) request.getSession().getAttribute("user");

		// Session失效检查
		if (loginUser == null) {
			request.setAttribute(
					"message",
					"ログイン情報がありません。再度ログインしてください。");
			return "/WEB-INF/jsp/login.jsp";
		}

		// 管理者権限の確認
		// false = 管理者という设计前提
		if (loginUser.getAuthority()) {
			request.setAttribute(
					"message",
					"更新失敗！管理者専用機能です。");
			return page;
		}

		try {
			int updatedId = Integer.parseInt(request.getParameter("userId"));

			UsersDTO updateUser = usersService.select(new UsersDTO(updatedId));

			if (updateUser == null) {
				request.setAttribute(
						"message",
						"更新対象のユーザーが見つかりません。");
				return page;
			}

			boolean newActiveStatus = "1".equals(request.getParameter("userStatus"));

			// 自分自身の無効化を禁止
			if (loginUser.getUserId() == updatedId
					&& !newActiveStatus) {

				request.setAttribute(
						"message",
						"自分を無効に変更できません。");
				return page;
			}

			//ページの情報をDTOに保存
			updateUser.setUserName(
					request.getParameter("userName"));

			updateUser.setMailAddress(
					request.getParameter("mailAddress"));

			updateUser.setActive(newActiveStatus);

			updateUser.setAuthority(
					"1".equals(request.getParameter("authority")));

			String newPassword = request.getParameter("loginPw");

			//パスワードをハッシュ化
			if (newPassword != null && !newPassword.isBlank()) {
				String hashedPassword = BCrypt.hashpw(
						newPassword,
						BCrypt.gensalt(12));

				updateUser.setLoginPw(hashedPassword);
			}
			//UPDATE実行
			if (usersService.update(updateUser)) {
				page = "/WEB-INF/jsp/users.jsp";
				request.setAttribute("message", "更新成功！");
			} else {
				request.setAttribute("message", "更新失敗。");
			}

		} catch (NumberFormatException e) {
			request.setAttribute(
					"message",
					"ユーザーIDが不正です。");
		}

		return page;
	}

	/**
	 * 新しいユーザー情報を追加
	 *
	 * @return String ページのURL 成功user.jsp 失敗user_regist.jsp
	 */
	public String insert() {

		String page = "/WEB-INF/jsp/user_regist.jsp";

		UsersDTO loginUser = (UsersDTO) request.getSession().getAttribute("user");

		if (loginUser == null) {
			request.setAttribute(
					"message",
					"ログイン情報がありません。再度ログインしてください。");
			return "/WEB-INF/jsp/login.jsp";
		}

		// 管理者権限の確認
		if (loginUser.getAuthority()) {
			request.setAttribute(
					"message",
					"登録失敗！管理者専用機能です。");
			return page;
		}

		String password = request.getParameter("loginPw");

		// 密码为空检查
		if (password == null || password.isBlank()) {
			request.setAttribute(
					"message",
					"パスワードを入力してください。");
			return page;
		}

		UsersDTO newUser = new UsersDTO();

		newUser.setLoginId(
				request.getParameter("loginId"));

		newUser.setUserName(
				request.getParameter("userName"));

		String hashedPassword = BCrypt.hashpw(
				password,
				BCrypt.gensalt(12));

		newUser.setLoginPw(hashedPassword);

		newUser.setMailAddress(
				request.getParameter("mailAddress"));

		newUser.setAuthority(
				"1".equals(request.getParameter("authority")));

		newUser.setActive(true);

		if (usersService.insert(newUser)) {
			page = "/WEB-INF/jsp/users.jsp";
			request.setAttribute(
					"message",
					"登録成功！");
		} else {
			request.setAttribute(
					"message",
					"登録失敗。");
		}

		return page;
	}

	/**
	 * ユーザーを無効化メソッド
	 *
	 * @return ページ
	 */
	public String invalid() {
		//失敗ページを設定
		String page = "/WEB-INF/jsp/user.jsp";
		//ログインユーザーの情報を保存
		UsersDTO loginUser = (UsersDTO) request.getSession().getAttribute("user");

		if (loginUser == null) {
			request.setAttribute(
					"message",
					"ログイン情報がありません。再度ログインしてください。");
			return "/WEB-INF/jsp/login.jsp";
		}
		// 管理者権限の確認
		if (loginUser.getAuthority()) {
			request.setAttribute(
					"message",
					"更新失敗！管理者専用機能です。");
			return page;
		}

		try {
			//無効化する人のid
			int updatedId = Integer.parseInt(request.getParameter("userId"));
			//idで全情報をゲット
			UsersDTO updateUser = usersService.select(new UsersDTO(updatedId));

			// 更新対象が存在しない場合、終了
			if (updateUser == null) {
				request.setAttribute(
						"message",
						"更新対象のユーザーが見つかりません。");
				return page;
			}

			if (loginUser.getUserId() == updatedId) {
				request.setAttribute(
						"message",
						"自分を無効に変更できません。");
				return page;
			}
			//無効化に設定
			updateUser.setActive(false);
			//更新実行、成功したらページを変更
			if (usersService.update(updateUser)) {
				page = "/WEB-INF/jsp/users.jsp";
				request.setAttribute("message", "更新成功！");
			} else {
				request.setAttribute("message", "更新失敗。");
			}

		} catch (NumberFormatException e) {
			request.setAttribute("message", "ユーザーIDが不正です。");
		}

		return page;
	}

}
