package com.dept.model;

import java.util.List;

public class DeptService {
	private I_DeptDAO dao;
	
	public void addEmp(String dept_des, String dept_id) {
		DeptVO deptVO =  new DeptVO();

		deptVO.setDept_id(dept_id);
		deptVO.setDept_des(dept_des);
		
		dao.insert(deptVO);
	};
	public void updateMem(String dept_des, String dept_id) {
		DeptVO deptVO =  new DeptVO();
		
		deptVO.setDept_id(dept_id);
		deptVO.setDept_des(dept_des);
		
		dao.insert(deptVO);
	};
	public void delete(String dept_id) {
		dao.delete(dept_id);
	};
	public DeptVO getOneMem(String dept_id) {
		return dao.findByPrimaryKey(dept_id);
	};
	public List<DeptVO> getAll(){
		return dao.getAll();
	};
}
