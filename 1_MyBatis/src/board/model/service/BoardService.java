package board.model.service;

import static common.Template.getSqlSession;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;

import board.model.dao.BoardDAO;
import board.model.vo.Board;
import board.model.vo.BoardException;
import board.model.vo.PageInfo;

public class BoardService {

	public int getListCount() throws BoardException {
		SqlSession session= getSqlSession();
		
		int listCount = new BoardDAO().getListCount(session);
		
		session.close();
		
		return listCount;
	}

	public ArrayList<Board> selectBoardList(PageInfo pi) throws BoardException {
		SqlSession session = getSqlSession();
		
		ArrayList<Board> list = new BoardDAO().selectBoardList(session, pi);
		
		session.close();
		
		return list;
	}

	public Board selectBoardDetail(int bId) throws BoardException {
		SqlSession session = getSqlSession();
		//카운트도 가져와야하기때문에
		BoardDAO dao = new BoardDAO();
		
		int result = dao.updateCount(session, bId);
		
		Board b = dao.selectBoardDetail(session, bId);
		
		session.commit();
		session.close();
		
		return b;
	}
	//검색 리스트
	public int getSearchListCount(HashMap<String, String> map) throws BoardException {
		SqlSession session = getSqlSession();
		
		int listCount = new BoardDAO().getSearchListCount(session, map);
		
		session.close();
		
		return listCount;
	}

	public ArrayList<Board> selectSearchList(HashMap<String, String> map, PageInfo pi) throws BoardException {
		SqlSession session = getSqlSession();
		
		ArrayList<Board> list= new BoardDAO().selectSearchList(session, map, pi);
		
		session.close();
		
		return list;
	}

}
