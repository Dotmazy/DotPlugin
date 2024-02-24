package fr.dotmazy.dotplugin.discord;

import fr.dotmazy.dotplugin.util.Database;
import org.javacord.api.entity.user.User;

public class Lang {

    // String to java unicode: https://itpro.cz/juniconv/
    public static String traduct(String path, User user){
        String lang = Database.executeStringQuery("SELECT lang FROM members WHERE id='"+user.getId()+"'");
        if(lang.equals("frensh")){
            return path
                    .replace("no-permission-remove-country","Vous n'\u00EAtes pas autoris\u00E9 \u00E0 supprimer ce pays.")
                    .replace("invalid-country","Pays invalide.")
                    .replace("success-delete-country","Le pays `{name}` a \u00E9t\u00E9 supprim\u00E9 avec succ\u00E8s.")
                    .replace("no-permission-add-player","Vous n'\u00EAtes pas autoris\u00E9 \u00E0 ajouter des joueurs dans ce pays.")
                    .replace("member-already-on-country","{member} est d\u00E9j\u00E0 dans ce pays.")
                    .replace("member-already-on-a-country","{member} est d\u00E9j\u00E0 dans un pays, il a d'abord d\u00FB quitter son pays.")
                    .replace("user-send-request","{user} vous a envoy\u00E9 une demande pour rejoindre son pays (`{country}`).")
                    .replace("request-send","Une demande d'adh\u00E9sion \u00E0 votre pays a \u00E9t\u00E9 envoy\u00E9e \u00E0 {member}")
                    .replace("no-permission-remove-players","Vous n'\u00EAtes pas autoris\u00E9 \u00E0 supprimer des joueurs de ce pays.")
                    .replace("member-not-on-country","{member} n'est pas dans ce pays.")
                    .replace("member-no-longer-in-country","{member} n'est plus dans ce pays.")
                    .replace("member-already-admin","{member} est d\u00E9j\u00E0 administrateur de ce pays.")
                    .replace("member-already-not-admin","{member} n'est d\u00E9j\u00E0 pas administrateur dans ce pays.")
                    .replace("member-admin","{member} est d\u00E9sormais administrateur de ce pays.")
                    .replace("member-not-admin","{member} n'est plus administrateur de ce pays.")
                    .replace("invalid-hex","Couleur hexad\u00E9cimale invalide.");
        }
        if(lang.equals("english")){
            return path
                    .replace("no-permission-remove-country","You don't have the permission to remove this country.")
                    .replace("invalid-country","Invalid country.")
                    .replace("success-delete-country","Successfully deleted the country `{name}`.")
                    .replace("no-permission-add-player","You don't have permission to add players to this country.")
                    .replace("member-already-on-country","{member} is already on this country.")
                    .replace("member-already-on-a-country","{member} is already in a country, he first had to leave his country.")
                    .replace("user-send-request","{user} have send you a request to join his country (`{country}`).")
                    .replace("request-send","A request to join your country have been sent to {member}")
                    .replace("no-permission-remove-players","You don't have permission to remove players from this country.")
                    .replace("member-not-on-country","{member} is not in this country.")
                    .replace("member-no-longer-in-country","{member} is no longer in this country.")
                    .replace("member-already-admin","{member} is already admin on this country.")
                    .replace("member-already-not-admin","{member} is already not admin on this country.")
                    .replace("member-admin","{member} is now admin on this country.")
                    .replace("member-not-admin","{member} is no longer an admin on this country.")
                    .replace("invalid-hex","Invalid hex color.");
        }
        if(lang.equals("spanish")){
            return path
                    .replace("no-permission-remove-country","No tienes permiso para eliminar este pa\u00EDs.")
                    .replace("invalid-country","Pa\u00EDs inv\u00E1lido.")
                    .replace("success-delete-country","Elimin\u00F3 con \u00E9xito el pa\u00EDs `{name}`.")
                    .replace("no-permission-add-player","No tienes permiso para agregar jugadores a este pa\u00EDs.")
                    .replace("member-already-on-country","{member} ya est\u00E1 en este pa\u00EDs.")
                    .replace("member-already-on-a-country","{member} ya est\u00E1 en un pa\u00EDs, primero tuvo que salir de su pa\u00EDs.")
                    .replace("user-send-request","{user} te ha enviado una solicitud para unirte a su pa\u00EDs (`{country}`).")
                    .replace("request-send","Se envi\u00F3 una solicitud para unirse a su pa\u00EDs a {member}")
                    .replace("no-permission-remove-players","No tienes permiso para eliminar jugadores de este pa\u00EDs.")
                    .replace("member-not-on-country","{member} no est\u00E1 en este pa\u00EDs.")
                    .replace("member-no-longer-in-country","{member} ya no se encuentra en este pa\u00EDs.")
                    .replace("member-already-admin","{member} ya es administrador en este pa\u00EDs.")
                    .replace("member-already-not-admin","{member} ya no es administrador en este pa\u00EDs.")
                    .replace("member-admin","{member} ahora es administrador en este pa\u00EDs.")
                    .replace("member-not-admin","{member} ya no es administrador en este pa\u00EDs.")
                    .replace("invalid-hex","Color hexadecimal no v\u00E1lido.");
        }
        if(lang.equals("chinese")){
            return path
                    .replace("no-permission-remove-country","\u60A8\u65E0\u6743\u5220\u9664\u8BE5\u56FD\u5BB6/\u5730\u533A\u3002")
                    .replace("invalid-country","\u65E0\u6548\u56FD\u5BB6\u3002")
                    .replace("success-delete-country","\u5DF2\u6210\u529F\u5220\u9664\u56FD\u5BB6/\u5730\u533A`{name}`\u3002")
                    .replace("no-permission-add-player","\u60A8\u65E0\u6743\u5411\u8BE5\u56FD\u5BB6/\u5730\u533A\u6DFB\u52A0\u73A9\u5BB6\u3002")
                    .replace("member-already-on-country","{member} \u5DF2\u5728\u8BE5\u56FD\u5BB6/\u5730\u533A\u3002")
                    .replace("member-already-on-a-country","{member} \u5DF2\u7ECF\u5728\u67D0\u4E2A\u56FD\u5BB6/\u5730\u533A\uFF0C\u4ED6\u9996\u5148\u5FC5\u987B\u79BB\u5F00\u81EA\u5DF1\u7684\u56FD\u5BB6/\u5730\u533A\u3002")
                    .replace("user-send-request","{user} \u5411\u60A8\u53D1\u9001\u4E86\u52A0\u5165\u4ED6\u7684\u56FD\u5BB6/\u5730\u533A (`{country}`) \u7684\u8BF7\u6C42\u3002")
                    .replace("request-send","\u52A0\u5165\u60A8\u6240\u5728\u56FD\u5BB6/\u5730\u533A\u7684\u8BF7\u6C42\u5DF2\u53D1\u9001\u81F3 {member}")
                    .replace("no-permission-remove-players","\u60A8\u65E0\u6743\u4ECE\u8BE5\u56FD\u5BB6/\u5730\u533A\u5220\u9664\u73A9\u5BB6\u3002")
                    .replace("member-not-on-country","{member} \u4E0D\u5728\u8FD9\u4E2A\u56FD\u5BB6/\u5730\u533A\u3002")
                    .replace("member-no-longer-in-country","{member} \u5DF2\u4E0D\u5728\u8FD9\u4E2A\u56FD\u5BB6/\u5730\u533A\u3002")
                    .replace("member-already-admin","{member} \u5DF2\u662F\u8BE5\u56FD\u5BB6/\u5730\u533A\u7684\u7BA1\u7406\u5458\u3002")
                    .replace("member-already-not-admin","{member} \u5DF2\u7ECF\u4E0D\u662F\u8BE5\u56FD\u5BB6/\u5730\u533A\u7684\u7BA1\u7406\u5458\u3002")
                    .replace("member-admin","{member} \u73B0\u5728\u662F\u8BE5\u56FD\u5BB6/\u5730\u533A\u7684\u7BA1\u7406\u5458\u3002")
                    .replace("member-not-admin","{member} \u4E0D\u518D\u662F\u8BE5\u56FD\u5BB6/\u5730\u533A\u7684\u7BA1\u7406\u5458\u3002")
                    .replace("invalid-hex","\u65E0\u6548\u7684\u5341\u516D\u8FDB\u5236\u989C\u8272\u3002");
        }
        if(lang.equals("japanese")){
            return path
                    .replace("no-permission-remove-country","\u3053\u306E\u56FD\u3092\u524A\u9664\u3059\u308B\u6A29\u9650\u304C\u3042\u308A\u307E\u305B\u3093\u3002")
                    .replace("invalid-country","\u7121\u52B9\u306A\u56FD\u3067\u3059\u3002")
                    .replace("success-delete-country","\u56FD `{name}` \u304C\u6B63\u5E38\u306B\u524A\u9664\u3055\u308C\u307E\u3057\u305F\u3002")
                    .replace("no-permission-add-player","\u3053\u306E\u56FD\u306B\u30D7\u30EC\u30FC\u30E4\u30FC\u3092\u8FFD\u52A0\u3059\u308B\u6A29\u9650\u304C\u3042\u308A\u307E\u305B\u3093\u3002")
                    .replace("member-already-on-country","{member} \u306F\u3059\u3067\u306B\u3053\u306E\u56FD\u306B\u3044\u307E\u3059\u3002")
                    .replace("member-already-on-a-country","{member} \u306F\u3059\u3067\u306B\u3042\u308B\u56FD\u306B\u3044\u308B\u305F\u3081\u3001\u307E\u305A\u56FD\u3092\u96E2\u308C\u308B\u5FC5\u8981\u304C\u3042\u308A\u307E\u3057\u305F\u3002")
                    .replace("user-send-request","{user} \u304C\u3042\u306A\u305F\u306B\u5F7C\u306E\u56FD (`{country}`) \u3078\u306E\u53C2\u52A0\u30EA\u30AF\u30A8\u30B9\u30C8\u3092\u9001\u4FE1\u3057\u307E\u3057\u305F\u3002")
                    .replace("request-send","\u3042\u306A\u305F\u306E\u56FD\u3078\u306E\u53C2\u52A0\u30EA\u30AF\u30A8\u30B9\u30C8\u304C {member} \u306B\u9001\u4FE1\u3055\u308C\u307E\u3057\u305F")
                    .replace("no-permission-remove-players","\u3053\u306E\u56FD\u304B\u3089\u30D7\u30EC\u30A4\u30E4\u30FC\u3092\u524A\u9664\u3059\u308B\u6A29\u9650\u304C\u3042\u308A\u307E\u305B\u3093\u3002")
                    .replace("member-not-on-country","{member} \u306F\u3053\u306E\u56FD\u306B\u3044\u307E\u305B\u3093\u3002")
                    .replace("member-no-longer-in-country","{member} \u306F\u3082\u3046\u3053\u306E\u56FD\u306B\u3044\u307E\u305B\u3093\u3002")
                    .replace("member-already-admin","{member} \u306F\u3059\u3067\u306B\u3053\u306E\u56FD\u306E\u7BA1\u7406\u8005\u3067\u3059\u3002")
                    .replace("member-already-not-admin","{member} \u306F\u3059\u3067\u306B\u3053\u306E\u56FD\u306E\u7BA1\u7406\u8005\u3067\u306F\u3042\u308A\u307E\u305B\u3093\u3002")
                    .replace("member-admin","{member} \u306F\u73FE\u5728\u3053\u306E\u56FD\u306E\u7BA1\u7406\u8005\u3067\u3059\u3002")
                    .replace("member-not-admin","{member} \u306F\u3082\u3046\u3053\u306E\u56FD\u306E\u7BA1\u7406\u8005\u3067\u306F\u3042\u308A\u307E\u305B\u3093\u3002")
                    .replace("invalid-hex","\u7121\u52B9\u306A 16 \u9032\u6570\u306E\u8272\u3067\u3059\u3002");
        }
        if(lang.equals("german")){
            return path
                    .replace("no-permission-remove-country","Sie verf\u00FCgen nicht \u00FCber die Berechtigung, dieses Land zu entfernen.")
                    .replace("invalid-country","Ung\u00FCltiges Land.")
                    .replace("success-delete-country","Das Land `{name}` wurde erfolgreich gel\u00F6scht.")
                    .replace("no-permission-add-player","Sie sind nicht berechtigt, diesem Land Spieler hinzuzuf\u00FCgen.")
                    .replace("member-already-on-country","{member} ist bereits in diesem Land.")
                    .replace("member-already-on-a-country","{member} befindet sich bereits in einem Land, er musste sein Land zun\u00E4chst verlassen.")
                    .replace("user-send-request","{user} hat Ihnen eine Anfrage zum Beitritt zu seinem Land (`{country}`) gesendet.")
                    .replace("request-send","Eine Beitrittsanfrage f\u00FCr Ihr Land wurde an {member} gesendet.")
                    .replace("no-permission-remove-players","Sie sind nicht berechtigt, Spieler aus diesem Land zu entfernen.")
                    .replace("member-not-on-country","{member} ist nicht in diesem Land.")
                    .replace("member-no-longer-in-country","{member} ist nicht mehr in diesem Land.")
                    .replace("member-already-admin","{member} ist bereits Administrator f\u00FCr dieses Land.")
                    .replace("member-already-not-admin","{member} ist in diesem Land bereits kein Administrator mehr.")
                    .replace("member-admin","{member} ist jetzt Administrator f\u00FCr dieses Land.")
                    .replace("member-not-admin","{member} ist kein Administrator mehr f\u00FCr dieses Land.")
                    .replace("invalid-hex","Ung\u00FCltige Hex-Farbe.");
        }
        if(lang.equals("esperanto")){
            return path
                    .replace("no-permission-remove-country","Vi ne havas la permeson forigi \u0109i tiun landon.")
                    .replace("invalid-country","Nevalida lando.")
                    .replace("success-delete-country","Sukcese forigis la landon `{name}`.")
                    .replace("no-permission-add-player","Vi ne havas permeson aldoni ludantojn al \u0109i tiu lando.")
                    .replace("member-already-on-country","{member} jam estas en \u0109i tiu lando.")
                    .replace("member-already-on-a-country","{member} jam estas en lando, li unue devis forlasi sian landon.")
                    .replace("user-send-request","{user} sendis al vi peton por ali\u011Di al sia lando (`{country}`).")
                    .replace("request-send","Peto por ali\u011Di al via lando estis sendita al {member}")
                    .replace("no-permission-remove-players","Vi ne havas permeson forigi ludantojn el \u0109i tiu lando.")
                    .replace("member-not-on-country","{member} ne estas en \u0109i tiu lando.")
                    .replace("member-no-longer-in-country","{member} ne plu estas en \u0109i tiu lando.")
                    .replace("member-already-admin","{member} jam estas administranto en \u0109i tiu lando.")
                    .replace("member-already-not-admin","{member} jam ne estas administranto en \u0109i tiu lando.")
                    .replace("member-admin","{member} nun estas administranto en \u0109i tiu lando.")
                    .replace("member-not-admin","{member} ne plu estas administranto de \u0109i tiu lando.")
                    .replace("invalid-hex","Nevalida hekskoloro.");
        }
        if(lang.equals("greek")){
            return path
                    .replace("no-permission-remove-country","\u0394\u03B5\u03BD \u03AD\u03C7\u03B5\u03C4\u03B5 \u03C4\u03B7\u03BD \u03AC\u03B4\u03B5\u03B9\u03B1 \u03BD\u03B1 \u03BA\u03B1\u03C4\u03B1\u03C1\u03B3\u03AE\u03C3\u03B5\u03C4\u03B5 \u03B1\u03C5\u03C4\u03AE\u03BD \u03C4\u03B7 \u03C7\u03CE\u03C1\u03B1.")
                    .replace("invalid-country","\u039C\u03B7 \u03AD\u03B3\u03BA\u03C5\u03C1\u03B7 \u03C7\u03CE\u03C1\u03B1.")
                    .replace("success-delete-country","\u0397 \u03C7\u03CE\u03C1\u03B1 `{name}` \u03B4\u03B9\u03B1\u03B3\u03C1\u03AC\u03C6\u03B7\u03BA\u03B5 \u03BC\u03B5 \u03B5\u03C0\u03B9\u03C4\u03C5\u03C7\u03AF\u03B1.")
                    .replace("no-permission-add-player","\u0394\u03B5\u03BD \u03AD\u03C7\u03B5\u03C4\u03B5 \u03AC\u03B4\u03B5\u03B9\u03B1 \u03BD\u03B1 \u03C0\u03C1\u03BF\u03C3\u03B8\u03AD\u03C3\u03B5\u03C4\u03B5 \u03C0\u03B1\u03AF\u03BA\u03C4\u03B5\u03C2 \u03C3\u03B5 \u03B1\u03C5\u03C4\u03AE\u03BD \u03C4\u03B7 \u03C7\u03CE\u03C1\u03B1.")
                    .replace("member-already-on-country","\u039F \u03C7\u03C1\u03AE\u03C3\u03C4\u03B7\u03C2 {member} \u03B2\u03C1\u03AF\u03C3\u03BA\u03B5\u03C4\u03B1\u03B9 \u03AE\u03B4\u03B7 \u03C3\u03B5 \u03B1\u03C5\u03C4\u03AE\u03BD \u03C4\u03B7 \u03C7\u03CE\u03C1\u03B1.")
                    .replace("member-already-on-a-country","\u039F {member} \u03B2\u03C1\u03AF\u03C3\u03BA\u03B5\u03C4\u03B1\u03B9 \u03AE\u03B4\u03B7 \u03C3\u03B5 \u03BC\u03B9\u03B1 \u03C7\u03CE\u03C1\u03B1, \u03AD\u03C0\u03C1\u03B5\u03C0\u03B5 \u03C0\u03C1\u03CE\u03C4\u03B1 \u03BD\u03B1 \u03C6\u03CD\u03B3\u03B5\u03B9 \u03B1\u03C0\u03CC \u03C4\u03B7 \u03C7\u03CE\u03C1\u03B1 \u03C4\u03BF\u03C5.")
                    .replace("user-send-request","\u039F \u03C7\u03C1\u03AE\u03C3\u03C4\u03B7\u03C2 {user} \u03C3\u03AC\u03C2 \u03AD\u03C7\u03B5\u03B9 \u03C3\u03C4\u03B5\u03AF\u03BB\u03B5\u03B9 \u03AD\u03BD\u03B1 \u03B1\u03AF\u03C4\u03B7\u03BC\u03B1 \u03C3\u03C5\u03BC\u03BC\u03B5\u03C4\u03BF\u03C7\u03AE\u03C2 \u03C3\u03C4\u03B7 \u03C7\u03CE\u03C1\u03B1 \u03C4\u03BF\u03C5 (`{country}`).")
                    .replace("request-send","\u0388\u03BD\u03B1 \u03B1\u03AF\u03C4\u03B7\u03BC\u03B1 \u03B3\u03B9\u03B1 \u03AD\u03BD\u03C4\u03B1\u03BE\u03B7 \u03C3\u03C4\u03B7 \u03C7\u03CE\u03C1\u03B1 \u03C3\u03B1\u03C2 \u03AD\u03C7\u03B5\u03B9 \u03C3\u03C4\u03B1\u03BB\u03B5\u03AF \u03C3\u03C4\u03BF {member}")
                    .replace("no-permission-remove-players","\u0394\u03B5\u03BD \u03AD\u03C7\u03B5\u03C4\u03B5 \u03AC\u03B4\u03B5\u03B9\u03B1 \u03BD\u03B1 \u03B1\u03C6\u03B1\u03B9\u03C1\u03AD\u03C3\u03B5\u03C4\u03B5 \u03C0\u03B1\u03AF\u03BA\u03C4\u03B5\u03C2 \u03B1\u03C0\u03CC \u03B1\u03C5\u03C4\u03AE\u03BD \u03C4\u03B7 \u03C7\u03CE\u03C1\u03B1.")
                    .replace("member-not-on-country","\u039F \u03C7\u03C1\u03AE\u03C3\u03C4\u03B7\u03C2 {member} \u03B4\u03B5\u03BD \u03B2\u03C1\u03AF\u03C3\u03BA\u03B5\u03C4\u03B1\u03B9 \u03C3\u03B5 \u03B1\u03C5\u03C4\u03AE\u03BD \u03C4\u03B7 \u03C7\u03CE\u03C1\u03B1.")
                    .replace("member-no-longer-in-country","\u039F \u03C7\u03C1\u03AE\u03C3\u03C4\u03B7\u03C2 {member} \u03B4\u03B5\u03BD \u03B2\u03C1\u03AF\u03C3\u03BA\u03B5\u03C4\u03B1\u03B9 \u03C0\u03BB\u03AD\u03BF\u03BD \u03C3\u03B5 \u03B1\u03C5\u03C4\u03AE\u03BD \u03C4\u03B7 \u03C7\u03CE\u03C1\u03B1.")
                    .replace("member-already-admin","\u039F \u03C7\u03C1\u03AE\u03C3\u03C4\u03B7\u03C2 {member} \u03B5\u03AF\u03BD\u03B1\u03B9 \u03AE\u03B4\u03B7 \u03B4\u03B9\u03B1\u03C7\u03B5\u03B9\u03C1\u03B9\u03C3\u03C4\u03AE\u03C2 \u03C3\u03B5 \u03B1\u03C5\u03C4\u03AE\u03BD \u03C4\u03B7 \u03C7\u03CE\u03C1\u03B1.")
                    .replace("member-already-not-admin","\u039F \u03C7\u03C1\u03AE\u03C3\u03C4\u03B7\u03C2 {member} \u03B4\u03B5\u03BD \u03B5\u03AF\u03BD\u03B1\u03B9 \u03AE\u03B4\u03B7 \u03B4\u03B9\u03B1\u03C7\u03B5\u03B9\u03C1\u03B9\u03C3\u03C4\u03AE\u03C2 \u03C3\u03B5 \u03B1\u03C5\u03C4\u03AE\u03BD \u03C4\u03B7 \u03C7\u03CE\u03C1\u03B1.")
                    .replace("member-admin","\u039F \u03C7\u03C1\u03AE\u03C3\u03C4\u03B7\u03C2 {member} \u03B5\u03AF\u03BD\u03B1\u03B9 \u03C0\u03BB\u03AD\u03BF\u03BD \u03B4\u03B9\u03B1\u03C7\u03B5\u03B9\u03C1\u03B9\u03C3\u03C4\u03AE\u03C2 \u03C3\u03B5 \u03B1\u03C5\u03C4\u03AE\u03BD \u03C4\u03B7 \u03C7\u03CE\u03C1\u03B1.")
                    .replace("member-not-admin","\u039F \u03C7\u03C1\u03AE\u03C3\u03C4\u03B7\u03C2 {member} \u03B4\u03B5\u03BD \u03B5\u03AF\u03BD\u03B1\u03B9 \u03C0\u03BB\u03AD\u03BF\u03BD \u03B4\u03B9\u03B1\u03C7\u03B5\u03B9\u03C1\u03B9\u03C3\u03C4\u03AE\u03C2 \u03C3\u03B5 \u03B1\u03C5\u03C4\u03AE\u03BD \u03C4\u03B7 \u03C7\u03CE\u03C1\u03B1.")
                    .replace("invalid-hex","\u039C\u03B7 \u03AD\u03B3\u03BA\u03C5\u03C1\u03BF \u03B5\u03BE\u03AC\u03B3\u03C9\u03BD\u03BF \u03C7\u03C1\u03CE\u03BC\u03B1.");
        }
        if(lang.equals("arab")){
            return path
                    .replace("no-permission-remove-country","\u0644\u064A\u0633 \u0644\u062F\u064A\u0643 \u0627\u0644\u0625\u0630\u0646 \u0628\u0625\u0632\u0627\u0644\u0629 \u0647\u0630\u0627 \u0627\u0644\u0628\u0644\u062F.")
                    .replace("invalid-country","\u0628\u0644\u062F \u063A\u064A\u0631 \u0635\u0627\u0644\u062D.")
                    .replace("success-delete-country","\u062A\u0645 \u062D\u0630\u0641 \u0627\u0644\u0628\u0644\u062F {member} \u0628\u0646\u062C\u0627\u062D.")
                    .replace("no-permission-add-player","\u0644\u064A\u0633 \u0644\u062F\u064A\u0643 \u0625\u0630\u0646 \u0628\u0625\u0636\u0627\u0641\u0629 \u0644\u0627\u0639\u0628\u064A\u0646 \u0625\u0644\u0649 \u0647\u0630\u0627 \u0627\u0644\u0628\u0644\u062F.")
                    .replace("member-already-on-country","{member} \u0645\u0648\u062C\u0648\u062F \u0628\u0627\u0644\u0641\u0639\u0644 \u0641\u064A \u0647\u0630\u0627 \u0627\u0644\u0628\u0644\u062F.")
                    .replace("member-already-on-a-country","{member} \u0645\u0648\u062C\u0648\u062F \u0628\u0627\u0644\u0641\u0639\u0644 \u0641\u064A \u0628\u0644\u062F \u0645\u0627\u060C \u0648\u0643\u0627\u0646 \u0639\u0644\u064A\u0647 \u0623\u0648\u0644\u0627\u064B \u0645\u063A\u0627\u062F\u0631\u0629 \u0628\u0644\u062F\u0647.")
                    .replace("user-send-request","\u0644\u0642\u062F \u0623\u0631\u0633\u0644 \u0644\u0643 {user} \u0637\u0644\u0628\u064B\u0627 \u0644\u0644\u0627\u0646\u0636\u0645\u0627\u0645 \u0625\u0644\u0649 \u0628\u0644\u062F\u0647 (`{country}`).")
                    .replace("request-send","\u062A\u0645 \u0625\u0631\u0633\u0627\u0644 \u0637\u0644\u0628 \u0644\u0644\u0627\u0646\u0636\u0645\u0627\u0645 \u0625\u0644\u0649 \u0628\u0644\u062F\u0643 \u0625\u0644\u0649 {member}")
                    .replace("no-permission-remove-players","\u0644\u064A\u0633 \u0644\u062F\u064A\u0643 \u0625\u0630\u0646 \u0628\u0625\u0632\u0627\u0644\u0629 \u0644\u0627\u0639\u0628\u064A\u0646 \u0645\u0646 \u0647\u0630\u0627 \u0627\u0644\u0628\u0644\u062F.")
                    .replace("member-not-on-country","{member} \u0644\u064A\u0633 \u0641\u064A \u0647\u0630\u0627 \u0627\u0644\u0628\u0644\u062F.")
                    .replace("member-no-longer-in-country","{member} \u0644\u0645 \u064A\u0639\u062F \u0645\u0648\u062C\u0648\u062F\u064B\u0627 \u0641\u064A \u0647\u0630\u0627 \u0627\u0644\u0628\u0644\u062F.")
                    .replace("member-already-admin","{member} \u0647\u0648 \u0627\u0644\u0645\u0633\u0624\u0648\u0644 \u0628\u0627\u0644\u0641\u0639\u0644 \u0641\u064A \u0647\u0630\u0627 \u0627\u0644\u0628\u0644\u062F.")
                    .replace("member-already-not-admin","{member} \u0644\u064A\u0633 \u0645\u0633\u0624\u0648\u0644\u064B\u0627 \u0628\u0627\u0644\u0641\u0639\u0644 \u0641\u064A \u0647\u0630\u0627 \u0627\u0644\u0628\u0644\u062F.")
                    .replace("member-admin","{member} \u0647\u0648 \u0627\u0644\u0622\u0646 \u0645\u0633\u0624\u0648\u0644 \u0641\u064A \u0647\u0630\u0627 \u0627\u0644\u0628\u0644\u062F.")
                    .replace("member-not-admin","\u0644\u0645 \u064A\u0639\u062F {member} \u0645\u0633\u0624\u0648\u0644\u064B\u0627 \u0641\u064A \u0647\u0630\u0627 \u0627\u0644\u0628\u0644\u062F.")
                    .replace("invalid-hex","\u0627\u0644\u0644\u0648\u0646 \u0627\u0644\u0633\u062F\u0627\u0633\u064A \u063A\u064A\u0631 \u0635\u0627\u0644\u062D.");
        }
        return "";
    }

}
