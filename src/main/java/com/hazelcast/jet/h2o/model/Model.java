package com.hazelcast.jet.h2o.model;

import com.hazelcast.jet.pipeline.ContextFactory;

import hex.genmodel.MojoModel;
import hex.genmodel.easy.EasyPredictModelWrapper;

//This class is intended to be abstract or an interface to make models more portable,
//just haven't decided how that should be yet.

public class Model {
	
	public static ContextFactory<EasyPredictModelWrapper> loadModel(String FQFilename) {
				
		ContextFactory<EasyPredictModelWrapper> modelContext = ContextFactory
                .withCreateFn(jet -> new EasyPredictModelWrapper(MojoModel.load(FQFilename)))
                .withLocalSharing();
               
		return modelContext;
	}
}
