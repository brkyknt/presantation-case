package com.n11.conference.service.impl;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.n11.conference.dto.ConferencePlanDto;
import com.n11.conference.entity.ConferencePlan;
import com.n11.conference.repository.ConferenceRepository;
import com.n11.conference.service.ConferencePlanService;

@Service
public class ConferencePlanServiceImpl implements ConferencePlanService {

	public static final int SESSION_TOTAL_TIME_DURATION = 420;
	public static final String TIME_FORMAT_PATTERN = "hh:mm a";
	public static final int FIRST_SESSION_TOTAL_TIME_DURATION = 180;
	public static final int SECOND_SESSION_TOTAL_TIME_DURATION = 240;

	private static final Logger logger = LoggerFactory.getLogger(ConferencePlanServiceImpl.class);

	@Autowired
	private ConferenceRepository conferenceRepository;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { SQLException.class })
	public List<List<String>> conferencePlanner(List<ConferencePlanDto> conferencePlanDtoList) {
		List<List<String>> plannedList = new ArrayList<>();
		try {
			conferenceRepository.saveAll(convertConferencePlanDtoToConferencePlan(conferencePlanDtoList));

			int totalTimeDuration = conferencePlanDtoList.stream().mapToInt(ConferencePlanDto::getTimeDuration).sum();
			int sessionSize = (int) Math.ceil(totalTimeDuration / Double.valueOf(SESSION_TOTAL_TIME_DURATION));

			Map<String, Integer> conferencePlanMap = conferencePlanDtoList.stream()
					.collect(Collectors.toMap(ConferencePlanDto::getSubject, ConferencePlanDto::getTimeDuration));

			planFirstSessions(plannedList, sessionSize, conferencePlanMap);
			planSecondSessions(plannedList, sessionSize, conferencePlanMap);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return plannedList;
	}

	private void planFirstSessions(List<List<String>> plannedList, int sessionSize,
			Map<String, Integer> conferencePlanMap) {
		for (int i = 0; i < sessionSize; i++) {
			LocalDateTime time = LocalDate.now().atTime(9, 0);

			List<String> plan = planSessions(conferencePlanMap, FIRST_SESSION_TOTAL_TIME_DURATION, time);

			plannedList.add(plan);

		}
	}

	private void planSecondSessions(List<List<String>> plannedList, int sessionSize,
			Map<String, Integer> conferencePlanMap) {
		for (int i = 0; i < sessionSize; i++) {
			LocalDateTime time = LocalDate.now().atTime(13, 0);

			List<String> plan = planSessions(conferencePlanMap, SECOND_SESSION_TOTAL_TIME_DURATION, time);
			plannedList.get(i).addAll(plan);

		}
	}

	private List<String> planSessions(Map<String, Integer> conferencePlanDtoMap, int sessionDuration,
			LocalDateTime time) {
		List<String> session = new ArrayList<>();

		int elepsedTime = 0;
		Map<String, Integer> shallowMap = produceConferencePlanDtoMapClone(conferencePlanDtoMap);

		time = findEligiblePresentationsForSessions(conferencePlanDtoMap, time, session, sessionDuration, elepsedTime,
				shallowMap);

		ifHaveSuitableTimeThenStartNetworkingEvent(conferencePlanDtoMap, time, session);

		return session;

	}

	private LocalDateTime findEligiblePresentationsForSessions(Map<String, Integer> conferencePlanDtoMap,
			LocalDateTime time, List<String> session, int sessionDuration, int elepsedTime,
			Map<String, Integer> shallowMap) {

		int flag = 0;
		for (Map.Entry<String, Integer> map : shallowMap.entrySet()) {
			flag++;
			if (sessionDuration == elepsedTime
					|| ((elepsedTime + map.getValue() > sessionDuration) && flag == shallowMap.size() - 1)) {
				if (time.isBefore(LocalDate.now().atTime(12, 01))) {
					time = (LocalDate.now().atTime(12, 0));
					session.add(subjectFormatter(time, ""));
					time = time.plusMinutes(60);
				}
				break;
			}

			if (map.getValue() > 0 && map.getValue() > (sessionDuration - elepsedTime))
				continue;

			if (map.getValue() > 0 && map.getValue() <= sessionDuration
					&& map.getValue() <= (sessionDuration - elepsedTime)) {
				session.add(subjectFormatter(time, map.getKey() + " " + map.getValue() + "min"));
				time = time.plusMinutes(map.getValue());
				elepsedTime += map.getValue();
				conferencePlanDtoMap.remove(map.getKey());
			}
		}

		return time;
	}

	private void ifHaveSuitableTimeThenStartNetworkingEvent(Map<String, Integer> conferencePlanDtoMap,
			LocalDateTime time, List<String> session) {

		Iterator<?> it = conferencePlanDtoMap.entrySet().iterator();

		if (it.hasNext()) {
			@SuppressWarnings("unchecked")
			Map.Entry<String, Integer> map = (Entry<String, Integer>) it.next();
			if (map.getValue() == 0 && time.isAfter(LocalDate.now().atTime(15, 59))
					&& time.isBefore(LocalDate.now().atTime(16, 55))) {
				session.add(subjectFormatter(time, map.getKey()));
				time = time.plusMinutes(5);
				session.add(time.format(DateTimeFormatter.ofPattern(TIME_FORMAT_PATTERN)) + " " + "Networking Event");
				conferencePlanDtoMap.remove(map.getKey());
			}

		}
	}

	private String subjectFormatter(LocalDateTime time, String subject) {
		if (!subject.isEmpty())
			return time.format(DateTimeFormatter.ofPattern(TIME_FORMAT_PATTERN)) + " " + subject;
		else
			return time.format(DateTimeFormatter.ofPattern(TIME_FORMAT_PATTERN)) + " " + "Lunch";
	}

	private Map<String, Integer> produceConferencePlanDtoMapClone(Map<String, Integer> conferencePlanDtoMap) {
		Map<String, Integer> shallowMap = new HashMap<>();
		shallowMap.putAll(conferencePlanDtoMap);

		return shallowMap;
	}

	private List<ConferencePlan> convertConferencePlanDtoToConferencePlan(
			List<ConferencePlanDto> conferencePlanDtoList) {
		ModelMapper modelMapper = new ModelMapper();

		return conferencePlanDtoList.stream().map(e -> modelMapper.map(e, ConferencePlan.class))
				.collect(Collectors.toList());
	}

}
