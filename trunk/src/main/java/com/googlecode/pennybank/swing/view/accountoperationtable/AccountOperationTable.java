/**
 * 
 */
package com.googlecode.pennybank.swing.view.accountoperationtable;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JPopupMenu;
import javax.swing.JTable;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.matchers.MatcherEditor;
import ca.odell.glazedlists.swing.EventTableModel;
import ca.odell.glazedlists.swing.TableComparatorChooser;
import ca.odell.glazedlists.swing.TextComponentMatcherEditor;

import com.explodingpixels.macwidgets.MacWidgetFactory;
import com.googlecode.pennybank.model.accountoperation.entity.AccountOperation;
import com.googlecode.pennybank.swing.view.accountoperation.AccountOperationComparator;
import com.googlecode.pennybank.swing.view.util.AccountOperationTableRenderer;
import com.googlecode.pennybank.swing.view.util.PlatformUtils;

/**
 * @author spenap
 * 
 */
public class AccountOperationTable {

	private FilterList<AccountOperation> filterableAccountOperations = null;
	private SortedList<AccountOperation> sortedAccountOperations = null;
	private EventList<AccountOperation> eventList = null;
	private EventTableModel<AccountOperation> tableModel = null;
	private JTable theTable = null;

	public AccountOperationTable() {

		installTableFilters();

		if (PlatformUtils.isMacOS()) {
			theTable = MacWidgetFactory.createITunesTable(getTableModel());
		} else {
			theTable = new JTable(getTableModel());
		}
		TableComparatorChooser.install(theTable, getSortedList(),
				TableComparatorChooser.MULTIPLE_COLUMN_MOUSE);

		theTable.setDefaultRenderer(Date.class,
				new AccountOperationTableRenderer());
		theTable.setDefaultRenderer(Double.class,
				new AccountOperationTableRenderer());
		theTable.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				evaluatePopup(e);
			}

			public void mouseReleased(MouseEvent e) {
				evaluatePopup(e);
			}

			private void evaluatePopup(MouseEvent e) {
				if (e.isPopupTrigger()) {
					JPopupMenu menu = new AccountOperationTableContextMenu(
							theTable);
					menu.show(e.getComponent(), e.getX(), e.getY());
				}
			}

		});
	}

	private void installTableFilters() {
		MatcherEditor<AccountOperation> textMatcherEditor = new TextComponentMatcherEditor<AccountOperation>(
				AccountOperationTableFilter.getSearchField(),
				new AccountOperationTextFilterator());
		filterableAccountOperations = new FilterList<AccountOperation>(
				getSortedList(), textMatcherEditor);
	}

	public AccountOperationTable(List<AccountOperation> accountOperations) {
		this();
		this.setAccountOperations(accountOperations);
	}

	public JTable getJTable() {
		return theTable;
	}

	public void setAccountOperations(List<AccountOperation> operations) {
		getSortedList().clear();
		getSortedList().addAll(operations);
	}

	public List<AccountOperation> getSelectedAccountOperations() {
		int selectedRows[] = theTable.getSelectedRows();
		List<AccountOperation> selectedOperations = new ArrayList<AccountOperation>();

		for (int row : selectedRows) {
			if (row >= 0 && row < getSortedList().size())
				selectedOperations.add(getSortedList().get(row));
		}
		return selectedOperations;
	}

	private EventList<AccountOperation> getEventList() {
		if (eventList == null) {
			eventList = new BasicEventList<AccountOperation>();
		}
		return eventList;
	}

	private SortedList<AccountOperation> getSortedList() {
		if (sortedAccountOperations == null) {
			sortedAccountOperations = new SortedList<AccountOperation>(
					getEventList(), new AccountOperationComparator());
		}
		return sortedAccountOperations;
	}

	private EventTableModel<AccountOperation> getTableModel() {
		if (tableModel == null) {
			tableModel = new EventTableModel<AccountOperation>(
					filterableAccountOperations,
					new AccountOperationTableFormat());
		}
		return tableModel;
	}
}
