package br.com.belapp.belapp.presenter;

public class LocalizacaoCliente {
    //Serve para guardar em um objeto a localização do cliente obtida por GPS
    private double latitude;
    private double longitude;

    public LocalizacaoCliente() {

    }

    public LocalizacaoCliente(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
