package com.skhynix.hydesign.portal.common.resolver;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.LocalizedResourceHelper;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.AbstractCachingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.skhynix.hydesign.portal.common.view.ExcelView;

public class ExcelViewResolver extends InternalResourceViewResolver {

/*성능개선안
    private final Logger logger = LoggerFactory.getLogger(getClass());
*/
	//private Log log = LogFactory.getLog(this.getClass());

	@Override
	protected ExcelView buildView(String viewName) throws Exception {
		ExcelView view = (ExcelView) super.buildView(viewName);
		if (!viewName.equals("excelView"))
			return view;

		LocalizedResourceHelper helper = new LocalizedResourceHelper( getApplicationContext());
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		viewName = (String) request.getAttribute("tmplt");
		Locale userLocale = RequestContextUtils.getLocale(request);
		Resource resource = helper.findLocalizedResource(super.getPrefix() + StringUtils.chomp(viewName,FilenameUtils.getExtension(viewName)),FilenameUtils.getExtension(viewName), userLocale);

		if (resource != null) {
			view.setResource(resource);
			view.setExistFile(resource.exists());
		} else {
			view.setExistFile(false);
		}

		return view;
	}

	protected View loadView(String viewName, Locale locale) throws Exception {
		//viewName = "/WEB-INF/excel/datalist.xls";
		ExcelView view = buildView(viewName);
		View result = (View) getApplicationContext().getAutowireCapableBeanFactory().initializeBean(view, viewName);
		return (view.isExistFile() ? result : null);
	}
}

/*
 * public class ExcelViewResolver extends AbstractCachingViewResolver {
 *
 * private String prefix = "";
 *
 * private String suffix = "";
 *
 * @Override protected View loadView(String viewName, Locale locale) throws
 * Exception { ExcelView excelView = new ExcelView(); String excelTemplatePath =
 * prefix + viewName + suffix; excelTemplatePath =
 * getServletContext().getRealPath(excelTemplatePath);
 * excelView.setExcelTemplatePath(excelTemplatePath); return excelView; }
 *
 * @Override protected Object getCacheKey(String viewName, Locale locale) {
 * return viewName; }
 *
 * public String getPrefix() { return prefix; }
 *
 * public void setPrefix(String prefix) { this.prefix = prefix; }
 *
 * public String getSuffix() { return suffix; }
 *
 * public void setSuffix(String suffix) { this.suffix = suffix; }
 *
 * }
 */
