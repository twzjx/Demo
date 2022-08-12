package org.example

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}
import org.apache.spark.sql.functions._


/**
 * @author jiaxing zhou
 * @version 1.0
 * @Date 2022/8/11
 */
object Demo1 {

    val FILE_PATH = "movies.csv"

    def main(args: Array[String]): Unit = {
        val map = createSparkAndDataFrame()
        rankByYearGross(map)
        rankByMovieType(map)
    }

    def createSparkAndDataFrame() = {
        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Demo1")
        val spark = SparkSession.builder().config(sparkConf).getOrCreate()
        var df = spark.read.options(Map("header" -> "true", "inferSchema" -> "true", "delimiter" -> ",")).csv(FILE_PATH)
        df = df.withColumn("Worldwide_Gross", regexp_replace(col("Worldwide_Gross"), "\\$", ""))
        val map = Map("spark" -> spark, "df" -> df)
        map
    }

    def rankByYearGross(map: Map[String, Serializable]) = {
        val df = map("df").asInstanceOf[DataFrame]
        val spark = map("spark").asInstanceOf[SparkSession]
        import spark.implicits._
        df.show()
        var result = df.select($"year", $"Worldwide_Gross").groupBy("year")
            .agg(sum("Worldwide_Gross").as("sum_gross"))
            .orderBy(desc("sum_gross"))
        result = result.withColumn("sum_gross", round(col("sum_gross"), 2))
        result.show()
    }

    def rankByMovieType(map: Map[String, Serializable]): Unit = {
        val df = map("df").asInstanceOf[DataFrame]
        val spark = map("spark").asInstanceOf[SparkSession]
        import spark.implicits._
        df.show()
        var result = df.select($"year", $"Worldwide_Gross", $"Genre")
            .groupBy("Genre", "year")
            .agg(sum("Worldwide_Gross").as("sum_gross"))
            .orderBy(desc("sum_gross"))
        result = result.withColumn("sum_gross", round(col("sum_gross"), 2))
        result.show()
    }
}
