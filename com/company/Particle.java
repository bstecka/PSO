package com.company;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;

public class Particle {

    private int[] permutation;
    private int[][] costMatrix;
    private SwapSequence velocity;
    private Particle pbest;

    public Particle(int[][] costMatrix) {
        initialize(costMatrix.length);
        this.costMatrix = costMatrix;
        velocity = new SwapSequence(permutation.length);
        pbest = new Particle(Arrays.copyOf(permutation, permutation.length), costMatrix);
    }

    public Particle(int[] permutation, int[][] costMatrix){
        this.permutation = permutation;
        this.costMatrix = costMatrix;
        velocity = new SwapSequence(permutation.length);
    }

    public int[] getPermutation() {
        return permutation;
    }

    public Particle getPbest() {
        if (pbest == null)
            pbest = new Particle(Arrays.copyOf(permutation, permutation.length), costMatrix);
        return pbest;
    }

    private void initialize(int numberOfGenes) {
        int[] arr = new int[numberOfGenes];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i+1;
        }
        ArrayUtils.shuffle(arr);
        this.permutation = arr;
    }

    public void applySwapSequence(SwapSequence swapSequence) {
        for (int i = 0; i < swapSequence.getSize(); i++) {
            permutation = swapSequence.swap(permutation, i);
        }
    }

    public void updateParticle(double alpha, double beta, Particle gbest) {
        //System.out.println("PBEST: " + pbest);
        //System.out.println(this);
        //System.out.println(velocity);
        updateVelocity(alpha, beta, pbest, gbest);
        //System.out.println(velocity);
        applySwapSequence(velocity);
        //System.out.println(velocity);
        //System.out.println("PBEST: " + pbest);
        //System.out.println(this);
        if (this.getEvaluation() < pbest.getEvaluation()) {
            //System.out.println("old p: " + pbest.getEvaluation() + ", new p: " + this.getEvaluation());
            pbest = new Particle(Arrays.copyOf(permutation, permutation.length), costMatrix);
        }
    }

    private void updateVelocity(double alpha, double beta, Particle pbest, Particle gbest) {
        SwapSequence alphaSequence = pbest.subtract(this);
        alphaSequence.adjustForFactor(alpha);
        //System.out.println(this);
        //System.out.println(gbest);
        SwapSequence betaSequence = gbest.subtract(this);
        //System.out.println(betaSequence);
        betaSequence.adjustForFactor(beta);
        //System.out.println(betaSequence);
        //System.out.println("V: " + velocity);
        velocity.addSwapSequence(alphaSequence);
        velocity.addSwapSequence(betaSequence);
        //System.out.println("V: " + velocity);
    }

    int findDifferingIndexFromStart(Particle secondParticle){
        int [] secondPermutation = secondParticle.getPermutation();
        for (int i = 0; i < permutation.length; i++) {
            if (permutation[i] != secondPermutation[i])
                return i;
        }
        return -1;
    }

    int findDifferingIndexFromEnd(Particle secondParticle){
        int [] secondPermutation = secondParticle.getPermutation();
        for (int i = permutation.length - 1; i >= 0; i--) {
            if (permutation[i] != secondPermutation[i])
                return i;
        }
        return -1;
    }

    int getElement(int index) {
        return permutation[index];
    }

    int indexOf(int element) {
        return ArrayUtils.indexOf(permutation, element);
    }

    void swap(int index1, int index2) {
        ArrayUtils.swap(permutation, index1, index2);
    }

    public SwapSequence subtract(Particle secondParticle) {
        SwapSequence swapSequence = new SwapSequence(permutation.length);
        Particle transformedParticle = new Particle(Arrays.copyOf(permutation, permutation.length), costMatrix);
        while (!transformedParticle.equals(secondParticle)) {
            int differingIndex = transformedParticle.findDifferingIndexFromStart(secondParticle);
            int element = secondParticle.getElement(differingIndex);
            int indexForSwap = transformedParticle.indexOf(element);
            if (indexForSwap == -1)
                throw new RuntimeException();
            swapSequence.addSwapOperator(differingIndex, indexForSwap);
            //System.out.println(differingIndex + " " + indexForSwap);
            transformedParticle.swap(differingIndex, indexForSwap);
        }
        //System.out.println(transformedParticle.toString());
        //System.out.println(secondParticle.toString());
        return swapSequence;
    }

    public SwapSequence subtract2(Particle secondParticle) {
        SwapSequence swapSequence = new SwapSequence(permutation.length);
        Particle transformedParticle = new Particle(Arrays.copyOf(permutation, permutation.length), costMatrix);
        System.out.println(transformedParticle.toString());
        int count = 0, differingIndex;
        while (!transformedParticle.equals(secondParticle)) {
            if (count%2 == 0)
                differingIndex = transformedParticle.findDifferingIndexFromStart(secondParticle);
            else
                differingIndex = transformedParticle.findDifferingIndexFromEnd(secondParticle);
            System.out.println(differingIndex);
            count++;
            int element = secondParticle.getElement(differingIndex);
            System.out.println("ELEMENT " + differingIndex);
            int indexForSwap = transformedParticle.indexOf(element);
            if (indexForSwap == -1)
                throw new RuntimeException();
            swapSequence.addSwapOperator(differingIndex, indexForSwap);
            //System.out.println(differingIndex + " " + indexForSwap);
            transformedParticle.swap(differingIndex, indexForSwap);
            System.out.println(transformedParticle.toString());
        }
        System.out.println(transformedParticle.toString());
        System.out.println(secondParticle.toString());
        return swapSequence;
    }

    public int getEvaluation() {
        int value = 0;
        for (int i = 0; i < permutation.length; i++) {
            int source = permutation[i];
            int dest = (i+1) < permutation.length ? this.permutation[i+1] : permutation[0];
            int cost = costMatrix[source-1][dest-1];
            if (cost != -1)
                value += cost;
        }
        return value;
    }

    public double getInverseEvaluation() {
        double eval = (double) getEvaluation();
        return 1/eval;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Particle) {
            return Arrays.equals(permutation, ((Particle) o).getPermutation());
        }
        else
            return false;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + Arrays.hashCode(permutation);
        return result;
    }

    public String toString(){
        return "Particle " + Arrays.toString(permutation);
    }
}
