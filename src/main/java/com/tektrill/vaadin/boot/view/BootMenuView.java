package com.tektrill.vaadin.boot.view;

import com.tektrill.vaadin.boot.constants.BootConstants;
import com.tektrill.vaadin.boot.events.LogoutEvent;
import com.tektrill.vaadin.boot.util.VaadinBootUtils;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.events.EventBus;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.label.MLabel;

/**
 * Created by isadsrj on 26/06/2017.
 */
@SpringView(name = BootMenuView.VIEW_NAME)
public class BootMenuView extends VerticalLayout implements View {

	public static final String VIEW_NAME = "home";

	@Autowired
	private EventBus.UIEventBus uiEventBus;

	private Label lblWelcome = new MLabel("Welcome");
	private Button btnLogout = new MButton("Logout", this::onLogoutButtonClicked);

	@Override
	public void enter(ViewChangeListener.ViewChangeEvent event) {
		lblWelcome.setValue("Welcome - "+ (String) VaadinSession.getCurrent().getAttribute(BootConstants.USER));
		addComponent(lblWelcome);
		addComponent(btnLogout);

	}
	private void onLogoutButtonClicked(Button.ClickEvent event){
		uiEventBus.publish(this, new LogoutEvent());
	}
}
