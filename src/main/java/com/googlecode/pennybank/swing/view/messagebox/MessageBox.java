/**
 * MessageBox.java
 * 
 * 04/03/2009
 */
package com.googlecode.pennybank.swing.view.messagebox;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import com.googlecode.pennybank.swing.view.util.IconManager;
import com.googlecode.pennybank.swing.view.util.MessageManager;
import com.googlecode.pennybank.swing.view.util.ResultWindow;

/**
 * @author spenap
 * 
 */
public class MessageBox extends JDialog implements ResultWindow {

	private static final long serialVersionUID = 1L;
	private JPanel mainContentsPane = null;
	private JPanel buttonsPane = null;
	private JPanel fieldsPane = null;
	private JButton cancelButton = null;
	private JButton okButton = null;
	private JLabel iconLabel = null;
	private JPanel informationPane = null;
	private JTextPane mainTextPane = null;
	private String messageHeader = null;
	private String messageDescription = null;
	private MessageType messageType;
	private JTextPane descriptionPane = null;
	private ResultType windowResult = ResultType.CANCEL; // @jve:decl-index=0:

	public enum MessageType {
		INFORMATION, WARNING, ERROR, YESNO
	}

	public MessageBox(String messageHeader, String messageDescription,
			MessageType type) {
		this.messageHeader = messageHeader;
		this.messageDescription = messageDescription;
		this.messageType = type;
		initialize(null);
	}

	/**
	 * @param owner
	 */
	public MessageBox(Frame owner, String messageHeader,
			String messageDescription, MessageType type) {
		super(owner);
		this.messageHeader = messageHeader;
		this.messageDescription = messageDescription;
		this.messageType = type;
		initialize(owner);
	}

	/**
	 * This method initializes this
	 * 
	 * @param owner
	 * 
	 * @return void
	 */
	private void initialize(Frame owner) {
		this.setSize(375, 185);
		this.setResizable(false);
		this.setModal(true);
		this.setContentPane(getMainContentsPane());
		this.setLocationRelativeTo(owner);
	}

	/**
	 * This method initializes mainContentsPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getMainContentsPane() {
		if (mainContentsPane == null) {
			mainContentsPane = new JPanel();
			mainContentsPane.setLayout(new BorderLayout());
			mainContentsPane.add(getButtonsPane(), BorderLayout.SOUTH);
			mainContentsPane.add(getFieldsPane(), BorderLayout.CENTER);
		}
		return mainContentsPane;
	}

	/**
	 * This method initializes buttonsPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getButtonsPane() {
		if (buttonsPane == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(java.awt.FlowLayout.RIGHT);
			buttonsPane = new JPanel();
			buttonsPane.setLayout(flowLayout);
			buttonsPane.add(getCancelButton());
			buttonsPane.add(getOkButton());
		}
		return buttonsPane;
	}

	/**
	 * This method initializes fieldsPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getFieldsPane() {
		if (fieldsPane == null) {
			fieldsPane = new JPanel();
			fieldsPane.setLayout(new BorderLayout());
			fieldsPane.add(getIconLabel(), BorderLayout.WEST);
			fieldsPane.add(getInformationPane(), BorderLayout.CENTER);
		}
		return fieldsPane;
	}

	private JLabel getIconLabel() {
		iconLabel = new JLabel();
		iconLabel.setBorder(new EmptyBorder(0, 10, 0, 10));
		ImageIcon icon = IconManager.getIcon("smallpiggybank");
		iconLabel.setIcon(icon);
		return iconLabel;
	}

	/**
	 * This method initializes cancelButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton();
			cancelButton.setVisible(false);
			if (messageType == MessageType.YESNO) {
				cancelButton.setVisible(true);
				getRootPane().setDefaultButton(cancelButton);
			}
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
		this.windowResult = ResultType.CANCEL;
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
			if (messageType != MessageType.YESNO) {
				getRootPane().setDefaultButton(okButton);
			}
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
		this.windowResult = ResultType.OK;
		this.dispose();
	}

	/**
	 * This method initializes informationPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getInformationPane() {
		if (informationPane == null) {
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(2);
			gridLayout.setVgap(5);
			gridLayout.setHgap(0);
			informationPane = new JPanel();
			informationPane.setBorder(new EmptyBorder(10, 0, 10, 0));
			informationPane.setLayout(gridLayout);
			informationPane.add(getMessageHeader(), 0);
			informationPane.add(getDescriptionPane(), null);
		}
		return informationPane;
	}

	private JTextPane getMessageHeader() {
		mainTextPane = new JTextPane();
		Font theFont = mainTextPane.getFont();
		mainTextPane.setBackground(mainContentsPane.getBackground());
		mainTextPane.setEditable(false);
		mainTextPane.setFont(theFont.deriveFont(Font.BOLD));
		mainTextPane.setText(messageHeader);
		return mainTextPane;
	}

	/**
	 * This method initializes descriptionPane
	 * 
	 * @return javax.swing.JTextPane
	 */
	private JTextPane getDescriptionPane() {
		if (descriptionPane == null) {
			descriptionPane = new JTextPane();
			descriptionPane.setBackground(mainContentsPane.getBackground());
			descriptionPane.setEditable(false);
			descriptionPane.setText(messageDescription);
		}
		return descriptionPane;
	}

	public ResultType getResult() {
		return windowResult;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
