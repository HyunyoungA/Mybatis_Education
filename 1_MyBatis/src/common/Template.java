package common;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class Template {
	private Template() {}
	
	public static SqlSession getSqlSession() {
		SqlSession session = null;
		
		try {
			InputStream stream = Resources.getResourceAsStream("/mybatis-config.xml");
			
			//Sql Session Factory Builder 만들기
			SqlSessionFactoryBuilder ssfb = new SqlSessionFactoryBuilder();
			SqlSessionFactory ssf = ssfb.build(stream);
			//openSession(false)-->자동 커밋하지 않겠다.
			session = ssf.openSession(false);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return session;
	}
}
