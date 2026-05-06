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
                String linea = lector.nextLine();
                // Suponemos formato: PID;Arribo;Rafaga
                String[] datos = linea.split(";");
                
                int pid = Integer.parseInt(datos[0]);
                int arribo = Integer.parseInt(datos[1]);
                int rafaga = Integer.parseInt(datos[2]);
                
                lista.add(new Proceso(pid, arribo, rafaga));
            }
            lector.close();
        } catch (FileNotFoundException e) {
            System.err.println("Error: No se encontro el archivo en " + ruta);
        } catch (Exception e) {
            System.err.println("Error al procesar el archivo: " + e.getMessage());
        }
        return lista;
    }
}