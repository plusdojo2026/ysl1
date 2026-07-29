package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.UsersDAO;
import dao.utils.DBUtils;
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
	public UsersDTO login(UsersDTO user) {

		if (user == null
				|| user.getLoginId() == null
				|| user.getLoginId().isBlank()) {

			return null;
		}

		UsersDAO dao = new UsersDAO();
		return dao.login(user);
	}

	/**
	 * 全てのユーザー情報を取得するメソッド
	 * 
	 * @return ArrayList<UsersDTO>
	 */
	public List<UsersDTO> selectAll() {

		UsersDAO dao = new UsersDAO();
		return dao.selectAll();

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
	public boolean insert(UsersDTO uDTO) {
		UsersDAO dao = new UsersDAO();
		boolean ans = false;
		Connection conn = null;
		try {
			conn = DBUtils.getConnection();
			DBUtils.setAutoCommit(conn, false);
			if (uDTO == null
					|| uDTO.getLoginId() == null
					|| uDTO.getLoginId().isBlank()) {

				return ans;
			} else if (dao.check(uDTO)) {
				ans = dao.insert(conn, uDTO);
				if (ans) {
					DBUtils.commit(conn);
				} else {
					DBUtils.rollback(conn);
				}
			}
		} catch (Exception e) {
			DBUtils.rollback(conn);
		} finally {
			DBUtils.close(conn);
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
	public boolean update(UsersDTO uDTO) {
		Connection conn = null;
		boolean ans = false;
		try {
			conn = DBUtils.getConnection();
			DBUtils.setAutoCommit(conn, false);
			UsersDAO dao = new UsersDAO();

			if (!(uDTO == null
					|| uDTO.getUserId() == null
					|| uDTO.getUserId() <= 0)) {

				ans = dao.update(conn, uDTO);
				//ansの結果でトランザクションを判断
				if (ans) {
					DBUtils.commit(conn);
				} else {
					DBUtils.rollback(conn);
				}

			}
		} catch (Exception e) {
			DBUtils.rollback(conn);

		} finally {
			DBUtils.close(conn);

		}
		return ans;
	}

	/**
	 * idで削除するメソッド
	 * 
	 *
	 * @param id
	 * @return 件数int 0の場合は失敗
	 */
	public boolean delete(String id) {
		Connection conn = null;
		try {
			conn = DBUtils.getConnection();
			DBUtils.setAutoCommit(conn, false);
			UsersDAO usersDAO = new UsersDAO();
			//id数値チェック
			if (id == null || id.isBlank()) {
				return false;
			}

			//String→int
			int userId = Integer.parseInt(id);

			if (userId <= 0) {
				return false;
			}

			//実行
			boolean ans = usersDAO.delete(conn, userId);

			if (ans) {
				DBUtils.commit(conn);
			} else {
				DBUtils.rollback(conn);
			}

			return ans;
		} catch (NumberFormatException | ClassNotFoundException | SQLException e) {

			//転換失敗の場合FALSE
			DBUtils.rollback(conn);
			return false;
		} finally {
			DBUtils.close(conn);
		}
	}

}
