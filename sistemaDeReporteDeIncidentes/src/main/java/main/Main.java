package main;

import controladores.ControladorClientes;
import entidades.Rol;
import entidades.Sesion;
import entidades.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.sql.SQLException;

public class Main {
    public static EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPA_PU");
        return factory.createEntityManager();
    }

    public static void main(String[] args) throws SQLException {
        EntityManager em = getEntityManager();

        //Técnico
        //Rol rol = new Rol("RRHH", null);
        //Usuario usuario = new Usuario("lpavan", "123", rol);
        //Sesion sesion = new Sesion(null, null, usuario);
        //ControladorTecnicos controladorTecnicos = new ControladorTecnicos(em, sesion);
        //controladorTecnicos.altaTecnico();
        //controladorTecnicos.bajaTecnico();
        //controladorTecnicos.editarTecnico();

        //Cliente
        Rol rol1 = new Rol("Area comercial", null);
        Usuario usuario = new Usuario("lpavan", "123", rol1);
        Sesion sesion = new Sesion(null, null, usuario);
        ControladorClientes controladorClientes = new ControladorClientes(em, sesion);
        //controladorClientes.altaCliente();
        //controladorClientes.bajaCliente();
        //controladorClientes.editarCliente();


        //Incidente
        Rol rol2 = new Rol("Mesa de ayuda", null);

    }
}