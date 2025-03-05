package com.LoadTool.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public class HashUtil {

    // Método para gerar o hash MD5 de uma string
    public static String md5(String input) {
        try {
            // Obtém uma instância do MessageDigest para MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Converte a string de entrada em um array de bytes e gera o hash
            byte[] hash = md.digest(input.getBytes());

            // Converte o hash de bytes para uma string hexadecimal
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            // Lança uma exceção em caso de erro (MD5 não disponível)
            throw new RuntimeException("Erro ao gerar hash MD5", e);
        }
    }
}