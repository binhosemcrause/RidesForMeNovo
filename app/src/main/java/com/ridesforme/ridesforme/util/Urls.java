package com.ridesforme.ridesforme.util;

/**
 * Created by Robson on 26/10/2015.
 */
public class Urls {
    //ftp://ridesforme.no-ip.info/

    public static final String webservice = "http://ridesforme.no-ip.info:8080";

    //Metodos webservice
    public static final String AllCarona = webservice+"/rpg/carona/getAllCarona";
    public static final String login = webservice+"/rpg/usuario/login";
    public static final String getCaronaFiltro = webservice+"/rpg/carona/getCaronaFiltro";
    public static final String getCaronaFiltroData = webservice+"/rpg/carona/getCaronaFiltroData";
    public static final String getCaronaFiltroDataHora = webservice+"/rpg/carona/getCaronaFiltroDataHora";
    public static final String cadastroUsuario = webservice+"/rpg/usuario/cadastrarUsuario";
    public static final String cadastrarViagem = webservice+"/rpg/carona/cadastrarViagem";
}
