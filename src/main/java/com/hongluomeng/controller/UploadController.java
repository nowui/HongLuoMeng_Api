package com.hongluomeng.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.ActionKey;
import com.jfinal.kit.PathKit;
import com.jfinal.upload.UploadFile;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.service.UploadService;
import com.hongluomeng.type.CodeEnum;

public class UploadController extends BaseController {

	private UploadService uploadService = new UploadService();

	@ActionKey(Const.URL_UPLOAD_LIST)
	public void list() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		String user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		List<File> fileList = new ArrayList<File>();

		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();

		Utility.createUserUploadPath(user_id);

		File pathFile = new File(PathKit.getWebRootPath() + "/" + Const.UPLOAD_FILE + "/" + user_id);

		File [] files = pathFile.listFiles();

		if (files.length > 0) {
			Arrays.sort(files, new CompratorByLastModified());
		}

		for (File file : files) {
			String extName = file.getName().substring(file.getName().lastIndexOf(".") + 1);

			if (".jpg.gif.png.bmp.JPG.GIF.PNG.BMP".contains(extName)) {
				fileList.add(file);
			}
		}

		Integer start = Utility.getStarNumber(jsonObject);
		Integer end = Utility.getEndNumber(jsonObject);

		for (int i = 0; i < fileList.size(); i++) {
			if (i >= start && i < start + end) {
				File file = fileList.get(i);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(Const.KEY_URL, "/upload/" + user_id + "/" + file.getName());
				resultList.add(map);
			}
		}

		Map<String, Object> resultMap = Utility.setResultMap(fileList.size(), resultList);

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", resultMap));
	}

	@ActionKey(Const.URL_UPLOAD_IMAGE)
	public void uploadImage() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		List<UploadFile> uploadFileList = getFiles(request_user_id, 1024 * 1024 * 2);

		JSONArray jsonArray = new JSONArray();

		for (UploadFile uploadFile : uploadFileList) {
			String path = uploadService.uploadImage(uploadFile, request_user_id);

			if(! Utility.isNullOrEmpty(path)) {
				jsonArray.add(path);
			}
		}

		if(jsonArray.size() == 0) {
			throw new RuntimeException("上传出错了");
		} else {
			renderJson(Utility.setResponse(CodeEnum.CODE_200, "", jsonArray));
		}
	}

	public class CompratorByLastModified implements Comparator<File> {

	    public int compare(File f1, File f2) {
	        long diff = f1.lastModified() - f2.lastModified();
	        if (diff > 0) {
	               return -1;
	        } else if (diff == 0) {
	               return 0;
	        } else {
	              return 1;
	        }
	    }
	}
}