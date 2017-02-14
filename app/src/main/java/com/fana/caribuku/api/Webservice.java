package com.fana.caribuku.api;

/**
 * Created by arifcebe on 5/25/16.
 */
public class Webservice {

    private static final String URL = "http://niya.ga/cari-buku/bo/";

    private static Webservice ourInstance = new Webservice();

    public static Webservice getInstance() {
        return ourInstance;
    }

    private Webservice() {
    }


}
