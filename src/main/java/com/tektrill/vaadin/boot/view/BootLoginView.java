package com.tektrill.vaadin.boot.view;

import com.tektrill.vaadin.boot.events.LoginEvent;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.events.EventBus;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.layouts.MVerticalLayout;

import javax.annotation.PostConstruct;

/**
 * Created by isadsrj on 26/06/2017.
 */
@ViewScope
@SpringView(name = BootLoginView.VIEW_NAME)
public class BootLoginView extends VerticalLayout implements View {

	public static final String VIEW_NAME = "login";
	private TextField txtUsername = new MTextField("Username");
	private PasswordField pwdPassword = new PasswordField("Password");
	private Button btnLogin = new MButton("Login", this::onLoginButtonClicked);

	@Autowired
	private EventBus.UIEventBus uiEventBus;

	@PostConstruct
	private void init() {
		//prepareLayout();
		System.out.println("Layout created!!!");
		btnLogin.setClickShortcut(ShortcutAction.KeyCode.ENTER);
	}

	private void prepareLayout() {
		addComponent(new MVerticalLayout(
				txtUsername,
				pwdPassword,
				btnLogin)
		);
	}

	@Override
	public void enter(ViewChangeListener.ViewChangeEvent event) {
		prepareLayout();
	}

	private void onLoginButtonClicked(Button.ClickEvent event) {
		uiEventBus.publish(this, new LoginEvent(txtUsername.getValue(), pwdPassword.getValue()));
	}
}
