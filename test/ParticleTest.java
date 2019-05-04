package test;

import com.company.Particle;
import com.company.SwapSequence;
import org.junit.Assert;

import java.util.Arrays;
import java.util.Random;

class ParticleTest {

    @org.junit.jupiter.api.Test
    void getEvaluation() {
        int arr1[] = {3, 4, 5, 2, 1, 6};
        int arr2[] = {3, 1, 5, 6, 2, 4};
        int costMatrix[][] = new int[5][5];
        Particle E = new Particle(arr1, costMatrix);
        Particle A = new Particle(arr2, costMatrix);
        SwapSequence result = E.subtract(A);
        System.out.println(result);
        System.out.println(E);
        E.applySwapSequence(result);
        System.out.println(E);
        System.out.println(A);
    }
}