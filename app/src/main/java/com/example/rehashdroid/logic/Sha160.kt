package com.example.rehashdroid.logic

/**
 *
 *
 * The Secure Hash Algorithm (SHA-1) is required for use with the Digital
 * Signature Algorithm (DSA) as specified in the Digital Signature Standard
 * (DSS) and whenever a secure hash algorithm is required for federal
 * applications. For a message of length less than 2^64 bits, the SHA-1 produces
 * a 160-bit condensed representation of the message called a message digest.
 * The message digest is used during generation of a signature for the message.
 * The SHA-1 is also used to compute a message digest for the received version
 * of the message during the process of verifying the signature. Any change to
 * the message in transit will, with very high probability, result in a
 * different message digest, and the signature will fail to verify.
 *
 *
 *
 *
 * The SHA-1 is designed to have the following properties: it is computationally
 * infeasible to find a message which corresponds to a given message digest, or
 * to find two different messages which produce the same message digest.
 *
 *
 *
 *
 * References:
 *
 *
 *
 *  1. [SECURE HASH
 * STANDARD](http://www.itl.nist.gov/fipspubs/fip180-1.htm)<br></br>
 * Federal Information, Processing Standards Publication 180-1, 1995 April 17.
 *
 */
class Sha160  // Constructor(s)
// -------------------------------------------------------------------------
/**
 * Trivial 0-arguments constructor.
 */
    () : BaseHash("sha-160", 20, BLOCK_SIZE) {
    /**
     * 160-bit interim result.
     */
    private var h0 = 0
    private var h1 = 0
    private var h2 = 0
    private var h3 = 0
    private var h4 = 0

    /**
     *
     *
     * Private constructor for cloning purposes.
     *
     *
     * @param md the instance to clone.
     */
    private constructor(md: Sha160) : this() {
        this.h0 = md.h0
        this.h1 = md.h1
        this.h2 = md.h2
        this.h3 = md.h3
        this.h4 = md.h4
        this.count = md.count
        this.buffer = (md.buffer.clone() as ByteArray?)!!
    }

    // Instance methods
    // -------------------------------------------------------------------------
    // java.lang.Cloneable interface implementation ----------------------------
    override fun clone(): Any {
        return Sha160(this)
    }

    // Implementation of concrete methods in BaseHash --------------------------
    override fun transform(input: ByteArray, offset: Int) {
        // int i, T;
        // for (i = 0; i < 16; i++) {
        // W[i] = in[offset++] << 24 |
        // (in[offset++] & 0xFF) << 16 |
        // (in[offset++] & 0xFF) << 8 |
        // (in[offset++] & 0xFF);
        // }
        // for (i = 16; i < 80; i++) {
        // T = W[i-3] ^ W[i-8] ^ W[i-14] ^ W[i-16];
        // W[i] = T << 1 | T >>> 31;
        // }

        // int[] result = sha(h0, h1, h2, h3, h4, in, offset, W);

        val result = sha(h0, h1, h2, h3, h4, input, offset)

        h0 = result[0]
        h1 = result[1]
        h2 = result[2]
        h3 = result[3]
        h4 = result[4]
    }

    override fun padBuffer(): ByteArray {
        val n = (count % BLOCK_SIZE).toInt()
        var padding = if (n < 56) (56 - n) else (120 - n)
        val result = ByteArray(padding + 8)

        // padding is always binary 1 followed by binary 0s
        result[0] = 0x80.toByte()

        // save number of bits, casting the long to an array of 8 bytes
        val bits = count shl 3
        result[padding++] = (bits ushr 56).toByte()
        result[padding++] = (bits ushr 48).toByte()
        result[padding++] = (bits ushr 40).toByte()
        result[padding++] = (bits ushr 32).toByte()
        result[padding++] = (bits ushr 24).toByte()
        result[padding++] = (bits ushr 16).toByte()
        result[padding++] = (bits ushr 8).toByte()
        result[padding] = bits.toByte()

        return result
    }

    override val result: ByteArray
        get() {
            val result = byteArrayOf(
                (h0 ushr 24).toByte(), (h0 ushr 16).toByte(),
                (h0 ushr 8).toByte(), h0.toByte(), (h1 ushr 24).toByte(),
                (h1 ushr 16).toByte(), (h1 ushr 8).toByte(), h1.toByte(),
                (h2 ushr 24).toByte(), (h2 ushr 16).toByte(), (h2 ushr 8).toByte(),
                h2.toByte(), (h3 ushr 24).toByte(), (h3 ushr 16).toByte(),
                (h3 ushr 8).toByte(), h3.toByte(), (h4 ushr 24).toByte(),
                (h4 ushr 16).toByte(), (h4 ushr 8).toByte(), h4.toByte()
            )

            return result
        }

    override fun resetContext() {
        // magic SHA-1/RIPEMD160 initialisation constants
        h0 = 0x67452301
        h1 = -0x10325477
        h2 = -0x67452302
        h3 = 0x10325476
        h4 = -0x3c2d1e10
    }

    public override fun selfTest(): Boolean {
        if (valid == null) {
            val md = Sha160()
            md.update(0x61.toByte()) // a
            md.update(0x62.toByte()) // b
            md.update(0x63.toByte()) // c
            val result: String? = UtilServices.toString(md.digest())
            valid = DIGEST0 == result
        }
        return valid!!
    }

    companion object {
        // Constants and variables
        // -------------------------------------------------------------------------
        private const val BLOCK_SIZE = 64 // inner block size in bytes

        private const val DIGEST0 = "A9993E364706816ABA3E25717850C26C9CD0D89D"

        private val w = IntArray(80)

        /**
         * caches the result of the correctness test, once executed.
         */
        private var valid: Boolean? = null

        // Class methods
        // -------------------------------------------------------------------------
        fun G(
            hh0: Int, hh1: Int, hh2: Int, hh3: Int, hh4: Int,
            input: ByteArray, offset: Int
        ): IntArray {
            // int[] w = new int[80];
            // int i, T;
            // for (i = 0; i < 16; i++) {
            // w[i] = in[offset++] << 24 |
            // (in[offset++] & 0xFF) << 16 |
            // (in[offset++] & 0xFF) << 8 |
            // (in[offset++] & 0xFF);
            // }
            // for (i = 16; i < 80; i++) {
            // T = w[i-3] ^ w[i-8] ^ w[i-14] ^ w[i-16];
            // w[i] = T << 1 | T >>> 31;
            // }

            // return sha(hh0, hh1, hh2, hh3, hh4, in, offset, w);

            return sha(hh0, hh1, hh2, hh3, hh4, input, offset)
        }

        // SHA specific methods ----------------------------------------------------
        @Synchronized
        private fun  // sha(int hh0, int hh1, int hh2, int hh3, int hh4, byte[] in, int offset,
        // int[] w) {
                sha(
            hh0: Int,
            hh1: Int,
            hh2: Int,
            hh3: Int,
            hh4: Int,
            input: ByteArray,
            offset: Int
        ): IntArray {
            var offset = offset
            var A = hh0
            var B = hh1
            var C = hh2
            var D = hh3
            var E = hh4
            var r: Int
            var T: Int

            r = 0
            while (r < 16) {
                w[r] = (input[offset++].toInt() shl 24 or ((input[offset++].toInt() and 0xFF) shl 16
                        ) or ((input[offset++].toInt() and 0xFF) shl 8) or (input[offset++].toInt() and 0xFF))
                r++
            }
            r = 16
            while (r < 80) {
                T = w[r - 3] xor w[r - 8] xor w[r - 14] xor w[r - 16]
                w[r] = T shl 1 or (T ushr 31)
                r++
            }

            // rounds 0-19
            r = 0
            while (r < 20) {
                T = ((A shl 5 or (A ushr 27)) + ((B and C) or (B.inv() and D)) + E + w[r]
                        + 0x5A827999)
                E = D
                D = C
                C = B shl 30 or (B ushr 2)
                B = A
                A = T
                r++
            }

            // rounds 20-39
            r = 20
            while (r < 40) {
                T = (A shl 5 or (A ushr 27)) + (B xor C xor D) + E + w[r] + 0x6ED9EBA1
                E = D
                D = C
                C = B shl 30 or (B ushr 2)
                B = A
                A = T
                r++
            }

            // rounds 40-59
            r = 40
            while (r < 60) {
                T = ((A shl 5 or (A ushr 27)) + (B and C or (B and D) or (C and D)) + E + w[r]
                        + -0x70e44324)
                E = D
                D = C
                C = B shl 30 or (B ushr 2)
                B = A
                A = T
                r++
            }

            // rounds 60-79
            r = 60
            while (r < 80) {
                T = (A shl 5 or (A ushr 27)) + (B xor C xor D) + E + w[r] + -0x359d3e2a
                E = D
                D = C
                C = B shl 30 or (B ushr 2)
                B = A
                A = T
                r++
            }

            return intArrayOf(hh0 + A, hh1 + B, hh2 + C, hh3 + D, hh4 + E)
        }
    }
}