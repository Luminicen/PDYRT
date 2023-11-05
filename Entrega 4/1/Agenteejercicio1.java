import java.util.ArrayList;
import java.util.List;

import jade.core.*;
public class Agenteejercicio1 extends Agent {
private List<ContainerID> contenerdores;
private final Integer cantidadContenedores = 5;
private Integer actual = 0;
private Location origen;
public Agenteejercicio1(){
    super();
    contenerdores = new ArrayList<ContainerID>();
    for(int i = 0; i<cantidadContenedores ; i++){
        contenerdores.add(new ContainerID("Contenedor "+i, null));
    }
}
public void setup()
{
	Location origen = here();
	System.out.println("\n\nHola, agente con nombre local " + getLocalName());
	System.out.println("Y nombre completo... " + getName());
	System.out.println("Y en location " + origen.getID() + "\n\n");
// Para migrar el agente
try {
	ContainerID destino = contenerdores.get(actual);
	System.out.println("Migrando el agente a " + destino.getID());
	doMove(destino);
} catch (Exception e) {
	System.out.println("No fue posible migrar el agente\n\n\n");}
}

// Ejecutado al llegar a un contenedor como resultado de una migracin
protected void afterMove()
{
	origen = here();
	System.out.println("\n\nHola, agente migrado con nombre local " + getLocalName());
	System.out.println("Y nombre completo... " + getName());
	System.out.println("Y en location " + origen.getID() + "\n\n");
    actual ++;
    //Miro para moverme a la siguiente ubicacion
    if (actual < (cantidadContenedores - 1)){
        //no estoy en el ultimo contenedor
        // Para migrar el agente
        try {
	    ContainerID destino = contenerdores.get(actual);
	    System.out.println("Migrando el agente a " + destino.getID());
	    doMove(destino);
        } catch (Exception e) {
	        System.out.println("No fue posible migrar el agente\n\n\n");
        }
    if(actual == (cantidadContenedores - 1)){
        try {
        actual = -1;
	    ContainerID destino = contenerdores.get(actual);
	    System.out.println("Migrando el agente a " + destino.getID());
	    doMove(destino);
        } catch (Exception e) {
	        System.out.println("No fue posible migrar el agente\n\n\n");
        }
        //estoy en el ultimo
    }
    if(actual == -1){
        System.out.println("Termine\n\n\n");
    }


    }
}
}
