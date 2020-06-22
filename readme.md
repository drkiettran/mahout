# Sample code using Mahout

## SampleRecommender

The following example is from Mahout website: `https://mahout.apache.org/users/recommender/userbased-5-minutes.html`.

Create a class called SampleRecommender with a main method.

The first thing we have to do is load the data from the file. Mahout’s recommenders use an interface called DataModel to handle interaction data. You can load our made up interactions like this:

```java
DataModel model = new FileDataModel(new File("/path/to/dataset.csv"));
```

The training model is organized as a comma separated value as follows:

```csv
user_id,item_number,preference_value
```

dataset2.csv

```csv
1,101,5.0
1,102,3.0
1,103,2.5
2,101,2.0
2,102,2.5
2,103,5.0
2,104,2.0
3,101,2.5
3,104,4.0
3,105,4.5
3,107,5.0
4,101,5.0
4,103,3.0
4,104,4.5
4,106,4.0
5,101,4.0
5,102,3.0
5,103,2.0
5,104,4.0
5,105,3.5
5,106,4.0
```

dataset.csv:

```csv
1,10,1.0
1,11,2.0
1,12,5.0
1,13,5.0
1,14,5.0
1,15,4.0
1,16,5.0
1,17,1.0
1,18,5.0
2,10,1.0
2,11,2.0
2,15,5.0
2,16,4.5
2,17,1.0
2,18,5.0
3,11,2.5
3,12,4.5
3,13,4.0
3,14,3.0
3,15,3.5
3,16,4.5
3,17,4.0
3,18,5.0
4,10,5.0
4,11,5.0
4,12,5.0
4,13,0.0
4,14,2.0
4,15,3.0
4,16,1.0
4,17,4.0
4,18,1.0

```

In this example, we want to create a user-based recommender. The idea behind this approach is that when we want to compute recommendations for a particular users, we look for other users with a similar taste and pick the recommendations from their items. For finding similar users, we have to compare their interactions. There are several methods for doing this. One popular method is to compute the correlation coefficient between their interactions. In Mahout, you use this method as follows:

```
UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
```

The next thing we have to do is to define which similar users we want to leverage for the recommender. For the sake of simplicity, we’ll use all that have a similarity greater than 0.1. This is implemented via a ThresholdUserNeighborhood:

```
UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);
```

Now we have all the pieces to create our recommender:

```
UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
```

We can easily ask the recommender for recommendations now. If we wanted to get three items recommended for the user with userID 2, we would do it like this:

```java
List recommendations = recommender.recommend(2, 3);
for (RecommendedItem recommendation : recommendations) {
  System.out.println(recommendation);
  
```

### to build

```
mvn clean package
```

### to run

```
java -cp target/cisc-525-mahout-jar-with-dependencies.jar com.drkiettran.mahout.SampleRecommender /home/student/cisc_525/mahout/src/main/resources/dataset2.csv 1 1
```
