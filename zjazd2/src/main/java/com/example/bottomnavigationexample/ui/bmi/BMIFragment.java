package com.example.bottomnavigationexample.ui.bmi;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.bottomnavigationexample.R;
import com.example.bottomnavigationexample.databinding.FragmentBmiBinding;

import java.text.NumberFormat;

public class BMIFragment extends Fragment {

    private static final NumberFormat numberFormat =
            NumberFormat.getNumberInstance();

    private double weight = 0.0; // weight entered by the user
    private double height = 0.0; // height entered by the user
    private TextView weightTextView; // shows the weight value
    private TextView heightTextView; // shows the height value
    private TextView bmiTextView; // shows calculated bmi

    private FragmentBmiBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BMIViewModel BMIViewModel =
                new ViewModelProvider(this).get(BMIViewModel.class);

        binding = FragmentBmiBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // get references to programmatically manipulated TextViews
        weightTextView = (TextView) root.findViewById(R.id.weightTextView);
        heightTextView = (TextView) root.findViewById(R.id.heightTextView);
        bmiTextView = (TextView) root.findViewById(R.id.bmiTextView);
        bmiTextView.setText(numberFormat.format(0));

        // set amountEditText's TextWatcher
        EditText weightEditText =
                (EditText) root.findViewById(R.id.weightEditText);
        weightEditText.addTextChangedListener(weightEditTextWatcher);

        // set percentSeekBar's OnSeekBarChangeListener
        EditText heightEditText =
                (EditText) root.findViewById(R.id.heightEditText);
        heightEditText.addTextChangedListener(heightEditTextWatcher);

        return root;
    }

    // calculate and display tip and total amounts
    private void calculate() {
        // calculate the height in meters square and bmi
        double bmi = weight / Math.pow(height / 100, 2);
        if(Double.isInfinite(bmi) || Double.isNaN(bmi)){
            bmi = 0.0;
        }
        // display bmi
        bmiTextView.setText(numberFormat.format(bmi));
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
            }
            catch (NumberFormatException e) { // if s is empty or non-numeric
                weightTextView.setText("");
                weight = 0.0;
            }

            calculate(); // update the bmi TextView
        }

        @Override
        public void afterTextChanged(Editable s) { }

        @Override
        public void beforeTextChanged(
                CharSequence s, int start, int count, int after) { }
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
            }
            catch (NumberFormatException e) { // if s is empty or non-numeric
                heightTextView.setText("");
                height = 0.0;
            }

            calculate(); // update the bmi textView
        }

        @Override
        public void afterTextChanged(Editable s) { }

        @Override
        public void beforeTextChanged(
                CharSequence s, int start, int count, int after) { }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}