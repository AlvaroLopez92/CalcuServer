import java.io.*;
import java.net.*;
import java.util.Hashtable;

public class Client
{

	public static void main(String []args)
	{

		int port=555;			// Puerto del servidor
		String strPort="",		// Puerto del servidor
			   ip="",               // IP del servidor
			   strUserName="";	// Nombre de usuario del cliente

		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

		// Recoger IP, puerto y nombre de usuario del cliente
		try
		{
			
			System.out.println("Instrucciones para conectar al servidor: \n\n" +
				"-> Si el servidor funciona en le mismo ordenador," +
				"presiona Enter o introduce \"127.0.0.1\".\n\n" +
				"-> Introduce tu nombre de usuario.\n");

			// Recoger IP del usuario
			System.out.print("\n\nIntroducir IP del servidor: ");
			ip = input.readLine();
			if (ip.equals(""))
				ip = "127.0.0.1";	// default IP

			// Recoger puerto del usuario
			System.out.print("Introducir puerto: ");
			strPort = input.readLine();
			if (strPort.equals(""))
				port = 555;
			else
				port = Integer.parseInt(strPort);

			// Recoger nombre de usuario del cliente
			strUserName = "temp";
			do
			{
				System.out.print("Introducir nombre de usuario: ");
				strUserName = input.readLine();
			}
			while (strUserName.equals(""));	// Repetir hasta que se proporcione un nombre de usuario válido

			// Crear un nuevo socket
			Socket socket = new Socket(ip, port);

			// Conexión exitosa
			System.out.print("\n\n\t\tConexión exitosa. \n\t\t----------------------");

			String strAnother = "z",
				   strInt1 = "45",		// Primer operador
				   strInt2 = "2",		// Segundo operador
				   strOp = "-",			// Operando
				   strResult="";
			
                        // Recoger y enviar datos al servidor
			InputStream in = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			OutputStream out = socket.getOutputStream();
			PrintWriter pr = new PrintWriter(out, true);

			// Enviar nombre de usuario al servidor
			pr.println(strUserName);

			// Comienza el while
			// --------------
			while (strAnother.charAt(0) != 'd')
			{
				// Pequeño menú para el usuario
				System.out.println("\n\nPulsa 'D' para desconectar.\n" +
					"      O cualquier otra tecla para pasar a la calculadora.  ");
				strAnother = input.readLine();
				if (strAnother.equals(""))
					strAnother = "z";
				switch(strAnother.charAt(0))
				{
					case 'd':
						pr.println("d");
						break;
					default:	// Enviar operación al servidor
						pr.println("z");
						// Recoger primer número
						System.out.print("Enter First Number: ");
						strInt1 = input.readLine();
						if (strInt1.equals(""))
							strInt1 = "45";

						// Recoger segundo número
						System.out.print("Enter Second Number: ");
						strInt2 = input.readLine();
						if (strInt2.equals(""))
							strInt2 = "2";

						// Recoger operando
						System.out.print("Enter Operator: ");
						strOp = input.readLine();
						if (strOp.equals(""))
							strOp = "-";

						// Envía los dos operadores y el operando al servidor
						pr.println(strInt1);
						pr.println(strInt2);
						pr.println(strOp);

						// Recoge el resultado del servidor
						strResult = br.readLine();
						System.out.println(strResult);
						break;
				}	// Fin del switch
			}	// Fin del while

			// Aquí cortamos la conexión con el socket
			socket.close();

		}
		catch(Exception e)
		{
			System.out.println("Ha ocurrido un error");
			System.exit(0);
		}

	}
}