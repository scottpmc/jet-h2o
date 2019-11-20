# Example of using Hazelcast Jet to operationalize an H2O ML Data Model

[H2O](https://www.h2o.ai) is a leading platform for building, training, and executing machine learning data models and algorithms.

[Hazelcast Jet](https://jet.hazelcast.org) is a leading in-memory compute platform for high performace stream processing.

This repository is an example of enabling H2O data models for use in real-time stream processing by Jet.

In this example we use H2O's MOJO model type to execute locally in the Jet run time. We create an H2O Deep Learning model, train it with a well known sample data set [(Iris)](https://en.wikipedia.org/wiki/Iris_flower_data_set) commonly used to prove statistical classification, export the model to a MOJO and incorporate the MOJO into a Jet Pipeline.

## Building the MOJO

You will find a pre-built MOJO in the 'bin' directory of this repository. For further exploration, they python code used to build the included MOJO is found in the same 'bin' directory (h2o-test-model.py). H2O MOJO's are packaged in 'zip' format and should be left in this state. It can be unzipped to explore the model details if desired.

Note: The class: JetIrisExample contains the main method to run the example. This class has a constant:

    private static String FQ_MODEL_FILE = "./bin/DeepLearning_Model.zip";
    
If using a model located other than the 'bin' directory, the constant needs to be updated to the correct path.

## Running the Example

This example has 2 independent running parts: The Jet process that consumes messages and invokes the H2O Deep Learning model, and a data generator that publishes random data instances for the Jet process to consume. For simplicity, a Hazelcast Map with an event journal is used.

Compile the example with Maven:

    $ mvn clean package
    
And then run the Jet consumer with:

    mvn exec:java -Dexec.mainClass="com.hazelcast.jet.h2o.JetIrisExample"
    
This creates a Jet instance and configures the necessary Map and Journal components for the generator to connect to.
    
Once the Jet instance is up, run the generator with:

    mvn exec:java -Dexec.mainClass="com.hazelcast.jet.h2o.model.iris.DataGenerator"
    
Note: The DataGenerator class relies on the constant:

    private static String IRIS_DATA_FILE = "./bin/iris.data";
    
as the source of Iris data points. If using a different data source file, this constant need to be updated to the correct path.

Once both are running, observed output should similar to;

    06:14,937 [192.168.1.109]:5701 [jet] [3.2] Match: A: Iris-versicolor     P: Iris-versicolor     2.0955834684188753E-7    0.9999706883800461       2.9102061607096872E-5    
    06:15,041 [192.168.1.109]:5701 [jet] [3.2] Match: A: Iris-versicolor     P: Iris-versicolor     5.610579151736111E-6     0.9997230126546157       2.7137676623249313E-4    
    06:15,147 [192.168.1.109]:5701 [jet] [3.2] Match: A: Iris-setosa         P: Iris-setosa         0.9999999753770564       2.462294346114491E-8     1.3031347700520356E-23   
    06:15,249 [192.168.1.109]:5701 [jet] [3.2] Match: A: Iris-virginica      P: Iris-virginica      1.1965838286945377E-12   0.009540653606585582     0.9904593463922179       
    06:15,355 [192.168.1.109]:5701 [jet] [3.2] Match: A: Iris-virginica      P: Iris-virginica      5.1397645372094E-14      6.324490814564425E-4     0.9993675509184922       
    06:15,455 [192.168.1.109]:5701 [jet] [3.2] Match: A: Iris-virginica      P: Iris-virginica      5.515384584643081E-12    0.09150538508504653      0.9084946149094381  
    
This output indicates that Jet is consuming the Iris data points, invoking the H2O Deep Learning model, and receiving the Iris type prediction. The Iris data point contains the correct type for comparison of the prediction (A: Actual and P: Predicted). Jet compares the Actual to Predicted types and prints Match or Miss. The trailing decimal numbers indicate the models statistical evaluation of the data point for each Iris type. 





