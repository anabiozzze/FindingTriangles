package com.anabiozzze;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetAndCheck {

    private String source; // file with coordinates
    private String coordinates; // string for storing coordinates during calculation
    protected List<int[]> result = new ArrayList<>(); // final list with correct coordinates

    public GetAndCheck(String source) {
        this.source = source;
        readFile();
        splitCheck();
        outCheck();
    }

    //get coordinates from file and put them into String
    private void readFile() {
        try {
            FileReader reader = new FileReader(source);
            char[] buf = new char[256];
            int c;
            while ((c = reader.read(buf))>0) {
                if (c < 256) {
                    buf = Arrays.copyOf(buf, c);
                }
                coordinates = new String(buf);
            }

            reader.close();

        } catch (IOException e) {
            System.out.println("\nTrouble with second parameter format: "); e.printStackTrace();
        }
    }

    //split String to lines
    private void splitCheck() {
        String[] res = coordinates.split("\n");

        for (String str: res) {
            getInts(str);
        }
    }

    //get coordinates from a string and check their number and format
    private void getInts(String str) {
        str.trim();
        String[] arr = null;

        //the separator in line may be a space, ';' or both
        if (str.contains(";")) {
            str = str.replaceAll("\\s+","");
            arr = str.split(";");

        } else if (str.contains (" ")) {
            arr = str.split("\\s");

        } else {
            System.out.println("Format of coordinates is incorrect in line: " + str);
            return;
        }


        //add coordinates to result list
        int[] res = new int[6];
        for (int i =0; i<arr.length; i++) {
            try {
                res[i] = Integer.parseInt(arr[i]);

            //catch two types of errors: too much coordinates in line or incorrect format (letters, commas etc)
            } catch (NumberFormatException e) {
                System.out.println("Format of coordinates is incorrect in line: " + str);
                return;
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Number of coordinates is incorrect in line: " + str);
                return;
            }
        }

        result.add(res);
    }


    //checking result list
    private void outCheck() {
        System.out.println();
        for (int[] arr : result) {
            for (int a : arr) {
                System.out.print(a + ", ");
            }
            System.out.println();
        }
    }
}
