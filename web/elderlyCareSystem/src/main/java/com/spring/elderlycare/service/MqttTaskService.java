package com.spring.elderlycare.service;

import org.springframework.stereotype.Component;

import com.spring.elderlycare.dto.DevicesDTO;

@Component
public interface MqttTaskService {

	public void runningBackground(DevicesDTO ddto);
	//private void savedb();


}
