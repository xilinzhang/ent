package com.lianchuan.ma.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.lianchuan.common.exception.EntException;
import com.lianchuan.common.exception.ErrorId;
import com.lianchuan.ma.entity.vo.result.BaseMaResult;

public class ExceptionAction extends BaseMaAction implements HandlerExceptionResolver {

	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		logger.error("path:{}", request.getRequestURI(), ex);
		try {
			BaseMaResult result = new BaseMaResult();
			result.setDes(ex.getMessage());
			if (ex instanceof EntException) {
				EntException entException = (EntException) ex;
				result.setStatus(entException.getStatus());
			} else {
				result.setStatus(ErrorId.ERROR_UNKNOWN);
			}
			writeValue(result);
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

}
