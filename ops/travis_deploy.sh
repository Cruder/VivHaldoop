sbt package
scp target/scala-2.12/vivhaldoop_2.12-0.1.jar vivhaldoop@edge5.sagean.fr:app/vivhaldoop.jar
ssh vivhaldoop@edge5.sagean.fr -t "
hdfs dfs -rm vivhaldoop.jar;
hdfs dfs -rm app.properties;
hdfs dfs -copyFromLocal app/vivhaldoop.jar vivhaldoop.jar;
hdfs dfs -copyFromLocal app/app.properties app.properties
"
