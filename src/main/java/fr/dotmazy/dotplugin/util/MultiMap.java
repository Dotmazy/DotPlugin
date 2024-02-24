package fr.dotmazy.dotplugin.util;

import java.util.AbstractMap;
import java.util.Map;

public class MultiMap<K extends Object,V extends Object> {
    private Map<Integer,K> keys;
    private Map<Integer,V> values;

    public MultiMap(){}

    public MultiMap(Map<Integer,K> keys, Map<Integer,V> values){
        this.keys = keys;
        this.values = values;
    }

    public void put(K k, V v){
        keys.put(keys.size(), k);
        values.put(values.size(), v);
    }

    public int getSize(){
        return keys.size()==values.size()?keys.size():-1;
    }

    public AbstractMap.SimpleEntry<K,V> get(int i){
        return new AbstractMap.SimpleEntry<>(keys.get(i),values.get(i));
    }

    public void remove(int i){
        keys.remove(i);
        values.remove(i);
    }

    public void removeAll(K k, V v){
        for(int i : keys.keySet())
            if(keys.get(i)==k && values.get(i)==v){
                keys.remove(keys.get(i));
                values.remove(values.get(i));
            }
    }

    public void removeAll(K k){
        for(K key : keys.values())
            if(key==k){
                keys.remove(key);
                values.remove(key);
            }
    }

    public MultiMap<K,V> getAll(K k){
        MultiMap<K,V> map = new MultiMap<>();
        for(int i : keys.keySet())
            if(keys.get(i)==k){
                map.put(keys.get(i),values.get(i));
            }
        return map;
    }

    public Map<Integer,K> getKeys(){
        return keys;
    }

    public Map<Integer,V> getValues(){
        return values;
    }

}
