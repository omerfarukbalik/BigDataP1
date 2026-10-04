library(lattice)

values <- rep(0, 100)

function(value) {
  values[1:99] <<- values[2:100]
  values[100] <<- value

  svg(width = 10, height = 6)
  print(xyplot(values ~ 0:99,
               type = c("g", "l"),
               col = "#654321",
               lwd = 2.5,
               main = "SWE 307 - Col-1 Live Data",
               xlab = "Sample (0-99)",
               ylab = "Value",
               ylim = c(-1.2, 1.2),
               scales = list(x = list(at = seq(0, 99, by = 11)), tck = c(1, 0))))
  svg.off()
}
