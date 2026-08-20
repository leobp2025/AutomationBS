package com.bipautomations.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class AutomatizacionesService {
    
    private static final Logger logger = LoggerFactory.getLogger(AutomatizacionesService.class);
    
    @Value("${autumatizaciones.excel.path}")
    private String excelPath;
    
    @Value("${autumatizaciones.output.path}")
    private String outputPath;
    
    @Value("${bip.sucursales.url}")
    private String bipUrl;
    
    @Value("${bip.usuario.default}")
    private String usuarioDefault;
    
    @Value("${bip.password.default}")
    private String passwordDefault;

    /**
     * Método genérico para guardar el archivo Excel enviado
     */
    private String guardarArchivoExcel(MultipartFile file) throws IOException {
        String nombreArchivo = "temp_" + System.currentTimeMillis() + ".xlsx";
        Path rutaDestino = Paths.get(excelPath, nombreArchivo);
        
        // Crear directorio si no existe
        Files.createDirectories(rutaDestino.getParent());
        
        // Guardar archivo
        Files.write(rutaDestino, file.getBytes());
        logger.info("Archivo guardado en: {}", rutaDestino);
        
        return rutaDestino.toString();
    }
    
    // MÉTODOS DE ALTA
    public void ejecutarAltaCajasAhorro(MultipartFile file) throws IOException {
        logger.info("Iniciando: Alta Cajas de Ahorro");
        String rutaArchivo = guardarArchivoExcel(file);
        ejecutarAutomatizacionSelenium("AltaCajaAhorro", rutaArchivo);
    }
    
    public void ejecutarAltaClientes(MultipartFile file) throws IOException {
        logger.info("Iniciando: Alta Clientes");
        String rutaArchivo = guardarArchivoExcel(file);
        ejecutarAutomatizacionSelenium("AltaCuentasCorriente", rutaArchivo);
    }
    
    public void ejecutarAltaMasivaTDBS(MultipartFile file) throws IOException {
        logger.info("Iniciando: Alta Masiva TDBS");
        String rutaArchivo = guardarArchivoExcel(file);
        ejecutarAutomatizacionSelenium("AltaMasivaTDBS", rutaArchivo);
    }
    
    public void ejecutarAltaPersonaHumana(MultipartFile file) throws IOException {
        logger.info("Iniciando: Alta Persona Humana");
        String rutaArchivo = guardarArchivoExcel(file);
        ejecutarAutomatizacionSelenium("AltaPersonaHumana", rutaArchivo);
    }
    
    public void ejecutarAltaPasivosPago(MultipartFile file) throws IOException {
        logger.info("Iniciando: Alta Pasivos Pago");
        String rutaArchivo = guardarArchivoExcel(file);
        ejecutarAutomatizacionSelenium("AltaPasivosPago", rutaArchivo);
    }
    
    // MÉTODOS DE OPERACIONES
    public void ejecutarOp02(MultipartFile file) throws IOException {
        ejecutarOperacion("Op02", file);
    }
    
    public void ejecutarOp03(MultipartFile file) throws IOException {
        ejecutarOperacion("Op03", file);
    }
    
    public void ejecutarOp1258(MultipartFile file) throws IOException {
        ejecutarOperacion("Op1258", file);
    }
    
    public void ejecutarOp1263(MultipartFile file) throws IOException {
        ejecutarOperacion("Op1263", file);
    }
    
    public void ejecutarOp502(MultipartFile file) throws IOException {
        ejecutarOperacion("Op502", file);
    }
    
    public void ejecutarOp505(MultipartFile file) throws IOException {
        ejecutarOperacion("Op505", file);
    }
    
    public void ejecutarOp5400(MultipartFile file) throws IOException {
        ejecutarOperacion("Op5400", file);
    }
    
    public void ejecutarOp7717(MultipartFile file) throws IOException {
        ejecutarOperacion("Op7717", file);
    }
    
    private void ejecutarOperacion(String nombreOperacion, MultipartFile file) throws IOException {
        logger.info("Iniciando operación: {}", nombreOperacion);
        String rutaArchivo = guardarArchivoExcel(file);
        ejecutarAutomatizacionSelenium(nombreOperacion, rutaArchivo);
    }
    
    /**
     * Ejecuta los tests TestNG usando Maven
     * IMPORTANTE: Asegúrate que tus clases de Selenium estén en src/test/java
     */
    private void ejecutarAutomatizacionSelenium(String nombreClase, String rutaExcel) throws IOException {
        logger.info("Ejecutando: {} con archivo: {}", nombreClase, rutaExcel);
        
        try {
            // Comando para ejecutar el test con Maven
            ProcessBuilder pb = new ProcessBuilder(
                "mvn", "test",
                "-Dtest=" + nombreClase,
                "-DexcelPath=" + rutaExcel
            );
            
            pb.redirectErrorStream(true);
            Process process = pb.start();
            
            // Leer salida
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream())
            );
            String line;
            while ((line = reader.readLine()) != null) {
                logger.info("[{}] {}", nombreClase, line);
            }
            
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new RuntimeException("Falló la ejecución de " + nombreClase);
            }
            
            logger.info("✅ {} completada exitosamente", nombreClase);
            
        } catch (InterruptedException e) {
            logger.error("Interrupción en la ejecución de {}", nombreClase, e);
            Thread.currentThread().interrupt();
            throw new RuntimeException("Ejecución interrumpida: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Error ejecutando {}", nombreClase, e);
            throw new IOException("Error en " + nombreClase + ": " + e.getMessage(), e);
        }
    }
}
