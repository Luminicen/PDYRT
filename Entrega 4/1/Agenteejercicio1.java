import java.util.ArrayList;
import java.util.List;

import jade.core.*;

public class Agenteejercicio1 extends Agent {
    private List<ContainerID> contenedores;
    private List<String> data;
    private final Integer cantidadContenedores = 5;
    private Integer actual = 0;
    private Location origen;
    private long tiempoTotalRecorrido = 0;
    private long tiempoActual = 0;



    public Agenteejercicio1() {
        super();
        contenedores = new ArrayList<ContainerID>();
        data = new ArrayList<String>();
        for (int i = 0; i < cantidadContenedores; i++) {
            contenedores.add(new ContainerID("Contenedor " + i, null));
        }
    }

    public void setup() {
        // Migrar el agente al primer contenedor de la lista
        ContainerID primerDestino = contenedores.get(0);
        System.out.println("\n\nHola, agente con nombre local " + getLocalName());
        System.out.println("Y nombre completo... " + getName());
        System.out.println("Y en location " + primerDestino.getID() + "\n\n");

        try {
            System.out.println("Migrando el agente a " + primerDestino.getID());
            tiempoActual= System.nanoTime();
            doMove(primerDestino);
        } catch (Exception e) {
            System.out.println("No fue posible migrar el agente\n\n\n");
        }
    }

    protected void afterMove() {
        tiempoTotalRecorrido = tiempoTotalRecorrido + (System.nanoTime() - tiempoActual);
        origen = here();
        System.out.println("\n\nHola, agente migrado con nombre local " + getLocalName());
        System.out.println("Y nombre completo... " + getName());
        System.out.println("Y en location " + origen.getID() + "\n\n");
        actual++;
         // Simular la recopilación de información 
        data.add(origen.getName());
        // Miro para moverme al siguiente ubicación
        if (actual < cantidadContenedores) {
            // No estoy en el último contenedor
            try {
                ContainerID destino = contenedores.get(actual);
                System.out.println("Migrando el agente a " + destino.getID());
                tiempoActual= System.nanoTime();
                doMove(destino);
            } catch (Exception e) {
                System.out.println("No fue posible migrar el agente\n\n\n");
            }
        } else {
            // Estoy en el último
            System.out.println("Termine\n\n\n");
            System.out.println(data);
        }
    }
    
   
   
}