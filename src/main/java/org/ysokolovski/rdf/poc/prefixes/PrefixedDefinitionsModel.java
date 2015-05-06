package org.ysokolovski.rdf.poc.prefixes;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import org.ysokolovski.rdf.poc.simplemodel.SimpleModel;

import java.io.ByteArrayInputStream;

/**
 * Created by ysokolov on 5/6/2015.
 */
public class PrefixedDefinitionsModel {

    private static final String RDF="<rdf:RDF\n" +
            "    xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n" +
            "    xmlns:nsA=\"http://somewhere/else#\"\n" +
            "    xmlns:cat=\"http://nowhere/else#\" > \n" +
            "  <rdf:Description rdf:about=\"http://somewhere/else#y\">\n" +
            "    <cat:Q rdf:resource=\"http://somewhere/else#z\"/>\n" +
            "  </rdf:Description>\n" +
            "  <rdf:Description rdf:about=\"http://somewhere/else#root\">\n" +
            "    <nsA:P rdf:resource=\"http://somewhere/else#y\"/>\n" +
            "    <nsA:P rdf:resource=\"http://somewhere/else#x\"/>\n" +
            "  </rdf:Description>\n" +
            "</rdf:RDF>\n";


    public static void main(String[] args) {
        PrefixedDefinitionsModel prefixedDefinitionModel=new PrefixedDefinitionsModel();

        prefixedDefinitionModel.create();
        prefixedDefinitionModel.printXml();
        prefixedDefinitionModel.read();
        prefixedDefinitionModel.printXml();

    }

    private void read() {
        model.read(new ByteArrayInputStream(RDF.getBytes()),null);
        System.out.println("Loaded from XML");
    }

    private void printXml() {
        model.write(System.out);
    }

    private final Model model= ModelFactory.createDefaultModel();

    private void create() {
        String nsA="http://somewhere/else#";
        String nsB="http://nowhere/else#";

        Resource root=model.createResource(nsA+"root");
        Property P=model.createProperty(nsA + "P");
        Property Q=model.createProperty(nsB +"Q");

        Property x=model.createProperty(nsA+"x");
        Property y=model.createProperty(nsA+"y");
        Property z=model.createProperty(nsA+"z");

        model.  add(root,P,x).
                add(root,P,y).
                add(y,Q,z);

        System.out.println("# -- no special prefixes defined");
        model.write(System.out);

        model.setNsPrefix("nsA",nsA);
        model.setNsPrefix("cat",nsB);
        System.out.println("# -- prefixes defined");
    }
}
