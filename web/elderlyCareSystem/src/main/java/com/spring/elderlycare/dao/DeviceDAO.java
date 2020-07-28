package com.spring.elderlycare.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.spring.elderlycare.dto.DevicesDTO;
import com.spring.elderlycare.dto.Elderly2DTO;
import com.spring.elderlycare.dto.ElderlyDTO;

@Component
public interface DeviceDAO {
	public List<Elderly2DTO> selectList(String id);
	public ElderlyDTO selectOne(int dnum);
	public DevicesDTO selectDevice(int dnum);
	public void insertDevice(ElderlyDTO edto, String id);
	public void updateDevice(ElderlyDTO dudto);
	public void deleteDevice(int dnum);
	
}
