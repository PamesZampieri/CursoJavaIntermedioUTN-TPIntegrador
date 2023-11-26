package controladores;

import entidades.Cliente;
import entidades.Sesion;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Scanner;

public class ControladorIncidente {
    private EntityManager em;
    private Sesion sesion;

    Scanner entrada = new Scanner(System.in);

    public ControladorIncidente(EntityManager em, Sesion sesion) {
        this.em = em;
        this.sesion = sesion;
    }

    public ControladorIncidente() {
    }

    public void registrarIncidente() {
        if (!sesion.esUsuarioMesaAyuda()) {
            return;
        }

        System.out.println("Ingrese la razón social del cliente: ");
        String razonSocial = entrada.nextLine();

        System.out.println("Ingrese el cuil del cliente: ");
        String cuil = entrada.nextLine();

        List<Cliente> clientes = em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();

        Cliente clienteIncidente = null;
        for (Cliente cliente : clientes) {
            if(cliente.getCuil().equals(cuil)) {
                clienteIncidente = cliente;
            }
        }
    }
}
