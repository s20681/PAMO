package com.example.pamo_kotlin_bap.quiz

import android.os.Bundle
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.pamo_kotlin_bap.R
import com.example.pamo_kotlin_bap.databinding.FragmentQuizBinding

class QuizFragment : Fragment() {
    private var questionTextView: TextView? = null
    private var answerTrueButton: Button? = null
    private var answerFalseButton: Button? = null
    private var answerHintTextView: TextView? = null
    private var points = 0
    private var currentQuestion: Pair<String, Boolean>? = null
    private var binding: FragmentQuizBinding? = null
    private val questions: MutableList<Pair<String, Boolean>?> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizBinding.inflate(inflater, container, false)
        val root: View = binding!!.getRoot()
        questionTextView = root.findViewById<View>(R.id.questionTextView) as TextView
        answerHintTextView = root.findViewById<View>(R.id.answerHintTextView) as TextView
        answerTrueButton = root.findViewById<Button>(R.id.answerTrueButton)
        answerFalseButton = root.findViewById<Button>(R.id.answerFalseButton)
        answerTrueButton?.setOnClickListener(View.OnClickListener {
            if (currentQuestion!!.second) {
                points++
            }
            displayNextScreen()
        })
        answerFalseButton?.setOnClickListener(View.OnClickListener {
            val currentQuestionIndex = questions.indexOf(currentQuestion)
            if (!currentQuestion!!.second) {
                points++
            }
            displayNextScreen()
        })
        questions.add(Pair<String, Boolean>(getString(R.string.questionText2), java.lang.Boolean.TRUE))
        questions.add(Pair<String, Boolean>(getString(R.string.questionText3), java.lang.Boolean.TRUE))
        questions.add(Pair<String, Boolean>(getString(R.string.questionText1), java.lang.Boolean.FALSE))
        questions.add(Pair<String, Boolean>(getString(R.string.questionText4), java.lang.Boolean.FALSE))
        questions.add(Pair<String, Boolean>(getString(R.string.questionText5), java.lang.Boolean.FALSE))
        questions.add(Pair<String, Boolean>(getString(R.string.questionText6), java.lang.Boolean.TRUE))
        questions.add(Pair<String, Boolean>(getString(R.string.questionText7), java.lang.Boolean.TRUE))
        questions.add(Pair<String, Boolean>(getString(R.string.questionText8), java.lang.Boolean.FALSE))
        points = 0
        currentQuestion = questions[0]
        questionTextView!!.setText(currentQuestion!!.first)
        return root
    }

    private fun displayNextScreen() {
        val currentQuestionIndex = questions.indexOf(currentQuestion)
        if (currentQuestionIndex == questions.size - 1) {
            answerTrueButton!!.visibility = View.GONE
            answerFalseButton!!.visibility = View.GONE
            answerHintTextView?.setVisibility(View.GONE)
            questionTextView?.setText(String.format(getString(R.string.yourPoints), points))
        } else {
            currentQuestion = questions[currentQuestionIndex + 1]
            questionTextView?.setText(currentQuestion!!.first)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}