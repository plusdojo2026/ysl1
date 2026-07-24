package test;

import org.mindrot.jbcrypt.BCrypt;

/**
 * 
 * BCryptTestクラス。
 * <p>
 * TODO String passwordの後ろに転換したいパスワードを入力、JAVAアプリケーションで実行、結果は変換後のパスワード。
 * </p>
 *
 * @author YSL黄范航
 */
public class BCryptTest {

	public static void main(String[] args) {

		String password = "adminpass999";

		String hash = BCrypt.hashpw(
				password,
				BCrypt.gensalt(12));

		System.out.println("hash：");
		System.out.println(hash);

		boolean result = BCrypt.checkpw(password, hash);

		System.out.println("password: " + password + "\n result：" + result);
	}
}