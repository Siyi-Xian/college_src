install.packages("data.table")
library(data.table)
install.packages("curl")
mydata <- fread("https://archive.ics.uci.edu/ml/machine-learning-databases/breast-cancer-wisconsin/breast-cancer-wisconsin.data")
head(mydata)

length(mydata$V1)

sum(mydata=="?")

sum(mydata$V11 == 4) # malignant
sum(mydata$V11 == 2) # benign

meanV7 <- mean(as.numeric(mydata[mydata$V7 != "?",]$V7))
mydata[which(mydata$V7 == "?"),"V7"] <- meanV7

hist(mydata$V2, 
     main = "Histogram of Clump Thickness", 
     xlab = "Clump Thickness")
hist(mydata$V3, 
     main = "Histogram of Uniformity of Cell Size", 
     xlab = "Uniformity of Cell Size")
hist(mydata$V4, 
     main = "Histogram of Uniformity of Cell Shape", 
     xlab = "Uniformity of Cell Shape")
hist(mydata$V5, 
     main = "Histogram of Marginal Adhesion",
     xlab = "Marginal Adhesion")
hist(mydata$V6, 
     main = "Histogram of Single Epithelial Cell Size", 
     xlab = "Single Epithelial Cell Size")
hist(as.numeric(mydata$V7), 
     main = "Histogram of Bare Nuclei", 
     xlab = "Bare Nuclei")
hist(mydata$V8, 
     main = "Histogram of Bland Chromatin", 
     xlab = "Bland Chromatin")
hist(mydata$V9, 
     main = "Histogram of Normal Nucleoli", 
     xlab = "Normal Nucleoli")
hist(mydata$V10, 
     main = "Histogram of Mitoses", 
     xlab = "Mitoses")
hist(mydata$V11, 
     main = "Histogram of Class", 
     xlab = "Class")

