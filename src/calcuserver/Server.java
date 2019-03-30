import java.io.*;
import java.net.*;
import java.util.Hashtable;

public class Server
{
	public static void main(String []args)
	{
		try
		{
			// Creamos un socket en el servidor
			ServerSocket ss = new ServerSocket(555);

			// Se hace una tabla hash con los clientes activos
			Hashtable tOnlineUsers = new Hashtable(10);
			Hashtable tOfflineUsers = new Hashtable(10);

			// ---------------------------------------------------------------
			// Se empiezan a aceptar peticiones en el while
			// El servidor sigue funcionando en el while
			while(true)
			{
				Socket socket = ss.accept();	// Aceptamos conexiones de clientes
				System.out.println("Se ha conectado un nuevo cliente.");

				// Recogemos y enviamos datos al servidor
				InputStream in = socket.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				OutputStream out = socket.getOutputStream();
				PrintWriter pr = new PrintWriter(out, true);

				// Leemos el nombre de usuario del cliente y lo almacenamos en la tabla
				String strUserName = br.readLine();
				System.out.println("Nombre de usuario: " + strUserName + "\n");
				tOnlineUsers.put(strUserName, socket);

				// Se crean los hilos que permiten conexiones simultáneas
				Worker w = new Worker(socket, tOnlineUsers, tOfflineUsers, strUserName);
				w.start();
			}	// Fin del while

		}	
		catch(Exception e)
		{
			System.out.println("Error en la conexión");
		}

	}
}
