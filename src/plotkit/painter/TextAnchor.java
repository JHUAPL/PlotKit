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
package plotkit.painter;

/**
 * Enum which describes the point at which text should be anchored too.
 *
 * @author lopeznr1
 */
public enum TextAnchor
{
	/**
	 * Text should be anchored to the center of the relevant dimension.
	 * <P>
	 * If the dimension is the y-axis then the accent will be considered but will NOT include the descent.
	 */
	Center,

	/**
	 * Text should be anchored to the center of the relevant dimension.
	 * <P>
	 * If the dimension is the y-axis then the accent and the descent will be considered.
	 * <P>
	 * If the dimension is the x-axis then this is equivalent to {@link PivotPoint#Center}
	 */
	CenterFull,

	/**
	 * Text should be anchored to the start of the relevant dimension.
	 * <P>
	 * If the dimension is the y-axis then the baseline of the font will be used.
	 */
	Lead,

	/**
	 * Text should be anchored to the end of the relevant dimension.
	 * <P>
	 * If the dimension is the y-axis then the top of the bounding box will be used.
	 */
	Tail;
}
