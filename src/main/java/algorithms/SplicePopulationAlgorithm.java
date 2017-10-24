package algorithms;

import genetics.Gene;
import genetics.Specimen;
import log.Log;

import java.util.ArrayList;
import java.util.Random;

public class SplicePopulationAlgorithm {
    private static Random random = new Random();

    protected SplicePopulationAlgorithm(){
    }

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
        int splicePoint = (int) Math.round(random.nextDouble() * (double)a.getGenome().size());

        if(splicePoint==0||splicePoint==a.getGenome().size()){
            a = a.clone();
            b = b.clone();
            a.setChanged(false);
            b.setChanged(false);
            product.add(a);
            product.add(b);
        }
        else{
            ArrayList<Gene> genesTempA1 = new ArrayList<>();
            ArrayList<Gene> genesTempA2 = new ArrayList<>();
            ArrayList<Gene> genesTempB1 = new ArrayList<>();
            ArrayList<Gene> genesTempB2 = new ArrayList<>();
            genesTempA1.addAll(a.getGenome().subList(0, splicePoint));
            genesTempA2.addAll(a.getGenome().subList(splicePoint, a.getGenome().size()));
            genesTempB1.addAll(b.getGenome().subList(0, splicePoint));
            genesTempB2.addAll(b.getGenome().subList(splicePoint, b.getGenome().size()));

            ArrayList<Gene> productA = new ArrayList<>();
            ArrayList<Gene> productB = new ArrayList<>();

            productA.addAll(genesTempA1);
            productA.addAll(genesTempB2);

            productB.addAll(genesTempB1);
            productB.addAll(genesTempA2);

            Specimen specimenA = new Specimen();
            specimenA.setGenome(productA);

            Specimen specimenB = new Specimen();
            specimenB.setGenome(productB);

            product.add(specimenA);
            product.add(specimenB);
        }
    }
}
