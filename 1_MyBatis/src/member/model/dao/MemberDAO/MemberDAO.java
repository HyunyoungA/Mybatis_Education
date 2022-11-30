package member.model.dao.MemberDAO;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;

import member.model.vo.Member;
import member.model.vo.MemberException;

public class MemberDAO {

	public Member selectMember(SqlSession session, Member m) throws MemberException {
		//selectOne : 1개의 행을 반환
		//selectList: 여러행를 반환
		//첫 번째 인자(arg0) : 연결할 쿼리문 지정 resources->mappers->mapper.xml(sql쿼리문 설정)
		//member-query.properties = mapper.xml(sql쿼리문 설정) 같은 역할
		//어떤쿼리를 호출할것인가 selectOne, selectList 반환행에 따라 사용.insert, update,delete는 똑같이 사용
		//mapper가 여러개일 수 있다. 어떤 mapper에 들어가있는 쿼리인지 중요하다!!
		//memberMapper안에 loginMember쿼리 사용
		//두 번째 인자(arg1) : 쿼리에 보낼 데이터 지정-->보낼 데이터가 있으면 어떤걸 보낼건지 지정
		//위치홀더 없을 때 인자 1개짜리 사용한다.
		//보내고 싶은게 있으면 한개의 vo, 컬렉션에 담아야한다. 2개의 인자뿐이기 때문에. 하나하나보낼 수 없다. 
		//loginservlet에 Member m에 담은 이유는 하나에 모든걸 담아야되기 때문에 객체에 담아서 가져온 것이다.
		//memberMapper.loginMember에 m이 보내진것이다.
		//selectOne의 반환값 Object여서 원하는 형태로 바꿔서 사용가능->Member member로 형태 바꿔서 반환타입이 다시 멤버로 바뀌었다.
		Member member = session.selectOne("memberMapper.loginMember", m);
		//필드명과 컬럼명이 달라서 맵핑이 안됬다. 리절트셋의 컬럼명과 담겠다고 한 필드명이 같으면 값이 알아서 맵핑되고
		//다르면 null이 들어간다.--><resultMap>으로 해결가능하다
//		System.out.println(member);
		//session.close();에러가 났을때 클로즈 닫아줌
		if(member == null) {
			session.close();
			throw new MemberException("로그인에 실패하였습니다");
		}
		return member;
	}

	public void insertMember(SqlSession session, Member m) throws MemberException {
		int result = session.insert("memberMapper.insertMember", m);
		//insert반환값은 int이다.
		//잘 진행이 안되면 롤백을 해줘야한다.비정상종료일때.throw때문에 DAO에서 롤백을 진행한다.
		if(result <= 0) {
			session.rollback();
			session.close();
			throw new MemberException("회원가입에 실패하였습니다.");
		}
	}

	public void updateMember(SqlSession session, Member m) throws MemberException {
		int result = session.update("memberMapper.updateMember", m);
		if(result <= 0) {
			session.rollback();
			session.close();
			throw new MemberException("회원 수정에 실패하였습니다.");
		}
		
	}

	public void updatePwd(SqlSession session, HashMap<String, String> map) throws MemberException {
		int result = session.update("memberMapper.updatePwd", map);
		if(result <= 0) {
			session.rollback();
			session.close();
			throw new MemberException("비밀번호 수정에 실패하였습니다.");
		}
		
	}

	public void deleteMember(SqlSession session, String userId) throws MemberException {
		//누구의 status를 바꿀건지 알아야하기때문에 userId를 가져온다.
		int result = session.update("memberMapper.deleteMember", userId);
		if(result <= 0) {
			session.rollback();
			session.close();
			throw new MemberException("탈퇴에 실패하였습니다");
		}
	}
	

}
