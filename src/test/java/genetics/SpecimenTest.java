package genetics;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class SpecimenTest {
    @Test
    public void equals() throws Exception {
        ArrayList<Gene> genome = new ArrayList<>();
        Gene geneA = new Gene("test",1,2,8);
        Gene geneB = new Gene("tes2",2,4,8);
        geneA.setValue(2);
        geneB.setValue(2);
        genome.add(geneA);
        genome.add(geneB);


        Specimen a = new Specimen(genome);
        a.getGenome().get(0).setValue(0);
        a.getGenome().get(1).setValue(1);
        Specimen b = new Specimen(genome);
        b.getGenome().get(0).setValue(0);
        b.getGenome().get(1).setValue(1);
        assertEquals(a,b);
    }

}