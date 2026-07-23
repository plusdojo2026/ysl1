package dao;

import java.util.List;

import dao.utils.BaseDAO;
import dto.UsersDTO;

/**
 * Usersテーブルを操作するDAOクラス。
 *
 * <p>
 * UsersDTO情報の検索、条件検索、登録、更新、削除などの
 * データベース操作を行う。
 * </p>
 *
 * <p>
 * DB接続、PreparedStatement生成、パラメータ設定、
 * ResultSetからDTOへの変換処理は親クラスBaseDAOで行う。
 * </p>
 *
 * <p>
 * テーブル名はUsersDTOに定義された@Tableから自動取得する。
 * </p>
 *
 * @author YSL黄范航
 */
public class UsersDAO extends BaseDAO<UsersDTO> {

	/**
	 * UsersDAOのコンストラクタ。
	 *
	 * <p>
	 * 親クラスBaseDAOにUsersDTOクラスを渡す。
	 * BaseDAOはUsersDTOに設定された@Tableから
	 * テーブル名を自動取得する。
	 * </p>
	 */
	public UsersDAO() {
		super(UsersDTO.class);
	}

	/**
	 * ログインIDとパスワードを条件に、
	 * ユーザーが存在するか確認する。
	 *
	 * @param user ログイン情報
	 * @return ユーザーが存在する場合UsersDTO、それ以外null
	 */
	public UsersDTO login(UsersDTO user) {

		//SQL文作成・テーブル名はDTOクラス名の上に@Tableがある
		String sql = """
				SELECT *
				FROM %s
				WHERE login_id = ?
				  AND login_pw = ?
				  AND active = ?
				""".formatted(getTableName());

		return selectOne(sql, user.getLoginId(), user.getLoginPw(), true);
	}

	/**
	 * Usersテーブルの全件検索を行う。
	 *
	 * @return UsersDTO情報のリスト。
	 *         データが存在しない場合は空のリスト
	 */
	public List<UsersDTO> selectAll() {
		return super.selectAll();
	}

	/**
	 * UsersDTOに設定された条件で情報を1件取得する。
	 *
	 * <p>
	 * String型の項目は部分一致検索、
	 * String型以外の項目は完全一致検索を行う。
	 * </p>
	 *
	 * <p>
	 * 複数件存在する場合は先頭の1件を返す。
	 * </p>
	 *
	 * @param user 検索条件
	 * @return 該当するUsersDTO。
	 *         存在しない場合はnull
	 */
	public UsersDTO selectOne(UsersDTO user) {

		return selectOneByCondition(user);

	}

	/**
	 * UsersDTOに設定された条件で情報を複数件取得する。
	 *
	 * <p>
	 * String型の項目は部分一致検索、
	 * String型以外の項目は完全一致検索を行う。
	 * </p>
	 *
	 * @param user 検索条件
	 * @return 条件に一致するUsersDTOのリスト。
	 *         存在しない場合は空のリスト
	 */
	public List<UsersDTO> selectList(UsersDTO user) {
		return selectByCondition(user);
	}

	/**
	 * ログインIDがすでに登録されているか確認する。
	 *
	 * @param user 確認対象ユーザー
	 * @return 登録可能な場合true、
	 *         同じログインIDが存在する場合false
	 */
	public boolean check(UsersDTO user) {

		String sql = """
				SELECT COUNT(*)
				FROM %s
				WHERE login_id = ?
				""".formatted(getTableName());

		Long count = selectValue(
				sql,
				Long.class,
				user.getLoginId());

		return count == null || count == 0;
	}

	/**
	 * UsersDTO情報を新規登録する。
	 *
	 * @param user 登録対象のUsersDTO
	 * @return 登録に成功した場合true
	 */
	public boolean insert(UsersDTO user) {
		String sql = """
				INSERT INTO %s (
				    login_id,
				    login_pw,
				    user_name,
				    mail_address,
				    authority,
				    active
				)
				VALUES (?, ?, ?, ?, ?, ?)
				""".formatted(getTableName());
		//activeの初期設定,nullの場合false、それ以外の場合userのactiveを使用
		Boolean active = user.getActive() == null ? true : user.getActive();

		int result = executeUpdate(
				sql,
				user.getLoginId(),
				user.getLoginPw(),
				user.getUserName(),
				user.getMailAddress(),
				user.getAuthority(),
				active);

		return result > 0;
	}

	/**
	 * UsersDTO情報を更新する。
	 *
	 * @param user 更新対象のUsersDTO
	 * @return 更新に成功した場合true
	 */
	public boolean update(UsersDTO user) {

		String sql = """
				UPDATE %s
				SET
				    login_id = ?,
				    login_pw = ?,
				    user_name = ?,
				    mail_address = ?,
				    authority = ?,
				    active = ?
				WHERE id = ?
				""".formatted(getTableName());

		int result = executeUpdate(
				sql,
				user.getLoginId(),
				user.getLoginPw(),
				user.getUserName(),
				user.getMailAddress(),
				user.getAuthority(),
				user.getActive(),
				user.getUserId());

		return result > 0;
	}

	/**
	 * 指定されたユーザーIDのユーザーを削除する。
	 *
	 * @param userId 削除対象ユーザーID
	 * @return 削除に成功した場合true
	 */
	public boolean delete(int userId) {

		String sql = """
				DELETE FROM %s
				WHERE id = ?
				""".formatted(getTableName());

		return executeUpdate(sql, userId) > 0;
	}

}