/**
 * AddUserWindow.java
 * 
 * 04/03/2009
 */
package com.googlecode.pennybank.swing.view.profile;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.googlecode.pennybank.swing.view.main.MainWindow;
import com.googlecode.pennybank.swing.view.util.IconManager;
import com.googlecode.pennybank.swing.view.util.MessageBox;
import com.googlecode.pennybank.swing.view.util.MessageManager;
import com.googlecode.pennybank.swing.view.util.MessageBox.MessageType;

/**
 * @author spenap
 * 
 */
public class AddProfileWindow extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel mainContentPane = null;
	private JPanel fieldsPane = null;
	private JLabel iconLabel = null;
	private JPanel componentsPane = null;
	private JLabel userNameLabel = null;
	private JTextField userNameTextField = null;
	private JPanel buttonsPane = null;
	private JButton cancelButton = null;
	private JButton okButton = null;

	/**
	 * @param owner
	 */
	public AddProfileWindow(Frame owner) {
		super(owner);
		initialize(owner);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize(Frame owner) {
		this.setSize(400, 145);
		this.setTitle(MessageManager.getMessage("AddProfileWindow.Title"));
		this.setResizable(false);
		this.setLocationRelativeTo(owner);
		this.setContentPane(getMainContentPane());
	}

	/**
	 * This method initializes mainContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getMainContentPane() {
		if (mainContentPane == null) {
			mainContentPane = new JPanel();
			mainContentPane.setLayout(new BorderLayout());
			mainContentPane.add(getFieldsPane(), java.awt.BorderLayout.CENTER);
			mainContentPane.add(getButtonsPane(), java.awt.BorderLayout.SOUTH);
		}
		return mainContentPane;
	}

	/**
	 * This method initializes fieldsPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getFieldsPane() {
		if (fieldsPane == null) {
			iconLabel = new JLabel();
			iconLabel.setBorder(new EmptyBorder(0, 10, 0, 10));
			iconLabel.setIcon(IconManager.getIcon("toolbar_add_user"));
			fieldsPane = new JPanel();
			fieldsPane.setLayout(new BorderLayout());
			fieldsPane.add(iconLabel, java.awt.BorderLayout.WEST);
			fieldsPane.add(getComponentsPane(), java.awt.BorderLayout.CENTER);
		}
		return fieldsPane;
	}

	/**
	 * This method initializes componentsPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getComponentsPane() {
		if (componentsPane == null) {
			userNameLabel = new JLabel();
			userNameLabel.setBounds(new Rectangle(15, 30, 120, 30));
			userNameLabel.setText(MessageManager
					.getMessage("ProfileWindow.ProfileName"));
			userNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
			componentsPane = new JPanel();
			componentsPane.setLayout(null);
			componentsPane.add(userNameLabel, null);
			componentsPane.add(getUserNameTextField(), null);
		}
		return componentsPane;
	}

	/**
	 * This method initializes userNameTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getUserNameTextField() {
		if (userNameTextField == null) {
			userNameTextField = new JTextField();
			userNameTextField.setBounds(new Rectangle(150, 30, 180, 30));
		}
		return userNameTextField;
	}

	/**
	 * This method initializes buttonsPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getButtonsPane() {
		if (buttonsPane == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			buttonsPane = new JPanel();
			buttonsPane.setLayout(flowLayout);
			buttonsPane.add(getCancelButton(), null);
			buttonsPane.add(getOkButton(), null);
		}
		return buttonsPane;
	}

	/**
	 * This method initializes cancelButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton();
			cancelButton.setText(MessageManager.getMessage("cancelButton"));
			cancelButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					cancelButtonActionPerformed(e);
				}
			});
		}
		return cancelButton;
	}

	protected void cancelButtonActionPerformed(ActionEvent e) {
		this.dispose();
	}

	/**
	 * This method initializes okButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getOkButton() {
		if (okButton == null) {
			okButton = new JButton();
			okButton.setText(MessageManager.getMessage("okButton"));
			okButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					okButtonActionPerformed(e);
				}
			});
		}
		return okButton;
	}

	protected void okButtonActionPerformed(ActionEvent e) {
		MessageBox messageBox = new MessageBox(
				MainWindow.getInstance(),
				"Solicitada creación de usuario",
				"Se ha solicitado la creación de un usuario a la base de datos",
				MessageType.YESNO);
		messageBox.setVisible(true);
	}

} // @jve:decl-index=0:visual-constraint="10,10"
