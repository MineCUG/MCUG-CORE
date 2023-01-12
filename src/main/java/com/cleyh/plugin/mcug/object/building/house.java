package com.cleyh.plugin.mcug.object.building;

import com.cleyh.plugin.mcug.Mcug;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.sql.Struct;


public class House {
    //公寓
    // 这一部分我遇到了些问题，感觉是比较容易解决的，但是某些原因，先搁置了
    // 考虑删除
    /*
    public static class Apartment {

        // 变量：
        //   公寓类型：ID，房间数量，名称，地址
        //   房间类型：房间号，拥有者
        // 构造函数：
        //   创建新公寓时需要包含：房间数量、名称、地址
        //   创建已有的公寓时需要包含：ID
        // 函数：
        //   改名
        //   改拥有者
        //   改地址
        // 函数：
        //   读取数据
        //   保存数据
        //   读取新ID

        int roomNumber,ID;
        String name,manager;
        Location location;
        ApRoom[] room;
        FileConfiguration file = Mcug.main().getHouseConfig();

        public Apartment(String name1, int roomNumber1, Player owner1, Location location1){
            ID = loadLatestID();
            name = name1;
            manager = owner1.getName();
            roomNumber = roomNumber1;
            location = location1;
            room = new ApRoom[roomNumber];
            for(int i = 0;i<roomNumber;i++){
                room[i] = new ApRoom();
                room[i].roomID = i+1;
                room[i].owner = owner1.getName();
                room[i].price = 0;
                room[i].onSelling = false;
            }
        }
        public Apartment(int lid){
            loadDataFromFile(lid);
        }
        //子类：公寓房间
        public class ApRoom {
            String owner;
            Boolean onSelling;
            int roomID,price;
        }

        public int getIntID() {
            return ID;
        }
        public String getID() {
            return "ag-"+ID;
        }

        public void setNewName(String NewName){
            name = NewName;
        }
        public void setNewManager(Player newManager) {
            manager = newManager.getName();
        }
        public void setNewLocation(Location NewLocation){
            location = NewLocation;
        }
        public void setRoomNumber(int newNumber) {
            roomNumber = newNumber;
            room = new ApRoom[roomNumber];
            for(int i=1;i<=roomNumber;i++){
                room[i].roomID = i;
            }
        }
        public void setOwner(int rid,Player newOwner){
            if(rid > roomNumber||rid <= 0)return;
            room[rid].owner = newOwner.getName();
        }
        public void setNewPrice(int rid,int newPrice) {
            if(rid > roomNumber||rid <= 0)return;
            room[rid].price = newPrice;
        }
        public void setOnSelling(int rid,Boolean newBool) {
            if(rid > roomNumber||rid <= 0)return;
            room[rid].onSelling = newBool;
        }

        public boolean saveDataToFile(){
            String path = "apartment.ap-"+ID+".";
            file.set(path+"name",name);
            file.set(path+"manager",manager);
            file.set(path+"location",location);
            file.set(path+"roomNumber",roomNumber);
            path = "apartment.ap-"+ID+".rooms.";
            for(int i=1;i<=roomNumber;i++){
                file.set(path+i+".owner",room[i].owner);
                file.set(path+i+".price",room[i].price);
                file.set(path+i+".onSelling",room[i].onSelling);
            }
            return Mcug.main().saveHouseConfig();
        }
        public void loadDataFromFile(int lid){
            String path = "apartment.ap-"+lid+".";
            if(!file.isSet(path+"name")) {
                setDefault();
                return;
            }
            //赋值（复制）
            ID = lid;
            name = file.getString(path+"name");
            manager = file.getString(path+"manager");
            location = file.getLocation(path+"location");
            roomNumber = file.getInt(path+"roomNumber");
            room = new ApRoom[roomNumber];
            for(int i=1;i<=roomNumber;i++){
                room[i].owner = file.getString(path+".rooms."+i+".owner");
                room[i].price = file.getInt(path+".rooms."+i+".price");
                room[i].onSelling = file.getBoolean(path+".rooms."+i+".onSelling");
            }
        }
        public int loadLatestID(){
            for(int i=0;;i++){
                if(!file.isSet("apartment.ap-"+i+".name")) {
                    return i;
                }
            }
        }
        private void setDefault(){

        }
    }
    */

    //别墅
    public static class Villa {
        // 变量：
        //   ID，名称，所有者，地址，价格（默认10000），售卖状态（默认关闭）
        // 构造函数：
        //   创建新别墅时至少需要包含：名称、所有者、地址、价格（可选）
        //   创建已有的公寓时需要包含：ID
        // 函数：
        //   改名、改拥有者、改地址、改价格、改售卖状态
        // 函数：
        //   读取数据、保存数据
        //   获取最新未使用过的ID
        int ID,price;
        boolean onSelling;
        String owner,name;
        Location location;
        FileConfiguration file = Mcug.main().getHouseConfig();

        //构造函数
        public Villa(String setName,Player setOwner,Location setLocation){
            this(setName,setOwner,setLocation,10000);//默认价格10000；java的构造函数重写示例
        };
        public Villa(String setName,Player setOwner,Location setLocation,int setPrice){
            ID = loadLatestID();
            name = setName;
            owner = setOwner.getName();
            location = setLocation;
            price = setPrice;
            onSelling = false;
        }

        //获取ID
        public int getIntID(){
            return ID;
        }
        public String getID() {
            return "vi-"+ID;
        }

        //文件操作
        public void loadDataFromFile(int lid){
            String path = "villa.vi-"+lid+".";
            if(!file.isSet(path+"name")) {
                setDefault();
                return;
            }
            ID = lid;
            name = file.getString(path+"name");
            owner = file.getString(path+"owner");
            onSelling = file.getBoolean(path+"onSelling");
            price = file.getInt(path+"price");
            location = file.getLocation(path+"location");
        }
        public boolean saveDataToFile(){
            String path = "villa.vi-"+ID+".";
            file.set(path+"name",name);
            file.set(path+"owner",owner);
            file.set(path+"onSelling",onSelling);
            file.set(path+"price",price);
            file.set(path+"location",location);
            return Mcug.main().saveHouseConfig();
        }
        public int loadLatestID(){
            for(int i=0;;i++){
                if(file.getBoolean("villa.vi-"+i+".del")) continue;
                if(!file.isSet("villa.vi-"+i+".name")) {
                    return i;
                }
            }
        }
        private void setDefault(){ }

    }
}
