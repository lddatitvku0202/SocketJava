package SocketClock;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClockClient extends JFrame {
    private JLabel timeLabel;

    public ClockClient() {
        setTitle("Clock");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(200, 100);
        setLocationRelativeTo(null);

        timeLabel = new JLabel();
        add(timeLabel, BorderLayout.CENTER);

        Timer timer = new Timer(1000, e -> {
            try (Socket socket = new Socket("localhost", 8888);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                out.println("time");
                String response = in.readLine();
                if (response != null) {
                    timeLabel.setText(response);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ClockClient clock = new ClockClient();
            clock.setVisible(true);
        });
    }
}
