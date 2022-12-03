import breeze.linalg._
import breeze.numerics._
import breeze.stats.{mean, stddev}

import java.io._


class LM() {
  var w: DenseVector[Double] = DenseVector[Double]()

  def fit(x: DenseMatrix[Double], y: DenseVector[Double]): Unit = {
    this.w = inv(x.t * x) * x.t * y
  }

  def predict(x : DenseMatrix[Double]): DenseVector[Double] = {
    x * this.w
  }
}

class RMSE() {
  def error(y_true: DenseVector[Double], y_pred: DenseVector[Double]): Double = {
    sqrt(mean((y_true - y_pred) *:* (y_true - y_pred)))
  }
}

object main {
  def main(args: Array[String]): Unit = {
    val train_data = csvread(new File("data\\Real estate.csv"),',', skipLines=1)
    val pw = new PrintWriter(new File("data\\metric.txt"))

    // lets split the data
    var x_train = train_data(0 to 350, 1 until 7)
    var y_train = train_data(0 to 350, 7)
    var x_val = train_data(350 to -1, 1 until 7)
    var y_val = train_data(350 to -1, 7)

    // data preprocessing
    var mean_x = mean(x_train, Axis._0).t
    var sko = stddev(x_train, Axis._0).t
    for (i <- 0 to 5) {
      x_train(::, i) := (x_train(::, i) - mean_x(i)) / sko(i)
      x_val(::, i) := (x_val(::, i) - mean_x(i)) / sko(i)
//      println("new value: " + x_train(::, i))
    }
    // add bias
    val bias_train = DenseVector.ones[Double](x_train.rows)
    val bias_test = DenseVector.ones[Double](x_val.rows)
    var x_train_b = DenseMatrix.horzcat(bias_train.toDenseMatrix.t, x_train)
    var x_val_b = DenseMatrix.horzcat(bias_test.toDenseMatrix.t, x_val)

    val model = new LM()
    println("train started")
    model.fit(x_train_b, y_train)
    println("train finished")
    var train_preds = model.predict(x_train_b)
    var rmse = new RMSE()
    var train_error = rmse.error(y_train, train_preds)
    pw.write("Train RMSE: " + train_error + "\n")

    var predict = model.predict(x_val_b)
    var test_error = rmse.error(y_val, predict)
    pw.write("Test RMSE: " + test_error + "\n")
    println("train loss is: " + train_error)
    println("test loss is: " + test_error)
    csvwrite(new File("data\\prediction.txt"), predict.toDenseMatrix.t)

    pw.close()
  }
}

