package member.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.GregorianCalendar;

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
 * Servlet implementation class MemberUpdateServlet
 */
@WebServlet("/mupdate.me")
public class MemberUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberUpdateServlet() {
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
		String userName = request.getParameter("userName");
		String nickName = request.getParameter("nickName");
		String email = request.getParameter("email");
		
		int year = Integer.parseInt(request.getParameter("year"));
		int month = Integer.parseInt(request.getParameter("month"));
		int date = Integer.parseInt(request.getParameter("date"));
		Date birthDay = new Date(new GregorianCalendar(year, month-1, date).getTimeInMillis());
		
		String gender = request.getParameter("gender");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		
		Member m = new Member(userId, null, userName, nickName, email, birthDay, gender, phone, address, null, null, null);
		
		try {
			new MemberService().updateMember(m);
			//수정성공하면 아이디 비밀번호 넘길것 위에 Member member을 넘긴다.
			//selectMember는 로그인에 대한 정보. 반환값 멤버
			//m = new MemberService().selectMember(member);
			//session.setAttribute("loginUser", m);
			//두개를 가져온 이유는 디비에 있는것과 세션에 있는것이 다르기때문에(수정할 내용이 담겨있는 )
			//디비/세션 따로 있는데 뷰에 뿌릴때 세션의 로그인정보를 뿌리고 ->뷰를통해 ->DB로 보낸다. DB가 바뀐다해서 세션이 같이 바뀌는건 아니기 때문에 
			//디비에 있는 정보를 셀렉트해와서 다시 세션에 집어넣는다.
			m = new MemberService().selectMember(member);//로그인코드 작성할 때 만들었던 것을 재사용한다.
			session.setAttribute("loginUser", m);
			response.sendRedirect("info.me");//내정보보기에 사용했던 뷰 연결 서블릿을 재사용한다.
			
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
