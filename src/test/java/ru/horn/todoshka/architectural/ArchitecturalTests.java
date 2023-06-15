package ru.horn.todoshka.architectural;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public class ArchitecturalTests {

    @Test
    public void controllerShouldBeInControllerFolder() {
        JavaClasses classes = new ClassFileImporter().importPackages("ru.horn");

        ArchRule rule = classes()
                .that().haveNameMatching(".*Controller$")
                .should().resideInAPackage("..controller");

        rule.check(classes);
    }

    @Test
    public void ShouldBeInFolder() {
        JavaClasses classes = new ClassFileImporter().importPackages("ru.horn");

        ArchRule rule = classes()
                .that().haveNameMatching(".*$")
                .should().resideInAPackage("..");

        rule.check(classes);
    }

    @Test
    public void entityShouldBeInEntityFolder() {
        JavaClasses classes = new ClassFileImporter().importPackages("ru.horn");

        ArchRule rule = classes()
                .that().haveNameMatching(".*Task$")
                .should().resideInAPackage("..entity");

        rule.check(classes);
    }

    @Test
    public void repositoryShouldBeInRepoFolder() {
        JavaClasses classes = new ClassFileImporter().importPackages("ru.horn");

        ArchRule rule = classes()
                .that().haveNameMatching(".*Repository$")
                .should().resideInAPackage("..repo");

        rule.check(classes);
    }

    @Test
    public void todoServiceIntShouldBeInServiceFolder() {
        JavaClasses classes = new ClassFileImporter().importPackages("ru.horn");

        ArchRule rule = classes()
                .that().haveNameMatching(".*ToDoServiceInt$")
                .should().resideInAPackage("..service");

        rule.check(classes);
    }

    @Test
    public void ToDoServiceImplShouldBeInImplFolder() {
        JavaClasses classes = new ClassFileImporter().importPackages("ru.horn");

        ArchRule rule = classes()
                .that().haveNameMatching(".*TodoServiceImpl$")
                .should().resideInAPackage("..service.impl");

        rule.check(classes);
    }

}
