# install packages
#install.packages("caret")
#library("caret")

# CarEvaluationErrorRates
errorRates = 0
for (k in 1:5) {
    # handle data set
    errorRate = 0
    train.X = CarEvaluationSets[[as.numeric(k) * 2 - 1]][, 1:6]
    train.Direction = CarEvaluationSets[[as.numeric(k) * 2 - 1]][, 7]
    test.X = CarEvaluationSets[[as.numeric(k) * 2]][, 1:6]
    test.Direction = CarEvaluationSets[[as.numeric(k) * 2]][, 7]
    colnames(train.X) <- c("1", "2", "3", "4", "5", "6")
    colnames(test.X) <- c("1", "2", "3", "4", "5", "6")
    
    # do naive bayes
    model = train(train.X,
                  as.factor(train.Direction),
                  'nb',
                  trControl = trainControl(method = 'cv', number = 10))
    ans = table(predict(model$finalModel, test.X)$class,
                as.factor(test.Direction))
    
    # calclate error rate
    for (j in 1:ncol(ans))
        errorRate = errorRate + ans[j, j]
    errorRate = (length(test.Direction) - errorRate) / length(test.Direction)
    errorRates = errorRates + errorRate
}
CarEvaluationErrorRates = errorRates / 5
CarEvaluationErrorRates

# CreditApprovalErrorRates
errorRates = 0
for (k in 1:5) {
    # handle data set
    errorRate = 0
    train.X = CreditApprovalSets[[as.numeric(k) * 2 - 1]][, 1:15]
    train.Direction = CreditApprovalSets[[as.numeric(k) * 2 - 1]][, 16]
    test.X = CreditApprovalSets[[as.numeric(k) * 2]][, 1:15]
    test.Direction = CreditApprovalSets[[as.numeric(k) * 2]][, 16]
    colnames(train.X) <- c("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15")
    colnames(test.X) <- c("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15")
    
    # do naive bayes
    model = train(train.X,
                  as.factor(train.Direction),
                  'nb',
                  trControl = trainControl(method = 'cv', number = 10))
    ans = table(predict(model$finalModel, test.X)$class,
                as.factor(test.Direction))
    
    # calclate error rate
    for (j in 1:ncol(ans))
        errorRate = errorRate + ans[j, j]
    errorRate = (length(test.Direction) - errorRate) / length(test.Direction)
    errorRates = errorRates + errorRate
}
CreditApprovalErrorRates = errorRates / 5
CreditApprovalErrorRates
