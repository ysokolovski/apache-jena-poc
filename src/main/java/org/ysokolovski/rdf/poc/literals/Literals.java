package org.ysokolovski.rdf.poc.literals;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.VCARD;
import org.ysokolovski.rdf.poc.simplemodel.SimpleModel;

/**
 * Created by ysokolov on 5/6/2015.
 */
public class Literals {

    private final SimpleModel simpleModel=new SimpleModel();

    public static void main(String[] args) {
        Literals literals=new Literals();

        literals.createResourceWithLiterals();
        literals.printXml();
    }

    private void printXml() {
        simpleModel.printPrettyXml();
    }

    private void createResourceWithLiterals() {
        simpleModel.create();

        Resource root= simpleModel.getRoot();

        final Model model = simpleModel.getModel();
        Literal literal_en= model.createLiteral("Good day", "en");
        Literal literal_fr= model.createLiteral("Bon jour", "fr");

        final String namespace = SimpleModel.RDF_PREFIX + "properties#";
        Property property=model.createProperty(namespace + "welcomePhrase");
        model.setNsPrefix("ysokolovski",namespace);
        root.addLiteral(property,literal_en);
        root.addLiteral(property,literal_fr);
    }
}
