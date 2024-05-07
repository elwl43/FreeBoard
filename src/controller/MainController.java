package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import service.FreeService;
import util.ScanUtil;
import util.View;
import view.Print;

public class MainController extends Print {
   
   static public Map<String, Object> sessionStorage = new HashMap<>();
   boolean debug = true;
   FreeService freeService = FreeService.getInstance();
   
   public static void main(String[] args) {
      new MainController().start();
   }

//   ------------- 자유게시판(홈)---------------
//   1. 게시판 전체 출력
//   2. 게시판 추가
//
//
//   ------------- 게시판 전체 출력 --------------
//   1. 게시판 상세 조회
//   2. 홈
//
//
//   ------------- 게시판 상세 조회 ---------------
//   1. 게시판 수정
//   2. 게시판 삭제
//   3. 게시판 전체 출력
//
//
//   ------------- 게시판 수정 --------------
//   syso -> 게시판 수정 출력
//   # 게시판 상세조회로 이동
//
//
//   ------------- 게시판 삭제 --------------
//   syso ->  게시판 삭제 출력
//   # 게시판 전체로 이동
//
//   ------------- 게시판 추가 ---------------
//   syso -> 게시판 추가 출력
//   # 게시판 전체로 이동
   
   private void start() {
      // enum 
      View view = View.HOME;
      while (true) {
         switch (view) {
         case HOME:
            view = home();
            break;
         case FREEBOARD_LIST:
        	 view = list();
        	 break;
         case FREEBOARD_INSERT:
        	 view = insert();
        	 break;
         case FREEBOARD_UPDATE:
        	 view = update();
        	 break;
         case FREEBOARD_DELETE:
        	 view = delete();
        	 break;
         case FREEBOARD_DETAIL:
        	 view = detail();
        	 break;
         default:
            break;
         }
      }
   }
   
   public View detail() {
	   if(debug)System.out.println("======게시판 상세 조회======");
	   
	   int boardNo = (int)sessionStorage.get("boardNo");
	   
	   List<Object> param = new ArrayList<Object>();
	   param.add(boardNo);
	   Map<String, Object> detail = freeService.detail(param);
	   System.out.println(detail);
	   
	   System.out.println("1. 게시판 수정");
	   System.out.println("2. 게시판 삭제");
	   System.out.println("3. 게시판 전체 출력");
	   
	   int sel = ScanUtil.menu();
	   if (sel == 1) return View.FREEBOARD_UPDATE;
	   else if (sel == 2) return View.FREEBOARD_DELETE;
	   else if (sel == 3) return View.FREEBOARD_LIST;
	   else return View.FREEBOARD_DETAIL;
   }
   
   public View delete() {
	   if(debug)System.out.println("======게시판 삭제======");
	   int boardNo = (int)sessionStorage.get("boardNo");
	   List<Object> param = new ArrayList();
	   param.add(boardNo);
	   freeService.delete(param);
	   return View.FREEBOARD_LIST;
   }
   
   public View update() {
	   if(debug)System.out.println("======게시판 수정======");
	   return View.FREEBOARD_DETAIL;
   }
   
   public View insert() {
	   if(debug)System.out.println("======게시판 등록======");
//	   NAME, CONTENT, WRITER
	   String name = ScanUtil.nextLine("제목");
	   String content = ScanUtil.nextLine("내용");
	   String writer = ScanUtil.nextLine("작성자");
	   List<Object> param = new ArrayList();
	   param.add(name);
	   param.add(content);
	   param.add(writer);
	   
	   freeService.insert(param);
	   
	   return View.FREEBOARD_LIST;
   }
   
   public View list() {
	   if(debug) System.out.println("=====리스트 페이지=====");
	   List<Map<String, Object>> list = freeService.list();
	   for (Map<String, Object> map : list) {
		System.out.println(map);
	}
	   System.out.println("1. 게시판 상세 조회");
	   System.out.println("2. 홈");
	   int sel = ScanUtil.menu();
	   if (sel == 1) {
		   int boardNo = ScanUtil.nextInt("게시판 번호 입력 : ");
		   sessionStorage.put("boardNo", boardNo);
		   return View.FREEBOARD_DETAIL;
	   }
	   else if (sel == 2) return View.HOME;
	   else return View.FREEBOARD_LIST;
	   
   }
// ------------- 자유게시판(홈)---------------
// 1. 게시판 전체 출력
// 2. 게시판 추가
//
//
// ------------- 게시판 전체 출력 --------------
// 1. 게시판 상세 조회
// 2. 홈
//
//
// ------------- 게시판 상세 조회 ---------------
// 1. 게시판 수정
// 2. 게시판 삭제
// 3. 게시판 전체 출력
//
//
// ------------- 게시판 수정 --------------
// syso -> 게시판 수정 출력
// # 게시판 상세조회로 이동
//
//
// ------------- 게시판 삭제 --------------
// syso ->  게시판 삭제 출력
// # 게시판 전체로 이동
//
// ------------- 게시판 추가 ---------------
// syso -> 게시판 추가 출력
// # 게시판 전체로 이동
   private View home() {
      System.out.println("1. 게시판 전체 출력");
      System.out.println("2. 게시판 추가");
      
      int sel = ScanUtil.nextInt("메뉴 선택 : ");
      if (sel == 1) return View.FREEBOARD_LIST;
      else if (sel == 2) return View.FREEBOARD_INSERT;
      
      else return View.HOME;
   }
}