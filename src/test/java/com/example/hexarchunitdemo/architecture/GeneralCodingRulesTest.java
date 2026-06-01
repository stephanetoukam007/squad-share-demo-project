package com.example.hexarchunitdemo.architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.GeneralCodingRules;

@AnalyzeClasses(packages = "com.example.hexarchunitdemo", importOptions = ImportOption.DoNotIncludeTests.class)
class GeneralCodingRulesTest {

    @ArchTest
    static final ArchRule pas_de_standard_streams = GeneralCodingRules.NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS;

    @ArchTest
    static final ArchRule pas_de_java_util_logging = GeneralCodingRules.NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING;

    @ArchTest
    static final ArchRule pas_de_jodatime = GeneralCodingRules.NO_CLASSES_SHOULD_USE_JODATIME;
}

