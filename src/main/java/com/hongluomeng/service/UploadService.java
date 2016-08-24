package com.hongluomeng.service;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.jfinal.kit.FileKit;
import com.jfinal.kit.PathKit;
import com.jfinal.upload.UploadFile;

public class UploadService {

	public Map<String, Object> uploadImage(UploadFile uploadFile, String request_user_id) {
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

					return null;
		        }
			} catch (IOException e) {
				isImage = false;

				return null;
			} finally {
				image = null;
			}
		}

		if (isImage) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(Const.KEY_URL, "/upload/" + request_user_id + "/" + uploadFile.getFileName());

			uploadFile.getFile().renameTo(new File(PathKit.getWebRootPath() + "/upload/" + request_user_id + "/" + Utility.getUUID() + "." + extName));

			return map;
		} else {
			FileKit.delete(uploadFile.getFile());

			return null;
		}
	}

}
