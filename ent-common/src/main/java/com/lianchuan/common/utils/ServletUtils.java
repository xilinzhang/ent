package com.lianchuan.common.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet工具类.
 * 
 */
public final class ServletUtils {

	private static final String MULTIPART = "multipart/form-data";
	private static final String POST = "POST";
	private static final String P3P_HEADER = "CP=\"NOI CURa ADMa DEVa TAIa OUR BUS IND UNI COM NAV INT\"";
	private static final String UNKNOWN = "unknown";
	private static final String X_FORWARDED_FOR_HEADER = "x-forwarded-for";
	private static final String X_REAL_IP_HEADER = "x-real-ip";
	private static final String PROXY_CLIENT_IP_HEADER = "Proxy-Client-IP";
	private static final String WL_PROXY_CLIENT_IP_HEADER = "WL-Proxy-Client-IP";

	private static Logger logger = LoggerFactory.getLogger(ServletUtils.class);

	private static String charSet = "UTF-8";

	private ServletUtils() {
	}

	/**
	 * 设置字符集
	 * 
	 * @param charSet
	 *            字符集
	 */
	public static void setCharSet(String charSet) {
		ServletUtils.charSet = charSet;
	}

	/**
	 * 清除http缓存
	 * 
	 * @param response
	 *            http响应
	 */
	public static void clearCache(HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
	}

	/**
	 * 是否是POST请求
	 * 
	 * @param request
	 *            http请求
	 * @return 是true，否则false
	 */
	public static boolean isPost(HttpServletRequest request) {
		return POST.equals(request.getMethod());
	}

	/**
	 * 设置http请求的字符集为GBK
	 * 
	 * @param request
	 *            http请求
	 * @throws ServletException
	 * @throws IOException
	 */
	public static void setGBKEncoding(HttpServletRequest request) throws ServletException, IOException {
		if (request.getCharacterEncoding() == null) {
			request.setCharacterEncoding("GBK");
			logger.debug("request.setCharacterEncoding[GBK]");
		}
	}

	/**
	 * 设置http请求的字符集
	 * 
	 * @param request
	 *            http请求
	 * @throws ServletException
	 * @throws IOException
	 */
	public static void setCharacterEncoding(HttpServletRequest request) throws ServletException, IOException {
		if (request.getCharacterEncoding() == null) {
			request.setCharacterEncoding(charSet);
			logger.debug("request.setCharacterEncoding[" + charSet + "]");
		}
	}

	/**
	 * 是否是文件上传的http请求
	 * 
	 * @param request
	 *            http请求
	 * @return 是true，否则false
	 */
	public static boolean isMultipart(HttpServletRequest request) {
		String contentType = request.getContentType();
		return contentType != null && contentType.startsWith(MULTIPART);
	}

	/**
	 * 输出字符串内容到http响应中
	 * 
	 * @param response
	 *            http响应
	 * @param value
	 *            字符串内容
	 * @throws IOException
	 */
	public static void print(HttpServletResponse response, String value) throws IOException {
		response.setContentType("text/html; charset=" + charSet);
		// response.setContentLength(value.getBytes().length);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(value);
			out.flush();
		} finally {
			out.close();
		}
	}

	/**
	 * 增加cookie，cookie的path为"/"
	 * 
	 * @param response
	 *            http响应
	 * @param cookieName
	 *            cookie的名称
	 * @param cookieValue
	 *            cookie的值
	 * @param maxAge
	 *            cookie的存活期，毫秒为单位
	 */
	public static void addCookie(HttpServletResponse response, String cookieName, String cookieValue, int maxAge) {
		Cookie cookie = new Cookie(cookieName, cookieValue);
		cookie.setMaxAge(maxAge);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	/**
	 * 取得cookie的值
	 * 
	 * @param request
	 *            http请求
	 * @param cookieName
	 *            cookie的名称
	 * @return cookie的值
	 */
	public static String getCookie(HttpServletRequest request, String cookieName) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return null;
		}
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookieName.equals(cookie.getName())) {
				return cookie.getValue();
			}
		}
		return null;
	}

	/**
	 * 删除cookie
	 * 
	 * @param response
	 *            http响应
	 * @param cookieName
	 *            cookie的名称
	 */
	public static void removeCookie(HttpServletResponse response, String cookieName) {
		addCookie(response, cookieName, "", 0);
	}

	/**
	 * 取得网站的跟目录，比如：http://www.zdsoft.net
	 * 
	 * @param request
	 *            http请求
	 * @return 网站的跟目录
	 */
	public static String getWebsiteRoot(HttpServletRequest request) {
		int serverPort = request.getServerPort();
		return request.getScheme() + "://" + request.getServerName() + (serverPort == 80 ? "" : ":" + serverPort)
				+ request.getContextPath();
	}

	/**
	 * 为了跨域设置cookie需要增加P3P的HTTP头
	 * 
	 * @param response
	 */
	public static void setP3PHeader(HttpServletResponse response) {
		response.setHeader("P3P", P3P_HEADER);
	}

	/**
	 * 在服务器使用多级反向代理(例如Squid)的情况下, 直接调用 {@link ServletRequest#getRemoteAddr()}
	 * 方法将无法获取客户端真实的IP地址. 而使用此方法可以获取到客户端真实的IP地址.
	 * 
	 * @param request
	 *            请求对象
	 * @return 请求客户端的真实IP地址.
	 */
	public static String getRemoteAddr(HttpServletRequest request) {
		String remoteAddr = request.getHeader(X_FORWARDED_FOR_HEADER);

		if (remoteAddr == null || remoteAddr.trim().length() == 0 || UNKNOWN.equalsIgnoreCase(remoteAddr)) {
			remoteAddr = request.getHeader(X_REAL_IP_HEADER);
		}

		if (remoteAddr == null || remoteAddr.trim().length() == 0 || UNKNOWN.equalsIgnoreCase(remoteAddr)) {
			remoteAddr = request.getHeader(PROXY_CLIENT_IP_HEADER);
		}

		if (remoteAddr == null || remoteAddr.trim().length() == 0 || UNKNOWN.equalsIgnoreCase(remoteAddr)) {
			remoteAddr = request.getHeader(WL_PROXY_CLIENT_IP_HEADER);
		}

		if (remoteAddr == null || remoteAddr.trim().length() == 0 || UNKNOWN.equalsIgnoreCase(remoteAddr)) {
			remoteAddr = request.getRemoteAddr();
		}

		if (remoteAddr != null && remoteAddr.trim().length() != 0) {
			String[] remoteAddrs = remoteAddr.split(",");
			remoteAddr = remoteAddrs[0].trim();
		}

		return remoteAddr;
	}

	
	
	
	/**
	 * 下载文件到客户端
	 * @param in
	 * @param response
	 */
	public static void downloadFile(byte[] data, String fileName, String contentType, HttpServletRequest request, HttpServletResponse response) {
		InputStream in = new ByteArrayInputStream(data);
		downloadFile(in, fileName, contentType, request, response);
	}
	
	/**
	 * 下载文件到客户端
	 * @param in
	 * @param response
	 */
	public static void downloadFile(InputStream in, String fileName, String contentType, HttpServletRequest request, HttpServletResponse response) {
		fileName = getDownLoadFileName(fileName, request);
		response.setHeader("Content-Disposition", "attachment; " + fileName);
		response.setHeader("Cache-Control", "");
		response.setContentType(contentType); 
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			byte[] buffer = new byte[128 * 1024];
			int read_count;

			while ((read_count = in.read(buffer)) != -1) {
				out.write(buffer, 0, read_count);
			}
			out.flush();
		} catch (Exception e) {

		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
			}
		}

	}

	/**
	 * 根据不同个的浏览器，返回支持中文显示的文件名编码
	 * 
	 * @param filename
	 * @param request
	 * @return
	 */
	public static String getDownLoadFileName(String filename, HttpServletRequest request) {
		String new_filename = null;
		try {
			new_filename = URLEncoder.encode(filename, "UTF8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String userAgent = request.getHeader("User-Agent");
		// System.out.println(userAgent);
		String rtn = "filename=\"" + new_filename + "\"";
		// 如果没有UA，则默认使用IE的方式进行编码，因为毕竟IE还是占多数的
		if (userAgent != null) {
			userAgent = userAgent.toLowerCase();
			// IE浏览器，只能采用URLEncoder编码
			if (userAgent.indexOf("msie") != -1) {
				rtn = "filename=\"" + new_filename + "\"";
			}
			// Opera浏览器只能采用filename*
			else if (userAgent.indexOf("opera") != -1) {
				rtn = "filename*=UTF-8''" + new_filename;
			}
			// Safari浏览器，只能采用ISO编码的中文输出
			else if (userAgent.indexOf("safari") != -1) {
				try {
					rtn = "filename=" + new String(filename.getBytes("UTF-8"), "ISO8859-1");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			// Chrome浏览器，只能采用MimeUtility编码或ISO编码的中文输出
			else if (userAgent.indexOf("applewebkit") != -1) {
				try {
					rtn = "filename=" + new String(filename.getBytes("UTF-8"), "ISO8859-1");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			// FireFox浏览器，可以使用MimeUtility或filename*或ISO编码的中文输出
			else if (userAgent.indexOf("mozilla") != -1) {
				rtn = "filename*=UTF-8''" + new_filename;
				rtn = rtn.replaceAll("\\+","%20"); 
			}
		}
		return rtn;
	}

}
