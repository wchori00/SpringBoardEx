package tommy.spring.web.board;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

@Controller
@SessionAttributes("board")
public class BoardController {
	@Autowired
	private BoardService boardService;
	@RequestMapping("/dataTransformJSON.do")
	@ResponseBody
	public List<BoardVO> dataTransform2(BoardVO vo) {
		vo.setSearchCondition("TITLE");
		vo.setSearchKeyword("");
		List<BoardVO> boardList = boardService.getBoardList(vo);
		System.out.println(boardList);
		return boardList;
	}
	@RequestMapping("/dataTransformXML.do")
	@ResponseBody
	public BoardListVO dataTransform(BoardVO vo) {
		vo.setSearchCondition("TITLE");
		vo.setSearchKeyword("");
		List<BoardVO> boardList = boardService.getBoardList(vo);
		BoardListVO boardListVO = new BoardListVO();
		boardListVO.setBoardList(boardList);
		return boardListVO;
	}
	@ModelAttribute("conditionMap")
	public Map<String, String> searchConditionMap() {
		Map<String, String> conditionMap = new HashMap<String, String>();
		conditionMap.put("����", "TITLE");
		conditionMap.put("����", "CONTENT");
		return conditionMap;
	}
	@RequestMapping("/insertBoard.do")
	public String insertBoard(BoardVO vo) throws IOException {
		System.out.println("�� ��� ó��");
		MultipartFile uploadFile = vo.getUploadFile();
		if(!uploadFile.isEmpty()) {
			String fileName = uploadFile.getOriginalFilename();
			uploadFile.transferTo(new File("C:/myProject/" + fileName));
		}
		boardService.insertBoard(vo);
		return "getBoardList.do";
	}
	@RequestMapping("/updateBoard.do")
	public String updateBoard(@ModelAttribute("board") BoardVO vo) {
		System.out.println("�� ���� ��� ó��");
		System.out.println("��ȣ: " + vo.getSeq());
		System.out.println("����: " + vo.getTitle());
		System.out.println("�ۼ���: " + vo.getWriter());
		System.out.println("����: " + vo.getContent());
		System.out.println("�����: " + vo.getRegDate());
		System.out.println("��ȸ��: " + vo.getCnt());
		System.out.println("�ۼ��� �̸�: " + vo.getWriter());
		boardService.updateBoard(vo);
		return "getBoardList.do";
	}
	@RequestMapping("/deleteBoard.do")
	public String deleteBoard(BoardVO vo) {
		System.out.println("�� ���� ó��");
		boardService.deleteBoard(vo);
		return "getBoardList.do";
	}
	@RequestMapping("/getBoard.do")
	public String getBoard(BoardVO vo, Model model) {
		System.out.println("�� �� ���� ó��");
		model.addAttribute("board", boardService.getBoard(vo));
		return "getBoard.jsp";
	}
	@RequestMapping("/getBoardList.do")
	public String getBoardList(BoardVO vo, Model model) {
		System.out.println("�� ��� �˻� ó��");
		if(vo.getSearchCondition() == null) vo.setSearchCondition("TITLE");
		if(vo.getSearchKeyword() == null) vo.setSearchKeyword("");
		model.addAttribute("boardList", boardService.getBoardList(vo));
		return "getBoardList.jsp";
	}
}
