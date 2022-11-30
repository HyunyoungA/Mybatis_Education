package board.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.service.BoardService;
import board.model.vo.Board;
import board.model.vo.BoardException;
import board.model.vo.PageInfo;
import common.Pagination;

/**
 * Servlet implementation class BoardSearchServlet
 */
@WebServlet("/search.bo")
public class BoardSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardSearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String condition = request.getParameter("searchCondition");
		String value = request.getParameter("searchValue");
		//페이징 처리 레퍼런스 변수만들기
		BoardService service = new BoardService();
	
		int currentPage = 1;
		if(request.getParameter("currentPage")!= null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		//리스트카운트 이전에는 전체를 가져오지만 이번엔 검색을해서 가져와야하기때문에
		//검색한 내용에 맞게 리스트카운트를 가져와야한다. 
		//하나만 인자로 보낼수있어서 vo나 컬렉션에 담아서 넘겨야한다.
		//컬렉션 키와 벨류 활용.
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("condition", condition);
		map.put("value", value);
		
		try {
			int listCount = service.getSearchListCount(map);
			
			PageInfo pi = Pagination.getPageInfo(currentPage, listCount);
			//뭘 검색할 지 알아야되서 map도 반환, 여러개가 나올 수 있으니 리스트로 진행한다.
			ArrayList<Board> list = service.selectSearchList(map, pi);
			
			request.setAttribute("list", list);
			request.setAttribute("pi", pi);
			//condition, value 이 두개때문에 검색한결과를 가져올 수 있게 했다, 있으면 검색, 없으면 전체를 가져온다.이걸 구분하기위해서 넣은것이다
			request.setAttribute("condition", condition);
			request.setAttribute("value", value);
			
			request.getRequestDispatcher("WEB-INF/views/board/boardList.jsp").forward(request, response);
			
		} catch (BoardException e) {
			request.setAttribute("message", e.getMessage());
			request.getRequestDispatcher("WEB-INF/views/common/errorPage.jsp").forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
