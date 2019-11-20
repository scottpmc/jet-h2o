import h2o
h2o.init()

#import public data set
url = "http://h2o-public-test-data.s3.amazonaws.com/smalldata/iris/iris_wheader.csv"
iris = h2o.import_file(url)

#split data into train and test frames and print summary
train, test = iris.split_frame([0.8])
train.summary()

print ("Rows for training: ", train.nrows)
print ("Rows for testing : ", test.nrows)

from h2o.estimators.deeplearning import H2ODeepLearningEstimator

mDL = H2ODeepLearningEstimator()
mDL.train(["sepal_len", "sepal_wid", "petal_len", "petal_wid"], "class", train)
print mDL

p = mDL.predict(test)
print p

mDL.model_performance(test)

modelfile = mDL.download_mojo(path="~/ML/", get_genmodel_jar=True)
print("Model saved to " + modelfile)