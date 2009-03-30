/**
 * 
 */
package com.googlecode.pennybank.swing.view.main;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.explodingpixels.macwidgets.SourceListCategory;
import com.explodingpixels.macwidgets.SourceListContextMenuProvider;
import com.explodingpixels.macwidgets.SourceListItem;
import com.googlecode.pennybank.model.user.entity.User;
import com.googlecode.pennybank.model.userfacade.delegate.UserFacadeDelegateFactory;
import com.googlecode.pennybank.model.util.exceptions.InternalErrorException;
import com.googlecode.pennybank.swing.controller.navigationpanel.DeleteItemListener;
import com.googlecode.pennybank.swing.controller.navigationpanel.EditItemListener;
import com.googlecode.pennybank.swing.view.util.MessageManager;

/**
 * @author spenap
 * 
 */
public class MainNavigationPanelContextMenu implements
		SourceListContextMenuProvider {

	private JMenuItem editMenuItem = null;
	private JMenuItem copyMenuItem = null;
	private JMenuItem cutMenuItem = null;
	private JMenuItem pasteMenuItem = null;
	private JMenuItem deleteMenuItem = null;
	private JPopupMenu contextMenu = null;
	private boolean isAccount = false;
	private boolean isUser = false;

	public MainNavigationPanelContextMenu() {
		contextMenu = new JPopupMenu();
		contextMenu.setOpaque(true);

		// Delete sourceListItem
		contextMenu.add(getDeleteMenuItem());
		contextMenu.addSeparator();

		// Edit sourceListItem
		contextMenu.add(getEditMenuItem());
		contextMenu.addSeparator();

		// Copy
		contextMenu.add(getCopyMenuItem());
		// Cut
		contextMenu.add(getCutMenuItem());
		// Paste
		contextMenu.add(getPasteMenuItem());
	}

	public JPopupMenu createContextMenu() {
		JPopupMenu popupMenu = new JPopupMenu();
		return popupMenu;

	}

	public JPopupMenu createContextMenu(SourceListCategory sourceListCategory) {
		JPopupMenu popupMenu = new JPopupMenu();
		return popupMenu;

	}

	public JPopupMenu createContextMenu(SourceListItem sourceListItem) {
		MainWindow.getInstance().getNavigationPanel().getSourceList()
				.setSelectedItem(sourceListItem);
		isAccount = isAccountSelected(sourceListItem);
		isUser = isUserSelected(sourceListItem);

		updateMenuItem(getDeleteMenuItem(), isAccount, isUser);
		updateMenuItem(getCopyMenuItem(), isAccount, isUser);
		updateMenuItem(getCutMenuItem(), isAccount, isUser);
		updateMenuItem(getEditMenuItem(), isAccount, isUser);
		return contextMenu;

	}

	private void updateMenuItem(JMenuItem menuItem, boolean accountSelected,
			boolean userSelected) {
		menuItem.setEnabled(userSelected || accountSelected);
		String menuItemText = menuItem.getText();
		int userIndex = menuItemText.indexOf(MessageManager
				.getMessage("NavigationPanel.ContextMenu.User"));
		int accountIndex = menuItemText.indexOf(MessageManager
				.getMessage("NavigationPanel.ContextMenu.Account"));

		if (userSelected) {
			if (accountIndex != -1) {
				menuItemText = menuItemText.substring(0, accountIndex).trim();
			}
			if (userIndex == -1) {
				menuItemText = menuItemText
						+ " "
						+ MessageManager
								.getMessage("NavigationPanel.ContextMenu.User");
			}
		} else if (accountSelected) {
			if (userIndex != -1) {
				menuItemText = menuItemText.substring(0, userIndex).trim();
			}
			if (accountIndex == -1) {
				menuItemText = menuItemText
						+ " "
						+ MessageManager
								.getMessage("NavigationPanel.ContextMenu.Account");
			}
		} else {
			if (userIndex != -1) {
				menuItemText = menuItemText.substring(0, userIndex).trim();
			}
			if (accountIndex != -1) {
				menuItemText = menuItemText.substring(0, accountIndex).trim();
			}
		}
		menuItem.setText(menuItemText);
	}

	private boolean isAccountSelected(SourceListItem sourceListItem) {
		boolean isAccount = sourceListItem.getChildItems().size() == 0;
		return isAccount;
	}

	private boolean isUserSelected(SourceListItem sourceListItem) {
		String userName = sourceListItem.getText();
		try {
			for (User user : UserFacadeDelegateFactory.getDelegate()
					.findUsers()) {
				if (user.getName().equals(userName))
					return true;
			}
		} catch (InternalErrorException e) {

		}
		return false;
	}

	private JMenuItem getCopyMenuItem() {
		if (copyMenuItem == null) {
			copyMenuItem = new JMenuItem(MessageManager
					.getMessage("NavigationPanel.ContextMenu.Copy"));
			// TODO: Copy action
		}
		return copyMenuItem;
	}

	private JMenuItem getCutMenuItem() {
		if (cutMenuItem == null) {
			cutMenuItem = new JMenuItem(MessageManager
					.getMessage("NavigationPanel.ContextMenu.Cut"));
			// TODO: Cut action
		}
		return cutMenuItem;
	}

	private JMenuItem getDeleteMenuItem() {
		if (deleteMenuItem == null) {
			deleteMenuItem = new JMenuItem(MessageManager
					.getMessage("NavigationPanel.ContextMenu.Delete"));
			deleteMenuItem.addActionListener(new DeleteItemListener());
		}
		return deleteMenuItem;
	}

	private JMenuItem getEditMenuItem() {
		if (editMenuItem == null) {
			editMenuItem = new JMenuItem(MessageManager
					.getMessage("NavigationPanel.ContextMenu.Edit"));
			editMenuItem.addActionListener(new EditItemListener());
		}
		return editMenuItem;
	}

	private JMenuItem getPasteMenuItem() {
		if (pasteMenuItem == null) {
			pasteMenuItem = new JMenuItem(MessageManager
					.getMessage("NavigationPanel.ContextMenu.Paste"));
			// TODO: Paste action
		}
		return pasteMenuItem;
	}
}
