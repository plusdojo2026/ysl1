package service;

import java.sql.SQLException;

import dao.UserDAO;
import dao.WorksDAO;

public class WorksService extends DBAccess{
	
	public WorksService() {
		super.access();
	}
	//工数を登録するメソッド---------------------------------------
		public int userRegist(String id ,String pw ,String name,String address,String kan) {
			//DAOに処理を任せる
			UserDAO dao = new WorksDAO(conn);
			int ans=0;
			try {
				ans = dao.worksInsert(workDate,actualHours,workDescription);
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			return ans;		
		}
		
		//工数を削除するメソッド---------------------------------------
			public int userDelete(String id) {
				//DAOに処理を任せる
				UserDAO dao = new WorksDAO(super.conn);
				int ans=0;
				try {
					ans = dao.worksDelete(id);
				} catch (SQLException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
				return ans;		
			
			}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
