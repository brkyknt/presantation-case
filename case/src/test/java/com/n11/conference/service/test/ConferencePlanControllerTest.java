package com.n11.conference.service.test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.n11.conference.controller.ConferencePlanController;
import com.n11.conference.dto.ConferencePlanWrapper;
import com.n11.conference.service.ConferencePlanService;

@ExtendWith(MockitoExtension.class)
class ConferencePlanControllerTest {

	@Mock
	private ConferencePlanService conferencePlanService;

	@InjectMocks
	private ConferencePlanController conferencePlanController;

	private static ConferencePlanWrapper wrapper;

	@BeforeEach
	void setMockOutput() {
		wrapper = new ConferencePlanWrapper();
		wrapper.setConferencePlanDtoList(new ArrayList<>());
	}

	@Test
	void shouldReturnDefaultMessage() {
		List<List<String>> response = conferencePlanController.conferencePlan(wrapper);
		assertTrue(response.isEmpty());
	}
}
