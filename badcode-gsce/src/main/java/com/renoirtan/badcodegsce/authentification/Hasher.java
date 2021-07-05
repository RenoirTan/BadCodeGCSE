package com.renoirtan.badcodegsce.authentification;

import java.io.File;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.security.MessageDigest;

import org.apache.commons.codec.digest.DigestUtils;

public class Hasher {
    public static int sha256ToInt(final byte[] bytes) throws Exception {
        if (bytes.length < 32) {
            throw new Exception("Byte array must be at least 32 bytes long.");
        }
        int result = 0;
        for (int index = 0; index < 32; index++) {
            if ((index % 8) == 0) {
                result <<= 8;
                result += bytes[index];
            }
        }
        return result;
    }

    public static String byteArrayToString(final byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02X", b));
        }
        return builder.toString();
    }

    public static int hashUsernameAndPassword(
        final String username,
        final String password
    ) throws Exception {
        return new Hasher().hash(username + password).toIntSafe();
    }

    public byte[] result;
    public MessageDigest hasher;

    public Hasher() throws Exception {
        this.hasher = MessageDigest.getInstance("SHA3-256");
    }

    public int toIntSafe() throws Exception {
        return Hasher.sha256ToInt(this.result);
    }

    public int toInt() {
        try {
            return this.toIntSafe();
        } catch (Exception e) {
            return 0;
        }
    }

    public Hasher digest() {
        this.result = this.hasher.digest();
        return this;
    }

    public Hasher hash(final byte[] bytes) {
        DigestUtils.updateDigest(this.hasher, bytes);
        return this.digest();
    }

    public Hasher hash(final String string) {
        DigestUtils.updateDigest(this.hasher, string);
        return this.digest();
    }

    public Hasher hash(ByteBuffer bytes) {
        DigestUtils.updateDigest(this.hasher, bytes);
        return this.digest();
    }

    public Hasher hash(InputStream stream) throws Exception {
        DigestUtils.updateDigest(this.hasher, stream);
        return this.digest();
    }

    public Hasher hash(File file) throws Exception {
        DigestUtils.updateDigest(this.hasher, file);
        return this.digest();
    }

    public Hasher hash(Object object) throws Exception {
        // I couldn't use a switch statement here because for some reason
        // Java only accepts literals.
        // Thank you for no operator overloading :)
        Class<?> objectType = object.getClass();
        if (objectType == byte[].class) {
            this.hash((byte[]) object);
        } else if (objectType == String.class) {
            this.hash((String) object);
        } else if (objectType == ByteBuffer.class) {
            this.hash((ByteBuffer) object);
        } else if (objectType == InputStream.class) {
            this.hash((InputStream) object);
        } else if (objectType == File.class) {
            this.hash((File) object);
        } else {
            this.hash(object.toString());
        }
        return this;
    }

    public Hasher then(Object object) throws Exception {
        return this.hash(object);
    }
}
