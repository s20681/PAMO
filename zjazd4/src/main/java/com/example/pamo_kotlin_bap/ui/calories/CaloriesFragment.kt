package com.example.pamo_kotlin_bap.ui.calories

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.pamo_kotlin_bap.R
import com.example.pamo_kotlin_bap.databinding.FragmentCaloriesBinding

class CaloriesFragment : Fragment() {
    private var binding: FragmentCaloriesBinding? = null
    private var root: View? = null
    private var weight = 0.0
    private var height = 0.15
    private var age = 0
    private var heightTextView: TextView? = null
    private var weightTextView: TextView? = null
    private var ageTextView: TextView? = null
    private var totalTextView: TextView? = null
    var radioGroup: RadioGroup? = null
    var radioButton1: RadioButton? = null
    var radioButton2: RadioButton? = null
    var textView: TextView? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCaloriesBinding.inflate(inflater, container, false)
        val root: View = binding!!.getRoot()

//looking up TextViews in layout
        radioGroup = root.findViewById<RadioGroup>(R.id.radioGroup)
        weightTextView = root!!.findViewById<View>(R.id.weightTextView) as TextView
        heightTextView = root!!.findViewById<View>(R.id.heightTextView) as TextView
        ageTextView = root!!.findViewById<View>(R.id.ageTextView) as TextView
        totalTextView = root!!.findViewById<View>(R.id.totalTextView) as TextView
        totalTextView!!.setText(String.format(0.toString() + ""))

// setting TextWatchers for both weight and height fields
        val weightEditText: EditText = root!!.findViewById<View>(R.id.weightEditText) as EditText
        weightEditText.addTextChangedListener(weightEditTextWatcher)
        val heightEditText: EditText = root!!.findViewById<View>(R.id.heightEditText) as EditText
        heightEditText.addTextChangedListener(heightEditTextWatcher)
        val ageEditText: EditText = root!!.findViewById<View>(R.id.ageEditText) as EditText
        ageEditText.addTextChangedListener(ageEditTextWatcher)
        radioGroup = root.findViewById<RadioGroup>(R.id.radioGroup)
        textView = root.findViewById<TextView>(R.id.answerHintTextView)
        val buttonApply = root!!.findViewById<Button>(R.id.button_apply)
        buttonApply.setOnClickListener {
            val radioId: Int? = radioGroup?.getCheckedRadioButtonId()
            radioButton1 = radioId?.let { it1 -> root.findViewById<RadioButton>(it1) }
            textView?.setText("Your choice: " + radioButton1?.getText())
        }
        radioGroup?.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(radioGroup: RadioGroup, i: Int) {
                val radioId = i
                onRadioButtonClickedCalculate()
            }
        })
        return root
    }

    //method calculate, calculating BMI, setting totalTextView as result
    private fun calculate() {
        val total = weight / (height * height)
        totalTextView?.setText(String.format(total.toString() + ""))
    }

    // listeners object for TextViews weight and height text-changed events
    private val weightEditTextWatcher: TextWatcher = object : TextWatcher {
        override fun onTextChanged(
            s: CharSequence, start: Int,
            before: Int, count: Int
        ) {
            try {
                weight = s.toString().toDouble()
                weightTextView?.setText(String.format(weight.toString() + ""))
            } catch (e: NumberFormatException) {
                weightTextView?.setText("")
                weight = 0.0
            }
            //recalculate on change
            calculate()
            onRadioButtonClickedCalculate()
        }

        override fun afterTextChanged(s: Editable) {}
        override fun beforeTextChanged(
            s: CharSequence, start: Int, count: Int, after: Int
        ) {
        }
    }
    private val heightEditTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(
            s: CharSequence, start: Int,
            before: Int, count: Int
        ) {
            try {
                height = s.toString().toDouble()
                heightTextView?.setText(String.format(height.toString() + ""))
            } catch (e: NumberFormatException) {
                heightTextView?.setText("")
                height = 0.0
            }
            //recalculate on change
//            calculate();
            onRadioButtonClickedCalculate()
        }

        override fun afterTextChanged(s: Editable) {}
    }
    private val ageEditTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(
            s: CharSequence, start: Int,
            before: Int, count: Int
        ) {
            try {
                age = s.toString().toInt()
                ageTextView?.setText(String.format(age.toString() + ""))
            } catch (e: NumberFormatException) {
                age = 0
            }
            //recalculate on change
//            calculate();
            onRadioButtonClickedCalculate()
        }

        override fun afterTextChanged(s: Editable) {}
    }

    fun onRadioButtonClickedCalculate() {
        val radioId: Int? = radioGroup?.getCheckedRadioButtonId()
        radioButton1 = root?.findViewById<RadioButton>(R.id.rid1)
        radioButton2 = root?.findViewById<RadioButton>(R.id.rid2)

        // Is the male button now checked?
        val checked: Boolean = radioButton1?.isChecked() == true
        var total = 0.00
        total = if (checked) {
            88.362 + 13.397 * weight + 4.799 * height - 5.677 * age
        } else {
            447.593 + 9.247 * weight + 3.098 * height - 4.330 * age
        }
        totalTextView?.setText(String.format(total.toString() + ""))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}