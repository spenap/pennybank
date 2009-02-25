/*
 * AddProfileWindow.java
 *
 * Created on 21-feb-2009, 13:01:53
 */
package com.googlecode.pennybank.swing.view.profile;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.googlecode.pennybank.model.user.entity.User;
import com.googlecode.pennybank.model.userfacade.delegate.UserFacadeDelegate;
import com.googlecode.pennybank.model.userfacade.delegate.UserFacadeDelegateFactory;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.swing.view.util.IconManager;
import com.googlecode.pennybank.swing.view.util.MessageManager;

/**
 * JDialog allowing the user to create a new profile
 * 
 * @author spenap
 */
@SuppressWarnings("serial")
public class AddProfileWindow extends JDialog {

	private JPanel buttonsPanel;
	private JButton cancelButton;
	private JPanel fieldsPanel;
	private JButton okButton;
	private JLabel profileIcon;
	private JLabel profileNameLabel;
	private JTextField profileNameTextField;

	/**
	 * Creates a new AddProfileWindow with the specified parameters
	 * 
	 * @param parent
	 *            The parent JFrame
	 * @param modal
	 *            Boolean value telling whether this JDialog is modal or not
	 */
	public AddProfileWindow(JFrame parent, boolean modal) {
		super(parent, modal);
		initComponents();
	}

	private void initComponents() {

		buttonsPanel = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();
		profileIcon = new JLabel();
		fieldsPanel = new JPanel();
		profileNameLabel = new JLabel();
		profileNameTextField = new JTextField();

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle(MessageManager.getMessage("AddProfileWindow.Title"));
		setResizable(false);

		buttonsPanel.setLayout(new FlowLayout(
				FlowLayout.RIGHT));

		okButton.setText(MessageManager.getMessage("okButton"));
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				okButtonActionPerformed(evt);
			}
		});
		buttonsPanel.add(okButton);

		cancelButton.setText(MessageManager.getMessage("cancelButton"));
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				cancelButtonActionPerformed(evt);
			}
		});
		buttonsPanel.add(cancelButton);

		getContentPane().add(buttonsPanel, BorderLayout.PAGE_END);

		profileIcon.setIcon(IconManager.getIcon("toolbar_add_user"));
		profileIcon.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		getContentPane().add(profileIcon, BorderLayout.LINE_START);

		fieldsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		fieldsPanel.setLayout(new GridLayout(1, 2, 0, 1));

		profileNameLabel.setText(MessageManager
				.getMessage("ProfileWindow.ProfileName"));
		fieldsPanel.add(profileNameLabel);
		fieldsPanel.add(profileNameTextField);

		getContentPane().add(fieldsPanel, BorderLayout.CENTER);

		pack();
	}

	private void okButtonActionPerformed(ActionEvent evt) {

		User user = new User(profileNameTextField.getText());
		try {
			UserFacadeDelegate userFacade = UserFacadeDelegateFactory
					.getDelegate();
			userFacade.createUser(user);
			this.dispose();

		} catch (InternalErrorException ex) {
			Logger.getLogger(AddProfileWindow.class.getName()).log(
					Level.SEVERE, null, ex);
		}

	}
	
	private void cancelButtonActionPerformed(ActionEvent evt) {
		this.dispose();
	}
}
