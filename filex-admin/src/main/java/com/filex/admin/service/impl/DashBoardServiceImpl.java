package com.filex.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DashBoardServiceImpl {
	@Autowired
	private JdbcTemplate jdbcTemplate;
}
