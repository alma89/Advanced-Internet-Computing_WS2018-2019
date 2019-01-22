package at.sentiment.report.sentiment_analysis.features;

/*
 * Twitter-specific feature which consists of tags and plain words.
 * We define a plain word as a word that is not a tag.
 * Since the common tweeter message as a rule contains not only natural language words,
 * but also tags, we need to make the distinction as defined above.
 */

public class Feature {

    private String word;        /* represents plain words */
    private String tag;         /* represents tags */


    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "Feature{" +
                "word='" + word + '\'' +
                ", tag='" + tag + '\'' +
                '}';
    }
}
