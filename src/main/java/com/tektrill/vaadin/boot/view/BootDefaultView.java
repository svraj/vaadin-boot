package com.tektrill.vaadin.boot.view;

import com.tektrill.vaadin.boot.constants.BootConstants;
import com.tektrill.vaadin.boot.util.VaadinBootUtils;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.VerticalLayout;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by ISADSRJ on 28/06/2017.
 */
@SpringView(name = BootDefaultView.VIEW_NAME)
@ViewScope
public class BootDefaultView extends VerticalLayout implements View {
	public static final String VIEW_NAME="";

	@Override
	public void enter(ViewChangeListener.ViewChangeEvent event) {
		calculateTargetViewAndNavigate();
	}

	private void calculateTargetViewAndNavigate() {
		String currentUser = (String) VaadinSession.getCurrent().getAttribute(BootConstants.USER);
		if (StringUtils.isEmpty(currentUser)) {
			VaadinBootUtils.navigateToView(BootLoginView.VIEW_NAME);
		} else {
			VaadinBootUtils.navigateToView(BootMenuView.VIEW_NAME);
		}
	}
}
