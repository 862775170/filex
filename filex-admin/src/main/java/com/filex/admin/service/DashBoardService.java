package com.filex.admin.service;

import java.util.List;
import java.util.Map;

public interface DashBoardService {

	/**
	 * 发送文件最多的10个用户
	 * 
	 * @return
	 */
	List<Map<String, Object>> getSendFileCountTop10();

}
