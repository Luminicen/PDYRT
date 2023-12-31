/* A simple server in the internet domain using TCP
   The port number is passed as an argument */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <sys/time.h>

double dwalltime();

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

  if (argc < 3)
  {
    fprintf(stderr, "ERROR, no port or buffer size provided\n");
    exit(1);
  }

  int buf_size = atoi(argv[2]);

  printf("tamaño de buffer: %d \n", buf_size);

  char buffer[buf_size];

  // CREA EL FILE DESCRIPTOR DEL SOCKET PARA LA CONEXION
  sockfd = socket(AF_INET, SOCK_STREAM, 0);
  // AF_INET - FAMILIA DEL PROTOCOLO - IPV4 PROTOCOLS INTERNET
  // SOCK_STREAM - TIPO DE SOCKET

  if (sockfd < 0)
    error("ERROR opening socket");
  bzero((char *)&serv_addr, sizeof(serv_addr));
  // ASIGNA EL PUERTO PASADO POR ARGUMENTO
  // ASIGNA LA IP EN DONDE ESCUCHA (SU PROPIA IP)
  portno = atoi(argv[1]);
  serv_addr.sin_family = AF_INET;
  serv_addr.sin_addr.s_addr = INADDR_ANY;
  serv_addr.sin_port = htons(portno);

  // VINCULA EL FILE DESCRIPTOR CON LA DIRECCION Y EL PUERTO
  if (bind(sockfd, (struct sockaddr *)&serv_addr,
           sizeof(serv_addr)) < 0)
    error("ERROR on binding");

  // SETEA LA CANTIDAD QUE PUEDEN ESPERAR MIENTRAS SE MANEJA UNA CONEXION
  listen(sockfd, 5);

  // SE BLOQUEA A ESPERAR UNA CONEXION
  clilen = sizeof(cli_addr);
  newsockfd = accept(sockfd,
                     (struct sockaddr *)&cli_addr,
                     &clilen);

  // DEVUELVE UN NUEVO DESCRIPTOR POR EL CUAL SE VAN A REALIZAR LAS COMUNICACIONES
  if (newsockfd < 0)
    error("ERROR on accept");
  bzero(buffer, buf_size);

  // LEE CANT DE BYTES QUE RECIBIRA
  int cant_bytes;
  n = read(newsockfd, &cant_bytes, sizeof(int));
  printf("cant bytes: %d\n", cant_bytes);

  bzero(buffer, buf_size);

  //sleep antes de reponder al cliente
  sleep(10);

  // RESPONDE AL CLIENTE
  n = write(newsockfd, "ok", 2);
  bzero(buffer, buf_size);

  // LEE EL MENSAJE DEL CLIENTE
  int j = 0;
  do
  {
    n = read(newsockfd, &buffer[j], cant_bytes);
    if (n < 0)
    {
      error("ERROR reading from socket");
      break;
    }
    j += n;
  } while (j < cant_bytes);

  // RESPONDE AL CLIENTE
  n = write(newsockfd, "ok", 2);

  // GENERA SDBM HASH
  unsigned int hash = 0;

  // CALCULO TIEMPO PROCESAMIENTO HASH
  double thash_inicio = dwalltime();
  for (int i = 0; i < cant_bytes; i++)
    hash = buffer[i] + (hash << 6) + (hash << 16) - hash;
  double thash_fin = dwalltime() - thash_inicio;

  // LEE EL HASH DEL CLIENTE
  unsigned int recieved_hash;
  n = read(newsockfd, &recieved_hash, buf_size);
  if (n < 0)
    error("ERROR reading to socket");

    printf("hash calculado: %u, hash recibido %u\n", hash, recieved_hash);

  if (hash != recieved_hash)
  {
    printf("El mensaje fue alterado\n");
    thash_fin *= -1.0;
  }

  n = write(newsockfd, &thash_fin, sizeof(thash_fin));

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
