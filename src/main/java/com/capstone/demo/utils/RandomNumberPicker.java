package com.capstone.demo.utils;

public class RandomNumberPicker {

    /* 0 부터 endIdx 까지의 정수를 반환 */
    public static int generate(int endIdx) {
        return (int) (Math.random() * endIdx);
    }
}