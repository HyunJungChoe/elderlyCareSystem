package com.spring.elderlycare.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.elderlycare.dao.MemberDAO;
import com.spring.elderlycare.dto.MemberDTO;
import com.spring.elderlycare.util.SHAUtil;

@Service
public class MemberServiceImpl implements MemberService{
	@Autowired
	private MemberDAO mdao;
	@Autowired SHAUtil SHA;
	
	@Override
	public int join(MemberDTO mdto) {
		mdto.setUpwd(SHA.encryptSHA256(mdto.getUpwd()));
		int ret =  mdao.insertMember(mdto);
		
		return ret;
	}
	@Override
	public int loginCheck(MemberDTO mdto){
		mdto.setUpwd(SHA.encryptSHA256(mdto.getUpwd()));
		return mdao.exist(mdto);
	}
	@Override
	public int modify(MemberDTO mdto){
		int ret = mdao.updateMember(mdto);
		return ret;
	}
	@Override
	public int delet(String id){
		int ret = mdao.deleteMember(id);
		return ret;
	}
	@Override
	public MemberDTO myPage(String id){
		MemberDTO mdto = null;
		mdto = mdao.selectOne(id);
		return mdto;
	}
	/*@Override
	public List<DeviceUserDTO>devicesList(String id){
		return mdao.selectManageDevices(id);

	}*/
}
