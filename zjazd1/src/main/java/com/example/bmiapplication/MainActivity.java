package com.example.bmiapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable; // for EditText event handling
import android.text.TextWatcher; // EditText listener
import android.widget.EditText; // for input
import android.widget.TextView; // for displaying text

import java.text.NumberFormat; // for formatting

public class MainActivity extends AppCompatActivity {

    private static final NumberFormat numberFormat =
            NumberFormat.getNumberInstance();

    private double weight = 0.0; // weight entered by the user
    private double height = 0.0; // height entered by the user
    private TextView weightTextView; // shows the weight value
    private TextView heightTextView; // shows the height value
    private TextView bmiTextView; // shows calculated bmi

    // called when the activity is first created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // call superclass onCreate
        setContentView(R.layout.activity_main); // inflate the GUI

        // get references to programmatically manipulated TextViews
        weightTextView = (TextView) findViewById(R.id.weightTextView);
        heightTextView = (TextView) findViewById(R.id.heightTextView);
        bmiTextView = (TextView) findViewById(R.id.bmiTextView);
        bmiTextView.setText(numberFormat.format(0));

        // set amountEditText's TextWatcher
        EditText weightEditText =
                (EditText) findViewById(R.id.weightEditText);
        weightEditText.addTextChangedListener(weightEditTextWatcher);

        // set percentSeekBar's OnSeekBarChangeListener
        EditText heightEditText =
                (EditText) findViewById(R.id.heightEditText);
        heightEditText.addTextChangedListener(heightEditTextWatcher);
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
}


/*************************************************************************
 * (C) Copyright 1992-2016 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************************************************************/
