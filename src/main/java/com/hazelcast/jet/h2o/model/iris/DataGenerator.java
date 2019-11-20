package com.hazelcast.jet.h2o.model.iris;

import java.util.Map;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;

public class DataGenerator {
	
	//This needs an Iris data file
	//See: https://archive.ics.uci.edu/ml/datasets/iris
	private static String IRIS_DATA_FILE = "/tmp/iris.data";
	
	//This needs to match the map name the pipeline is looking for
	private static String SOURCE_MAP_NAME = "testMap";

	public static void main(String[] args) throws Exception {		
		HazelcastInstance client = HazelcastClient.newHazelcastClient(buildClientConfig());
		Map<Integer, IrisDefinition> map = client.getMap(SOURCE_MAP_NAME);
		IrisData testData = new IrisData(IRIS_DATA_FILE);
		
		for(int i=0; i<2000; i++) {
			map.put(i, testData.getRandomIris());
			Thread.sleep(100);
		}
		
		client.shutdown();
	}
	
	public static ClientConfig buildClientConfig() {
		ClientConfig config = new ClientConfig();		
		config.getGroupConfig().setName("jet");
		return config;
	}
}
