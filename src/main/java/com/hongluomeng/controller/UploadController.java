package com.hongluomeng.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

		File pathFile = new File(PathKit.getWebRootPath() + "/upload/" + user_id);

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


		for (int i = 0; i < fileList.size(); i++) {
			File file = fileList.get(i);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put(Const.KEY_URL, "/upload/" + user_id + "/" + file.getName());

			int start = Utility.getStarNumber(jsonObject);
			int end = Utility.getEndNumber(jsonObject);

			if (i >= start && i < start + end) {
				resultList.add(map);
			}
		}

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", fileList.size(), resultList));
	}

	@ActionKey(Const.URL_UPLOAD_IMAGE)
	public void uploadImage() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		List<UploadFile> uploadFileList = getFiles(request_user_id, 1024 * 1024);

		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();

		for (UploadFile uploadFile : uploadFileList) {
			Map<String, Object> map = uploadService.uploadImage(uploadFile, request_user_id);

			if(map != null) {
				list.add(0, map);
			}
		}

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", list));
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