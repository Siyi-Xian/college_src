testData = read.csv("/Users/xiansiyi/Desktop/Final365/test.csv")
trainData = read.csv("/Users/xiansiyi/Desktop/Final365/train.csv")
data.frame(data)

tinyData = as.matrix(trainData[,1])
sum(tinyData == "1")

install.packages("ggplot2")
library(ggplot2)

km = kmeans(tinyData, 50)
km$centers
library(class)
knn(trainData, testData, 1)

km = kmeans(trainData[,3:58], 10)
km$centers
