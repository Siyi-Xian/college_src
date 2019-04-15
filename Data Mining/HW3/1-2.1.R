install.packages("data.table")
library(data.table)
install.packages("curl")
head(mydata)

nrow(mydata)

sum(mydata == "")

v1 <- c(sum(mydata$V1 == "0"), sum(mydata$V1 == "1"))
barplot(
    v1,
    xlab = "Number",
    ylab = "Frequency",
    main = "V1",
    names.arg = c(0, 1)
)

v2 <- c(sum(mydata$V2 == "0"), sum(mydata$V2 == "1"))
barplot(
    v2,
    xlab = "Numbe
    r",
    ylab = "Frequency",
    main = "V2",
    names.arg = c(0, 1)
)

v35 <- c(sum(mydata$V35 == "g"), sum(mydata$V35 == "b"))
barplot(
    v35,
    xlab = "Good \ Bad",
    ylab = "Frequency",
    main = "V35",
    names.arg = c("Good", "Bad")
)

chageToColor <- function (x) {
    for (i in 1:nrow(mydata)) {
        if (x[i] == "g") {
            x[i] <- "blue"
        } else {
            x[i] <- "red"
        }
    }
    return (x)
}
v35Color <- chageToColor(mydata$V35)
plot(
    mydata$V22,
    mydata$V20,
    xlab = "V22",
    ylab = "V20",
    main = "V22 & V20",
    col = v35Color
)
plot(
    mydata$V1,
    mydata$V2,
    xlab = "V1",
    ylab = "V2",
    main = "V1 & V2",
    col = v35Color
)
