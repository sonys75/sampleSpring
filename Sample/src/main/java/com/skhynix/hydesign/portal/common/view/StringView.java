package com.skhynix.hydesign.portal.common.view;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.view.AbstractView;

/**
 * The Class MappingJacksonJsonView.
 *
 * @author l.xue.nong
 */
public class StringView extends AbstractView {

	/** The Constant DEFAULT_CONTENT_TYPE. */
	public static final String DEFAULT_CONTENT_TYPE = "text/plain";

	/** The model keys. */
	private Set<String> modelKeys;

	/** The extract value from single key model. */
	private boolean extractValueFromSingleKeyModel = false;

	/** The disable caching. */
	private boolean disableCaching = true;

	/**
	 * Instantiates a new mapping jackson json view.
	 */
	public StringView() {
		setContentType(DEFAULT_CONTENT_TYPE);
		setExposePathVariables(false);
	}

	/**
	 * Sets the model key.
	 *
	 * @param modelKey
	 *            the new model key
	 */
	public void setModelKey(String modelKey) {
		this.modelKeys = Collections.singleton(modelKey);
	}

	/**
	 * Sets the model keys.
	 *
	 * @param modelKeys
	 *            the new model keys
	 */
	public void setModelKeys(Set<String> modelKeys) {
		this.modelKeys = modelKeys;
	}

	/**
	 * Gets the model keys.
	 *
	 * @return the model keys
	 */
	public Set<String> getModelKeys() {
		return this.modelKeys;
	}

	/**
	 * Sets the extract value from single key model.
	 *
	 * @param extractValueFromSingleKeyModel
	 *            the new extract value from single key model
	 */
	public void setExtractValueFromSingleKeyModel( boolean extractValueFromSingleKeyModel) {
		this.extractValueFromSingleKeyModel = extractValueFromSingleKeyModel;
	}

	/**
	 * Sets the disable caching.
	 *
	 * @param disableCaching
	 *            the new disable caching
	 */
	public void setDisableCaching(boolean disableCaching) {
		this.disableCaching = disableCaching;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.springframework.web.servlet.view.AbstractView#prepareResponse(javax
	 * .servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void prepareResponse(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType(getContentType());
		if (this.disableCaching) {
			response.addHeader("Pragma", "no-cache");
			response.addHeader("Cache-Control", "no-cache, no-store, max-age=0");
			response.addDateHeader("Expires", 1L);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.springframework.web.servlet.view.AbstractView#renderMergedOutputModel
	 * (java.util.Map, javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*성능개선안
		Object value = filterModel(model);
		*/
		String respText = model.containsKey("response") ? (String) model.get("response") : "";
		response.getOutputStream().write(respText.getBytes());
	}

	/**
	 * Filter model.
	 *
	 * @param model
	 *            the model
	 * @return the object
	 */
	protected Object filterModel(Map<String, Object> model) {
		Map<String, Object> result = new HashMap<String, Object>(model.size());
		Set<String> renderedAttributes = (!CollectionUtils.isEmpty(this.modelKeys) ? this.modelKeys : model.keySet());
		for (Map.Entry<String, Object> entry : model.entrySet()) {
			if (!(entry.getValue() instanceof BindingResult) && renderedAttributes.contains(entry.getKey())) {
				if(entry.getKey().equals("response")) result.put(entry.getKey(), entry.getValue());
			}
		}
		return (this.extractValueFromSingleKeyModel && result.size() == 1 ? result.values().iterator().next() : result);
	}

}
