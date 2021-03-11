package nl.magicshoot.dashboard;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("nl.magicshoot.dashboard");

        noClasses()
            .that()
            .resideInAnyPackage("nl.magicshoot.dashboard.service..")
            .or()
            .resideInAnyPackage("nl.magicshoot.dashboard.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..nl.magicshoot.dashboard.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
