package com.hengyue.controller.admin.vo;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hengyue.service.LogService;

@RestController
@RequestMapping("/admin/dataClean")
public class DataCleaningAdminController {

	@Resource
	private LogService logService;
	
}
