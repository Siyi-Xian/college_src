install.packages("data.table")
library(data.table)
install.packages("curl")
mydata <-
    fread(
        "https://archive.ics.uci.edu/ml/machine-learning-databases/ionosphere/ionosphere.data"
    )
bigData <- data.frame(mydata[, 3:34])

sse = c()

for (i in 2:15) {
    kcluster = kmeans(bigData, i, algorithm = "Lloyd", iter.max = 30)
    
    sse[i - 1] = kcluster$tot.withinss
    
}

plot(
    2:15,
    sse,
    type = "b",
    pch = 20,
    frame = FALSE,
    xlab = "Number of clusters k",
    ylab = "Total within-clusters sum of squares"
)
