/* A simple server in the internet domain using TCP
   The port number is passed as an argument */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h> 
#include <sys/types.h> 
#include <sys/socket.h>
#include <netinet/in.h>

void error(char *msg)
{
    perror(msg);
    exit(1);
}	

int main(int argc, char *argv[])
{
     int sockfd, newsockfd, portno, clilen;
     
     struct sockaddr_in serv_addr, cli_addr;
     int n;
     if (argc < 3) {
         fprintf(stderr,"ERROR, no port or no buffer size provided\n");
         exit(1);
     }

     int buf_size = atoi(argv[2]);

     printf("tamaño de buffer: %d \n", buf_size);

     char buffer[buf_size];

	 //CREA EL FILE DESCRIPTOR DEL SOCKET PARA LA CONEXION
     sockfd = socket(AF_INET, SOCK_STREAM, 0);
	 //AF_INET - FAMILIA DEL PROTOCOLO - IPV4 PROTOCOLS INTERNET
	//SOCK_STREAM - TIPO DE SOCKET 
	
     if (sockfd < 0) 
        error("ERROR opening socket");
     bzero((char *) &serv_addr, sizeof(serv_addr));
     //ASIGNA EL PUERTO PASADO POR ARGUMENTO
	 //ASIGNA LA IP EN DONDE ESCUCHA (SU PROPIA IP)
	 portno = atoi(argv[1]);
     serv_addr.sin_family = AF_INET;
     serv_addr.sin_addr.s_addr = INADDR_ANY;
     serv_addr.sin_port = htons(portno);
	 
	 //VINCULA EL FILE DESCRIPTOR CON LA DIRECCION Y EL PUERTO
     if (bind(sockfd, (struct sockaddr *) &serv_addr,
              sizeof(serv_addr)) < 0) 
              error("ERROR on binding");
			  
	 //SETEA LA CANTIDAD QUE PUEDEN ESPERAR MIENTRAS SE MANEJA UNA CONEXION		  
     listen(sockfd,5);
	 
	 // SE BLOQUEA A ESPERAR UNA CONEXION
     clilen = sizeof(cli_addr);
     newsockfd = accept(sockfd, 
                 (struct sockaddr *) &cli_addr, 
                 &clilen);
				 
     //DEVUELVE UN NUEVO DESCRIPTOR POR EL CUAL SE VAN A REALIZAR LAS COMUNICACIONES
	 if (newsockfd < 0) 
          error("ERROR on accept");
     bzero(buffer,buf_size);

	//LEE EL MENSAJE DEL CLIENTE
     //int j = 0;
     n = read(newsockfd,buffer,buf_size);
     //j+=n;
     if (n < 0) error("ERROR reading from socket");
     /*while(j < buf_size){
     	n = read(newsockfd,buffer+j,buf_size-1);
     	j += n;
     }*/
     
     printf("Cantidad de caracteres leidos: %d\n",n);
     //printf("Here is the message: %s\n",buffer);
	 
	 //RESPONDE AL CLIENTE
     n = write(newsockfd,"I got your message",18);
     if (n < 0) error("ERROR writing to socket");
     return 0; 
}
