package fr.dotmazy.dotplugin.util;

import fr.dotmazy.dotplugin.old.api.PermApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Rank {

    private String name;
    private List<String> permissions;
    private String prefix;
    private String suffix;

    public Rank(String name, String prefix, String suffix, List<String> permissions){
        this.name = name;
        this.permissions = permissions;
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public Rank(String name, String prefix, String suffix, String... permissions){
        this.name = name;
        this.permissions = PermApi.makePermissions(permissions);
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(String... permissions) {
        this.permissions = new ArrayList<>(Arrays.asList(permissions));
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
