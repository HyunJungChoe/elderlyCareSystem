package com.spring.elderlycare.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;

import com.spring.elderlycare.dao.DatasDAO;
import com.spring.elderlycare.dto.DatasDTO;

//@Service
public class MqttTaskServiceImpl implements MqttTaskService, MqttCallback{
	private String brokerURL = "";
	private String clientId = "asyncMqtt_elderlyCare";
	private MemoryPersistence persistence = new MemoryPersistence();

	MqttAsyncClient client = null;
	MqttConnectOptions options = null;
	// ClientComms comms = null;
	// private int eld = 0;

	@Autowired DatasDAO dao;
	// private SqlSession sqlSession = null;
	@Autowired DatasDTO dto;

	// private static final String ns = "mqttSubscriber.";
	public void mqttSubscribe(String broker, int port, String topic) {
		this.brokerURL = "tcp://" + broker + ":" + port;
		try {
			// this.eld = elderly;
			// this.topic = topic;
			client = new MqttAsyncClient(brokerURL, clientId, persistence);
			options = new MqttConnectOptions();
			options.setCleanSession(true);
			//options.setAutomaticReconnect(true);

			// IMqttToken token =
			client.connect(options);
			// token.waitForCompletion();
			Thread.sleep(1000);
			client.setCallback(new MqttTaskServiceImpl());
			
			client.subscribe(topic, 2);
		} catch (Exception me) {
			if (me instanceof MqttException) {
				System.out.println("reason " + ((MqttException) me).getReasonCode());
			}
			System.out.println("msg " + me.getMessage());
			System.out.println("loc " + me.getLocalizedMessage());
			System.out.println("cause " + me.getCause());
			System.out.println("excep " + me);
			me.printStackTrace();
		}
	}

	public void connectionLost(Throwable cause) {
		System.out.println("connection lost");
		
		// cause.printStackTrace();

	}

	public void messageArrived(String topic, MqttMessage message) throws Exception {
		
		new Thread(
				()->{
					insertData(topic, message);
				}
			).start();
		//insertData(topic, message);

	}

	public void deliveryComplete(IMqttDeliveryToken token) {
		System.out.println("deliveryComplete");

	}

	private void insertData(String topic, MqttMessage message) {
		System.out.println("topic "+topic);
		//System.out.println("message "+message);
		try {
			String[] tp = topic.split("/");

			if (tp[2].equals("humid") || tp[2].equals("temp")) {
				/*
				 * Map<String, Object> obj = new HashMap<String, Object>();
				 * float data = Float.parseFloat(message.toString());
				 * obj.put("elderly", Integer.parseInt(tp[1])); 
				 * obj.put(tp[2], data);
				 * sqlSession.insert(ns+"log", obj); 
				 * dao.insertHomeDatas(obj);
				 */
			}else if(tp[2].equals("ht")){
				String datas[] = message.toString().split("/");
				dto.setElderly(Integer.parseInt(tp[1]));
				dto.setHumid(Float.parseFloat(datas[0]));
				dto.setTemp(Float.parseFloat(datas[1]));
				dao.insertHomeDatas(dto);
			}else if(tp[2].equals("vid")) {
				//"home/{num}/vid"
				/*
				 * byte[] vid = message.toString().getBytes(); Decoder decoder =
				 * Base64.getDecoder();
				 * 
				 * byte[] dvid = decoder.decode(vid); SimpleDateFormat forma = new
				 * SimpleDateFormat("yyyyMMdd"); Date time = new Date();
				 * 
				 * writeFile(forma.format(time),dvid);
				 */
			}else {
				myAlert(tp[2]);
			}

		} catch (Exception e) {
			System.out.println("catch "+topic);
			// home vid?? 왜?
			//일단 버림.
		}
	}
	private void writeFile(String filename, byte[] data) {
		if(data == null) return;
		System.out.println("write File");
		//int byteArrSize = data.length;
		
		try {
			File outFile = new File("./"+filename);
			FileOutputStream outStream = new FileOutputStream(outFile);
			outStream.write(data);
			outStream.close();
		}catch(Throwable e) {
			e.printStackTrace();
		}
	}

	private void myAlert(String tp) {
		System.out.println("alert "+tp);
		// tp : gas, alone
		/*
		 * 알림 시스템. 어플로 알림 전송. 1. humidity, temperature 이상범위 2. 3일 이상
		 * 움직임이 없을 시 4. 이상 가스 검출 시
		 */
	}

	public List<Map<String, Object>> getIPList() {
		return dao.getIPs();
	}
}

