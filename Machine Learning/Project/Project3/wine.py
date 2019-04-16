import numpy as np
from sklearn.neural_network import MLPClassifier


class Wine:

    def __init__(self):
        # import and load data
        url = 'data/wine.data'
        data = np.loadtxt(url, delimiter=",")

        # divide data into X and y
        self.X = data[:, 1:14]
        self.y = data[:, 0].astype(int)
        # scale all X
        for i in range(13):
            self.X[:, i] = self.X[:, i] / max(self.X[:, i])

        # initializate classifier
        self.mlp = MLPClassifier(solver='lbfgs',
                                 activation='logistic',
                                 hidden_layer_sizes=(52),
                                 learning_rate_init=1e-5,
                                 max_iter=1e+4,
                                 verbose=True)
        # train data
        self.mlp.fit(self.X, self.y)

    def predict(self, input):
        # predict using test data
        pred = self.mlp.predict(np.reshape(np.array(input), (-1, 13)))
        return pred[0]

    def retrain(self, input, target):
        self.X = np.reshape(np.append(self.X, np.array(input)), (-1, 13))
        self.y = np.reshape(np.append(self.y, np.array(target)), (-1, 1))
        # self.mlp.partial_fit(, )
        print(self.X.shape)
        print(self.y.shape)
        self.mlp.fit(self.X, self.y)