package com.filex.admin.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.filex.admin.service.DashBoardService;
import com.filex.common.service.UserService;

@Service
public class DashBoardServiceImpl implements DashBoardService {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private UserService userService;

	private final static String SEND_FILE_COUNT_TOP_10 = "SELECT l.source_user_id,COUNT(1) as file_count  from public.file_exchange_log as l GROUP BY l.source_user_id ORDER BY file_count desc limit 10";

	@Override
	public List<Map<String, Object>> getSendFileCountTop10() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(SEND_FILE_COUNT_TOP_10);
		Set<String> userIds = new HashSet<String>();
		queryForList.forEach(action -> {
			action.get("source_user_id").toString();

		});
		Map<String, String> userNames = userService.getUserNames(userIds);
		List<Map<String, Object>> collect = queryForList.stream().map(m -> {
			String username = userNames.get(m.get("source_user_id"));
			m.put("username", username);
			return m;
		}).collect(Collectors.toList());
		return collect;
	}
}
