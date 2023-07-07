package Sockets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Cliente {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MarcoCliente marco = new MarcoCliente();
		marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

class MarcoCliente extends JFrame{
	public MarcoCliente() {
		setBounds(600,300,280,350);
		LaminaCliente lamina = new LaminaCliente();
		add(lamina);
		setVisible(true);
	}
	
}
 class LaminaCliente extends JPanel implements Runnable{
	 
	 private JTextField campo1, ip;
	 private JLabel nick;
	 private JTextArea chat;
	 private JButton boton;
	 
	 public LaminaCliente() {
		 String nickName = JOptionPane.showInputDialog("Nick: ");
		 nick = new JLabel();
		 nick.setText(nickName);
		 add(nick);
		 JLabel text = new JLabel("CLIENTE");
		 add(text);
		 ip = new JTextField(8);
		 add(ip);
		 campo1 = new JTextField(20);
		 add(campo1);		 
		 chat = new JTextArea(12,20);
		 add(chat);
		 boton = new JButton("Enviar");
		 EnviarTexto evento = new EnviarTexto();
		 boton.addActionListener(evento);
		 add(boton);	
		 Thread hilo = new Thread(this);
		 hilo.start();
	 }
	 
	 private class EnviarTexto implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			chat.append("\n" + campo1.getText());
			try {
				Socket socketC = new Socket("192.168.100.115",3000);
				//DataOutputStream flujoSalida = new DataOutputStream(socketC.getOutputStream());
				//flujoSalida.writeUTF(campo1.getText());
				//flujoSalida.close();
				Envio data = new Envio();
				data.setNick(nick.getText());
				data.setIp(nick.getText());
				data.setMensaje(nick.getText());
				
				ObjectOutputStream paqueteDta = new ObjectOutputStream(socketC.getOutputStream());
				paqueteDta.writeObject(paqueteDta);
				socketC.close();
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.out.println(e1.getMessage());
			}	
		}	 
	 }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			ServerSocket serverCliente = new ServerSocket(3300);
			Socket cliente;
			
			Envio paqueteRecibido;
			while (true) {
				cliente = serverCliente.accept();
				ObjectInputStream flujoEntrada = new ObjectInputStream(cliente.getInputStream());
				paqueteRecibido =  (Envio) flujoEntrada.readObject();
				chat.append("\n "+paqueteRecibido.getNick()+ ": "+ paqueteRecibido.getMensaje());
			}
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		
	}
 }
 
 class Envio implements Serializable{
	 private String nick, ip, mensaje;

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	 
	 
 }
