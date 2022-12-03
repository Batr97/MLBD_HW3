  var df = csvread(new File("data\\Real estate.csv"),',', skipLines=1)
  var x_train = df(0 until 350, 0 until 7)
  var y_train = df(0 until 350, 7)
  var mean_x = mean(x_train, Axis._0).t