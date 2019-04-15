from DataProcess import DataProcess
import numpy as np
from sklearn.datasets.samples_generator import make_blobs

train_file = "train_0.csv"
test_file = "test_0.csv"

cluster_size = 20
para = 0.15


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


def distance(vector_0, vector_1):
    return np.sqrt(np.sum(np.square(vector_0 - vector_1)))


data = process_data()

X = data[0].data[0:499, 1:39]
Y = data[0].data[500:999, 1:39]
result = []
correct = 0
for i in range(len(Y)):
    dis = [0xfffffff] * cluster_size
    min = 0
    minValue = 0xffffff
    for j in range(cluster_size):
        dis[j] = distance(X[j, 1:37], Y[i, 1:37])
        if dis[j] < minValue:
            min = j
            minValue = dis[j]
    result.append(X[min, 0])
    correct += np.abs(X[min, 0] - int(Y[i, 0]))

print("\nCorrect Rate: " + str(1 - correct / len(Y)))
print(result)

import matplotlib.pyplot as plt
result = sorted(result)
plt.hist(result)
plt.show()