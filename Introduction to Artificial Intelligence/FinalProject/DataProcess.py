import numpy as np
import matplotlib.pyplot as plt
import xgboost as xgb
from decision_tree_factory import DecisionTreeFactory

train_file = "train_0.csv"
test_file = "test_0.csv"


class DataProcess:
    def __init__(self, file):
        self.data = self.process_csv(file)
        self.size = self.data.shape
        self.label = self.process_label(file)

    # Read data from csv
    def process_csv(self, file):
        csv_file = np.loadtxt(file, dtype=np.str, delimiter=',')
        return csv_file[1:, 0:].astype(np.float)

    # Read label form csv
    def process_label(self, file):
        csv_file = np.loadtxt(file, dtype=np.str, delimiter=',')
        return csv_file[0, 0:]

    # Check if there is some missing data
    def has_missing(self):
        for row in self.data:
            for column in row:
                if column == -1.0:
                    return True
        return False

    # Process missing data
    def handle_missing_data(self):
        total_correct = 0
        for i in range(self.size[1]):
            amount = float(self.size[0])
            sum = 0.0
            for point in self.data[0:, i]:
                if point == -1.0:
                    amount -= 1
                else:
                    sum += point
            if sum != 0:
                avg = amount / sum
            else:
                avg = 0
            for j in range(self.size[0]):
                if self.data[j, i] == -1.0:
                    self.data[j, i] = avg
                    total_correct += 1
        print("Processed " + str(total_correct) + " missing data.")

    # Boxplot data check noise
    def plot_data(self):
        for data_colunm in self.data.transpose():
            plt.boxplot(data_colunm)
            plt.show()

    # Normalize data to keep them in range of [0, 1]
    def normalize_data(self):
        for i in range(1, self.size[1]):
            max_num = max(self.data[0:, i])
            for j in range(self.size[0]):
                self.data[j, i] /= max_num


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


# data = process_data()
