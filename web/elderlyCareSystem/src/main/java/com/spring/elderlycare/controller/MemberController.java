package com.spring.elderlycare.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.spring.elderlycare.dto.MemberDTO;
import com.spring.elderlycare.service.MemberService;

@SessionAttributes("uid")
@RestController
@RequestMapping("/users")
public class MemberController {
	
	@Autowired private MemberService service;
	@Autowired private MemberDTO mdto;
	private final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@RequestMapping(value ="login", method = RequestMethod.GET)
	public ModelAndView memberLogin(ModelAndView mav) {
		mav.setViewName("member/login");
		return mav;
	}
	//화면에서 입력 폼 json으로 받기
	@RequestMapping(value = "/login", method = RequestMethod.POST,
			headers= {"Content-type=application/json"})
	public @ResponseBody Map<String, Object> loginCheck(Model model,@RequestBody MemberDTO mdto) {
		logger.info("++++++++++++login form+++++++++++++");
		logger.info("++++++++++++"+mdto.getUid()+"+++++++++++++");
		boolean isExist = service.loginCheck(mdto);
		Map<String, Object> ret = new HashMap<String, Object>();
		//ret.put("result", false);
		if(isExist) {
			ret.put("result", true);
			ret.put("uid", mdto.getUid());
			//model.addAttribute("result", true);
			model.addAttribute("uid", mdto.getUid());
		}else //model.addAttribute("result", false); 
			ret.put("result", false);
		//return model;
		return ret;
	}
	
	/*@RequestMapping(value = "/login", method = RequestMethod.POST,
			headers= {"Content-type=application/json"})
	public @ResponseBody Model loginCheckTest(Model model,@RequestBody MemberDTO mdto) {
		logger.info("++++++++++++login form+++++++++++++");
		logger.info("++++++++++++"+mdto.getUid()+"+++++++++++++");
		boolean isExist = service.loginCheck(mdto);
		//Map<String, Object> ret = new HashMap<String, Object>();
		//ret.put("result", false);
		if(isExist) {
			//ret.put("result", true);
			//ret.put("uid", mdto.getUid());
			model.addAttribute("result", true);
			model.addAttribute("uid", mdto.getUid());
		}else model.addAttribute("result", false); 
			//ret.put("result", false);
		return model;
	}*/
	
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public ModelAndView memberJoinForm(ModelAndView mav){
		logger.info("++++++++++++join form+++++++++++++");
		mav.setViewName("member/join");
		return mav;
	}
	
	@RequestMapping(value = "/join", method = RequestMethod.POST,
			headers= {"Content-type=application/json"})
	public @ResponseBody /*Map<String,Object>*/Boolean memberJoin(@RequestBody MemberDTO mdto) {
		logger.info("++++++++++++join process+++++++++++++");
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("result", false);
		
		if(service.join(mdto)>0) { //서비스단 아래로 수정 필요 성공시 0보다 큰 값, 실패시 0
			ret.put("result", true);
			return true;
		}
		
		//return ret;
		return false;
	}
	
	@RequestMapping("/{uid}")
	public MemberDTO myPage(Model model, @PathVariable("uid") String m_id) {
		mdto = service.myPage(m_id);
		System.out.println(m_id);
		return mdto;
	}
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public @ResponseBody Boolean memberLogout(SessionStatus sessionStatus) {
		sessionStatus.setComplete();
		return true;
	}
	@DeleteMapping("/{uid}")
	public @ResponseBody Boolean memberDelete(SessionStatus sessionStatus, @PathVariable("uid") String uid) {
		logger.info("++++++++++delete+++++++++");
		if(service.delet(uid)>0) {
			sessionStatus.setComplete();
			return true;
		}
		return false;
	}
	@PutMapping("/{uid}")
	public @ResponseBody Boolean memberModify(MemberDTO mdto, @PathVariable("uid") String uid) {
		logger.info("++++++++++modify++++++++++");
		if(service.modify(mdto)>0) {
			return true;
		}
		return false;
	}
	@RequestMapping("/mod-form")
	public ModelAndView modifyForm(ModelAndView mav) {
		mav.setViewName("member/modify");
		
		return mav;
	}
}
