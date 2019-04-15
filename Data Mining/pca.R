##########################################################################################
# @Hasan Kurban: Toy PCA example, 2018
# B365 Intro to Data Analysis and Mining
##########################################################################################
#Example 1:
A = matrix(c(2,4,4,6),ncol=2)
#Example 2:
#A = matrix(c(1,2,-3,-2,6,8),ncol=2)
#Example 3:
#A = matrix(c(1,1,3,5),ncol=2) 
# 1. Center data
A.center <- scale(A, scale = FALSE)
A.center
# 2. Covariance matrix
A.center.cov <- cov(A.center)
A.center.cov
# 3. eigen value and eigen vectors
A.eigen <- eigen(A.center.cov)
A.eigen
#unlist A.eigen and store eigen vectors in a matrix
A.eigenVectors <- t(matrix(unlist(A.eigen[2]), ncol = 2, byrow = TRUE))
A.eigenVectors #
#4.  PCs: new data
PC <- A.center %*% A.eigenVectors
PC <- as.data.frame(PC)
names(PC) <- c("PC1", "PC2")
PC
##########################################################################################
#Finding PCs with SVD: singular value decomposition
SVDecomp <-svd(A.center)
U<- SVDecomp$u
D <- SVDecomp$d
t(U *D) #new data matrix
##########################################################################################
# Checking the results with prcomp()
pca <- prcomp(A) with 
#principal component
pca$x  #return new data 
plot(pca, type = "l")
screeplot(pca)
# Checking the results with princomp()
pca <- princomp(A)
pca$scores # return new data
########################################################################################
# PCA with prcomp() over Iris data set
?prcomp
data(iris)
pca.data <- iris[,-5] # remove class variable
pca <- prcomp(pca.data) #pca with prcomp()
summary(pca) # importance of principal components
#How many principal components
plot(pca, type = "l")
screeplot(pca)
#newdata
pca$x
pca$x[1:10,]
pca$x[,1:2]
pairs(pca$x[,1:2])
# principal components are linear combinations of the original variables 
pca$rotation  #rotation or loadings
biplot(pca,scale=0)
########################################################################################
#PCA with princomp()
#princomp()
?princomp
data(iris)
pca.data <- iris[,-5] # remove the class variable
pca <- princomp(pca.data) #pca with princomp()
loadings(pca) #loadings
pca$scores[1:5,] #data after transformation
dim(iris)
#adding class variable to transformed data that only contains first two PCs
reduced.iris <- data.frame(pca$scores[,1:2],Species=iris$Species)
dim(reduced.iris)
head(reduced.iris)