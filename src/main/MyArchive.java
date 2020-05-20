package main;

import guiUtils.ImageLayeredPane;
import pages.*;
import utils.Connections;
import utils.MYSQL;
import utils.NotificationSystem;

import javax.swing.*;
import java.awt.*;

import static utils.CommonMethods.createPage;
import static utils.CommonMethods.setJOptionPaneBackGroundColor;
import static utils.Connections.connectionInfoData;
import static utils.Connections.isConnected;
import static utils.ConstantAttributes.*;

public class MyArchive {
    private JFrame archiveFrame;
    public static MainPage mainPage;
    public static AddPage addPage;
    public static EditPage editPage;
    public static SearchPage searchPage;
    public static FollowPage followPage;
    public static OptionsPage optionsPage;
    public static BranchPage branchPage;
    public static ImageLayeredPane layeredPane;
    public static Connections connObject = new Connections();

    //    /**
//     * Launch the application.
//     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            setJOptionPaneBackGroundColor();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        // 1. Get Connection to DB
        connObject.dbConnect();
        if (isConnected) {
            // Use database if exist or create a new one;
            if (MYSQL.useDBIfExist(connectionInfoData) != 1) {
                MYSQL.dbCreate(Connections.connectionInfoData.databaseName);
                JOptionPane.showMessageDialog(null, "الرجاء الذهاب للإعدادات وعمل استعادة لقاعدة البيانات");
            }
        }

        EventQueue.invokeLater(() -> {
            try {
                MyArchive window = new MyArchive();
                window.archiveFrame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
//

    /**
     * Create the application.
     */
    public MyArchive() {
        initialize();
    }
//

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        // Frame Settings
        archiveFrame = new JFrame();
        archiveFrame.setMinimumSize(new Dimension(1000, 600));
        archiveFrame.setIconImage(trayIcon.getImage());
        archiveFrame.setName("frame");
        archiveFrame.setTitle("أرشيفي");
        archiveFrame.setBounds(100, 100, 900, 500);
        archiveFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        archiveFrame.getContentPane().setLayout(new BorderLayout(0, 0));

        // Initialize LayeredPane
        layeredPane = new ImageLayeredPane(backgroundImage);
        layeredPane.setOpaque(true);
        layeredPane.setBackground(backColor);
        archiveFrame.getContentPane().add(layeredPane);
        layeredPane.setLayout(new BorderLayout(0, 0));

        // Create Pages Object
        mainPage = (MainPage) createPage("main");
        addPage = (AddPage) createPage("add");
        editPage = (EditPage) createPage("edit");
        searchPage = (SearchPage) createPage("search");
        followPage = (FollowPage) createPage("follow");
        optionsPage = (OptionsPage) createPage("options");
        branchPage = (BranchPage) createPage("branch");

        // Open Options Page if there is any settings missed
        // Open Main Page if All things Correct
        if(!mainPage.checkConnection()) {
            mainPage.centerPanel(layeredPane);
//            JOptionPane pane = new JOptionPane();
//            pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
//            pane.showMessageDialog(null, "غير قادر على الاتصال بالخادم");

        }else {
            optionsStatus = optionsPage.getLoadOptionsStatus();
            if (optionsStatus > 0) {
                optionsPage.centerPanel(layeredPane);
            } else {
                mainPage.centerPanel(layeredPane);
            }
        }

        NotificationSystem.createAndShowGUI();
    }
}