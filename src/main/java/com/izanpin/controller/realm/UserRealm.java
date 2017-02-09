package com.izanpin.controller.realm;

import com.izanpin.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//		String userno = (String) principals.getPrimaryPrincipal();
//		Result<RcUser> result = biz.queryByUserNo(userno);
//		if(result.isStatus()){
//			Result<List<RcRole>> resultRole = biz.queryRoles(result.getResultData().getId());
//			if(resultRole.isStatus()){
//				//获取角色
//				HashSet<String> roles = new HashSet<String>();
//				for (RcRole rcRole : resultRole.getResultData()) {
//					roles.add(rcRole.getRoleValue());
//				}
//				System.out.println("角色："+roles);
//				authorizationInfo.setRoles(roles);
//
//				//获取权限
//				Result<List<RcPermission>> resultPermission = biz.queryPermissions(resultRole.getResultData());
//				if(resultPermission.isStatus()){
//					HashSet<String> permissions = new HashSet<String>();
//					for (RcPermission rcPermission : resultPermission.getResultData()) {
//						permissions.add(rcPermission.getPermissionsValue());
//					}
//					System.out.println("权限："+permissions);
//					authorizationInfo.setStringPermissions(permissions);
//				}
//			}
//		}
        return authorizationInfo;

    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//		String userno = (String) authenticationToken.getPrincipal();
//		String password = new String((char[]) authenticationToken.getCredentials());
//		Result<RcUser> result = biz.login(userno, password);
//		if (result.isStatus()) {
//			Session session = SecurityUtils.getSubject().getSession();
//			session.setAttribute(Constants.Token.RONCOO, userno);
//			RcUser user = result.getResultData();
//			return new SimpleAuthenticationInfo(user.getUserNo(), user.getPassword(), getName());
//		}
        return null;
    }
}
