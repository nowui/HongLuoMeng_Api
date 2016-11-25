package com.hongluomeng.common;

import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.jfinal.render.Render;

public class MyPoiRender extends Render {

	private HSSFWorkbook wb;
	private String name;

	public MyPoiRender(HSSFWorkbook wb, String name) {
		this.wb = wb;
		this.name = name;
	}

	public void render() {
		response.reset();
		response.addHeader("Content-disposition", "attachment; filename=" + name);
		response.setContentType("application/x-msdownload");

		try {
			wb.write(response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
