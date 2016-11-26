package com.hongluomeng.common;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.hongluomeng.type.CodeEnum;
import com.jfinal.kit.PathKit;

public class Utility {

	private static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	/*
	 * 获取客户端IP地址
	 *
	 * @param request
	 * @return
	 */
	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 获取完整请求路径(含内容路径及请求参数)
	 *
	 * @param request
	 * @return
	 */
	public static String getRequestURIWithParam(HttpServletRequest request) {
		return request.getRequestURI() + (request.getQueryString() == null ? "" : "?" + request.getQueryString());
	}

	public static String isLength(String str, int minLength, int maxLength, String message) {
		int length = str.length();

		if (length < minLength) {
			return message + "长度小于" + minLength;
		} else if (length >= minLength || length <= maxLength) {
			return "";
		} else {
			return message + "长度大于" + maxLength;
		}
	}

	public static boolean isNullOrEmpty(Object obj) {
		if (obj == null)
			return true;

		if (obj instanceof CharSequence)
			return ((CharSequence) obj).length() == 0;

		if (obj instanceof Collection)
			return ((Collection<?>) obj).isEmpty();

		if (obj instanceof Map)
			return ((Map<?, ?>) obj).isEmpty();

		if (obj instanceof Object[]) {
			Object[] object = (Object[]) obj;
			if (object.length == 0) {
				return true;
			}
			boolean empty = true;
			for (int i = 0; i < object.length; i++) {
				if (!isNullOrEmpty(object[i])) {
					empty = false;
					break;
				}
			}
			return empty;
		}
		return false;
	}

	public static boolean isNull(Object obj) {
		return obj == null;
	}

	public static boolean isEmail(String str) {
		Pattern emailPattern = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		Matcher matcher = emailPattern.matcher(str);
		if (matcher.find()) {
			return true;
		}
		return false;
	}

	public static boolean isPhone(String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$");
		m = p.matcher(str);
		b = m.matches();
		return b;
	}

	public static boolean isLength(String str) {
		String regex = "^.{8,}$";
		return match(regex, str);
	}

	public static boolean isDate(String str) {
		String regex = "^((((1[6-9]|[2-9]//d)//d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]//d|3[01]))|(((1[6-9]|[2-9]//d)//d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]//d|30))|(((1[6-9]|[2-9]//d)//d{2})-0?2-(0?[1-9]|1//d|2[0-8]))|(((1[6-9]|[2-9]//d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-)) (20|21|22|23|[0-1]?//d):[0-5]?//d:[0-5]?//d$";
		return match(regex, str);
	}

	public static boolean isNumber(String str) {
		String regex = "^[0-9]*$";
		return match(regex, str);
	}

	public static boolean isIntNumber(String str) {
		String regex = "^//+?[1-9][0-9]*$";
		return match(regex, str);
	}

	public static boolean isDecimal(String str) {
		String regex = "^[0-9]+(.[0-9]{2})?$";
		return match(regex, str);
	}

	private static boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	public static Map<String, Object> setResponse(CodeEnum code, String message, Object data) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Const.KEY_CODE, code.getKey());
		if (code.equals(CodeEnum.CODE_200)) {
			map.put(Const.KEY_DATA, data);
		} else {
			map.put(Const.KEY_MESSAGE, message.replaceAll("java.lang.RuntimeException: ", ""));
		}
		return map;
	}

	public static Map<String, Object> setResultMap(Integer total, Object data) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put(Const.KEY_TOTAL, total);
		resultMap.put(Const.KEY_LIST, data);

		return resultMap;
	}

	public static Integer getStarNumber(Map<String, Object> map) {
		Integer page = Integer.parseInt(String.valueOf(map.get(Const.KEY_PAGE)));
		Integer limit = Integer.parseInt(String.valueOf(map.get(Const.KEY_LIMIT)));

		if (page > 0) {
			return (page - 1) * limit;
		} else {
			return 1;
		}
	}

	public static Integer getEndNumber(Map<String, Object> map) {
		Integer limit = Integer.parseInt(String.valueOf(map.get(Const.KEY_LIMIT)));

		return limit > 0 ? limit : 0;
	}

	public static Long getLongValue(Object value) {
		if (isNullOrEmpty(value)) {
			return null;
		} else {
			return Long.valueOf(value.toString());
		}
	}

	public static Integer getIntegerValue(Object value) {
		if (isNullOrEmpty(value)) {
			return null;
		} else {
			return Integer.valueOf(value.toString());
		}
	}

	public static Date getDateTime(String dateTime) {
		try {
			return dateTimeFormat.parse(dateTime);
		} catch (ParseException e) {
			e.printStackTrace();

			throw new RuntimeException("时间格式不正确");
		}
	}

	public static Date getDate(String date) throws ParseException {
		return dateFormat.parse(date);
	}

	public static String getDateString(Date dateTime) {
		return dateFormat.format(dateTime);
	}

	public static String getDateTimeString(Date dateTime) {
		return dateTimeFormat.format(dateTime);
	}

	public static Object[][] getObjectArray(List<Object[]> parameterList) {
		Object[][] parameterArray = new Object[parameterList.size()][];
		int i = 0;
		for (Object[] oo : parameterList) {
			parameterArray[i++] = oo;
		}

		return parameterArray;
	}

	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static boolean checkSql(String str) {
		String inj_str = "'|and|exec|insert|select|delete|update|count|*|%|chr|mid|master|truncate|char|declare|;|or|-|+|,";
		String inj_stra[] = inj_str.split("|");
		for (int i = 0; i < inj_stra.length; i++) {
			if (str.indexOf(inj_stra[i]) >= 0) {
				return true;
			}
		}
		return false;
	}

//	public static void checkContent(String content) throws Exception {
//		Map<String, String> parameter = new HashMap<String, String>();
//		parameter.put("apikey", "297e48e88d49aa57c9f6f062c01cf138");
//		String result = HttpKit.post("http://apis.baidu.com/tutusoft/shajj/shajj", "content=" + content, parameter);
//
//		JSONObject jsonObject = JSONObject.parseObject(result);
//
//		if(jsonObject.getIntValue(Const.KEY_RESULT) > 0) {
//			throw new RuntimeException("包含不良信息");
//		}
//	}

	private static void createPath(String path) {
		File file = new File(path);

		if (!file.exists() && !file.isDirectory()) {
			file.mkdirs();
		}
	}

	public static void createUserUploadPath(String user_id) {
		String path = PathKit.getWebRootPath() + "/" + Const.UPLOAD_FILE + "/" + user_id;
		File file = new File(path + "/" + Const.UPLOAD_SMALL);

		if (!file.exists() && !file.isDirectory()) {
			createPath(path + "/" + Const.UPLOAD_SMALL);
			createPath(path + "/" + Const.UPLOAD_LARGE);
		}
	}

	public static String packageImagePath(String path, String type) {
		return path.substring(0, path.lastIndexOf("/")) + "/" + type + "/" + path.substring(path.lastIndexOf("/") + 1);
	}

	public static String getFixLenthString(int strLength) {
		Random rm = new Random();

		double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);

		String fixLenthString = String.valueOf(pross);

		return fixLenthString.substring(1, strLength + 1);
	}

	public static void checkPageAndLimit(Map<String, Object> map) {
		if (!map.containsKey(Const.KEY_PAGE)) {
			throw new RuntimeException("page为空");
		} else {
			if (!isNumber(map.get(Const.KEY_PAGE).toString())) {
				throw new RuntimeException("page格式不对");
			}
		}

		if (!map.containsKey(Const.KEY_LIMIT)) {
			throw new RuntimeException("limit为空");
		} else {
			if (!isNumber(map.get(Const.KEY_LIMIT).toString())) {
				throw new RuntimeException("limit格式不对");
			}
		}
	}

	public static void checkList(Map<String, Object> map) {
		if (!map.containsKey(Const.KEY_LIST)) {
			throw new RuntimeException(Const.KEY_LIST + "为空");
		}
	}

	public static void checkDecimalLength(BigDecimal bigDecimal, int start, int end, String message) {
		if (isNullOrEmpty(bigDecimal)) {
			throw new RuntimeException(message + "不能为空");
		}
	}

	public static void checkIntegerLength(Integer integer, int start, String message) {
		checkIntegerLength(integer, start, start, message);
	}

	public static void checkIntegerLength(Integer integer, int start, int end, String message) {
		if (isNullOrEmpty(integer)) {
			throw new RuntimeException(message);
		}

		checkStringLength(integer.toString(), start, start, message);
	}

	public static void checkStringLength(String str, int start, String message) {
		checkStringLength(str, start, start, message);
	}

	public static void checkStringLength(String str, int start, int end, String message) {
		if (start != end) {
			if (start == 0) {
				message += "长度应该少于" + end;
			} else {
				message += "长度应该为" + start + "至" + end;
			}
		} else {
			if (start == 0) {
				message += "不能为空";
			} else {
				message += "长度应该为" + start;
			}
		}

		if (isNullOrEmpty(str)) {
			throw new RuntimeException(message);
		}

		String regex = "^.{" + start + "," + end + "}$";
		if (!match(regex, str)) {
			throw new RuntimeException(message);
		}
	}

	public static void checkNullOrEmpty(Object object, String message) {
		if (isNullOrEmpty(object)) {
			throw new RuntimeException(message + "不能为空");
		}
	}

	public static void checkNull(Object object, String message) {
		if (isNull(object)) {
			throw new RuntimeException(message + "不能为空");
		}
	}

}
