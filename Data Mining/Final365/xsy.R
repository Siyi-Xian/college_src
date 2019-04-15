library(class)

mytraindata <-
    read.csv(file = "/u/siyixian/365/train.csv")
mytraindata <- as.matrix(mytraindata)
#for (i in 3:39)
#    mytraindata[, i] = mytraindata[, i]/max(mytraindata[, i])
mytestdata <-
    read.csv(file = "/u/siyixian/365/test.csv")
mytestdata <- as.matrix(mytestdata)
#for (i in 2:38)
#    mytestdata[, i] = mytestdata[, i]/max(mytestdata[, i])

pca = prcomp(mytraindata[, 2:59])
var = pca$sdev^2/sum(pca$sdev^2)
for (i in 2:length(var))
    var[i] = var[i] + var[i - 1]
plot(var, type = "l")

ans = c()
for (i in 2:100) {
   kmean = kmeans(pca$x[, 1:6], i, algorithm = "Lloyd", iter.max = i * 10)
   error = rep(0, times = i)
   numbe = rep(0, times = i)
   for (j in 1:length(kmean$cluster)) {
       error[kmean$cluster[j]] = error[kmean$cluster[j]] + mytraindata[j, 2]
       numbe[kmean$cluster[j]] = numbe[kmean$cluster[j]] + 1
   }
   for (j in 1:i)
       error[j] = error[j] / numbe[j]
   print(error)
   ans = c(ans, var(error))
}
plot(ans, type = "l")

kmeans_pred <- function(traindata, testdata) {
    k = 40
    
    kmean = kmeans(traindata, k, algorithm = "Lloyd", iter.max = 1000)
    error = rep(0, times = k)
    numbe = rep(0, times = k)
    for (j in 1:length(kmean$cluster)) {
        error[kmean$cluster[j]] = error[kmean$cluster[j]] + traindata[j, 1]
        numbe[kmean$cluster[j]] = numbe[kmean$cluster[j]] + 1
    }
    for (j in 1:k)
        error[j] = error[j] / numbe[j]
    
    #knn.pred = knn(mytraindata[, 3:59], mytestdata[1:100,2:58], kmean$cluster, 1)
    
    dis = function (p1, p2) {
        ans = 0
        for (i in 1:length(p1))
            ans = ans + (p1[i] - p2[i]) ^ 2
        return (sqrt(ans))
    }
    
    predict = c()
    #for (i in 1:nrow(mytestdata)) {
    for (i in 1:nrow(testdata)) {
        weightMin = 0xffffff
        index = 0
        for (j in 1:nrow(kmean$centers)) {
            diste = dis(as.double(kmean$centers[j, -1]), as.double(testdata[i,]))
            if (diste < weightMin) {
                weightMin = diste
                index = j
            }
        }
        print(i)
        print(error[index])
        predict = c(predict, error[index])
    }
    return(predict)
}

write.table(predict, file = "/u/siyixian/365/myresult_2.csv",row.names = F)


ans <-
    read.csv(file = "/Users/xiansiyi/Downloads/ans.csv")
as.matrix(ans)
plot(ans[[1]], type = "l", xlab = "CLusters Amount", ylab = "Variance", main = "PCA")
