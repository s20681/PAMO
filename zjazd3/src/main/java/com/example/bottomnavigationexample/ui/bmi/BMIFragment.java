package com.example.bottomnavigationexample.ui.bmi;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.bottomnavigationexample.R;
import com.example.bottomnavigationexample.databinding.FragmentBmiBinding;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class BMIFragment extends Fragment {

    private static final NumberFormat numberFormat =
            NumberFormat.getNumberInstance();

    private double weight = 0.0; // weight entered by the user
    private double height = 0.0; // height entered by the user
    double bmi;

    private List<Double> bmiArr = new ArrayList<>();
    private TextView weightTextView; // shows the weight value
    private TextView heightTextView; // shows the height value
    private TextView bmiTextView; // shows calculated bmi

    private FragmentBmiBinding binding;

    private LineChart volumeReportChart;
    private Button addToChartButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentBmiBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // get references to programmatically manipulated TextViews
        weightTextView = (TextView) root.findViewById(R.id.weightTextView);
        heightTextView = (TextView) root.findViewById(R.id.heightTextView);
        bmiTextView = (TextView) root.findViewById(R.id.bmiTextView);
        bmiTextView.setText(numberFormat.format(0));
        volumeReportChart = root.findViewById(R.id.reportingChart);
        addToChartButton = root.findViewById(R.id.addBmi);
        addToChartButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (bmi != 0) {
                    bmiArr.add(bmi);
                }
                redrawChart();
            }
        });

        // set amountEditText's TextWatcher
        EditText weightEditText =
                (EditText) root.findViewById(R.id.weightEditText);
        weightEditText.addTextChangedListener(weightEditTextWatcher);

        // set percentSeekBar's OnSeekBarChangeListener
        EditText heightEditText =
                (EditText) root.findViewById(R.id.heightEditText);
        heightEditText.addTextChangedListener(heightEditTextWatcher);

        bmiArr.add(28.5);
        bmiArr.add(28.3);
        bmiArr.add(28.1);
        bmiArr.add(28.0);

        List<Entry> entries = new ArrayList<Entry>();
        for (int i = 0; i < bmiArr.size(); i++) {
            entries.add(new Entry(i, bmiArr.get(i).floatValue()));
        }

        LineDataSet dataSet = new LineDataSet(entries, getString(R.string.bmiLabel)); // add entries to dataset
        dataSet.setColor(Color.BLACK);
        dataSet.setValueTextColor(Color.BLACK);

        LineData lineData = new LineData(dataSet);
        volumeReportChart.setData(lineData);
        volumeReportChart.invalidate(); // refresh

        return root;
    }

    // calculate and display tip and total amounts
    private void calculate() {
        // calculate the height in meters square and bmi
        bmi = weight / Math.pow(height / 100, 2);
        if (Double.isInfinite(bmi) || Double.isNaN(bmi)) {
            bmi = 0.0;
        }
        // display bmi
        bmiTextView.setText(numberFormat.format(bmi));
    }

    private void redrawChart() {
        List<Entry> entries = new ArrayList<Entry>();
        for (int i = 0; i < bmiArr.size(); i++) {
            entries.add(new Entry(i, bmiArr.get(i).floatValue()));
        }

        LineDataSet dataSet = new LineDataSet(entries, getString(R.string.bmiLabel)); // add entries to dataset
        dataSet.setColor(Color.BLACK);
        dataSet.setValueTextColor(Color.BLACK);

        LineData lineData = new LineData(dataSet);
        volumeReportChart.setData(lineData);
        volumeReportChart.invalidate(); // refresh
    }

    // listener object for the EditText's text-changed events
    private final TextWatcher weightEditTextWatcher = new TextWatcher() {
        // called when the user modifies the weight amount
        @Override
        public void onTextChanged(CharSequence s, int start,
                                  int before, int count) {

            try { // get weight amount and display value
                weight = Double.parseDouble(s.toString());
                weightTextView.setText(numberFormat.format(weight));
            } catch (NumberFormatException e) { // if s is empty or non-numeric
                weightTextView.setText("");
                weight = 0.0;
            }

            calculate(); // update the bmi TextView
        }

        @Override
        public void afterTextChanged(Editable s) {
        }

        @Override
        public void beforeTextChanged(
                CharSequence s, int start, int count, int after) {
        }
    };

    // listener object for the EditText's text-changed events
    private final TextWatcher heightEditTextWatcher = new TextWatcher() {
        // called when the user modifies the height amount
        @Override
        public void onTextChanged(CharSequence s, int start,
                                  int before, int count) {

            try { // parsing height value
                height = Double.parseDouble(s.toString());
                heightTextView.setText(numberFormat.format(height));
            } catch (NumberFormatException e) { // if s is empty or non-numeric
                heightTextView.setText("");
                height = 0.0;
            }

            calculate(); // update the bmi textView
        }

        @Override
        public void afterTextChanged(Editable s) {
        }

        @Override
        public void beforeTextChanged(
                CharSequence s, int start, int count, int after) {
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}