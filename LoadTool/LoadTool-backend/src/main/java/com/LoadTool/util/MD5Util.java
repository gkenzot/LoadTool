package com.LoadTool.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public class MD5Util {

    // Método para gerar o hash MD5 de uma string
    public static String hashMD5(String input) {
        try {
            // Obtém uma instância do MessageDigest para o algoritmo MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Gera o hash a partir da string de entrada
            byte[] hash = md.digest(input.getBytes());

            // Converte o hash de bytes para uma string hexadecimal
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao gerar hash MD5", e);
        }
    }
}