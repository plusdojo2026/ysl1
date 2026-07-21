package service;


import java.sql.SQLException;
import java.util.ArrayList;

import dao.UserDAO;
import dto.AllDTO;

	

public class TasksService extends DBAccess{
	
	//空のコンストラクタを生成
	public TasksService() {
		super.access();
	}
	
	//一覧表示メソッド---------------------------
	public ArrayList<AllDTO> selectAll() {
		ArrayList<AllDTO> taskList = null;
		
		UserDAO dao = new UserDAO(super.conn);
		try {
			taskList = dao.selectAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		super.close();
		
		return taskList ;	
	}
}
