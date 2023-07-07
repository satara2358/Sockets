package Sockets;

import java.awt.BorderLayout;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Servidor {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MarcoServidor marco = new MarcoServidor();
		marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

class MarcoServidor extends JFrame implements Runnable{
	
	private JTextArea areadeT;
	
	public MarcoServidor() {
		setBounds(1200, 300, 280, 350);
		JPanel lamin = new JPanel();
		lamin.setLayout(new BorderLayout());
		areadeT = new JTextArea();
		lamin.add(areadeT, BorderLayout.CENTER);
		add(lamin);
		setVisible(true);
		Thread hilo = new Thread(this);
		hilo.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		try {
			ServerSocket server = new ServerSocket(3000);
			String nick, ip, mensaje;
			Envio recibido;
			
			while (true) {
				Socket socketA = server.accept();
				ObjectInputStream pDatos = new ObjectInputStream(socketA.getInputStream());
				recibido = (Envio) pDatos.readObject();
				nick = recibido.getNick();
				ip= recibido.getIp();
				mensaje = recibido.getMensaje();
				//DataInputStream flujo = new DataInputStream(socketA.getInputStream());
				//String Mtext = flujo.readUTF();
				//areadeT.append("\n"+ Mtext);
				areadeT.append("\n"+nick+" : "+mensaje+" : "+ip);
				Socket sendDestinatario = new Socket(ip,3300);
				ObjectOutputStream reenvio = new ObjectOutputStream(sendDestinatario.getOutputStream());
				reenvio.writeObject(recibido);
				reenvio.close();
				sendDestinatario.close();
				socketA.close();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
