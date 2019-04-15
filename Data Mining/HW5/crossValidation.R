CarEvaluation =
    fread("https://archive.ics.uci.edu/ml/machine-learning-databases/car/car.data")
as.matrix(CarEvaluation)

CreditApproval = fread(
    "https://archive.ics.uci.edu/ml/machine-learning-databases/credit-screening/crx.data"
)
as.matrix(CreditApproval)

########################
# Data
########################
for (i in 1:nrow(CarEvaluation))
    if (CarEvaluation[i, 1] == "vhigh")
        CarEvaluation[i, 1] = 4
for (i in 1:nrow(CarEvaluation))
    if (CarEvaluation[i, 1] == "high")
        CarEvaluation[i, 1] = 3
for (i in 1:nrow(CarEvaluation))
    if (CarEvaluation[i, 1] == "med")
        CarEvaluation[i, 1] = 2
for (i in 1:nrow(CarEvaluation))
    if (CarEvaluation[i, 1] == "low")
        CarEvaluation[i, 1] = 1
CarEvaluation$V1 = as.numeric(CarEvaluation$V1)

for (i in 1:nrow(CarEvaluation))
    if (CarEvaluation[i, 2] == "vhigh")
        CarEvaluation[i, 2] = 4
for (i in 1:nrow(CarEvaluation))
    if (CarEvaluation[i, 2] == "high")
        CarEvaluation[i, 2] = 3
for (i in 1:nrow(CarEvaluation))
    if (CarEvaluation[i, 2] == "med")
        CarEvaluation[i, 2] = 2
for (i in 1:nrow(CarEvaluation))
    if (CarEvaluation[i, 2] == "low")
        CarEvaluation[i, 2] = 1
CarEvaluation$V2 = as.numeric(CarEvaluation$V2)

for (i in 1:nrow(CarEvaluation))
    if (CarEvaluation[i, 3] == "5more")
        CarEvaluation[i, 3] = 5
CarEvaluation$V3 = as.numeric(CarEvaluation$V3)

for (i in 1:nrow(CarEvaluation))
    if (CarEvaluation[i, 4] == "more")
        CarEvaluation[i, 4] = 5
CarEvaluation$V4 = as.numeric(CarEvaluation$V4)

for (i in 1:nrow(CarEvaluation))
    if (CarEvaluation[i, 5] == "big")
        CarEvaluation[i, 5] = 3
for (i in 1:nrow(CarEvaluation))
    if (CarEvaluation[i, 5] == "med")
        CarEvaluation[i, 5] = 2
for (i in 1:nrow(CarEvaluation))
    if (CarEvaluation[i, 5] == "small")
        CarEvaluation[i, 5] = 1
CarEvaluation$V5 = as.numeric(CarEvaluation$V5)

for (i in 1:nrow(CarEvaluation))
    if (CarEvaluation[i, 6] == "high")
        CarEvaluation[i, 6] = 3
for (i in 1:nrow(CarEvaluation))
    if (CarEvaluation[i, 6] == "med")
        CarEvaluation[i, 6] = 2
for (i in 1:nrow(CarEvaluation))
    if (CarEvaluation[i, 6] == "low")
        CarEvaluation[i, 6] = 1
CarEvaluation$V6 = as.numeric(CarEvaluation$V6)

for (i in 1:nrow(CarEvaluation))
    if (CarEvaluation[i, 7] == "vgood")
        CarEvaluation[i, 7] = 4
for (i in 1:nrow(CarEvaluation))
    if (CarEvaluation[i, 7] == "good")
        CarEvaluation[i, 7] = 3
for (i in 1:nrow(CarEvaluation))
    if (CarEvaluation[i, 7] == "acc")
        CarEvaluation[i, 7] = 2
for (i in 1:nrow(CarEvaluation))
    if (CarEvaluation[i, 7] == "unacc")
        CarEvaluation[i, 7] = 1
CarEvaluation$V7 = as.numeric(CarEvaluation$V7)



for (i in 1:nrow(CreditApproval))
    CreditApproval[as.numeric(i), 1] = as.numeric(charToRaw(as.character(substr(
        CreditApproval[i, 1], 1, 1
    ))))
CreditApproval$V1 = as.numeric(CreditApproval$V1)
for (i in 1:nrow(CreditApproval))
    CreditApproval[as.numeric(i), 4] = as.numeric(charToRaw(as.character(substr(
        CreditApproval[(i), 4], 1, 1
    ))))
CreditApproval$V4 = as.numeric(CreditApproval$V4)
for (i in 1:nrow(CreditApproval))
    CreditApproval[as.numeric(i), 5] = as.numeric(charToRaw(as.character(substr(
        CreditApproval[(i), 5], 1, 1
    ))))
CreditApproval$V5 = as.numeric(CreditApproval$V5)
for (i in 1:nrow(CreditApproval))
    CreditApproval[as.numeric(i), 6] = as.numeric(charToRaw(as.character(substr(
        CreditApproval[(i), 6], 1, 1
    ))))
CreditApproval$V6 = as.numeric(CreditApproval$V6)
for (i in 1:nrow(CreditApproval))
    CreditApproval[as.numeric(i), 7] = as.numeric(charToRaw(as.character(substr(
        CreditApproval[(i), 7], 1, 1
    ))))
CreditApproval$V7 = as.numeric(CreditApproval$V7)
for (i in 1:nrow(CreditApproval))
    CreditApproval[as.numeric(i), 9] = as.numeric(charToRaw(as.character(substr(
        CreditApproval[(i), 9], 1, 1
    ))))
CreditApproval$V9 = as.numeric(CreditApproval$V9)
for (i in 1:nrow(CreditApproval))
    CreditApproval[as.numeric(i), 10] = as.numeric(charToRaw(as.character(substr(
        CreditApproval[(i), 10], 1, 1
    ))))
CreditApproval$V10 = as.numeric(CreditApproval$V10)
for (i in 1:nrow(CreditApproval))
    CreditApproval[as.numeric(i), 12] = as.numeric(charToRaw(as.character(substr(
        CreditApproval[(i), 12], 1, 1
    ))))
CreditApproval$V12 = as.numeric(CreditApproval$V12)
for (i in 1:nrow(CreditApproval))
    CreditApproval[as.numeric(i), 13] = as.numeric(charToRaw(as.character(substr(
        CreditApproval[(i), 13], 1, 1
    ))))
CreditApproval$V13 = as.numeric(CreditApproval$V13)
CreditApproval$V14 = as.numeric(CreditApproval$V14)
for (i in 1:nrow(CreditApproval)) {
    if (CreditApproval[as.numeric(i), 16] == "+")
        CreditApproval[as.numeric(i), 16] = 1
    else
        CreditApproval[as.numeric(i), 16] = 2
}
CreditApproval$V16 = as.numeric(CreditApproval$V16)
for (i in 1:nrow(CreditApproval))
    if (1 == as.numeric(is.na(CreditApproval[i, 14])))
        CreditApproval[i, 14] = CreditApproval[as.numeric(i) - 1, 14]
CreditApproval$V2 = as.numeric(CreditApproval$V2)
for (i in 1:nrow(CreditApproval))
    if (1 == as.numeric(is.na(CreditApproval[i, 2])))
        CreditApproval[i, 2] = CreditApproval[as.numeric(i) - 1, 2]


sum(is.na(CarEvaluation))
sum(is.na(CreditApproval))

########################
# F-Fold Cross Validation
########################

CarEvaluationSet = sample(1:5, nrow(CarEvaluation), replace = T)
CreditApprovalSet = sample(1:5, nrow(CreditApproval), replace = T)

getSet = function (set, tempData) {
    sets = list()
    for (i in 1:5) {
        tempTrain = matrix(
            nrow = length(set) - sum(set == as.numeric(i)),
            ncol = ncol(tempData),
            byrow = T
        )
        ttr = 1
        tempTest = matrix(
            nrow = sum(set == as.numeric(i)),
            ncol = ncol(tempData),
            byrow = T
        )
        tte = 1
        for (j in 1:length(set)) {
            if (set[j] == as.numeric(i)) {
                tempTest[tte, 1:ncol(tempData)] = as.numeric(tempData[j, ])
                tte = tte + 1
            }
            else {
                tempTrain[ttr, 1:ncol(tempData)] = as.numeric(tempData[j, ])
                ttr = ttr + 1
            }
        }
        sets = c(sets, list(tempTrain, tempTest))
    }
    return(sets)
}

CarEvaluationSets = getSet(CarEvaluationSet, CarEvaluation)
CreditApprovalSets = getSet(CreditApprovalSet, CreditApproval)

error1 = c()
for (i in 1:100) {
    errorRate = 0
    for (k in 1:5) {
        ans = kNearestNeighbors(CarEvaluationSets[[as.numeric(k) * 2 - 1]],
                                CarEvaluationSets[[as.numeric(k) * 2]],
                                Euclidean,
                                as.numeric(i) + 1)
        for (j in 1:length(ans))
            if (ans[j] != CarEvaluationSets[[as.numeric(k) * 2]][j, ncol(CarEvaluation)])
                errorRate = errorRate + 1
    }
    error1 = c(error1, errorRate / (length(ans) * 5))
}
print(error1)
plot(error, main = "Car Evaluation Euclidean", xlab = "K-value", ylab = "Error Rate", type = "l")
error2 = c()
for (i in 1:100) {
    errorRate = 0
    for (k in 1:5) {
        ans = kNearestNeighbors(CarEvaluationSets[[as.numeric(k) * 2 - 1]],
                                CarEvaluationSets[[as.numeric(k) * 2]],
                                Cosine,
                                as.numeric(i) + 1)
        for (j in 1:length(ans))
            if (ans[j] != CarEvaluationSets[[as.numeric(k) * 2]][j, ncol(CarEvaluation)])
                errorRate = errorRate + 1
    }
    error2 = c(error2, errorRate / (length(ans) * 5))
}
print(error2)
plot(error2, main = "Car Evaluation Cosine", xlab = "K-value", ylab = "Error Rate", type = "l")

error3 = c()
for (i in 1:100) {
    errorRate = 0
    for (k in 1:5) {
        ans = kNearestNeighbors(CreditApprovalSets[[as.numeric(k) * 2 - 1]],
                                CreditApprovalSets[[as.numeric(k) * 2]],
                                Euclidean,
                                as.numeric(i) + 1)
        for (j in 1:length(ans))
            if (ans[j] != CreditApprovalSets[[as.numeric(k) * 2]][j, ncol(CreditApproval)])
                errorRate = errorRate + 1
    }
    error3 = c(error3, errorRate / (length(ans) * 5))
}
print(error3)
plot(error3, main = "Credit Approval Euclidean", xlab = "K-value", ylab = "Error Rate", type = "l")
error4 = c()
for (i in 1:100) {
    errorRate = 0
    for (k in 1:5) {
        ans = kNearestNeighbors(CreditApprovalSets[[as.numeric(k) * 2 - 1]],
                                CreditApprovalSets[[as.numeric(k) * 2]],
                                Cosine,
                                as.numeric(i) + 1)
        for (j in 1:length(ans))
            if (ans[j] != CreditApprovalSets[[as.numeric(k) * 2]][j, ncol(CreditApproval)])
                errorRate = errorRate + 1
    }
    error4 = c(error4, errorRate / (length(ans) * 5))
}
print(error4)
plot(error4, main = "Credit Approval Cosine", xlab = "K-value", ylab = "Error Rate", type = "l")
