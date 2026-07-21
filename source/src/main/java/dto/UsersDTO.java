package dto;

import dao.utils.Column;

/**
 * usersテーブルのユーザー情報を保持するJavaBeansクラスです。
 *
 * <p>保持する項目：</p>
 * <ul>
 *   <li>userId：ユーザーID</li>
 *   <li>loginId：ログインID</li>
 *   <li>loginPw：ログインパスワード</li>
 *   <li>userName：ユーザー名</li>
 *   <li>mailAddress：メールアドレス</li>
 *   <li>authority：ユーザー権限</li>
 *   <li>active：有効状態</li>
 *   <li>createdAt：作成日時</li>
 *   <li>updateAt：更新日時</li>
 * </ul>
 */
public class UsersDTO {
	/**
	 * ユーザーID。
	 * usersテーブルの id カラムに対応します。
	 */
	@Column("id")
	private Integer userId;

	/**
	 * ログインID。
	 * usersテーブルの login_id カラムに対応します。
	 */
	@Column("login_id")
	private String loginId;

	/**
	 * ハッシュ化されたログインパスワード。
	 * usersテーブルの login_pw カラムに対応します。
	 */
	@Column("login_pw")
	private String loginPw;

	/**
	 * ユーザー名。
	 * usersテーブルの user_name カラムに対応します。
	 */
	@Column("user_name")
	private String userName;

	/**
	 * メールアドレス。
	 * usersテーブルの mail_address カラムに対応します。
	 */
	@Column("mail_address")
	private String mailAddress;

	/**
	 * ユーザー権限。
	 * usersテーブルの authority カラムに対応します。
	 */
	@Column("authority")
	private Boolean authority;

	/**
	 * ユーザーの有効状態。
	 * usersテーブルの active カラムに対応します。
	 */
	@Column("active")
	private Boolean active;

	/**
	 * 作成日時。
	 * usersテーブルの created_at カラムに対応します。
	 */
	@Column("created_at")
	private String createdAt;

	/**
	 * 更新日時。
	 * usersテーブルの update_at カラムに対応します。
	 */
	@Column("update_at")
	private String updateAt;

	/**
	 * デフォルトコンストラクタ。
	 */
	public UsersDTO() {
	}

	/**
	 * 全フィールドを指定してユーザーDTOを生成します。
	 *
	 * @param userId      ユーザーID
	 * @param loginId     ログインID
	 * @param loginPw     ハッシュ化されたログインパスワード
	 * @param userName    ユーザー名
	 * @param mailAddress メールアドレス
	 * @param authority   ユーザー権限
	 * @param active      有効状態
	 * @param createdAt   作成日時
	 * @param updateAt    更新日時
	 */
	public UsersDTO(
			Integer userId,
			String loginId,
			String loginPw,
			String userName,
			String mailAddress,
			Boolean authority,
			Boolean active,
			String createdAt,
			String updateAt) {

		this.userId = userId;
		this.loginId = loginId;
		this.loginPw = loginPw;
		this.userName = userName;
		this.mailAddress = mailAddress;
		this.authority = authority;
		this.active = active;
		this.createdAt = createdAt;
		this.updateAt = updateAt;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getLoginPw() {
		return loginPw;
	}

	public void setLoginPw(String loginPw) {
		this.loginPw = loginPw;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public Boolean getAuthority() {
		return authority;
	}

	public void setAuthority(Boolean authority) {
		this.authority = authority;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(String updateAt) {
		this.updateAt = updateAt;
	}

	/**
	 * DTOの内容を文字列として返します。
	 * セキュリティ上の理由により、ログインパスワードは出力しません。
	 *
	 * @return DTOの文字列表現
	 */
	@Override
	public String toString() {
		return "UsersDTO{" +
				"userId=" + userId +
				", loginId='" + loginId + '\'' +
				", userName='" + userName + '\'' +
				", mailAddress='" + mailAddress + '\'' +
				", authority=" + authority +
				", active=" + active +
				", createdAt='" + createdAt + '\'' +
				", updateAt='" + updateAt + '\'' +
				'}';
	}
}