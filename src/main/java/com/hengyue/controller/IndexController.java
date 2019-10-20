package com.hengyue.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 首页控制器
 * @author 章家宝
 *
 */
@Controller
public class IndexController {

	@RequestMapping("/")
	public String root() {
		return "redirect:/login.html";
	}
}
