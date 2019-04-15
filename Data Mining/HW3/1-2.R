install.packages("data.table")
library(data.table)
install.packages("curl")
mydata <-
    fread(
        "https://archive.ics.uci.edu/ml/machine-learning-databases/ionosphere/ionosphere.data"
    )

dis <- function (p1, p2) {
    d <- 0
    for (i in 1:length(p1))
        d <- d + (p1[i] - p2[i]) * (p1[i] - p2[i])
    return (sqrt(d))
}

# stopping criteria
thresHold <- function(c1, c2, t, k, dataSet) {
    ans <- 0
    for (i in 1:k)
        ans <- ans + dis(c1[i, ], c2[i, ])
    ans <- ans / k
    return (ans < t)
}

k_means <- function (dataSet, dis, k, t) {
    # initial central point of each clusters
    len <- ncol(dataSet)
    cv <- matrix(nrow = k, ncol = len)
    cB <- matrix(nrow = k, ncol = nrow(dataSet) + 5)
    c_copy <- cv
    initialC <- sample(1:nrow(dataSet), k, replace = F)
    for (i in 1:k) {
        for (j in 1:len)
            cv[i, j] = dataSet[initialC[i], j]
        cB[i, 1] <- 2
    }
    
    # maintaining k centroids
    i <- 0
    while (i >= 0) {
        c_copy <- cv
        
        cB <- matrix(nrow = k, ncol = nrow(dataSet) + 5)
        for (j in 1:k)
            cB[j, 1] <- 2
        
        lo <- 0
        for (j in 1:nrow(dataSet)) {
            lo <- lo + 1
            minDis <- 0xfffffff
            n <- -1
            
            for (kk in 1:k) {
                p1 <- as.double(dataSet[j, ])
                p2 <- cv[kk, ]
                if (dis(p1, p2) < minDis) { # deciding ties
                    n <- kk
                    minDis <- dis(p1, p2)
                }
            }
            cB[n, cB[n, 1]] <- lo
            cB[n, 1] <- cB[n, 1] + 1
        }
        
        for (j in 1:k) {
            co <- c()
            for (kk in 1:len) {
                sum <- 0
                num <- 0
                l <- 2
                while (!is.na(cB[j, l])) {
                    xLoc <- cB[j, l]
                    yLoc <- kk
                    val <- as.double(dataSet[xLoc, yLoc])
                    sum <- sum + val
                    num <- num + 1
                    l <- l + 1
                }
                co <- c(co, sum / num)
            }
            cv[j,] <- co
        }
        
        # stopping criteria
        if (i > 0 && thresHold(c_copy, cv, t, k, dataSet))
            break
        i <- i + 1
    }
    
    return (list(v = cv, B = cB))
}

bigData <- data.frame(mydata[, 1:34])
k_means (bigData, dis, 5, 10)
tinyData <-
    matrix(c(2, 1, 22, 42, 15, 5, 5, 55, 12, 16),
           nrow = 5,
           ncol = 2)
tinyData <- data.frame(tinyData)
k_means (tinyData, dis, 2, 10)

