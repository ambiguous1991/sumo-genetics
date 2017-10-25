package algorithms;

import genetics.Gene;
import genetics.Specimen;
import log.Log;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ShufflerTest {
    @Test
    public void shuffle() throws Exception {
        Log.init("test", 1);
        Gene gene1 = new Gene("Test1",1,2,4);
        Gene gene2 = new Gene("Test2",2,4,6);
        Gene gene3 = new Gene("test3",4,6,8);
        Gene gene4 = new Gene("Test4",6,8,10);

        gene1.setGeneralOrder(1);
        gene2.setGeneralOrder(2);
        gene3.setGeneralOrder(3);
        gene4.setGeneralOrder(4);

        ArrayList<Gene> list = new ArrayList<>();
        list.add(gene1);
        list.add(gene2);
        list.add(gene3);
        list.add(gene4);

        Specimen specimen = new Specimen();
        specimen.setGenome(list);
        ArrayList<Specimen> specs = new ArrayList<>();
        specs.add(specimen);

        Shuffler shuffler = new Shuffler(list.size());
        shuffler.shuffle(specs);

        shuffler.unshuffle(specs);
    }

    @Test
    public void unshuffle() throws Exception {
    }

}