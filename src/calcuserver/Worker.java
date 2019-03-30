import java.io.*;
import java.net.*;
import java.util.*;

public class Worker extends Thread
{
	Socket socket;
	Hashtable tUsuariosOnline;
	Hashtable tUsuariosOffline;
	String strUserName;

	public Worker(Socket s, Hashtable online, Hashtable offline, String userName)
	{
		socket = s;
		tUsuariosOnline = online;
		tUsuariosOffline = offline;
		strUserName = userName;
	}

	public void run()
	{
		try
		{
			// Recoge y envía datos al servidor
			InputStream in = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			OutputStream out = socket.getOutputStream();
			PrintWriter pr = new PrintWriter(out, true);

			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			String strAnother="", strResult="";

			while(!strAnother.equals("d"))
			{
				strResult = "";
				strAnother = br.readLine();
				System.out.println("La opción del usuario es: " + strAnother);
				switch(strAnother.charAt(0))
				{
					case 'd':
						System.out.println("Petición para desconectar..");
						break;
					case 'z':
						System.out.println("Calculando resultado y enviando respuesta...\n");
						String strInt1 = br.readLine();
						String strInt2 = br.readLine();
						String strOp = br.readLine();
						int int1 = Integer.parseInt(strInt1);
						int int2 = Integer.parseInt(strInt2);
						char chOp = strOp.charAt(0);
						String strCalcResult = "";
						switch(chOp)
						{
							case '+':
								strCalcResult = "El resultado es "+(int1+int2); break;
							case '-':
								strCalcResult = "El resultado es "+(int1-int2); break;
							case '*':
								strCalcResult = "El resultado es "+(int1*int2); break;
							case '/':
								strCalcResult = "El resultado es "+(int1/int2); break;
							default:
								strCalcResult = "El operando es inválido."; break;
						}
						pr.println(strCalcResult);

						break;
				}
			}

			// Con esto cerramos el socket
			Socket s = (Socket)tUsuariosOnline.remove(strUserName);
			tUsuariosOffline.put(strUserName, s);
			socket.close();

		}
		catch(Exception e)
		{
			System.out.println("Ha ocurrido un error en el Worker.");
		}

	}

}
