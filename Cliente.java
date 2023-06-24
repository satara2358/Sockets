package Sockets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
 class LaminaCliente extends JPanel{
	 
	 private JTextField campo1, nick, ip;
	 private JTextArea chat;
	 private JButton boton;
	 
	 public LaminaCliente() {
		 nick = new JTextField(5);
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
	 }
	 
	 private class EnviarTexto implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
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
