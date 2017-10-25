package genetics;

import algorithms.MutationAlgorithm;
import algorithms.SplicePopulationAlgorithm;
import algorithms.TournamentSelectionAlgorithm;
import log.Log;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import sumo.SumoWrapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Population implements Runnable{
    public static int DATA_SWITCH;
    private double spliceProb=0.7, mutationProb=1/15;
    private int FFECounter, FFETarget, generationNo, FFEOfBest;
    private int populationSize;
    private File definition;
    private ArrayList<Specimen> population;
    private ArrayList<Gene> determinedGenome;
    private Specimen globalBestSpecimen, currentGenerationBestSpecimen, currentGenerationWorstSpecimen;

    public Population(int populationSize, int maxFFE, int dataSwitch){
        this.DATA_SWITCH = dataSwitch;
        definition = new File("data/pasubio"+dataSwitch+"/pasubio_tls.add.xml");
        this.populationSize=populationSize;
        this.FFETarget = maxFFE;
        this.FFECounter = 0;
        population = new ArrayList<>();
        determinedGenome = new ArrayList<>();
        generationNo=1;
        globalBestSpecimen = new Specimen();
        globalBestSpecimen.setFitness(0.0);
        currentGenerationWorstSpecimen = new Specimen();
        currentGenerationWorstSpecimen.setFitness(1.0);
        currentGenerationBestSpecimen = new Specimen();
        currentGenerationBestSpecimen.setFitness(0.0);
    }

    private void mainLoop(){
        createPopulation();
        evaluatePopulation("BEGINNING");
        while(FFECounter<FFETarget){
            Shuffler.shuffle(population);
            population = TournamentSelectionAlgorithm.select(population);
            population = SplicePopulationAlgorithm.splice(population, spliceProb);
            evaluatePopulation("FITNESS");

            findGlobalBest();
            findGenerationsBest();
            findGenerationsWorst();
            findMeanAndDeviation();

            MutationAlgorithm.mutate(population, mutationProb);

            evaluatePopulation("AFTER MUTATION");

            Log.info("End of generation "+generationNo);
            generationNo++;
        }
        Log.close();
    }

    public void createPopulation(){
        SAXBuilder builder = new SAXBuilder();
        int genomeLength = 0;

        try {
            Document document = builder.build(definition);
            Element rootNode = document.getRootElement();
            List<Element> list = rootNode.getChildren();
            for (Element child: list){
                List<Element> phases = child.getChildren();
                for (int i=0; i<phases.size(); i++){
                    Element phase = phases.get(i);
                    String minDur = phase.getAttributeValue("minDur");
                    if(minDur!=null){
                        String maxDur = phase.getAttributeValue("maxDur");
                        if (!minDur.equals(maxDur)){
                            determinedGenome.add(new Gene(
                                    child.getAttributeValue("id"),
                                    i,
                                    Integer.parseInt(minDur),
                                    Integer.parseInt(maxDur)
                            ));
                            genomeLength++;
                        }
                    }
                }
            }
            Log.info("Determined genome lenght is "+genomeLength);
            Log.info("Determined genome: ");
            for(Gene gene: determinedGenome){
                Log.info(gene.getId()+", "+gene.getOrder()+" from "+gene.getMinVal()+" to "+gene.getMaxVal());
            }

            for (int i=0; i<populationSize; i++){
                population.add(new Specimen(determinedGenome));
            }

            for (Specimen specimen: population){
                Log.log("DEFS",specimen.toString());
            }
            mutationProb = (double)(1)/(double)(genomeLength);
            Log.info("Mutation prob is "+mutationProb);
            Log.info("Splice prob is "+spliceProb);
        }

        catch (Exception e) {
            Log.error(e.toString());
            e.printStackTrace();
        }
    }

    private synchronized void evaluatePopulation(String tag){
        try {
            for (int i=0; i<population.size();i++) {
                Specimen specimen = population.get(i);
                Log.info("Progress "+i+"/"+population.size());
                if(specimen.isChanged()) {
                    serializeSpecimen(specimen);
                    Log.log("SUMO","Evaluating specimen " + specimen);
                    SumoWrapper wrapper = new SumoWrapper();
                    Thread t = wrapper.getThread();
                    synchronized (t) {
                        t.start();
                        Log.info("Waiting for sumowrapper to finish...");
                        t.wait();
                        specimen.setFitness(wrapper.getEvaluatedFitness());
                    }
                    Log.log(tag, "FFE;" + FFECounter + ";generation;"+generationNo+";fitness;" + specimen.getFitness());
                    FFECounter++;
                    specimen.setChanged(false);
                }
                else{
                    Log.info("Specimen "+specimen.toString()+" did not change, skipping...");
                    Log.log(tag, "FFE;" + FFECounter + ";generation;"+generationNo+";fitness;" + specimen.getFitness());
                }
            }
        }
        catch (InterruptedException e){

        }
    }

    private void serializeSpecimen(Specimen specimen){
        try {
            List<Gene> genome = specimen.getGenome();
            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(definition);
            Element root = document.getRootElement();
            List<Element> tlLogics = root.getChildren();

            for (int i=0; i<genome.size(); i++){
                Gene gene = genome.get(i);
                for(Element tlLogic: tlLogics){
                    if(tlLogic.getAttributeValue("id").equals(gene.getId())){
                        tlLogic.getChildren().get(gene.getOrder()).setAttribute("duration", ""+gene.getValue());
                    }
                }
            }
            XMLOutputter xmlOutputter = new XMLOutputter();
            xmlOutputter.setFormat(Format.getPrettyFormat());
            FileOutputStream fos = new FileOutputStream(definition);
            xmlOutputter.output(document, fos);
            fos.close();
        }
        catch (JDOMException e){
            e.printStackTrace();
            Log.error(e.toString());
        }
        catch(IOException e){
            e.printStackTrace();
            Log.error(e.toString());
        }
    }

    private void findGlobalBest(){
        for (Specimen specimen: population){
            if(globalBestSpecimen.getFitness()<specimen.getFitness()) {
                globalBestSpecimen = specimen.clone();
                FFEOfBest = FFECounter;
            }
        }
        Log.log("GLOBAL BEST", "generation;"+generationNo+";ffe;"+FFEOfBest+";fitness;"+globalBestSpecimen.getFitness()+";genome;"+globalBestSpecimen.toString());
    }

    private void findGenerationsBest(){
        currentGenerationBestSpecimen.setFitness(0.0);
        for (Specimen specimen: population){
            if(currentGenerationBestSpecimen.getFitness()<specimen.getFitness()) {
                currentGenerationBestSpecimen = specimen.clone();
            }
        }
        Log.log("POPULATION BEST", "generation;"+generationNo+";fitness;"+currentGenerationBestSpecimen.getFitness()+";genome;"+currentGenerationBestSpecimen.toString());
    }

    private void findGenerationsWorst(){
        currentGenerationWorstSpecimen.setFitness(1.0);
        for (Specimen specimen: population){
            if(currentGenerationWorstSpecimen.getFitness()>specimen.getFitness()) {
                currentGenerationWorstSpecimen = specimen.clone();
            }
        }
        Log.log("POPULATION WORSE", "generation;"+generationNo+";fitness;"+currentGenerationWorstSpecimen.getFitness()+";genome;"+currentGenerationWorstSpecimen.toString());
    }

    private void findMeanAndDeviation(){
        double mean=0;

        for(Specimen specimen: population){
            mean+=specimen.getFitness();
        }

        mean/=populationSize;

        Log.log("POPULATION MEAN","generation;"+generationNo+";"+mean);

        double deviation=0;
        for(Specimen specimen: population){
            deviation+=Math.pow(specimen.getFitness()-mean,2);
        }
        deviation/=population.size();
        deviation=Math.sqrt(deviation);

        Log.log("POPULATION DEVIATION","generation;"+generationNo+";"+deviation);
    }

    @Override
    public void run() {
        mainLoop();
    }
}
