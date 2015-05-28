#!/bin/bash

spark-submit --class "ModelFinal.PredictDataSet1Final" --master yarn-client target/scala-2.10/prediction1_2.10-1.0.jar
