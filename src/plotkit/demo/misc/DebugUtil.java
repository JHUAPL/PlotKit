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
package plotkit.demo.misc;

import plotkit.misc.LogicError;

/**
 * This class provides a collection of utility methods which serve as place holders for code still in development.
 * <P>
 * Thus any method will be defined as deprecated - so that is shows up as a warning in the IDE.
 *
 * @author lopeznr1
 */
public class DebugUtil
{

	/**
	 * Prints out a message that the logic has not fully been developed.
	 * <P>
	 * The line number, class name, and method will be logged to the error console.
	 */
	@Deprecated
	public static void wrnincompleteLogic()
	{
		Throwable tmpThrowable = new Throwable();
		StackTraceElement ste = tmpThrowable.getStackTrace()[1];
		System.err.println(
				"Incomplete code: " + ste.getClassName() + "." + ste.getMethodName() + "  -> L:" + ste.getLineNumber());
	}

	/**
	 * Throws a LogicError. This should be used as a place holder for code that is not complete and should not be run.
	 * <P>
	 * The line number, class name, and method will be logged to the error console.
	 */
	@Deprecated
	public static void errIncompleteLogic()
	{
		throw new LogicError("Incomplete code.");
	}

}
