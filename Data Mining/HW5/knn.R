# Load knn package
library(class)

# Calclus Eulidean Distance
Euclidean = function (p1, p2) {
    ans = 0
    for (i in 1:length(p1))
        ans = ans + (p1[i] - p2[i]) ^ 2
    return (sqrt(ans))
}

# Calclus Cosine Similarity
Cosine = function (p1, p2) {
    f1 = 0
    f2 = 0
    f3 = 0
    for (i in 1:length(p1))
        f1 = f1 + (p1[i] * p2[i])
    for (i in 1:length(p1))
        f2 = f2 + (p1[i] * p1[i])
    for (i in 1:length(p1))
        f3 = f3 + (p2[i] * p2[i])
    return(f1/(sqrt(f2) * sqrt(f3)))
}

# knn
kNearestNeighbors = function (trian, test, dis, k) {
    # initials index and result
    i = 0
    ans = c()
    
    # get each test point
    for (i in 1:nrow(test)) {
        # handle test point and all distances data
        testPoint = as.numeric(test[i, 1:ncol(test) - 1])
        distances = matrix(nrow = nrow(trian), ncol = 2, byrow = TRUE)
        # calculate distance to every trianing points
        for (j in 1:nrow(trian)) {
            trianPoint = as.numeric(trian[j, 1:ncol(trian) - 1])
            distances[j, 1] = dis(testPoint, trianPoint)
            distances[j, 2] = trian[j, ncol(trian)]
        }
        # sort data
        as.data.frame(distances)
        distances = distances[order(distances[,1]),]
        distances = as.matrix(distances[1:k,])
        # voting
        voting = matrix(c(0,0,0,0,0), nrow = 5, ncol = 1)
        # calculate class data
        for (j in 1:k) {
            if (distances[j,2] == 1)
                voting[1] = voting[1] + 1
            else if (distances[j,2] == 2)
                voting[2] = voting[2] + 1
            else if (distances[j,2] == 3)
                voting[3] = voting[3] + 1
            else if (distances[j,2] == 4)
                voting[4] = voting[4] + 1
            else
                voting[5] = voting[5] + 1
            
        }
        # choose class
        maxIndex = 0
        max = 0
        for (j in 1:5) {
            if (voting[j] > max) {
                max = voting[j]
                maxIndex = j
            }
        }
        # add result
        ans = c(ans, maxIndex)
    }
    return(ans)
}
