import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import jade.core.*;
import jade.core.behaviours.OneShotBehaviour;
import jade.util.leap.Serializable;
public class AgenteSuma extends Agent
{
// Ejecutado por nica vez en la creacin
private String fileName;
private String computadora;
transient Profile profile;

@Override
public void setup()
{
    Object[] args = getArguments();
    if (args != null && args.length > 1) {
            // Verifica que se hayan proporcionado argumentos
        fileName = args[0].toString();
        computadora = args[1].toString();
    }
    else {
        System.out.println("Falta ingresar el archivo a leer y/o computadora" );
        doDelete(); // Elimino al tipo
    }

    // Get the JADE runtime interface (singleton)
    jade.core.Runtime runtime = jade.core.Runtime.instance();
    // Create a Profile, where the launch arguments are stored
    profile = new ProfileImpl();
    profile.setParameter(Profile.CONTAINER_NAME, computadora);
    profile.setParameter(Profile.MAIN_HOST, "localhost");
    // create a non-main agent container
    runtime.createAgentContainer(profile);  
    System.out.println("\n\n "+args);
	Location origen = here();
	System.out.println("\n\nHola, agente con nombre local " + getLocalName());
	System.out.println("Y nombre completo... " + getName());
	System.out.println("Y en location " + origen.getID() + "\n\n");
    try {
        Thread.sleep(2000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
// Para migrar el agente
try {
	Location destino = new ContainerID(computadora, null);
    System.out.println("INTENTO \n\n");
    System.out.println("Migrando a " + destino.getID() + "\n\n");
    doMove(destino);
} catch (Exception e) {
	System.out.println("No fue posible migrar el agente\n\n\n");}
}

// Ejecutado al llegar a un contenedor como resultado de una migracin
@Override
    protected void afterMove() {
        super.afterMove();//por las dudas
        addBehaviour(new CalculateSumBehaviour()); // agrego el comportamiento al tipo
    }
    //A lo laboratorio de software
    class CalculateSumBehaviour extends OneShotBehaviour implements Serializable   {
        //Esta clase gestiona el comportamiento del tipo
        //En este caso hace la suma
       
    
        @Override
        public void action() {
            try {
                String stringWithoutApostrophe = fileName.replace("'", "");
                FileReader fileReader = new FileReader(stringWithoutApostrophe);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                String line;
                int sum = 0;

                while ((line = bufferedReader.readLine()) != null) {
                    try {
                        String[] numbers = line.split(" ");
                        for (String numberStr : numbers) {
                            try {
                                int number = Integer.parseInt(numberStr);
                                sum += number;
                            } catch (NumberFormatException e) {
                                // Ignorar tokens que no son numeros enteros
                            }
                        }
                        
                    } catch (NumberFormatException e) {
                        // Ignorar lineas que no contienen numeros
                    }
                }

                bufferedReader.close();
                System.out.println("La suma de los numeros en el archivo es: " + sum);
            } catch (IOException e) {
                System.err.println("Error al leer el archivo: " + e.getMessage());
            }

            
        }
    }

}
