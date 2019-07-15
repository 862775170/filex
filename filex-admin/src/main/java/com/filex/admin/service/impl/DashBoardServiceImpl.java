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
	private final static String RECEIVE_RULE_TOP_10 = "select r.rule_name,count(1)as confirm_count from public.rule_confirm d LEFT JOIN public.rule r on d.rule_id = r.rule_id GROUP BY r.rule_id ORDER BY confirm_count desc LIMIT 10";

	private final static String SEND_FILE_COUNT_TOP_10 = "SELECT l.source_user_id,COUNT(1) as file_count  from public.file_exchange_log as l GROUP BY l.source_user_id ORDER BY file_count desc limit 10";

	private final static String RECEIVE_FILE_COUNT_TOP_10 = "SELECT l.target_user_id,COUNT(1) as file_count  from \"public\".file_exchange_log as l GROUP BY l.target_user_id ORDER BY file_count desc limit 10";

	private static final String RULE_COUNT = "SELECT count(*) FROM public.rule";
	
	private static final String USER_COUNT = "SELECT count(*) FROM (SELECT user_id FROM public.rule union SELECT user_id FROM \"public\".rule_confirm) b";
	
	private static final String FILEE_XCHANGE_COUNT_30_DAY = "select b.day,COALESCE(q.file_count, 0) as file_count FROM (select to_char(generate_series(now() +  '-29 day', now(), '1 day'),'yyyy-MM-dd') as \"day\") b LEFT JOIN (SELECT to_char(l.create_time,'yyyy-MM-dd') as \"day\",count(1) as file_count FROM \"public\".\"file_exchange_log\" as l where l.create_time BETWEEN (now() - INTERVAL '30 days') AND now() group by to_char(l.create_time,'yyyy-MM-dd'))q on(q.\"day\" = b.\"day\")";

	@Override
	public List<Map<String, Object>> getSendFileCountTop10() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(SEND_FILE_COUNT_TOP_10);
		Set<String> userIds = new HashSet<String>();
		queryForList.forEach(action -> {
			String e = action.get("source_user_id").toString();
			userIds.add(e);
		});
		Map<String, String> userNames = userService.getUserNames(userIds);
		List<Map<String, Object>> collect = queryForList.stream().map(m -> {
			String username = userNames.get(m.get("source_user_id"));
			m.put("username", username);
			return m;
		}).collect(Collectors.toList());
		return collect;
	}

	@Override
	public List<Map<String, Object>> getReceiveFileCountTop10() {
		List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(RECEIVE_FILE_COUNT_TOP_10);
		Set<String> userIds = new HashSet<String>();
		queryForList.forEach(action -> {
			String e = action.get("target_user_id").toString();
			userIds.add(e);
		});
		Map<String, String> userNames = userService.getUserNames(userIds);
		List<Map<String, Object>> collect = queryForList.stream().map(m -> {
			String username = userNames.get(m.get("target_user_id"));
			m.put("username", username);
			return m;
		}).collect(Collectors.toList());
		return collect;
	}

	@Override
	public List<Map<String, Object>> getReceiveRuleTop10() {
		List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(RECEIVE_RULE_TOP_10);
		return queryForList;
	}

	@Override
	public int getRuleCount() {
		Integer queryForObject = jdbcTemplate.queryForObject(RULE_COUNT, Integer.class);
		if (queryForObject != null) {
			return queryForObject;
		}
		return 0;
	}

	@Override
	public int getUserCount() {
		// TODO Auto-generated method stub
		Integer queryForObject = jdbcTemplate.queryForObject(USER_COUNT, Integer.class);
		if (queryForObject != null) {
			return queryForObject;
		}
		return 0;
	}

	@Override
	public List<Map<String, Object>> getFileExchangeCount30day() {
		List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(FILEE_XCHANGE_COUNT_30_DAY);
		return queryForList;
	}
}
