package algorithms;

import genetics.Specimen;
import log.Log;

import java.util.ArrayList;
import java.util.Random;

public class TournamentSelectionAlgorithm {
    private static Random random = new Random();
    private TournamentSelectionAlgorithm(){

    }

    public static ArrayList<Specimen> select(ArrayList<Specimen> population){
        ArrayList<Specimen> product = new ArrayList<>();
        for (int i=0; i<population.size(); i++){
            Specimen a, b;

            int aSelection, bSelection;

            aSelection = (int) (random.nextDouble()*population.size());
            if(aSelection==population.size())
                aSelection-=1;
            bSelection = (int) (random.nextDouble()*population.size());
            if(bSelection==population.size())
                bSelection-=1;

            a = population.get(aSelection);
            b = population.get(bSelection);

            if(a.getFitness()>b.getFitness()){
                Specimen clone = a.clone();
                clone.setChanged(false);
                product.add(clone);
            }
            else{
                Specimen clone = b.clone();
                clone.setChanged(false);
                product.add(clone);
            }
        }
        return product;
    }
}
