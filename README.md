#Example of using Hazelcast Jet to operationalize an H2O ML Data Model

[H2O](https://www.h2o.ai) is a leading platform for building, training, and executing machine learning data models and algorithms.

[Hazelcast Jet](https://jet.hazelcast.org) is a leading in-memory compute platform for high performace stream processing.

This repository is an example of enabling H2O data models for use in real-time stream processing by Jet.

In this example we use H2O's MOJO models to execute locally in the Jet run time. We create an H2O Deep Learning model, train it with a well known sample data set [(Iris)](https://en.wikipedia.org/wiki/Iris_flower_data_set) commonly used to prove statistical classification, export the model to a MOJO and incorporate the MOJO into a Jet Pipeline.
