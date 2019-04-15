library(data.table)
crx <- fread("https://archive.ics.uci.edu/ml/machine-learning-databases/credit-screening/crx.data")
car <- fread("https://archive.ics.uci.edu/ml/machine-learning-databases/car/car.data")

#initial data
##car
car$V1 = as.factor(car$V1)
levels(car$V1) <- c(3,1,2,4)
car$V2 = as.factor(car$V2)
levels(car$V2) <- c(3,1,2,4)
car$V3 = as.factor(car$V3)
levels(car$V3) <- c(2,3,4,5)
car$V4 = as.factor(car$V4)
levels(car$V4) <- c(2,4,5)
car$V5 = as.factor(car$V5)
levels(car$V5) <- c(3,2,1)
car$V6 = as.factor(car$V6)
levels(car$V6) <- c(3,1,2)
car$V7 = as.factor(car$V7)

##crx
crx$V1 = as.factor(crx$V1)
levels(crx$V1) <- c(0,1,2)
crx$V2[crx$V2 == "?"] = 40#mean
crx$V2 <- as.numeric(crx$V2)/10
crx$V3 <- as.numeric(crx$V3)/10
crx$V4 = as.factor(crx$V4)
levels(crx$V4) <- c(0,1,2,3)
crx$V5 = as.factor(crx$V5)
levels(crx$V5) <- c(0,1,2,3)
crx$V6 = as.factor(crx$V6)
levels(crx$V6) <- c(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14)
crx$V6 <- as.numeric(crx$V6)/10
crx$V7 = as.factor(crx$V7)
levels(crx$V7) <- c(0,1,2,3,4,5,6,7,8,9)
crx$V7 <- as.numeric(crx$V7)/10
crx$V8 <- as.numeric(crx$V8)/10
crx$V9 = as.factor(crx$V9)
levels(crx$V9) <- c(1,0)
crx$V10 = as.factor(crx$V10)
levels(crx$V10) <- c(1,0)
crx$V11 <- as.numeric(crx$V11)/10
crx$V12 = as.factor(crx$V12)
levels(crx$V12) <- c(1,0)
crx$V13 = as.factor(crx$V13)
levels(crx$V13) <- c(1,2,3)
crx$V14[crx$V14 == "?"] = 0
crx$V14 <- as.numeric(crx$V14)/100
crx$V15 <- as.numeric(crx$V15)/1000
crx$V16 = as.factor(crx$V16)





testDatacrx=list()
trainDatacrx=list()
testDatacar=list()
trainDatacar=list()



for(i in 1:5){
  #Randomly shuffle the data to itself
  crx<-crx[sample(nrow(crx)),]
  car<-car[sample(nrow(car)),]
  
  #Create 5 equally size folds
  foldscrx <- cut(seq(1,nrow(crx)),breaks=5,labels=FALSE)
  foldscar <- cut(seq(1,nrow(car)),breaks=5,labels=FALSE)
  
  #Segement your data by fold using the which() function 
  Indexescrx <- which(foldscrx==5,arr.ind=TRUE)
  Indexescar <- which(foldscar==5,arr.ind=TRUE)

  testDatacrx <- append(list(crx[Indexescrx, ]),testDatacrx)
  testDatacar <- append(list(car[Indexescar, ]),testDatacar)
  trainDatacrx <- append(list(crx[-Indexescrx, ]),trainDatacrx)
  trainDatacar <- append(list(car[-Indexescar, ]),trainDatacar)
}

KCrossValidation <- function(dataset,k){
  
  #initial data
  testData=list()
  trainData=list()
  
  
  #Perform 5 fold cross validation
  for(i in 1:k){
    #Randomly shuffle the data to itself
    dataset<-dataset[sample(nrow(dataset)),]
    
    #Create 5 equally size folds
    folds <- cut(seq(1,nrow(dataset)),breaks=5,labels=FALSE)
    
    Indexes <- which(folds==5,arr.ind=TRUE)
    testData <- append(list(dataset[Indexes, ]),testData)
    trainData <- append(list(dataset[-Indexes, ]),trainData)
    
  }
}

#print(testDatacrx[[1]])
#print(trainDatacrx[[1]])
