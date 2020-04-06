package com.hengyue.controller.admin.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hengyue.utils.DateUtils;

@RestController
@RequestMapping("/admin/year")
public class YearAdminController {

	@RequestMapping("/getTwoYearCombobox")
	public List<Map<String, Integer>> getTwoYearCombobox() throws Exception{
		List<Map<String, Integer>> result = new ArrayList<Map<String,Integer>>();
		Map<String, Integer> dateMap = DateUtils.getCurrentTwoYear();
		dateMap.put("id", dateMap.get("currentYear"));
		dateMap.put("name", dateMap.get("currentYear"));
		Map<String, Integer> dateMap1 = DateUtils.getCurrentTwoYear();
		dateMap1.put("id", dateMap1.get("nextYear"));
		dateMap1.put("name", dateMap1.get("nextYear"));
		result.add(dateMap);
		result.add(dateMap1);
		return result;
	}
}
