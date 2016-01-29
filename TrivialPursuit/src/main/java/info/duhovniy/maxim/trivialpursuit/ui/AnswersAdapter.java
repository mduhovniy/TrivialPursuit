package info.duhovniy.maxim.trivialpursuit.ui;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import info.duhovniy.maxim.trivialpursuit.R;
import info.duhovniy.maxim.trivialpursuit.data.Answer;

/**
 * Created by maxduhovniy on 22/01/2016.
 */
public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.RecyclerViewHolder> {

    ArrayList<Answer> listAnswers = new ArrayList<>();
    private View mView;

    public AnswersAdapter(View view, ArrayList<Answer> answers) {
        mView = view;
        listAnswers = answers;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView answerText;
        public CardView answerCard;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            answerText = (TextView) itemView.findViewById(R.id.answer_text);
            answerCard = (CardView) itemView.findViewById(R.id.cv);

        }
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);

        // Return a new holder instance
        return new RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        holder.answerText.setText(listAnswers.get(position).getAnswer());

        holder.answerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listAnswers.get(position).isTrue()) {
                    Snackbar.make(mView, "BINGO!!!", Snackbar.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(mView, "Let's try another question", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listAnswers.size();
    }

    public void updateList(ArrayList<Answer> list) {
        listAnswers = list;
        notifyDataSetChanged();
    }
}
