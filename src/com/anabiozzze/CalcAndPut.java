package com.anabiozzze;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class CalcAndPut {

    private GetAndCheck gac;
    private Map<Double, int[]> result;
    private String outSource;

    public CalcAndPut(String source, String outSource) {
        this.outSource = outSource;
        gac = new GetAndCheck(source);
        result = new TreeMap<>();
    }

    public CalcAndPut(String source) {
        System.out.println ("Input both parameters and try again.");
    }

    public CalcAndPut() {
        System.out.println ("Input both parameters and try again.");
    }


    public void runCalc() {
        isosFind();
        showResult();
        writeFile(outSource);
    }

    //takes six coordinates from the GetAndCheck result list, calculates the length of the sides, searches for equal
    private void isosFind() {
        double dist1 = 0, dist2 = 0, dist3 = 0;

        for (int i =gac.result.size()-1; i>=0; i--) {
            System.out.println("\n" + "TRIANGLE №" + i);
            int[] arr = gac.result.get(i);
            int x1 =arr[0], y1=arr[1], x2=arr[2], y2=arr[3], x3=arr[4], y3=arr[5];

            // calc the length of the sides
            dist1 = Math.sqrt(Math.pow((x1-x2), 2) + Math.pow((y1-y2), 2));
            dist2 = Math.sqrt(Math.pow((x2-x3), 2) + Math.pow((y2-y3), 2));
            dist3 = Math.sqrt(Math.pow((x3-x1), 2) + Math.pow((y3-y1), 2));

            System.out.println("Sides: " + dist1 + "; " + dist2 + "; " + dist3);

            //if there are no equal sides - delete the current coordinate set from the list
            if ((dist1 != dist2) && (dist2!=dist3) && (dist3!=dist1)) {
                gac.result.remove(i);
                System.out.println("№ " + i + " not isosceles and been removed.");
                System.out.println("Triangles left: " + gac.result.size());

            }

            //there are equal sides - transfer the length of the sides and the base for calculating the area
            if (dist1 == dist2) result.put(calcArea(dist1, dist3), arr);
            if (dist2 == dist3) result.put(calcArea(dist2, dist1), arr);
            if (dist3 == dist1) result.put(calcArea(dist3, dist2), arr);

        }
    }

    //calculates the area of isosceles triangles, the sides of which takes from isosFind()
    private double calcArea(double a, double b) {
        double h = Math.sqrt(a*a - (b*b)/4); // h = sqrt(a^2-(b^2)/4)
        return 0.5*b*h; // S = 1\2bh
    }

    //displays the areas and coordinates of all isosceles triangles, found by isosFind()
    private void showResult() {
        System.out.println("\n" + result.size() + " isosceles triangles were found:".toUpperCase());
        for( Map.Entry<Double, int[]> e : result.entrySet()) {
            System.out.println("Area: " + e.getKey() + " Coordinates: " +
                    e.getValue()[0] + "," + e.getValue()[1] + "," + e.getValue()[2] +
                    "," + e.getValue()[3] + "," + e.getValue()[4] + "," + e.getValue()[5]);
        }
    }

    //writing the coordinates of the bigger area isosceles triangle in the new file
    private void writeFile(String outSource) {
        try {
            FileWriter writer = new FileWriter(outSource);
            int lastIndex = result.size()-1;

            //since TreeMap automatically sorts the collection and keys is a areas - just take last (biggest) element
            //If no isosceles triangles are found, the array will remain empty
            int[] arr = (lastIndex>0) ? (int[])result.values().toArray()[lastIndex] : new int[0];
            for (int i:arr) {writer.write (i + ";");}

            writer.flush();
            writer.close();

        } catch (IOException e) {
            System.out.println("\nTrouble with second parameter format: "); e.printStackTrace();
        }
    }
}