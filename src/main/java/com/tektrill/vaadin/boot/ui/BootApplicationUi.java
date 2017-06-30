package com.tektrill.vaadin.boot.ui;

import com.tektrill.vaadin.boot.constants.BootConstants;
import com.tektrill.vaadin.boot.events.LoginEvent;
import com.tektrill.vaadin.boot.services.BootLoginService;
import com.tektrill.vaadin.boot.util.VaadinBootUtils;
import com.tektrill.vaadin.boot.view.BootDefaultView;
import com.tektrill.vaadin.boot.view.BootLoginView;
import com.tektrill.vaadin.boot.view.BootMenuView;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import org.apache.commons.lang3.StringUtils;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.events.EventScope;
import org.vaadin.spring.events.annotation.EventBusListenerMethod;

/**
 * Created by isadsrj on 26/06/2017.
 */
@SpringUI
@Theme("valo")
public class BootApplicationUi extends UI {

	private final SpringViewProvider springViewProvider;
	private final SpringNavigator springNavigator;
	private final EventBus.UIEventBus uiEventBus;

	private Panel viewContainer = new Panel();

	private final BootLoginService bootLoginService;

	public BootApplicationUi(SpringViewProvider springViewProvider, SpringNavigator springNavigator, EventBus.UIEventBus uiEventBus, BootLoginService bootLoginService) {
		this.springViewProvider = springViewProvider;
		this.springNavigator = springNavigator;
		this.uiEventBus = uiEventBus;
		this.bootLoginService = bootLoginService;
	}


	@Override
	protected void init(VaadinRequest request) {
		setContent(viewContainer);
		initNavigator();
		uiEventBus.subscribe(this);
		VaadinBootUtils.navigateToView(BootDefaultView.VIEW_NAME);
	}



	private void initNavigator() {
		springNavigator.init(this, viewContainer);
	}


	@EventBusListenerMethod(scope = EventScope.UI)
	private void onLoginEvent(LoginEvent loginEvent) {
		VaadinSession.getCurrent().setAttribute(BootConstants.USER, loginEvent.getUsername());
		VaadinBootUtils.navigateToView(BootMenuView.VIEW_NAME);
	}


}
