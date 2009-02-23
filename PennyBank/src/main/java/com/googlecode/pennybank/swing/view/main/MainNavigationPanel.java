/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.googlecode.pennybank.swing.view.main;

import com.explodingpixels.macwidgets.MacIcons;
import com.explodingpixels.macwidgets.SourceList;
import com.explodingpixels.macwidgets.SourceListCategory;
import com.explodingpixels.macwidgets.SourceListControlBar;
import com.explodingpixels.macwidgets.SourceListItem;
import com.explodingpixels.macwidgets.SourceListModel;
import com.explodingpixels.widgets.PopupMenuCustomizerUsingStrings;
import com.googlecode.pennybank.model.account.entity.Account;
import com.googlecode.pennybank.swing.controller.account.AddAccountListener;
import com.googlecode.pennybank.swing.controller.account.RemoveAccountListener;
import com.googlecode.pennybank.swing.view.util.IconManager;
import com.googlecode.pennybank.swing.view.util.MessageManager;
import java.util.ArrayList;
import java.util.List;

/**
 * The application Content Panel
 *
 * @author spenap
 */
public class MainNavigationPanel {

    private static SourceListControlBar createControlBar() {
        SourceListControlBar controlBar =
                new SourceListControlBar();
        controlBar.createAndAddButton(MacIcons.PLUS, new AddAccountListener());
        controlBar.createAndAddButton(MacIcons.MINUS, new RemoveAccountListener());
        controlBar.createAndAddPopdownButton(MacIcons.GEAR, new PopupMenuCustomizerUsingStrings(null, "Element 1", "Element 2", "Element 3"));
        return controlBar;
    }
    private SourceListCategory accountsCategory;
    private final SourceListModel model;
    private List<SourceListItem> sourceListItems;
    private SourceListControlBar controlBar;
    private SourceList sourceList;

    public MainNavigationPanel() {
        accountsCategory =
                new SourceListCategory(MessageManager.getMessage("NavigationPanel.Accounts"));
        model = new SourceListModel();
        sourceListItems = new ArrayList<SourceListItem>();

        populateAccountList();
        sourceList = new SourceList(model);
        controlBar = createControlBar();
        sourceList.installSourceListControlBar(controlBar);

    }

    public void addAccount(Account theAccount) {
        SourceListItem item =
                new SourceListItem(theAccount.getName(), IconManager.getIcon("navigation_account"));
        sourceListItems.add(item);
        model.addItemToCategory(item, accountsCategory);
    }

    public void removeAccount(Account theAccount) {
        int itemIndex = -1;

        for (int i = 0; i < sourceListItems.size(); i++) {
            if (sourceListItems.get(i).
                    getText().
                    equals(theAccount.getName())) {
                itemIndex = i;
                break;
            }
        }
        sourceListItems.remove(itemIndex);
        populateAccountList();
    }

    public SourceList getSourceList() {
        return sourceList;
    }

    private void populateAccountList() {
        if (model.getCategories().
                contains(accountsCategory)) {
            model.removeCategory(accountsCategory);
        }
        model.addCategory(accountsCategory);
        for (SourceListItem sourceListItem : sourceListItems) {
            model.addItemToCategory(sourceListItem, accountsCategory);
        }
    }
    /**
     * Gets the Navigation Panel
     *
     * @return the Navigation Panel
     */
//    public static SourceList getNavigationPanel() {
//        SourceListControlBar controlBar = createControlBar();

//        SourceList sourceList = new SourceList(model);
//        sourceList.installSourceListControlBar(controlBar);

//        return sourceList;

//        JSplitPane splitPane = MacWidgetFactory.createSplitPaneForSourceList(
//                sourceList, new JLabel("Mac Widgets for Java"));
//        controlBar.installDraggableWidgetOnSplitPane(splitPane);
//        return splitPane;
//    }
}
