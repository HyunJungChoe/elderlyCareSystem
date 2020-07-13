package com.spring.elderlycare.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.spring.elderlycare.dto.ElderlyDTO;
import com.spring.elderlycare.dto.MemberDTO;
import com.spring.elderlycare.service.MemberService;

@SessionAttributes({"uid", "auth"})
@RestController
@RequestMapping("/users")
public class MemberController {
	@Autowired private MemberService service;
	@Autowired private MemberDTO mdto;
	@Autowired private ElderlyDTO edto;
	private final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@RequestMapping(value ="login", method = RequestMethod.GET)
	public ModelAndView memberLogin(ModelAndView mav) {
		mav.setViewName("member/login");
		return mav;
	}
	//화면에서 입력 폼 json으로 받기
	@RequestMapping(value = "/login", method = RequestMethod.POST,
			headers= {"Content-type=application/json"})
	public Map<String, Object> loginCheck(HttpSession httpSession,@RequestBody MemberDTO mdto) {
		int isExist = service.loginCheck(mdto);
		Map<String, Object> ret = new HashMap<String, Object>();
		//ret.put("result", false);
		if(isExist!=-2) {
			ret.put("result", true);
			ret.put("uid", mdto.getUid());
			//model.addAttribute("uid", mdto.getUid());
			httpSession.setAttribute("uid", mdto.getUid());
			httpSession.setAttribute("auth", isExist);
			logger.info(httpSession.getId());
			logger.info((String) httpSession.getAttribute("uid"));
		}else
			ret.put("result", false);
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
		mav.setViewName("member/join");
		return mav;
	}
	
	@RequestMapping(value = "/join", method = RequestMethod.POST,
			headers= {"Content-type=application/json"})
	public Map<String,Object>/*Boolean*/ memberJoin(@RequestBody Map<String, Object>map) {
		mdto.setUid((String)map.get("uid"));
		mdto.setUpwd((String)map.get("upwd"));
		mdto.setUname((String)map.get("uname"));
		mdto.setUtel((String)map.get("utel"));
		mdto.setUemail((String)map.get("uemail"));
		
		edto.setEname((String) map.get("ename"));
		edto.setEbirth((String) map.get("ebirth"));
		
		
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("result", false);
		
		if(service.join(mdto)>0) { //join(mdto, edto)수정 0713
			ret.put("result", true);
			return ret;
		}
		
		//return ret;
		return ret;
	}
	
	@RequestMapping("/info")
	public MemberDTO myPage(Model model, /*@PathVariable("uid") String m_id*/HttpSession session) {
		String uid = (String) session.getAttribute("uid");
		mdto = service.myPage(uid);
		System.out.println(uid);
		return mdto;
	}
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public Boolean memberLogout(SessionStatus sessionStatus) {
		sessionStatus.setComplete();
		return true;
	}
	@DeleteMapping("/info")
	public Boolean memberDelete(SessionStatus sessionStatus, /*@PathVariable("uid") String m_id*/HttpSession session) {
		String uid = (String) session.getAttribute("uid");
		if(service.delet(uid)>0) {
			sessionStatus.setComplete();
			return true;
		}
		return false;
	}
	@PutMapping("/info")
	public Boolean memberModify(MemberDTO mdto  /*@PathVariable("uid") String m_id*/) {
		
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
