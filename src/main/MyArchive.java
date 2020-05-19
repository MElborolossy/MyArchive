package main;

import guiUtils.ImageLayeredPane;
import pages.*;
import utils.Connections;
import utils.MYSQL;
import utils.NotificationSystem;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.sql.Statement;

import static utils.CommonMethods.createPage;
import static utils.Connections.*;
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
        } catch (Throwable e) {
            e.printStackTrace();
        }

        try {
            // 1. Get Connection to DB
            connObject.dbConnect();
            if (isConnected) {
                // 2. Create Statement
                Statement myStat = myConn.createStatement();

                // Check if database exist or not
                int check = MYSQL.isDBExist(connectionInfoData.databaseName);
                if (check == 1) {
                    String sql = "use " + connectionInfoData.databaseName;
                    myStat.executeUpdate(sql);
                    MYSQL.dbSQLBackup(connectionInfoData.username, connectionInfoData.password, connectionInfoData.databaseName);
//                if (isServer) {
//                    MYSQL.Backupdbtosql(USERNAME, PASSWORD, DBNAME);
//                }
                } else {
                    MYSQL.dbCreate(connectionInfoData.databaseName);
                    JOptionPane.showMessageDialog(null, "الرجاء الذهاب للإعدادات وعمل استعادة لقاعدة البيانات");
                }
            }

        } catch (SQLException sqlExp) {
            sqlExp.printStackTrace();
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
        if(!isConnected) {
            addPage.disableAllComponents();
            editPage.disableAllComponents();
            searchPage.disableAllComponents();
            followPage.disableAllComponents();
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