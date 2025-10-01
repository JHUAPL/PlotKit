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

import plotkit.text.TextProvider;

/**
 * Interface that defines an object as listening for when a TextProvider is changed.
 *
 * @author lopeznr1
 */
public interface TextProviderChangeListener
{
	/**
	 * Method will be called when a TextProvider has been changed.
	 *
	 * @param source
	 *    The object that caused the event.
	 * @param aTextProvider
	 *    The new TextProvider to be used.
	 */
	public void textProviderChanged(Object source, TextProvider aTextProvider);

}
