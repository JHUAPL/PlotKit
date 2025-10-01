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
package plotkit.demo.data.text;

import java.util.*;

import com.google.common.collect.ImmutableList;

import plotkit.text.TextProvider;

/**
 * Object that will be used to route notification of TextProvider change events.
 *
 * @author lopeznr1
 */
public class TextProviderChanger
{
	private List<TextProviderChangeListener> listenerList;

	public TextProviderChanger()
	{
		listenerList = new ArrayList<>();
	}

	/**
	 * Registers a Listener with this TextProviderChanger
	 */
	public synchronized void addListener(TextProviderChangeListener aListener)
	{
		Objects.requireNonNull(aListener);

		listenerList.add(aListener);
	}

	/**
	 * Sends out notification to the listeners of the specified TextProvider.
	 */
	public synchronized void notifyListeners(TextProvider aTextProvider)
	{
		ImmutableList<TextProviderChangeListener> tmpList = ImmutableList.copyOf(listenerList);

		for (TextProviderChangeListener aListener : tmpList)
			aListener.textProviderChanged(this, aTextProvider);
	}

}
