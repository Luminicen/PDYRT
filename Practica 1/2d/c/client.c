#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h> 
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h> 
#include <sys/time.h>

double dwalltime();

void error(char *msg)
{
    perror(msg);
    exit(0);
}

int main(int argc, char *argv[])
{
    int sockfd, portno, n;
    struct sockaddr_in serv_addr;
    struct hostent *server;

    if (argc < 4) {
       fprintf(stderr,"usage %s hostname port buffer size\n", argv[0]);
       exit(0);
    }

    int buf_size = atoi(argv[3]);

    printf("tamaño de buffer: %d \n", buf_size);

    char buffer[buf_size];
    
	//TOMA EL NUMERO DE PUERTO DE LOS ARGUMENTOS
    portno = atoi(argv[2]);
	
	//CREA EL FILE DESCRIPTOR DEL SOCKET PARA LA CONEXION
    sockfd = socket(AF_INET, SOCK_STREAM, 0);
	//AF_INET - FAMILIA DEL PROTOCOLO - IPV4 PROTOCOLS INTERNET
	//SOCK_STREAM - TIPO DE SOCKET 
	
    if (sockfd < 0) 
        error("ERROR opening socket");
	
	//TOMA LA DIRECCION DEL SERVER DE LOS ARGUMENTOS
    server = gethostbyname(argv[1]);
    if (server == NULL) {
        fprintf(stderr,"ERROR, no such host\n");
        exit(0);
    }
    bzero((char *) &serv_addr, sizeof(serv_addr));
    serv_addr.sin_family = AF_INET;
	
	//COPIA LA DIRECCION IP Y EL PUERTO DEL SERVIDOR A LA ESTRUCTURA DEL SOCKET
    bcopy((char *)server->h_addr, 
         (char *)&serv_addr.sin_addr.s_addr,
         server->h_length);
     serv_addr.sin_port = htons(portno);
	
	//DESCRIPTOR - DIRECCION - TAMAÑO DIRECCION
    if (connect(sockfd,&serv_addr,sizeof(serv_addr)) < 0) 
        error("ERROR connecting");
    //printf("Please enter the message: ");
    bzero(buffer,buf_size);
    //fgets(buffer,buf_size-1,stdin);
    memset((buffer), 'a', buf_size);

    //CALCULA TIEMPO INICIO DE COMUNICACION
    double tiempoInicio = dwalltime();

    //ENVIA UN MENSAJE AL SOCKET
    n = write(sockfd,buffer,strlen(buffer));
    printf("pepe\n");
    if (n < 0) 
         error("ERROR writing to socket");
    bzero(buffer,buf_size);

    //ESPERA RECIBIR UNA RESPUESTA
    printf("pepe\n");
	n = read(sockfd,buffer,buf_size-1);
    printf("pepe\n");
    if (n < 0) 
         error("ERROR reading from socket");

    //CALCULA TIEMPO FIN DE COMUNICACION
    double tiempoFin = dwalltime() - tiempoInicio;
    printf("pepe\n");
    printf("Tiempo total de comunicacion en segundos: %f\n", tiempoFin);
    
	printf("%s\n",buffer);
    return 0;
}

double dwalltime()
{
  double sec;
  struct timeval tv;

  gettimeofday(&tv, NULL);
  sec = tv.tv_sec + tv.tv_usec / 1000000.0;
  return sec;
}
