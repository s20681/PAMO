package com.example.bottomnavigationexample.ui.calories;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.bottomnavigationexample.R;
import com.example.bottomnavigationexample.databinding.FragmentCaloriesBinding;

public class CaloriesFragment extends Fragment {

    private FragmentCaloriesBinding binding;
    private View root;
    private double weight = 0.0;
    private double height = 0.15;
    private int age = 0;

    private TextView heightTextView;
    private TextView weightTextView;
    private TextView ageTextView;
    private TextView totalTextView;
    RadioGroup radioGroup;
    RadioButton radioButton1;
    RadioButton radioButton2;
    TextView textView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CaloriesViewModel caloriesViewModel =
                new ViewModelProvider(this).get(CaloriesViewModel.class);

        binding = FragmentCaloriesBinding.inflate(inflater, container, false);
        root = binding.getRoot();

//looking up TextViews in layout
        radioGroup = root.findViewById(R.id.radioGroup);
        weightTextView = (TextView) root.findViewById(R.id.weightTextView);
        heightTextView = (TextView) root.findViewById(R.id.heightTextView);
        ageTextView = (TextView) root.findViewById(R.id.ageTextView);
        totalTextView = (TextView) root.findViewById(R.id.totalTextView);
        totalTextView.setText(String.format(0 + ""));

// setting TextWatchers for both weight and height fields
        EditText weightEditText =
                (EditText) root.findViewById(R.id.weightEditText);
        weightEditText.addTextChangedListener(weightEditTextWatcher);
        EditText heightEditText =
                (EditText) root.findViewById(R.id.heightEditText);
        heightEditText.addTextChangedListener(heightEditTextWatcher);

        EditText ageEditText =
                (EditText) root.findViewById(R.id.ageEditText);
        ageEditText.addTextChangedListener(ageEditTextWatcher);


        radioGroup = root.findViewById(R.id.radioGroup);
        textView = root.findViewById(R.id.answerHintTextView);
        Button buttonApply = root.findViewById(R.id.button_apply);
        buttonApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton1 = root.findViewById(radioId);

                textView.setText("Your choice: " + radioButton1.getText());
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int radioId = i;
                onRadioButtonClickedCalculate();
            }
        });

        return root;
    }


    //method calculate, calculating BMI, setting totalTextView as result
    private void calculate() {
        double total = weight / (height * height);
        totalTextView.setText(String.format(total + ""));
    }


    // listeners object for TextViews weight and height text-changed events
    private final TextWatcher weightEditTextWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start,
                                  int before, int count) {

            try {
                weight = Double.parseDouble(s.toString());
                weightTextView.setText(String.format(weight + ""));

            } catch (NumberFormatException e) {
                weightTextView.setText("");
                weight = 0.0;

            }
//recalculate on change
            calculate();
            onRadioButtonClickedCalculate();
        }

        @Override
        public void afterTextChanged(Editable s) {
        }

        @Override
        public void beforeTextChanged(
                CharSequence s, int start, int count, int after) {
        }
    };
    private final TextWatcher heightEditTextWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start,
                                  int before, int count) {

            try {
                height = Double.parseDouble(s.toString());
                heightTextView.setText(String.format(height + ""));
            } catch (NumberFormatException e) {
                heightTextView.setText("");
                height = 0.0;
            }
//recalculate on change
//            calculate();
            onRadioButtonClickedCalculate();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private final TextWatcher ageEditTextWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start,
                                  int before, int count) {

            try {
                age = (int) (Integer.parseInt(s.toString()));
                ageTextView.setText(String.format(age + ""));
            } catch (NumberFormatException e) {
                age = 0;
            }
//recalculate on change
//            calculate();
            onRadioButtonClickedCalculate();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public void onRadioButtonClickedCalculate() {
        int radioId = radioGroup.getCheckedRadioButtonId();

        radioButton1 = root.findViewById(R.id.rid1);
        radioButton2 = root.findViewById(R.id.rid2);

        // Is the male button now checked?
        boolean checked = radioButton1.isChecked();

        double total = 0.00;

        if (checked) {
            total = 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age);
        } else {
            total = 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age);
        }

        totalTextView.setText(String.format(total + ""));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}