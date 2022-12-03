import breeze.linalg.{Axis, DenseVector, csvread}
import breeze.stats.{mean, stddev}

import java.io.File

var df = csvread(new File("data\\Real estate.csv"), ',', skipLines = 1)
var x_train = df(0 until 350, 1 until 7)
var y_train = df(0 until 350, 7)
var mean_x = mean(x_train, Axis._0).t
var sko = stddev(x_train, Axis._0).t
println(x_train)
var x_sc = x_train(::, 0) - mean_x(0)
//println("hey", )
//var x_train_sc = (x)
for( i <- 0 to 5){
  x_train(::, i) := (x_train(::, i) - mean_x(i)) / sko(i)
  println( "new value: " + x_train(::, i) );
}