package it.juan.user.entity;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name="habitacion")
public class Habitacion {
    @Schema(description = "Identificador de la habitación", example = "101", required = true)
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_Habitacion")
    private int idHabitacion;

    @Schema(description = "Identificador del hotel", example = "1", required = true)
    @ManyToOne
    @JoinColumn(name = "id_Hotel")
    @JsonIgnore
    private Hotel hotel;

    @Schema(description = "Capacidad de la habitación", example = "2")
    @Column(name="capacidad")
    private int capacidad;

    @Schema(description = "Precio de la habitación por noche", example = "150")
    @Column(name="precio_Noche")
    private double precio_Noche;

    @Schema(description = "Desayuno incluido", example = "1")
    @Column(name="incluye_Desayuno")
    private boolean incluye_Desayuno;

    @Schema(description = "Habitación ocupada", example = "0")
    @Column(name="ocupada")
    private boolean ocupada;


    public Habitacion() {
    }

    public Habitacion(int idHabitacion, int capacidad, double precio_Noche, boolean incluye_Desayuno, boolean ocupada, Hotel hotel) {
        this.idHabitacion = idHabitacion;
        this.capacidad = capacidad;
        this.precio_Noche = precio_Noche;
        this.incluye_Desayuno = incluye_Desayuno;
        this.ocupada = ocupada;
        this.hotel = hotel;
    }

    public int getIdHabitacion() {
        return idHabitacion;
    }

    public void setIdHabitacion(int idHabitacion) {
        this.idHabitacion = idHabitacion;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public double getPrecio() {
        return precio_Noche;
    }

    public void setPrecio(double precio_Noche) {
        this.precio_Noche = precio_Noche;
    }

    public boolean getDesayuno() {
        return incluye_Desayuno;
    }

    public void setDesayuno(boolean incluye_Desayuno) {
        this.incluye_Desayuno = incluye_Desayuno;
    }

    public boolean getOcupada() {
        return ocupada;
    }

    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
    //PARA QUE ME MUESTRE EL IDHOTEL AL BUSCAR UNA HABITACION
    @JsonProperty("idHotel")
    public int getIdHotel() {
        return hotel.getIdHotel();
    }

    @Override
    public String toString() {
        return "Habitacion{" +
                "idHabitacion=" + idHabitacion +
                ", capacidad=" + capacidad +
                ", precio=" + precio_Noche +
                ", desayuno=" + incluye_Desayuno +
                ", ocupada=" + ocupada +
                ", hotel=" +  hotel+
                '}';
    }
}
