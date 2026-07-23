package service;


import java.sql.SQLException;
import java.util.ArrayList;

import dao.TasksDAO;
import dto.AllDTO;
import dto.TasksDTO;

	

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
	
	public ArrayList<AllDTO> regist() {
        ArrayList<AllDTO> casesList = null;
        ArrayList<AllDTO> pmList = null;

        TasksDAO dao = new TasksDAO(super.conn);
        try {
            casesList = dao.selectAllCases();
            pmList = dao.selectAllPM();
        } catch (SQLException e) {
            System.out.println("SQL文おかしいよ");
            e.printStackTrace();
        } finally {
            super.close();
        }

        return List;
    }

    public ArrayList<TasksDTO> edit(int id) {
        ArrayList<TasksDTO> taskList = null;

        TasksDAO dao = new TasksDAO(super.conn);
        try {
            taskList = dao.selectByTaskID(id);
        } catch (SQLException e) {
            System.out.println("SQL文おかしいよ");
            e.printStackTrace();
        } finally {
            super.close();
        }

        return taskList;
    }
}
