package com.tektrill.vaadin.boot.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by isadsrj on 11/07/2017.
 */
public class AuthenticationUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationUtils.class);

	public static void doLogin(String username, String password) {
		shiroAuthenicate(username, password);
	}

	public static void doLogout() {
		SecurityUtils.getSubject().logout();
	}

	private static void shiroAuthenicate(String username, String password) {
		Subject currentUser = SecurityUtils.getSubject();
		try {
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			currentUser.login(token);
		} catch (Exception e) {
			LOGGER.error("Error on trying to login user - " + username + " !!! " + e.getMessage(),e);
			throw e;
		}
	}
}
