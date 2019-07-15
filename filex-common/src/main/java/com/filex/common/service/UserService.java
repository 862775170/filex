package com.filex.common.service;

import java.util.Collection;
import java.util.Map;

import com.filex.common.model.UserInfo;

public interface UserService {
	/**
	 * 获得用户Map
	 * 
	 * @param userIds
	 * @return
	 */
	Map<String, String> getUserNames(Collection<String> userIds);

	/**
	 * 获得用户详情
	 * 
	 * @param userId
	 * @return
	 */
	UserInfo getUserInfo(String userId);

	UserInfo getUserInfoByToken(String token);

}
