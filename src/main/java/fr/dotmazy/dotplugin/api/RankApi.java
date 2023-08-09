package fr.dotmazy.dotplugin.api;

import fr.dotmazy.dotplugin.DotPlugin;
import fr.dotmazy.dotplugin.util.Rank;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class RankApi {

    /**
     * Make a rank
     * <p></p>
     * Example:
     * <pre>
     * {@code
     * if(RankApi.makeRank("example")){
     *     System.out.println("Rank successfully created");
     * }else{
     *     System.out.println("This rank already exist");
     * }
     * }
     * </pre>
     *
     * @param name Name of the rank
     * @return Rank successfully created ? (Rank already existing)
     */
    public static boolean makeRank(String name){
        if(DotPlugin.files.get("ranks").get().get(name) == null) {
            DotPlugin.files.get("ranks").get().set(name + ".prefix", "");
            DotPlugin.files.get("ranks").get().set(name + ".suffix", "");
            DotPlugin.files.get("ranks").get().set(name + ".permissions", new ArrayList<String>());
            return true;
        }
        return false;
    }

    /**
     * Make a rank
     * <p></p>
     * Example:
     * <pre>
     * {@code
     * if(RankApi.makeRank("example", "example2", "example3")){
     *     System.out.println("Rank successfully created");
     * }else{
     *     System.out.println("This rank already exist");
     * }
     * }
     * </pre>
     *
     * @param name Name of the rank
     * @return Rank successfully created ? (Rank already existing)
     */
    public static boolean makeRank(String name, String... permissions){
        if(DotPlugin.files.get("ranks").get().get(name) == null) {
            DotPlugin.files.get("ranks").get().set(name + ".prefix", "");
            DotPlugin.files.get("ranks").get().set(name + ".suffix", "");
            DotPlugin.files.get("ranks").get().set(name + ".permissions", permissions);
            return true;
        }
        return false;
    }

    /**
     * Make a rank
     * <p></p>
     * Example:
     * <pre>
     * {@code
     * if(RankApi.makeRank("example", "prefix", "suffix", "example2", "example3")){
     *     System.out.println("Rank successfully created");
     * }else{
     *     System.out.println("This rank already exist");
     * }
     * }
     * </pre>
     *
     * @param name Name of the rank
     * @return Rank successfully created ? (Rank already existing)
     */
    public static boolean makeRank(String name, String prefix, String suffix, String... permissions){
        if(DotPlugin.files.get("ranks").get().get(name) == null){
            DotPlugin.files.get("ranks").get().set(name+".prefix",prefix);
            DotPlugin.files.get("ranks").get().set(name+".suffix",suffix);
            DotPlugin.files.get("ranks").get().set(name+".permissions",permissions);
            return true;
        }
        return false;
    }

    /**
     * Make a rank
     * <p></p>
     * Example:
     * <pre>
     * {@code
     * if(RankApi.makeRank("example", "prefix", "suffix", list)){
     *     System.out.println("Rank successfully created");
     * }else{
     *     System.out.println("This rank already exist");
     * }
     * }
     * </pre>
     *
     * @param name Name of the rank
     * @return Rank successfully created ? (Rank already existing)
     */
    public static boolean makeRank(String name, String prefix, String suffix, List<String> permissions){
        if(DotPlugin.files.get("ranks").get().get(name) == null){
            DotPlugin.files.get("ranks").get().set(name+".prefix",prefix);
            DotPlugin.files.get("ranks").get().set(name+".suffix",suffix);
            DotPlugin.files.get("ranks").get().set(name+".permissions",permissions);
            return true;
        }
        return false;
    }

    /**
     * Make a rank
     * <p></p>
     * Example:
     * <pre>
     * {@code
     * if(RankApi.removeRank(RankApi.getRank("example"))){
     *     System.out.println("Rank successfully removed");
     * }else{
     *     System.out.println("This rank doesn't exist");
     * }
     * }
     * </pre>
     *
     * @param rank Name of the rank
     * @return Rank successfully created ? (Rank already existing)
     */
    public static boolean removeRank(Rank rank){
        if(rank != null && DotPlugin.files.get("ranks").get().get(rank.getName()) != null){
            DotPlugin.files.get("ranks").get().set(rank.getName(), null);
            return true;
        }
        return false;
    }

    /**
     * Make a rank
     * <p></p>
     * Example:
     * <pre>
     * {@code
     * if(RankApi.setPrefix(RankApi.getRank("example"),"example")){
     *     System.out.println("Rank prefix successfully set");
     * }else{
     *     System.out.println("This rank doesn't exist");
     * }
     * }
     * </pre>
     *
     * @param rank Name of the rank
     * @return Rank successfully created ? (Rank already existing)
     */
    public static boolean setPrefix(Rank rank, String prefix){
        if (rank != null && DotPlugin.files.get("ranks").get().get(rank.getName())!=null){
            DotPlugin.files.get("ranks").get().set(rank.getName()+".prefix",prefix);
            return true;
        }
        return false;
    }

    /**
     * Make a rank
     * <p></p>
     * Example:
     * <pre>
     * {@code
     * if(RankApi.setSuffix(RankApi.getRank("example"),"example")){
     *     System.out.println("Rank suffix successfully set");
     * }else{
     *     System.out.println("This rank doesn't exist");
     * }
     * }
     * </pre>
     *
     * @param rank Name of the rank
     * @return Rank successfully created ? (Rank already existing)
     */
    public static boolean setSuffix(Rank rank, String suffix){
        if (rank != null && DotPlugin.files.get("ranks").get().get(rank.getName())!=null){
            DotPlugin.files.get("ranks").get().set(rank.getName()+".suffix",suffix);
            return true;
        }
        return false;
    }

    /**
     * Make a rank
     * <p></p>
     * Example:
     * <pre>
     * {@code
     * if(RankApi.setName(RankApi.getRank("example"),"example2")){
     *     System.out.println("Rank name successfully set");
     * }else{
     *     System.out.println("This rank doesn't exist");
     * }
     * }
     * </pre>
     *
     * @param rank Name of the rank
     * @return Rank successfully created ? (Rank already existing)
     */
    public static boolean setName(Rank rank, String name){
        if (rank != null && DotPlugin.files.get("ranks").get().get(rank.getName())!=null && DotPlugin.files.get("rank").get().get(name)==null){
            DotPlugin.files.get("ranks").get().set(name+".prefix",DotPlugin.files.get("rank").get().getString(rank.getName()+".prefix"));
            DotPlugin.files.get("ranks").get().set(name+".suffix",DotPlugin.files.get("rank").get().getString(rank.getName()+".suffix"));
            DotPlugin.files.get("ranks").get().set(name+".permissions",DotPlugin.files.get("rank").get().getStringList(rank.getName()+".permissions"));
            DotPlugin.files.get("ranks").get().set(rank.getName(),null);
            return true;
        }
        return false;
    }

    /**
     * Make a rank
     * <p></p>
     * Example:
     * <pre>
     * {@code
     * if(RankApi.setPerms(RankApi.getRank("example"),"example","example")){
     *     System.out.println("Rank perms successfully set");
     * }else{
     *     System.out.println("This rank doesn't exist");
     * }
     * }
     * </pre>
     *
     * @param rank Name of the rank
     * @return Rank successfully created ? (Rank already existing)
     */
    public static boolean setPerms(Rank rank, String... perms){
        if (rank != null && DotPlugin.files.get("ranks").get().get(rank.getName())!=null){
            DotPlugin.files.get("ranks").get().set(rank.getName()+".permissions",perms);
            return true;
        }
        return false;
    }

    /**
     * Make a rank
     * <p></p>
     * Example:
     * <pre>
     * {@code
     * if(RankApi.makeRank("example")){
     *     System.out.println("Rank successfully created");
     * }else{
     *     System.out.println("This rank already exist");
     * }
     * }
     * </pre>
     *
     * @param rank Name of the rank
     * @return Rank successfully created ? (Rank already existing)
     */
    public static boolean setPerms(Rank rank, List<String> perms){
        if (rank != null && DotPlugin.files.get("ranks").get().get(rank.getName())!=null){
            DotPlugin.files.get("ranks").get().set(rank.getName()+".permissions",perms);
            return true;
        }
        return false;
    }

    /**
     * Make a rank
     * <p></p>
     * Example:
     * <pre>
     * {@code
     * if(RankApi.makeRank("example")){
     *     System.out.println("Rank successfully created");
     * }else{
     *     System.out.println("This rank already exist");
     * }
     * }
     * </pre>
     *
     * @param rank Name of the rank
     * @return Rank successfully created ? (Rank already existing)
     */
    public static boolean addPerm(Rank rank, String perm){
        if (rank != null && DotPlugin.files.get("ranks").get().get(rank.getName())!=null){
            List<String> perms = DotPlugin.files.get("ranks").get().getStringList(rank.getName()+".permissions");
            if(perms.contains(perm)) {
                return false;
            }
            perms.add(perm);
            DotPlugin.files.get("ranks").get().set(rank.getName()+".permissions",perms);
            return true;
        }
        return false;
    }

    /**
     * Make a rank
     * <p></p>
     * Example:
     * <pre>
     * {@code
     * if(RankApi.makeRank("example")){
     *     System.out.println("Rank successfully created");
     * }else{
     *     System.out.println("This rank already exist");
     * }
     * }
     * </pre>
     *
     * @param rank Name of the rank
     * @return Rank successfully created ? (Rank already existing)
     */
    public static boolean removePerm(Rank rank, String perm){
        if (rank != null && DotPlugin.files.get("ranks").get().get(rank.getName())!=null){
            List<String> perms = DotPlugin.files.get("ranks").get().getStringList(rank.getName()+".permissions");
            if(!perms.contains(perm)){
                return false;
            }
            perms.remove(perm);
            DotPlugin.files.get("ranks").get().set(rank.getName()+".permissions",perms);
            return true;
        }
        return false;
    }

    /**
     * Make a rank
     * <p></p>
     * Example:
     * <pre>
     * {@code
     * if(RankApi.makeRank("example")){
     *     System.out.println("Rank successfully created");
     * }else{
     *     System.out.println("This rank already exist");
     * }
     * }
     * </pre>
     *
     * @param name Name of the rank
     * @return Rank successfully created ? (Rank already existing)
     */
    public static Rank getRank(String name){
        if(DotPlugin.files.get("ranks").get().get(name) != null) return new Rank(name, DotPlugin.files.get("ranks").get().getString(name+".prefix"), DotPlugin.files.get("ranks").get().getString(name+"suffix"), DotPlugin.files.get("ranks").get().getStringList(name+"permissions"));
        return null;
    }

    /**
     * Make a rank
     * <p></p>
     * Example:
     * <pre>
     * {@code
     * if(RankApi.makeRank("example")){
     *     System.out.println("Rank successfully created");
     * }else{
     *     System.out.println("This rank already exist");
     * }
     * }
     * </pre>
     *
     * @return Rank successfully created ? (Rank already existing)
     */
    public static List<Rank> getRanks(){
        List<Rank> list = new ArrayList<Rank>();
        System.out.println(DotPlugin.files);
        for (String rankName : DotPlugin.files.get("ranks").get().getKeys(false)){
            String prefix = DotPlugin.files.get("ranks").get().getString(rankName+".prefix");
            String suffix = DotPlugin.files.get("ranks").get().getString(rankName+".suffix");
            List<String> permissions = DotPlugin.files.get("ranks").get().getStringList(rankName+".permissions");
            Rank rank = new Rank(rankName,prefix,suffix,permissions);
            list.add(rank);
        }
        return list;
    }

}
