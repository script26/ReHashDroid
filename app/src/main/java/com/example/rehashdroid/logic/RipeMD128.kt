package com.example.rehashdroid.logic

/**
 *
 *
 * RIPEMD-128 is a 128-bit message digest.
 *
 *
 *
 *
 * References:
 *
 *
 *
 *  1. [
 * RIPEMD160](http://www.esat.kuleuven.ac.be/~bosselae/ripemd160.html): A Strengthened Version of RIPEMD.<br></br>
 * Hans Dobbertin, Antoon Bosselaers and Bart Preneel.
 *
 */
class RipeMD128  // Constructor(s)
// -------------------------------------------------------------------------
/**
 * Trivial 0-arguments constructor.
 */
    () : BaseHash("ripemd-128", 16, BLOCK_SIZE) {
    /**
     * 128-bit h0, h1, h2, h3 (interim result)
     */
    private var h0 = 0
    private var h1 = 0
    private var h2 = 0
    private var h3 = 0

    /**
     * 512 bits work buffer = 16 x 32-bit words
     */
    private val X = IntArray(16)

    /**
     *
     *
     * Private constructor for cloning purposes.
     *
     *
     * @param md the instance to clone.
     */
    private constructor(md: RipeMD128) : this() {
        this.h0 = md.h0
        this.h1 = md.h1
        this.h2 = md.h2
        this.h3 = md.h3
        this.count = md.count
        this.buffer = (md.buffer.clone() as ByteArray?)!!
    }

    // Class methods
    // -------------------------------------------------------------------------
    // Instance methods
    // -------------------------------------------------------------------------
    // java.lang.Cloneable interface implementation ----------------------------
    override fun clone(): Any {
        return RipeMD128(this)
    }

    // Implementation of concrete methods in BaseHash --------------------------
    override fun transform(input: ByteArray, offset: Int) {
        var offset = offset
        var A: Int
        var B: Int
        var C: Int
        var D: Int
        var Ap: Int
        var Bp: Int
        var Cp: Int
        var Dp: Int
        var T: Int
        var s: Int
        var i: Int

        // encode 64 bytes from input block into an array of 16 unsigned
        // integers.
        i = 0
        while (i < 16) {
            X[i] = ((input[offset++].toInt() and 0xFF) or ((input[offset++].toInt() and 0xFF) shl 8
                    ) or ((input[offset++].toInt() and 0xFF) shl 16) or (input[offset++].toInt() shl 24))
            i++
        }

        Ap = h0
        A = Ap
        Bp = h1
        B = Bp
        Cp = h2
        C = Cp
        Dp = h3
        D = Dp

        i = 0
        while (i < 16) {
            // rounds 0...15
            s = S[i]
            T = A + (B xor C xor D) + X[i]
            A = D
            D = C
            C = B
            B = T shl s or (T ushr (32 - s))

            s = Sp[i]
            T = Ap + ((Bp and Dp) or (Cp and Dp.inv())) + X[Rp[i]] + 0x50A28BE6
            Ap = Dp
            Dp = Cp
            Cp = Bp
            Bp = T shl s or (T ushr (32 - s))
            i++
        }

        while (i < 32) {
            // rounds 16...31
            s = S[i]
            T = A + ((B and C) or (B.inv() and D)) + X[R[i]] + 0x5A827999
            A = D
            D = C
            C = B
            B = T shl s or (T ushr (32 - s))

            s = Sp[i]
            T = Ap + ((Bp or Cp.inv()) xor Dp) + X[Rp[i]] + 0x5C4DD124
            Ap = Dp
            Dp = Cp
            Cp = Bp
            Bp = T shl s or (T ushr (32 - s))
            i++
        }

        while (i < 48) {
            // rounds 32...47
            s = S[i]
            T = A + ((B or C.inv()) xor D) + X[R[i]] + 0x6ED9EBA1
            A = D
            D = C
            C = B
            B = T shl s or (T ushr (32 - s))

            s = Sp[i]
            T = Ap + ((Bp and Cp) or (Bp.inv() and Dp)) + X[Rp[i]] + 0x6D703EF3
            Ap = Dp
            Dp = Cp
            Cp = Bp
            Bp = T shl s or (T ushr (32 - s))
            i++
        }

        while (i < 64) {
            // rounds 48...63
            s = S[i]
            T = A + ((B and D) or (C and D.inv())) + X[R[i]] + -0x70e44324
            A = D
            D = C
            C = B
            B = T shl s or (T ushr (32 - s))

            s = Sp[i]
            T = Ap + (Bp xor Cp xor Dp) + X[Rp[i]]
            Ap = Dp
            Dp = Cp
            Cp = Bp
            Bp = T shl s or (T ushr (32 - s))
            i++
        }

        T = h1 + C + Dp
        h1 = h2 + D + Ap
        h2 = h3 + A + Bp
        h3 = h0 + B + Cp
        h0 = T
    }

    override fun padBuffer(): ByteArray {
        val n = (count % BLOCK_SIZE).toInt()
        var padding = if (n < 56) (56 - n) else (120 - n)
        val result = ByteArray(padding + 8)

        // padding is always binary 1 followed by binary 0s
        result[0] = 0x80.toByte()

        // save number of bits, casting the long to an array of 8 bytes
        val bits = count shl 3
        result[padding++] = bits.toByte()
        result[padding++] = (bits ushr 8).toByte()
        result[padding++] = (bits ushr 16).toByte()
        result[padding++] = (bits ushr 24).toByte()
        result[padding++] = (bits ushr 32).toByte()
        result[padding++] = (bits ushr 40).toByte()
        result[padding++] = (bits ushr 48).toByte()
        result[padding] = (bits ushr 56).toByte()

        return result
    }

    override val result: ByteArray
        get() {
            val result = byteArrayOf(
                h0.toByte(), (h0 ushr 8).toByte(),
                (h0 ushr 16).toByte(), (h0 ushr 24).toByte(), h1.toByte(),
                (h1 ushr 8).toByte(), (h1 ushr 16).toByte(), (h1 ushr 24).toByte(),
                h2.toByte(), (h2 ushr 8).toByte(), (h2 ushr 16).toByte(),
                (h2 ushr 24).toByte(), h3.toByte(), (h3 ushr 8).toByte(),
                (h3 ushr 16).toByte(), (h3 ushr 24).toByte()
            )

            return result
        }

    override fun resetContext() {
        // magic RIPEMD128 initialisation constants
        h0 = 0x67452301
        h1 = -0x10325477
        h2 = -0x67452302
        h3 = 0x10325476
    }

    public override fun selfTest(): Boolean {
        if (valid == null) {
            valid = DIGEST0 == UtilServices
                .toString(RipeMD128().digest())
        }
        return valid!!
    }

    companion object {
        // Constants and variables
        // -------------------------------------------------------------------------
        private const val BLOCK_SIZE = 64 // inner block size in bytes

        private const val DIGEST0 = "CDF26213A150DC3ECB610F18F6B38B46"

        /**
         * Constants for the transform method.
         */
        // selection of message word
        private val R = intArrayOf(
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
            13, 14, 15, 7, 4, 13, 1, 10, 6, 15, 3, 12, 0, 9, 5, 2, 14, 11, 8,
            3, 10, 14, 4, 9, 15, 8, 1, 2, 7, 0, 6, 13, 11, 5, 12, 1, 9, 11, 10,
            0, 8, 12, 4, 13, 3, 7, 15, 14, 5, 6, 2
        )

        private val Rp = intArrayOf(
            5, 14, 7, 0, 9, 2, 11, 4, 13, 6, 15, 8,
            1, 10, 3, 12, 6, 11, 3, 7, 0, 13, 5, 10, 14, 15, 8, 12, 4, 9, 1, 2,
            15, 5, 1, 3, 7, 14, 6, 9, 11, 8, 12, 2, 10, 0, 4, 13, 8, 6, 4, 1,
            3, 11, 15, 0, 5, 12, 2, 13, 9, 7, 10, 14
        )

        // amount for rotate left (rol)
        private val S = intArrayOf(
            11, 14, 15, 12, 5, 8, 7, 9, 11, 13, 14,
            15, 6, 7, 9, 8, 7, 6, 8, 13, 11, 9, 7, 15, 7, 12, 15, 9, 11, 7, 13,
            12, 11, 13, 6, 7, 14, 9, 13, 15, 14, 8, 13, 6, 5, 12, 7, 5, 11, 12,
            14, 15, 14, 15, 9, 8, 9, 14, 5, 6, 8, 6, 5, 12
        )

        private val Sp = intArrayOf(
            8, 9, 9, 11, 13, 15, 15, 5, 7, 7, 8, 11,
            14, 14, 12, 6, 9, 13, 15, 7, 12, 8, 9, 11, 7, 7, 12, 7, 6, 15, 13,
            11, 9, 7, 15, 11, 8, 6, 6, 14, 12, 13, 5, 14, 13, 13, 7, 5, 15, 5,
            8, 11, 14, 14, 6, 14, 6, 9, 12, 9, 12, 5, 15, 8
        )

        /**
         * caches the result of the correctness test, once executed.
         */
        private var valid: Boolean? = null
    }
}