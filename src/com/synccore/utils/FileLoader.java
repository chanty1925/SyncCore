package com.synccore.utils;

import com.synccore.models.Proceso;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileLoader {
    public static ArrayList<Proceso> cargarProcesos(String ruta) {
        ArrayList<Proceso> lista = new ArrayList<>();
        try {
            File archivo = new File(ruta);
            Scanner lector = new Scanner(archivo);
            
            while (lector.hasNextLine()) {
                //Se quita espacios basura a la linea
                String linea = lector.nextLine().trim(); 
                
                // Si la linea está completamente vacía, nos la saltamos
                if (linea.isEmpty()) {
                    continue; 
                }
                
                String[] datos = linea.split(";");
                
                int pid = Integer.parseInt(datos[0]);
                int arribo = Integer.parseInt(datos[1]);
                int rafaga = Integer.parseInt(datos[2]);
                
                //  El archivo trae 3 o 4 columnas?
                if (datos.length >= 4) {
                    // Tiene prioridad
                    int prioridad = Integer.parseInt(datos[3]);
                    lista.add(new Proceso(pid, arribo, rafaga, prioridad));
                } else {
                    // No tiene prioridad, usamos el constructor normal
                    lista.add(new Proceso(pid, arribo, rafaga));
                }
            }
            lector.close();
        } catch (FileNotFoundException e) {
            System.err.println("Error: No se encontro el archivo en " + ruta);
        } catch (Exception e) {
            System.err.println("Error fatal al leer la linea: " + e.getMessage());
        }
        return lista;
    }
}