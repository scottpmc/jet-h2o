#Example of using Hazelcast Jet to operationalize an H2O ML Data Model

[H2O](https://www.h2o.ai) is a leading platform for building, training, and executing machine learning data models and algorithms.

[Hazelcast Jet](https://jet.hazelcast.org) is a leading in-memory compute platform for high performace stream processing.

This repository is an example of enabling H2O data models for use in real-time stream processing by Jet.

In this example we use H2O's MOJO model type to execute locally in the Jet run time. We create an H2O Deep Learning model, train it with a well known sample data set [(Iris)](https://en.wikipedia.org/wiki/Iris_flower_data_set) commonly used to prove statistical classification, export the model to a MOJO and incorporate the MOJO into a Jet Pipeline.

Building the MOJO

You will find a pre-built MOJO in the 'bin' directory of this repository. For further exploration, they python file used to build the included MOJO is found in the same directory. H2O MOJO's are packaged in 'zip' format and should be left in this state. It can be unzipped to explore the model details if desired.


