package pages;

import guiUtils.GridBagLabel;
import guiUtils.GridBagPanel;
import guiUtils.GridBagTextField;
import guiUtils.GhostText;
import utils.Connections;

import javax.swing.*;
import java.awt.*;

import static utils.Connections.*;
public class ConnectionPanel {

    public GridBagPanel connectPanel = new GridBagPanel(new int[]{0, 0, 0, 0, 0},new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE},
            new int[]{0, 0, 0, 0, 0, 0, 0},new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE});

    public static GridBagLabel welcomeLabel = new GridBagLabel("First Time Connection Setup", SwingConstants.CENTER, GridBagConstraints.BOTH,1,1);
    public static GridBagLabel serverLabel = new GridBagLabel("Server", SwingConstants.LEFT,GridBagConstraints.BOTH,1,2);
    public static GridBagLabel portLabel = new GridBagLabel("Port", SwingConstants.LEFT,GridBagConstraints.BOTH,1,3);
    public static GridBagLabel databaseLabel = new GridBagLabel("Database", SwingConstants.LEFT,GridBagConstraints.BOTH,1,4);
    public static GridBagLabel usernameLabel = new GridBagLabel("Username", SwingConstants.LEFT,GridBagConstraints.BOTH,1,5);
    public static GridBagLabel passwordLabel = new GridBagLabel("Password", SwingConstants.LEFT,GridBagConstraints.BOTH,1,6);

    public static GridBagTextField serverText = new GridBagTextField(SwingConstants.LEFT,2,2);
    public static GridBagTextField portText = new GridBagTextField(SwingConstants.LEFT,2,3);
    public static GridBagTextField databaseText = new GridBagTextField(SwingConstants.LEFT,2,4);
    public static GridBagTextField usernameText = new GridBagTextField(SwingConstants.LEFT,2,5);
    public static GridBagTextField passwordText = new GridBagTextField(SwingConstants.LEFT,2,6);

    public ConnectionPanel(){
        loadPanel();
    }

    private void loadPanel(){
        connectPanel.add(welcomeLabel, welcomeLabel.getGBC());
        connectPanel.add(serverLabel, serverLabel.getGBC());
        connectPanel.add(portLabel, portLabel.getGBC());
        connectPanel.add(databaseLabel, databaseLabel.getGBC());
        connectPanel.add(usernameLabel, usernameLabel.getGBC());
        connectPanel.add(passwordLabel, passwordLabel.getGBC());
        connectPanel.add(serverText, serverText.getGBC());
        new GhostText(serverText, "localhost");
        connectPanel.add(portText, portText.getGBC());
        new GhostText(portText, "3306");
        connectPanel.add(databaseText, databaseText.getGBC());
        new GhostText(databaseText, "egyarmy");
        connectPanel.add(usernameText, usernameText.getGBC());
        new GhostText(usernameText, "root");
        connectPanel.add(passwordText, passwordText.getGBC());
        new GhostText(passwordText, "12345678");
    }

    public void getConnectionInfoFirstTime(){
        int result = JOptionPane.showConfirmDialog(null,connectPanel,"Welcome",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION){
            Connections.connectionInfoData.server = serverText.getText();
            Connections.connectionInfoData.port = portText.getText();
            Connections.connectionInfoData.databaseName = databaseText.getText();
            Connections.connectionInfoData.username = usernameText.getText();
            Connections.connectionInfoData.password = passwordText.getText();
        }else{
            setDefaultValues();
        }
    }
}
