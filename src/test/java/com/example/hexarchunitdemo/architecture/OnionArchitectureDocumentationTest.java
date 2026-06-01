package com.example.hexarchunitdemo.architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.Architectures.onionArchitecture;

@AnalyzeClasses(packages = "com.example.hexarchunitdemo", importOptions = ImportOption.DoNotIncludeTests.class)
class OnionArchitectureDocumentationTest {

    /**
     * Documentation exécutable de l'architecture en oignon / hexagonale.
     */
    @ArchTest
    static final ArchRule architecture_hexagonale_doit_garder_des_dependances_vers_l_interieur = onionArchitecture()
            .domainModels("..domain.model..")
            .domainServices("..domain.service..", "..domain.port..", "..domain.exception..")
            .applicationServices("..application..")
            .adapter("rest", "..adapter.rest..")
            .adapter("persistence", "..adapter.persistence..")
            .because("les dépendances doivent pointer vers le domaine, jamais vers l'infrastructure");
}

