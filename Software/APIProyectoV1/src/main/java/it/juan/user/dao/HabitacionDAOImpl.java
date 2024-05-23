package it.juan.user.dao;

import it.juan.user.entity.Habitacion;

import javax.persistence.EntityManager;
import java.util.List;

import it.juan.user.entity.Hotel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HabitacionDAOImpl implements HabitacionDAO{
    @Autowired
    private EntityManager entityManager;
    @Override
    public List<Habitacion> findHabitacionAll() {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<Habitacion> theQuery = currentSession.createQuery("SELECT u from Habitacion u", Habitacion.class);

        List<Habitacion> habitaciones = theQuery.getResultList();

        return habitaciones;
    }

    @Override
    public Habitacion findByIdHabitacion(int idHabitacion) {
        Session currentSession = entityManager.unwrap(Session.class);

        Habitacion habitacion = currentSession.get(Habitacion.class, idHabitacion);

        return habitacion;

    }

    @Override
    public void saveHabitacion(Habitacion habitacion) {
        Session currentSession = entityManager.unwrap(Session.class);
        Transaction t = currentSession.beginTransaction();
        currentSession.saveOrUpdate(habitacion);
        t.commit();
        currentSession.close();

    }

    @Override
    public void deleteHabitacionById(int idHabitacion) {
        Session currentSession = entityManager.unwrap(Session.class);
        Transaction t = currentSession.beginTransaction();
        Query theQuery = currentSession.createQuery("delete from Habitacion u where id IN (:idHabitacion)");

        theQuery.setParameter("idHabitacion", idHabitacion);
        theQuery.executeUpdate();
        t.commit();
        currentSession.close();

    }

    @Override
    public List<Habitacion> findByCapacidadAndOcupadaOrderByPrecioNocheAsc(int capacidad, int ocupada) {
        Session currentSession = entityManager.unwrap(Session.class);

        Query<Habitacion> query = currentSession.createQuery("FROM Habitacion WHERE capacidad = :capacidad AND ocupada = :ocupada ORDER BY precio_Noche ASC", Habitacion.class);
        query.setParameter("capacidad", capacidad);
        query.setParameter("ocupada", ocupada);

        return query.getResultList();
    }

    @Override
    public List<Habitacion> habitaciones_Tamano_Precio(double capacidad_Minima, double capacidad_Maxima, double precio_Minimo, double precio_Maximo) {
        Session currentSession = entityManager.unwrap(Session.class);

        String hql = "FROM Habitacion h WHERE h.capacidad BETWEEN :capacidad_Minima AND :capacidad_Maxima " +
                "AND h.precio_Noche BETWEEN :precio_Minimo AND :precio_Maximo AND h.ocupada = false";

        Query<Habitacion> query = currentSession.createQuery(hql, Habitacion.class);
        query.setParameter("capacidad_Minima", capacidad_Minima);
        query.setParameter("capacidad_Maxima", capacidad_Maxima);
        query.setParameter("precio_Minimo", precio_Minimo);
        query.setParameter("precio_Maximo", precio_Maximo);

        return query.getResultList();
    }
}
