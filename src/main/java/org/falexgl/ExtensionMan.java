package org.falexgl;

import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ExtensionMan {

    private static HashMap<String, ArrayList<String>> agrupacionExtensiones = null;
    public void listarArchivos(boolean mostrarContenido) {

        int totalArchivos = 0;

        String directorioActual = System.getProperty("user.dir");
        File directorio = new File(directorioActual);
        File[] contenidoDirectorio = directorio.listFiles();

        if (contenidoDirectorio.length <= 1) {
            System.out.println("La carpeta " + System.getProperty("user.dir") + " no contiene más archivos.");
        } else {
            agrupacionExtensiones = new HashMap<>();
            ArrayList<String> grupo;
            for (File f : contenidoDirectorio) {
                String extension = FilenameUtils.getExtension(f.getName());
                if (!agrupacionExtensiones.containsKey(extension) && f.isFile()) {
                    grupo = new ArrayList<>();
                    grupo.add(f.getName());
                    agrupacionExtensiones.put(extension, grupo);
                    totalArchivos++;
                } else if (f.isFile()) {
                    grupo = agrupacionExtensiones.get(extension);
                    grupo.add(f.getName());
                    agrupacionExtensiones.put(extension, grupo);
                    totalArchivos++;
                }
            }
        }

        if(mostrarContenido) {
            System.out.println("CONTENIDO DE LA CARPETA: " + System.getProperty("user.dir") + ": \n");
            System.out.printf("%d archivos encontrados.\n\n", totalArchivos);

            for (Map.Entry<String, ArrayList<String>> entry : agrupacionExtensiones.entrySet()) {
                if (entry.getKey().equals("")) {
                    System.out.printf("%d archivos sin extensión:\n", entry.getValue().size());
                    listarArchivosMismaExtension(entry.getValue());
                } else {
                    System.out.printf("%d archivos con extensión \"%s\":\n", entry.getValue().size(), entry.getKey());
                    listarArchivosMismaExtension(entry.getValue());
                }
            }
            mostrarContenido = false;
        }
    }

    public void modificarExtension(String extensionActual, String nuevaExtension, boolean mantenerOriginal) {
        listarArchivos(false);
        if (!agrupacionExtensiones.containsKey(extensionActual) && !extensionActual.equals("")) {
            System.out.println("No existen archivos con la extensión " + extensionActual);
        } else {
            ArrayList<String> archivos = agrupacionExtensiones.get(extensionActual);

            if(mantenerOriginal){
                mantenerArchivo(archivos, extensionActual, nuevaExtension);
            } else {
                sustituirArchivo(archivos, extensionActual, nuevaExtension);
            }
        }
    }

    private void listarArchivosMismaExtension(ArrayList<String> grupoExtension) {
        for (String archivo : grupoExtension) {
            System.out.println("   " + archivo);
        }
    }

    private void sustituirArchivo(ArrayList<String> archivos, String extensionActual, String nuevaExtension){
        int archivosModificados = 0;
        String erroresEncontrados = "";
        String archivosSustituidos = "";

        for (int i = 0; i < archivos.size(); i++) {

            //Archivo con el antiguo nombre
            File archivoAntiguo = new File(archivos.get(i));

            //Archivo con el nombre antiguo
            int tamanoNombreSinExtension = archivos.get(i).length() - extensionActual.length();
            String nombreNuevo = archivos.get(i).substring(0, tamanoNombreSinExtension) + nuevaExtension;
            File archivoNuevo = new File(nombreNuevo);

            if (archivoNuevo.exists()) {
                erroresEncontrados += "  " + archivos.get(i) + " no se ha modificado: Ya existe archivo con la extensión " + nuevaExtension + "\n";
            } else {
                try {
                    archivoAntiguo.renameTo(archivoNuevo);
                    archivosSustituidos += "  " + archivos.get(i);
                    archivosModificados++;
                } catch (Exception e) {
                    erroresEncontrados += "  " + archivos.get(i) + " no se ha modificado: Error con cambio de extensión" + "\n";
                }
            }
        }

        System.out.printf("%d ARCHIVOS CORRECTAMENTE SUSTITUIDOS:", archivosModificados);
        System.out.println(archivosSustituidos);

        if(!erroresEncontrados.equals("")){
            System.out.println("ERRORES ENCONTRADOS:");
            System.out.println(erroresEncontrados);
        }
    }

    private void mantenerArchivo(ArrayList<String> archivos, String extensionActual, String nuevaExtension){
        int archivosModificados = 0;
        String erroresEncontrados = "";
        String archivosSustituidos = "";

        for (int i = 0; i < archivos.size(); i++) {
            InputStream is = null;
            OutputStream os = null;
            try {
                is = new FileInputStream(archivos.get(i));

                int tamanoNombreSinExtension = archivos.get(i).length() - extensionActual.length();
                String nombreNuevo = archivos.get(i).substring(0, tamanoNombreSinExtension) + nuevaExtension;
                os = new FileOutputStream(nombreNuevo);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
            } catch (IOException e) {
                erroresEncontrados += "  " + archivos.get(i) + ": No se ha generado nuevo archivo con extensión" + "\n";
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    erroresEncontrados += "  " + archivos.get(i) + ": Error cerrando el archivo." + "\n";
                }
                try {
                    os.close();
                } catch (IOException e) {
                    erroresEncontrados += "  " + archivos.get(i) + ": Error cerrando el nuevo archivo generado." + "\n";
                }
            }
            System.out.printf("%d ARCHIVOS CORRECTAMENTE SUSTITUIDOS:", archivosModificados);
            System.out.println(archivosSustituidos);

            if(!erroresEncontrados.equals("")){
                System.out.println("ERRORES ENCONTRADOS:");
                System.out.println(erroresEncontrados);
            }
        }
    }
}
