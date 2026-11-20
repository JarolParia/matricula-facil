package com.arq.app.architecture;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Verifica las reglas de dependencia de la arquitectura hexagonal leyendo los imports del código fuente:
 * las dependencias siempre apuntan hacia el dominio.
 */
class ArquitecturaHexagonalTest {

    private static final Path BASE = Path.of("src/main/java/com/arq/app");

    private static List<String> importsViolados(String carpeta, List<String> prohibidos) throws IOException {
        try (Stream<Path> archivos = Files.walk(BASE.resolve(carpeta))) {
            return archivos.filter(p -> p.toString().endsWith(".java"))
                    .flatMap(p -> {
                        try {
                            return Files.readAllLines(p).stream()
                                    .filter(l -> l.startsWith("import "))
                                    .filter(l -> prohibidos.stream().anyMatch(l::contains))
                                    .map(l -> p.getFileName() + " -> " + l);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .collect(Collectors.toList());
        }
    }

    @Test
    void elDominioNoDependeDeSpringNiJakartaNiDeOtrasCapas() throws IOException {
        List<String> violaciones = importsViolados("domain", List.of(
                "org.springframework", "jakarta.persistence", "jakarta.validation",
                "com.arq.app.application", "com.arq.app.infrastructure"));
        assertTrue(violaciones.isEmpty(), "El dominio no debe depender de frameworks ni de otras capas: " + violaciones);
    }

    @Test
    void laAplicacionSoloDependeDelDominio() throws IOException {
        List<String> violaciones = importsViolados("application", List.of(
                "org.springframework", "jakarta.", "com.arq.app.infrastructure"));
        assertTrue(violaciones.isEmpty(), "La aplicación solo debe conocer puertos del dominio: " + violaciones);
    }

    @Test
    void losControladoresNoAccedenALaPersistencia() throws IOException {
        List<String> violaciones = importsViolados("infrastructure/adapter/in", List.of(
                "adapter.out", "jakarta.persistence", "org.springframework.data", "Repository"));
        assertTrue(violaciones.isEmpty(), "Los adaptadores de entrada no deben usar repositorios ni JPA: " + violaciones);
    }

    @Test
    void laPersistenciaNoConoceLaCapaWeb() throws IOException {
        List<String> violaciones = importsViolados("infrastructure/adapter/out", List.of("adapter.in"));
        assertTrue(violaciones.isEmpty(), "Los adaptadores de salida no deben conocer la web: " + violaciones);
    }
}
