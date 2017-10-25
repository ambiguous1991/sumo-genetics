package genetics;

import java.util.Random;

public class Gene implements Comparable<Gene>{
    private static Random random = new Random();
    private String id;
    private int order, minVal, maxVal, generalOrder;
    private int value;

    public Gene(String id, int order, int minVal, int maxVal){
        this.id=id;
        this.order=order;
        this.minVal=minVal;
        this.maxVal=maxVal;
        generateValue();
    }

    public Gene(Gene gene){
        this(gene.id,gene.order,gene.minVal,gene.maxVal);
    }

    private void generateValue(){
        double delta = (double)maxVal-(double)minVal;
        delta*=random.nextDouble();
        value = (int)(minVal+Math.round(delta));
    }

    @Override
    protected Gene clone() {
        Gene clone = new Gene(id, order, minVal, maxVal);
        clone.value = value;
        clone.generalOrder = generalOrder;
        return clone;
    }

    public void mutate(){
        generateValue();
    }

    public String getId() {
        return id;
    }

    public int getOrder() {
        return order;
    }

    public int getMinVal() {
        return minVal;
    }

    public int getMaxVal() {
        return maxVal;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value){
        this.value=value;
    }

    @Override
    public boolean equals(Object o) {
        if(o.getClass()==Gene.class){
            Gene equal = (Gene) o;
            return equal.value==value&&equal.maxVal==maxVal&&equal.minVal==minVal&&equal.order==order&&equal.id.equals(id);
        }
        return false;
    }

    public void setGeneralOrder(int generalOrder) {
        this.generalOrder = generalOrder;
    }

    public int getGeneralOrder() {
        return generalOrder;
    }

    @Override
    public int compareTo(Gene gene) {
        if(generalOrder>gene.generalOrder) return 1;
        else if (generalOrder==gene.generalOrder) return 0;
        else return -1;
    }
}
