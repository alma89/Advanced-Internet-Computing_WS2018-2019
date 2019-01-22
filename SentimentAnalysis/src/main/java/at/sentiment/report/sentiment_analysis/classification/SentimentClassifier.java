package at.sentiment.report.sentiment_analysis.classification;

/* IMPLEMENTATION BASED ON WEKA CLASSIFICATION FRAMEWORK */

import at.sentiment.report.sentiment_analysis.features.Feature;
import at.sentiment.report.sentiment_analysis.features.FeatureVector;
import at.sentiment.report.sentiment_analysis.preprocessing.PreprocessorException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;

import java.util.*;

/*
* Idea sources:
*   https://machinelearningmastery.com/how-to-run-your-first-classifier-in-weka/
*   https://www.ibm.com/developerworks/library/os-weka2/
* */

public class SentimentClassifier implements ISentimentClassifier {

    private final static Logger logger = LogManager.getLogger(SentimentClassifier.class);

    private final Classifier classifier;
    private final Instances instances;
    private final ArrayList<Attribute> featureList;
    private final Map<String,Integer> featureIndexMap;

    public SentimentClassifier(List<Sample> samplesList, Classifier algorithm) throws ClassificationException{

        classifier = algorithm;
        featureList = createFeatureList(samplesList);

        featureIndexMap = initFeatureIndexMap(featureList);
        instances = createInstances("train", samplesList, featureList);

        train();
    }



    private ArrayList<Attribute> createFeatureList(Iterable<Sample> samples)
            throws ClassificationException {
        try {
            Set<String> featureStrings = loadDistinctFeatures(samples);
            ArrayList<Attribute> featureList = new ArrayList<>();
            for (String feature : featureStrings) {
                featureList.add(new Attribute(feature));
            }

            List<String> sentiments = new ArrayList<>();
            sentiments.add("negative");
            sentiments.add("positive");
            Attribute sentimentAttribute = new Attribute("___sentiment___", sentiments);
            featureList.add(sentimentAttribute);

            return featureList;
        } catch (PreprocessorException e) {
            throw new ClassificationException(e);
        }
    }

    private Set<String> loadDistinctFeatures(Iterable<Sample> samples) throws PreprocessorException {
        HashSet<String> features = new HashSet<>();
        for (Sample sample : samples) {
            FeatureVector featureVector = sample.getFeatureVector();
            for (Feature feature : featureVector.getFeatures()) {
                features.add(feature.getWord());
            }
        }
        return features;
    }

    private Map<String, Integer> initFeatureIndexMap(ArrayList<Attribute> featureList) {
        Map<String, Integer> featureIndexMap = new HashMap<>();
        for (int i = 0; i < featureList.size() - 1; i++) {
            Attribute feature = featureList.get(i);
            featureIndexMap.put(feature.name(), i);
        }
        return featureIndexMap;
    }

    private Instances createInstances(String name, List<Sample> samples, ArrayList<Attribute> featureList) {
        Instances instances = new Instances(name, featureList,samples.size());
        instances.setClassIndex(featureList.size() - 1);

        for (Sample sample : samples) {
            FeatureVector featureVector = sample.getFeatureVector();
            Sentiment sentiment = sample.getSentiment();
            Instance instance = createInstance(instances, featureVector, sentiment);
            instances.add(instance);
        }

        return instances;
    }

    private Instance createInstance(Instances instances, FeatureVector featureVector) {
        return createInstance(instances, featureVector, null);
    }

    private Instance createInstance(Instances instances, FeatureVector featureVector, Sentiment sentiment) {
        DenseInstance instance = new DenseInstance(featureList.size());
        instance.setDataset(instances);

        for (Feature feature : featureVector.getFeatures()) {
            if (classificationHelper(feature.getWord())) {
                instance.setValue(featureIndexMap.get(feature.getWord()), 1.0);
            }
        }

        if (sentiment != null) {
            instance.setClassValue(intFromSentiment(sentiment));
        }

        double[] defaultValues = new double[featureList.size()];
        instance.replaceMissingValues(defaultValues);

        return instance;
    }

    private boolean classificationHelper(String token) {
        return featureIndexMap.get(token) != null;
    }

    private int intFromSentiment(Sentiment sentiment) {
        if (sentiment == Sentiment.NEGATIVE) {
            return 0;
        } else {
            return 1;
        }
    }

    private void train() throws ClassificationException {
        try {
            classifier.buildClassifier(instances);
        } catch (Exception e) {
            throw new ClassificationException(e);
        }
    }


    @Override
    public Sentiment classify(FeatureVector featureVector) throws ClassificationException{
        Instances instances = new Instances("live", featureList, 1);
        instances.setClassIndex(featureList.size() - 1);
        Instance instanceToClassify = createInstance(instances, featureVector);
        instances.add(instanceToClassify);
        try {
            double classification = classifier.classifyInstance(instanceToClassify);
            return sentimentFromClassification(classification);
        } catch (Exception e) {
            throw new ClassificationException(e);
        }
    }

    private Sentiment sentimentFromClassification(double classification) {
        if (classification == 0) {
            return Sentiment.NEGATIVE;
        } else {
            return Sentiment.POSITIVE;
        }
    }

    public Evaluation evaluate(List<Sample> testSamples) throws ClassificationException {
        Instances testInstances = createInstances("test", testSamples, featureList);
        try {
            Evaluation evaluate = new Evaluation(instances);
            evaluate.evaluateModel(classifier, testInstances);
            return evaluate;
        } catch (Exception e) {
            throw new ClassificationException(e);
        }
    }
}
