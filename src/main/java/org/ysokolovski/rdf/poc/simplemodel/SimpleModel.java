package org.ysokolovski.rdf.poc.simplemodel;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.vocabulary.VCARD;

import java.io.ByteArrayInputStream;

/**
 * Created by ysokolov on 5/6/2015.
 */
public class SimpleModel {

    private static final String personURI="http://org.ysokolovski.rdf/johnsmith";
    private static final String fullName="John Smith";

    private static final String PERSON_RDF="<rdf:RDF\n" +
            "  xmlns:rdf='http://www.w3.org/1999/02/22-rdf-syntax-ns#'\n" +
            "  xmlns:vcard='http://www.w3.org/2001/vcard-rdf/3.0#'\n" +
            " >\n" +
            "  <rdf:Description rdf:nodeID=\"A0\">\n" +
            "    <vcard:Family>Smith</vcard:Family>\n" +
            "    <vcard:Given>John</vcard:Given>\n" +
            "  </rdf:Description>\n" +
            "  <rdf:Description rdf:about='http://somewhere/JohnSmith/'>\n" +
            "    <vcard:FN>John Smith</vcard:FN>\n" +
            "    <vcard:N rdf:nodeID=\"A0\"/>\n" +
            "  </rdf:Description>\n" +
            "  <rdf:Description rdf:about='http://somewhere/SarahJones/'>\n" +
            "    <vcard:FN>Sarah Jones</vcard:FN>\n" +
            "    <vcard:N rdf:nodeID=\"A1\"/>\n" +
            "  </rdf:Description>\n" +
            "  <rdf:Description rdf:about='http://somewhere/MattJones/'>\n" +
            "    <vcard:FN>Matt Jones</vcard:FN>\n" +
            "    <vcard:N rdf:nodeID=\"A2\"/>\n" +
            "  </rdf:Description>\n" +
            "  <rdf:Description rdf:nodeID=\"A3\">\n" +
            "    <vcard:Family>Smith</vcard:Family>\n" +
            "    <vcard:Given>Rebecca</vcard:Given>\n" +
            "  </rdf:Description>\n" +
            "  <rdf:Description rdf:nodeID=\"A1\">\n" +
            "    <vcard:Family>Jones</vcard:Family>\n" +
            "    <vcard:Given>Sarah</vcard:Given>\n" +
            "  </rdf:Description>\n" +
            "  <rdf:Description rdf:nodeID=\"A2\">\n" +
            "    <vcard:Family>Jones</vcard:Family>\n" +
            "    <vcard:Given>Matthew</vcard:Given>\n" +
            "  </rdf:Description>\n" +
            "  <rdf:Description rdf:about='http://somewhere/RebeccaSmith/'>\n" +
            "    <vcard:FN>Becky Smith</vcard:FN>\n" +
            "    <vcard:N rdf:nodeID=\"A3\"/>\n" +
            "  </rdf:Description>\n" +
            "</rdf:RDF>";

    private final Model model=ModelFactory.createDefaultModel();

    public static void main(String[] args) {
        final SimpleModel simpleModel = new SimpleModel();
        simpleModel.create();
        simpleModel.list();
        simpleModel.printDumbXml();
        simpleModel.printPrettyXml();
        simpleModel.printTripleXml();

        simpleModel.loadFromXml();
        simpleModel.printPrettyXml();
    }

    private void loadFromXml() {
        model.read(new ByteArrayInputStream(PERSON_RDF.getBytes()),null);
        System.out.println("Loaded RDF from XML...");
    }

    private void printTripleXml() {
        System.out.println("Triples RDF XML:");
        model.write(System.out,"N-TRIPLES");
    }

    private void printPrettyXml() {
        System.out.println("Pretty RDF XML:");
        model.write(System.out,"RDF/XML-ABBREV");
    }

    private void printDumbXml() {
        System.out.println("Dumb RDF XML:");
        model.write(System.out);
    }

    private void list() {
        StmtIterator stmtIterator=model.listStatements();

        System.out.println("Model statements:");

        while(stmtIterator.hasNext()) {
            Statement statement=stmtIterator.nextStatement();

            Resource s=statement.getSubject();
            Property p=statement.getPredicate();
            RDFNode o=statement.getObject();

            System.out.printf("%s %s ",s,p);

            if(o.isResource()) {
                System.out.print(o);
            } else {
                System.out.printf("\"%s\"", o);
            }

            System.out.println();
        }
    }

    private void create() {
        model.
                createResource(personURI).
                addProperty(VCARD.FN, fullName).
                addProperty(
                        VCARD.N, model.createResource().
                        addProperty(VCARD.Given,"John").
                                addProperty(VCARD.Family,"Smith")
                );



        System.out.printf("Initial model: %s\n", model);
    }


}
