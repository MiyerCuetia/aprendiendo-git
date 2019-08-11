/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.holamundo;

import java.util.ArrayList;
import spark.Request;
import spark.Response;
import spark.Spark;

/**
 *
 * @author adsi1613314
 */
public class Principal {

    static ArrayList<Double> alturas;
    static ArrayList<String> ciudades;

    public static void main(String[] args) {
        //Define puerto
        Spark.port(80);

        //Habilitar CORS
        Spark.before(
                (request, response)
                -> response.header("Access Control-Allow Origin", "*")
        );
        //Primer web services
        Spark.get("/", (request, response) -> {
            return "Hola Mundo ...";
        });

        //Primer servicio web tipo get argumentos
        Spark.get("/saludar/:nombre", (rq, rs) -> {
            String nombre = rq.params("nombre");
            return "<b>Hola</b> " + nombre;
        });

        Spark.get("/ejercicio1/:numero", (rq, rs) -> {
            String num = rq.params("numero");
            double n1 = 0;
            try {
                n1 = Double.parseDouble(num);
            } catch (Exception e) {
                return "<h1 style='color:red'>Error: " + e.getMessage() + "</h1>";
            }
            //****
            String html = "";
            if (n1 > 0) {
                html += "<div style='background-color:green'>";
                html += "<h1 style='color:white'>El numero " + n1 + " es positivo.</h1>";
                html += "</div>";
            } else if (n1 < 0) {
                html += "<div style='background-color:red'>";
                html += "<h1 style='color:white'>El numero " + n1 + " es negativo.</h1>";
                html += "</div>";
            } else {
                html += "<div style='background-color:orange'>";
                html += "<h1 style='color:white'> El numero " + n1 + " es cero! </h1>";
                html += "</div'>";
            }
            return html;
        });

        Spark.get("/ejercicio2/:numcac/:precio", (rq, rs) -> {
            String numcac = rq.params("numcac");
            String precio = rq.params("precio");
            int n1 = 0;
            int n2 = 0;

            int totalCompra = 0;
            int totalAPagar = 0;

            try {
                n1 = Integer.parseInt(numcac);
                n2 = Integer.parseInt(precio);
            } catch (Exception e) {
                return "<h1 style='color:red'>Error: " + e.getMessage() + "</h1>";
            }
            totalCompra = n1 * n2;

            //****
            String html = "";
            if (n1 >= 3) {
                totalAPagar = totalCompra - (totalCompra * 20 / 100);
                html += "<div style='background-color:green; text-align:center'>";
                html += "<h1 style='color:white'>Total a pagar $" + totalAPagar + ", con descuento del 20%.</h1>";
                html += "</div>";
            } else if (n1 <= 2 & n1 > 0) {
                totalAPagar = totalCompra - (totalCompra * 10 / 100);
                html += "<div style='background-color:green; text-align:center'>";
                html += "<h1 style='color:white'>Total a pagar $" + totalAPagar + ", con descuento del 10%.</h1>";
                html += "</div>";
            } else {
                html += "<div style='background-color:red; text-align:center'>";
                html += "<h1 style='color:white'> Su compra no puede ser realizada; la cantidad digitada es:" + n1 + " ! </h1>";
                html += "</div'>";
            }
            return html;
        });

        Spark.get("/ejercicio3/:numHoras/:pagoHora", (rq, rs) -> {
            String numHoras = rq.params("numHoras");
            String pagoHora = rq.params("pagoHora");
            int horasTrabajas = 0;
            int pagoPorHora = 0;
            int totalPago = 0;

            try {
                horasTrabajas = Integer.parseInt(numHoras);
                pagoPorHora = Integer.parseInt(pagoHora);
            } catch (Exception e) {
                return "<h1 style='color:red'>Error: " + e.getMessage() + "</h1>";
            }

            totalPago = horasTrabajas * pagoPorHora;
            //****
            String html = "";

            if (horasTrabajas > 40) {
                totalPago += (horasTrabajas - 40) * pagoPorHora;
                html += "<div style='background-color:green; text-align:center'>";
                html += "<h1 style='color:white'>Valor a Recibir $" + totalPago + ", por horas trabajadas mayor 40.</h1>";
                html += "</div>";
            }
            if (horasTrabajas > 48) {
                totalPago += (horasTrabajas - 48) * pagoPorHora;
                html += "<div style='background-color:green; text-align:center'>";
                html += "<h1 style='color:white'>Valor a Recibir $" + totalPago + ", por horas trabajadas mayor 48.</h1>";
                html += "</div>";
            }

            return html;
        });

        Spark.get("/ejercicio4/:numero", (rq, rs) -> {
            String numero = rq.params("numero");

            int n1 = 0;
            int multiplicacion = 0;

            try {
                n1 = Integer.parseInt(numero);

            } catch (Exception e) {
                return "<h1 style='color:red'>Error: " + e.getMessage() + "</h1>";
            }

            //****
            String html = "";
            if (n1 > 0 & n1 <= 10) {
                for (int i = 1; i <= 13; i++) {

                    multiplicacion = n1 * i;

                    html += "<body style='color:orange; font-size:30px;'>" + multiplicacion + ", </body>";

                }
            }

            return html;
        });

        Spark.get("/ejercicio5/:numeroImp", (rq, rs) -> {
            String numeroImp = rq.params("numeroImp");

            int n1 = 0;

            int numf1 = 0;
            int numf2 = 1;
            int numf3;

            try {
                n1 = Integer.parseInt(numeroImp);

            } catch (Exception e) {
                return "<h1 style='color:red'>Error: " + e.getMessage() + "</h1>";
            }

            //****
            String html = "";
            html += "<body style='color:orange; font-size:30px;'>" + numf1 + ", " + numf2 + ", </body>";
            for (int i = 0; i <= n1 - 2; i++) {

                numf3 = numf1 + numf2;

                numf1 = numf2;
                numf2 = numf3;

                html += "<body style='color:orange; font-size:30px;'>" + numf3 + ", </body>";

            }

            return html;
        });

        ArrayList<Integer> numeros = new ArrayList<>();
        Spark.post("/agregardato", (rq, rs) -> {
            String body = rq.body();
            if (body.equals("")) {
                rs.status(403);
                return "El cuerpo esta llegando vacio";
            }

            int numero = 0;

            try {
                numero = Integer.parseInt(body);
            } catch (Exception e) {
                rs.status(500);
                return e.getMessage();
            }
            numeros.add(numero);
            return "El numero fue agregado con exito!";
        });

        alturas = new ArrayList<>();
        Spark.post("/alturas", (rq, rs) -> {
            String body = rq.body();
            //Validacion 1
            if (body.equals("")) {
                rs.status(403);
                return "El body no puede llegar vacio";
            }
            //Validacion 2
            double altura = 0;
            try {
                altura = Double.parseDouble(body);
            } catch (Exception e) {
                rs.status(500);
                return e.getMessage();
            }
            //Validacion 3
            if (alturas.size() < 5) {
                alturas.add(altura);
                return "Agregado con exito";
            } else {
                rs.status(403);
                return "No se puede agregar mas de 5";
            }

        });

        Spark.get("/alturas", (rq, rs) -> {
            if (alturas.size() < 5) {
                rs.status(500);
                return "No se han completado los 5 datos";
            }
            double sumatoria = 0, promedio = 0;
            int mayorp = 0, menorp = 0;

            for (double a : alturas) {
                sumatoria += a;
            }
            promedio = sumatoria / alturas.size();

            for (double a : alturas) {
                if (a > promedio) {
                    mayorp++;
                } else if (a < promedio) {
                    menorp++;
                }
            }

            return "Promedio de alturas: " + promedio + ", Cantidad de Mayores al Promedio: " + mayorp + ", Cantidad de Menores al Promedio: " + menorp;
        });

        ciudades = new ArrayList<>();
        ArrayList<String> gradosCiudades = new ArrayList<>();

        Spark.post("/registroGrados", (rq, rs) -> {
            String body = rq.body();
            //Validacion 1
            if (body.equals("")) {
                rs.status(403);
                return "El body no puede llegar vacio";
            }

            ciudades.add(body);
            System.out.println(ciudades);
            return "Agregado";

        });
        Spark.get("/obtenerCiudades", (Request rq, Response rs) -> {
            String body = rq.body();
            //Validacion 1
            if (ciudades.isEmpty()) {
                rs.status(403);
                return "Registre ciudades";
            } else {    

                /*primer ejercicio obtener primer ciudad y sus datos*/
                System.out.println(ciudades);
                String ciudad = ciudades.get(0);
                String[] datosCiudad = ciudad.split(",");
                String nombreCiudad = datosCiudad[0];
                System.out.println("La ciudad es: " + nombreCiudad);
                /*cierre de ejercicio 1*/

                String ejercicio1 = "La ciudad es: " + ciudad + "y la temperatura de la semana fue:"
                        + "\n Lunes: " + ciudades.get(1) + " grados"
                        + "\n Martes: " + ciudades.get(2) + " grados"   
                        + "\n Miercoles: " + ciudades.get(3) + " grados";
                
                System.out.println(ejercicio1);

                return ejercicio1 + "\n"
                        + "*******************************************";
            }

        });

    }
}
