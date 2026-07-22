package dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import dao.utils.BaseDAO;
import dao.utils.Column;
import dao.utils.DBMapper;
import dto.UsersDTO;

/**
 * Usersテーブルを操作するDAOクラス。
 * <p>
 * UsersDTO情報の検索、条件検索、登録などの
 * データベース操作を行う。
 * </p>
 *
 * <p>
 * DB接続、PreparedStatement生成、パラメータ設定、
 * ResultSetからDTOへの変換処理は親クラスBaseDAOで行う。
 * このクラスでは主にSQL文と業務用メソッドを定義する。
 * </p>
 *
 * @author YSL黄范航
 */
public class UserDAO extends BaseDAO<UsersDTO> {

	/**
	 * UserDAOのコンストラクタ。
	 * <p>
	 * 親クラスBaseDAOにUsersDTOクラスを渡し、
	 * ResultSetからUsersDTOオブジェクトへ変換できるようにする。
	 * </p>
	 */
	public UserDAO() {
		super(UsersDTO.class);
	}

	/**
	 * ログインIDとパスワードを条件にUsersDTO情報を1件取得する、存在する場合、TRUEに戻る。
	 *
	 * @param UsersDTO uDto
	 * @return 検索結果があればTRUE・あるいはFALSE
	 */
	public boolean login(UsersDTO uDTO) {

		// ユーザーID検索SQL
		String sql = "SELECT * FROM users WHERE login_id = ? and login_pw = ?";

		// 単一レコード検索を実行
		if (selectOne(sql, uDTO.getLoginId(), uDTO.getLoginPw()) != null) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * UsersDTOテーブルの全件検索を行う。
	 *
	 * @return UsersDTO情報のリスト。データが存在しない場合は空のリスト
	 */
	public List<UsersDTO> selectAll() {

		// 全件取得SQL
		String sql = "SELECT * FROM users";

		// 複数件検索を実行
		return selectList(sql);
	}

	/**
	 * UsersDTOの中に保存された条件で情報を1件取得する。
	 * <p>
	 * ユーザーが複数存在する場合、
	 * 先頭の1件のみを取得する。
	 * </p>
	 *
	 * @param UsersDTO uDTO
	 * @return 該当するUsersDTOオブジェクト。存在しない場合はnull
	 */

	public UsersDTO selectOne(UsersDTO uDTO) {
		//FieldでDTOの構造を取る
		List<Field> fields = DBMapper.getNotEmptyFields(uDTO);
		//SQL文を用意
		StringBuilder sql = new StringBuilder(
				"SELECT * FROM users WHERE 1 = 1");
		//ループ用List
		List<Object> params = new ArrayList<>();
		//ループ
		for (Field field : fields) {
			try {
				field.setAccessible(true);

				Object value = field.get(uDTO);

				// @Columnからデータベースのカラム名を取得
				Column column = field.getAnnotation(Column.class);

				// @Columnがないフィールドは対象外
				if (column == null) {
					continue;
				}

				String columnName = column.value();

				// String型は部分一致検索
				if (value instanceof String) {
					sql.append(" AND ")
							.append(columnName)
							.append(" LIKE ?");

					params.add("%" + value + "%");

				} else {
					// String以外は完全一致検索
					sql.append(" AND ")
							.append(columnName)
							.append(" = ?");

					params.add(value);
				}

			} catch (IllegalAccessException e) {
				throw new RuntimeException(
						"検索条件の取得に失敗しました。フィールド名: "
								+ field.getName(),
						e);
			}
		}

		System.out.println("SQL: " + sql);
		System.out.println("params: " + params);

		return selectOne(sql.toString(), params.toArray());
	}

	/**
	 * UsersDTOの中に保存された条件で情報を複数件取得する。
	 *
	 * @param UsersDTO uDTO
	 * @return 該当するUsersDTOリスト。存在しない場合はnull
	 */
	public List<UsersDTO> selectList(UsersDTO uDTO) {
		//FieldでDTOの構造を取る
		List<Field> fields = DBMapper.getNotEmptyFields(uDTO);
		//SQL文を用意
		StringBuilder sql = new StringBuilder(
				"SELECT * FROM users WHERE 1 = 1");
		//ループ用List
		List<Object> params = new ArrayList<>();
		//ループ
		for (Field field : fields) {
			try {
				field.setAccessible(true);

				Object value = field.get(uDTO);

				// @Columnからデータベースのカラム名を取得
				Column column = field.getAnnotation(Column.class);

				// @Columnがないフィールドは対象外
				if (column == null) {
					continue;
				}

				String columnName = column.value();

				// String型は部分一致検索
				if (value instanceof String) {
					sql.append(" AND ")
							.append(columnName)
							.append(" LIKE ?");

					params.add("%" + value + "%");

				} else {
					// String以外は完全一致検索
					sql.append(" AND ")
							.append(columnName)
							.append(" = ?");

					params.add(value);
				}

			} catch (IllegalAccessException e) {
				throw new RuntimeException(
						"検索条件の取得に失敗しました。フィールド名: "
								+ field.getName(),
						e);
			}
		}

		System.out.println("SQL: " + sql);
		System.out.println("params: " + params);

		return selectList(sql.toString(), params.toArray());
	}

	/**
	 * ログインIDを条件にUsersDTO情報を1件取得する、存在する場合、FALSEに戻る。
	 * 重複しているIDがあるかどうか検証するメソッド
	 *
	 * @param UsersDTO uDto
	 * @return 検索結果があればFALSE・あるいはTRUE
	 */
	public boolean check(UsersDTO uDTO) {

		// ユーザーID検索SQL
		String sql = "SELECT * FROM users WHERE login_id = ?";

		// 単一レコード検索を実行
		if (selectOne(sql, uDTO.getLoginId()) != null) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * UsersDTO情報を新規登録する。
	 *
	 * @param user 登録対象のUsersDTOオブジェクト
	 * @return 登録された件数。通常は1
	 */
	public int insert(UsersDTO user) {

		// 登録SQL
		String sql = "INSERT INTO users (\n"
				+ "    login_id,\n"
				+ "    login_pw,\n"
				+ "    user_name,\n"
				+ "    mail_address,\n"
				+ "    authority,\n"
				+ "    active\n"
				+ ") VALUES(?,?,?,?,?,?)";

		// 更新処理を実行
		return executeUpdate(sql, user.getLoginId(), user.getLoginPw(), user.getUserName(), user.getMailAddress(),
				user.getAuthority(), true);
	}

	/**
	 * UsersDTO情報を新規登録する。
	 *
	 * @param user 登録対象のUsersDTOオブジェクト
	 * @return 登録された件数。通常は1
	 */
	public int update(UsersDTO user) {

		// 登録SQL
		String sql = "UPDATE users " +
				"SET login_id=?, login_pw=?, user_name=?, mail_address=?, authority=?, active=? " +
				"WHERE id=?";
		// 更新処理を実行
		return executeUpdate(sql, user.getLoginId(), user.getLoginPw(), user.getUserName(), user.getMailAddress(),
				user.getAuthority(), user.getActive(), user.getUserId());
	}

	/**
	 * UsersDTO情報を削除する。
	 *
	 * @param 削除したいUserのID
	 * @return 削除された件数。通常は1
	 */
	public int delete(int userId) {

		// 登録SQL
		String sql = "DELETE FROM users WHERE id = ?";

		// 更新処理を実行
		return executeUpdate(sql, userId);
	}
}
