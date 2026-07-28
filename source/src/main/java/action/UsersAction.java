package action;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
	/**
	 * ログイン処理を実行する。
	 *
	 * @return 遷移先ページ
	 */
	public String login() {

		String page = "/WEB-INF/jsp/login.jsp";

		/* 入力値を取得する */
		String loginId = request.getParameter("loginId");
		String password = request.getParameter("loginPw");

		boolean hasError = false;

		/* ログインIDの未入力チェック */
		if (loginId == null || loginId.isBlank()) {
			request.setAttribute(
					"errorMsgId",
					"ログインIDを入力してください。");

			hasError = true;
		}

		/* パスワードの未入力チェック */
		if (password == null || password.isBlank()) {
			request.setAttribute(
					"errorMsgPw",
					"パスワードを入力してください。");

			hasError = true;
		}

		/* 入力エラーがある場合はログイン画面へ戻る */
		if (hasError) {
			return page;
		}

		/* ログインIDをDTOへ設定する */
		UsersDTO loginUser = new UsersDTO();
		loginUser.setLoginId(loginId.trim());

		/* ログインIDに対応するユーザーを検索する */
		UsersDTO user = usersService.login(loginUser);

		try {
			/*
			 * セキュリティ上、ユーザーが存在しない場合と
			 * パスワードが間違っている場合は同じメッセージを表示する。
			 */
			if (user == null || user.getLoginPw() == null) {
				request.setAttribute(
						"errorMsgLogin",
						"ログインIDまたはパスワードが正しくありません。");
				System.out.println("ログインIDまたはパスワードが正しくありません。");

				return page;
			}

			/* パスワードを確認する */
			if (!BCrypt.checkpw(password, user.getLoginPw())) {
				request.setAttribute(
						"errorMsgLogin",
						"ログインIDまたはパスワードが正しくありません。");

				return page;
			}

			/* ログイン成功時にユーザー情報をセッションへ保存する */
			HttpSession session = request.getSession();
			session.setAttribute("user", user);

			page = "/WEB-INF/jsp/home.jsp";

		} catch (IllegalArgumentException e) {

			/*
			 * DBに保存されているパスワードが
			 * BCrypt形式ではない場合に表示する。
			 */
			request.setAttribute(
					"errorMsgSystem",
					"ログイン処理中にエラーが発生しました。"
							+ "管理者へお問い合わせください。");

		} catch (Exception e) {

			/* 予期しないエラーが発生した場合 */
			request.setAttribute(
					"errorMsgSystem",
					"システムエラーが発生しました。"
							+ "しばらくしてから再度お試しください。");
		}

		return page;
	}

	public String selectAll() {
		String page = "/WEB-INF/jsp/home.jsp";
		HttpSession session = request.getSession();
		UsersDTO loginUser = (UsersDTO) session.getAttribute("user");
		if (loginUser.getAuthority()) {
			request.setAttribute("message", "管理者専用機能です。");
		} else {
			request.setAttribute("userList", usersService.selectAll());
			page = "/WEB-INF/jsp/user.jsp";
		}
		return page;
	}

	/**
	 * updateページの初期化メッセージ
	 * 
	 *
	 * @return String IDでサーチ、成功後、updateページに遷移する。失敗の場合、user一覧に戻る。
	 */
	public String updateInit() {
		//userIdを取る
		UsersDTO dto = new UsersDTO(request.getParameter("userId"));
		//page設定
		String page = "/WEB-INF/jsp/user.jsp";
		//id取得成功の場合
		if (dto.getUserId() != null) {
			//更新するユーザーの全情報を保存
			request.setAttribute("updateUser", usersService.select(dto));
			page = "/WEB-INF/jsp/user_update.jsp";
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

		String page = updateInit();

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

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
					"yyyy-MM-dd HH:mm:ss");

			updateUser.setUpdateAt(
					LocalDateTime.now().format(formatter));
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
				page = selectAll();
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

		// セッションチェック
		if (loginUser == null) {
			request.setAttribute(
					"message",
					"ログイン情報がありません。再度ログインしてください。");

			return "/WEB-INF/jsp/login.jsp";
		}

		// 管理者権限チェック
		// false = 管理者という設計
		if (loginUser.getAuthority()) {
			request.setAttribute(
					"message",
					"登録失敗！管理者専用機能です。");

			return page;
		}

		// 入力値を取得
		String loginId = request.getParameter("loginId");

		String userName = request.getParameter("userName");

		String mailAddress = request.getParameter("mailAddress");

		String password = request.getParameter("loginPw");

		String authority = request.getParameter("authority");

		boolean hasError = false;

		// ログインID：
		// 4～20文字の半角英数字
		if (loginId == null || loginId.isBlank()) {

			request.setAttribute(
					"errorMsgLoginId",
					"ログインIDを入力してください。");

			hasError = true;

		} else if (!loginId.matches("^[A-Za-z0-9]{4,20}$")) {

			request.setAttribute(
					"errorMsgLoginId",
					"ログインIDは4～20文字の半角英数字で入力してください。");

			hasError = true;
		}

		// 氏名：
		// 必須、50文字以内
		if (userName == null || userName.isBlank()) {

			request.setAttribute(
					"errorMsgName",
					"氏名を入力してください。");

			hasError = true;

		} else if (userName.trim().length() > 50) {

			request.setAttribute(
					"errorMsgName",
					"氏名は50文字以内で入力してください。");

			hasError = true;
		}

		// メールアドレス：
		// 空欄を許可し、入力された場合のみ形式確認
		if (mailAddress != null
				&& !mailAddress.isBlank()
				&& !mailAddress.matches(
						"^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {

			request.setAttribute(
					"errorMsgMail",
					"メールアドレスの形式が正しくありません。");

			hasError = true;
		}

		// 初期パスワード：
		// 8～20文字、半角英字と数字を両方含む
		if (password == null || password.isBlank()) {

			request.setAttribute(
					"errorMsgLoginPw",
					"初期パスワードを入力してください。");

			hasError = true;

		} else if (!password.matches(
				"^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]{8,20}$")) {

			request.setAttribute(
					"errorMsgLoginPw",
					"パスワードは8～20文字の半角英数字で、"
							+ "英字と数字を両方含めてください。");

			hasError = true;
		}

		// 権限：
		// 0 = 管理者、1 = 一般
		if (authority == null
				|| (!"0".equals(authority)
						&& !"1".equals(authority))) {

			request.setAttribute(
					"errorMsgAuthority",
					"権限を選択してください。");

			hasError = true;
		}

		// 入力エラーがある場合は登録画面へ戻る
		if (hasError) {
			return page;
		}

		try {
			UsersDTO newUser = new UsersDTO();

			newUser.setLoginId(loginId.trim());
			newUser.setUserName(userName.trim());

			// パスワードをハッシュ化
			String hashedPassword = BCrypt.hashpw(
					password,
					BCrypt.gensalt(12));

			newUser.setLoginPw(hashedPassword);

			// メールアドレスが空欄の場合はnullにする
			if (mailAddress == null || mailAddress.isBlank()) {
				newUser.setMailAddress(null);
			} else {
				newUser.setMailAddress(mailAddress.trim());
			}

			// 0 = 管理者(false)
			// 1 = 一般(true)
			newUser.setAuthority("1".equals(authority));

			// 新規登録時は有効
			newUser.setActive(true);

			if (usersService.insert(newUser)) {

				page = selectAll();

				request.setAttribute(
						"message",
						"登録成功！");

			} else {

				request.setAttribute(
						"message",
						"登録失敗。");
			}

		} catch (Exception e) {

			request.setAttribute(
					"message",
					"登録処理中にエラーが発生しました。");
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
		String page = selectAll();
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
				page = selectAll();
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
