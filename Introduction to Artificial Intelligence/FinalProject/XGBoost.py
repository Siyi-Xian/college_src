import numpy as np
import matplotlib.pyplot as plt
import xgboost as xgb
from DataProcess import DataProcess

# train_file = "train.csv"
# test_file = "test.csv"

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

# XGBoost
label = data[0].data[0:, 1]
dtrain = data[0].data[0:, 2:39]
# dtest = data[1].data[0:, 1:38]
dtest = data[0].data[0:, 2:39]

param = {
    'booster': 'gbtree',
    'objective': 'multi:softmax',
    'num_class': 10,
    'gamma': 0.1,
    'max_depth': 30,
    'lambda': 450,
    'subsample': 0.4,
    'colsample_bytree': 0.8,
    'silent': 1,
    'eta': 0.005,
    'seed': 710,
    'nthread': 4}

plst = list(param.items())

# Using 1000 rows for early stopping.
offset = 700
num_rounds = 500
xgtest = xgb.DMatrix(dtest)

xgtrain = xgb.DMatrix(dtrain[:offset, :], label=label[:offset])
xgval = xgb.DMatrix(dtrain[offset:, :], label=label[offset:])

watchlist = [(xgtrain, 'train'), (xgval, 'val')]

model = xgb.train(plst, xgtrain, num_rounds, watchlist, early_stopping_rounds=100)

model.save_model('./xgb.model')

preds = model.predict(xgtest, ntree_limit=model.best_iteration)

result = np.c_[range(1, len(dtest) + 1), preds]
print(result)

correct = 0
for i in range(len(result)):
    if result[i][1] == label[i]:
        correct += 1
print("\nCorrect rate: " + str(correct / len(label)))
