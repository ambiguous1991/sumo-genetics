package algorithms;

import genetics.Gene;
import genetics.Specimen;
import log.Log;

import java.util.ArrayList;
import java.util.Collections;

public class Shuffler {
    private final int genesSize;
    private ArrayList<Integer> pattern;

    public Shuffler(int genesSize){
        pattern = new ArrayList<>();
        this.genesSize=genesSize;
        initializePattern();
    }

    private void initializePattern(){
        for (int i=0; i<genesSize; i++){
            pattern.add(i);
        }
        Collections.shuffle(pattern);
        Log.info("List shuffled to "+pattern.toArray());
    }

    public void shuffle(ArrayList<Specimen> population){
        for(Specimen s: population){
            shuffle(s);
        }
    }

    private void shuffle(Specimen spec){
        ArrayList<Gene> genome = spec.getGenome();
        ArrayList<Gene> product = new ArrayList<>();

        for (int i=0; i<genome.size(); i++){
            product.add(genome.get(pattern.get(i)));
        }
        spec.setGenome(product);
    }

    public void unshuffle(ArrayList<Specimen> population){
        for (Specimen s: population){
            unshuffle(s);
        }
    }

    private void unshuffle(Specimen s){
        Collections.sort(s.getGenome());
    }
}
