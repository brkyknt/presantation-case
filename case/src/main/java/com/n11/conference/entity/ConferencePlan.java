package com.n11.conference.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class ConferencePlan implements Serializable {

	private static final long serialVersionUID = 4867778471758500330L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "SUBJECT", nullable = false)
	private String subject;

	@Column(name = "TIME_DURATION", nullable = true)
	private int timeDuration;

}
