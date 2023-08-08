package org.falexgl;

import java.util.Scanner;

public class Main {

    private static ExtensionMan em = new ExtensionMan();

    public static void main(String[] args) {

        menu();
    }

    private static void menu(){

        Scanner teclado = new Scanner(System.in);
        boolean continuar = true;

        while(continuar) {
            System.out.println("INTRODUCE LA OPCIÓN DESEADA:");
            System.out.println("-------------------------------------------");
            System.out.println("    1. Listar archivos del directorio actual.");
            System.out.println("    2. Sustituir los archivos con una extensión determinada por otra.");
            System.out.println("    3. Añadir extensión a archivos sin extensión.");
            System.out.println("    4. Salir.");

            boolean opcionIncorrecta = true;
            boolean mantenerOriginal = true;
            String opcionMantener = "";
            String nuevaExtension = "";
            String confirmar = "";

            try{
                int opcion = teclado.nextInt();
                switch (opcion){
                    case 1:
                        em.listarArchivos(true);
                        System.out.println("Pulse cualquier tecla para continuar...");
                        nuevaExtension = teclado.nextLine();
                        System.out.println("");
                        break;
                    case 2:
                        teclado.nextLine();
                        System.out.println("Introduzca la extensión que quiere sustituir sin incluir el punto...");
                        String extensionActual = teclado.nextLine();
                        System.out.println("Introduzca la nueva extensión que sustituirá a '" + extensionActual + "'");
                        nuevaExtension = teclado.nextLine();

                        while(opcionIncorrecta){
                            System.out.println("¿Desea mantener una copia del archivo con su extensión original? (y/n)");
                            opcionMantener = teclado.nextLine();
                            if(opcionMantener.equals("y") || opcionMantener.equals("Y")){
                                mantenerOriginal = true;
                                opcionIncorrecta = false;
                            } else if(opcionMantener.equals("n") || opcionMantener.equals("N")){
                                mantenerOriginal = false;
                                opcionIncorrecta = false;
                            } else {
                                System.out.println("No ha seleccionado una opción correcta...");
                            }
                        }
                        opcionIncorrecta = true;

                        while(opcionIncorrecta){
                            opcionMantener = (mantenerOriginal) ? "MANTENER" : "ELIMINAR";
                            System.out.println("Se van a realizar los siguientes cambios en los archivos:");
                            System.out.println("    Extensión a sustituir: " + extensionActual);
                            System.out.println("    Extensión nueva: " + nuevaExtension);
                            System.out.println("    " + opcionMantener + " archivos originales.");
                            System.out.println("¿Desea continuar? (y/n)");

                            confirmar = teclado.nextLine();

                            if(confirmar.equals("y") || confirmar.equals("Y")){
                                em.modificarExtension(extensionActual, nuevaExtension, mantenerOriginal);
                                opcionIncorrecta = false;
                            } else if(confirmar.equals("n") || confirmar.equals("N")){
                                System.out.println("Operación cancelada...");
                                opcionIncorrecta = false;
                            } else {
                                System.out.println("No ha seleccionado una opción correcta...");
                            }
                        }
                        opcionIncorrecta = true;
                        break;
                    case 3:
                        teclado.nextLine();
                        System.out.println("Introduzca la extensión que se añadirá a los archivos sin extensión:");
                        nuevaExtension = teclado.nextLine();

                        while(opcionIncorrecta){
                            System.out.println("¿Desea mantener una copia del archivo original sin extensión? (y/n)");
                            opcionMantener = teclado.nextLine();
                            if(opcionMantener.equals("y") || opcionMantener.equals("Y")){
                                mantenerOriginal = true;
                                opcionIncorrecta = false;
                            } else if(opcionMantener.equals("n") || opcionMantener.equals("N")){
                                mantenerOriginal = false;
                                opcionIncorrecta = false;
                            } else {
                                System.out.println("No ha seleccionado una opción correcta...");
                            }
                        }
                        opcionIncorrecta = true;

                        while(opcionIncorrecta){
                            opcionMantener = (mantenerOriginal) ? "MANTENER" : "ELIMINAR";
                            System.out.println("Se van a realizar los siguientes cambios en los archivos:");
                            System.out.println("    A los archivos sin extensión se le añadirá la siguiente extensión: " + nuevaExtension);
                            System.out.println("    " + opcionMantener + " archivos originales.");
                            System.out.println("¿Desea continuar? (y/n)");

                            confirmar = teclado.nextLine();

                            if(confirmar.equals("y") || confirmar.equals("Y")){
                                em.modificarExtension("", nuevaExtension, mantenerOriginal);
                                opcionIncorrecta = false;
                            } else if(confirmar.equals("n") || confirmar.equals("N")){
                                System.out.println("Operación cancelada...");
                                opcionIncorrecta = false;
                            } else {
                                System.out.println("No ha seleccionado una opción correcta...");
                            }
                        }
                        opcionIncorrecta = true;

                        break;
                    case 4:
                        teclado.nextLine();
                        continuar = false;
                }

            } catch (Exception e){
                System.out.println("Por favor, introduzca una opción válida (sólo números del 1 al 4)\n");
            }

        }
    }
}