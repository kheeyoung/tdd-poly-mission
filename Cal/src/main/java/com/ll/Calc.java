package com.ll;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class Calc {
    public int run(String input) {

        ArrayList<String> parts = getParts(input);


        //괄호 없애기
        for (int i = 0; i < parts.size(); i++) {
            String str = parts.get(i);
            if (str.startsWith("(")) {
                String inner = str.substring(1, str.length() - 1);

                int result = run(inner);
                parts.set(i, String.valueOf(result));
            }
            else if (str.startsWith("-") && str.length()>2 && str.charAt(1)=='(') {

                String inner = str.substring(2, str.length() - 1);

                int result = run(inner);
                parts.set(i, "-"+result);
            }
        }


        // *,/

        ArrayList<String> arr = new ArrayList<>();
        arr.add(parts.get(0)); // 첫 번째 숫자



        for (int i = 1; i < parts.size(); i++) {
            String str = parts.get(i);
            if (str.equals("*") || str.equals("/")) {
                // 직전 값
                int left = Integer.parseInt(arr.remove(arr.size() - 1));
                // 다음 값
                int right = Integer.parseInt(parts.get(i+1));
                int result = cul(left, right, str);
                // 계산 결과 저장
                arr.add(String.valueOf(result));
                i++;
            } else {
                arr.add(str);
            }
        }


        int result = Integer.parseInt(arr.get(0));
        for(int i=1; i<arr.size()-1; i+=2) {

            result=cul(result, Integer.parseInt(arr.get(i+1)), arr.get(i));
        }

        return result;

    }




    private ArrayList<String> getParts(String input) {
        ArrayList<String> parts = new ArrayList<>();
        String p = "";
        int n = 0;
        for (int i = 0; i < input.length(); i++) {
            String s = String.valueOf(input.charAt(i));
            if (n != 0) {
                if (s.equals(")")) {
                    n--;
                } else if (s.equals("(")) {
                    n++;
                }
                p += s;
                if (n == 0) {
                    parts.add(p);
                    p = "";
                }
            } else {
                switch (s) {
                    case " ":
                        if (!p.isEmpty()) {
                            parts.add(p);
                            p = "";
                        }
                        break;
                    case "+":
                    case "*":
                    case "/":
                        parts.add(s);
                        break;

                    case "-":
                        if(input.charAt(i+1)==' ')parts.add(s);
                        else{
                            p += s;
                        }
                        break;
                    case "(":
                        p += s;
                        n++;
                        break;

                    default:
                        p += s;
                        break;
                }
            }
        }
        if (!p.isEmpty()) {
            parts.add(p);
        }
        return parts;
    }


    private int cul(int a, int b, String op) {
        switch (op) {
            case "+":
                return a+b;
            case "-":
                return a-b;
            case "*":
                return a*b;
            case "/":
                return a/b;
            default:
                throw new IllegalArgumentException("알 수 없는 연산자입니다: " + op);
        }

    }
}
