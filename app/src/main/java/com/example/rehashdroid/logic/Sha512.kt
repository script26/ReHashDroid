package com.example.rehashdroid.logic

/**
 *
 *
 * Implementation of SHA2-3 [SHA-512] per the IETF Draft Specification.
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
class Sha512  // Constructor(s)
// -------------------------------------------------------------------------
/** Trivial 0-arguments constructor.  */
    () : BaseHash("sha-512", 64, BLOCK_SIZE) {
    /** 512-bit interim result.  */
    private var h0: Long = 0
    private var h1: Long = 0
    private var h2: Long = 0
    private var h3: Long = 0
    private var h4: Long = 0
    private var h5: Long = 0
    private var h6: Long = 0
    private var h7: Long = 0

    /**
     *
     *
     * Private constructor for cloning purposes.
     *
     *
     * @param md
     * the instance to clone.
     */
    private constructor(md: Sha512) : this() {
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
        return Sha512(this)
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
        var padding = if (n < 112) (112 - n) else (240 - n)
        val result = ByteArray(padding + 16)

        // padding is always binary 1 followed by binary 0s
        result[0] = 0x80.toByte()

        // save number of bits, casting the long to an array of 8 bytes
        // TODO: FIX Only ~35 bits of 128 bit counter usable this way
        val bits = count shl 3
        padding += 8
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
            (h0 ushr 56).toByte(), (h0 ushr 48).toByte(),
            (h0 ushr 40).toByte(), (h0 ushr 32).toByte(), (h0 ushr 24).toByte(),
            (h0 ushr 16).toByte(), (h0 ushr 8).toByte(), h0.toByte(),
            (h1 ushr 56).toByte(), (h1 ushr 48).toByte(), (h1 ushr 40).toByte(),
            (h1 ushr 32).toByte(), (h1 ushr 24).toByte(), (h1 ushr 16).toByte(),
            (h1 ushr 8).toByte(), h1.toByte(), (h2 ushr 56).toByte(),
            (h2 ushr 48).toByte(), (h2 ushr 40).toByte(), (h2 ushr 32).toByte(),
            (h2 ushr 24).toByte(), (h2 ushr 16).toByte(), (h2 ushr 8).toByte(),
            h2.toByte(), (h3 ushr 56).toByte(), (h3 ushr 48).toByte(),
            (h3 ushr 40).toByte(), (h3 ushr 32).toByte(), (h3 ushr 24).toByte(),
            (h3 ushr 16).toByte(), (h3 ushr 8).toByte(), h3.toByte(),
            (h4 ushr 56).toByte(), (h4 ushr 48).toByte(), (h4 ushr 40).toByte(),
            (h4 ushr 32).toByte(), (h4 ushr 24).toByte(), (h4 ushr 16).toByte(),
            (h4 ushr 8).toByte(), h4.toByte(), (h5 ushr 56).toByte(),
            (h5 ushr 48).toByte(), (h5 ushr 40).toByte(), (h5 ushr 32).toByte(),
            (h5 ushr 24).toByte(), (h5 ushr 16).toByte(), (h5 ushr 8).toByte(),
            h5.toByte(), (h6 ushr 56).toByte(), (h6 ushr 48).toByte(),
            (h6 ushr 40).toByte(), (h6 ushr 32).toByte(), (h6 ushr 24).toByte(),
            (h6 ushr 16).toByte(), (h6 ushr 8).toByte(), h6.toByte(),
            (h7 ushr 56).toByte(), (h7 ushr 48).toByte(), (h7 ushr 40).toByte(),
            (h7 ushr 32).toByte(), (h7 ushr 24).toByte(), (h7 ushr 16).toByte(),
            (h7 ushr 8).toByte(), h7.toByte()
        )

    override fun resetContext() {
        // magic SHA-512 initialisation constants
        h0 = 0x6a09e667f3bcc908L
        h1 = -0x4498517a7b3558c5L
        h2 = 0x3c6ef372fe94f82bL
        h3 = -0x5ab00ac5a0e2c90fL
        h4 = 0x510e527fade682d1L
        h5 = -0x64fa9773d4c193e1L
        h6 = 0x1f83d9abfb41bd6bL
        h7 = 0x5be0cd19137e2179L
    }

    public override fun selfTest(): Boolean {
        if (valid == null) {
            val md = Sha512()
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
        private val k = longArrayOf(
            0x428a2f98d728ae22L, 0x7137449123ef65cdL,
            -0x4a3f043013b2c4d1L, -0x164a245a7e762444L, 0x3956c25bf348b538L,
            0x59f111f1b605d019L, -0x6dc07d5b50e6b065L, -0x54e3a12a25927ee8L,
            -0x27f855675cfcfdbeL, 0x12835b0145706fbeL, 0x243185be4ee4b28cL,
            0x550c7dc3d5ffb4e2L, 0x72be5d74f27b896fL, -0x7f214e01c4e9694fL,
            -0x6423f958da38edcbL, -0x3e640e8b3096d96cL, -0x1b64963e610eb52eL,
            -0x1041b879c7b0da1dL, 0x0fc19dc68b8cd5b5L, 0x240ca1cc77ac9c65L,
            0x2de92c6f592b0275L, 0x4a7484aa6ea6e483L, 0x5cb0a9dcbd41fbd4L,
            0x76f988da831153b5L, -0x67c1aead11992055L, -0x57ce3992d24bcdf0L,
            -0x4ffcd8376704dec1L, -0x40a680384110f11cL, -0x391ff40cc257703eL,
            -0x2a586eb86cf558dbL, 0x06ca6351e003826fL, 0x142929670a0e6e70L,
            0x27b70a8546d22ffcL, 0x2e1b21385c26c926L, 0x4d2c6dfc5ac42aedL,
            0x53380d139d95b3dfL, 0x650a73548baf63deL, 0x766a0abb3c77b2a8L,
            -0x7e3d36d1b812511aL, -0x6d8dd37aeb7dcac5L, -0x5d40175eb30efc9cL,
            -0x57e599b443bdcfffL, -0x3db4748f2f07686fL, -0x3893ae5cf9ab41d0L,
            -0x2e6d17e62910ade8L, -0x2966f9dbaa9a56f0L, -0xbf1ca7aa88edfd6L,
            0x106aa07032bbd1b8L, 0x19a4c116b8d2d0c8L, 0x1e376c085141ab53L,
            0x2748774cdf8eeb99L, 0x34b0bcb5e19b48a8L, 0x391c0cb3c5c95a63L,
            0x4ed8aa4ae3418acbL, 0x5b9cca4f7763e373L, 0x682e6ff3d6b2b8a3L,
            0x748f82ee5defb2fcL, 0x78a5636f43172f60L, -0x7b3787eb5e0f548eL,
            -0x7338fdf7e59bc614L, -0x6f410005dc9ce1d8L, -0x5baf9314217d4217L,
            -0x41065c084d3986ebL, -0x398e870d1c8dacd5L, -0x35d8c13115d99e64L,
            -0x2e794738de3f3df9L, -0x15258229321f14e2L, -0xa82b08011912e88L,
            0x06f067aa72176fbaL, 0x0a637dc5a2c898a6L, 0x113f9804bef90daeL,
            0x1b710b35131c471bL, 0x28db77f523047d84L, 0x32caab7b40c72493L,
            0x3c9ebe0a15c9bebcL, 0x431d67c49c100d4cL, 0x4cc5d4becb3e42b6L,
            0x597f299cfc657e2aL, 0x5fcb6fab3ad6faecL, 0x6c44198c4a475817L
        )

        private const val BLOCK_SIZE = 128 // inner block size in bytes

        private val DIGEST0 = ("DDAF35A193617ABACC417349AE20413112E6FA4E89A97EA20A9EEEE64B55D39A"
                + "2192992A274FC1A836BA3C23A3FEEBBD454D4423643CE80E2A9AC94FA54CA49F")

        private val w = LongArray(80)

        /** caches the result of the correctness test, once executed.  */
        private var valid: Boolean? = null

        // Class methods
        // -------------------------------------------------------------------------
        fun G(
            hh0: Long, hh1: Long, hh2: Long, hh3: Long,
            hh4: Long, hh5: Long, hh6: Long, hh7: Long, input: ByteArray, offset: Int
        ): LongArray {
            return sha(hh0, hh1, hh2, hh3, hh4, hh5, hh6, hh7, input, offset)
        }

        // SHA specific methods ----------------------------------------------------
        @Synchronized
        private fun sha(
            hh0: Long, hh1: Long, hh2: Long,
            hh3: Long, hh4: Long, hh5: Long, hh6: Long, hh7: Long, input: ByteArray,
            offset: Int
        ): LongArray {
            var offset = offset
            var A = hh0
            var B = hh1
            var C = hh2
            var D = hh3
            var E = hh4
            var F = hh5
            var G = hh6
            var H = hh7
            var T: Long
            var T2: Long
            var r: Int

            r = 0
            while (r < 16) {
                w[r] =
                    (input[offset++].toLong() shl 56 or ((input[offset++].toLong() and 0xFFL) shl 48
                            ) or ((input[offset++].toLong() and 0xFFL) shl 40
                            ) or ((input[offset++].toLong() and 0xFFL) shl 32
                            ) or ((input[offset++].toLong() and 0xFFL) shl 24
                            ) or ((input[offset++].toLong() and 0xFFL) shl 16
                            ) or ((input[offset++].toLong() and 0xFFL) shl 8
                            ) or (input[offset++].toLong() and 0xFFL))
                r++
            }
            r = 16
            while (r < 80) {
                T = w[r - 2]
                T2 = w[r - 15]
                w[r] = ((((T ushr 19) or (T shl 45)) xor ((T ushr 61) or (T shl 3)) xor (T ushr 6))
                        + w[r - 7]
                        + (((T2 ushr 1) or (T2 shl 63)) xor ((T2 ushr 8) or (T2 shl 56)) xor (T2 ushr 7))
                        + w[r - 16])
                r++
            }

            r = 0
            while (r < 80) {
                T = (H
                        + (((E ushr 14) or (E shl 50)) xor ((E ushr 18) or (E shl 46)) xor ((E ushr 41) or (E shl 23)))
                        + ((E and F) xor ((E.inv()).toLong() and G)) + k[r] + w[r])
                T2 =
                    ((((A ushr 28) or (A shl 36)) xor ((A ushr 34) or (A shl 30)) xor ((A ushr 39) or (A shl 25)))
                            + ((A and B) xor (A and C) xor (B and C)))
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

            return longArrayOf(
                hh0 + A, hh1 + B, hh2 + C, hh3 + D, hh4 + E,
                hh5 + F, hh6 + G, hh7 + H
            )
        }
    }
}