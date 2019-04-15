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
        ans <- ans + dis(c1[i,], c2[i,])
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
                p1 <- as.double(dataSet[j,])
                p2 <- cv[kk,]
                if (dis(p1, p2) < minDis) {
                    # deciding ties
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
            cv[j, ] <- co
        }
        
        # stopping criteria
        if (i > 0 && thresHold(c_copy, cv, t, k, dataSet))
            break
        i <- i + 1
    }
    
    return (list(v = cv, B = cB))
}

totalErrorRate <- function(c, dataSet, k) {
    ans <- 0
    for (i in 1:k) {
        cB <- c$B[i, 2:(as.integer(c$B[i, 1]) - 1)]
        g <- 0
        b <- 0
        for (j in cB) {
            if (dataSet[j, 35] == "g")
                g <- g + 1
            else
                b <- b + 1
        }
        if (g > b)
            ans <- ans + b / (b + g)
    }
    print(ans)
    return(ans)
}

bigData <- data.frame(mydata[, 3:34])
error <- matrix(nrow = 20, ncol = 5)
kmeans(bigData, 5)$cluster

for (k in 2:5) {
    print (k)
    for (i in 1:20) {
        c <- k_means (bigData, dis, as.integer(k), 100)
        #c <- kmeans(bigData)
        error[i, k] <- totalErrorRate(c, mydata, as.integer(k))
    }
}

boxplot(error)

