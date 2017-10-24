package genetics;

import log.Log;

import java.util.ArrayList;
import java.util.Random;

public class Specimen {
    private static Random random = new Random();
    private double fitness;
    private ArrayList<Gene> genome;
    private boolean isChanged;

    public Specimen(ArrayList<Gene> determinedGenome){
        genome = new ArrayList<>();
        for(Gene gene: determinedGenome){
            genome.add(new Gene(gene));
        }
        isChanged=true;
    }

    public Specimen(){
        genome = new ArrayList<>();
        isChanged=true;
    }

    public void mutate(double mutationProbability){
        for (Gene gene:genome){
            if(random.nextDouble()<mutationProbability) {
                Log.info("Mutating "+this.toString());
                gene.mutate();
                isChanged=true;
            }
        }
    }

    public void setValue(ArrayList<Gene> genes){
        genome.clear();
        for (Gene gene: genes){
            genome.add(gene.clone());
        }
    }

    public ArrayList<Gene> getGenome(){
        return genome;
    }

    public double getFitness(){
        return fitness;
    }

    public void setFitness(double fitness){
        this.fitness=fitness;
    }

    public boolean isChanged() {
        return isChanged;
    }

    public void setChanged(boolean changed) {
        isChanged = changed;
    }

    public void setGenome(ArrayList<Gene> newGenome){
        genome.clear();
        for(Gene g: newGenome)
            genome.add(g.clone());
    }

    @Override
    public Specimen clone() {
        Specimen clone = new Specimen();
        for(Gene g: genome){
            clone.genome.add(g.clone());
        }
        clone.fitness=fitness;

        return clone;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        sb.append("Genome: [");

        for (Gene gene: genome){
            sb.append(gene.getValue());
            sb.append(", ");
        }

        String product = sb.toString();
        product = product.substring(0,product.length()-2);
        product+="]";

        return product;
    }

    @Override
    public boolean equals(Object o) {
        if(o.getClass()==Specimen.class){
            Specimen specimen = (Specimen) o;
            boolean parametersEqual = false;
            if (specimen.fitness==fitness&&specimen.isChanged==isChanged){
                parametersEqual=true;
            }
            for (int i=0; i<specimen.genome.size(); i++){
                if(!specimen.genome.get(i).equals(genome.get(i))){
                    return false;
                }
            }
            return parametersEqual;
        }
        return false;
    }
}
