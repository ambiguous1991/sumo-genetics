package algorithms;

import genetics.Specimen;

import java.util.ArrayList;

public class MutationAlgorithm {

    private MutationAlgorithm(){}

    public static void mutate(ArrayList<Specimen> population, double mutationProbability){
        for (Specimen specimen: population){
            specimen.mutate(mutationProbability);
        }
    }
}
