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
        Spark.post("/persona", (rq, rs) -> {

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
            genero = datos[1].charAt(0);
            //*****
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
            isCasados.add(b);
            isEstudia.add(b);
            gastos.add(gastosd);

            return "Paso!";

        });

    }
}
