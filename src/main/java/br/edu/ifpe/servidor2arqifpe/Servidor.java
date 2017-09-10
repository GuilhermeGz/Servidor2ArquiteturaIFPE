package br.edu.ifpe.servidor2arqifpe;

import java.io.IOException;
import java.io.PrintStream;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author Guilherme
 */
public class Servidor {

    public String retornarHorario() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date hora = Calendar.getInstance().getTime();
        String dataFormatada = sdf.format(hora);
        return dataFormatada;
    }

    public static void main(String[] args) throws IOException {
        ServerSocket servidor = new ServerSocket(32154);
        System.out.println("Porta 32154 aberta!");
        while (true) {

            Socket cliente = servidor.accept();
            System.out.println("Nova conexão com o cliente "
                    + cliente.getInetAddress().getHostAddress());
            Thread t1 = new Thread(new Runnable() {

                public void run() {
                    try {
                        Scanner teclado = new Scanner(cliente.getInputStream());
                        PrintStream saida = new PrintStream(cliente.getOutputStream());

                        while (teclado.hasNextLine()) {
                            String escrita = teclado.nextLine();
                            if (escrita.equals("sair")) {
                                System.exit(0);
                            }
                            saida.println(new Servidor().retornarHorario());

                            System.out.println(escrita);
                        }

                        teclado.close();

                        servidor.close();

                        cliente.close();
                    } catch (Exception e) {

                    }
                }
            });
            t1.start();

        }
    }

}
