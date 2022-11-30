package board.model.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import board.model.vo.Board;
import board.model.vo.BoardException;
import board.model.vo.PageInfo;

public class BoardDAO {

	public int getListCount(SqlSession session) throws BoardException {
		//전체게시글갯수만 가져오는 니즈가 있기때문에 셀렉트 카운트 *로 전체게시글갯수의 1개의 행만 나와서 selectOne으로 예상했습니다.
		//매개변수 1개만 사용 쿼리에 넘길 데이터가 없기 때문에.
		//반환값 int로 바꿔줌
		int listCount = session.selectOne("boardMapper.selectListCount");
		
		if(listCount <= 0) {
			session.close();
			throw new BoardException("게시물 조회에 실패하였습니다.");
		}
		return listCount;
	}

	public ArrayList<Board> selectBoardList(SqlSession session, PageInfo pi) throws BoardException {
		//기존에 startRow,endRow 만든 이유 : rownum로우넘을 이용해서 몇번째 행인지 between a and b로 몇행에서 몇행을 가져와 쿼리 
		//마이바티스에서 페이징처리는 RowBounds : 몇개를 건너뛰고 보여줄건지 중요!
		//1번째 페이지는 5개, 2번째페이지는 5개 건너뛰고, 3번째페이지는 10개 건너뛰고!
		//m(offset)개를 건너뛰고 n(limit)개의 게시물을 가져오겟다
		
		//1page = 0개
		//2page = 5개 = (2page-1)*5개
		//3page = 10개 = (3page-1)*5개
		//5는BoardLimit
		//rowBounds가 rownum역할을 해주기 때문에 따로 로우넘 해줄필요없이 쿼리가 짧아진다.
		int offset = (pi.getCurrentPage() -1)*pi.getBoardLimit();
		
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		//두번째 인자는 보낼값이 없어서 null로 한다
		//제네릭때문에 ArrayList로 형변환을 해준다.
		ArrayList<Board> list = (ArrayList)session.selectList("boardMapper.selectBoardList", null, rowBounds);
		
		if(list == null) {
			session.close();
			throw new BoardException("게시물 조회에 실패하였습니다.");
		}
		
		return list;
	}

	public int updateCount(SqlSession session, int bId) throws BoardException {
		int result = session.update("boardMapper.updateCount", bId); 
		
		if(result <= 0) {
			session.rollback();
			session.close();
			throw new BoardException("게시물 조회수 증가에 실패하였습니다.");
		}
		
		return result;
	}

	public Board selectBoardDetail(SqlSession session, int bId) throws BoardException {
		Board b = session.selectOne("boardMapper.selectBoard", bId);
		
		if(b == null) {
			session.close();
			throw new BoardException("게시글 조회에 실패하였습니다");
		}
		return b;
	}

	public int getSearchListCount(SqlSession session, HashMap<String, String> map) throws BoardException {
		int listCount = session.selectOne("boardMapper.getSearchListCount", map);
		
		if(listCount <= 0) {
			session.close();
			throw new BoardException("검색 결과 개수 조회에 실패하였습니다.");
		}
		
		return listCount;
	}

	public ArrayList<Board> selectSearchList(SqlSession session, HashMap<String, String> map, PageInfo pi) throws BoardException {
		//페이징처리를 위해, map안에 어떤 조건으로 어떻게 검색할건지 들어가 있어서, 
		int offset = (pi.getCurrentPage() - 1)*pi.getBoardLimit();
		
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		
		ArrayList<Board> list = (ArrayList)session.selectList("boardMapper.selectSearchList", map, rowBounds);
		
		if(list == null) {
			session.close();
			throw new BoardException("검색 결과 리스트 조회에 실패하였습니다.");
		}
		
		return list;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
