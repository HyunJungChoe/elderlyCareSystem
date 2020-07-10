package com.spring.elderlycare.dto;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;

@Component("Datas2DTO")
public class Datas2DTO {
	private Timestamp measuredtime;
	private int ekey;
	private int estep;
	private int epulse;
	private double ekcal;
	private double ealtitude;
	private double elongitude;
	public Timestamp getMeasuredtime() {
		return measuredtime;
	}
	public void setMeasuredtime(Timestamp measuredtime) {
		this.measuredtime = measuredtime;
	}
	public int getEkey() {
		return ekey;
	}
	public void setEkey(int ekey) {
		this.ekey = ekey;
	}
	public int getEstep() {
		return estep;
	}
	public void setEstep(int estep) {
		this.estep = estep;
	}
	public int getEpulse() {
		return epulse;
	}
	public void setEpulse(int epulse) {
		this.epulse = epulse;
	}
	public double getEkcal() {
		return ekcal;
	}
	public void setEkcal(double ekcal) {
		this.ekcal = ekcal;
	}
	public double getEaltitude() {
		return ealtitude;
	}
	public void setEaltitude(double ealtitude) {
		this.ealtitude = ealtitude;
	}
	public double getElongitude() {
		return elongitude;
	}
	public void setElongitude(double elongitude) {
		this.elongitude = elongitude;
	}
	
}
