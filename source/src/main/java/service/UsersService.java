package service;

import java.util.ArrayList;

import dao.UsersDAO;
import dto.UsersDTO;

/**
 * 
 * UsersServiceクラス。
 * <p>
 * TODO DAOとActionを繋がるサービス層。条件・結果を判断、処理を行う。
 * </p>
 *
 * @author YSL黄范航
 */
public class UsersService {

	/**
	 * ログインのメソッド、ログインID・パスワードが入っているDTOで判断する
	 *
	 * @param uDTO(id,pw)
	 * @return UsersDTO
	 */
	public UsersDTO login(UsersDTO uDTO) {
		UsersDAO dao = new UsersDAO();

		if (dao.login(uDTO)) {
			return dao.selectOne(uDTO);
		} else {
			return null;
		}

	}

	/**
	 * 全てのユーザー情報を取得するメソッド
	 * 
	 * @return ArrayList<UsersDTO>
	 */
	public ArrayList<UsersDTO> selectAll() {

		UsersDAO dao = new UsersDAO();
		return new ArrayList<>(dao.selectAll());

	}

	/**
	 * 一件のユーザー情報を取得するメソッド
	 * @param UsersDTO （条件が入っているDTO）
	 * @return UsersDTO
	 */
	public UsersDTO select(UsersDTO uDTO) {
		UsersDTO user = null;

		UsersDAO dao = new UsersDAO();
		user = dao.selectOne(uDTO);

		return user;

	}

	/**
	 * データベースを新規登録を行うメソッド
	 * TODO DTOの中に必要なデータを保存して、更新件数を戻る
	 *
	 * @param uDTO
	 * @return 件数int
	 */
	public int insert(UsersDTO uDTO) {
		UsersDAO dao = new UsersDAO();
		int ans = 0;
		if (dao.check(uDTO)) {
			ans = dao.insert(uDTO);
		}

		return ans;
	}

	/**
	 * データベースを更新するメソッド
	 * TODO DTOの中に必要なデータを保存して、更新件数を戻る
	 *
	 * @param uDTO
	 * @return 件数int
	 */
	public int update(UsersDTO uDTO) {
		UsersDAO dao = new UsersDAO();
		int ans = 0;
		ans = dao.update(uDTO);
		return ans;
	}

	/**
	 * idで削除するメソッド
	 * 
	 *
	 * @param id
	 * @return 件数int 0の場合は失敗
	 */
	public int delete(String id) {
		UsersDAO dao = new UsersDAO();
		int ans = 0;
		ans = dao.delete(Integer.parseInt(id));
		return ans;
	}

}
