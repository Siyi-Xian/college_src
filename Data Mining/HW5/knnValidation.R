library(class)
error = c()
for (i in 1:100) {
    errorRate = 0
    for (k in 1:5) {
        train.X = CarEvaluationSets[[as.numeric(k) * 2 - 1]][, 1:6]
        train.Direction = CarEvaluationSets[[as.numeric(k) * 2 - 1]][, 7]
        test.X = CarEvaluationSets[[as.numeric(k) * 2]][, 1:6]
        knn.pred = knn(train.X, test.X, train.Direction, k = as.numeric(i) + 1)
        for (j in 1:length(knn.pred))
            if (knn.pred[j] != CarEvaluationSets[[as.numeric(k) * 2]][j, ncol(CarEvaluation)])
                errorRate = errorRate + 1
    }
    error = c(error, errorRate / (length(knn.pred) * 5))
    print(errorRate / (length(knn.pred) * 5))
}
plot(error, main = "Car Evaluation - R Package", xlab = "K-value", ylab = "Error Rate", type = "l")

error = c()
for (i in 1:100) {
    errorRate = 0
    for (k in 1:5) {
        train.X = CreditApprovalSets[[as.numeric(k) * 2 - 1]][, 1:15]
        train.Direction = CreditApprovalSets[[as.numeric(k) * 2 - 1]][, 16]
        test.X = CreditApprovalSets[[as.numeric(k) * 2]][, 1:15]
        knn.pred = knn(train.X, test.X, train.Direction, k = as.numeric(i) + 1)
        for (j in 1:length(knn.pred))
            if (knn.pred[j] != CreditApprovalSets[[as.numeric(k) * 2]][j, ncol(CreditApproval)])
                errorRate = errorRate + 1
    }
    error = c(error, errorRate / (length(knn.pred) * 5))
    print(errorRate / (length(knn.pred) * 5))
}
plot(error, main = "Credit Approval - R Package", xlab = "K-value", ylab = "Error Rate", type = "l")
