package com.niccholaspage.nAchievement;

import java.util.HashMap;
import org.bukkit.Achievement;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class nAchievement extends JavaPlugin {
	private HashMap<String, Achievement> achievements = new HashMap<String, Achievement>();
    @Override
	public void onDisable() {
		//Print "Basic Disabled" on the log.
		System.out.println("nAchievement Disabled");
		
	}
    @Override
	public void onEnable() {
       //Get the infomation from the yml file.
        PluginDescriptionFile pdfFile = this.getDescription();
        //Add achievements
        for (int i = 0; i < Achievement.values().length; i++){
        	achievements.put(Achievement.values()[i].name().toLowerCase().replace("_", ""), Achievement.values()[i]);
        }
        //Print that the plugin has been enabled!
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
	}
    private Player getPlayerStartsWith(String startsWith){
    	if (getServer().getOnlinePlayers().length == 0) return null;
    	for (int i = 0; i < getServer().getOnlinePlayers().length; i++){
    		if (getServer().getOnlinePlayers()[i].getName().toLowerCase().startsWith(startsWith.toLowerCase())){
    			return getServer().getOnlinePlayers()[i];
    		}
    	}
    	return null;
    }
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
    	if (args.length < 1) return false;
    	if (args[0].equalsIgnoreCase("add")){
    		//Perma
    		if (args.length < 3){
    			sender.sendMessage("/na add achievement");
    			return true;
    		}
    		if (!(args[2].equalsIgnoreCase("all"))){
    		if (!(achievements.containsKey(args[2].toLowerCase()))){
    			sender.sendMessage("That achievement does not exist!");
    			return true;
    		}
    		}
    		Player player = getPlayerStartsWith(args[1]);
    		if (player == null){
    			sender.sendMessage("That player is not online or doesn't exist.");
    			return true;
    		}
    		if (args[2].equalsIgnoreCase("all")){
    			for (int i = 0; i < Achievement.values().length; i++){
    				player.awardAchievement(Achievement.values()[i]);
    			}
    		}else {
    			player.awardAchievement(achievements.get(args[2].toLowerCase()));
    		}
    	}
    	return true;
    }
}
