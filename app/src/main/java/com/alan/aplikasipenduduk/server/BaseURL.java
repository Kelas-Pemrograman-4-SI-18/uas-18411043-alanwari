package com.alan.aplikasipenduduk.server;

public class BaseURL {

    public static String baseUrl = "http://192.168.43.26:5050/";

    public static String login   = baseUrl + "user/login";
    public static String register = baseUrl + "user/registrasi";


    //    KONTEN
    public static String dataWarga = baseUrl + "Data/data";
    public static String editDataWarga = baseUrl + "Data/ubah/";
    public static String hapusDataWarga = baseUrl + "Data/hapus/";
    public static String inputDataWarga = baseUrl + "Data/input/";
}
