/*
 * Copyright (c) 2014-2022 Wurst-Imperium and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package net.hackedclient.utils;

import net.hackedclient.Feature;
import net.hackedclient.HackedClient;
import net.hackedclient.command.CmdError;
import net.hackedclient.settings.Setting;

import java.util.stream.Stream;

public enum CmdUtils
{
	;
	
	public static Feature findFeature(String name) throws CmdError
	{
		Stream<Feature> stream =
			HackedClient.INSTANCE.getNavigator().getList().stream();
		stream = stream.filter(f -> name.equalsIgnoreCase(f.getName()));
		Feature feature = stream.findFirst().orElse(null);
		
		if(feature == null)
			throw new CmdError(
				"A feature named \"" + name + "\" could not be found.");
		
		return feature;
	}
	
	public static Setting findSetting(Feature feature, String name)
		throws CmdError
	{
		name = name.replace("_", " ").toLowerCase();
		Setting setting = feature.getSettings().get(name);
		
		if(setting == null)
			throw new CmdError("A setting named \"" + name
				+ "\" could not be found in " + feature.getName() + ".");
		
		return setting;
	}
}
