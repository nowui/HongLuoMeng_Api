package com.hongluomeng.service;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;

import org.apache.commons.codec.binary.Base64;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.jfinal.kit.FileKit;
import com.jfinal.kit.PathKit;
import com.jfinal.upload.UploadFile;

public class UploadService extends BaseService {

	public String uploadImage(UploadFile uploadFile, String request_user_id) {
		Boolean isImage = true;

		String suffix = uploadFile.getFileName().substring(uploadFile.getFileName().lastIndexOf(".") + 1);

		if (!Const.UPLOAD_IMAGE_NAME.contains(suffix)) {
			isImage = false;
		} else {
			Image image = null;

			try {
				image = ImageIO.read(uploadFile.getFile());

				int width = image.getWidth(null);
				int height = image.getHeight(null);

				if (image == null || width <= 0 || height <= 0) {
					isImage = false;
				}
			} catch (IOException e) {
				isImage = false;
                e.printStackTrace();
			} finally {
				image = null;
			}
		}

		if (isImage) {
			String name = Utility.getUUID();

			Utility.createUserUploadPath(request_user_id);

			try {
				resizeImage(uploadFile.getFile(), PathKit.getWebRootPath() + "/" + Const.UPLOAD_FILE + "/" + request_user_id + "/" + Const.UPLOAD_SMALL + "/" + name + "." + suffix, 100);

				resizeImage(uploadFile.getFile(), PathKit.getWebRootPath() + "/" + Const.UPLOAD_FILE + "/" + request_user_id + "/" + name + "." + suffix, 320);
			} catch (IOException e) {
				e.printStackTrace();
			}

			uploadFile.getFile().renameTo(new File(PathKit.getWebRootPath() + "/" + Const.UPLOAD_FILE + "/" + request_user_id + "/" + Const.UPLOAD_LARGE + "/" + name + "." + suffix));

			return "/" + Const.UPLOAD_FILE + "/" + request_user_id + "/" + name + "." + suffix;
		} else {
			FileKit.delete(uploadFile.getFile());

            return "";

			//throw new RuntimeException("图片格式不对");
		}
	}

	public void uploadBase64(JSONObject jsonObject, String request_user_id) {
		String dataString = jsonObject.getString(Const.KEY_DATA);

		String name = Utility.getUUID();

		String suffix = dataString.substring(11, dataString.indexOf(";base64,"));

		String imageString = dataString.substring(dataString.indexOf(","));

		byte[] imageByte = Base64.decodeBase64(imageString.getBytes());

		File imageFile = new File(PathKit.getWebRootPath() + "/" + Const.UPLOAD_FILE + "/" + request_user_id + "/" + Const.UPLOAD_LARGE + "/" + name + "." + suffix);

		try {
			FileImageOutputStream fos = new FileImageOutputStream(imageFile);
			fos.write(imageByte);
			fos.flush();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			resizeImage(imageFile, PathKit.getWebRootPath() + "/" + Const.UPLOAD_FILE + "/" + request_user_id + "/" + Const.UPLOAD_SMALL + "/" + name + "." + suffix, 100);

			resizeImage(imageFile, PathKit.getWebRootPath() + "/" + Const.UPLOAD_FILE + "/" + request_user_id + "/" + name + "." + suffix, 320);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void resizeImage(File imageFile, String newPath, int length) throws IOException {
		BufferedImage bufferedImage = ImageIO.read(imageFile);

		int originalWidth = bufferedImage.getWidth();
		int originalHeight = bufferedImage.getHeight();

		int newWidth = 0;
		int newHeight = 0;


		if (originalWidth > length) {
			if (originalWidth > originalHeight) {
				newWidth = length;

				double scale = (double) originalWidth / (double) newWidth;
				newHeight = (int) (originalHeight / scale);
			} else {
				newHeight = length;

				double scale = (double) originalHeight / (double) newHeight;
				newWidth = (int) (originalWidth / scale);
			}
		} else {
			newWidth = originalWidth;
			newHeight = originalHeight;
		}

		zoomImageUtils(imageFile, newPath, bufferedImage, newWidth, newHeight);
	}

	private void zoomImageUtils(File imageFile, String newPath, BufferedImage bufferedImage, int width, int height) throws IOException {

		String suffix = imageFile.getName().substring(imageFile.getName().lastIndexOf(".") + 1);

		// 处理 png 背景变黑的问题
		if (suffix != null && (suffix.trim().toLowerCase().endsWith("png") || suffix.trim().toLowerCase().endsWith("gif"))) {
			BufferedImage to = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = to.createGraphics();
			to = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
			g2d.dispose();

			g2d = to.createGraphics();
			Image from = bufferedImage.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
			g2d.drawImage(from, 0, 0, null);
			g2d.dispose();

			ImageIO.write(to, suffix, new File(newPath));
		} else {
			BufferedImage newImage = new BufferedImage(width, height, bufferedImage.getType());
			Graphics g = newImage.getGraphics();
			g.drawImage(bufferedImage, 0, 0, width, height, null);
			g.dispose();
			ImageIO.write(newImage, suffix, new File(newPath));
		}
	}

}
