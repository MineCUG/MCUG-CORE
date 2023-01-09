package com.cleyh.plugin.mcug;

import com.cleyh.plugin.mcug.hotel.HotelCMD;
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
    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("MCUG is Enable");
        instance = this;
        saveDefaultConfig();
        createHotelConfig();
        FileConfiguration config = getConfig();
        Objects.requireNonNull(Bukkit.getPluginCommand("hotel-check-in")).setExecutor(new HotelCMD());
        Objects.requireNonNull(Bukkit.getPluginCommand("house-buy")).setExecutor(new HotelCMD());
        Objects.requireNonNull(Bukkit.getPluginCommand("hotel-set")).setExecutor(new HotelCMD());
        Objects.requireNonNull(Bukkit.getPluginCommand("house-set")).setExecutor(new HotelCMD());
        Objects.requireNonNull(Bukkit.getPluginCommand("hotel-remove")).setExecutor(new HotelCMD());
        Objects.requireNonNull(Bukkit.getPluginCommand("house-remove")).setExecutor(new HotelCMD());
    }

    private File HouseFile;
    private FileConfiguration HouseConfig;
    public FileConfiguration getHouseConfig() {return this.HouseConfig;}
    private void createHotelConfig(){
        HouseFile = new File(getDataFolder(),"hotel.yml");
        if(!HouseFile.exists()){
            HouseFile.getParentFile().mkdirs();
            saveResource("hotel.yml",false);
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

    public static Mcug main(){
        return instance;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("MCUG is Disable");
    }
}
