// Copyright (C) 2024 The Johns Hopkins University Applied Physics Laboratory LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package plotkit.demo.gui.edit.cad;

import glum.gui.component.GComboBox;
import glum.gui.panel.CardPanel;
import glum.gui.panel.GPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.JLabel;

import plotkit.cadence.*;
import plotkit.demo.misc.PlainClassNameRenderer;
import net.miginfocom.swing.MigLayout;

public class CadenceInputPanel extends GPanel implements ActionListener
{
	// Gui vars
	private GComboBox<Class<? extends Cadence>> typeBox;
	private CardPanel<SpawnPanel> cardPanel;

	/**
	 * Constructor
	 */
	public CadenceInputPanel()
	{
		// Build the gui areas
		buildGui();

		// Set in the default Cadence
		setCadenceConfig(new PlainAxisCadence(250, 0));

	}

	/**
	 * Returns the selected Cadence.
	 */
	public Cadence getCadenceConfig()
	{
		SpawnPanel spawnPanel = cardPanel.getActiveCard();
		return spawnPanel.getCadence();
	}

	/**
	 * Returns true if the panel is ready to proceed
	 */
	public boolean isReady()
	{
		return cardPanel.getActiveCard().isReady();
	}

	/**
	 * Sets in the current selected Cadence.
	 */
	public void setCadenceConfig(Cadence aCadence)
	{

		Class<? extends Cadence> type = aCadence.getClass();
		typeBox.setChosenItem(type);
		cardPanel.switchToCard(type);

		SpawnPanel tmpPanel = cardPanel.getActiveCard();
		tmpPanel.setCadence(aCadence);
	}

	@Override
	public void actionPerformed(ActionEvent aEvent)
	{
		Object source = aEvent.getSource();
		if (source == typeBox)
			cardPanel.switchToCard(typeBox.getSelectedItem());

		notifyListeners(this, ID_UPDATE);
	}

	private void buildGui()
	{
		setLayout(new MigLayout("", "0[][]0", "0[][]"));

		List<Class<? extends Cadence>> typeList = Arrays.asList(AutoModelCadence.class, FixedModelCadence.class,
				PlainAxisCadence.class, PlainModelCadence.class);
		typeBox = new GComboBox<>(this, typeList);
		typeBox.setRenderer(new PlainClassNameRenderer());
		add("", new JLabel("Cadence Type: "));
		add("pushx,wrap", typeBox);

		cardPanel = new CardPanel<>();
		cardPanel.addCard(AutoModelCadence.class, new AutoModelCadencePanel());
		cardPanel.addCard(FixedModelCadence.class, new FixedModelCadencePanel());
		cardPanel.addCard(PlainAxisCadence.class, new PlainAxisCadencePanel());
		cardPanel.addCard(PlainModelCadence.class, new PlainModelCadencePanel());
		cardPanel.addCard(null, new BlankPanel());
		cardPanel.setBackupCard(null);
		for (SpawnPanel aPanel : cardPanel.getAllCards())
			aPanel.addActionListener(this);
		add(cardPanel, "growx,span");
	}

}
