package io.github.andreatp.crd2openapi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.fabric8.kubernetes.api.model.HasMetadata;
import io.fabric8.kubernetes.client.utils.Serialization;
import io.fabric8.kubernetes.api.model.apiextensions.v1.CustomResourceDefinition;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("Starting crd2openapi");

        var crdFile = new File("src/main/resources/crontab.yaml");

        try (FileInputStream fis = new FileInputStream(crdFile)) {
            List<Object> resources = new ArrayList<>();

            Object deserialized = Serialization.unmarshal(fis);
            if (deserialized instanceof List) {
                resources.addAll((List<Object>) deserialized);
            } else {
                resources.add(deserialized);
            }

            for (var rawResource: resources) {
                if (rawResource != null && rawResource instanceof HasMetadata) {
                    final HasMetadata resource = (HasMetadata) rawResource;

                    if (resource != null && resource.getKind()
                            .toLowerCase(Locale.ROOT)
                            .equals("customresourcedefinition")) {
                        CustomResourceDefinition crd = (CustomResourceDefinition) resource;

                        // GO on from here!!!
                    }
            }
        }

        System.out.println("Finished crd2openapi");
    }
}
