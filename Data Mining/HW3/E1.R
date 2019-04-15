install.packages("data.table")
library(data.table)
install.packages("curl")
mydata <-
    fread(
        "https://archive.ics.uci.edu/ml/machine-learning-databases/breast-cancer-wisconsin/breast-cancer-wisconsin.data"
    )
m <- mean(as.integer(mydata$V7 != '?'))
for(i in 1:nrow(mydata))
    if (mydata[i, 7] == '?')
        mydata[i, 7] <- m

dis <- function (p1, p2) {
    d <- 0
    for (i in 1:length(p1))
        d <- d + (p1[i] - p2[i]) * (p1[i] - p2[i])
    return (sqrt(d))
}

# stopping criteria
thresHold <- function(c1, c2, t, k) {
    ans <- 0
    for (i in 1:k)
        ans <- ans + dis(as.double(c1[i,]), as.double(c2[i,]))
    ans <- ans / k
    return (ans < t)
}

SSE <- function(c, dataSet, k) {
    ans <- 0
    for (i in 1:k) {
        cB <- c$B[i, 2:(as.integer(c$B[i, 1]) - 1)]
        cv <- as.double(c$v[i, ])
        for (j in cB)
             ans <- ans + dis(as.double(dataSet[j, ]), cv)**2
    }
    #print(ans)
    return(ans)
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
        cb_copy <- cB
        
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
                p2 <- as.double(cv[kk,])
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
        
        #print(list(v = cv, B = cB[,1:10]))
        
        # stopping criteria
        c1 <- list(v = cv, B = cB)
        c2 <- list(v = c_copy, B = cb_copy)
        if (i > 2 && SSE(c1, dataSet, k) == SSE(c2, dataSet, k))
            break
        i <- i + 1
    }
    
    return (SSE(list(v = cv, B = cB), dataSet, k))
}

bigData <- data.frame(mydata[, 2:11])
error <- matrix(nrow = 20, ncol = 5)
kmeans(bigData, 5)$tot.withinss

for (k in 5:5) {
    print (k)
    for (i in 13:18) {
        c <- k_means (bigData, dis, as.integer(k), 100)
        #c <- kmeans(bigData)
        print(c)
        error[i, k] <- as.double(c)
    }
}
boxplot(error, main = "SSE Analysis", xlab = "# of Clusters", ylab = "SSE")

