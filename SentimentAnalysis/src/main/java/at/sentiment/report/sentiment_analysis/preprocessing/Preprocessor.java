package at.sentiment.report.sentiment_analysis.preprocessing;
/* IMPLEMENTATION IS BASED ON:
 * --> Stanford NLP framework
 */

import at.sentiment.report.sentiment_analysis.features.Feature;
import at.sentiment.report.sentiment_analysis.features.FeatureVector;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Preprocessor implements IPreprocessor{

    private final static Logger logger = LogManager.getLogger(Preprocessor.class);

    private static final String URL_PATTERN = "((https?|ftp|gopher|telnet|file|Unsure|http):" +
            "((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
    private static final String HASHTAG_PATTERN = "#";
    private static final String TAGGER_PATH = "gate-EN-twitter-fast.model";

    private PTBTokenizer.PTBTokenizerFactory<CoreLabel> tokenizer;
    private MaxentTagger tagger;

    public Preprocessor() {

        PTBTokenizer.PTBTokenizerFactory factory = PTBTokenizer.PTBTokenizerFactory.newPTBTokenizerFactory(false,false);
        factory.setOptions("untokenizable=noneDelete");

        tokenizer = factory;
        tagger = new MaxentTagger(TAGGER_PATH);
    }

    public FeatureVector conductPreprocessing(String message) throws PreprocessorException{

        List<Feature> features = new ArrayList<Feature>();
        List<CoreLabel> coreLabels = tokenizer.getTokenizer(new StringReader(message)).tokenize();

        /* tag all labels */
        tagger.tagCoreLabels(coreLabels);

        for(CoreLabel label : coreLabels){
            String word = normalizeWord(label.word(), label.tag());
            if(!word.isEmpty()){
                if(containsAllowedTag(label.tag())){
                    logger.debug(word);
                    Feature feature = new Feature();
                    feature.setWord(word);
                    feature.setTag(label.tag());
                    features.add(feature);
                }
            }
        }

        FeatureVector featureVector = new FeatureVector();
        featureVector.setFeatures(features);
        featureVector.setOriginalMessage(message);

        return featureVector;
    }

    /**
     * @name removeHashtagCharacter
     * @brief function removes the hashtag character form the given word.
     * @details after the completion of tokenization procedure the text is represented as a bag of words. The
     *          tokenization happens on spaces, that's why everything that is between two spaces is a word. We
     *          know that tweeter posts may contains hashtags before words. Hashtags are not relevant for analysis
     *          of tweeter posts and may produce problems in the future, therefore there is a need to delete them.
     * @author Gruzdev Eugen
     * @date 01.12.2017
     * @param word : String
     * @return word without hashtag character
     */
    private String removeHashtagCharacter(String word) {
        return word.replaceAll(HASHTAG_PATTERN,"");
    }

    /**
     * @name removeURL
     * @brief function removes URLs if they are present in text
     * @details after tokenization of textual data by spaces, each token is a single word. Having URL pattern
     *          we can determine if the given token is URL. If it is so, then there is a reason to delete this
     *          token from the text to reduce the classification task complexity. URLs does not carry much
     *          information for analysis, therefore the information loss after deletion of them is minimal.
     * @author Gruzdev Eugen
     * @date 01.12.2017
     * @param word : String
     * @return empty String
     */
    private String removeUrl(String word) {
        Pattern p = Pattern.compile(URL_PATTERN, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(word);

        int i = 0;
        while (m.find()) {
            word = word.replaceAll(m.group(i), "").trim();
            i++;
        }

        return word;
    }


    /**
     * @name normalizeWord
     * @brief function normalizes the given word
     * @details there is a need to define some rules every tweet should adhere to for analysis purposes. This function
     *          defines and performs such rules: convert the word to lover case, remove URLs and remove hashtags.
     * @author Gruzdev Eugen
     * @date 01.12.2017
     * @param word : String
     * @param tag : String
     * @return normalized word
     */
    private String normalizeWord(String word, String tag) {
        // lowercase word
        word = word.toLowerCase();

        // remove URL
        word = removeUrl(word);

        // remove hashtag character
        if (tag.equals("HT")) {
            word = removeHashtagCharacter(word);
        }
        return word;
    }

    /**
     * @name containsAllowedTag
     * @brief function filters unnecessary tags
     * @details idea source: https://www.winwaed.com/blog/2011/11/08/part-of-speech-tags/
     *          not every tag is important for sentimental analysis. The real information gain can be mined from
     *          the verbs and adjectives. So, this functions filters out tags with minimal information gain: all
     *          those tags that are not verbs, hashtags (normalization of hashtags is separate function) or adjectives.
     * @author Gruzdev Eugen
     * @date 01.12.2017
     * @param tag : String
     * @return true in case if tag is verb, adjective or hashtag; false otherwise.
     */
    private boolean containsAllowedTag(String tag) {
        if (tag.startsWith("VB") ||         /* verb, base form  (Ex.: is) */
                tag.startsWith("VBD") ||    /* verb, past tense (Ex.: was) */
                tag.startsWith("VBG") ||    /* verb, gerund     (Ex.: taking) */
                tag.startsWith("VBN") ||    /* verb, past participle (Ex.: taken) */
                tag.startsWith("VBP") ||    /* verb, sing. present, non-3d (Ex.: take) */
                tag.startsWith("VBZ") ||    /* verb, 3rd person, single, present (Ex.: takes) */
                tag.startsWith("JJ") ||     /* adjective        (Ex.: big) */
                tag.startsWith("HT")) {     /* hashtag          (Ex.: #) */
            return true;
        }

        return false;
    }


}
