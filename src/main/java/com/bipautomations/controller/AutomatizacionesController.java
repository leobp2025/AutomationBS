package com.bipautomations.controller;

import com.bipautomations.service.AutomatizacionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/automatizaciones")
@CrossOrigin(origins = "*")
public class AutomatizacionesController {

    @Autowired
    private AutomatizacionesService automatizacionesService;

    @PostMapping("/ejecutar/{tipo}")
    public ResponseEntity<?> ejecutarAutomatizacion(
            @PathVariable String tipo,
            @RequestParam("file") MultipartFile file) {
        
        try {
            Map<String, Object> resultado = new HashMap<>();
            
            switch (tipo.toUpperCase()) {
                case "ALTA_CAJAS_AHORRO":
                    automatizacionesService.ejecutarAltaCajasAhorro(file);
                    resultado.put("mensaje", "Alta de Cajas de Ahorro ejecutada");
                    break;
                    
                case "ALTA_CLIENTES":
                    automatizacionesService.ejecutarAltaClientes(file);
                    resultado.put("mensaje", "Alta de Clientes ejecutada");
                    break;
                    
                case "ALTA_MASIVA_TDBS":
                    automatizacionesService.ejecutarAltaMasivaTDBS(file);
                    resultado.put("mensaje", "Alta Masiva TDBS ejecutada");
                    break;
                    
                case "ALTA_PERSONA_HUMANA":
                    automatizacionesService.ejecutarAltaPersonaHumana(file);
                    resultado.put("mensaje", "Alta Persona Humana ejecutada");
                    break;
                    
                case "ALTA_PASIVOS_PAGO":
                    automatizacionesService.ejecutarAltaPasivosPago(file);
                    resultado.put("mensaje", "Alta Pasivos Pago ejecutada");
                    break;
                    
                case "OP02":
                    automatizacionesService.ejecutarOp02(file);
                    resultado.put("mensaje", "Operación OP02 ejecutada");
                    break;
                    
                case "OP03":
                    automatizacionesService.ejecutarOp03(file);
                    resultado.put("mensaje", "Operación OP03 ejecutada");
                    break;
                    
                case "OP1258":
                    automatizacionesService.ejecutarOp1258(file);
                    resultado.put("mensaje", "Operación OP1258 ejecutada");
                    break;
                    
                case "OP1263":
                    automatizacionesService.ejecutarOp1263(file);
                    resultado.put("mensaje", "Operación OP1263 ejecutada");
                    break;
                    
                case "OP502":
                    automatizacionesService.ejecutarOp502(file);
                    resultado.put("mensaje", "Operación OP502 ejecutada");
                    break;
                    
                case "OP505":
                    automatizacionesService.ejecutarOp505(file);
                    resultado.put("mensaje", "Operación OP505 ejecutada");
                    break;
                    
                case "OP5400":
                    automatizacionesService.ejecutarOp5400(file);
                    resultado.put("mensaje", "Operación OP5400 ejecutada");
                    break;
                    
                case "OP7717":
                    automatizacionesService.ejecutarOp7717(file);
                    resultado.put("mensaje", "Operación OP7717 ejecutada");
                    break;
                    
                default:
                    return ResponseEntity.badRequest().body(
                        Map.of("error", "Tipo de automatización desconocido: " + tipo)
                    );
            }
            
            resultado.put("detalles", "Archivo procesado: " + file.getOriginalFilename());
            return ResponseEntity.ok(resultado);
            
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(
                Map.of("error", "Error al procesar el archivo: " + e.getMessage())
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                Map.of("error", "Error durante la ejecución: " + e.getMessage())
            );
        }
    }
    
    @GetMapping("/estado")
    public ResponseEntity<?> obtenerEstado() {
        return ResponseEntity.ok(Map.of(
            "estado", "En línea",
            "automatizaciones", 13
        ));
    }
}
