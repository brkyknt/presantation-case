package com.n11.conference.repository;

import org.springframework.data.repository.CrudRepository;

import com.n11.conference.entity.ConferencePlan;

public interface ConferenceRepository extends CrudRepository<ConferencePlan, Long> {

}
