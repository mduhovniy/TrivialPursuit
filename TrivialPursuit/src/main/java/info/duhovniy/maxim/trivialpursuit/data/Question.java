package info.duhovniy.maxim.trivialpursuit.data;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by maxduhovniy on 18/01/2016.
 */
public class Question implements Parcelable {

    private String mSubject;
    private String mQuery;
    private String mRightAnswer;
    private String mWrongAnswer1;
    private String mWrongAnswer2;
    private String mWrongAnswer3;

    public Question() {
    }

    public Question(String subject, String query, String rightAnswer, String wrongAnswer1,
                    String wrongAnswer2, String wrongAnswer3) {
        super();
        mSubject = subject;
        mQuery = query;
        mRightAnswer = rightAnswer;
        mWrongAnswer1 = wrongAnswer1;
        mWrongAnswer2 = wrongAnswer2;
        mWrongAnswer3 = wrongAnswer3;
    }

    protected Question(Parcel in) {
        mSubject = in.readString();
        mQuery = in.readString();
        mRightAnswer = in.readString();
        mWrongAnswer1 = in.readString();
        mWrongAnswer2 = in.readString();
        mWrongAnswer3 = in.readString();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public JSONObject toJSONObject() {
        JSONObject json = null;

        try {
            json = new JSONObject();

            json.put(DataConstant.QUERY, getQuery());
            json.put(DataConstant.RIGHT, getRightAnswer());
            json.put(DataConstant.SUBJECT, getSubject());
            json.put(DataConstant.WRONG1, getWrongAnswer1());
            json.put(DataConstant.WRONG2, getWrongAnswer2());
            json.put(DataConstant.WRONG3, getWrongAnswer3());

        } catch (JSONException e) {
            e.getCause();
        }

        return json;
    }

    public ArrayList<Answer> getAnswerList() {
        int bingo = (int)(Math.random() * 4);
        ArrayList<Answer> resultList = new ArrayList<>();

        resultList.add(new Answer(getWrongAnswer1(), false));
        resultList.add(new Answer(getWrongAnswer2(), false));
        resultList.add(new Answer(getWrongAnswer3(), false));
        resultList.add(bingo, new Answer(getRightAnswer(), true));

        return resultList;
    }

    public String getSubject() {
        return mSubject;
    }

    public void setSubject(String subject) {
        mSubject = subject;
    }

    public String getQuery() {
        return mQuery;
    }

    public void setQuery(String query) {
        mQuery = query;
    }

    public String getRightAnswer() {
        return mRightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        mRightAnswer = rightAnswer;
    }

    public String getWrongAnswer1() {
        return mWrongAnswer1;
    }

    public void setWrongAnswer1(String wrongAnswer1) {
        mWrongAnswer1 = wrongAnswer1;
    }

    public String getWrongAnswer2() {
        return mWrongAnswer2;
    }

    public void setWrongAnswer2(String wrongAnswer2) {
        mWrongAnswer2 = wrongAnswer2;
    }

    public String getWrongAnswer3() {
        return mWrongAnswer3;
    }

    public void setWrongAnswer3(String wrongAnswer3) {
        mWrongAnswer3 = wrongAnswer3;
    }

    @Override
    public String toString() {
        return "Question: " + mSubject + " " + mQuery + " " + mRightAnswer + " " + mWrongAnswer1 + " "
                + mWrongAnswer2 + " " + mWrongAnswer3;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mSubject);
        dest.writeString(mQuery);
        dest.writeString(mRightAnswer);
        dest.writeString(mWrongAnswer1);
        dest.writeString(mWrongAnswer2);
        dest.writeString(mWrongAnswer3);
    }
}
