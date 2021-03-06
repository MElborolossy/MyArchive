package pages;

import guiUtils.GridBagImageButton;
import guiUtils.GridBagLabel;
import guiUtils.GridBagPanel;
import guiUtils.StretchIcon;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static main.MyArchive.*;
import static utils.CommonMethods.setJOptionPaneDarkBackGroundColor;
import static utils.CommonMethods.setJOptionPaneLightBackGroundColor;
import static utils.Connections.isConnected;
import static utils.Connections.myConn;
import static utils.ConstantAttributes.*;

public class MainPage extends GridBagPanel {

	// Attributes
	// Buttons
	GridBagImageButton mOptionsButton = new GridBagImageButton(optionIcon, 1, 1);
	GridBagImageButton mAddButton = new GridBagImageButton(addIcon, 4, 3);
	GridBagImageButton mSearchButton = new GridBagImageButton(searchIcon, 2, 3);
	GridBagImageButton mEditButton = new GridBagImageButton(editIcon, 4, 5);
	GridBagImageButton mFollowButton = new GridBagImageButton(followIcon, 2, 5);
	GridBagImageButton mNLModeButton = new GridBagImageButton(darkModeIcon,4,1);

	// Labels
	private GridBagLabel mFollowLabel = new GridBagLabel(followStatusEmptyIcon,(long)GridBagConstraints.CENTER , GridBagConstraints.VERTICAL, 2, 1);

	private GridBagLabel mConnectionLabel = new GridBagLabel(dbDisconnectedIcon,(long)GridBagConstraints.WEST , GridBagConstraints.VERTICAL, 3, 1);

	// MainPage Constructor
	public MainPage(StretchIcon img, int[] colWidth, double[] colWeights, int[] rowHeight, double[] rowWeights) {
		super(img, colWidth, colWeights, rowHeight, rowWeights);
		loadPage();
	}

	@Override
	public void loadPage() {
		this.add(mOptionsButton, mOptionsButton.getGBC());
		this.add(mAddButton, mAddButton.getGBC());
		this.add(mSearchButton, mSearchButton.getGBC());
		this.add(mEditButton, mEditButton.getGBC());
		this.add(mFollowButton, mFollowButton.getGBC());
		this.add(mFollowLabel, mFollowLabel.getGBC());
		this.add(mConnectionLabel,mConnectionLabel.getGBC());
		this.add(mNLModeButton,mNLModeButton.getGBC());
		checkFollow();
		onClickButtonsListeners();
	}

	public void onClickButtonsListeners() {
		onClickAddButton();
		onClickEditButton();
		onClickSearchButton();
		onClickFollowButton();
		onClickOptionsButton();
		onClickNLModeButton();
	}
	// *** Check follow Status Function *** \\
	public void checkFollow() {
		String sqlFollow = "select * from " + MAINTABLENAME + " where followstatus like '1'";
		int countFollow = 0;
		try {
			Statement myStat = myConn.createStatement();
			ResultSet myRs = myStat.executeQuery(sqlFollow);
			if (myRs != null) {
				myRs.last();
				countFollow = myRs.getRow();
			}
			String finalCount = Integer.toString(countFollow);
			if (countFollow == 0) {
				mFollowLabel.setIcon(followStatusEmptyIcon);
				mFollowLabel.setText("");
			} else {
				mFollowLabel.setIcon(followStatusFillIcon);
				mFollowLabel.setText(finalCount);
				mFollowLabel.setForeground(Color.WHITE);
			}

		} catch (SQLException e) {
			System.out.println("SQL Connection Error");
			e.printStackTrace();
		}
	}


	private void onClickAddButton() {
		mAddButton.addActionListener(arg0 -> addPage.centerPanel(layeredPane));
	}

	private void onClickEditButton() {
		mEditButton.addActionListener(arg0 -> editPage.centerPanel(layeredPane));
	}

	private void onClickSearchButton() {
		mSearchButton.addActionListener(arg0 -> searchPage.centerPanel(layeredPane));
	}

	private void onClickFollowButton() {
		mFollowButton.addActionListener(arg0 -> {
			followPage.centerPanel(layeredPane);
			if(checkConnection()){
				followPage.loadFollowFaxes();
			}
		});
	}

	private void onClickOptionsButton() {
		mOptionsButton.addActionListener(arg0 -> optionsPage.centerPanel(layeredPane));
	}

	private void onClickNLModeButton(){
		mNLModeButton.addActionListener(arg0 ->{
			if (mNLModeButton.getIcon().toString().contains("Night")) {
				mNLModeButton.setIcon(lightModeIcon);
				mainPage.lightMode();
				addPage.lightMode();
				editPage.lightMode();
				searchPage.lightMode();
				followPage.lightMode();
				branchPage.lightMode();
				optionsPage.lightMode();
				layeredPane.setBackground(backLightColor);
				layeredPane.validate();
				connObject.connectPanel.connectPanel.lightMode();
				setJOptionPaneLightBackGroundColor();
			}else {
				mNLModeButton.setIcon(darkModeIcon);
				mainPage.darkMode();
				addPage.darkMode();
				editPage.darkMode();
				searchPage.darkMode();
				followPage.darkMode();
				branchPage.darkMode();
				optionsPage.darkMode();
				connObject.connectPanel.connectPanel.darkMode();
				layeredPane.setBackground(backDarkColor);
				layeredPane.validate();
				setJOptionPaneDarkBackGroundColor();
			}
		});
	}

	public boolean checkConnection(){
		if (isConnected){
			mConnectionLabel.setIcon(dbConnectedIcon);
			addPage.reloadPage();
			editPage.reloadPage();
			searchPage.reloadPage();
			followPage.reloadPage();
		}else{
			mConnectionLabel.setIcon(dbDisconnectedIcon);
			addPage.disablePage();
			editPage.disablePage();
			searchPage.disablePage();
			followPage.disablePage();
		}
		return isConnected;
	}
}
