data = matrix(c(1.00,0.10,0.41,0.55,0.35,0.10,1.00,0.64,0.47,0.98,0.41,0.64,1.00,0.44,0.85,0.55,0.47,0.44,1.00,0.76,0.35,0.98,0.85,0.76,1.00), ncol = 5)
data = 1 - data
data = as.dist(data)
sh = hclust(data)
plot(sh, main = "Complete link", xlab = "")
