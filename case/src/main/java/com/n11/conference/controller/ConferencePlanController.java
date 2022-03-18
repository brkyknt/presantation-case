package com.n11.conference.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.n11.conference.dto.ConferencePlanWrapper;
import com.n11.conference.service.ConferencePlanService;

@RestController
public class ConferencePlanController {

	@Autowired
	private ConferencePlanService conferencePlanService;

	@PostMapping("/plan")
	public List<List<String>> conferencePlan(@RequestBody ConferencePlanWrapper conferencePlanWrapper) {
		return conferencePlanService.conferencePlanner(conferencePlanWrapper.getConferencePlanDtoList());
	}

}
