package com.hongluomeng.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Url;
import com.jfinal.core.ActionKey;
import com.jfinal.kit.FileKit;
import com.jfinal.kit.PathKit;
import com.jfinal.upload.UploadFile;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.service.UploadService;
import com.hongluomeng.type.CodeEnum;

public class UploadController extends BaseController {

	private UploadService uploadService = new UploadService();

	@ActionKey(Url.URL_UPLOAD_LIST)
	public void list() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		String user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		List<File> fileList = new ArrayList<File>();

		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

		Utility.createUserUploadPath(user_id);

		File pathFile = new File(PathKit.getWebRootPath() + "/" + Const.UPLOAD_FILE + "/" + user_id);

		File[] files = pathFile.listFiles();

		if (files.length > 0) {
			Arrays.sort(files, new CompratorByLastModified());
		}

		for (File file : files) {
			String extName = file.getName().substring(file.getName().lastIndexOf(".") + 1);

			if (Const.UPLOAD_IMAGE_NAME.contains(extName)) {
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

	@ActionKey(Url.URL_UPLOAD_IMAGE)
	public void uploadImage() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		List<UploadFile> uploadFileList = getFiles(request_user_id, 1024 * 1024 * 5);

        Boolean isOver = false;
        Boolean isImage = true;
        List<String> resultList = new ArrayList<String>();

        for (UploadFile uploadFile : uploadFileList) {
            if (uploadFile.getFile().length() > 1024 * 1024 * 2) {
                isOver = true;
            }
        }

        if(isOver) {
            for (UploadFile uploadFile : uploadFileList) {
                FileKit.delete(uploadFile.getFile());
            }

            throw new RuntimeException("文件超过2M");
        } else {
            for (UploadFile uploadFile : uploadFileList) {
                String path = uploadService.uploadImage(uploadFile, request_user_id);

                if (Utility.isNullOrEmpty(path)) {
                    isImage = false;
                }

                resultList.add(path);
            }
        }

        if(! isImage) {
            throw new RuntimeException("图片格式不对");
        }

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", resultList));
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

	@ActionKey(Url.URL_UPLOAD_BASE64)
	public void uploadBase64() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		uploadService.uploadBase64(jsonObject, request_user_id);

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

}