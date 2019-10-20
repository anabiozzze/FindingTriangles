package com.anabiozzze;

public class Main {

    public static void main(String[] args) {
        try {
            CalcAndPut cap = new CalcAndPut (args[0], args[1]);
            cap.runCalc();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println ("Input both parameters and try again.");
        }

    }
}
