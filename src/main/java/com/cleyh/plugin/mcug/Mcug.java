package com.cleyh.plugin.mcug;

import com.cleyh.plugin.mcug.commands.houseCmd.HouseDel;
import com.cleyh.plugin.mcug.commands.houseCmd.HouseSet;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public final class Mcug extends JavaPlugin implements Listener {

    private static Mcug instance;

    //主函数（插件运行时调用）
    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("MCUG is Enable");
        instance = this;
        saveDefaultConfig();
        createHotelConfig();
        FileConfiguration config = getConfig();
        //指令注册
        //Objects.requireNonNull(Bukkit.getPluginCommand("house-set-apartment")).setExecutor(new HouseSet());
        Objects.requireNonNull(Bukkit.getPluginCommand("house-set-villa")).setExecutor(new HouseSet());
        Objects.requireNonNull(Bukkit.getPluginCommand("house-del")).setExecutor(new HouseDel());
    }

    //自定义的
    private File HouseFile;
    private FileConfiguration HouseConfig;
    public FileConfiguration getHouseConfig() {return this.HouseConfig;}
    private void createHotelConfig(){
        HouseFile = new File(getDataFolder(), "house.yml");
        if(!HouseFile.exists()){
            HouseFile.getParentFile().mkdirs();
            saveResource("house.yml",false);
        }
        HouseConfig = new YamlConfiguration();
        try{
            HouseConfig.load(HouseFile);
        }catch (IOException | InvalidConfigurationException e){
            e.printStackTrace();
        }
    }
    public boolean reloadHouseConfig(){
        try{
            HouseConfig.load(HouseFile);
            return true;
        }catch (IOException | InvalidConfigurationException e){
            e.printStackTrace();
            return false;
        }
    }
    public boolean saveHouseConfig(){
        try{
            HouseConfig.save(HouseFile);
            return true;
        }catch (IOException e) {
            e.printStackTrace();
            return false;
            }
    }

    //返回main
    public static Mcug main(){
        return instance;
    }

    //主函数（插件结束时调用）
    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("MCUG is Disable");
    }
}
