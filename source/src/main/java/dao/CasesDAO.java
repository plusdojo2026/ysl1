package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.AllDTO;

public class CasesDAO {

	public Connection conn = null;

	//コネクションを保持するコンストラクタ
	public CasesDAO(Connection conn) {
		this.conn = conn;
	}

	//ページを開いた時に表示するもののメソッド
	public ArrayList<AllDTO> selectCases() throws SQLException {
		ArrayList<AllDTO> allDTOs = new ArrayList<AllDTO>();
		String sql = "select * from cases";
		// まとめる
		PreparedStatement pStmt = conn.prepareStatement(sql);
		// SELECT文を実行し、結果表を取得する
		ResultSet rs = pStmt.executeQuery();

		while (rs.next()) {
			AllDTO dto = new AllDTO();
			dto.setCaseId(rs.getInt("id"));

			allDTOs.add(dto);
		}
		return allDTOs;
	}

	//ページを開いた時に表示するもののメソッド
	public ArrayList<AllDTO> selectCases() throws SQLException {
		ArrayList<AllDTO> allDTOs = new ArrayList<AllDTO>();
		String sql = "select * from cases WHERE";
		// まとめる
		PreparedStatement pStmt = conn.prepareStatement(sql);
		// SELECT文を実行し、結果表を取得する
		ResultSet rs = pStmt.executeQuery();

		while (rs.next()) {
			AllDTO dto = new AllDTO();
			dto.setCaseId(rs.getInt("id"));

			allDTOs.add(dto);
		}
		return allDTOs;
	}
}
