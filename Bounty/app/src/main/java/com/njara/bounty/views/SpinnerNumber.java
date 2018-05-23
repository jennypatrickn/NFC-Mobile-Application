package com.njara.bounty.views;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.njara.bounty.R;


/**
 * Created by NJARA on 12/04/2016.
 */
public class SpinnerNumber {

    EditText valueTextView;
    ImageView minusBtn;
    ImageView plusBtn;

    private int minValue=0;
    private int maxValue=Integer.MAX_VALUE;

   public  SpinnerNumber(){

    }
    public SpinnerNumber(LinearLayout inputLayout){
        try {
            init(inputLayout);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    int d,b;
    public SpinnerNumber(LinearLayout inputLayout,int d,int b){
        try {
            init(inputLayout);
            this.d=d;
            this.b=b;

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    private void init(LinearLayout inputLayout) throws NumberFormatException{


        valueTextView=(EditText)inputLayout.findViewById(R.id.valueTextView);
        minusBtn=(ImageView)inputLayout.findViewById(R.id.minusButton);
        plusBtn=(ImageView)inputLayout.findViewById(R.id.plusButton);
        minusBtn.setClickable(true);
        plusBtn.setClickable(true);
        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementValue();
                valueTextView.setSelection(valueTextView.getText().toString().length());
            }
        });
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementValue();
                valueTextView.setSelection(valueTextView.getText().toString().length());
            }
        });
        valueTextView.setSelection(valueTextView.getText().toString().length());
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }
    public int getValue() throws NumberFormatException{
        String value=valueTextView.getText().toString();
        if(value.length()>0)
        return Integer.valueOf(value);
        else return  Integer.valueOf(1);

    }
    public void setValue(int newValue){
        int value = newValue;
        if(newValue < minValue) {
            value = minValue;
        } else if (newValue > maxValue) {
            value = maxValue;
        }

        valueTextView.setText(String.valueOf(value));
    }
    private void incrementValue() throws NumberFormatException{
        int currentVal = getValue();
        if(currentVal < maxValue) {
            setValue(currentVal+1);
        }
    }

    private void decrementValue() throws NumberFormatException{
        int currentVal = getValue();
        if(currentVal >= minValue) {
            setValue(currentVal-1);
        }
    }

    public EditText getValueTextView() {
        return valueTextView;
    }

    public void setValueTextView(EditText valueTextView) {
        this.valueTextView = valueTextView;
    }
}
