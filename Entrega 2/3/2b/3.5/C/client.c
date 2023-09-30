#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <sys/time.h>
#include <math.h>

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

    if (argc < 3)
    {
        fprintf(stderr, "usage %s hostname port buffer size\n", argv[0]);
        exit(0);
    }

    int buf_size = atoi(argv[3]);

    char buffer[buf_size];
    char message[2];

    printf("tamaño de buffer: %d \n", buf_size);

    // TOMA EL NUMERO DE PUERTO DE LOS ARGUMENTOS
    portno = atoi(argv[2]);

    // CREA EL FILE DESCRIPTOR DEL SOCKET PARA LA CONEXION
    sockfd = socket(AF_INET, SOCK_STREAM, 0);
    // AF_INET - FAMILIA DEL PROTOCOLO - IPV4 PROTOCOLS INTERNET
    // SOCK_STREAM - TIPO DE SOCKET

    if (sockfd < 0)
        error("ERROR opening socket");

    // TOMA LA DIRECCION DEL SERVER DE LOS ARGUMENTOS
    server = gethostbyname(argv[1]);
    if (server == NULL)
    {
        fprintf(stderr, "ERROR, no such host\n");
        exit(0);
    }
    bzero((char *)&serv_addr, sizeof(serv_addr));
    serv_addr.sin_family = AF_INET;

    // COPIA LA DIRECCION IP Y EL PUERTO DEL SERVIDOR A LA ESTRUCTURA DEL SOCKET
    bcopy((char *)server->h_addr,
          (char *)&serv_addr.sin_addr.s_addr,
          server->h_length);
    serv_addr.sin_port = htons(portno);

    // DESCRIPTOR - DIRECCION - TAMAÑO DIRECCION
    if (connect(sockfd, &serv_addr, sizeof(serv_addr)) < 0)
        error("ERROR connecting");

    bzero(buffer, buf_size);

    //**********************************************//

    // GENERA MENSAJE
    memset((buffer), 'a', buf_size);

    // GENERA SDBM HASH
    unsigned int hash = 0;
    for (int i = 0; i < strlen(buffer); i++)
        hash = buffer[i] + (hash << 6) + (hash << 16) - hash;

    int cant_bytes = strlen(buffer);

    printf("cant bytes: %d\n", cant_bytes);

    // CALCULA TIEMPO INICIO DE COMUNICACION
    double tiempoInicio = dwalltime();

    //sleep 1
    sleep(10);

    // ENVIA CANTIDAD DE BYTES DEL MENSAJE AL SOCKET
    n = write(sockfd, &cant_bytes, sizeof(cant_bytes));
    if (n < 0)
        error("ERROR writing cant bytes message to socket");

    //sleep 2
    sleep(10);


    // ESPERA RECIBIR UNA RESPUESTA
    n = read(sockfd, message, 2);

    if (n < 0)
        error("ERROR reading from socket");

    //sleep 3
    sleep(10);


    // ENVIA UN MENSAJE AL SOCKET
    n = write(sockfd, buffer, strlen(buffer));
    if (n < 0)
        error("ERROR writing message to socket");
    bzero(buffer, buf_size);

    //sleep 4
    sleep(10);

    // ESPERA RECIBIR UNA RESPUESTA
    n = read(sockfd, message, 2);
    if (n < 0)
        error("ERROR reading from socket");

    // ENVIA HASH AL SOCKET
    printf("hash: %u\n", hash);

    //sleep 5
    sleep(10);

    n = write(sockfd, &hash, sizeof(hash));
    if (n < 0)
        error("ERROR writing to socketz");
    bzero(buffer, buf_size);

    // ESPERA RECIBIR UNA RESPUESTA
    double tproc;

    //sleep 6
    sleep(10);

    n = read(sockfd, &tproc, sizeof(double));
    // CALCULA TIEMPO FIN DE COMUNICACION
    double tiempoFin = dwalltime() - tiempoInicio - fabs(tproc); // calcula abs para restar a la comuinicacion

    
    if (n < 0)
        error("ERROR reading from socket");

    // si es negativo es xq esta corrupto
    if (tproc < 0)
        printf("Error en la transmision del mensaje enviado\n");

    printf("Tiempo total de comunicacion en segundos: %f\n", tiempoFin);

    printf("%s\n", buffer);
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