package com.hazelcast.jet.h2o.model.iris;

import com.hazelcast.jet.pipeline.ContextFactory;

import hex.genmodel.MojoModel;
import hex.genmodel.easy.EasyPredictModelWrapper;
import hex.genmodel.easy.RowData;
import hex.genmodel.easy.exception.PredictException;
import hex.genmodel.easy.prediction.MultinomialModelPrediction;

public class Model {
	
	public static ContextFactory<EasyPredictModelWrapper> getModelContext(String FQFilename) {
		ContextFactory<EasyPredictModelWrapper> modelContext = ContextFactory
                .withCreateFn(jet -> new EasyPredictModelWrapper(MojoModel.load(FQFilename)))
                .withLocalSharing();
               
		return modelContext;
	}
	
	public static String getPrediction(EasyPredictModelWrapper model, IrisDefinition iris) throws PredictException {
		MultinomialModelPrediction p = applyModel(model, iris);
		return formatPrediction(p);
	}
	
	public static String getPredictionWithMatch(EasyPredictModelWrapper model, IrisDefinition iris)
			throws PredictException {
		MultinomialModelPrediction p = applyModel(model, iris);
		if (iris.getActualType().equals(p.label)) {
			return "Match: " + "A: " + String.format("%1$-" + 20 + "s", iris.getActualType()) + "P: "
					+ formatPrediction(p);
		} else {
			return "Miss:  " + "A: " + String.format("%1$-" + 20 + "s", iris.getActualType()) + "P: "
					+ formatPrediction(p);
		}

	}
	
	public static MultinomialModelPrediction applyModel(EasyPredictModelWrapper model, IrisDefinition iris) throws PredictException {
		
		RowData row = new RowData();
		row.put("sepal_len", iris.getSepal_len());
		row.put("sepal_wid", iris.getSepal_wid());
		row.put("petal_len", iris.getPetal_len());
		row.put("petal_wid", iris.getPetal_wid());
		
		MultinomialModelPrediction p = model.predictMultinomial(row);
		
		return p;
	}
	
	public static String formatPrediction(MultinomialModelPrediction p) {
		
		String result = String.format("%1$-" + 20 + "s", p.label);
		for (int i = 0; i < p.classProbabilities.length; i++) {
			result += String.format("%1$-" + 25 + "s", p.classProbabilities[i]);
		}
		
		
		return result;
	}

}
