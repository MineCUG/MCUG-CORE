package com.cleyh.plugin.mcug.object.building;

import com.cleyh.plugin.mcug.Mcug;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class house {
    public class hotel{
        int roomAmount,ID;
        Player onwer;
        Location location;

        public class Horoom{
            Player tenant;
            int roomID;
        }

    }

    public class apartment{
        int roomNuber,ID;
        Location location;
        Aproom[] room;
        FileConfiguration file = Mcug.main().getHouseConfig();

        apartment(int Num){
            roomNuber = Num;
            room = new Aproom[Num];
            for(int i=0;i<Num;i++){
                room[i].roomID = i;
            }
        }
        public class Aproom{
            Player onwer;
            int roomID;
        }
        public void setOwner(int rid,Player newOwner){
            if(rid+1 > roomNuber)return;
            room[rid].onwer = newOwner;
        }
        public void setLocation(Location setL){
            location = setL;
        }
        public void saveDataToFile(){

        }
        public void loadDataFromFile(){

        }
    }

    public class villa{
        int ID;
        Player onwer;
        Location location;
    }
}
