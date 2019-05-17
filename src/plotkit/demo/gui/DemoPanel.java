package plotkit.demo.gui;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;

import glum.gui.GuiUtil;
import glum.gui.component.GComboBox;
import glum.gui.icon.IconUtil;
import glum.gui.panel.CardPanel;
import glum.gui.panel.generic.PromptPanel;
import glum.gui.panel.itemList.*;
import glum.gui.panel.itemList.query.QueryComposer;
import net.miginfocom.swing.MigLayout;
import plotkit.Painter;
import plotkit.anchor.*;
import plotkit.demo.data.text.*;
import plotkit.demo.gui.add.*;
import plotkit.demo.gui.edit.*;
import plotkit.demo.misc.PlainClassNameRenderer;
import plotkit.layout.*;
import plotkit.painter.*;
import plotkit.text.*;

public class DemoPanel extends JPanel implements ActionListener, ListSelectionListener
{
	// Ref vars
	private final PlotPanel refPlotPanel;

	// State vars
	private Map<Painter, Anchor> workAnchorMap;
	private TextProviderChanger workTPC;

	// GUI vars
	private AddPainterPanel addPainterPanel;
	private PromptPanel promptPanel;
	private StaticItemProcessor<Painter> painterIP;
	private ItemListPanel<Painter> painterILP;
	private CardPanel<SpawnPanel> editCP;
	private JLabel painterL;
	private JButton addB, delB, upB, dnB;
	private GComboBox<TextProvider> textProviderBox;
	private JCheckBox eastCB, westCB, northCB, southCB;

	public DemoPanel(PlotPanel aPlotPanel)
	{
		refPlotPanel = aPlotPanel;

		workAnchorMap = new HashMap<>();
		workTPC = new TextProviderChanger();

		addPainterPanel = new AddPainterPanel(this, workTPC);
		promptPanel = new PromptPanel(this);
//		promptPanel.setSize(MiscUtil.getDimensionScaled(aFrame.getSize(), 0.60, 0.40));
		promptPanel.setSize(500, 235);
		formUI();

		eastCB.setSelected(refPlotPanel.isDrawEastSide());
		westCB.setSelected(refPlotPanel.isDrawWestSide());
		northCB.setSelected(refPlotPanel.isDrawNorthSide());
		southCB.setSelected(refPlotPanel.isDrawSouthSide());
		setDemoLayout(refPlotPanel.getDemoLayout(), null);

		// Send out the initial notification
		workTPC.notifyListeners(textProviderBox.getChosenItem());
	}

	@Override
	public void actionPerformed(ActionEvent aEvent)
	{
		Object source = aEvent.getSource();
		if (source == addB)
			doAddAction();
		else if (source == delB)
			doDelAction();
		else if (source == upB)
			doMoveUpAction();
		else if (source == dnB)
			doMoveDownAction();
		else if (source instanceof SpawnPanel)
			doEditAction();
		else if (source == textProviderBox)
			doUpdateTextProvider();
		else if (source == eastCB)
		{
			refPlotPanel.setDrawEastSide(eastCB.isSelected());
			doUpdateRefPlotPanel();
		}
		else if (source == westCB)
		{
			refPlotPanel.setDrawWestSide(westCB.isSelected());
			doUpdateRefPlotPanel();
		}
		else if (source == northCB)
		{
			refPlotPanel.setDrawNorthSide(northCB.isSelected());
			doUpdateRefPlotPanel();
		}
		else if (source == southCB)
		{
			refPlotPanel.setDrawSouthSide(southCB.isSelected());
			doUpdateRefPlotPanel();
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent aEvent)
	{
		// Wait until the selection has stopped changing
		if (aEvent.getValueIsAdjusting() == true)
			return;

		doUpdateEditArea();
		doUpdateUI();
	}

	/**
	 * Helper method that executes the add Painter action
	 */
	private void doAddAction()
	{
		// Determine if only 1 Painter has been selected - and if so allow the user to select the insert position
		Painter pickPainter = null;
		if (painterILP.getSelectedItems().size() == 1)
			pickPainter = painterILP.getSelectedItem();

		addPainterPanel.setAllowInsertPositionConfiguration(pickPainter != null);

		// Prompt the user to specify a new Painter
		addPainterPanel.setVisibleAsModal();

		// Bail if no Painter was specified
		Painter newPainter = addPainterPanel.getPainter();
		if (newPainter == null)
			return;

		// Determine the index of the currently selected Painter
		List<Painter> painterList = painterIP.getItems();
		int pickIdx = painterList.size();
		if (pickPainter != null)
			pickIdx = painterList.indexOf(pickPainter);

		// Insert the Painter into the proper position in our list of Painters
		int insertIdx = -1;
		InsertPos insertPos = addPainterPanel.getInsertPosition();
		if (insertPos == InsertPos.BeforeSelection)
			insertIdx = pickIdx;
		else if (insertPos == InsertPos.AfterSelection)
			insertIdx = pickIdx + 1;
		else
			insertIdx = painterList.size();
		painterList.add(insertIdx, newPainter);

		// Update the table
		painterIP.setItems(painterList);

		// Update the UI and the refPlotPane
		doUpdateRefPlotPanel();
		doUpdateUI();
	}

	/**
	 * Helper method that executes the delete Painter action
	 */
	private void doDelAction()
	{
		// Form the infoStr
		List<Painter> delList = painterILP.getSelectedItems();
		String tabStr = "    ";
		String infoStr = "Are you sure you want to delete the selected " + delList.size() + " painters:\n";
		for (Painter aPainter : delList)
			infoStr += tabStr + aPainter.getClass().getSimpleName() + ": " + aPainter.getDescription() + "\n";
		infoStr += "\n";

		// Prompt the user to confirm the action
		promptPanel.setTitle("Confirm Deletion");
		promptPanel.setInfo(infoStr);
		promptPanel.setVisibleAsModal();
		if (promptPanel.isAccepted() == false)
			return;

		// Update the UI and the refPlotPane
		List<Painter> painterList = painterIP.getItems();
		painterList.removeAll(delList);
		painterIP.setItems(painterList);

		editCP.switchToCard(null);
		doUpdateUI();

		doUpdateRefPlotPanel();
	}

	/**
	 * Helper method that executes the edit Painter action.
	 * <P>
	 * The currently selected Painter will be replaced with the Painter retrieved from the editCP.
	 */
	private void doEditAction()
	{
		List<Painter> painterList = painterIP.getItems();
		Painter origPainter = painterILP.getSelectedItem();
		int currIdx = painterList.indexOf(origPainter);

		// Bail if the editPanel is not properly configured
		SpawnPanel editPanel = editCP.getActiveCard();
		if (editPanel.isReady() == false)
			return;

		Painter newPainter = editPanel.getPainter();
		painterList.set(currIdx, newPainter);
		painterIP.setItems(painterList);
		painterILP.selectItem(newPainter);

		// Associate the new Painter with the original Anchor
		Anchor origAnchor = workAnchorMap.get(origPainter);
		workAnchorMap.remove(origPainter);
		workAnchorMap.put(newPainter, origAnchor);

		doUpdateRefPlotPanel();
	}

	/**
	 * Helper method that executes the move Painter up action
	 */
	private void doMoveUpAction()
	{
		List<Painter> fullList = painterIP.getItems();
		List<Painter> pickList = painterILP.getSelectedItems();

		for (Painter aItem : pickList)
		{
			int idx = fullList.indexOf(aItem);
			fullList.remove(idx);
			fullList.add(idx - 1, aItem);
		}
		painterIP.setItems(fullList);

		// Update the UI and the refPlotPane
		doUpdateRefPlotPanel();
		doUpdateUI();
	}

	/**
	 * Helper method that executes the move Painter(s) down action
	 */
	private void doMoveDownAction()
	{
		List<Painter> fullList = painterIP.getItems();
		List<Painter> pickList = painterILP.getSelectedItems();

		Collections.reverse(pickList);
		for (Painter aItem : pickList)
		{
			int idx = fullList.indexOf(aItem);
			fullList.remove(idx);
			fullList.add(idx + 1, aItem);
		}
		painterIP.setItems(fullList);

		// Update the UI and the refPlotPane
		doUpdateRefPlotPanel();
		doUpdateUI();
	}

	/**
	 * Helper method to update the config area
	 */
	private void doUpdateEditArea()
	{
		Painter targPainter = painterILP.getSelectedItem();
		if (painterILP.getSelectedItems().size() > 1)
			targPainter = null;

		// Switch to the proper card
		Object targKey = null;
		if (targPainter != null)
			targKey = targPainter.getClass();

		editCP.switchToCard(targKey);
		SpawnPanel activeCard = editCP.getActiveCard();
		activeCard.setPainter(targPainter);
	}

	/**
	 * Helper method that will update the various UI components to reflect the chosen TextProvider.
	 *
	 * @param aTextProvider
	 *        The TextProvider to be installed. Must not be null.
	 */
	public void doUpdateTextProvider()
	{
		// Retrieve the chosen TextProvider
		TextProvider tmpTextProvider = textProviderBox.getChosenItem();

		// Notify the registered TextProviderChangeListeners
		workTPC.notifyListeners(tmpTextProvider);

		// Determine the currently selected Painter
		Painter activePainter = painterILP.getSelectedItem();

		PlainLayout origLayout = refPlotPanel.getDemoLayout();
		PlainLayout targLayout = new PlainLayout();

		// Synthesize a new PlainLayout so that all TextTickPainter/TextMarkerPainter will be replaced
		// with corresponding TextTickPainter/TextMarkerPointer with the updated TextProvider
		for (Painter aPainter : origLayout.getPainters())
		{
			Painter tmpPainter = aPainter;
			Anchor tmpAnchor = origLayout.getAnchor(tmpPainter);

			if (aPainter instanceof TextTickPainter)
			{
				TextTickPainter bPainter = (TextTickPainter) aPainter;
				tmpPainter = new TextTickPainter(tmpTextProvider, bPainter.getCadence(), bPainter.getColor(),
						bPainter.getFont(), bPainter.getAnchor(), bPainter.getAngle(), true);
			}
			else if (aPainter instanceof TextMarkerPainter)
			{
				TextMarkerPainter bPainter = (TextMarkerPainter) aPainter;
				tmpPainter = new TextMarkerPainter(tmpTextProvider, bPainter.getColorBG(), bPainter.getColorFG(),
						bPainter.getFont(), bPainter.getAnchor(), bPainter.getAngle(), bPainter.getPadSize());
			}

			targLayout.addPainter(tmpPainter, tmpAnchor);

			// Update the reference to the active Painter
			if (aPainter == activePainter)
				activePainter = tmpPainter;
		}

		// Update the refPlotPanel
		refPlotPanel.setDemoLayout(targLayout);

		// Update our internal UI
		setDemoLayout(targLayout, activePainter);
	}

	/**
	 * Helper method that keeps the various UI components synchronized.
	 */
	private void doUpdateUI()
	{
		List<Painter> fullList = painterIP.getItems();
		painterL.setText("Painters: " + fullList.size());

		// Update the action buttons
		List<Painter> pickList = painterILP.getSelectedItems();

		boolean isEnabled = pickList.size() > 0;
		delB.setEnabled(isEnabled);

		isEnabled = pickList.size() > 0 && pickList.contains(fullList.get(0)) == false;
		upB.setEnabled(isEnabled);

		isEnabled = pickList.size() > 0 && pickList.contains(fullList.get(fullList.size() - 1)) == false;
		dnB.setEnabled(isEnabled);
	}

	/**
	 * Helper method to update the refPlotPanel.
	 */
	private void doUpdateRefPlotPanel()
	{
		// Synthesize the new demo Layout
		PlainLayout tmpLayout = new PlainLayout();

		List<Painter> painterL = painterIP.getItems();
		for (Painter aPainter : painterL)
		{
			Anchor tmpAnchor = workAnchorMap.get(aPainter);
			if (tmpAnchor == null)
				tmpAnchor = AnchorFixed.Base;

			tmpLayout.addPainter(aPainter, tmpAnchor);
		}

		// Install the demo Layout
		refPlotPanel.setDemoLayout(tmpLayout);
	}

	/**
	 * Helper method that forms the default GUI
	 */
	private void formUI()
	{
		setLayout(new MigLayout("", "[right][]", "[]"));

		// TextProvider area
		textProviderBox = new GComboBox<>(this, InvalidTextProvider.Default, PiTextProvider.Default,
				RawTextProvider.Default, WattTimeTextProvider.DemoDefault);
		textProviderBox.setChosenItem(WattTimeTextProvider.DemoDefault);
		textProviderBox.setRenderer(new PlainClassNameRenderer());
		add("align left,span,split", new JLabel("TextProvider"));
		add("wrap", textProviderBox);

		// NSWE enable area
		westCB = GuiUtil.createJCheckBox("West", this);
		eastCB = GuiUtil.createJCheckBox("East", this);
		northCB = GuiUtil.createJCheckBox("North", this);
		southCB = GuiUtil.createJCheckBox("South", this);
		add("align center,span,split", westCB);
		add("gapright 15", eastCB);
		add("gapleft 15", northCB);
		add("wrap", southCB);

		add("growx,h 4!,span,wrap", GuiUtil.createDivider());

		// Painter table area
		painterL = new JLabel("Painters: ---");
		upB = GuiUtil.createJButton(IconUtil.loadIcon("resources/icons/ArrowUp.16.png"), this, "Move Up");
		dnB = GuiUtil.createJButton(IconUtil.loadIcon("resources/icons/ArrowDn.16.png"), this, "Move Down");
		addB = GuiUtil.createJButton(IconUtil.loadIcon("resources/icons/ItemAdd.16.png"), this, "Add Item");
		delB = GuiUtil.createJButton(IconUtil.loadIcon("resources/icons/ItemDel.16.png"), this, "Remove Item");
		add(painterL, "growx,pushx,span,split");
		add(upB, "w 18!,h 18!");
		add(dnB, "w 18!,h 18!");
		add(addB, "w 18!,h 18!");
		add(delB, "w 18!,h 18!,wrap 2");

		// Painter table area / and edit area
		QueryComposer<LookUp> tmpComposer = new QueryComposer<>();
		tmpComposer.addAttribute(LookUp.Type, String.class, "Type", null);
		tmpComposer.addAttribute(LookUp.Description, String.class, "Description", null);
		tmpComposer.get(0).minSize = (int) (tmpComposer.get(0).defaultSize * 3.5);
		tmpComposer.get(1).minSize = (int) (tmpComposer.get(1).defaultSize * 3.5);

		PainterItemHandler tmpIH = new PainterItemHandler(tmpComposer);
		painterIP = new StaticItemProcessor<>();
		painterILP = new ItemListPanel<>(tmpIH, painterIP, false, true);
		painterILP.setSortingEnabled(false);
		painterILP.addListSelectionListener(this);

		editCP = new CardPanel<>();
		editCP.addCard(PlumbLinePainter.class, new PlumbLinePanel(this));
		editCP.addCard(ExteriorTickPainter.class, new PlainTickPanel(this));
		editCP.addCard(TextTickPainter.class, new TextTickPanel(this, workTPC));
		editCP.addCard(TextMarkerPainter.class, new TextMarkerPanel(this, workTPC));
		editCP.addCard(null, new BlankPanel());
		editCP.setBackupCard(null);

		JSplitPane tmpSP = new JSplitPane(JSplitPane.VERTICAL_SPLIT, painterILP, editCP);
		tmpSP.setBorder(null);
		tmpSP.setContinuousLayout(true);
		add(tmpSP, "growx,growy,pushy,span,wrap 0");
	}

	/**
	 * Updates the GUI to reflect the specified demo Layout.
	 */
	private void setDemoLayout(PlainLayout aLayout, Painter aActivePainter)
	{
		// Install the Painters
		List<Painter> painterL = aLayout.getPainters();
		painterIP.setItems(painterL);

		// Keep track of the Painter/Anchors mapping
		workAnchorMap = new HashMap<>();
		for (Painter aPainter : painterL)
		{
			Anchor tmpAnchor = aLayout.getAnchor(aPainter);
			workAnchorMap.put(aPainter, tmpAnchor);
		}

		// Update the painterILP
		if (aActivePainter != null)
			painterILP.selectItem(aActivePainter);

		doUpdateUI();
	}

}
