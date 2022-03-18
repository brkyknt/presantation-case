package com.n11.conference.service;

import java.util.List;

import com.n11.conference.dto.ConferencePlanDto;

public interface ConferencePlanService {

	List<List<String>> conferencePlanner(List<ConferencePlanDto> conferencePlannerDtoList);

}
