package member.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.model.service.MemberService;
import member.model.vo.Member;
import member.model.vo.MemberException;

/**
 * Servlet implementation class MemberPwdUpdateServlet
 */
@WebServlet("/mPwdUpdate.me")
public class MemberPwdUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberPwdUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Member member = (Member)session.getAttribute("loginUser");
		String userId = member.getUserId();
		String userPwd = request.getParameter("userPwd");
		String newPwd = request.getParameter("newPwd");
		//하나의 인자만 가능해서 담아야한다.-->vo, collection(컬렉션을 사용한다.)
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("id", userId);
		map.put("oldPwd", userPwd);
		map.put("newPwd", newPwd);
		//세션을 분리해서 진행
		try {
			new MemberService().updatePwd(map);
			member.setUserPwd(newPwd);
			session.setAttribute("loginUser", member);
			response.sendRedirect(request.getContextPath());
		} catch (MemberException e) {
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
