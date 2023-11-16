import java.util.ArrayList;
import java.util.List;

import jade.core.*;

public class AgenteMovil2 extends Agent {
    private List<String> contenedores;
    private List<String> data;
    private final Integer cantidadContenedores = 5;
    private Integer actual = 0;
    private Integer limite = cantidadContenedores + 8;
    private Location origen;
    private Location origen_inicial;
    private Boolean fin = false;
    private long tiempoTotalRecorrido = 0;
    private long tiempoActual = 0;

    public void setup() {

        Location origen = here();
        origen_inicial = here();
        System.out.println("\n\nHola, agente con nombre local " + getLocalName());
        System.out.println("Y nombre completo... " + getName());
        System.out.println("Y en location " + origen.getID() + "\n\n");

        // Migrar el agente al primer contenedor de la lista
        contenedores = new ArrayList<String>();
        data = new ArrayList<String>();

        for (int i=0; i<5; i++){
            String name= "Contenedor-" + i;
            if(!name.equals(origen.getName())){
                contenedores.add(name);

                // Get the JADE runtime interface (singleton)
                jade.core.Runtime runtime = jade.core.Runtime.instance();
                // Create a Profile, where the launch arguments are stored
                Profile profile = new ProfileImpl();
                profile.setParameter(Profile.CONTAINER_NAME, name);
                profile.setParameter(Profile.MAIN_HOST, "localhost");
                // create a non-main agent container
                runtime.createAgentContainer(profile);
            }
        }

        contenedores.add(origen.getName());

        try {
            ContainerID primerDestino = new ContainerID(contenedores.get(this.actual++), null);
            System.out.println("Migrando el agente a " + primerDestino.getID());
            tiempoActual= System.nanoTime();
            doMove(primerDestino);
        } catch (Exception e) {
            System.out.println("No fue posible migrar el agente\n\n\n");
        }
    }

    protected void afterMove() {
        if(!fin){
            tiempoTotalRecorrido = tiempoTotalRecorrido + (System.nanoTime() - tiempoActual);
            // Simular la recopilacion de informacion
        }
        
        origen = here();
        //System.out.println("\n\nCola actual " + getLocalName());
        //System.out.println(contenedores);
        System.out.println("\n\nHola, agente migrado con nombre local " + getLocalName());
        System.out.println("Y nombre completo... " + getName());
        System.out.println("Y en location " + origen.getID() + "\n\n");
        //System.out.println("\n\nActual: " + actual);
        if(!fin){data.add(origen.getName()+" MEMORIA USADA: 100MB, CARGA: 70%, MEMORIA DISPONIBLE: 30MB");}
        
        // Miro para moverme al siguiente ubicacion
        if (actual < cantidadContenedores) {
            // No estoy en el ultimo contenedor
            try {
                ContainerID destino = new ContainerID(contenedores.get(this.actual++), null);
                System.out.println(destino);
                System.out.println("Migrando el agente a " + destino.getID());
                tiempoActual= System.nanoTime();
                doMove(destino);
            } catch (Exception e) {
                System.out.println("No fue posible migrar el agente\n\n\n");
            }
        } else {
            if (actual < limite ){
                //ContainerID destino = new ContainerID(origen.getID(), null);
                fin = true;
                actual = limite;
                doMove(this.origen_inicial);
            
            }
            else{
                System.out.println("Termine\n\n\n");
                System.out.println("Tiempo Total: "+tiempoTotalRecorrido+" \n\n\n");
                System.out.println(data);
            }
            
        }
    }



}