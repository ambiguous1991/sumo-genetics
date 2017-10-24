package algorithms;

import genetics.Gene;
import genetics.Specimen;

import java.util.ArrayList;
import java.util.Random;

public class TwoPointSplicePopulationAlgorithm {
    private static Random random = new Random();
    private TwoPointSplicePopulationAlgorithm() {
    }

    public static ArrayList<Specimen> splice(ArrayList<Specimen> population, double spliceProbability) {
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
        int splicePoint1 = (int) Math.round(random.nextDouble() * (double)a.getGenome().size()/2);
        while(splicePoint1==0){
            splicePoint1= (int) Math.round(random.nextDouble() * (double)a.getGenome().size()/2);
        }
        int splicePoint2 = (int) Math.round(random.nextDouble() * (double)(a.getGenome().size()-splicePoint1));
        splicePoint2 +=splicePoint1;
        while(splicePoint2==splicePoint1||splicePoint2==a.getGenome().size()){
            splicePoint2 = (int) Math.round(random.nextDouble() * (double)(a.getGenome().size()-splicePoint1));
            splicePoint2 +=splicePoint1;
        }

        System.out.println("Genome size is "+a.getGenome().size());
        System.out.println("SP 1:"+splicePoint1);
        System.out.println("SP 2:"+splicePoint2);

        ArrayList<Gene> genesTempA1 = new ArrayList<>();
        ArrayList<Gene> genesTempA2 = new ArrayList<>();
        ArrayList<Gene> genesTempA3 = new ArrayList<>();
        ArrayList<Gene> genesTempB1 = new ArrayList<>();
        ArrayList<Gene> genesTempB2 = new ArrayList<>();
        ArrayList<Gene> genesTempB3 = new ArrayList<>();

        genesTempA1.addAll(a.getGenome().subList(0, splicePoint1));
        genesTempA2.addAll(a.getGenome().subList(splicePoint1, splicePoint2));
        genesTempA3.addAll(a.getGenome().subList(splicePoint2, a.getGenome().size()));
        genesTempB1.addAll(b.getGenome().subList(0, splicePoint1));
        genesTempB2.addAll(b.getGenome().subList(splicePoint1, splicePoint2));
        genesTempB3.addAll(b.getGenome().subList(splicePoint2, b.getGenome().size()));

        ArrayList<Gene> productA = new ArrayList<>();
        ArrayList<Gene> productB = new ArrayList<>();

        productA.addAll(genesTempA1);
        productA.addAll(genesTempB2);
        productA.addAll(genesTempA3);

        productB.addAll(genesTempB1);
        productB.addAll(genesTempA2);
        productB.addAll(genesTempB3);

        Specimen specimenA = new Specimen();
        specimenA.setGenome(productA);

        Specimen specimenB = new Specimen();
        specimenB.setGenome(productB);

        product.add(specimenA);
        product.add(specimenB);

    }
}
