/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.holamundo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;
import spark.Request;
import spark.Response;
import spark.Spark;

/**
 *
 * @author MIYER
 */
public class Vectores {

    public static String espString = "^([A-Za-zÁÉÍÓÚñáéíóúÑ]{0}?[A-Za-zÁÉÍÓÚñáéíóúÑ\\']+[\\s])+([A-Za-zÁÉÍÓÚñáéíóúÑ]{0}?[A-Za-zÁÉÍÓÚñáéíóúÑ\\'])+[\\s]?([A-Za-zÁÉÍÓÚñáéíóúÑ]{0}?[A-Za-zÁÉÍÓÚñáéíóúÑ\\'])?$";

    public static void main(String[] args) {
        //Estructura de datos, vectores paralelos        

        ArrayList<String> nombres = new ArrayList<>();
        ArrayList<Character> generos = new ArrayList<>();
        ArrayList<Date> fechas = new ArrayList<>();
        ArrayList<Boolean> isCasados = new ArrayList<>();
        ArrayList<Boolean> isEstudia = new ArrayList<>();
        ArrayList<Double> gastos = new ArrayList<>();

        //Define puerto
        Spark.port(80);

        //Habilitar CORS
        Spark.before(
                (request, response)
                -> response.header("Access Control-Allow Origin", "*")
        );
        //servicio POST
        Spark.post("/persona", (Request rq, Response rs) -> {

            String body = rq.body();
            //******
            if (body.isEmpty()) {
                rs.status(400);
                return "Ingresar datos en el body";
            }
            //******
            String[] datos = body.split(",");
            if (datos.length != 6) {
                rs.status(400);
                return "No se ingresaron los 6 datos";
            }
            //*****
            String nombre = datos[0];
            boolean b = Pattern.matches(espString, nombre);
            if ((!Pattern.matches(espString, nombre))) {
                rs.status(400);
                return "Validar info del nombre";
            }
            //*****
            char genero;
            if (datos[1].length() != 1) {
                rs.status(400);
                return "Validar info del genero";
            }
            genero = datos[1].toLowerCase().charAt(0);
            //*****
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String strDate = datos[2];
            Date fecha = new Date();

            try {
                fecha = sdf.parse(strDate);
            } catch (Exception e) {
                rs.status(500);
                return e.getMessage();
            }
            //*****
            if (datos[3].isEmpty()) {
                rs.status(400);
                return "Validar info de casado";
            }
            boolean isCasado = Boolean.parseBoolean(datos[3]);
            //*******
            if (datos[4].isEmpty()) {
                rs.status(400);
                return "Validar info de estudiante";
            }
            boolean isEstudiante = Boolean.parseBoolean(datos[4]);

            //********
            double gastosd;
            try {
                gastosd = Double.parseDouble(datos[5]);
            } catch (Exception e) {
                rs.status(500);
                return e.getMessage();
            }
            //********
            nombres.add(nombre);
            generos.add(genero);
            fechas.add(fecha);
            isCasados.add(isCasado);
            isEstudia.add(isEstudiante);
            gastos.add(gastosd);

            return "Registrado Exitosamene!";

        });
        Spark.get("/persona/:genero", (rq, rs) -> {
            String strGenero = rq.params("genero").toLowerCase();

            char genero = strGenero.equals("m")
                    || strGenero.equals("f")
                            ? strGenero.charAt(0) : '*';
            System.out.println(genero);
            if (genero == '*') {
                rs.status(400);
                return "El genero no existe m|f";
            }
            System.out.println(generos.size());
            String html = "<table border=1>";
            for (int i = 0; i < generos.size(); i++) {
                char g = generos.get(i);
                if (g == genero) {
                    html += "<tr>";
                    html += "<td>" + nombres.get(i) + "</td>";
                    html += "<td>" + generos.get(i) + "</td>";
                    html += "<td>" + fechas.get(i) + "</td>";
                    html += "<td>" + isCasados.get(i) + "</td>";
                    html += "<td>" + isEstudia.get(i) + "</td>";
                    html += "<td>" + gastos.get(i) + "</td>";
                    html += "</tr>";
                }
            }
            html += "</table>";
            return html;
        });

        Spark.get("/persona/ordenarEdades/", (rq, rs) -> {
            ArrayList<Date> fechasAux = new ArrayList<>(fechas);
            ArrayList<Integer> indices = new ArrayList<>();

            for (int i = 0; i < fechasAux.size(); i++) {
                indices.add(i);
            }
            for (int i = 1; i < fechasAux.size(); i++) {
                for (int j = fechasAux.size() - 1; j >= i; j--) {
                    if (fechasAux.get(j).getTime() > fechasAux.get(j - 1).getTime()) {
                        Date aux = fechasAux.get(j);
                        fechasAux.set(j, fechasAux.get(j - 1));
                        fechasAux.set(j - 1, aux);
                        //********
                        int auxInd = indices.get(j);
                        indices.set(j, indices.get(j - 1));
                        indices.set(j - 1, auxInd);
                    }
                }
            }
            String html = "<table border=1>";
            //for (Integer i : indices) {
            //for (int x = 0; x < indices.size(); x++) {
            for (int x = indices.size() - 1; x >= 0; x--) {
                int i = indices.get(x);
                html += "<tr>";
                html += "<td>" + nombres.get(i) + "</td>";
                html += "<td>" + generos.get(i) + "</td>";
                html += "<td>" + fechas.get(i) + "</td>";
                html += "<td>" + isCasados.get(i) + "</td>";
                html += "<td>" + isEstudia.get(i) + "</td>";
                html += "<td>" + gastos.get(i) + "</td>";
                html += "</tr>";
            }
            html += "</table>";
            return html;

        });

        Spark.get("/persona/ordenarGastos/", (rq, rs) -> {
            ArrayList<Double> gastosAux = new ArrayList<>(gastos);
            ArrayList<Integer> indices = new ArrayList<>();

            for (int i = 0; i < gastosAux.size(); i++) {
                indices.add(i);
            }
            for (int i = 1; i < gastosAux.size(); i++) {
                for (int j = gastosAux.size() - 1; j >= i; j--) {
                    if (gastosAux.get(j) > gastosAux.get(j - 1)) {
                        double aux = gastosAux.get(j);
                        gastosAux.set(j, gastosAux.get(j - 1));
                        gastosAux.set(j - 1, aux);
                        //********
                        int auxInd = indices.get(j);
                        indices.set(j, indices.get(j - 1));
                        indices.set(j - 1, auxInd);
                    }
                }
            }
            String html = "<table border=1>";
            //for (Integer i : indices) {
            for (int x = 0; x < indices.size(); x++) {
                //for (int x = indices.size() - 1; x >= 0; x--) {
                int i = indices.get(x);
                html += "<tr>";
                html += "<td>" + nombres.get(i) + "</td>";
                html += "<td>" + generos.get(i) + "</td>";
                html += "<td>" + fechas.get(i) + "</td>";
                html += "<td>" + isCasados.get(i) + "</td>";
                html += "<td>" + isEstudia.get(i) + "</td>";
                html += "<td>" + gastos.get(i) + "</td>";
                html += "</tr>";
            }
            html += "</table>";
            return html;

        });
        Spark.delete("/persona/obtenerPersona/:id", (rq, rs) -> {
            String numId = rq.params("id");
            int id = 0;
            try {
                id = Integer.parseInt(numId);
            } catch (Exception e) {
                return "<h1 style='color:red'>Error: " + e.getMessage() + "</h1>";
            }

            String html = "<table border=1>";

            int pos = -1;
            for (int i = 0; i < nombres.size(); i++) {
                pos = i;
                
                if (pos == id) {
                    html += "<tr>";
                    
                    html += "<td>" + nombres.remove(pos) + "</td>";
                    html += "<td>" + generos.remove(pos) + "</td>";
                    html += "<td>" + fechas.remove(pos) + "</td>";
                    html += "<td>" + isCasados.remove(pos) + "</td>";
                    html += "<td>" + isEstudia.remove(pos) + "</td>";
                    html += "<td>" + gastos.remove(pos) + "</td>";
                    html += "</tr>";                                        
                   
                }else{
                rs.status(400);
                return "El id: "+id+" no existe.";                
                }
            }
            html += "</table>";
            return "Se eliminado exitosamente! <br></br>"+ html;
        });

    }
}
