package com.n11.conference.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConferencePlanWrapper {
	private List<ConferencePlanDto> conferencePlanDtoList;
}
