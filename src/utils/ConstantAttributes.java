package utils;

import guiUtils.StretchIcon;

import javax.swing.*;
import java.awt.*;
import java.security.CodeSource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class ConstantAttributes {
    // Program Images
    public static final StretchIcon mainBackgroundImage = new StretchIcon(ConstantAttributes.class.getResource("/images/Main.png"));
    public static final StretchIcon backgroundImage = new StretchIcon(ConstantAttributes.class.getResource("/images/Pages.png"));
    public static final ImageIcon addIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/Add.png"));
    public static final ImageIcon addFaxIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/AddFax.png"));
    public static final ImageIcon addLinkIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/AddLink.png"));
    public static final ImageIcon backIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/Back.png"));
    public static final ImageIcon backupIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/Backup.png"));
    public static final ImageIcon addEBranchIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/BEAdd.png"));
    public static final ImageIcon delEBranchIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/BEDel.png"));
    public static final ImageIcon editEBranchIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/BEEdit.png"));
    public static final ImageIcon addIBranchIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/BIAdd.png"));
    public static final ImageIcon delIBranchIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/BIDel.png"));
    public static final ImageIcon editIBranchIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/BIEdit.png"));
    public static final ImageIcon branchesIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/Branches.png"));
    public static final ImageIcon copyIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/Copy.png"));
    public static final ImageIcon copyrightsIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/Copyrights.png"));
    public static final ImageIcon defaultPrinterIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/DefPrint.png"));
    public static final ImageIcon deleteIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/Delete.png"));
    public static final ImageIcon editIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/Edit.png"));
    public static final ImageIcon folderIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/Folder.png"));
    public static final ImageIcon followIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/Follow.png"));
    public static final ImageIcon homeIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/Home.png"));
    public static final ImageIcon lActionIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/LAction.png"));
    public static final ImageIcon lArchiveIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/LArchive.png"));
    public static final ImageIcon lDateIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/LDate.png"));
    public static final ImageIcon lDateFaxIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/LDateFax.png"));
    public static final ImageIcon lDefPrinterIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/LDefPrinter.png"));
    public static final ImageIcon lDirectedIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/LDirected.png"));
    public static final ImageIcon lEBranchIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/LExtBranch.png"));
    public static final ImageIcon lFaxIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/LFax.png"));
    public static final ImageIcon lFileIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/LFile.png"));
    public static final ImageIcon lFollowIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/LFollow.png"));
    public static final ImageIcon lFollowQIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/LFollowQ.png"));
    public static final ImageIcon lFromIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/LFrom.png"));
    public static final ImageIcon lIBranchIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/LIntBranch.png"));
    public static final ImageIcon lNoteIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/LNotes.png"));
    public static final ImageIcon lOpenWithIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/LOpenWith.png"));
    public static final ImageIcon lScannerIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/LScanner.png"));
    public static final ImageIcon lSearchWithIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/LSearchWith.png"));
    public static final ImageIcon lSearchWordIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/LSearchWord.png"));
    public static final ImageIcon lSubjIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/LSubj.png"));
    public static final ImageIcon lToIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/LTo.png"));
    public static final ImageIcon noIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/No.png"));
    public static final ImageIcon followStatusFillIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/Notification.png"));
    public static final ImageIcon followStatusEmptyIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/NotificationEmpty.png"));
    public static final ImageIcon openIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/Open.png"));
    public static final ImageIcon pasteIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/Paste.png"));
    public static final ImageIcon pdfIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/PDF.png"));
    public static final ImageIcon printIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/Print.png"));
    public static final ImageIcon restoreIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/Restore.png"));
    public static final ImageIcon saveIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/Save.png"));
    public static final ImageIcon scanIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/Scan.png"));
    public static final ImageIcon searchIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/Search.png"));
    public static final ImageIcon searchNowIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/SearchNow.png"));
    public static final ImageIcon optionIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/settings.png"));
    public static final ImageIcon yesIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/Yes.png"));
    public static final ImageIcon dbConnectedIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/dbConnected.png"));
    public static final ImageIcon dbDisconnectedIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/dbDisconnect.png"));
    public static final ImageIcon connectionSettingsIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/ConnectionSettings.png"));
    public static final ImageIcon newDBIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/NewDB.png"));

    public static final TrayIcon trayIcon = new TrayIcon(new ImageIcon(ConstantAttributes.class.getResource("/images/icon.png")).getImage());

    // CheckBox Images
    // UnSelected
    public static final ImageIcon cActionIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/actionUnSelected.png"));
    public static final ImageIcon cDateIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/dateUnSelected.png"));
    public static final ImageIcon cDateFaxIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/faxdateUnSelected.png"));
    public static final ImageIcon cDirectedIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/directUnSelected.png"));
    public static final ImageIcon cFaxIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/faxIdUnSelected.png"));
    public static final ImageIcon cFileIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/fileUnSelected.png"));
    public static final ImageIcon cFollowIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/followUnSelected.png"));
    public static final ImageIcon cFromIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/fromUnSelected.png"));
    public static final ImageIcon cNoteIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/noteUnSelected.png"));
    public static final ImageIcon cSubjIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/subjUnSelected.png"));
    public static final ImageIcon cToIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/toUnSelected.png"));
    // Selected
    public static final ImageIcon cSActionIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/actionSelected.png"));
    public static final ImageIcon cSDateIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/dateSelected.png"));
    public static final ImageIcon cSDateFaxIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/faxdateSelected.png"));
    public static final ImageIcon cSDirectedIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/directSelected.png"));
    public static final ImageIcon cSFaxIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/faxIdSelected.png"));
    public static final ImageIcon cSFileIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/fileSelected.png"));
    public static final ImageIcon cSFollowIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/followSelected.png"));
    public static final ImageIcon cSFromIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/fromSelected.png"));
    public static final ImageIcon cSNoteIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/noteSelected.png"));
    public static final ImageIcon cSSubjIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/subjSelected.png"));
    public static final ImageIcon cSToIcon = new ImageIcon(ConstantAttributes.class.getResource("/images/toSelected.png"));

    // Fonts Attributes
    public static final Font mainFontBold = new Font("Times New Roman", Font.BOLD, 18);
    public static final Font mainFontPlain = new Font("Times New Roman", Font.PLAIN, 18);
    public static final Font dateFontPlain = new Font("Times New Roman", Font.PLAIN, 15);

    // Colors Attributes
    public static final Color backColor = new Color(60, 60, 60);
    public static final Color borderColor = new Color(170,100,0);

    // MySQL Connection Attribute

    public static final String MAINTABLENAME = "archivefax";
    public static final String INTERNALTABLE = "internalbranches";
    public static final String EXTERNALTABLE = "externalbranches";
    public static final String PRINTERSTABLE = "printers";
    public static final String SCANNERTABLE = "scanner";

    // Tables Header
    public static Object[] headers = new Object[]{"ملاحظات", "المتابعة", "الإجراء", "الفرع المختص", "رقم المكاتبة",
            "تاريخ المكاتبة", "إلى", "من", "الموضوع", "رقم الأرشيف", "التاريخ"};
    public static Object[] branchHeaders = new Object[]{"الاسم", "م"};

    // ComboBox Items
    public static String[] comboBoxItems = new String[]{"رقم الأرشيف", "التاريخ", "تاريخ المكاتبة", "الموضوع",
            "جهة الصادر", "جهة الوارد", "رقم المكاتبة"};

    // Default Printer and Scanner
    public static String defaultPrinter = "";
    public static String clientDefaultPrinter = "";
    public static String scannerLink = "";

    // File Chooser
    public static JFileChooser chooseFile = new JFileChooser();

    // Option Status
    public static int optionsStatus ;

    // Categories Enum
    public enum Categories {archiveid,date,faxdate,subject,faxfrom,faxTo,faxid}

    // Date Format
    public static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    // File Attributes
    public static CodeSource codeSource = ConstantAttributes.class.getProtectionDomain().getCodeSource();

}
