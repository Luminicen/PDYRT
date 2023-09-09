#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>  
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h> 

#define buf_size 25	

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

    char buffer[buf_size];
    char message[2];
    if (argc < 3) {
       fprintf(stderr,"usage %s hostname port\n", argv[0]);
       exit(0);
    }
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
        
    
    bzero(buffer,buf_size);
    
    memset((buffer), 'a', buf_size);
    
    //GENERA SDBM HASH
    unsigned int hash = 0;
    for (int i = 0; i < strlen(buffer); i++)
    	hash = buffer[i] + (hash << 6) + (hash << 16) - hash;
    
    
    int cant_bytes = strlen(buffer);
    
    printf("cant bytes: %d\n", cant_bytes);
    
    //ENVIA CANTIDAD DE BYTES DEL MENSAJE AL SOCKET
    n = write(sockfd,&cant_bytes,sizeof(buffer));
    if (n < 0) 
         error("ERROR writing cant bytes message to socket");
         
    //ESPERA RECIBIR UNA RESPUESTA
    n = read(sockfd,message,2);

    if (n < 0) 
 	error("ERROR reading from socket");
	
    //ENVIA UN MENSAJE AL SOCKET
    n = write(sockfd,buffer,strlen(buffer));
    if (n < 0) 
         error("ERROR writing message to socket");
    bzero(buffer,buf_size);
    
    //ESPERA RECIBIR UNA RESPUESTA
    n = read(sockfd,message,2);
    if (n < 0) 
         error("ERROR reading from socket");
        
    	
    //ENVIA HASH AL SOCKET
    printf("hash: %u\n", hash);
    n = write(sockfd,&hash,sizeof(hash));
    if (n < 0) 
         error("ERROR writing to socketz");
    bzero(buffer,buf_size);

    //ESPERA RECIBIR UNA RESPUESTA
    n = read(sockfd,message,2);
    if (n < 0) 
         error("ERROR reading from socket");
    
	printf("%s\n",buffer);
    return 0;
}
