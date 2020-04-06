package com.hengyue.controller.admin.vo;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hengyue.entity.vo.FolderType;
import com.hengyue.entity.vo.IsoFileUpload;
import com.hengyue.entity.Log;
import com.hengyue.entity.User;
import com.hengyue.service.GoodsService;
import com.hengyue.service.vo.FolderTypeService;
import com.hengyue.service.vo.IsoFileUploadService;
import com.hengyue.utils.DateUtils;
import com.hengyue.service.LogService;

/**
 * 文件夹类别控制层
 * 
 * @author 章家宝
 *
 */
@RestController
@RequestMapping("/admin/folderType")
public class FolderTypeAdminController {

	@Resource
	private FolderTypeService folderTypeService;

	@Resource
	private IsoFileUploadService isoFileUploadService;

	@Resource
	private LogService logService;

	@Resource
	private GoodsService goodsService;

	/**
	 * 添加文件夹类别
	 * 
	 * @param name
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	@RequiresPermissions(value = "资料管理")
	public Map<String, Object> save(String name, Integer parentId, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String url = request.getServletContext().getRealPath("/") + "\\file\\";// 获取父亲节点，进而获取全部的名称
		int parent = parentId;
		String folderName = "";
		int i = 1;
		FolderType currentFolderType = folderTypeService.findById(parentId);
		while (parent != 1 && parent != -1) {
			if (i == 1) {
				FolderType parentFolderType1 = folderTypeService.findById(parent);
				if (parentFolderType1.getpId() != 1) {
					FolderType parentFolderType2 = folderTypeService.findById(parentFolderType1.getpId());
					folderName = parentFolderType2.getName() + "\\" + folderName;
					parent = parentFolderType2.getpId();
				} else {
					parent = 1;
				}
				i++;
			} else {
				FolderType parentFolderType1 = folderTypeService.findById(parent);
				folderName = parentFolderType1.getName() + "\\" + folderName;
				parent = parentFolderType1.getpId();
			}

		}
		if (parentId != 1) {
			url += "所有文件\\" + folderName + "\\" + currentFolderType.getName();
		}else {
			url += "所有文件\\";
		}
		
		File f1 = new File(url);

		if (f1.isFile()) {
			map.put("success", false);
			map.put("errorInfo", "对不起，您所创建的文件夹的时候发生了系统错误！请立即联系管理员，管理员qq: 1295263075");
			return map;
		}

		url += "\\" + name;
		File file = new File(url);

		if (file.exists()) {
			map.put("success", false);
			map.put("errorInfo", "对不起，您所创建的文件夹已经存在了！");
			return map;
		} else {
			if (!file.mkdir()) {
				map.put("success", false);
				map.put("errorInfo", "创建文件夹失败！");
				return map;
			}
		}
		FolderType folderType = new FolderType();
		folderType.setName(name);
		folderType.setState(0);
		folderType.setpId(parentId);
		folderType.setIcon("icon-forder");
		folderType.setUrl(url);
		folderTypeService.save(folderType);
		FolderType parentFolderType = folderTypeService.findById(parentId);
		parentFolderType.setState(1);// 设置为非也在节点
		logService.save(new Log(Log.ADD_ACTION, "添加文件夹类别信息"));
		map.put("newTree", getAllByParentId(parentId).toString());
		map.put("success", true);
		return map;
	}

	/**
	 * 文件夹类别删除
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	@RequiresPermissions(value = "资料管理")
	public Map<String, Object> delete(Integer id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		FolderType currentFolderType = folderTypeService.findById(id);
		if (folderTypeService.findByParentId(currentFolderType.getpId()).size() == 1) {
			FolderType parentFolderType = folderTypeService.findById(currentFolderType.getpId());
			parentFolderType.setState(0);
			folderTypeService.save(parentFolderType);
		}
		File file = new File(currentFolderType.getUrl());
		if (file.isDirectory()) {
			// 删除里面的全部文件
			if (!delFile(file)) {
				map.put("success", false);
				map.put("errorInfo", "删除该文件夹的时候发生了错误，请稍后重新试下！");
				return map;
			}
			
		} else {
			if (!file.delete()) {
				map.put("success", false);
				map.put("errorInfo", "删除该文件夹的时候发生了错误，请稍后重新试下！");
				return map;
			}
		}
		logService.save(new Log(Log.UPDATE_ACTION, "删除文件夹类别信息" + currentFolderType));
//		folderTypeService.delete(id);
		deleteByParentId(id);
		map.put("success", true);
		try {
			folderTypeService.delete(id);
		}catch(Exception e) {
			
		}
		return map;
	}

	/**
	 * 递归删除文件夹
	 * @param file
	 * @return
	 */
	private static boolean delFile(File file) {
		if (!file.exists()) {
			return false;
		}
		if (file.isFile()) {
			return file.delete();
		} else {
			File[] files = file.listFiles();
			for (File f : files) {
				delFile(f);
			}
			return file.delete();
		}
	}

	/**
	 * 根据父节点获取所有复选框权限菜单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/loadTreeInfo")
	@RequiresPermissions(value = "资料管理")
	public String loadTreeInfo(HttpSession session, HttpServletRequest request) throws Exception {
		User user = (User) session.getAttribute("currentUser");
		Integer userId = 0;
		if(user != null) {
			userId = user.getId();
		}
		logService.save(new Log(Log.SEARCH_ACTION, "查询所有文件夹类别信息"));
		if(user.getId() < 5) {
			return getAllByParentId(-1).toString();
		}else {
			FolderType folderType = folderTypeService.findGenId(userId);
			if(folderType == null) {
				String url = request.getServletContext().getRealPath("/") + "\\file\\所有文件\\" + user.getTrueName();// 获取父亲节点，进而获取全部的名称
				FolderType folderType2 = new FolderType();
				folderType2.setState(1);
				folderType2.setIcon("icon-forder");
				folderType2.setpId(1);
				folderType2.setName(user.getTrueName());
				folderType2.setUserId(userId);
				folderType2.setUrl(url);
				File file = new File(url);
				file.mkdir();
				folderTypeService.save(folderType2);
				FolderType folderType4 = folderTypeService.findGenId(userId);
				FolderType folderType3 = new FolderType();
				folderType3.setState(0);
				folderType3.setIcon("icon-forder");
				folderType3.setpId(folderType4.getId());
				folderType3.setName(user.getTrueName());
				folderType3.setUserId(userId);
				url = url + "\\" + user.getTrueName();
				folderType3.setUrl(url);
				File file2 = new File(url);
				file2.mkdir();
				folderTypeService.save(folderType3);
				folderType = folderTypeService.findGenId(userId);
			}
			return getAllByParentId(folderType.getId()).toString();
		}
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
	/**
	 * 根据父节点删除所有子节点
	 * 递归删除
	 * @param parentId
	 * @return
	 */
	public void deleteByParentId(Integer parentId) {
		List<FolderType> folderTypeList = folderTypeService.findByParentId(parentId);
		for (FolderType folderType : folderTypeList) {
			if (folderType.getState() == 1) {
				List<IsoFileUpload> isoFileUploadList = isoFileUploadService.findByTypeId(folderType.getId());
				for (IsoFileUpload isoFileUpload : isoFileUploadList) {
					isoFileUploadService.delete(isoFileUpload.getId());
				}
				folderTypeService.delete(folderType.getId());
				deleteByParentId(folderType.getId());
			} else {
				List<IsoFileUpload> isoFileUploadList = isoFileUploadService.findByTypeId(folderType.getId());
				for (IsoFileUpload isoFileUpload : isoFileUploadList) {
					isoFileUploadService.delete(isoFileUpload.getId());
				}
				folderTypeService.delete(folderType.getId());
			}
		}
	}
}
