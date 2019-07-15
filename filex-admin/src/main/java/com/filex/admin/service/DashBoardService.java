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

	/**
	 * 接收文件最多的10个用户
	 * 
	 * @return
	 */
	List<Map<String, Object>> getReceiveFileCountTop10();

	/**
	 * 接受规则最多的10个规则
	 * 
	 * @return
	 */
	List<Map<String, Object>> getReceiveRuleTop10();

	/**
	 * 规则数
	 * 
	 * @return
	 */
	int getRuleCount();

	/**
	 * 用户数
	 * 
	 * @return
	 */
	int getUserCount();

	/**
	 * 统计近30天文件交换数
	 * @return
	 */
	List<Map<String, Object>> getFileExchangeCount30day();

}
