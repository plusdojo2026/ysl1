package service;


import java.sql.SQLException;
import java.util.ArrayList;

import dao.TasksDAO;
import dto.AllDTO;
import dto.CasesDTO;
import dto.TasksDTO;
import dto.UsersDTO;

	

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

    /**
     * タスク詳細を取得する
     *
     * @param id
     * @return detailsList
     *
     * @author haruto.tanaka
     */
    public ArrayList<AllDTO> details(int id) {
        ArrayList<AllDTO> detailsList = null;
        TasksDAO dao = new TasksDAO(super.conn);

        try {
            detailsList = dao.details(id);
        } catch (SQLException e) {
            System.out.println("SQL文おかしいよ");
            e.printStackTrace();
        } finally {
            super.close();
        }

        return detailsList;
    }

	
	/**
	 * - 登録されている全案件の名前を取得する -
	 * 
	 * @return casesList;
	 * 
	 * 
	 * @author haruto.tanaka
	 */
	public ArrayList<CasesDTO> selectCases() {
        ArrayList<CasesDTO> casesList = null;

        TasksDAO dao = new TasksDAO(super.conn);
        try {
            casesList = dao.selectCases();
        } catch (SQLException e) {
            System.out.println("SQL文おかしいよ");
            e.printStackTrace();
        } finally {
            super.close();
        }

        return casesList;
    }

	/**
	 * - 登録されているすべてのPMの名前を取得する
	 * 
	 * @return pmList;
	 * 
	 * 
	 * @author haruto.tanaka
	 */
	public ArrayList<UsersDTO> selectPM() {
        ArrayList<UsersDTO> pmList = null;

        TasksDAO dao = new TasksDAO(super.conn);
        try {
            pmList = dao.selectPM();
        } catch (SQLException e) {
            System.out.println("SQL文おかしいよ");
            e.printStackTrace();
        } finally {
            super.close();
        }

        return pmList;
    }
	

	/**
	 * - 選択したタスクIDのレコードの詳細を取得する
	 * 
	 * @return taskList;
	 * 
	 * 
	 * @author haruto.tanaka
	 */
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
    
    /**
     * - タスクを登録する -
     *
     * @return true:成功 false:失敗
     *
     *
     * @author haruto.tanaka
     */
    public boolean insert(TasksDTO tDTO) {
        boolean result = false;
        TasksDAO dao = new TasksDAO(super.conn);

        try {
            result = dao.insert(tDTO);
        } catch (SQLException e) {
            System.out.println("SQL文おかしいよ");
            e.printStackTrace();
        } finally {
            super.close();
        }

        return result;
    }
    
    /**
     * タスクを更新する
     *
     * @return true:成功 false:失敗
     *
     *
     * @author haruto.tanaka
     */
    public boolean update(TasksDTO tDTO) {
        boolean result = false;
        TasksDAO dao = new TasksDAO(super.conn);

        try {
            result = dao.update(tDTO);
        } catch (SQLException e) {
            System.out.println("SQL文おかしいよ");
            e.printStackTrace();
        } finally {
            super.close();
        }

        return result;
    }
}
