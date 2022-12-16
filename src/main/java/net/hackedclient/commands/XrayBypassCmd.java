package net.hackedclient.commands;

import net.hackedclient.command.CmdException;
import net.hackedclient.command.CmdSyntaxError;
import net.hackedclient.command.Command;
import net.hackedclient.hack.bypass.XRayBypass;

public class XrayBypassCmd extends Command {
    public XrayBypassCmd()
    {
        super("xraybypass", "xraybypass settings",
                ".xraybypass delay <long>",
                ".xraybypass radius <long>",
                ".xraybypass skip <int>");
    }

    @Override
    public void call(String[] args) throws CmdException
    {
        if(args.length == 0)
        {
            HACKED_CLIENT.getHax().xRayBypassHack.setEnabled(HACKED_CLIENT.getHax().xRayHack.isEnabled());
            return;
        }
        long value = Long.valueOf(args[1].toLowerCase());
        switch(args[0].toLowerCase())
        {
            default:
                throw new CmdSyntaxError();

            case "delay":
                HACKED_CLIENT.getHax().xRayBypassHack.delayMs = value;
                break;

            case "radius":
                HACKED_CLIENT.getHax().xRayBypassHack.radius = value;
                break;

            case "skip":
                System.out.printf("Nice\n");
                HACKED_CLIENT.getHax().xRayBypassHack.skip = (int)value;
                break;
        }
    }
}
