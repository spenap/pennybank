/**
 * AddUserWindow.java
 * 
 * 04/03/2009
 */
package com.googlecode.pennybank.swing.view.category;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.googlecode.pennybank.App;
import com.googlecode.pennybank.model.accountfacade.delegate.AccountFacadeDelegateFactory;
import com.googlecode.pennybank.model.category.entity.Category;
import com.googlecode.pennybank.model.util.exceptions.InstanceNotFoundException;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.swing.view.util.GuiUtils;
import com.googlecode.pennybank.swing.view.util.IconManager;
import com.googlecode.pennybank.swing.view.util.MessageManager;
import com.googlecode.pennybank.swing.view.util.exceptions.BadNameException;

/**
 * @author spenap
 * 
 */
public class ShowCategoryWindow extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel mainContentPane = null;
	private JPanel fieldsPane = null;
	private JLabel iconLabel = null;
	private JPanel componentsPane = null;
	private JLabel categoryNameLabel = null;
	private JTextField categoryNameTextField = null;
	private JPanel buttonsPane = null;
	private JButton cancelButton = null;
	private JButton okButton = null;
	private JLabel parentCategoryLabel = null;
	private JComboBox parentCategoryComboBox = null;
	private Category createdCategory = null; // @jve:decl-index=0:

	/**
	 * @param owner
	 */
	public ShowCategoryWindow(Frame owner) {
		super(owner);
		initialize(owner);
	}

	public Category getCreatedCategory() {
		return createdCategory;
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
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cancelButtonActionPerformed(e);
				}
			});
		}
		return cancelButton;
	}

	/**
	 * This method initializes userNameTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getCategoryNameTextField() {
		if (categoryNameTextField == null) {
			categoryNameTextField = new JTextField();
			categoryNameTextField.setBounds(new Rectangle(163, 8, 180, 30));
		}
		return categoryNameTextField;
	}

	/**
	 * This method initializes componentsPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getComponentsPane() {
		if (componentsPane == null) {
			// Parent category label
			parentCategoryLabel = new JLabel();
			parentCategoryLabel.setBounds(new Rectangle(17, 45, 133, 28));
			parentCategoryLabel.setText(MessageManager
					.getMessage("CategoryWindow.ParentCategory"));
			parentCategoryLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			// Category name label
			categoryNameLabel = new JLabel();
			categoryNameLabel.setBounds(new Rectangle(17, 8, 133, 30));
			categoryNameLabel.setText(MessageManager
					.getMessage("CategoryWindow.CategoryName"));
			categoryNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			// Pane
			componentsPane = new JPanel();
			componentsPane.setLayout(null);
			componentsPane.add(categoryNameLabel, null);
			componentsPane.add(getCategoryNameTextField(), null);
			componentsPane.add(parentCategoryLabel, null);
			componentsPane.add(getParentCategoryComboBox(), null);
		}
		return componentsPane;
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
			iconLabel.setIcon(IconManager.getIcon("toolbar_add_category"));
			fieldsPane = new JPanel();
			fieldsPane.setLayout(new BorderLayout());
			fieldsPane.add(iconLabel, BorderLayout.WEST);
			fieldsPane.add(getComponentsPane(), BorderLayout.CENTER);
		}
		return fieldsPane;
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
			mainContentPane.add(getFieldsPane(), BorderLayout.CENTER);
			mainContentPane.add(getButtonsPane(), BorderLayout.SOUTH);
		}
		return mainContentPane;
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
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					okButtonActionPerformed(e);
				}
			});
		}
		return okButton;
	}

	private Category getParentCategory() {
		Category selectedCategory = null;
		String selectedCategoryName = getParentCategoryComboBox()
				.getSelectedItem().toString();
		for (Category category : App.getCategories()) {
			if (category.getName().equals(selectedCategoryName)) {
				selectedCategory = category;
				break;
			}
		}

		return selectedCategory;
	}

	/**
	 * This method initializes parentCategoryComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getParentCategoryComboBox() {
		if (parentCategoryComboBox == null) {
			parentCategoryComboBox = new CategoriesComboBox();
			 parentCategoryComboBox.setBounds(new Rectangle(163, 45, 180,
			 28));
		}
		return parentCategoryComboBox;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize(Frame owner) {
		this.setModal(true);
		this.setSize(405, 145);
		this.setTitle(MessageManager.getMessage("AddCategoryWindow.Title"));
		this.setResizable(false);
		this.setModal(true);
		this.setLocationRelativeTo(owner);
		this.setContentPane(getMainContentPane());
	}

	protected void cancelButtonActionPerformed(ActionEvent e) {
		this.dispose();
	}

	protected void okButtonActionPerformed(ActionEvent e) {
		try {
			String categoryName = getCategoryNameTextField().getText();
			if (categoryName == null
					|| categoryName.trim().toString().equals("")) {
				throw new BadNameException();
			}
			Category parentCategory = getParentCategory();
			Category theCategory = new Category(categoryName, parentCategory);
			createdCategory = AccountFacadeDelegateFactory.getDelegate()
					.createCategory(theCategory);
			this.dispose();
			GuiUtils.info("CategoryWindow.Create.Success");
		} catch (InstanceNotFoundException ex) {
			GuiUtils.error("CategoryWindow.Create.Failure.NotFound");
		} catch (InternalErrorException ex) {
			GuiUtils.error("CategoryWindow.Create.Failure.Generic");
		} catch (BadNameException ex) {
			GuiUtils.error("CategoryWindow.Create.Failure.BadName");
		}
	}

} // @jve:decl-index=0:visual-constraint="10,10"
