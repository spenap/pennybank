package com.googlecode.pennybank.swing.view.category;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.swing.EventTableModel;
import ca.odell.glazedlists.swing.TableComparatorChooser;

import com.explodingpixels.macwidgets.MacIcons;
import com.explodingpixels.macwidgets.MacWidgetFactory;
import com.googlecode.pennybank.App;
import com.googlecode.pennybank.model.category.entity.Category;
import com.googlecode.pennybank.swing.view.util.MessageManager;

public class ManageCategoriesWindow extends JDialog {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		ManageCategoriesWindow w = new ManageCategoriesWindow(null);
		w.setVisible(true);
	}

	private JPanel jContentPane = null;

	private SortedList<Category> sortedCategories = null;
	private JScrollPane categoriesScrollPane = null;
	private JTable categoriesTable = null;
	private JPanel buttonsPane = null;
	private EventTableModel<Category> tableModel = null;
	private JPanel categoriesPane = null;
	private JButton okButton = null;
	private JButton addButton = null;
	private JButton delButton = null;
	private JPanel leftButtonsPanel = null;
	private JPanel rightButtonsPanel = null;

	/**
	 * @param owner
	 */
	public ManageCategoriesWindow(Frame owner) {
		super(owner);
		initialize(owner);
	}

	private void generateSortedList() {
		this.sortedCategories = new SortedList<Category>(
				new BasicEventList<Category>(), new CategoryComparator());
		this.sortedCategories.addAll(App.getCategories());
	}

	private JButton getAddButton() {
		if (addButton == null) {
			addButton = new JButton();
			addButton.setIcon(MacIcons.PLUS);
			addButton.putClientProperty("JButton.buttonType", "gradient");
			addButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					addButtonActionPerformed(e);
				}
			});
		}
		return addButton;
	}

	private JPanel getButtonsPane() {
		if (buttonsPane == null) {
			buttonsPane = new JPanel();
			buttonsPane.setLayout(new BorderLayout());
			buttonsPane.add(getLeftButtonsPane(), BorderLayout.WEST);
			buttonsPane.add(getRightButtonsPane(), BorderLayout.EAST);
		}
		return buttonsPane;
	}

	/**
	 * This method initializes categoriesPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getCategoriesPane() {
		if (categoriesPane == null) {
			categoriesPane = new JPanel();
			categoriesPane.setLayout(new BorderLayout());
			categoriesPane.setBorder(new EmptyBorder(0, 0, 0, 0));
			categoriesPane.add(getCategoriesScrollPane(), BorderLayout.CENTER);
		}
		return categoriesPane;
	}

	/**
	 * This method initializes categoriesScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getCategoriesScrollPane() {
		if (categoriesScrollPane == null) {
			categoriesScrollPane = MacWidgetFactory
					.wrapITunesTableInJScrollPane(getCategoriesTable());
			categoriesScrollPane.setViewportView(getCategoriesTable());
		}
		return categoriesScrollPane;
	}

	/**
	 * This method initializes categoriesTrable
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getCategoriesTable() {
		if (categoriesTable == null) {
			categoriesTable = MacWidgetFactory
					.createITunesTable(getCategoriesTableModel());
			TableComparatorChooser.install(categoriesTable, sortedCategories,
					TableComparatorChooser.SINGLE_COLUMN);
			TableColumn parentCategoryColumn = categoriesTable.getColumnModel()
					.getColumn(1);
			parentCategoryColumn.setCellEditor(new DefaultCellEditor(
					new CategoriesComboBox()));
			categoriesTable.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {

						public void valueChanged(ListSelectionEvent e) {
							getDelButton()
									.setEnabled((e.getFirstIndex() != -1));
						}

					});
		}
		return categoriesTable;
	}

	private TableModel getCategoriesTableModel() {
		if (tableModel == null) {
			tableModel = new EventTableModel<Category>(sortedCategories,
					new CategoriesTableFormat());
		}
		return tableModel;
	}

	private JButton getDelButton() {
		if (delButton == null) {
			delButton = new JButton();
			delButton.setIcon(MacIcons.MINUS);
			delButton.putClientProperty("JButton.buttonType", "gradient");
			delButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					delButtonActionPerformed(e);
				}
			});
			delButton.setEnabled(false);
		}
		return delButton;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getCategoriesPane(), BorderLayout.CENTER);
			jContentPane.add(getButtonsPane(), BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	private JPanel getLeftButtonsPane() {
		leftButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 4));
		leftButtonsPanel.add(getAddButton());
		leftButtonsPanel.add(getDelButton());
		return leftButtonsPanel;
	}

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

	private JPanel getRightButtonsPane() {
		rightButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 1));
		rightButtonsPanel.add(getOkButton());
		return rightButtonsPanel;
	}

	/**
	 * This method initializes this
	 * 
	 * @param owner
	 * 
	 * @return void
	 */
	private void initialize(Frame owner) {
		this.setSize(300, 200);
		this.setLocationRelativeTo(owner);
		generateSortedList();
		getRootPane().putClientProperty("Window.style", "small");
		this.setContentPane(getJContentPane());
	}

	protected void addButtonActionPerformed(ActionEvent e) {
		Category theCategory = new Category(MessageManager
				.getMessage("Category.New"));
		sortedCategories.add(theCategory);
		int rowIndex = sortedCategories.indexOf(theCategory);
		Rectangle r = categoriesTable.getCellRect(rowIndex, 0, true);
		categoriesTable.scrollRectToVisible(r);
		categoriesTable.editCellAt(rowIndex, 0);
	}

	protected void delButtonActionPerformed(ActionEvent e) {
		int rowIndex = categoriesTable.getSelectedRow();
		if (rowIndex != -1) {
			// TODO: Store removed object in order to allow "undoing"
			Category toDelete = sortedCategories.remove(rowIndex);
			System.out.println(toDelete);
		}
	}

	protected void okButtonActionPerformed(ActionEvent e) {
		this.dispose();
	}

}
