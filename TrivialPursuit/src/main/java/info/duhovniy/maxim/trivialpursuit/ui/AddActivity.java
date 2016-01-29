package info.duhovniy.maxim.trivialpursuit.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import info.duhovniy.maxim.trivialpursuit.R;
import info.duhovniy.maxim.trivialpursuit.data.Question;
import info.duhovniy.maxim.trivialpursuit.network.AddQueryService;

/**
 * Created by maxduhovniy on 26/01/2016.
 */
public class AddActivity extends AppCompatActivity {
    private EditText subject, question, rightAns, wrongAns1, wrongAns2, wrongAns3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        subject = (EditText) findViewById(R.id.input_subject);
        question = (EditText) findViewById(R.id.input_question);
        rightAns = (EditText) findViewById(R.id.input_right);
        wrongAns1 = (EditText) findViewById(R.id.input_wrong1);
        wrongAns2 = (EditText) findViewById(R.id.input_wrong2);
        wrongAns3 = (EditText) findViewById(R.id.input_wrong3);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.button_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Question q = new Question(subject.getText().toString(),
                        question.getText().toString(),
                        rightAns.getText().toString(),
                        wrongAns1.getText().toString(),
                        wrongAns2.getText().toString(),
                        wrongAns3.getText().toString());

                Intent intent = new Intent(AddActivity.this, AddQueryService.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("New Question", q);
                intent.putExtra("Bundle Question", bundle);

                startService(intent);
            }
        });
    }
}
