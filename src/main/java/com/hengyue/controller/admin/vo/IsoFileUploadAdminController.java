package com.hengyue.controller.admin.vo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hengyue.entity.Log;
import com.hengyue.entity.User;
import com.hengyue.entity.vo.FolderType;
import com.hengyue.entity.vo.IsoFileUpload;
import com.hengyue.service.LogService;
import com.hengyue.service.vo.FolderTypeService;
import com.hengyue.service.vo.IsoFileUploadService;
import com.hengyue.utils.FileUtils;

/**
 * 文件管理controller
 * 
 * @author 章家宝
 *
 */
@Controller
@RequestMapping("/admin/isoFileUpload")
public class IsoFileUploadAdminController {

	@Resource
	private IsoFileUploadService isoFileUploadService;

	@Resource
	private FolderTypeService folderTypeService;

	@Resource
	private LogService logService;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true)); // true:允许输入空值，false:不能为空值
	}

	/**
	 * 上传文件
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/isoUpload")
//	@RequiresPermissions(value = "数据导入")
	public Map<String, Object> isoUpload(@RequestParam("file") MultipartFile file,
			@RequestParam("content") String content, @RequestParam("remarks") String remarks,
			@RequestParam(value = "pid", required = false) Integer pid, HttpSession session, 
			HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer parentId = pid;
		String folderName = "";
		while (parentId != 1 && parentId != null) {
			FolderType parentFolderType1 = folderTypeService.findById(parentId);
			folderName = parentFolderType1.getName() + "\\" + folderName;
			parentId = parentFolderType1.getpId();
		}
		if(pid != 1) {
			folderName = "所有文件\\" + folderName + "\\";
		}
		FileInputStream fis = null;
		if (pid == null || pid == 0) {
			User user = (User) session.getAttribute("currentUser");
			if (user.getFolderId() == null || user.getFolderId() == 0) {
				map.put("success", false);
				map.put("errorInfo", "上传失败，您没有权限上传，请联系管理员赋予权限！");
				return map;
			} else {
				pid = user.getFolderId();
			}
		}
		try {
			if (file != null) {
				String formatname = null;
				String url = null;
				String type = null;
				String name = file.getOriginalFilename();
				int len = file.getOriginalFilename().split("\\.").length;
				if (len >= 2) {
					formatname = name;
					url = request.getServletContext().getRealPath("\\") + "\\file\\" + folderName + name;
					type = file.getOriginalFilename().split("\\.")[1];
				} else {
					url = request.getServletContext().getRealPath("\\") + "\\file\\" + folderName + name;
					formatname = name;
					type = file.getOriginalFilename().split("\\.")[0];
				}
				String size = file.getSize() / 1024 + "kb";
				/**
				 * 检测文件夹是否存在
				 * 并且检测是否为文件
				 */
				File checkFile = new File(request.getServletContext().getRealPath("\\") + "\\file\\" + folderName);
				if(checkFile.isFile()) {
					map.put("success", false);
					map.put("errorInfo", "您上传的目录是文件！请您上传到文件夹");
					return map;
				}
				if(!checkFile.exists()) {
					map.put("success", false);
					map.put("errorInfo", "您上传的目录丢失，请联系管理员，管理员qq：1295263075");
					return map;
				}
				
				/**
				 * 添加文件
				 */
				IsoFileUpload isoUploadFile = new IsoFileUpload();
				isoUploadFile.setTime(new Date());
				isoUploadFile.setFormatname(formatname);
				isoUploadFile.setName(name);
				isoUploadFile.setSize(size);
				isoUploadFile.setUrl(url);
				isoUploadFile.setType(type);
				isoUploadFile.setDownload(0);
				isoUploadFile.setContent(content);
				isoUploadFile.setRemarks(remarks);
				isoUploadFile.setFolderType(folderTypeService.findById(pid));
				
				
				/**
				 * 添加到文件夹的树形结构
				 */
				FolderType foldertype = new FolderType();
				foldertype.setUrl(url);
				foldertype.setpId(pid);
				foldertype.setState(0);
				foldertype.setName(name);
				foldertype.setIcon("icon-forder");
				folderTypeService.save(foldertype);
				
				
				/**
				 * 找到父亲节点并且把节点设置为1
				 */
				FolderType parentFolder = folderTypeService.findById(pid);
				parentFolder.setState(1);
				folderTypeService.save(parentFolder);

				file.transferTo(new File(url));
				fis = new FileInputStream(url);
				map.put("success", true);
				isoFileUploadService.save(isoUploadFile);
				
				
				map.put("newTree", getAllByParentId(pid).toString());
				logService.save(new Log(Log.ADD_ACTION, "上传文件" + isoUploadFile));
				return map;
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("errorInfo", "上传失败，请稍后重试！");
			return map;
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		map.put("success", true);
		return map;
	}

	/**
	 * 分页查询文件管理信息
	 * 
	 * @param fileUpload
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/list")
//	@RequiresPermissions(value = "数据导入")
	public Map<String, Object> list(IsoFileUpload fileUpload,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<IsoFileUpload> fileUploadList = isoFileUploadService.list(fileUpload, page, rows, Direction.ASC, "id");
		map.put("rows", fileUploadList);
		map.put("total", fileUploadList.size());
		logService.save(new Log(Log.SEARCH_ACTION, "查询文件管理信息"));
		return map;
	}

	/**
	 * 添加或者修改文件管理信息
	 * 
	 * @param fileUpload
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/save")
//	@RequiresPermissions(value = "数据导入")
	public Map<String, Object> save(IsoFileUpload fileUpload) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		if(fileUpload.getId() != null) {
			IsoFileUpload isoFileUpload = isoFileUploadService.findById(fileUpload.getId());
			FolderType folderType = folderTypeService.findById(isoFileUpload.getFolderType().getId());
			fileUpload.setFolderType(folderType);
		}else {
			fileUpload.setFolderType(null);
		}
		isoFileUploadService.save(fileUpload);
		map.put("success", true);
		return map;
	}

	/**
	 * 删除文件管理id
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/delete")
//	@RequiresPermissions(value = "数据导入")
	public Map<String, Object> delete(Integer id) throws Exception {
		logService.save(new Log(Log.DELETE_ACTION, "删除文件管理信息" + isoFileUploadService.findById(id)));
		Map<String, Object> map = new HashMap<String, Object>();
		isoFileUploadService.delete(id);
		map.put("success", true);
		return map;
	}

	/**
	 * 下载文件
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/download")
	public Map<String, Object> download(Integer downloadFileId, String path, HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			IsoFileUpload uploadFile = isoFileUploadService.findById(downloadFileId);
			uploadFile.setDownload(uploadFile.getDownload() + 1);
			File file = new File(uploadFile.getUrl());

			if (!file.exists()) {
				map.put("success", false);
				map.put("errorInfo", "文件不存在");
				return map;
			}

			File fileOut = new File(path + "\\" + uploadFile.getName());
			fis = new FileInputStream(file);
			fos = new FileOutputStream(fileOut);
			byte[] bytes = new byte[1024];
			int len = -1;
			while ((len = fis.read(bytes)) != -1) {
				fos.write(bytes, 0, len);
			}
			// 下载成功
			map.put("success", true);
			isoFileUploadService.save(uploadFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("errorInfo", "地址错误，找不到该地址");
		} catch (Exception e) {
			map.put("success", false);
			map.put("errorInfo", "没有访问权限");
		} finally {
			if (fis != null) {
				fis.close();
			}
			if (fos != null) {
				fos.close();
			}
		}
		return map;
	}

	/**
	 * 下载文件
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/downloadFolder")
	public Map<String, Object> downloadFolder(Integer downloadFolderId, String path, HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			FolderType folderType = folderTypeService.findById(downloadFolderId);
			File file = new File(folderType.getUrl());

			if (!file.exists()) {
				map.put("success", false);
				map.put("errorInfo", "文件不存在");
				return map;
			}
			FileUtils.clone(folderType.getUrl(), path);
			// 下载成功
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			map.put("errorInfo", "没有访问权限！ 请联系管理员");
		}
		return map;
	}

	/**
	 * 上传文件夹
	 * @param dir
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/uploadDir")
	public Map<String, Object> uploadDir(
			@RequestParam("dir") MultipartFile[] dir,
			Integer uploadFolderId,
			HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			logger.info("****    开始复制文件夹           ****");
			String url = "";
			User user = (User)session.getAttribute("currentUser");
			Integer pid = uploadFolderId;
			String subPath = "";
			while(pid != 1) {
				FolderType folderType = folderTypeService.findById(pid);
				subPath = "\\" + folderType.getName() + subPath;
				pid = folderType.getpId();
			}
			if(uploadFolderId != 1) {
				url += "所有文件\\" + subPath;
			}
			
			url = request.getServletContext().getRealPath("\\") + "\\file\\所有文件\\" + subPath + "\\";
			
			/**
			 * 检测文件是否存在，检测存放文件的位置是否为文件夹
			 */
			File checkFile = new File(url);
			if(!checkFile.exists()) {
				/**
				 * 文件夹不存在
				 */
				map.put("success", false);
				map.put("errorInfo", "所上传的文件夹不存在");
				return map;
			}
			if(checkFile.isFile()) {
				/**
				 * 文件
				 */
				map.put("success", false);
				map.put("errorInfo", "所上传的目录是文件，请选择文件夹上传！");
				return map;
			}
			
			logger.info("****    baseUrl           ****" + url);
			for (MultipartFile f : dir) {
				/**
				 * currentPid 把当前的父亲节点存起来，然后循环的时候可以赋值给其他的父亲节点，这样子所在父亲id的时候不迷路
				 */
				int currentPid = uploadFolderId;
				/**
				 * 创建文件时可能会出现问题
				 */
				logger.info("url :" + url + f.getOriginalFilename());
				String currentUrl = url + f.getOriginalFilename();
				String newCurrentUrl = currentUrl.replace("/", "\\");
				String newUrl = url;
				String[] folders = f.getOriginalFilename().split("/");
				for (int i = 0; i < folders.length; i++) {
					newUrl = newUrl + folders[i] + "\\";
					FolderType folderType = folderTypeService.findByName(folders[i], newUrl);
					if(folderType == null && i != folders.length - 1) {
						folderType = new FolderType();
						/**
						 * 找到父亲节点，设置状态为1
						 */
						FolderType parentFolderType = folderTypeService.findById(currentPid);
						parentFolderType.setState(1);
						parentFolderType.setIcon("icon-forderOpen");
						folderTypeService.save(parentFolderType);
						
						//如果没有存在文件，那么就创建一个文件夹
						folderType.setName(folders[i]);
						folderType.setState(0);
						folderType.setIcon("icon-forder");
						folderType.setpId(currentPid);
						folderType.setUrl(newUrl);
						folderType.setUserId(user.getId());
						folderTypeService.save(folderType);
						//创建真实文件夹
						File ff = new File(newUrl);
						logger.info("创建文件夹路径：" + newUrl);
						if(ff.mkdir()) {
//							newUrl = newUrl + folders[i] + "\\";
							logger.info("创建文件夹成功：" + folders[i]);
						}else {
							logger.info("创建文件夹失败：" + folders[i]);
						}
					}
					/**
					 * 把当前的父亲节点转化为新添加的父亲节点
					 */
					if(i == folders.length - 1) {
						//创建文件实体，并且设置父亲节点的状态为1，然后把文件移过去
						IsoFileUpload isoFileUpload = new IsoFileUpload();
						isoFileUpload.setDownload(0);
						isoFileUpload.setFormatname(folders[folders.length - 1]);
						isoFileUpload.setTime(new Date());
						isoFileUpload.setName(folders[folders.length - 1]);
						isoFileUpload.setFolderType(folderTypeService.findById(currentPid));
						isoFileUpload.setContent("");
						isoFileUpload.setUrl(newCurrentUrl);
						isoFileUpload.setRemarks("");
						isoFileUpload.setSize(f.getSize() / 1024 + "kb");
						isoFileUploadService.save(isoFileUpload);
						
						
						
						FolderType folderType1 = new FolderType();
						folderType1.setIcon("icon-forder");
						folderType1.setUrl(newCurrentUrl);
						folderType1.setName(folders[folders.length - 1]);
						folderType1.setUserId(user.getId());
						folderType1.setState(0);
						folderType1.setpId(currentPid);
						folderTypeService.save(folderType1);
						
						/**
						 * 找到父亲节点，然后设置状态为1
						 */
						FolderType parentFolderType = folderTypeService.findById(currentPid);
						parentFolderType.setState(1);
						folderTypeService.save(parentFolderType);
						String uploadPath = newCurrentUrl.replace(folders[folders.length - 1], "");
						logger.info("uploadPath" + uploadPath);
						f.transferTo(new File(newCurrentUrl));
					}else {
						FolderType folderType2 = folderTypeService.findByName(folders[i], newUrl);
						logger.error(folders[i]);
						logger.error(newUrl);
						currentPid = folderType2.getId();
					}
				}
			}
		} catch (IllegalStateException e) {
			map.put("success", false);
			e.printStackTrace();
			map.put("errorInfo", "系统发生未知错误！");
			return map;
		} catch (IOException e) {
			map.put("success", false);
			e.printStackTrace();
			map.put("errorInfo", "系统发生未知错误！IO异常");
			return map;
		}
		map.put("newTree", getAllByParentId(uploadFolderId).toString());
		map.put("success", true);
		return map;
	}
	
	/**
	 * 根据父节点id和权限菜单id集合获取所有复选框菜单集合
	 * 
	 * @param parentId
	 * @return
	 */
	public JsonArray getAllByParentId(Integer parentId) {
		JsonArray jsonArray = this.getByParentId(parentId);
		for (int i = 0; i < jsonArray.size(); i++) {
			JsonObject jsonObject = (JsonObject) jsonArray.get(i);
			if ("open".equals(jsonObject.get("state").getAsString())) {
				continue;
			} else {
				jsonObject.add("children", getAllByParentId(jsonObject.get("id").getAsInt()));
			}
		}
		return jsonArray;
	}

	/**
	 * 根据父节点查询所有子节点
	 * 
	 * @param parentId
	 * @return
	 */
	public JsonArray getByParentId(Integer parentId) {
		List<FolderType> folderTypeList = folderTypeService.findByParentId(parentId);
		JsonArray jsonArray = new JsonArray();
		for (FolderType folderType : folderTypeList) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("id", folderType.getId()); // 节点Id
			jsonObject.addProperty("text", folderType.getName()); // 节点名称

			if (folderType.getState() == 1) {
				jsonObject.addProperty("state", "closed"); // 根节点
			} else {
				jsonObject.addProperty("state", "open"); // 叶子节点
			}
			jsonObject.addProperty("iconCls", folderType.getIcon()); // 节点图标
			JsonObject attributeObject = new JsonObject(); // 扩展属性
//			attributeObject.addProperty("state", folderType.getState()); // 节点状态
			attributeObject.addProperty("state", 0); // 节点状态
			jsonObject.add("attributes", attributeObject);
			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}
}
