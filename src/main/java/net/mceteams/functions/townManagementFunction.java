package net.mceteams.functions;

import org.bukkit.entity.Player;

import java.io.FileNotFoundException;

public class townManagementFunction {

    public static boolean doesMember(String town, Player player) throws FileNotFoundException {
        String towndir = dataManagementFunction.loadData(town,"/towns/","directory");

        if(dataManagementFunction.doesDataExist("member_" + player.getUniqueId(), towndir, "role")) {
            return true;
        }

        return false;
    }

    public static String getRank(String town, Player player) throws FileNotFoundException {
        String towndir = dataManagementFunction.loadData(town,"/towns/","directory");

        if(dataManagementFunction.doesDataExist("member_" + player.getUniqueId(), towndir, "role")) {
            return dataManagementFunction.loadData("member_" + player.getUniqueId(), towndir, "role");
        }
        return null;
    }

    public static boolean doesOwner(String town, Player player) throws FileNotFoundException {
        if (dataManagementFunction.doesDataExist(town, "/towns/", "owner")) {
            if (player.getUniqueId().toString() == dataManagementFunction.loadData(town, "/towns/", "owner")) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public static boolean doesPerm(String town, Player player, String Perm) {
        try {
            String towndir = dataManagementFunction.loadData(town, "/towns/","directory");


            switch (Perm) {
                case "claim" -> {
                    if(dataManagementFunction.loadData("settings_" + player.getUniqueId(), "/towns-dataManagementFunction/" + towndir, "claim").equals("allow")) {
                        return true;
                    }
                }

                case "use" -> {
                    if(dataManagementFunction.loadData("settings_" + player.getUniqueId(), "/towns-dataManagementFunction/" + towndir, "use").equals("allow")) {
                        return true;
                    }
                }

                case "build" -> {
                    if(dataManagementFunction.loadData("settings_" + player.getUniqueId(), "/towns-dataManagementFunction/" + towndir, "build").equals("allow")) {
                        return true;
                    }
                }

                case "destroy" -> {
                    if(dataManagementFunction.loadData("settings_" + player.getUniqueId(), "/towns-dataManagementFunction/" + towndir, "destroy").equals("allow")) {
                        return true;
                    }
                }

                case "home" -> {
                    if(dataManagementFunction.loadData("settings_" + player.getUniqueId(), "/towns-dataManagementFunction/" + towndir, "home").equals("allow")) {
                        return true;
                    }
                }
            }
            return false;
        } catch (FileNotFoundException e) {throw new RuntimeException(e);}
    }

    public static String getTown(Player player, String as) {
        if(as == "owner") {
            String townname = dataManagementFunction.searchData("/towns/","owner",player.getUniqueId().toString());

            if( townname != null ) {
                return townname;
            } else {
                return "null";
            }
        } else if(as == "member") {
            String memberId = player.getUniqueId().toString();
            String fileName = "member_" + memberId;
            String file = dataManagementFunction.searchFoldersData("/towns-dataManagementFunction/","member-id",memberId);

            try {
                if (dataManagementFunction.doesDataExist(dataManagementFunction.loadData(fileName,file,"town"),"/towns/","owner")) {
                    return dataManagementFunction.loadData(fileName,file,"town");
                } else {
                    return "null";
                }
            } catch (FileNotFoundException e) {throw new RuntimeException(e);}
        }


        return "null";
    }
}
