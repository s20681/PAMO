package com.example.pamo_kotlin_bap.ui.bmi

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.pamo_kotlin_bap.R
import com.example.pamo_kotlin_bap.databinding.FragmentBmiBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import java.text.NumberFormat


class BMIFragment : Fragment() {
    private var weight = 0.0 // weight entered by the user
    private var height = 0.0 // height entered by the user
    var bmi = 0.0
    private val bmiArr: MutableList<Double> = ArrayList()
    private var weightTextView // shows the weight value
            : TextView? = null
    private var heightTextView // shows the height value
            : TextView? = null
    private var bmiTextView // shows calculated bmi
            : TextView? = null
    private var binding: FragmentBmiBinding? = null
    private var volumeReportChart: LineChart? = null
    private var addToChartButton: Button? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBmiBinding.inflate(inflater, container, false)
        val root: View = binding!!.getRoot()

        // get references to programmatically manipulated TextViews
        weightTextView = root.findViewById<View>(R.id.weightTextView) as TextView
        heightTextView = root.findViewById<View>(R.id.heightTextView) as TextView
        bmiTextView = root.findViewById<View>(R.id.bmiTextView) as TextView
        bmiTextView!!.setText(numberFormat.format(0))
        volumeReportChart = root.findViewById(R.id.reportingChart)
        addToChartButton = root.findViewById<Button>(R.id.addBmi)
        addToChartButton?.setOnClickListener(View.OnClickListener {
            if (bmi != 0.0) {
                bmiArr.add(bmi)
            }
            redrawChart()
        })

        // set amountEditText's TextWatcher
        val weightEditText: EditText = root.findViewById<View>(R.id.weightEditText) as EditText
        weightEditText.addTextChangedListener(weightEditTextWatcher)

        // set percentSeekBar's OnSeekBarChangeListener
        val heightEditText: EditText = root.findViewById<View>(R.id.heightEditText) as EditText
        heightEditText.addTextChangedListener(heightEditTextWatcher)
        bmiArr.add(28.5)
        bmiArr.add(28.3)
        bmiArr.add(28.1)
        bmiArr.add(28.0)
        val entries: MutableList<Entry> = ArrayList<Entry>()
        for (i in bmiArr.indices) {
            entries.add(Entry(i.toFloat(), bmiArr[i].toFloat()))
        }
        val dataSet = LineDataSet(entries, getString(R.string.bmiLabel)) // add entries to dataset
        dataSet.setColor(Color.BLACK)
        dataSet.setValueTextColor(Color.BLACK)
        val lineData = LineData(dataSet)
        volumeReportChart?.setData(lineData)
        volumeReportChart?.invalidate() // refresh
        return root
    }

    // calculate and display tip and total amounts
    private fun calculate() {
        // calculate the height in meters square and bmi
        bmi = weight / Math.pow(height / 100, 2.0)
        if (java.lang.Double.isInfinite(bmi) || java.lang.Double.isNaN(bmi)) {
            bmi = 0.0
        }
        // display bmi
        bmiTextView?.setText(numberFormat.format(bmi))
    }

    private fun redrawChart() {
        val entries: MutableList<Entry> = ArrayList<Entry>()
        for (i in bmiArr.indices) {
            entries.add(Entry(i.toFloat(), bmiArr[i].toFloat()))
        }
        val dataSet = LineDataSet(entries, getString(R.string.bmiLabel)) // add entries to dataset
        dataSet.setColor(Color.BLACK)
        dataSet.setValueTextColor(Color.BLACK)
        val lineData = LineData(dataSet)
        volumeReportChart?.setData(lineData)
        volumeReportChart?.invalidate() // refresh
    }

    // listener object for the EditText's text-changed events
    private val weightEditTextWatcher: TextWatcher = object : TextWatcher {
        // called when the user modifies the weight amount
        override fun onTextChanged(
            s: CharSequence, start: Int, before: Int, count: Int
        ) {
            try { // get weight amount and display value
                weight = s.toString().toDouble()
                weightTextView?.setText(numberFormat.format(weight))
            } catch (e: NumberFormatException) { // if s is empty or non-numeric
                weightTextView?.setText("")
                weight = 0.0
            }
            calculate() // update the bmi TextView
        }

        override fun afterTextChanged(s: Editable) {}
        override fun beforeTextChanged(
            s: CharSequence, start: Int, count: Int, after: Int
        ) {
        }
    }

    // listener object for the EditText's text-changed events
    private val heightEditTextWatcher: TextWatcher = object : TextWatcher {
        // called when the user modifies the height amount
        override fun onTextChanged(
            s: CharSequence, start: Int, before: Int, count: Int
        ) {
            try { // parsing height value
                height = s.toString().toDouble()
                heightTextView?.setText(numberFormat.format(height))
            } catch (e: NumberFormatException) { // if s is empty or non-numeric
                heightTextView?.setText("")
                height = 0.0
            }
            calculate() // update the bmi textView
        }

        override fun afterTextChanged(s: Editable) {}
        override fun beforeTextChanged(
            s: CharSequence, start: Int, count: Int, after: Int
        ) {
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        private val numberFormat = NumberFormat.getNumberInstance()
    }
}