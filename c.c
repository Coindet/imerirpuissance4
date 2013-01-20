#include <mysql.h>
#include<string.h>
#include <stdio.h>

void Insert(char* request){
	char FinalRequest[250]="INSERT INTO `stat`(Winner,NombreCoups,Time) VALUES(";
	MYSQL *conn;
 	MYSQL_RES *res;
  	MYSQL_ROW row;

  	char *server = "localhost";
  	char *user = "imerir";
  	char *password = "roots"; 
  	char *database = "puissance4";
  	char* Information = NULL;
	
	strcat(FinalRequest,request);
	strcat(FinalRequest,");");
	  conn = mysql_init(NULL);

  if (!mysql_real_connect(conn, server,
        user, password, database, 0, NULL, 0)) {
      fprintf(stderr, "%s\n", mysql_error(conn));
     
  }
  if (mysql_query(conn, FinalRequest)) {
      fprintf(stderr, "%s\n", mysql_error(conn));
      exit(1);
  }

  mysql_free_result(res);
  mysql_close(conn);

}
void extract(char* request){
	char FinalRequest[20]="";	
	
	strncpy(FinalRequest,request+2,strlen(request)-2);
	printf("FINAL:::%s\n",	FinalRequest);
	int i;
	for(i=0;i<strlen(FinalRequest);i++){
		switch(FinalRequest[i]){
			case '_': FinalRequest[i]=',';	break;
			default: break;	
		}
	}
	printf("FINAL:::%s\n",	FinalRequest);
	Insert(FinalRequest);
}
main(int argc, char **argv) {

 
  extract(argv[1]);	

}
