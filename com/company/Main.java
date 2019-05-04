package com.company;

public class Main {

    public static void main(String[] args) {
        TSPReader reader = new TSPReader("C:\\Users\\barbara\\Desktop\\PSO\\data\\dj38.tsp");
        int[][] costMatrix = reader.getCostMatrix();
        int swarmSize = 50;
        int numberOfIterations = 200;
        Swarm swarm = new Swarm(swarmSize, costMatrix);
        System.out.println(swarm.findBest().getEvaluation());
        Particle best = swarm.execute(numberOfIterations);
        System.out.println(best.getEvaluation());
    }
}
