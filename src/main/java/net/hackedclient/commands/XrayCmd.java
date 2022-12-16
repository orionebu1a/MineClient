package net.hackedclient.commands;

import net.hackedclient.HackedClient;
import net.hackedclient.command.CmdException;
import net.hackedclient.command.CmdSyntaxError;
import net.hackedclient.command.Command;

public final class XrayCmd extends Command
{

    public XrayCmd()
    {
        super("xray", "Shortcut for '.blocklist X-Ray Ores'.",
                ".xray add <block>", ".xray remove <block>", ".xray list [<page>]",
                ".xray reset", "Example: .xray add gravel");
    }

    @Override
    public void call(String[] args) throws CmdException
    {
        HACKED_CLIENT.getCmdProcessor()
                .process("blocklist X-Ray Ores " + String.join(" ", args));
    }

}
