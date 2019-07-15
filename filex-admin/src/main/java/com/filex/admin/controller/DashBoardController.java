package com.filex.admin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.filex.admin.service.DashBoardService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/dashboard")
public class DashBoardController {
	@Autowired
	private DashBoardService dashBoardService;

	@GetMapping("exchange/count/30/day")
	@ApiOperation("最近30天文件文件交换数-折线图")
	public ResponseEntity<Object> getFileExchangeCount30day() {
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	@GetMapping("send/file/count/top/10")
	@ApiOperation("发送文件最多的10个用户-柱状图")
	public ResponseEntity<Object> getSendFileCountTop10() {

		List<Map<String, Object>> result = dashBoardService.getSendFileCountTop10();
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}

	@GetMapping("receive/file/count/top/10")
	@ApiOperation("接受文件最多的10个用户-柱状图")
	public ResponseEntity<Object> getReceiveFileCountTop10() {
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	@GetMapping("receive/rule/top/10")
	@ApiOperation("接收规则人数最多10个规则-柱状图")
	public ResponseEntity<Object> getReceiveRuleTop10() {
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	@GetMapping("user/count")
	@ApiOperation("用户数")
	public ResponseEntity<Object> getUserCount() {
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	@GetMapping("rule/count")
	@ApiOperation("用户数")
	public ResponseEntity<Object> getRuleCount() {
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
}
