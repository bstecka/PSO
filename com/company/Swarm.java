package com.company;

import java.util.Random;

public class Swarm {

    private Particle[] swarm;
    private int[][] costMatrix;
    private Particle gbest;

    public Swarm(int swarmSize, int[][] costMatrix) {
        initialize(swarmSize, costMatrix);
        this.costMatrix = costMatrix;
    }

    private void initialize(int swarmSize, int[][] costMatrix) {
        Particle[] arr = new Particle[swarmSize];
        for (int i = 0; i < swarmSize; i++) {
            arr[i] = new Particle(costMatrix);
        }
        this.swarm = arr;
    }

    public Particle findBest() {
        Particle best = swarm[0];
        for (Particle p : swarm) {
            if (p.getEvaluation() < best.getEvaluation())
                best = p;
        }
        return best;
    }

    public Particle execute(int numberOfIterations) {
        //System.out.println("GBEST: " + gbest);
        Random random = new Random();
        for (int i = 0; i < numberOfIterations; i++) {
            for (Particle p : swarm) {
                p.updateParticle(random.nextDouble(), random.nextDouble(), getGbest());
                Particle pbest = p.getPbest();
                //System.out.println("gbest: " + gbest.getEvaluation());
                if (pbest.getEvaluation() < gbest.getEvaluation()) {
                    //System.out.println("old g: " + gbest.getEvaluation() + ", new g: " + pbest.getEvaluation());
                    gbest = pbest;
                    //System.out.println(gbest.getEvaluation());
                }
            }
        }
        return gbest;
    }

    public Particle getGbest() {
        if (gbest == null) {
            int min = Integer.MAX_VALUE;
            for (Particle p : swarm) {
                if (p.getEvaluation() < min) {
                    gbest = p;
                    min = p.getEvaluation();
                }
            }
        }
        return gbest;
    }
}
