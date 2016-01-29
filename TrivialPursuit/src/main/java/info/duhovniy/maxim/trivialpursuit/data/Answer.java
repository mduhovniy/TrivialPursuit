package info.duhovniy.maxim.trivialpursuit.data;

/**
 * Created by maxduhovniy on 21/01/2016.
 */
public class Answer {
    private String mAnswer;
    private boolean mTrue;

    public Answer(String s, boolean b) {
        mAnswer = s;
        mTrue = b;
    }

    public boolean isTrue() {
        return mTrue;
    }

    public String getAnswer() {
        return mAnswer;
    }
}
