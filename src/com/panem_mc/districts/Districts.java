package com.panem_mc.districts;


import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

public class Districts extends JavaPlugin implements Listener{
    
    public void onEnable(){
        getServer().getPluginManager().registerEvents(this, this);
        saveDistricts();
    }

    public void onDisable(){
        saveConfig();
        saveDistricts();
    }

    private FileConfiguration districts = null;
    private File districtsFile = null;

    //reload districts
    public void reloadDistricts(){
        if(districtsFile==null){
            districtsFile = new File(getDataFolder(), "districts.yml");
        }
        districts = YamlConfiguration.loadConfiguration(districtsFile);
    }

    //get districts
    public FileConfiguration getDistricts() {
        if (districtsFile == null) {
            reloadDistricts();
        }
        return districts;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerMove(PlayerMoveEvent e){
        Player player = e.getPlayer();
        String d = this.getDistricts().getString(player.getName());
        if (d == null || d.equalsIgnoreCase("Invalid")){
            player.sendMessage(ChatColor.RED+"Select a district with /selectdistrict (districtname)");
            e.setCancelled(true);
        }
    }


    public void saveDistricts() {
        if (districts == null || districtsFile == null) {
            return;
        }
        try {
            getDistricts().save(districtsFile);
        } catch (IOException ex) {
            getLogger().log(Level.SEVERE, "Could not save config to " + districtsFile, ex);
        }
    }
    public String playerDistrict (String pname){
        return this.getDistricts().getString(pname);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChat(AsyncPlayerChatEvent e){
        Player player = e.getPlayer();
        String pName = player.getName();
        if (playerDistrict(pName) == null){
            e.setCancelled(true);
            player.sendMessage("You have to choose a district before you can chat");
            player.sendMessage(getD(e.getMessage()));
        }
        else if (playerDistrict(pName).equalsIgnoreCase("district1")){
            e.setFormat("§0[§7District 1§0]§f %s: %s");
        }
        else if (playerDistrict(pName).equalsIgnoreCase("district2")){
            e.setFormat("§0[§7District 2§0]§f %s: %s");
        }
        else if (playerDistrict(pName).equalsIgnoreCase("district3")){
            e.setFormat("§0[§7District 3§0]§f %s: %s");
        }
        else if (playerDistrict(pName).equalsIgnoreCase("district4")){
            e.setFormat("§0[§7District 4§0]§f %s: %s");
        }
        else if (playerDistrict(pName).equalsIgnoreCase("district5")){
            e.setFormat("§0[§7District 5§0]§f %s: %s");
        }
        else if (playerDistrict(pName).equalsIgnoreCase("district6")){
            e.setFormat("§0[§7District 6§0]§f %s: %s");
        }
        else if (playerDistrict(pName).equalsIgnoreCase("district7")){
            e.setFormat("§0[§7District 7§0]§f %s: %s");
        }
        else if (playerDistrict(pName).equalsIgnoreCase("district8")){
            e.setFormat("§0[§7District 8§0]§f %s: %s");
        }
        else if (playerDistrict(pName).equalsIgnoreCase("district9")){
            e.setFormat("§0[§7District 9§0]§f %s: %s");
        }
        else if (playerDistrict(pName).equalsIgnoreCase("district10")){
            e.setFormat("§0[§7District 10§0]§f %s: %s");
        }
        else if (playerDistrict(pName).equalsIgnoreCase("district11")){
            e.setFormat("§0[§7District 11§0]§f %s: %s");
        }
        else if (playerDistrict(pName).equalsIgnoreCase("district12")){
            e.setFormat("§0[§7District 12§0]§f %s: %s");
        }
        else if (playerDistrict(pName).equalsIgnoreCase("district13")){
            e.setFormat("§0[§7District 13§0]§f %s: %s");
        }
        else {
            e.setCancelled(true);
            player.sendMessage("You have to choose a district before you can chat");
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {

        if(cmd.getName().equalsIgnoreCase("selectdistrict")) {
            if(args.length == 0 || args.length == 1) {
                return TabCompletionHelper.getPossibleCompletionsForGivenArgs(args, new String[] {"district1", "district2", "district3", "district4", "district5", "district6", "district7", "district8", "district9", "district10", "district11", "district12", "district13"});
            }
        }
        else if (cmd.getName().equalsIgnoreCase("districts")){
            if (args.length==0 || args.length==1){
                return TabCompletionHelper.getPossibleCompletionsForGivenArgs(args, new String[] {"help", "commands"});
            }
        }

        return null;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        String pName = player.getName();
        String a = args[0];

        if (cmd.getName().equalsIgnoreCase("setdistrict")){
            if (args.length!=2){
                return false;
            }
            if (getD(args[1]).equalsIgnoreCase("Invalid")){
                player.sendMessage("That is not a valid district");
                return true;
            }

            else{
                this.getDistricts().set(a, getD(args[1]));
                player.sendMessage(ChatColor.AQUA+"[Districts] "+ChatColor.WHITE+ "You have changed "+args[0]+"'s district to " + args[1]);
            }
            return true;
        }

        else if (cmd.getName().equalsIgnoreCase("selectdistrict")){
            if (args.length!=1){
                return false;
            }
            if (!(this.getDistricts().getString(pName) == null)){
                player.sendMessage(ChatColor.BLUE+"You can only select your district once");
                return true;
            }
            if (getD(a).equalsIgnoreCase("Invalid")){
                player.sendMessage(ChatColor.RED+"That is an invalid district");
                return true;
            }
            else {
                this.getDistricts().set(pName, getD(a));
                player.sendMessage("You are now a member of district "+a);
                return true;
            }
        }

        else if (cmd.getName().equalsIgnoreCase("districts")){
            if (args[0]==null){
                PluginDescriptionFile desc = this.getServer().getPluginManager().getPlugin("Districts").getDescription();
                player.sendMessage(ChatColor.DARK_BLUE+"Districts plugin version "+desc.getVersion()+ " made by minecreatr for panem-mc");
                player.sendMessage("Type '/districts' help for more information");
            }
            else if (args[0].equalsIgnoreCase("commands")){
                player.sendMessage(ChatColor.AQUA+"----Districts Commands----");
                player.sendMessage(ChatColor.AQUA+"Commands:");
                player.sendMessage(ChatColor.AQUA+"/selectdistrict (district name)- Command can only be used by a player once and assigns them to the specified district");
                if (player.isOp()){
                    player.sendMessage(ChatColor.DARK_BLUE+"-------------------------");
                    player.sendMessage(ChatColor.DARK_BLUE+"Operator Only Commands");
                    player.sendMessage(ChatColor.DARK_BLUE+"/setdistrict (player) (district)");
                }
                return true;
            }

            else if (args[0].equalsIgnoreCase("help")){
                player.sendMessage(ChatColor.AQUA+"----Districts Help----");
                player.sendMessage(ChatColor.AQUA+"Type '/districts commands' for a list of all commands");
                //player.sendMessage(ChatColor.AQUA+"Type '/districts (district name)' for info on the specified districts");  This is still WIP, it does not work
                return true;
            }

            else {
                player.sendMessage(ChatColor.RED+"Invalid argument");
                player.sendMessage(ChatColor.RED+"Type '/districts help' for more info");
                return true;
            }
        }
        return false;
    }

    public String getD(String a){
        if (a.equalsIgnoreCase("district1") || a.equalsIgnoreCase("1") || a.equalsIgnoreCase("d1")){
            return "district1";
        }
        else if (a.equalsIgnoreCase("district2") || a.equalsIgnoreCase("2") || a.equalsIgnoreCase("d2")){
            return "district2";
        }
        else if (a.equalsIgnoreCase("district3") || a.equalsIgnoreCase("3") || a.equalsIgnoreCase("d3")){
            return "district3";
        }
        else if (a.equalsIgnoreCase("district4") || a.equalsIgnoreCase("4") || a.equalsIgnoreCase("d4")){
            return "district4";
        }
        else if (a.equalsIgnoreCase("district5") || a.equalsIgnoreCase("5") || a.equalsIgnoreCase("d5")){
            return "district5";
        }
        else if (a.equalsIgnoreCase("district6") || a.equalsIgnoreCase("6") || a.equalsIgnoreCase("d6")){
            return "district6";
        }
        else if (a.equalsIgnoreCase("district7") || a.equalsIgnoreCase("7") || a.equalsIgnoreCase("d7")){
            return "district7";
        }
        else if (a.equalsIgnoreCase("district8") || a.equalsIgnoreCase("8") || a.equalsIgnoreCase("d8")){
            return "district8";
        }
        else if (a.equalsIgnoreCase("district9") || a.equalsIgnoreCase("9") || a.equalsIgnoreCase("d9")){
            return "district9";
        }
        else if (a.equalsIgnoreCase("district10") || a.equalsIgnoreCase("10") || a.equalsIgnoreCase("d10")){
            return "district10";
        }
        else if (a.equalsIgnoreCase("district11") || a.equalsIgnoreCase("11") || a.equalsIgnoreCase("d11")){
            return "district11";
        }
        else if (a.equalsIgnoreCase("district12") || a.equalsIgnoreCase("12") || a.equalsIgnoreCase("d12")){
            return "district12";
        }
        else if (a.equalsIgnoreCase("district13") || a.equalsIgnoreCase("13") || a.equalsIgnoreCase("d13")){
            return "district13";
        }
        else {
            return "Invalid";
        }
    }
}
