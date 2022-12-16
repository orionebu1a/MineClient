/*
 * Copyright (c) 2014-2022 Wurst-Imperium and contributors.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package net.hackedclient.command;

import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.hackedclient.commands.*;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.TreeMap;

public final class CmdList
{
	public final ClearCmd clearCmd = new ClearCmd();
	public final HelpCmd helpCmd = new HelpCmd();
	public final GiveCmd giveCmd = new GiveCmd();
	public final XrayCmd xrayCmd = new XrayCmd();
	public final BlockListCmd blockListCmd = new BlockListCmd();
	public final XrayBypassCmd xraybypassCmd = new XrayBypassCmd();


	
	private final TreeMap<String, Command> cmds =
		new TreeMap<>(String::compareToIgnoreCase);
	
	public CmdList()
	{
		try
		{
			for(Field field : CmdList.class.getDeclaredFields())
			{
				if(!field.getName().endsWith("Cmd"))
					continue;
				
				Command cmd = (Command)field.get(this);
				cmds.put(cmd.getName(), cmd);
			}
			
		}catch(Exception e)
		{
			String message = "Initializing Wurst commands";
			CrashReport report = CrashReport.create(e, message);
			throw new CrashException(report);
		}
	}
	
	public Command getCmdByName(String name)
	{
		return cmds.get("." + name);
	}
	
	public Collection<Command> getAllCmds()
	{
		return cmds.values();
	}
	
	public int countCmds()
	{
		return cmds.size();
	}
}
