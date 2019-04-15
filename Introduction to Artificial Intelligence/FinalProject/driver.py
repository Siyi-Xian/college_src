import numpy as np
import matplotlib.pyplot as plt
import xgboost as xgb
from decision_tree_factory import DecisionTreeFactory
from DataProcess import DataProcess

train_file = "train_0.csv"
test_file = "test_0.csv"


def process_data():
    print("Training Data:")
    train_data = DataProcess(train_file)
    print("Has missing data? " + str(train_data.has_missing()))
    train_data.handle_missing_data()
    print("Has missing data? " + str(train_data.has_missing()))
    # train_data.plot_data()
    # train_data.normalize_data()

    print("\nTest Data:")
    test_data = DataProcess(test_file)
    print("Has missing data? " + str(test_data.has_missing()))
    test_data.handle_missing_data()
    print("Has missing data? " + str(test_data.has_missing()))
    # test_data.plot_data()
    # test_data.normalize_data()

    return train_data, test_data


data = process_data()
print()

# Decision Tree
# load given dataset

# initialize our decision tree builder
factory = DecisionTreeFactory(data[0].data[0:, 2:39], data[0].data[0:, 1])

# make the tree
tree = factory.build_tree()

# run on test set
results = np.zeros(data[1].size)
for i in range(data[1].size[0]):
    results[i] = tree.classify(data[1].data[i, :])
count = 0
