package org.ysokolovski.rdf.poc.navigate;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.vocabulary.VCARD;
import org.ysokolovski.rdf.poc.simplemodel.SimpleModel;

/**
 * Created by ysokolov on 5/6/2015.
 */
public class NavigateModel {
    public static void main(String[] args) {

        NavigateModel navigateModel=new NavigateModel();

        navigateModel.navigate();
        navigateModel.query();

    }

    private void query() {

        simpleModel.create("Jane","Smith");
        simpleModel.create("Barb","Walley");

        Model model=simpleModel.getModel();

        ResIterator resIterator=model.listSubjectsWithProperty(VCARD.FN);

        if(resIterator.hasNext()) {
            System.out.println("Found records with Full Name:");

            while(resIterator.hasNext()) {
                Resource resource=resIterator.nextResource();
                System.out.printf(" %s\n", resource.getProperty(VCARD.FN).getString());
            }
        } else {
            System.out.println("No records with Full Name found");
        }

        Resource object=model.createResource();
        object.addProperty(VCARD.Family,"Smith");


        System.out.println("Find resources with last name=Smith");

        //StmtIterator stmtIterator=model.listStatements(new SimpleSelector(null,VCARD.N,object));

        StmtIterator stmtIterator = model.listStatements(
                new SimpleSelector(null, VCARD.FN, (RDFNode) null) {
                    public boolean selects(Statement s)
                    {return s.getString().endsWith("Smith");}
                });

        while(stmtIterator.hasNext()) {
            Statement statement=stmtIterator.nextStatement();
            System.out.printf(" %s\n", statement.getSubject());
        }



    }

    private final SimpleModel simpleModel=new SimpleModel();

    private NavigateModel() {
        simpleModel.create();
    }

    private void navigate() {
        Resource vcard=simpleModel.get(SimpleModel.JOHN_SMITH_URI);

        String fullName=vcard.getProperty(VCARD.FN).getString();

        vcard.addProperty(VCARD.NICKNAME,"Johnny");
        vcard.addProperty(VCARD.NICKNAME,"Adman");


        System.out.printf("The nicknames of %s are \n", fullName);

        StmtIterator stmtIterator=vcard.listProperties(VCARD.NICKNAME);
        while(stmtIterator.hasNext()) {
            System.out.printf(" %s\n", stmtIterator.nextStatement().getObject());
        }

    }
}
