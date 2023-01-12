package com.cleyh.plugin.mcug.commands.houseCmd;

import com.cleyh.plugin.mcug.Mcug;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Objects;

public class HouseDel implements CommandExecutor {
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
        if(cmdName.equalsIgnoreCase("house-del")){
            if(args.length!=1) return false;
            //if(!args[0].matches("ap-\\d+")||!args[0].matches("vi-\\d+")) {
            //公寓类未实现，故删除上句
            if(!args[0].matches("vi-\\d+")) {
                sender.sendMessage("房产ID格式不对！");
                return false;
            }
            //调用配置文件，准备进行数据写入
            Mcug.main().reloadHouseConfig();
            FileConfiguration file = Mcug.main().getHouseConfig();
            //如果是玩家执行，需要判断是不是他的房子
            if (sender instanceof Player) {
                if (!Objects.equals(file.getString("villa." + args[0] + ".owner"), sender.getName())) {
                    sender.sendMessage("You are not the owner of this house! "+sender.getName());
                    return true;
                }
            }
            //这里本应加一个判断的，但是暂时没有公寓这一类，所以搁置
            //数据写入
            file.set("villa."+args[0]+".name",null);
            file.set("villa."+args[0]+".owner",null);
            file.set("villa."+args[0]+".onSelling",null);
            file.set("villa."+args[0]+".price",null);
            file.set("villa."+args[0]+".location",null);
            file.set("villa."+args[0]+".del",true);
            //保存数据
            Mcug.main().saveHouseConfig();
            return true;
        }
        return false;
    }
}
