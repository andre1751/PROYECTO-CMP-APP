package com.example.demo.interfaces;

import java.util.List;

import com.example.demo.Entity.Reserva;

public interface ReservaInterface {

    /**
     * Devuelve todas las reservas de la base de datos.
     * @return Lista de todas las reservas.
     */
    List<Reserva> getAllReservas();

    /**
     * Devuelve la reserva correspondiente al nombre proporcionado.
     * Si no existe, devuelve null o lanza una excepción a discreción del implementador.
     * @param nombre El nombre asociado a la reserva (probablemente el nombre del huésped).
     * @return La reserva encontrada o null.
     */
    Reserva getReservaByNombre(String nombre);

    /**
     * Devuelve la reserva correspondiente al ID proporcionado.
     * Si no existe, devuelve null o lanza una excepción a discreción del implementador.
     * @param id El ID de la reserva.
     * @return La reserva encontrada o null.
     */
    Reserva getReservaById(Integer id);

    /**
     * Cancela (elimina) la reserva con el ID proporcionado.
     * @param id El ID de la reserva a cancelar.
     */
    void cancelarReserva(Integer id);

    /**
     * Actualiza la reserva con el ID proporcionado con los nuevos datos.
     * Sobreescribe lo que está en la base de datos.
     * @param id El ID de la reserva a actualizar.
     * @param nuevaReserva Los nuevos datos de la reserva.
     */
    void actualizarReserva(Integer id, Reserva nuevaReserva);

    /**
     * Crea una nueva reserva en la base de datos.
     * @param reserva Los datos de la nueva reserva.
     * @return La reserva creada.
     */
    Reserva crearReserva(Reserva reserva);
}