package tommy.spring.web.user;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import tommy.spring.web.user.impl.UserDAO;

@Controller
public class UserController {
	@RequestMapping(value="/login.do", method=RequestMethod.GET)
	public String loginView(UserVO vo) {
		System.out.println("로그인 화면으로 이동");
		vo.setId("test");
		vo.setPassword("test");
		return "login.jsp";
	}
	@RequestMapping(value="/login.do", method=RequestMethod.POST)
	public String login(UserVO vo, UserDAO userDAO, HttpSession session) {
		System.out.println("로그인 인증 처리");
		if(vo.getId() == null || vo.getId().equals("")) {
			throw new IllegalAccessError("아이디는 반드시 입력해야 합니다");
		}
		UserVO user = userDAO.getUser(vo);
		// 화면 네비게이션
		if(user != null) {
			session.setAttribute("userName", user.getName());
			return "getBoardList.do";
		}else {
			return "login.jsp";
		}
	}
	@RequestMapping("/logout.do")
	public String logout(HttpSession session) {
		System.out.println("로그아웃 처리");
		// 1. 브라우저와 연결된 세션 객체를 종료
		session.invalidate();
		// 2. 세션 종료 후 메인 화면으로 이동
		return "login.jsp";
	}
}
