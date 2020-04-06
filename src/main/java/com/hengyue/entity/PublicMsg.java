package com.hengyue.entity;

public class PublicMsg {
	public final static String UEDITOR_CONFIG = "{\r\n"
			+ "	\"imageActionName\": \"uploadimage\","
			+ "	\"imageFieldName\": \"upfile\", \"imageMaxSize\": 2048000,"
			+ "	\"imageAllowFiles\": [\r\n" + "		\".png\",\r\n" + "		\".jpg\",\r\n" + "		\".jpeg\",\r\n"
			+ "		\".gif\",\r\n" + "		\".bmp\"\r\n" + "	],/* 上传图片格式显示 */\r\n"
			+ "	\"imageCompressEnable\": true,\r\n"
			+ "	\"imageCompressBorder\": 1600,\r\n"
			+ "	\"imageInsertAlign\": \"none\",\r\n"
			+ "	\"imageUrlPrefix\": \"\",\r\n"
			+ "	\"imagePathFormat\": \"static/image/{yyyy}{mm}{dd}/{time}{rand:6}\",/* {filename} 会替换成原文件名,配置这项需要注意中文乱码问题 *//* {rand:6} 会替换成随机数,后面的数字是随机数的位数 *//* {time} 会替换成时间戳 *//* {yyyy} 会替换成四位年份 *//* {yy} 会替换成两位年份 *//* {mm} 会替换成两位月份 *//* {dd} 会替换成两位日期 *//* {hh} 会替换成两位小时 *//* {ii} 会替换成两位分钟 *//* {ss} 会替换成两位秒 *//* 非法字符 \\ : * ? \" < > | *//* 具请体看线上文档: fex.baidu.com/ueditor/#use-format_upload_filename *//* 涂鸦图片上传配置项 */\r\n"
			+ "	\"scrawlActionName\": \"uploadscrawl\",/* 执行上传涂鸦的action名称 */\r\n"
			+ "	\"scrawlFieldName\": \"upfile\",/* 提交的图片表单名称 */\r\n"
			+ "	\"scrawlPathFormat\": \"static/image/{yyyy}{mm}{dd}/{time}{rand:6}\",/* 上传保存路径,可以自定义保存路径和文件名格式 */\r\n"
			+ "	\"scrawlMaxSize\": 2048000,/* 上传大小限制，单imageUrlPrefix位B */\r\n"
			+ "	\"scrawlUrlPrefix\": \"\",/* 图片访问路径前缀 */\r\n" + "	\"scrawlInsertAlign\": \"none\",/* 截图工具上传 */\r\n"
			+ "	\"snapscreenActionName\": \"uploadimage\",/* 执行上传截图的action名称 */\r\n"
			+ "	\"snapscreenPathFormat\": \"static/image/{yyyy}{mm}{dd}/{time}{rand:6}\",/* 上传保存路径,可以自定义保存路径和文件名格式 */\r\n"
			+ "	\"snapscreenUrlPrefix\": \"localhost\",/* 图片访问路径前缀 */\r\n"
			+ "	\"snapscreenInsertAlign\": \"none\",/* 插入的图片浮动方式 *//* 抓取远程图片配置 */\r\n"
			+ "	\"catcherLocalDomain\": [\r\n" + "		\"127.0.0.1\",\r\n" + "		\"localhost\",\r\n"
			+ "		\"img.baidu.com\"\r\n" + "	],\r\n"
			+ "	\"catcherActionName\": \"catchimage\",/* 执行抓取远程图片的action名称 */\r\n"
			+ "	\"catcherFieldName\": \"source\",/* 提交的图片列表表单名称 */\r\n"
			+ "	\"catcherPathFormat\": \"static/image/{yyyy}{mm}{dd}/{time}{rand:6}\",/* 上传保存路径,可以自定义保存路径和文件名格式 */\r\n"
			+ "	\"catcherUrlPrefix\": \"\",/* 图片访问路径前缀 */\r\n" + "	\"catcherMaxSize\": 2048000,/* 上传大小限制，单位B */\r\n"
			+ "	\"catcherAllowFiles\": [\r\n" + "		\".png\",\r\n" + "		\".jpg\",\r\n" + "		\".jpeg\",\r\n"
			+ "		\".gif\",\r\n" + "		\".bmp\"\r\n" + "	],/* 抓取图片格式显示 *//* 上传视频配置 */\r\n"
			+ "	\"videoActionName\": \"uploadvideo\",/* 执行上传视频的action名称 */\r\n"
			+ "	\"videoFieldName\": \"upfile\",/* 提交的视频表单名称 */\r\n"
			+ "	\"videoPathFormat\": \"static/video/{yyyy}{mm}{dd}/{time}{rand:6}\",/* 上传保存路径,可以自定义保存路径和文件名格式 */\r\n"
			+ "	\"videoUrlPrefix\": \"\",/* 视频访问路径前缀 */\r\n"
			+ "	\"videoMaxSize\": 102400000,/* 上传大小限制，单位B，默认100MB */\r\n" + "	\"videoAllowFiles\": [\r\n"
			+ "		\".flv\",\r\n" + "		\".swf\",\r\n" + "		\".mkv\",\r\n" + "		\".avi\",\r\n"
			+ "		\".rm\",\r\n" + "		\".rmvb\",\r\n" + "		\".mpeg\",\r\n" + "		\".mpg\",\r\n"
			+ "		\".ogg\",\r\n" + "		\".ogv\",\r\n" + "		\".mov\",\r\n" + "		\".wmv\",\r\n"
			+ "		\".mp4\",\r\n" + "		\".webm\",\r\n" + "		\".mp3\",\r\n" + "		\".wav\",\r\n"
			+ "		\".mid\"\r\n" + "	],/* 上传视频格式显示 *//* 上传文件配置 */\r\n"
			+ "	\"fileActionName\": \"uploadfile\",/* controller里,执行上传视频的action名称 */\r\n"
			+ "	\"fileFieldName\": \"upfile\",/* 提交的文件表单名称 */\r\n"
			+ "	\"filePathFormat\": \"/ueditor/jsp/upload/file/{yyyy}{mm}{dd}/{time}{rand:6}\",/* 上传保存路径,可以自定义保存路径和文件名格式 */\r\n"
			+ "	\"fileUrlPrefix\": \"\",/* 文件访问路径前缀 */\r\n" + "	\"fileMaxSize\": 51200000,/* 上传大小限制，单位B，默认50MB */\r\n"
			+ "	\"fileAllowFiles\": [\r\n" + "		\".png\",\r\n" + "		\".jpg\",\r\n" + "		\".jpeg\",\r\n"
			+ "		\".gif\",\r\n" + "		\".bmp\",\r\n" + "		\".flv\",\r\n" + "		\".swf\",\r\n"
			+ "		\".mkv\",\r\n" + "		\".avi\",\r\n" + "		\".rm\",\r\n" + "		\".rmvb\",\r\n"
			+ "		\".mpeg\",\r\n" + "		\".mpg\",\r\n" + "		\".ogg\",\r\n" + "		\".ogv\",\r\n"
			+ "		\".mov\",\r\n" + "		\".wmv\",\r\n" + "		\".mp4\",\r\n" + "		\".webm\",\r\n"
			+ "		\".mp3\",\r\n" + "		\".wav\",\r\n" + "		\".mid\",\r\n" + "		\".rar\",\r\n"
			+ "		\".zip\",\r\n" + "		\".tar\",\r\n" + "		\".gz\",\r\n" + "		\".7z\",\r\n"
			+ "		\".bz2\",\r\n" + "		\".cab\",\r\n" + "		\".iso\",\r\n" + "		\".doc\",\r\n"
			+ "		\".docx\",\r\n" + "		\".xls\",\r\n" + "		\".xlsx\",\r\n" + "		\".ppt\",\r\n"
			+ "		\".pptx\",\r\n" + "		\".pdf\",\r\n" + "		\".txt\",\r\n" + "		\".md\",\r\n"
			+ "		\".xml\"\r\n" + "	],/* 上传文件格式显示 *//* 列出指定目录下的图片 */\r\n"
			+ "	\"imageManagerActionName\": \"listimage\",/* 执行图片管理的action名称 */\r\n"
			+ "	\"imageManagerListPath\": \"static/image/\",/* 指定要列出图片的目录 */\r\n"
			+ "	\"imageManagerListSize\": 20,/* 每次列出文件数量 */\r\n"
			+ "	\"imageManagerUrlPrefix\": \"\",/* 图片访问路径前缀 */\r\n"
			+ "	\"imageManagerInsertAlign\": \"none\",/* 插入的图片浮动方式 */\r\n" + "	\"imageManagerAllowFiles\": [\r\n"
			+ "		\".png\",\r\n" + "		\".jpg\",\r\n" + "		\".jpeg\",\r\n" + "		\".gif\",\r\n"
			+ "		\".bmp\"\r\n" + "	],/* 列出的文件类型 *//* 列出指定目录下的文件 */\r\n"
			+ "	\"fileManagerActionName\": \"listfile\",/* 执行文件管理的action名称 */\r\n"
			+ "	\"fileManagerListPath\": \"static/image/\",/* 指定要列出文件的目录 */\r\n"
			+ "	\"fileManagerUrlPrefix\": \"\",/* 文件访问路径前缀 */\r\n" + "	\"fileManagerListSize\": 20,/* 每次列出文件数量 */\r\n"
			+ "	\"fileManagerAllowFiles\": [\r\n" + "		\".png\",\r\n" + "		\".jpg\",\r\n"
			+ "		\".jpeg\",\r\n" + "		\".gif\",\r\n" + "		\".bmp\",\r\n" + "		\".flv\",\r\n"
			+ "		\".swf\",\r\n" + "		\".mkv\",\r\n" + "		\".avi\",\r\n" + "		\".rm\",\r\n"
			+ "		\".rmvb\",\r\n" + "		\".mpeg\",\r\n" + "		\".mpg\",\r\n" + "		\".ogg\",\r\n"
			+ "		\".ogv\",\r\n" + "		\".mov\",\r\n" + "		\".wmv\",\r\n" + "		\".mp4\",\r\n"
			+ "		\".webm\",\r\n" + "		\".mp3\",\r\n" + "		\".wav\",\r\n" + "		\".mid\",\r\n"
			+ "		\".rar\",\r\n" + "		\".zip\",\r\n" + "		\".tar\",\r\n" + "		\".gz\",\r\n"
			+ "		\".7z\",\r\n" + "		\".bz2\",\r\n" + "		\".cab\",\r\n" + "		\".iso\",\r\n"
			+ "		\".doc\",\r\n" + "		\".docx\",\r\n" + "		\".xls\",\r\n" + "		\".xlsx\",\r\n"
			+ "		\".ppt\",\r\n" + "		\".pptx\",\r\n" + "		\".pdf\",\r\n" + "		\".txt\",\r\n"
			+ "		\".md\",\r\n" + "		\".xml\"\r\n" + "	]\r\n" + "}";
	/**
	 * Ueditor的返回状态类型
	 */
	public enum UeditorMsg {
		SUCCESS("SUCCESS"), ERROR("上传失败");
		private String v;

		UeditorMsg(String v) {
			this.v = v;
		}

		public String get() {
			return this.v;
		}
	}
}
