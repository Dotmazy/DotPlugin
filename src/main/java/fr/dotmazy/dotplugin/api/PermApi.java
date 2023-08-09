package fr.dotmazy.dotplugin.api;

import fr.dotmazy.dotplugin.DotPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PermApi {

    /**
     * Make a list of permissions.
     *
     * @param permissions Player to define the rank
     * @return List of all permissions
     * @apiNote It's just for make your life easier.
     */
    public static List<String> makePermissions(String... permissions){
        return new ArrayList<>(Arrays.asList(permissions));
    }

    /**
     * Get all existing permissions
     *
     * @return A list of all permissions existing
     */
    public static List<String> getPermissions(){
        return DotPlugin.perms;
    }

    /**
     * Make a permission
     *
     * @param name The name of the permission
     * @return Permissions create successfully ? (Permission already exist)
     */
    public static boolean createPermission(String name){
        if (!DotPlugin.perms.contains(name)){
            DotPlugin.perms.add(name);
            return true;
        }
        return false;
    }

}
