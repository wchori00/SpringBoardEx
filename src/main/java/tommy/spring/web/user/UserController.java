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
		System.out.println("�α��� ȭ������ �̵�");
		vo.setId("test");
		vo.setPassword("test");
		return "login.jsp";
	}
	@RequestMapping(value="/login.do", method=RequestMethod.POST)
	public String login(UserVO vo, UserDAO userDAO, HttpSession session) {
		System.out.println("�α��� ���� ó��");
		if(vo.getId() == null || vo.getId().equals("")) {
			throw new IllegalAccessError("���̵�� �ݵ�� �Է��ؾ� �մϴ�");
		}
		UserVO user = userDAO.getUser(vo);
		// ȭ�� �׺���̼�
		if(user != null) {
			session.setAttribute("userName", user.getName());
			return "getBoardList.do";
		}else {
			return "login.jsp";
		}
	}
	@RequestMapping("/logout.do")
	public String logout(HttpSession session) {
		System.out.println("�α׾ƿ� ó��");
		// 1. �������� ����� ���� ��ü�� ����
		session.invalidate();
		// 2. ���� ���� �� ���� ȭ������ �̵�
		return "login.jsp";
	}
}
