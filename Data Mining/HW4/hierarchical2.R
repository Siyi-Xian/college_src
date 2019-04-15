# retrive data
install.packages("data.table")
library(data.table)
install.packages("curl")
mydata <-
    fread(
        "https://archive.ics.uci.edu/ml/machine-learning-databases/ionosphere/ionosphere.data"
    )

# random chose 50 points
initial <- sample(1:nrow(mydata), 50, replace = F)
bigData <- matrix(nrow = 50, ncol = 34)
error_rate = c()
for (i in 1:50) {
    bigData[i, ] = as.numeric(mydata[initial[i], 1:34])
    error_rate = c(error_rate, mydata[initial[i], 35])
}

# creat original dendrogram
hc = hclust(dist(bigData))
plot(hc, xlab = "", ylab = "", sub = "")

# cut tree with two clusters
cluster2 = cutree(hc, 2)

# calculate error rate
g1 = 0
g2 = 0
b1 = 0
b2 = 0
for (i in 1:50) {
    if (cluster2[i] == 1)
        if (error_rate[i] == "g")
            g1 = g1 + 1
        else
            b1 = b1 + 1
        else
            if (error_rate[i] == "g")
                g2 = g2 + 1
            else
                b2 = b2 + 1
}

{
    if (g1 > b1)
        error_rate1 = b1 / (g1 + b1)
    else
        error_rate1 = g1 / (g1 + b1)
}
{
    if (g2 > b2)
        error_rate2 = b2 / (g2 + b2)
    else
        error_rate2 = g2 / (g2 + b2)
}
error_rate1
error_rate2

# calculate PCA
hpca = prcomp(bigData)

# Calculate variance
hvar = hpca$sdev^2/sum(hpca$sdev^2)
# determain components
i = 1
th = 0
while (th < .9) {
    th = th + hvar[i]
    i = i + 1
}
# minus one becasue when adding the last elments, the threshold will go out of bound!
i = i - 1

# creat dendrogram which keeps 90% variance
hc = hclust(dist(bigData[,1:i]))
plot(hc, xlab = "", ylab = "", sub = "")

# cut tree with two clusters
cluster2 = cutree(hc, 2)

# calculate error rate
g1 = 0
g2 = 0
b1 = 0
b2 = 0
for (i in 1:50) {
    if (cluster2[i] == 1)
        if (error_rate[i] == "g")
            g1 = g1 + 1
        else
            b1 = b1 + 1
        else
            if (error_rate[i] == "g")
                g2 = g2 + 1
            else
                b2 = b2 + 1
}

{
    if (g1 > b1)
        error_rate1 = b1 / (g1 + b1)
    else
        error_rate1 = g1 / (g1 + b1)
}
{
    if (g2 > b2)
        error_rate2 = b2 / (g2 + b2)
    else
        error_rate2 = g2 / (g2 + b2)
}
error_rate1
error_rate2
