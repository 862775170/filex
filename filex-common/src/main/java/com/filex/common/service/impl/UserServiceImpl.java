package com.filex.common.service.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.filex.common.ApiConstants;
import com.filex.common.HttpClient;
import com.filex.common.model.UserInfo;
import com.filex.common.service.UserService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private HttpClient httpClient;
	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public Map<String, String> getUserNames(Collection<String> userIds) {
		Map<String, String> result = new HashMap<>();
		if (userIds.isEmpty()) {
			return result;
		} else {
			Map<String, Object> body = new HashMap<>();
			body.put("userIds", userIds);
			ResponseEntity<JsonObject> json = httpClient.postByJson(ApiConstants.api_batch_user_info, body,
					JsonObject.class);
			log.debug("http: /user/multiGetUserInfo result => {}", json);
			if (json.getBody().has("result")) {
				json.getBody().get("result").getAsJsonArray().forEach(el -> {
					if (!el.isJsonNull()) {
						JsonObject elobj = el.getAsJsonObject();
						result.put(elobj.get("id").getAsString(), elobj.get("nick_name").getAsString());
					}
				});
			}
			return result;
		}
	}

	@Override
	public UserInfo getUserInfo(String userId) {
		Map<String, Object> body = new HashMap<>();
		body.put("userIds", new String[] { userId });
		ResponseEntity<JsonObject> postByJson = httpClient.postByJson(ApiConstants.api_batch_user_info, body,
				JsonObject.class);
		UserInfo userInfo = null;
		if (postByJson.getBody().has("result")) {
			JsonArray jsonArray = postByJson.getBody().get("result").getAsJsonArray();
			if (!jsonArray.isJsonNull() && jsonArray.size() == 1) {
				JsonElement jsonElement = jsonArray.get(0);
				if (!jsonElement.isJsonNull()) {
					log.info("{}", jsonElement);
					try {
						userInfo = objectMapper.readValue(jsonElement.toString(), UserInfo.class);
						log.debug("userInfo => {}", userInfo);
					} catch (IOException e) {
						log.error("json pojo error", e);
					}
				}
			} else {
				log.warn("");
			}
		}
		return userInfo;
	}

	@Override
	public UserInfo getUserInfoByToken(String token) {
		ResponseEntity<JsonObject> postByJson = httpClient.login(ApiConstants.api_user_user_info, token,
				JsonObject.class);
		UserInfo userInfo = null;
		if (postByJson.getBody().has("result")) {
			JsonObject jsonObj = postByJson.getBody().get("result").getAsJsonObject();
			if (!jsonObj.isJsonNull()) {
				if (!jsonObj.isJsonNull()) {
					log.info("{}", jsonObj);
					try {
						userInfo = objectMapper.readValue(jsonObj.toString(), UserInfo.class);
						log.debug("userInfo => {}", userInfo);
					} catch (IOException e) {
						log.error("json pojo error", e);
					}
				}
			} else {
				log.warn("");
			}
		}
		return userInfo;
	}

}
