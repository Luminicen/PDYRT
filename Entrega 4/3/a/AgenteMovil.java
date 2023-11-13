import jade.core.*;

public class AgenteMovil extends Agent {
	private String sourceHost;
	private String targetHost;
	private String sourcePath;
	private String targetPath;
	private String mode;
	private byte[] content;
	private int position;
	private int amount;
	private String contentPath;

	//Ejecutado por unica vez en la creacion
	public void setup() {
		Location origen = here();
		System.out.println("\n\nHola, agente con nombre local " + getLocalName());
		System.out.println("Y nombre completo... " + getName());
		System.out.println("Y en location " + origen.getID() + "\n\n");

		Object[] args = getArguments();

		try {
			if (args.length < 5) {
				System.out.println("Ingresar 1ro el modo: r (read), w (write)");
				System.out.println("Para modo r ingresar 2: host destino 3: path origen, 4: path destino, 5: posicion y 6: cantidad de bytes a leer");
				System.out.println("Para modo w ingresar 2: host destino 3: path origen, 4: path destino, 5: cantidad de bytes a escribir");
				System.exit(1);
			}

			//inicializacion de variables
			this.mode = args[0].toString();
			this.sourceHost = here().getName();
			this.targetHost = args[1].toString();
			this.sourcePath = args[2].toString();
			this.targetPath = args[3].toString();

			ContainerID destination = new ContainerID(this.targetHost, null);
			System.out.println("Migrando el agente a " + destination.getID());

			//Ops en source
			switch (mode) {
				case "r":
					this.position = parseInt.args[4];
					if (args.length < 6) {
						System.out.println("Para modo r ingresar 2: host destino 3: path origen, 4: path destino, 5: posicion y 6: cantidad de bytes a leer");
						System.exit(1);
					}
					this.amount = parseInt.args[5];
					this.doMove(this.destination);
					break;
				case "w":
					this.amount = parseInt.args[4];
					this.content = Ftp.read(this.sourcePath, 0, this.amount);
					this.doMove(this.destination);
					break;
				default:
					System.out.println("modo ingresado invalido. Ingrese r para modo read o w para modo write");
					System.exit(1);
			}



		} catch (Exception e) {
			System.out.println("No fue posible migrar el agente\n\n\n");
		}
	}

	//Ejecutado al llegar a un contenedor como resultado de una migracion
	protected void afterMove()
	{
		if(mode.equals("r")) {
			if (this.here.getName().equals(this.sourceHost)) {
				Ftp.write(this.sourcePath, content);
				System.exit(0);
			} else {
				this.content = Ftp.read(this.targetPath, this.position, this.amount);
				this.doMove(new ContainerID(this.sourceHost, null));
			}
		}
		else {
			if (this.here.getName().equals(this.sourceHost)) {
				System.exit(0);
			} else {
				Ftp.write(this.targetPath, this.content);
				this.doMove(new ContainerID(this.sourceHost, null));
			}
		}

	}
}
