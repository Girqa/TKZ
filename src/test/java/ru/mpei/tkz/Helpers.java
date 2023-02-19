package ru.mpei.tkz;

import org.apache.commons.math3.complex.Complex;

public class Helpers {

    public static String phForm(Complex v) {
        Complex val = round(v);
        return Math.pow(Math.pow(val.getReal(), 2) + Math.pow(val.getImaginary(), 2), 0.5) + "∠" + Math.toDegrees(val.getArgument());
    }

    public static Complex alForm(double a, double phase) {
        return Complex.ZERO; // TODO
    }
    public static Complex round(Complex val) {
        double meaningSing = 1000.;
        double real = Math.round(val.getReal() * meaningSing) / meaningSing;
        double imag = Math.round(val.getImaginary() * meaningSing) / meaningSing;
        return new Complex(real, imag);
    }
}
