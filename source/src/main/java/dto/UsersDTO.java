package dto;

import dao.utils.Column;

/**
 * UsersDTO ユーザーオブジェクトのJAVAbeans
 * @param userId
 * @param age
 * @param userName
 * @author YSL黄范航
 */
public class UsersDTO {

	@Column("id")
	//データベース名id
	private int userId;

	@Column("login_id")
	//データベース名login_id
	private String loginId;

	@Column("login_pw")
	//データベース名login_pw
	private String loginPw;

	@Column("user_name")
	//データベース名user_name
	private int userName;

	@Column("mail_address")
	//データベース名mail_address
	private String mailAddress;

	@Column("authority")
	//データベース名authority
	private boolean authority;

	@Column("active")
	//データベース名active
	private boolean active;

	@Column("created_at")
	//データベース名created_at
	private String created_at;

	@Column("update_at")
	//データベース名user_name
	private String update_at;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
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

	public int getUserName() {
		return userName;
	}

	public void setUserName(int userName) {
		this.userName = userName;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public boolean isAuthority() {
		return authority;
	}

	public void setAuthority(boolean authority) {
		this.authority = authority;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getUpdate_at() {
		return update_at;
	}

	public void setUpdate_at(String update_at) {
		this.update_at = update_at;
	}

	public UsersDTO(int userId, String loginId, String loginPw, int userName, String mailAddress, boolean authority,
			boolean active, String created_at, String update_at) {
		super();
		this.userId = userId;
		this.loginId = loginId;
		this.loginPw = loginPw;
		this.userName = userName;
		this.mailAddress = mailAddress;
		this.authority = authority;
		this.active = active;
		this.created_at = created_at;
		this.update_at = update_at;
	}

	public UsersDTO() {
		super();
	}

	/**
	 *toString 変数を文字列に変換するメソッド
	 */
	@Override
	public String toString() {
		return "UsersDTO [userId=" + userId + ", loginId=" + loginId + ", loginPw=" + loginPw + ", userName=" + userName
				+ ", mailAddress=" + mailAddress + ", authority=" + authority + ", active=" + active + ", created_at="
				+ created_at + ", update_at=" + update_at + "]";
	}

}
