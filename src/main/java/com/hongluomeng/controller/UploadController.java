package com.hongluomeng.controller;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.ActionKey;
import com.jfinal.kit.FileKit;
import com.jfinal.kit.PathKit;
import com.jfinal.upload.UploadFile;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.type.CodeEnum;

public class UploadController extends BaseController {

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
	public void image() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		String user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		List<UploadFile> uploadFileList = getFiles(user_id, 1024 * 1024);

		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();

		for (UploadFile uploadFile : uploadFileList) {
			Boolean isImage = true;

			String extName = uploadFile.getFileName().substring(uploadFile.getFileName().lastIndexOf(".") + 1);

			if (!".jpg.gif.png.bmp.JPG.GIF.PNG.BMP".contains(extName)) {
				isImage = false;
			} else {
				Image image = null;

				try {
					image = ImageIO.read(uploadFile.getFile());

					int width = image.getWidth(null);
					int height = image.getHeight(null);

					if (image == null || width <= 0 || height <= 0) {
						isImage = false;

						break;
			        }
				} catch (IOException e) {
					isImage = false;

					break;
				} finally {
					image = null;
				}
			}

			if (isImage) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(Const.KEY_URL, "/upload/" + user_id + "/" + uploadFile.getFileName());

				list.add(0, map);

				uploadFile.getFile().renameTo(new File(PathKit.getWebRootPath() + "/upload/" + user_id + "/" + Utility.getUUID() + "." + extName));
			} else {
				FileKit.delete(uploadFile.getFile());
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