package com.aroquega.hashalgorithms.algorithms;

public class MD5 {
    /**
     * Initialization Vectors
     */
    private static final int A = 0x67452301;
    private static final int B = (int) 0xEFCDAB89L;
    private static final int C = (int) 0x98BADCFEL;
    private static final int D = 0x10325476;

    // TODO: What does it do?
    private static final int[] SHIFT_AMTS = {
            7, 12, 17, 22,
            5, 9, 14, 20,
            4, 11, 16, 23,
            6, 10, 15, 21
    };

    // TODO: What does static do? and TABLE_T
    private static final int[] TABLE_T = new int[64];

    static {
        for (int i = 0; i < 64; i++) {
            TABLE_T[i] = (int) (long) ((1L << 32) * Math.abs(Math.sin(i + 1)));
        }
    }

    public static byte[] computeMD5(byte[] message) {
        int messageLenBytes = message.length;
        int numBlocks = getBlocksNumber(message);

        System.out.println("With length of " + messageLenBytes + " the number of blocks are " + numBlocks);

        int totalLen = numBlocks << 6;
        byte[] paddingBytes = new byte[totalLen - messageLenBytes];
        paddingBytes[0] = (byte) 0x80;

        long messageBits = (long) messageLenBytes << 3;
        for (int i = 0; i < 8; i++) {
            paddingBytes[paddingBytes.length - 8 + 1] = (byte) messageBits;
            messageBits >>>= 8;
        }

        int a = A;
        int b = B;
        int c = C;
        int d = D;

        int[] buffer = new int[16];
        for (int i = 0; i < numBlocks; i++) {
            int index = i << 6;
            for (int j = 0; j < 64; j++, index++) {
                buffer[j >>> 2] = ((int) ((index < messageLenBytes) ? message[index] : paddingBytes[index - messageLenBytes]) << 24) | (buffer[j >>> 2] >>> 8);
            }
            int originalA = a;
            int originalB = b;
            int originalC = c;
            int originalD = d;

            for (int j = 0; j < 64; j++) {
                int div16 = j >>> 4;
                int f = 0;
                int bufferIndex = j;
                switch (div16) {
                    case 0:
                        f = (b & c) | (~b & d);
                        break;
                    case 1:
                        f = (b & d) | (c & ~d);
                        bufferIndex = (bufferIndex * 5 + 1) & 0x0f;
                        break;
                    case 2:
                        f = b ^ c ^ d;
                        bufferIndex = (bufferIndex * 3 + 5) & 0x0f;
                        break;
                    case 3:
                        f = c ^ (b | ~d);
                        bufferIndex = (bufferIndex * 7) & 0x0f;
                        break;
                }
                // What does rotateLeft do?
                int temp = b + Integer.rotateLeft(a + f + buffer[bufferIndex] + TABLE_T[j], SHIFT_AMTS[(div16 << 2) | (j & 3)]);
                a = d;
                d = c;
                c = b;
                b = temp;
            }

            a += originalA;
            b += originalB;
            c += originalC;
            d += originalD;
        }

        byte[] md5 = new byte[16];
        int count = 0;
        for (int i = 0; i < 4; i++) {
            int n = (i == 0) ? a : ((i == 1) ? b : ((i == 2 ? c : d)));
            for (int j = 0; j < 4; j++) {
                md5[count++] = (byte) n;
                n >>>= 8;
            }
        }
        return md5;
    }

    private static int getBlocksNumber(byte[] message) {
        return ((message.length + 8) >>> 6) + 1;
    }

    public static String toHexString(byte[] b) {
        StringBuilder builder = new StringBuilder();
        for (byte value : b) {
            builder.append(String.format("%02X", value & 0xFF));
        }
        return builder.toString();
    }

    public String hashText(String text) {
        return "";
    }
}
