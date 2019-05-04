package com.company;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Random;

public class SwapSequence {

    int[] arr1;
    int[] arr2;
    int size;

    public SwapSequence(int size) {
        this.size = 0;
        arr1 = new int[size];
        arr2 = new int[size];
    }

    public void adjustForFactor(double factor){
        double value = new Random().nextDouble();
        if (value > factor) { //value -> probability that all operators are included in a sequence
            int numberOfDeletions = size/10 + 1;
            for (int i = 0; i < numberOfDeletions; i++)
                removeSwapOperator();
        }
    }

    public void addSwapOperator(int index1, int index2) {
        arr1[size] = index1;
        arr2[size] = index2;
        size++;
        if (size > arr1.length) {
            int[] newArr1 = new int[arr1.length];
            arr1 = ArrayUtils.addAll(arr1, newArr1);
            int[] newArr2 = new int[arr2.length];
            arr2 = ArrayUtils.addAll(arr2, newArr2);
        }
    }

    public void removeSwapOperator() {
        if (size > 0) {
            arr1[size - 1] = -1;
            arr2[size - 1] = -1;
            size--;
        }
    }

    int[] getArr1() {
        return arr1;
    }

    int[] getArr2() {
        return arr2;
    }

    public void addSwapSequence(SwapSequence sequence){
        if (sequence.size > 0) {
            arr1 = ArrayUtils.insert(size, arr1, sequence.getArr1());
            arr2 = ArrayUtils.insert(size, arr2, sequence.getArr2());
            size += sequence.size;
        }
        /*if (sequence.size > 0) {
            System.out.println(ArrayUtils.toString(arr1));
            System.out.println(ArrayUtils.toString(arr2));
            System.out.println(size);
            System.out.println(ArrayUtils.toString(sequence.getArr1()));
            System.out.println(ArrayUtils.toString(sequence.getArr2()));
            arr1 = ArrayUtils.insert(size, arr1, sequence.getArr1());
            arr2 = ArrayUtils.insert(size, arr2, sequence.getArr2());
            size += sequence.size;
            System.out.println(ArrayUtils.toString(arr1));
            System.out.println(ArrayUtils.toString(arr2));
        }*/
    }

    public int getSize() {
        return size;
    }

    public int[] swap(int[] array, int index){
        if (index < size)
            ArrayUtils.swap(array, arr1[index], arr2[index]);
        else
            throw new RuntimeException();
        return array;
    }

    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SS: ");
        for (int i = 0; i < size; i++) {
            stringBuilder.append("(");
            stringBuilder.append(arr1[i]);
            stringBuilder.append(", ");
            stringBuilder.append(arr2[i]);
            stringBuilder.append(")");
            if (i < size - 1)
                stringBuilder.append(", ");
        }
        return stringBuilder.toString();
    }
}
