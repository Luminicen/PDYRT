import jade.core.*;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.SequentialBehaviour;

public class AgenteMovil extends Agent {
	private String sourceHost;
	private String targetHost;
	private String sourcePath;
	private String targetPath;
	private String backupPath;
	private int amount;
	private Boolean fileRead = false;

	//Ejecutado por unica vez en la creacion
	public void setup() {
		Location origen = here();
		System.out.println("\n\nHola, agente con nombre local " + getLocalName());
		System.out.println("Y nombre completo... " + getName());
		System.out.println("Y en location " + origen.getID() + "\n\n");

		Object[] args = getArguments();

		try {
			if (args.length < 5) {
				System.out.println("Ingresar 1: host destino 2: path origen, 3: path destino, 4: path backup, 5: cantidad bytes a copiar");
				System.exit(1);
			}

			//inicializacion de variables
			this.sourceHost = here().getName();
			this.targetHost = args[1].toString();
			this.sourcePath = args[2].toString();
			this.targetPath = args[3].toString();
			this.amount = Integer.parseInt(args[4].toString());

			//sub-behaviors para q no se ejecuten concurrente
			SequentialBehaviour copies = new SequentialBehaviour();
			CopyBehaviour localCopy = new CopyBehaviour(this.sourceHost, this.targetHost, this.sourcePath, this.targetPath);
			CopyBehaviour RemoteCopy = new CopyBehaviour(this.targetHost, this.sourceHost, this.backupPath, this.sourcePath);

			copies.addSubBehaviour(localCopy);
			copies.addSubBehaviour(RemoteCopy);

			addBehaviour(copies);

		} catch (Exception e) {
			System.out.println("No fue posible migrar el agente\n\n\n");
		}
	}

	private class CopyBehaviour extends Behaviour{
		private byte[] content;
		private String sourceHost;
		private String targetHost;
		private String sourcePath;
		private String targetPath;

		public CopyBehaviour(String sourceHost, String targetHost, String sourcePath, String targetPath){
			super();
			this.sourcePath = sourcePath;
			this.sourceHost = sourceHost;
			this.targetHost = targetHost;
			this.targetPath = targetPath;
		}

		public void action(){
			if(here().getName().equals(this.sourceHost)) {
				if(fileRead) {
					Ftp.write(this.sourcePath, content);
					//restablece para proxima copia
					fileRead = false;
				}
				else
					doMove(new ContainerID(this.targetHost, null));
			}
			else{
				content = Ftp.read(targetPath,0,amount);
				fileRead = true;
				doMove(new ContainerID(this.sourceHost, null));
			}

		}

		@Override
		public boolean done() {
			return true;
		}

	}

}
