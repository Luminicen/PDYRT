import jade.core.*;
import jade.core.behaviours.Behaviour;

public class AgenteMovil extends Agent {
	private String sourceHost;
	private String targetHost;
	private String sourcePath;
	private String targetPath;
	private int amount;

	//Ejecutado por unica vez en la creacion
	public void setup() {
		Location origen = here();
		System.out.println("\n\nHola, agente con nombre local " + getLocalName());
		System.out.println("Y nombre completo... " + getName());
		System.out.println("Y en location " + origen.getID() + "\n\n");

		Object[] args = getArguments();

		try {
			if (args.length < 4) {
				System.out.println("Ingresar 1: host destino 2: path origen, 3: path destino, 4: cantidad bytes a copiar");
				System.exit(1);
			}

			//inicializacion de variables
			this.sourceHost = here().getName();
			this.targetHost = args[1].toString();
			this.sourcePath = args[2].toString();
			this.targetPath = args[3].toString();
			this.amount = parseInt.args[4];

			//comportamiento

		} catch (Exception e) {
			System.out.println("No fue posible migrar el agente\n\n\n");
		}
	}

	pivate class CopyBehaviour extends Behaviour{
		private byte[] content;
		private String sourceHost;
		private String targetHost;
		private String sourcePath;
		private String targetPath;

		public CopyBehaviour(String sourceHost, String targetHost, String sourcePath, String targetPath){
			this.sourcePath = sourcePath;
			this.sourceHost = sourceHost;
			this.targetHost = targetHost;
			this.targetPath = targetPath;
		}

		public void action(){
			doMove(new ContainerID(this.targetHost, null));
			this.content = Ftp.read(sourcePath,0,amount);
			doMove(new ContainerID(this.sourceHost, null));
			Ftp.write(targetPath,content);
		}

	}

}
