package com.drkiettran.mahout;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

/**
 * Sample Recommender
 *
 */
public class SampleRecommender {
	private static void recommend(String dataset, int userId, int numItems) throws IOException, TasteException {
		DataModel model = new FileDataModel(new File(dataset));
		UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
		UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);
		UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
		List<RecommendedItem> recommendations = recommender.recommend(userId, numItems);
		for (RecommendedItem recommendation : recommendations) {
			System.out.println(recommendation);
		}
	}

	public static void main(String[] argv) throws IOException, TasteException {
		BasicConfigurator.configure();
		if (argv.length >= 3) {
			String dataset = argv[0];
			int userId = Integer.valueOf(argv[1]);
			int numItems = Integer.valueOf(argv[2]);
			recommend(dataset, userId, numItems);
		} else
			System.out.println("parameters: dataset userid itemid");
		return;
	}

}
