package org.ysokolovski.rdf.poc.container;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.vocabulary.VCARD;
import org.ysokolovski.rdf.poc.simplemodel.SimpleModel;

/**
 * Created by ysokolov on 5/6/2015.
 */
public class ResourceBagger {

    public static void main(String[] args) {
        ResourceBagger resourceBagger=new ResourceBagger();

        resourceBagger.addResource("John", "Smith");
        resourceBagger.addResource("Jane", "Smithson");

        resourceBagger.printXml();

        resourceBagger.printContainer();
    }

    private void printContainer() {
        System.out.println("Bag contains:");
        NodeIterator nodeIterator=bag.iterator();
        while(nodeIterator.hasNext()) {
            Resource node=(Resource) nodeIterator.nextNode();
            System.out.printf(" %s\n", node.getProperty(VCARD.FN));
        }
    }

    private void printXml() {
        model.write(System.out);
    }

    private Model model= ModelFactory.createDefaultModel();
    private Bag bag=model.createBag();

    private void addResource(String given, String family) {
        bag.add(new SimpleModel(model).create(given, family));
    }
}
