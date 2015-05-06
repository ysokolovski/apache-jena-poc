package org.ysokolovski.rdf.poc.merge;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.VCARD;
import org.ysokolovski.rdf.poc.simplemodel.SimpleModel;

/**
 * Created by ysokolov on 5/6/2015.
 */
public class MergeModels {


    private static class ResourceWrapper {
        String uri;

        public ResourceWrapper(String uri) {
            this.uri = uri;
        }
    }

    public static void main(String[] args) {
        MergeModels mergeModels=new MergeModels();

        Model model1=mergeModels.createModel(VCARD.N,
                VCARD.Given, "John",
                VCARD.Family, "Smith"
        );
        Model model2=mergeModels.createModel(VCARD.EMAIL,
                RDF.type,new ResourceWrapper("http://www.w3.org/2001/vcard-rdf/3.0#internet"),
                RDF.value,"johnsmith@email.com");

        Model model3=mergeModels.merge(model1,model2);

        mergeModels.printModel(model3);
    }

    private void printModel(Model model) {
        model.write(System.out, SimpleModel.RDF_XML_ABBREV);
    }

    private Model merge(Model model1, Model model2) {
        return model1.union(model2);
    }

    private Model createModel(Property property,Object ... subProperties) {
        SimpleModel simpleModel=new SimpleModel();
        simpleModel.create("John","Smith");

        Resource root=simpleModel.getRoot();

        final Resource resource = simpleModel.getModel().createResource();

        for(int i=0;i<subProperties.length;i+=2) {
            Property subProperty=(Property) subProperties[i];
            if(subProperties[i+1] instanceof ResourceWrapper) {
                ResourceWrapper resourceWrapper=(ResourceWrapper) subProperties[i+1];
                Resource subResource=simpleModel.getModel().createResource(resourceWrapper.uri);
                resource.addProperty(subProperty,subResource);
            } else {
                String value = (String) subProperties[i + 1];
                resource.addProperty(subProperty, value);
            }
        }

        root.addProperty(property,resource);

        return simpleModel.getModel();
    }
}
