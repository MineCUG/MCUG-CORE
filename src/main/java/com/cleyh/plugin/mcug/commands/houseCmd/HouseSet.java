package com.cleyh.plugin.mcug.commands.houseCmd;

import com.cleyh.plugin.mcug.Mcug;
import com.cleyh.plugin.mcug.object.building.House.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HouseSet implements CommandExecutor {
    private static boolean isNumeric(String str){
        for (int i = str.length();--i>=0;){
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String cmdName = cmd.getName();
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a Player");
            return false;
        }
        /*
        这段代码与公寓有关，因为公寓类为实现，删除了
        if(cmdName.equalsIgnoreCase("house-set-apartment")){
            Mcug.main().reloadHouseConfig();
            if(args.length!=2) return false;
            if(!isNumeric(args[1])) return false;

            String name = args[0];
            int roomNumber = Integer.parseInt(args[1]);

            Apartment setNew = new Apartment(name,roomNumber, (Player) sender,((Player) sender).getLocation());
            if(setNew.saveDataToFile()){
                sender.sendMessage("Your house is saved! ID: "+setNew.getID());
            }else{
                sender.sendMessage("got some error,Your house isn't saved!");
            }
            return true;
        }
        */
        if(cmdName.equalsIgnoreCase("house-set-villa")){
            Mcug.main().reloadHouseConfig();
            if(args.length < 1||args.length > 2) return false;
            String name = args[0];
            Villa setNew;
            if(args.length==2){
                if(!isNumeric(args[1])) return false;
                int price = Integer.parseInt(args[1]);
                setNew = new Villa(name, (Player) sender,((Player) sender).getLocation(),price);
            } else {
                setNew = new Villa(name, (Player) sender,((Player) sender).getLocation());
            }
            if(setNew.saveDataToFile()){
                sender.sendMessage("Your house is saved! ID: "+setNew.getID());
            }else{
                sender.sendMessage("got some error,Your house isn't saved!");
            }
            return true;
        }
        return false;
    }
}
