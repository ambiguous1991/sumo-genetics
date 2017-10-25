package genetics;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

public class GeneTest {
    @Test
    public void compareToTest() throws Exception{
        Gene gene1 = new Gene("Test1",1,2,4);
        Gene gene2 = new Gene("Test2",2,4,6);
        Gene gene3 = new Gene("test3",4,6,8);
        Gene gene4 = new Gene("Test4",6,8,10);

        gene1.setGeneralOrder(1);
        gene2.setGeneralOrder(5);
        gene3.setGeneralOrder(6);
        gene4.setGeneralOrder(3);

        ArrayList<Gene> list = new ArrayList<>();
        list.add(gene1);
        list.add(gene2);
        list.add(gene3);
        list.add(gene4);

        for (Gene gene: list)
            System.out.println(gene.getId()+", general order "+gene.getGeneralOrder()+", "+gene.getOrder()+" from "+gene.getMinVal()+" to "+gene.getMaxVal());

        Collections.sort(list);

        for (Gene gene: list)
            System.out.println(gene.getId()+", general order "+gene.getGeneralOrder()+", "+gene.getOrder()+" from "+gene.getMinVal()+" to "+gene.getMaxVal());
    }
}
