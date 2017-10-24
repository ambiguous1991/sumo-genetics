package algorithms;

import genetics.Gene;
import genetics.Specimen;

import java.util.ArrayList;
import java.util.Random;

public class EqualSplicePopulationAlgorithm {
    private static Random random = new Random();

    protected EqualSplicePopulationAlgorithm(){
    }

    //TODO Implement
    public static ArrayList<Specimen> splice(ArrayList<Specimen> population, double spliceProbability){
        ArrayList<Specimen> product = new ArrayList<>();

        int numberOfIterations = population.size();

        for (int i=0; i<numberOfIterations; i+=2){
            Specimen a, b;

            int aSelection, bSelection;

            aSelection = (int) (random.nextDouble()*population.size());
            if(aSelection==population.size())
                aSelection-=1;
            bSelection = (int) (random.nextDouble()*population.size());
            if(bSelection==population.size())
                bSelection-=1;

            while(aSelection==bSelection)
                bSelection = (int) (random.nextDouble()*population.size());

            a = population.get(aSelection);
            b = population.get(bSelection);

            population.remove(a);
            population.remove(b);

            //todo spliceprob

            if(random.nextDouble()<spliceProbability) {
                splice(a, b, product);
            }
            else{
                product.add(a);
                product.add(b);
            }
        }
        return product;
    }

    public static void splice(Specimen a, Specimen b, ArrayList<Specimen> product){
        ArrayList<Gene> productA = new ArrayList<>();
        ArrayList<Gene> productB = new ArrayList<>();

        for (int i=0; i<a.getGenome().size();i++){
            if(i%2==0){
                productA.add(b.getGenome().get(i));
                productB.add(a.getGenome().get(i));
            }
            else{
                productA.add(a.getGenome().get(i));
                productB.add(b.getGenome().get(i));
            }
        }

        Specimen specimenA = new Specimen();
        specimenA.setGenome(productA);

        Specimen specimenB = new Specimen();
        specimenB.setGenome(productB);

        product.add(specimenA);
        product.add(specimenB);
    }
}
