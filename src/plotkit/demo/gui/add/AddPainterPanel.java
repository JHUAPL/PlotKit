package plotkit.demo.gui.add;

import glum.gui.GuiUtil;
import glum.gui.component.GComboBox;
import glum.gui.panel.CardPanel;
import glum.gui.panel.GlassPanel;
import glum.gui.panel.generic.GenericCodes;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import plotkit.Painter;
import plotkit.demo.data.text.TextProviderChanger;
import plotkit.demo.gui.edit.*;
import plotkit.demo.misc.*;
import plotkit.misc.LogicError;
import plotkit.painter.*;
import net.miginfocom.swing.MigLayout;

public class AddPainterPanel extends GlassPanel implements ActionListener, GenericCodes
{
	// State vars
	private boolean isAccepted;

	// GUI vars
	private GComboBox<InsertPos> insertPosBox;
	private GComboBox<Class<? extends Painter>> painterTypeBox;
	private JButton cancelB, acceptB;
	private CardPanel<SpawnPanel> editCP;

	public AddPainterPanel(Component aParent, TextProviderChanger aTextProviderChanger)
	{
		super(aParent);

		isAccepted = false;

		formUI(aTextProviderChanger);
		doUpdateUI(null);
	}

	/**
	 * Returns the position where the new item should be added.
	 */
	public InsertPos getInsertPosition()
	{
		return insertPosBox.getChosenItem();
	}

	/**
	 * Returns the Painter that user selected.
	 * <P>
	 * Returns null if the user aborted the action.
	 */
	public Painter getPainter()
	{
		// Bail if this panel was not accepted
		if (isAccepted == false)
			return null;

		// Return the Painter as specified in the editCP
		SpawnPanel spawnPanel = editCP.getActiveCard();
		if (spawnPanel instanceof BlankPanel == false)
			return spawnPanel.getPainter();

		throw new LogicError("Invalid configuration.");
	}

	/**
	 * Method which sets whether the user can configure the insert position.
	 * <P>
	 * If set to false then the insertPosBox UI will be set and locked to: AtEndOfList
	 */
	public void setAllowInsertPositionConfiguration(boolean aBool)
	{
		// We need the previous enable to allow for a better default selection (whenever enable state changes)
		boolean prevBool = insertPosBox.isEnabled();

		// Update the enable state
		insertPosBox.setEnabled(aBool);

		// Set in the default selection
		if (aBool == false)
			insertPosBox.setChosenItem(InsertPos.AtEndOfList);
		else if (aBool != prevBool)
			insertPosBox.setChosenItem(InsertPos.AfterSelection);
	}

	@Override
	public void actionPerformed(ActionEvent aEvent)
	{
		Object source = aEvent.getSource();
		if (source == acceptB)
			doAcceptAction();
		else if (source == cancelB)
			doCancelAction();

		doUpdateUI(source);
	}

	/**
	 * Helper method to process the accept action.
	 */
	private void doAcceptAction()
	{
		isAccepted = true;
		setVisible(false);
	}

	/**
	 * Helper method to process the cancel action.
	 */
	private void doCancelAction()
	{
		isAccepted = false;
		setVisible(false);
	}

	/**
	 * Helper method that keeps the various UI components synchronized.
	 */
	private void doUpdateUI(Object aSource)
	{
		// Switch to the proper card
		Object targKey = painterTypeBox.getChosenItem();
		editCP.switchToCard(targKey);

		boolean isEnabled = editCP.getActiveCard().isReady() == true;
		acceptB.setEnabled(isEnabled);
	}

	/**
	 * Helper method that forms the actual UI.
	 */
	private void formUI(TextProviderChanger aTextProviderChanger)
	{
		setLayout(new MigLayout("", "[right][]", "[]"));
		Font tmpFont = (new JTextField()).getFont();

		// Title area
		JLabel titleL = new JLabel("Add a Painter");
		add(titleL, "align center,span,wrap");

		// Divider
		add(GuiUtil.createDivider(), "growx,h 4!,span,wrap");

		// Config area
		JLabel painterTypeL = new JLabel("Painter Type:");
		List<Class<? extends Painter>> typeList = Arrays.asList(ExteriorTickPainter.class, PlumbLinePainter.class,
				TextTickPainter.class, TextMarkerPainter.class);
		painterTypeBox = new GComboBox<>(this, typeList);
		painterTypeBox.setRenderer(new PlainClassNameRenderer());
		painterTypeBox.setFont(tmpFont);
		add(painterTypeL, "");
		add(painterTypeBox, "wrap");

		JLabel insertPosL = new JLabel("Insert Pos:");
		insertPosBox = new GComboBox<>(this, InsertPos.values());
		insertPosBox.setChosenItem(InsertPos.AfterSelection);
		insertPosBox.setFont(tmpFont);
		add(insertPosL, "");
		add(insertPosBox, "wrap");

		// Divider
		add(GuiUtil.createDivider(), "growx,h 4!,span,wrap");

		// Editor area
		editCP = new CardPanel<>();
		editCP.addCard(PlumbLinePainter.class, new PlumbLinePanel(this));
		editCP.addCard(ExteriorTickPainter.class, new PlainTickPanel(this));
		editCP.addCard(TextTickPainter.class, new TextTickPanel(this, aTextProviderChanger));
		editCP.addCard(TextMarkerPainter.class, new TextMarkerPanel(this, aTextProviderChanger));
		editCP.addCard(null, new BlankPanel());
		editCP.setBackupCard(null);
		add(editCP, "growy,pushy,span,split,w 300::,wrap");
		for (SpawnPanel aPanel : editCP.getAllCards())
			aPanel.addActionListener(this);

//		// Divider
//		add(GuiUtil.createDivider(), "growx,h 4!,span,wrap");

		// Control area
		cancelB = GuiUtil.createJButton("Cancel", this, tmpFont);
		acceptB = GuiUtil.createJButton("Accept", this, tmpFont);
		add(cancelB, "align right,span,split");
		add(acceptB, "");

		// Border
		setBorder(new BevelBorder(BevelBorder.RAISED));
	}

}
