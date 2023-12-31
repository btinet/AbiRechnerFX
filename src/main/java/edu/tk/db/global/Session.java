package edu.tk.db.global;

import edu.tk.examcalc.entity.User;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Objects;

public class Session {

    protected static HashMap<String, Object> post = new HashMap<>();

    protected static User user;
    private static final Charset UTF_8 = StandardCharsets.UTF_8;
    private static final String OUTPUT_FORMAT = "%-20s:%s";

    public static void set(String key, Object value){
        post.put(key,value);
    }

    public static Object get(String key){
        if(post.containsKey(key)){
            Object string = post.get(key);
            post.remove(key);
            return string;
        }
        return null;
    }

    public static Object copy(String key){
        if(post.containsKey(key)){
            return post.get(key);
        }
        return null;
    }

    public static void setUser(User user){
        Session.user = user;
    }

    public static User getUser(){
        return Session.user;
    }

    public static void destroyBenutzer(){
        Session.user = null;
    }
    public static void destroySession(){
        Session.post = new HashMap<>();
        Session.user = null;
    }

    private static byte[] digest(byte[] input) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
        return md.digest(input);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static String getHash(String string){
        byte[] md5InBytes = digest(string.getBytes(UTF_8));
        return bytesToHex(md5InBytes);
    }

}
