package com.n11.conference.service.test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.powermock.reflect.Whitebox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.n11.conference.dto.ConferencePlanDto;
import com.n11.conference.entity.ConferencePlan;
import com.n11.conference.service.ConferencePlanService;
import com.spring.application.SpringBootApp;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootApp.class)
class ConferencePlanServiceTest {

	@Autowired
	private ConferencePlanService conferencePlanService;

	public static List<ConferencePlanDto> conferencePlanDtoList;
	public static Map<String, Integer> conferencePlanMap;

	public static final int FIRST_SESSION_TOTAL_TIME_DURATION = 180;
	public static final int SECOND_SESSION_TOTAL_TIME_DURATION = 240;

	@BeforeEach
	void setUp() {
		conferencePlanDtoList = new ArrayList<>();
		conferencePlanDtoList.add(ConferencePlanDto.builder().subject("Architecting Your Codebase").timeDuration(60).build());
		conferencePlanDtoList.add(ConferencePlanDto.builder().subject("Overdoing it in Python").timeDuration(45).build());
		conferencePlanDtoList.add(ConferencePlanDto.builder().subject("Flavors of Concurrency in Java").timeDuration(30).build());
		conferencePlanDtoList.add(ConferencePlanDto.builder().subject("Ruby Errors from Mismatched Gem Versions").timeDuration(45).build());
		conferencePlanDtoList.add(ConferencePlanDto.builder().subject("JUnit 5 - Shaping the Future of Testing on the JVM").timeDuration(45).build());
		conferencePlanDtoList.add(ConferencePlanDto.builder().subject("Cloud Native Java lightning").timeDuration(0).build());
		conferencePlanDtoList.add(ConferencePlanDto.builder().subject("Communicating Over Distance").timeDuration(60).build());
		conferencePlanDtoList.add(ConferencePlanDto.builder().subject("AWS Technical Essentials").timeDuration(45).build());
		conferencePlanDtoList.add(ConferencePlanDto.builder().subject("Continuous Delivery").timeDuration(30).build());
		conferencePlanDtoList.add(ConferencePlanDto.builder().subject("Monitoring Reactive Applications").timeDuration(30).build());
		conferencePlanDtoList.add(ConferencePlanDto.builder().subject("Pair Programming vs Noise").timeDuration(45).build());
		conferencePlanDtoList.add(ConferencePlanDto.builder().subject("Rails Magic").timeDuration(60).build());
		conferencePlanDtoList.add(ConferencePlanDto.builder().subject("Microservices 'Just Right'").timeDuration(60).build());
		conferencePlanDtoList.add(ConferencePlanDto.builder().subject("Clojure Ate Scala (on my project)").timeDuration(45).build());
		conferencePlanDtoList.add(ConferencePlanDto.builder().subject("Perfect Scalability").timeDuration(30).build());
		conferencePlanDtoList.add(ConferencePlanDto.builder().subject("Apache Spark").timeDuration(30).build());
		conferencePlanDtoList.add(ConferencePlanDto.builder().subject("Async Testing on JVM").timeDuration(60).build());
		conferencePlanDtoList.add(ConferencePlanDto.builder().subject("A World Without HackerNews").timeDuration(30).build());
		conferencePlanDtoList.add(ConferencePlanDto.builder().subject("User Interface CSS in Apps").timeDuration(30).build());
		
		conferencePlanMap = conferencePlanDtoList.stream()
				.collect(Collectors.toMap(ConferencePlanDto::getSubject, ConferencePlanDto::getTimeDuration));
		
	}

	@ParameterizedTest
	@ValueSource(ints ={ FIRST_SESSION_TOTAL_TIME_DURATION, SECOND_SESSION_TOTAL_TIME_DURATION })
	void whenPlanSessionTestForFirstSessionAndSecondSession_thenFirstSessionHaveLunchTime_thenSecondSessionNotHaveLunchTime(int sessionDuration) throws Exception {
		LocalDateTime time;

		if (sessionDuration == FIRST_SESSION_TOTAL_TIME_DURATION)
			time = LocalDate.now().atTime(9, 0);
		else
			time = LocalDate.now().atTime(13, 0);

		List<String> session = Whitebox.invokeMethod(conferencePlanService, "planSessions", conferencePlanMap, sessionDuration, time);
		
		session.forEach(System.out::println);

		if (sessionDuration == FIRST_SESSION_TOTAL_TIME_DURATION)
			assertTrue(session.stream().filter(e -> e.contains("Lunch")).findFirst().isPresent());
		
		else
			assertTrue(session.stream().filter(e -> e.contains("Lunch")).findAny().isEmpty());

	}

	@Test
	void whenConferencePlannerPlanTheList_thenMustPlanAllPresentation() {
		List<List<String>> planList = conferencePlanService.conferencePlanner(conferencePlanDtoList);
		int count = 0;
		
		planList.forEach(e -> {
			e.forEach(System.out::println);
			System.out.println();
			System.out.println();
		});

		for (List<String> list : planList) {
			for(String str : list ) {
				if(str.contains("Networking Event") || str.contains("Lunch"))
						continue;
				count++;
			}
		}

		assertEquals(conferencePlanDtoList.size(), count);

	}
	
	@Test
	void whenConvertConferencePlanDtoListToConferencePlanList_thenCorrect() throws Exception {
		List<ConferencePlanDto> dtoList = new ArrayList<>();
		dtoList.add(ConferencePlanDto.builder().subject("test1").timeDuration(10).build());
		dtoList.add(ConferencePlanDto.builder().subject("test2").timeDuration(20).build());
		
		List<ConferencePlan> planList = Whitebox.invokeMethod(conferencePlanService, "convertConferencePlanDtoToConferencePlan", dtoList);
		
		assertEquals(dtoList.get(0).getSubject(), planList.get(0).getSubject());
		assertEquals(dtoList.get(0).getTimeDuration(), planList.get(0).getTimeDuration());
		assertEquals(dtoList.get(1).getSubject(), planList.get(1).getSubject());
		assertEquals(dtoList.get(1).getTimeDuration(), planList.get(1).getTimeDuration());
	}

}
