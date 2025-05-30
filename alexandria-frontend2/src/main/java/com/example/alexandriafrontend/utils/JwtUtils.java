package com.example.alexandriafrontend.utils;


import java.util.Base64;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JwtUtils {

    public static JsonObject decodificarToken(String jwt) {
        String[] partes = jwt.split("\\.");
        if (partes.length < 2) return null;

        String payloadBase64 = partes[1];
        String payloadJson = new String(Base64.getUrlDecoder().decode(payloadBase64));
        return JsonParser.parseString(payloadJson).getAsJsonObject();
    }
}
