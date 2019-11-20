package com.hazelcast.jet.h2o.model.iris;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class IrisData {
	
	private ArrayList<IrisDefinition> irisData = new ArrayList<IrisDefinition>();
	private Random rand = new Random();
	private int arraySize;
	
	public IrisData(String filename) {
		try (Scanner scanner = new Scanner(new File(filename));) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if(!line.isBlank()) irisData.add(parseIrisLine(scanner.nextLine()));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		arraySize = irisData.size();
	}

	private IrisDefinition parseIrisLine(String irisLine) {
		String[] values = irisLine.split(",");
		IrisDefinition iris = new IrisDefinition();
		iris.setSepal_len(Double.parseDouble(values[0]));
		iris.setSepal_wid(Double.parseDouble(values[1]));
		iris.setPetal_len(Double.parseDouble(values[2]));
		iris.setPetal_wid(Double.parseDouble(values[3]));
		iris.setActualType(values[4]);
		
		return iris;
	}
	
	public IrisDefinition getRandomIris() {
		return irisData.get(rand.nextInt(arraySize));
	}

}
