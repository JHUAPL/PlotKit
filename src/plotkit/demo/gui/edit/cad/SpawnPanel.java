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

import plotkit.cadence.Cadence;
import glum.gui.panel.GPanel;

public abstract class SpawnPanel extends GPanel
{
	/**
	 * Returns true if the SpawnPanel has been properly configured.
	 */
	public abstract boolean isReady();

	/**
	 * Returns the Cadence that reflects this panels GUI.
	 */
	public abstract Cadence getCadence();

	/**
	 * Sets in the Cadence that should be used to configure the GUI.
	 */
	public abstract void setCadence(Cadence aCadence);

}
