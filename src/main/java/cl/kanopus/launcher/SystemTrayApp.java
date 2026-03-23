/*-
 * !--
 * For support and inquiries regarding this library, please contact:
 *   soporte@kanopus.cl
 *
 * Project website:
 *   https://www.kanopus.cl
 * %%
 * Copyright (C) 2025 - 2026 Pablo Díaz Saavedra
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * --!
 */
package cl.kanopus.launcher;

import cl.kanopus.launcher.view.Home;
import java.awt.*;
import java.awt.event.*;
import java.net.ServerSocket;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.JOptionPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemTrayApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemTrayApp.class);

    public static void main(String[] args) {

        LOGGER.info("Starting SystemTrayApp");

        // checking for support
        if (!SystemTray.isSupported()) {
            LOGGER.error("System tray not supported on this platform");
            // System tray is not supported !!!
            JOptionPane.showMessageDialog(
                    null,
                    "Su Sistema Operativo no soporta esta aplicación",
                    "Error Fatal!",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        if (!isSocketAvailable(1982)) {
            LOGGER.warn("Port 1982 not available: application appears to be already running");
            JOptionPane.showMessageDialog(
                    null,
                    "Al parecer la aplicación ya se encuentra activa",
                    "Alerta",
                    JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        }

        SystemTray systemTray = SystemTray.getSystemTray();
        LOGGER.debug("Loading tray icon image from classpath");
        Image image =
                Toolkit.getDefaultToolkit()
                        .getImage(SystemTrayApp.class.getResource("/images/favicon.png"));

        // Create Home lazily on the EDT to avoid creating Swing components off the EDT
        AtomicReference<Home> homeRef = new AtomicReference<>();

        // setting tray icon
        TrayIcon trayIcon = new TrayIcon(image, "Kanopus");
        // adjust to default size as per system recommendation
        trayIcon.setImageAutoSize(true);

        // Add a popup menu with Open and Exit so we can test actions regardless of click events
        PopupMenu popup = new PopupMenu();
        MenuItem openItem = new MenuItem("Open");
        openItem.addActionListener(
                e -> {
                    LOGGER.info(
                            "Popup 'Open' selected (thread={})", Thread.currentThread().getName());
                    System.out.println("Popup 'Open' selected");
                    EventQueue.invokeLater(
                            () -> {
                                Home h = homeRef.get();
                                if (h == null) {
                                    LOGGER.info("Creating Home on EDT (popup open)");
                                    h = new Home();
                                    homeRef.set(h);
                                }
                                h.toggle();
                            });
                });
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(
                e -> {
                    LOGGER.info(
                            "Popup 'Exit' selected (thread={})", Thread.currentThread().getName());
                    System.out.println("Popup 'Exit' selected");
                    System.exit(0);
                });
        popup.add(openItem);
        popup.addSeparator();
        popup.add(exitItem);
        trayIcon.setPopupMenu(popup);

        // Main handling: Many Linux environments generate ActionEvent when the icon is clicked
        trayIcon.addActionListener(
                e -> {
                    LOGGER.info(
                            "ActionEvent received from tray icon (thread={})",
                            Thread.currentThread().getName());
                    System.out.println("ActionEvent received from tray icon");
                    EventQueue.invokeLater(
                            () -> {
                                Home h = homeRef.get();
                                if (h == null) {
                                    LOGGER.info("Creating Home on EDT (action event)");
                                    h = new Home();
                                    homeRef.set(h);
                                }
                                h.toggle();
                            });
                });

        // Fallback in case the environment delivers MouseEvents (use mouseReleased for
        // compatibility)
        trayIcon.addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        LOGGER.info(
                                "Mouse pressed on tray icon, button={} (thread={})",
                                e.getButton(),
                                Thread.currentThread().getName());
                        System.out.println("Mouse pressed on tray icon, button=" + e.getButton());
                    }

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        LOGGER.info(
                                "Mouse clicked on tray icon, clickCount={} button={} (thread={})",
                                e.getClickCount(),
                                e.getButton(),
                                Thread.currentThread().getName());
                        System.out.println(
                                "Mouse clicked on tray icon, clickCount="
                                        + e.getClickCount()
                                        + " button="
                                        + e.getButton());
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        LOGGER.info(
                                "Mouse released from tray icon, button={} (thread={})",
                                e.getButton(),
                                Thread.currentThread().getName());
                        System.out.println(
                                "MouseEvent received from tray icon, button=" + e.getButton());
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            EventQueue.invokeLater(
                                    () -> {
                                        Home h = homeRef.get();
                                        if (h == null) {
                                            LOGGER.info("Creating Home on EDT (mouse released)");
                                            h = new Home();
                                            homeRef.set(h);
                                        }
                                        h.toggle();
                                    });
                        }
                    }
                });

        try {
            systemTray.add(trayIcon);
            LOGGER.info("Tray icon added successfully");
        } catch (AWTException ex) {
            LOGGER.error("Error adding icon to SystemTray", ex);
            JOptionPane.showMessageDialog(
                    null,
                    "No se pudo agregar el icono al tray: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
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
