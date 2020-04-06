package com.hengyue.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hengyue.entity.PublicMsg;
import com.hengyue.entity.Ueditor;
import com.hengyue.utils.DateUtils;

@Controller
public class UEditorController {

	@RequestMapping("/ueditor")
	@ResponseBody
	public String ueditor(HttpServletRequest request) {
		return PublicMsg.UEDITOR_CONFIG;
	}

	/**
	 * 保存图片
	 * @param upfile
	 * @param request
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping("/imgUpload")
	@ResponseBody
	public Ueditor imgUpload(MultipartFile upfile , HttpServletRequest request) throws IllegalStateException, IOException {
		Ueditor ueditor = new Ueditor();
		if(upfile != null) {
			String filePath = request.getServletContext().getRealPath("/");//获取服务器路径
			String imageName = DateUtils.getCurrentTime() + "." + upfile.getOriginalFilename().split("\\.")[1];
			upfile.transferTo(new File(filePath + "static//image//" + imageName));
			ueditor.setUrl("http://localhost//static//image//" + imageName);
			ueditor.setState("SUCCESS");
			ueditor.setTitle(imageName);
			ueditor.setOriginal(imageName);
		}
		return ueditor;
	}
}
