package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

import dao.CasesDAO;
import dao.utils.DBUtils;
import dto.AllDTO;

public class CasesService {

	//ページを開いた時に表示するもののメソッド
	public ArrayList<AllDTO> intiCasesDetail() {
		//一旦空にする
		ArrayList<AllDTO> casesList = null;
		ArrayList<AllDTO> tasksList = null;
		ArrayList<E>
		// データベースに接続する
		// DBUtilsで接続
		Connection conn = DBUtils.getConnection();
		CasesDAO dao = new CasesDAO(conn);

		casesList = dao.select();
		tasksList
		list
		//まとめたリストの文

		return casesList;

	}

}
