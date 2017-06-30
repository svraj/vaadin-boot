package com.tektrill.vaadin.boot.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by svraj on 10/07/2017.
 */
public class CustomRealm  extends AuthorizingRealm{
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		//TODO Set Permissions and Roles here
		//add Permission Resources
		//String username = (String) principals.getPrimaryPrincipal();
		//info.setStringPermissions(ldapAuthenticationService.findPermissions(username));
		//add Roles String[Set<String> roles]
		//info.setRoles(roles);
		return new SimpleAuthorizationInfo();
	}

	/**
	 * This is a temp method which checks if username = password. Else throw AuthenicationException
	 * @param authenticationToken
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		SimpleAuthenticationInfo info;
		String userName = ((UsernamePasswordToken) authenticationToken).getUsername();
		String password = new String(((UsernamePasswordToken) authenticationToken).getPassword());
		if(StringUtils.isNotEmpty(userName) && StringUtils.isNotEmpty(password)){
			if(StringUtils.equals(userName, password)){
				return new SimpleAuthenticationInfo(userName,password,getName());
			}else{
				throw new AuthenticationException("Invalid credentials");
			}
		}else{
			throw new AuthenticationException("Credentials must not be empty!!!");
		}
	}
}
