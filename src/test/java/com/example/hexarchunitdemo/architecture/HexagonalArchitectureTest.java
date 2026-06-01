package com.example.hexarchunitdemo.architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

@AnalyzeClasses(packages = "com.example.hexarchunitdemo", importOptions = ImportOption.DoNotIncludeTests.class)
class HexagonalArchitectureTest {

    @ArchTest
    static final ArchRule domaine_doit_etre_independant = noClasses().that()
            .resideInAPackage("..domain..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..adapter..", "..application..", "org.springframework..", "jakarta..")
            .allowEmptyShould(true);

    @ArchTest
    static final ArchRule application_ne_doit_pas_dependre_des_adapters = noClasses().that()
            .resideInAPackage("..application..")
            .should()
            .dependOnClassesThat()
            .resideInAPackage("..adapter..")
            .allowEmptyShould(true);

    @ArchTest
    static final ArchRule rest_ne_depend_pas_de_persistence = noClasses().that()
            .resideInAPackage("..adapter.rest..")
            .should()
            .dependOnClassesThat()
            .resideInAPackage("..adapter.persistence..")
            .allowEmptyShould(true);

    @ArchTest
    static final ArchRule persistence_ne_depend_pas_de_rest = noClasses().that()
            .resideInAPackage("..adapter.persistence..")
            .should()
            .dependOnClassesThat()
            .resideInAPackage("..adapter.rest..")
            .allowEmptyShould(true);

    @ArchTest
    static final ArchRule pas_d_injection_de_champs = noClasses().should()
            .beAnnotatedWith(Autowired.class)
            .allowEmptyShould(true);

    @ArchTest
    static final ArchRule controllers_doivent_etre_dans_rest_adapter = noClasses().that()
            .areAnnotatedWith(RestController.class)
            .should()
            .resideOutsideOfPackage("..adapter.rest..")
            .allowEmptyShould(true);

    @ArchTest
    static final ArchRule ports_entrants_doivent_respecter_la_nomenclature = classes().that()
            .resideInAPackage("..domain.port.in..")
            .and().areInterfaces()
            .and().doNotHaveSimpleName("package-info")
            .and().haveSimpleNameNotEndingWith("Facade")
            .should().haveSimpleNameEndingWith("UseCase");

    @ArchTest
    static final ArchRule ports_sortants_doivent_respecter_la_nomenclature = classes().that()
            .resideInAPackage("..domain.port.out..")
            .and().areInterfaces()
            .and().doNotHaveSimpleName("package-info")
            .should().haveSimpleNameEndingWith("Port");

    @ArchTest
    static final ArchRule packages_doivent_etre_sans_cycles = slices()
            .matching("com.example.hexarchunitdemo.(*)..")
            .should().beFreeOfCycles();
}

