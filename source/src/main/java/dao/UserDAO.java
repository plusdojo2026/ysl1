package dao;

import java.util.List;

import dao.utils.BaseDAO;
import dto.Users;

/**
 * Usersテーブルを操作するDAOクラス。
 * <p>
 * Users情報の検索、条件検索、登録などの
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
public class UserDAO extends BaseDAO<Users> {

	/**
	 * UserDAOのコンストラクタ。
	 * <p>
	 * 親クラスBaseDAOにUsersクラスを渡し、
	 * ResultSetからUsersオブジェクトへ変換できるようにする。
	 * </p>
	 */
	public UserDAO() {
		super(Users.class);
	}

	/**
	 * Usersテーブルの全件検索を行う。
	 *
	 * @return Users情報のリスト。データが存在しない場合は空のリスト
	 */
	public List<Users> search() {

		// 全件取得SQL
		String sql = "SELECT * FROM users";

		// 複数件検索を実行
		return selectList(sql);
	}

	/**
	 * ユーザーIDを条件にUsers情報を1件取得する。
	 *
	 * @param userId 検索対象のユーザーID
	 * @return 該当するUsersオブジェクト。存在しない場合はnull
	 */
	public Users findById(int userId) {

		// ユーザーID検索SQL
		String sql = "SELECT * FROM users WHERE id = ?";

		// 単一レコード検索を実行
		return selectOne(sql, userId);
	}

	/**
	 * 年齢を条件にUsers情報を1件取得する。
	 * <p>
	 * 同じ年齢のユーザーが複数存在する場合、
	 * 先頭の1件のみを取得する。
	 * </p>
	 *
	 * @param age 検索対象の年齢
	 * @return 該当するUsersオブジェクト。存在しない場合はnull
	 */
	public Users findByAge(int age) {

		// 年齢検索SQL
		String sql = "SELECT * FROM users WHERE age = ?";

		// 単一レコード検索を実行
		return selectOne(sql, age);
	}


	/**
	 * Users情報を新規登録する。
	 *
	 * @param user 登録対象のUsersオブジェクト
	 * @return 登録された件数。通常は1
	 */
	public int insert(Users user) {

		// 登録SQL
		String sql = "INSERT INTO users(name, age) VALUES(?, ?)";

		// 更新処理を実行
		return executeUpdate(sql, user.getUserName(), user.getAge());
	}
}
