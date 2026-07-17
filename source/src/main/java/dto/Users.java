package dto;

import dao.utils.Column;

/**
 * UsersDTO ユーザーオブジェクトのJAVAbeans
 * @param userId
 * @param age
 * @param userName
 * @author YSL黄范航
 */
public class Users {

	@Column("id")
	private int userId;//データベース名id

	@Column("age")
	private int age;

	@Column("name")
	private String userName;//データベース名name

	public Users() {
		super();
	}

	public Users(int age, String userName) {
		super();
		this.age = age;
		this.userName = userName;
	}

	public Users(int userId, int age, String userName) {
		super();
		this.userId = userId;
		this.age = age;
		this.userName = userName;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	/**userNameをゲットするメソッド
	 * @return String
	 */
	public String getUserName() {
		return userName;
	}

	/**userNameをセットするメソッド
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 *toString 変数を文字列に変換するメソッド
	 */
	@Override
	public String toString() {
		return "Users [id=" + userId + ", age=" + age + ", username=" + userName + "]";
	}

}
