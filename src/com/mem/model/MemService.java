package com.mem.model;

import java.util.*;

public class MemService {
	private I_MemDAO dao;
	
	public MemService() {
		dao = new MemDAO();
	}
	
	public MemVO addMem(
			String  first_name, String  last_name, String  nickname, String  tel, 
			String  mob, String  email, String  shop_name, String  credit_card, 
			java.sql.Date  credit_card_expires, Integer  credit_card_cvc, String  bank_account, String  address, 
			Integer  authority) {
		MemVO memVO =  new MemVO();
		
		memVO.setFirst_name(first_name);
		memVO.setLast_name(last_name);
		memVO.setNickname(nickname);
		memVO.setTel(tel);
		memVO.setMob(mob);
		memVO.setEmail(email);
		memVO.setShop_name(shop_name);
		memVO.setCredit_card(credit_card);
		memVO.setCredit_card_expires(credit_card_expires);
		memVO.setCredit_card_cvc(credit_card_cvc);
		memVO.setBank_account(bank_account);
		memVO.setAddress(address);
		memVO.setAuthority(authority);
		
		dao.insert(memVO);
		return memVO;
	};
	public MemVO updateMem(
			String  mem_id, String  first_name, String  last_name, String  nickname, String  tel, 
			String  mob, String  email, String  password, String  shop_name, String  credit_card, 
			java.sql.Date  credit_card_expires, Integer  credit_card_cvc, String  bank_account, String  address, 
			Integer  authority) {
		MemVO memVO =  new MemVO();
		
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
		memVO.setCredit_card_expires(credit_card_expires);
		memVO.setCredit_card_cvc(credit_card_cvc);
		memVO.setBank_account(bank_account);
		memVO.setAddress(address);
		memVO.setAuthority(authority);		
		
		dao.update(memVO);
		return memVO;
	};
	public void deleteMem(String mem_id) {
		System.out.print("MemService deleteMem，");
		System.out.println("mem_id " + mem_id);
		dao.delete(mem_id);
	};
	public MemVO getOneEmp(String mem_id) {
		return dao.findByPrimaryKey(mem_id);
	};
	public List<MemVO> getEmails(String email){
		return dao.findByEmail(email);
	};
	public List<MemVO> getAll(){
		return dao.getAll();
	};
	
}
