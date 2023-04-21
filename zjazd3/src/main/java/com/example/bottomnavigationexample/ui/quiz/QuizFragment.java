package com.example.bottomnavigationexample.ui.quiz;

import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.bottomnavigationexample.R;
import com.example.bottomnavigationexample.databinding.FragmentQuizBinding;

import java.util.ArrayList;
import java.util.List;

public class QuizFragment extends Fragment {

    private TextView questionTextView;
    private Button answerTrueButton;
    private Button answerFalseButton;

    private TextView answerHintTextView;
    private int points;
    private Pair<String, Boolean> currentQuestion;

    private FragmentQuizBinding binding;
    private List<Pair<String, Boolean>> questions = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentQuizBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        questionTextView = (TextView) root.findViewById(R.id.questionTextView);
        answerHintTextView = (TextView) root.findViewById(R.id.answerHintTextView);
        answerTrueButton = root.findViewById(R.id.answerTrueButton);
        answerFalseButton = root.findViewById(R.id.answerFalseButton);

        answerTrueButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (currentQuestion.second) {
                    points++;
                }
                displayNextScreen();
            }
        });

        answerFalseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int currentQuestionIndex = questions.indexOf(currentQuestion);

                if (!currentQuestion.second) {
                    points++;
                }
                displayNextScreen();
            }
        });

        questions.add(new Pair<>(getString(R.string.questionText1), Boolean.FALSE));
        questions.add(new Pair<>(getString(R.string.questionText2), Boolean.TRUE));
        questions.add(new Pair<>(getString(R.string.questionText3), Boolean.TRUE));
        questions.add(new Pair<>(getString(R.string.questionText4), Boolean.FALSE));
        questions.add(new Pair<>(getString(R.string.questionText5), Boolean.FALSE));
        questions.add(new Pair<>(getString(R.string.questionText6), Boolean.TRUE));
        questions.add(new Pair<>(getString(R.string.questionText7), Boolean.TRUE));
        questions.add(new Pair<>(getString(R.string.questionText8), Boolean.FALSE));

        points = 0;
        currentQuestion = questions.get(0);

        questionTextView.setText(currentQuestion.first);
        return root;
    }

    private void displayNextScreen() {
        int currentQuestionIndex = questions.indexOf(currentQuestion);

        if (currentQuestionIndex == questions.size() - 1) {
            answerTrueButton.setVisibility(View.GONE);
            answerFalseButton.setVisibility(View.GONE);
            answerHintTextView.setVisibility(View.GONE);
            questionTextView.setText(String.format(getString(R.string.yourPoints), points));
        } else {
            currentQuestion = questions.get(currentQuestionIndex + 1);
            questionTextView.setText(currentQuestion.first);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

