package com.hazelcast.jet.h2o;

import java.util.Map.Entry;

import com.hazelcast.config.EventJournalConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.jet.Jet;
import com.hazelcast.jet.JetInstance;
import com.hazelcast.jet.Job;
import com.hazelcast.jet.config.JetConfig;
import com.hazelcast.jet.config.JobConfig;
import com.hazelcast.jet.h2o.model.iris.IrisDefinition;
import com.hazelcast.jet.h2o.model.iris.Model;
import com.hazelcast.jet.pipeline.ContextFactory;
import com.hazelcast.jet.pipeline.JournalInitialPosition;
import com.hazelcast.jet.pipeline.Pipeline;
import com.hazelcast.jet.pipeline.Sinks;
import com.hazelcast.jet.pipeline.Sources;
import com.hazelcast.jet.pipeline.StreamSource;


import hex.genmodel.easy.EasyPredictModelWrapper;

public class JetIrisExample {
	
	private static String SOURCE_MAP_NAME = "testMap";
	private static String FQ_MODEL_FILE = "/tmp/DeepLearning_model_python_1573778406631_1.zip";

	public static void main(String[] args) {
		System.setProperty("hazelcast.logging.type", "log4j");
		
		JetInstance jet = initializeJet(SOURCE_MAP_NAME);
		
		Job job = jet.newJob(buildPipeline(), new JobConfig().setName("H2O Iris Classification"));
		
		job.join();
		jet.shutdown();
	}
	
	private static Pipeline buildPipeline() {
	
		StreamSource<Entry<Integer, IrisDefinition>> sourceMap = Sources.mapJournal(SOURCE_MAP_NAME,
				JournalInitialPosition.START_FROM_CURRENT); 
		
		ContextFactory<EasyPredictModelWrapper> modelContext = Model.getModelContext(FQ_MODEL_FILE);
		
		Pipeline p = Pipeline.create();	
		
		p.drawFrom(sourceMap)
			.withoutTimestamps()
				.setName("Iris Incoming Source")
			.map(e -> e.getValue())
			.mapUsingContext(modelContext, (model, iris) -> Model.getPredictionWithMatch(model, iris))
				.setName("Call H2O Model")
			.drainTo(Sinks.logger())
				.setName("Simple Logger");
		
		return p;
	}
	
	private static JetInstance initializeJet(String sourceMapName) {

		MapConfig mapConfig = new MapConfig()
				.setName(sourceMapName);

		EventJournalConfig mapJournalConfig = new EventJournalConfig()
				.setMapName(sourceMapName)
				.setEnabled(true)
				.setCapacity(1000)
				.setTimeToLiveSeconds(30);

		JetConfig jetConfig = new JetConfig();

		jetConfig.getHazelcastConfig()
				.addMapConfig(mapConfig)
				.addEventJournalConfig(mapJournalConfig)
				.getManagementCenterConfig()
					.setEnabled(true)
					.setUrl("http://localhost:8080/hazelcast-mancenter/");
		return Jet.newJetInstance(jetConfig);

	}

}
