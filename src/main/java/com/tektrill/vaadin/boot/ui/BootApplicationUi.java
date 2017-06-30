package com.tektrill.vaadin.boot.ui;

import com.tektrill.vaadin.boot.constants.BootConstants;
import com.tektrill.vaadin.boot.events.LoginEvent;
import com.tektrill.vaadin.boot.events.LogoutEvent;
import com.tektrill.vaadin.boot.services.BootLoginService;
import com.tektrill.vaadin.boot.util.AuthenticationUtils;
import com.tektrill.vaadin.boot.util.VaadinBootUtils;
import com.tektrill.vaadin.boot.view.BootDefaultView;
import com.tektrill.vaadin.boot.view.BootMenuView;
import com.vaadin.annotations.Theme;
import com.vaadin.server.DeploymentConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.server.WrappedSession;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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

	@Value("${server.context-path}")
	private String contextRoot;

	@Value("${server.session.timeout}")
	private String sessionTimeOut;
	private static final Logger LOGGER = LoggerFactory.getLogger(BootApplicationUi.class);



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
		try {
			AuthenticationUtils.doLogin(loginEvent.getUsername(), loginEvent.getPassword());
			configureSessionParameters();
			VaadinSession.getCurrent().setAttribute(BootConstants.USER, loginEvent.getUsername());
			VaadinBootUtils.navigateToView(BootMenuView.VIEW_NAME);
		}catch(Exception e){
			Notification.show(e.getMessage(), Notification.Type.ERROR_MESSAGE);
		}


	}

	@EventBusListenerMethod(scope = EventScope.UI)
	private void onLogoutEvent(LogoutEvent logoutEvent) {
		AuthenticationUtils.doLogout();
		VaadinSession.getCurrent().setAttribute(BootConstants.USER, null);
		getSession().close();
		getUI().getPage().setLocation(contextRoot);
	}

	private void configureSessionParameters() {
		Integer timeOut = null;
		if (StringUtils.isNotEmpty(sessionTimeOut)) {
			timeOut = Integer.parseInt(sessionTimeOut);
		}
		if (timeOut == null) {
			timeOut = 1800;
		}
		VaadinSession.getCurrent().getSession().setMaxInactiveInterval(timeOut);
		DeploymentConfiguration config = getSession().getConfiguration();
		LOGGER.info("Heart Beat Interval :" + config.getHeartbeatInterval());
		WrappedSession session = getSession().getSession();
		LOGGER.info("Max Inactive Interval :" + session.getMaxInactiveInterval());
	}




}
