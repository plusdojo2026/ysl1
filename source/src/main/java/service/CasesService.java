package service;

import java.util.ArrayList;

import dao.CasesDAO;
import dto.CasesDTO;

public class CasesService {

	//ページを開いた時に表示するもののメソッド
	public ArrayList<CasesDTO> intiCasesDetail() {
		//一旦空にする
		ArrayList<CasesDTO> casesList = null;
		CasesDAO dao = new CasesDAO(super.conn);

		casesList = CasesDAO.class;
		return;

	}

}
