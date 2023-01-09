package com.cleyh.plugin.mcug.hotel;

import com.cleyh.plugin.mcug.Mcug;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Objects;

public class HotelCMD implements CommandExecutor {

    public static boolean isNumeric(String str){
        for (int i = str.length();--i>=0;){
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }
    public void setData(String name,String type,int rooms,CommandSender sender){
        Mcug.main().reloadHouseConfig();
        FileConfiguration config = Mcug.main().getHouseConfig();
        Player player = (Player) sender;

        int i=0;
        for(;;i++) { if(!config.isSet("ID."+i+".name")) { break; } }
        config.set("ID."+i+".name",name);
        config.set("ID."+i+".type",type);
        config.set("ID."+i+".owner",sender.getName());
        config.set("ID."+i+".location",player.getLocation());
        config.set("ID."+i+".selling",false);
        config.set("ID."+i+".price",0);

        if(Objects.equals(type, "hotel")){
            for(int j = 0; j<rooms; j++){
                config.set("ID."+i+".rooms."+j+".rent",0);
                config.set("ID."+i+".rooms."+j+".price",0);
            }
        }
        //save
        if(Mcug.main().saveHouseConfig()) sender.sendMessage("You set a "+type+" named "+name+".ID: "+i+" !");
        else sender.sendMessage("Something wrong and your hotel/house can not be register.Please contact the operator");
    }
    public  void delData(int id,CommandSender sender){
        Mcug.main().reloadHouseConfig();
        FileConfiguration config = Mcug.main().getHouseConfig();
        if(config.isSet("ID."+id+".name") && Objects.equals(config.get("ID." + id + ".owner"), sender.getName())){
            config.set("ID."+id,null);
            if(Mcug.main().saveHouseConfig()){
                sender.sendMessage("You delete a house/hotel named.ID: "+id+" !");
            }
            else{
                sender.sendMessage("Something wrong and your hotel/house can not be delete.Please contact the operator");
            }
        }
        else{
            sender.sendMessage("You are not the owner.Check the ID you typed.");
        }
    }
    public void checkIn(int id,int room,CommandSender sender){
        Mcug.main().reloadHouseConfig();
        FileConfiguration config = Mcug.main().getHouseConfig();

        boolean selling = config.getBoolean("ID."+id+".selling");
        String rent = config.getString("ID."+id+".type");

        if(!selling){
            sender.sendMessage("this rooms can't be checked");
            return;
        }
        if (Objects.equals(rent, "hotel")){
            rent = config.getString("ID."+id+".rooms."+room+".rent");
            if(Objects.equals(rent, "0")){
                rent = sender.getName();
                config.set("ID."+id+".rooms."+room+".rent",rent);
                sender.sendMessage("Well done!");
                Mcug.main().saveHouseConfig();
            }
            else sender.sendMessage("this rooms has been claim.");
        }
        else{
            sender.sendMessage("this is not a hotel");
        }
        Mcug.main().saveHouseConfig();
    }

    @Override
    public  boolean onCommand(CommandSender sender, Command cmd,String label,String[] arg){
        String cmd_name = cmd.getName();

        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a Player");
            return false;
        }

        if(cmd_name.equalsIgnoreCase("hotel-Check-in")){
            if(arg.length!=2) return false;
            if(!isNumeric(arg[0]) || !isNumeric(arg[1])) return false;
            int id = Integer.parseInt(arg[0]),room = Integer.parseInt(arg[1])-1;
            if(room <0) return false;
            checkIn(id,room,sender);
            return true;
        }

        else if (cmd_name.equalsIgnoreCase("house-buy")) {
            sender.sendMessage("this function isn't finished");
            return true;
        }

        else if(cmd_name.equalsIgnoreCase("hotel-set")){
            if(arg.length == 0 || arg.length >= 3) return false;
            int rooms = 1;
            String name = arg[0],type = "hotel";
            if(arg.length == 2){
                if(isNumeric(arg[1])) rooms = Integer.parseInt(arg[1]);
                else return false;
            }
            setData(name,type,rooms,sender);
            return true;
        }

        else if(cmd_name.equalsIgnoreCase("house-set")){
            if(arg.length != 1) return false;
            int rooms = 1;
            String name = arg[0],type = "house";
            setData(name,type,rooms,sender);
            return true;
        }

        else if (cmd_name.equalsIgnoreCase("hotel-remove")){
            if(arg.length != 1) return false;
            if(!isNumeric(arg[0])) return false;
            int id = Integer.parseInt(arg[0]);
            delData(id,sender);
            return true;
        }

        else if(cmd_name.equalsIgnoreCase("house-remove")){
            if(arg.length != 1) return false;
            if(!isNumeric(arg[0])) return false;
            int id = Integer.parseInt(arg[0]);
            delData(id,sender);
            return true;
        }

        return false;
    }

}
