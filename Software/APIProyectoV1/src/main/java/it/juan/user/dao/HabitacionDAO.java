package it.juan.user.dao;

import java.util.List;
import it.juan.user.entity.Habitacion;


public interface HabitacionDAO {

    //Métodos para Hotel
    public List<Habitacion> findHabitacionAll();

    public Habitacion findByIdHabitacion(int idHabitacion);

    public void saveHabitacion(Habitacion habitacion);

    public void deleteHabitacionById(int idHabitacion);

    List<Habitacion> findByCapacidadAndOcupadaOrderByPrecioNocheAsc(int capacidad, int ocupada);

    public List<Habitacion> habitaciones_Tamano_Precio(double capacidad_Minima, double capacidad_Maxima, double precio_Minimo, double precio_Maximo);

}
