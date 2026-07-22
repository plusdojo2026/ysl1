package service;


import java.sql.SQLException;
import java.util.ArrayList;

import dao.TasksDAO;
import dto.AllDTO;

	

public class TasksService extends DBAccess{
	
	//空のコンストラクタを生成
	public TasksService() {
		super.access();
	}
	
	
	/**
	 * 一覧表示メソッド
	 * @return ArrayList<AllDTO>
	 */
	public ArrayList<AllDTO> selectAll() {
		ArrayList<AllDTO> taskList = null;
		
		TasksDAO dao = new TasksDAO(super.conn);
	
		
		try {
			
			taskList = dao.selectAll();
		} catch (SQLException e) {
			System.out.println("SQL文おかしいよ");
			e.printStackTrace(); //エラーの説明をしてくれる
		} finally {
			super.close();
		}
		
		return taskList ;	
	}
	
	
}
