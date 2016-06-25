package com.example.dell.myapplication;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;

public class Drawable extends GradientDrawable {

    public Drawable(String shape, int size) {

        super(Orientation.BOTTOM_TOP,new int[]{Color.BLACK, Color.BLUE, Color.BLUE});
        if(shape== "square") {
            setSize(size, size);
            setShape(GradientDrawable.RECTANGLE);
        }

        else if(shape== "rectangle") {
            setSize(size, (size/2));
            setShape(GradientDrawable.RECTANGLE);
        }

        else if(shape== "circle") {
            setSize(size, size);
            setShape(GradientDrawable.OVAL);
        }

    }

}
