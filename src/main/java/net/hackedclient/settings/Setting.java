/*
 * Copyright (c) 2014-2022 Wurst-Imperium and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package net.hackedclient.settings;

import com.google.gson.JsonElement;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;
import net.hackedclient.HackedClient;
import net.hackedclient.gui.Component;
//import net.hackedclient.keybinds.PossibleKeybind;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

public abstract class Setting
{
	private final String name;
	private final String description;
	
	public Setting(String name, String description)
	{
		this.name = Objects.requireNonNull(name);
		this.description = Objects.requireNonNull(description);
	}
	
	public final String getName()
	{
		return name;
	}
	
	/*public final String getDescription()
	{
		return HackedClient.INSTANCE.translate(description);
	}*/
	
	/*public final String getWrappedDescription(int width)
	{
		List<StringVisitable> lines = HackedClient.MC.textRenderer
			.getTextHandler().wrapLines(getDescription(), width, Style.EMPTY);
		
		StringJoiner joiner = new StringJoiner("\n");
		lines.stream().map(StringVisitable::getString)
			.forEach(s -> joiner.add(s));
		
		return joiner.toString();
	}*/
	
	public abstract Component getComponent();
	
	public abstract void fromJson(JsonElement json);
	
	public abstract JsonElement toJson();
	
	public void update()
	{
		
	}
	
	/*public abstract Set<PossibleKeybind> getPossibleKeybinds(
		String featureName);*/
}
