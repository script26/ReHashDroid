package com.example.rehashdroid.logic

/**
 *
 *
 * Implementation of SHA2-1 [SHA-256] per the IETF Draft Specification.
 *
 *
 *
 *
 * References:
 *
 *
 *  1. [ Descriptions of SHA-256, SHA-384, and SHA-512](http://ftp.ipv4.heanet.ie/pub/ietf/internet-drafts/draft-ietf-ipsec-ciph-aes-cbc-03.txt),
 *  1. http://csrc.nist.gov/cryptval/shs/sha256-384-512.pdf
 *
 */
class Sha256  // Constructor(s)
// -------------------------------------------------------------------------
/**
 * Trivial 0-arguments constructor.
 */
    () : BaseHash("sha-256", 32, BLOCK_SIZE) {
    /**
     * 256-bit interim result.
     */
    private var h0 = 0
    private var h1 = 0
    private var h2 = 0
    private var h3 = 0
    private var h4 = 0
    private var h5 = 0
    private var h6 = 0
    private var h7 = 0

    /**
     *
     *
     * Private constructor for cloning purposes.
     *
     *
     * @param md the instance to clone.
     */
    private constructor(md: Sha256) : this() {
        this.h0 = md.h0
        this.h1 = md.h1
        this.h2 = md.h2
        this.h3 = md.h3
        this.h4 = md.h4
        this.h5 = md.h5
        this.h6 = md.h6
        this.h7 = md.h7
        this.count = md.count
        this.buffer = (md.buffer.clone() as ByteArray?)!!
    }

    // Instance methods
    // -------------------------------------------------------------------------
    // java.lang.Cloneable interface implementation ----------------------------
    override fun clone(): Any {
        return Sha256(this)
    }

    // Implementation of concrete methods in BaseHash --------------------------
    override fun transform(input: ByteArray, offset: Int) {
        val result = sha(h0, h1, h2, h3, h4, h5, h6, h7, input, offset)

        h0 = result[0]
        h1 = result[1]
        h2 = result[2]
        h3 = result[3]
        h4 = result[4]
        h5 = result[5]
        h6 = result[6]
        h7 = result[7]
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

    override val result: ByteArray?
        get() = byteArrayOf(
            (h0 ushr 24).toByte(), (h0 ushr 16).toByte(),
            (h0 ushr 8).toByte(), h0.toByte(), (h1 ushr 24).toByte(),
            (h1 ushr 16).toByte(), (h1 ushr 8).toByte(), h1.toByte(),
            (h2 ushr 24).toByte(), (h2 ushr 16).toByte(), (h2 ushr 8).toByte(),
            h2.toByte(), (h3 ushr 24).toByte(), (h3 ushr 16).toByte(),
            (h3 ushr 8).toByte(), h3.toByte(), (h4 ushr 24).toByte(),
            (h4 ushr 16).toByte(), (h4 ushr 8).toByte(), h4.toByte(),
            (h5 ushr 24).toByte(), (h5 ushr 16).toByte(), (h5 ushr 8).toByte(),
            h5.toByte(), (h6 ushr 24).toByte(), (h6 ushr 16).toByte(),
            (h6 ushr 8).toByte(), h6.toByte(), (h7 ushr 24).toByte(),
            (h7 ushr 16).toByte(), (h7 ushr 8).toByte(), h7.toByte()
        )

    override fun resetContext() {
        // magic SHA-256 initialisation constants
        h0 = 0x6a09e667
        h1 = -0x4498517b
        h2 = 0x3c6ef372
        h3 = -0x5ab00ac6
        h4 = 0x510e527f
        h5 = -0x64fa9774
        h6 = 0x1f83d9ab
        h7 = 0x5be0cd19
    }

    public override fun selfTest(): Boolean {
        if (valid == null) {
            val md = Sha256()
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
        private val k = intArrayOf(
            0x428a2f98, 0x71374491, -0x4a3f0431,
            -0x164a245b, 0x3956c25b, 0x59f111f1, -0x6dc07d5c, -0x54e3a12b,
            -0x27f85568, 0x12835b01, 0x243185be, 0x550c7dc3, 0x72be5d74,
            -0x7f214e02, -0x6423f959, -0x3e640e8c, -0x1b64963f, -0x1041b87a,
            0x0fc19dc6, 0x240ca1cc, 0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc,
            0x76f988da, -0x67c1aeae, -0x57ce3993, -0x4ffcd838, -0x40a68039,
            -0x391ff40d, -0x2a586eb9, 0x06ca6351, 0x14292967, 0x27b70a85,
            0x2e1b2138, 0x4d2c6dfc, 0x53380d13, 0x650a7354, 0x766a0abb,
            -0x7e3d36d2, -0x6d8dd37b, -0x5d40175f, -0x57e599b5, -0x3db47490,
            -0x3893ae5d, -0x2e6d17e7, -0x2966f9dc, -0xbf1ca7b, 0x106aa070,
            0x19a4c116, 0x1e376c08, 0x2748774c, 0x34b0bcb5, 0x391c0cb3,
            0x4ed8aa4a, 0x5b9cca4f, 0x682e6ff3, 0x748f82ee, 0x78a5636f,
            -0x7b3787ec, -0x7338fdf8, -0x6f410006, -0x5baf9315, -0x41065c09,
            -0x398e870e
        )

        private const val BLOCK_SIZE = 64 // inner block size in bytes

        private const val DIGEST0 =
            "BA7816BF8F01CFEA414140DE5DAE2223B00361A396177A9CB410FF61F20015AD"

        private val w = IntArray(64)

        /**
         * caches the result of the correctness test, once executed.
         */
        private var valid: Boolean? = null

        // Class methods
        // -------------------------------------------------------------------------
        fun G(
            hh0: Int, hh1: Int, hh2: Int, hh3: Int, hh4: Int,
            hh5: Int, hh6: Int, hh7: Int, input: ByteArray, offset: Int
        ): IntArray {
            return sha(hh0, hh1, hh2, hh3, hh4, hh5, hh6, hh7, input, offset)
        }

        // SHA specific methods ----------------------------------------------------
        @Synchronized
        private fun sha(
            hh0: Int, hh1: Int, hh2: Int,
            hh3: Int, hh4: Int, hh5: Int, hh6: Int, hh7: Int, input: ByteArray, offset: Int
        ): IntArray {
            var offset = offset
            var A = hh0
            var B = hh1
            var C = hh2
            var D = hh3
            var E = hh4
            var F = hh5
            var G = hh6
            var H = hh7
            var r: Int
            var T: Int
            var T2: Int

            r = 0
            while (r < 16) {
                w[r] = (input[offset++].toInt() shl 24 or ((input[offset++].toInt() and 0xFF) shl 16
                        ) or ((input[offset++].toInt() and 0xFF) shl 8) or (input[offset++].toInt() and 0xFF))
                r++
            }
            r = 16
            while (r < 64) {
                T = w[r - 2]
                T2 = w[r - 15]
                w[r] =
                    (((((T ushr 17) or (T shl 15)) xor ((T ushr 19) or (T shl 13)) xor (T ushr 10))
                            + w[r - 7]
                            + (((T2 ushr 7) or (T2 shl 25)) xor ((T2 ushr 18) or (T2 shl 14)) xor (T2 ushr 3)) + w[r - 16]))
                r++
            }

            r = 0
            while (r < 64) {
                T = ((H
                        + (((E ushr 6) or (E shl 26)) xor ((E ushr 11) or (E shl 21)) xor ((E ushr 25) or (E shl 7)))
                        + ((E and F) xor (E.inv() and G)) + k[r] + w[r]))
                T2 =
                    ((((A ushr 2) or (A shl 30)) xor ((A ushr 13) or (A shl 19)) xor ((A ushr 22) or (A shl 10))) + (((A and B)
                            xor (A and C) xor (B and C))))
                H = G
                G = F
                F = E
                E = D + T
                D = C
                C = B
                B = A
                A = T + T2
                r++
            }

            return intArrayOf(
                hh0 + A, hh1 + B, hh2 + C, hh3 + D, hh4 + E,
                hh5 + F, hh6 + G, hh7 + H
            )
        }
    }
}