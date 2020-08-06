package com.mem.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.json.JSONObject;

import com.mem.model.*;

public class MemServlet extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    doPost(req, res);
  }

  public void doPost(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {

    req.setCharacterEncoding("UTF-8");
    res.setContentType("text/html; charset=UTF-8");
    PrintWriter out = res.getWriter();
    String action = req.getParameter("action");

    System.out.println("MemServlet");
    
    if ("getOne_For_Display".equals(action)) {

      List<String> errorMsgs = new LinkedList<String>();
      req.setAttribute("errorMsgs", errorMsgs);

      try {
        /***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
        String str = req.getParameter("mem_id");
        if (str == null || (str.trim()).length() == 0) {
          errorMsgs.add("請輸入會員編號");
        }

        if (!errorMsgs.isEmpty()) {
          RequestDispatcher failureView = req
              .getRequestDispatcher("/emp/select_page.jsp");
          failureView.forward(req, res);
          return;
        }
        
        String mem_id = null;
        try {
          mem_id = str;
        } catch (Exception e) {
          errorMsgs.add("會員編號格式不正確");
        }
        if (!errorMsgs.isEmpty()) {
          RequestDispatcher failureView = req
              .getRequestDispatcher("/emp/select_page.jsp");
          failureView.forward(req, res);
          return;
        }
        
        /***************************2.開始查詢資料*****************************************/
        MemService memSvc = new MemService();
        MemVO memVO = memSvc.getOneEmp(mem_id);
        if (memVO == null) {
          errorMsgs.add("查無資料");
        }
        if (!errorMsgs.isEmpty()) {
          RequestDispatcher failureView = req
              .getRequestDispatcher("/emp/select_page.jsp");
          failureView.forward(req, res);
          return;
        }
        
        /***************************3.查詢完成,準備轉交(Send the Success view)*************/
        req.setAttribute("memVO", memVO);
        String url = "/emp/listOneEmp.jsp";
        RequestDispatcher successView = req.getRequestDispatcher(url);
        successView.forward(req, res);

        /***************************其他可能的錯誤處理*************************************/
      } catch (Exception e) {
        errorMsgs.add("無法取得資料:" + e.getMessage());
        RequestDispatcher failureView = req
            .getRequestDispatcher("/emp/select_page.jsp");
        failureView.forward(req, res);
      }
    }
    
    
    if ("getOne_For_Update".equals(action)) {

      List<String> errorMsgs = new LinkedList<String>();
      req.setAttribute("errorMsgs", errorMsgs);
      
      try {

        String mem_id = req.getParameter("mem_id");

        MemService memSvc = new MemService();
        MemVO memVO = memSvc.getOneEmp(mem_id);

        req.setAttribute("memVO", memVO);
        String url = "/emp/update_mem_input.jsp";
        RequestDispatcher successView = req.getRequestDispatcher(url);
        successView.forward(req, res);

      } catch (Exception e) {
        errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
        RequestDispatcher failureView = req
            .getRequestDispatcher("/emp/listAllMem.jsp");
        failureView.forward(req, res);
      }
    }
    
    // 更新權限
    if ("update_authority".equals(action)) {
    	// 設定錯誤紀錄
    	List<String> errorMsgs = new LinkedList<String>();
        req.setAttribute("errorMsgs", errorMsgs);
        try {
			String mem_id = req.getParameter("mem_id");
			Integer authority = Integer.parseInt(req.getParameter("authority"));
			
			// 取得會員資料
			MemService memSvc = new MemService();
			MemVO memVO = memSvc.getOneEmp(mem_id);
			// 更新權限
			memVO.setAuthority(authority);
			memSvc.updateMem(
					mem_id, memVO.getFirst_name(), memVO.getLast_name(), memVO.getNickname(), memVO.getTel(), 
					memVO.getMob(), memVO.getEmail(), memVO.getPassword(), memVO.getShop_name(), 
					memVO.getCredit_card(), memVO.getCredit_card_expires(), memVO.getCredit_card_cvc(), 
					memVO.getBank_account(), memVO.getAddress(), authority);
			// 回傳結果
			JSONObject jsonObjectJacky = new JSONObject();
			jsonObjectJacky.put("msg", "修改成功");
			jsonObjectJacky.put("status", 200);
			out.print(jsonObjectJacky);
        } catch (Exception e) {
        	// 紀錄錯誤信息
		    errorMsgs.add("修改權限失敗:"+e.getMessage());
		    // 回傳結果
			JSONObject jsonObjectJacky = new JSONObject();
			jsonObjectJacky.put("msg", "修改失敗");
			jsonObjectJacky.put("status", 200);
			out.print(jsonObjectJacky);
	    }
	 
    }
    
    // 更新全部會員資料
    if ("update".equals(action)) {
      
      List<String> errorMsgs = new LinkedList<String>();
      req.setAttribute("errorMsgs", errorMsgs);
    
      try {
        String mem_id = req.getParameter("mem_id").trim();        
        String first_name = req.getParameter("first_name");
        String last_name = req.getParameter("last_name");        
        String nickname = req.getParameter("nickname");
        String tel = req.getParameter("tel");
        String mob = req.getParameter("mob");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String shop_name = req.getParameter("shop_name");
        String credit_card = req.getParameter("credit_card");
        java.sql.Date credit_card_expires = java.sql.Date.valueOf(req.getParameter("credit_card_expires"));
        Integer credit_card_cvc = new Integer(req.getParameter("credit_card_cvc"));
        String bank_account = req.getParameter("bank_account");
        String address = req.getParameter("address");
        Integer authority = new Integer(req.getParameter("authority"));

        MemVO memVO = new MemVO();
        memVO.setMem_id(mem_id);
        memVO.setFirst_name(first_name);
        memVO.setLast_name(last_name);
        memVO.setNickname(nickname);
        memVO.setTel(tel);
        memVO.setMob(mob);
        memVO.setEmail(email);
        memVO.setPassword(password);
        memVO.setShop_name(shop_name);
        memVO.setCredit_card(credit_card);
        memVO.setCredit_card_expires((java.sql.Date) credit_card_expires);
        memVO.setCredit_card_cvc(credit_card_cvc);
        memVO.setBank_account(bank_account);
        memVO.setAddress(address);
        memVO.setAuthority(authority);		

        if (!errorMsgs.isEmpty()) {
          req.setAttribute("memVO", memVO);
          RequestDispatcher failureView = req
              .getRequestDispatcher("/emp/update_mem_input.jsp");
          failureView.forward(req, res);
          return;
        }
        
        MemService memSvc = new MemService();
        memVO = memSvc.updateMem(
        		mem_id, first_name, last_name, nickname, tel, 
        		mob, email, password, shop_name, credit_card, 
        		credit_card_expires, credit_card_cvc, bank_account, address, 
        		authority);

        req.setAttribute("memVO", memVO);
        String url = "/emp/listOneEmp.jsp";
        RequestDispatcher successView = req.getRequestDispatcher(url);
        successView.forward(req, res);
      } catch (Exception e) {
        errorMsgs.add("修改資料失敗:"+e.getMessage());
        RequestDispatcher failureView = req
            .getRequestDispatcher("/emp/update_mem_input.jsp");
        failureView.forward(req, res);
      }
    }

    if ("insert".equals(action)) {
      
      List<String> errorMsgs = new LinkedList<String>();
      req.setAttribute("errorMsgs", errorMsgs);

      try {
        String first_name = req.getParameter("first_name");
        String enameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
        if (first_name == null || first_name.trim().length() == 0) {
          errorMsgs.add("會員姓氏: 請勿空白");
        } else if(!first_name.trim().matches(enameReg)) {
          errorMsgs.add("會員姓氏: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
        }

        String last_name = req.getParameter("last_name");
        String enameReg1 = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
        if (last_name == null || last_name.trim().length() == 0) {
          errorMsgs.add("會員名稱: 請勿空白");
        } else if(!last_name.trim().matches(enameReg1)) {
          errorMsgs.add("會員名稱: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
        }
        
        String mem_id = req.getParameter("mem_id");
        String nickname = req.getParameter("nickname");
        String tel = req.getParameter("tel");
        String mob = req.getParameter("mob");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String shop_name = req.getParameter("shop_name");
        String credit_card = req.getParameter("credit_card");
        java.sql.Date credit_card_expires = java.sql.Date.valueOf(req.getParameter("credit_card_expires"));
        Integer credit_card_cvc = new Integer(req.getParameter("credit_card_cvc"));
        String bank_account = req.getParameter("bank_account");
        String address = req.getParameter("address");
        Integer authority = new Integer(req.getParameter("authority"));

        MemVO memVO = new MemVO();
        memVO.setMem_id(mem_id);
        memVO.setFirst_name(first_name);
        memVO.setLast_name(last_name);
        memVO.setNickname(nickname);
        memVO.setTel(tel);
        memVO.setMob(mob);
        memVO.setEmail(email);
        memVO.setPassword(password);
        memVO.setShop_name(shop_name);
        memVO.setCredit_card(credit_card);
        memVO.setCredit_card_expires((java.sql.Date) credit_card_expires);
        memVO.setCredit_card_cvc(credit_card_cvc);
        memVO.setBank_account(bank_account);
        memVO.setAddress(address);
        memVO.setAuthority(authority);	

        if (!errorMsgs.isEmpty()) {
          req.setAttribute("memVO", memVO);
          RequestDispatcher failureView = req
              .getRequestDispatcher("/emp/addMem.jsp");
          failureView.forward(req, res);
          return;
        }

        MemService memSvc = new MemService();
        memVO = memSvc.addMem(
        		first_name, last_name, nickname, tel, 
        		mob, email, shop_name, credit_card, 
        		credit_card_expires, credit_card_cvc, bank_account, address, 
        		authority);

        String url = "/emp/listAllMem.jsp";
        RequestDispatcher successView = req.getRequestDispatcher(url);
        successView.forward(req, res);				

      } catch (Exception e) {
        errorMsgs.add(e.getMessage());
        RequestDispatcher failureView = req
            .getRequestDispatcher("/emp/addMem.jsp");
        failureView.forward(req, res);
      }
    }
    
    
    if ("delete".equals(action)) {
	  System.out.println("MemServlet delete");
      List<String> errorMsgs = new LinkedList<String>();
      req.setAttribute("errorMsgs", errorMsgs);
  
      try {

        String mem_id = req.getParameter("mem_id");
        System.out.println("mem_id: "+mem_id);
        MemService memSvc = new MemService();
        memSvc.deleteMem(mem_id);
        
        JSONObject jsonObjectJacky = new JSONObject();
        jsonObjectJacky.put("msg", "刪除成功");
        jsonObjectJacky.put("status", 200);
        out.print(jsonObjectJacky);
      } catch (Exception e) {
        errorMsgs.add("刪除資料失敗:"+e.getMessage());
        JSONObject jsonObjectJacky = new JSONObject();
        jsonObjectJacky.put("msg", "此會員有關連資料，不可刪除");
        jsonObjectJacky.put("status", 200);
        out.print(jsonObjectJacky);
      }
    }
  }
}
