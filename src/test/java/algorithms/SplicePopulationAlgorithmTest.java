package algorithms;

import genetics.Gene;
import genetics.Specimen;
import log.Log;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class SplicePopulationAlgorithmTest {
    @Test
    public void splice2() throws Exception{
        Log.init("test", 1);
        ArrayList<Gene> genesA = new ArrayList<>();
        ArrayList<Gene> genesB = new ArrayList<>();

        genesA.add(new Gene("Test1",1,2,4));
        genesA.add(new Gene("Test2",2,4,6));
        genesA.add(new Gene("test3",4,6,8));
        genesA.add(new Gene("Test4",6,8,10));

        for(Gene g: genesA)
            g.setValue(g.getOrder());

        genesB.add(new Gene("Test5", 3,2,4));
        genesB.add(new Gene("Test6", 6, 9, 11));
        genesB.add(new Gene("Test7",9,11,13));
        genesB.add(new Gene("Test8",11,13,15));

        for(Gene g: genesB)
            g.setValue(g.getOrder());

        Specimen a = new Specimen(genesA);
        a.setGenome(genesA);
        Specimen b = new Specimen(genesB);
        b.setGenome(genesB);

        ArrayList<Specimen> splice = new ArrayList<>();
        splice.add(a);
        splice.add(b);

        splice = SplicePopulationAlgorithm.splice(splice, 0.7);

        System.out.println("Input a"+a);
        System.out.println("Input b"+b);

        System.out.println("Output a"+splice.get(0)+" is changed: "+splice.get(0).isChanged());
        System.out.println("Output b"+splice.get(1)+" is changed: "+splice.get(1).isChanged());
    }

    @Test
    public void splice() throws Exception {
        Log.init("test", 1);
        ArrayList<Gene> genesA = new ArrayList<>();
        ArrayList<Gene> genesB = new ArrayList<>();

        genesA.add(new Gene("Test1",1,2,4));
        genesA.add(new Gene("Test2",2,4,6));
        genesA.add(new Gene("test3",4,6,8));
        genesA.add(new Gene("Test4",6,8,10));

        for(Gene g: genesA)
            g.setValue(g.getOrder());

        genesB.add(new Gene("Test5", 3,2,4));
        genesB.add(new Gene("Test6", 6, 9, 11));
        genesB.add(new Gene("Test7",9,11,13));
        genesB.add(new Gene("Test8",11,13,15));

        for(Gene g: genesB)
            g.setValue(g.getOrder());

        Specimen a = new Specimen(genesA);
        a.setGenome(genesA);
        Specimen b = new Specimen(genesB);
        b.setGenome(genesB);

        ArrayList<Specimen> product = new ArrayList<>();
        TwoPointSplicePopulationAlgorithm.splice(a,b, product);

        System.out.println("Input a"+a);
        System.out.println("Input b"+b);

        System.out.println("Output a"+product.get(0)+" is changed: "+product.get(0).isChanged());
        System.out.println("Output b"+product.get(1)+" is changed: "+product.get(1).isChanged());

        assertTrue(product.get(0).getGenome().size()==genesA.size());
        assertTrue(product.get(1).getGenome().size()==genesB.size());
    }

}