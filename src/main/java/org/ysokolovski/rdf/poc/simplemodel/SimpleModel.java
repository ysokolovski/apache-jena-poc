package org.ysokolovski.rdf.poc.simplemodel;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.vocabulary.VCARD;

import java.io.ByteArrayInputStream;

/**
 * Created by ysokolov on 5/6/2015.
 */
public class SimpleModel {

    public static final String RDF_PREFIX = "http://org.ysokolovski.rdf/";
    public static final String JOHN_SMITH_URI = RDF_PREFIX + "johnsmith";


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
    public static final String N_TRIPLES = "N-TRIPLES";
    public static final String RDF_XML_ABBREV = "RDF/XML-ABBREV";

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

    public void loadFromXml() {
        model.read(new ByteArrayInputStream(PERSON_RDF.getBytes()),null);
        System.out.println("Loaded RDF from XML...");
    }

    public void printTripleXml() {
        System.out.println("Triples RDF XML:");
        model.write(System.out, N_TRIPLES);
    }

    public void printPrettyXml() {
        System.out.println("Pretty RDF XML:");
        model.write(System.out, RDF_XML_ABBREV);
    }

    public void printDumbXml() {
        System.out.println("Dumb RDF XML:");
        model.write(System.out);
    }

    public void list() {
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

    public void create() {
        final String given = "John";
        final String family = "Smith";

        Resource resource = create(given, family);

        resource.addProperty(
                VCARD.N, model.createResource().
                        addProperty(VCARD.Given, given).
                        addProperty(VCARD.Family, family)
        );


        System.out.printf("Initial model: %s\n", model);
    }

    public Resource create(String given, String family) {
        String name=given+" "+family;
        String uri= getUri(given, family);
        return model.
                createResource(uri).
                addProperty(VCARD.FN, name)
                ;
    }

    public String getUri(String given, String family) {
        return RDF_PREFIX+given.toLowerCase()+family.toLowerCase();
    }


    public Resource get(String uri) {
        return model.getResource(uri);
    }

    public Model getModel() {
        return model;
    }

    public Resource getRoot() {
        return model.listResourcesWithProperty(VCARD.FN).nextResource();
    }
}
