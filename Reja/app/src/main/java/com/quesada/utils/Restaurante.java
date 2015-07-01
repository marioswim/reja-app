package com.quesada.utils;

/**
 * Created by uni on 17/04/15.
 */
public class Restaurante {

    private String direccion;
    private Double rating;
    private Double userRating;
    private String nombre;
    private int telefono;
    private boolean terraza;

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    private double latitud;
    private double longitud;
    private int id;


    Restaurante()
    {
        this.direccion="";
        this.rating=0.0;
        this.nombre="";
        this.id=-1;
    }
/*
    public Restaurante(String nombre, String dir, double rating)
    {
        this.direccion=dir;
        this.rating= Double.toString(rating);
        this.nombre=nombre;
    }*/
    public Restaurante(String nombre, String dir, int id)
    {
        this.direccion=dir;
        this.id=id;
        this.nombre=nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Double getUserRating() {
        return userRating;
    }

    public void setUserRating(Double userRating) {
        this.userRating = userRating;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public boolean isTerraza() {
        return terraza;
    }

    public void setTerraza(boolean terraza) {
        this.terraza = terraza;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

