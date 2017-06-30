package com.tektrill.vaadin.boot.util;

import com.vaadin.ui.UI;

/**
 * Created by isadsrj on 26/06/2017.
 */
public class VaadinBootUtils {

	public static void navigateToView(String viewName) {
		UI.getCurrent().getNavigator().navigateTo(viewName);
	}


	public static String getLogoutPageLocation() {
		return "/";
	}
}
