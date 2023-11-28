package cl.kanopus.launcher;

import cl.kanopus.launcher.view.Home;
import java.awt.*;
import java.awt.event.*;
import java.net.ServerSocket;
import javax.swing.JOptionPane;

/**
 *
 * @author Pablo Diaz Saavedra
 * @email pabloandres.diazsaavedra@gmail.com
 * @company Kanopus.cl
 */
public class SystemTrayApp {

    public static void main(String[] args) throws AWTException {

        //checking for support
        if (!SystemTray.isSupported()) {
            //System tray is not supported !!!
            JOptionPane.showMessageDialog(null, "Su Sistema Operativo no soporta esta aplicación", "Error Fatal!", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        if (!isSocketAvailable(1982)) {
            JOptionPane.showMessageDialog(null, "Al parecer la aplicación ya se encuentra activa", "Alerta", JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        }

        SystemTray systemTray = SystemTray.getSystemTray();
        Image image = Toolkit.getDefaultToolkit().getImage(SystemTrayApp.class.getResource("/images/favicon.png"));

        final Home home = new Home();
        //setting tray icon
        TrayIcon trayIcon = new TrayIcon(image, "Kanopus");
        //adjust to default size as per system recommendation
        trayIcon.setImageAutoSize(true);
        trayIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getID() == MouseEvent.MOUSE_CLICKED && e.getButton() == MouseEvent.BUTTON1) {
                    home.toggle();
                }
            }
        });
        systemTray.add(trayIcon);

    }

    public static boolean isSocketAvailable(int port) {
        boolean available;
        try {
            ServerSocket clientSocket = new ServerSocket(port);
            clientSocket.close();
            available = true;
        } catch (Exception e) {
            available = false;
        }
        return available;
    }

}
