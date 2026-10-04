package com.example.rehashdroid.logic

/**
 *
 *
 * The MD5 message-digest algorithm takes as input a message of arbitrary length
 * and produces as output a 128-bit "fingerprint" or "message digest" of the
 * input. It is conjectured that it is computationally infeasible to produce two
 * messages having the same message digest, or to produce any message having a
 * given prespecified target message digest.
 *
 *
 *
 *
 * References:
 *
 *
 *
 *  1. The [MD5](http://www.ietf.org/rfc/rfc1321.txt) Message- Digest
 * Algorithm.<br></br>
 * R. Rivest.
 *
 */
class MD5  // Constructor(s)
// -------------------------------------------------------------------------
/**
 * Trivial 0-arguments constructor.
 */
    () : BaseHash("md5", 16, BLOCK_SIZE) {
    /**
     * 128-bit interim result.
     */
    private var h0 = 0
    private var h1 = 0
    private var h2 = 0
    private var h3 = 0

    /**
     *
     *
     * Private constructor for cloning purposes.
     *
     *
     * @param md the instance to clone.
     */
    private constructor(md: MD5) : this() {
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
        return MD5(this)
    }

    // Implementation of concrete methods in BaseHash --------------------------
    @Synchronized
    override fun transform(input: ByteArray, i: Int) {
        var i = i
        val X0 = ((input[i++].toInt() and 0xFF) or ((input[i++].toInt() and 0xFF) shl 8
                ) or ((input[i++].toInt() and 0xFF) shl 16) or (input[i++].toInt() shl 24))
        val X1 = ((input[i++].toInt() and 0xFF) or ((input[i++].toInt() and 0xFF) shl 8
                ) or ((input[i++].toInt() and 0xFF) shl 16) or (input[i++].toInt() shl 24))
        val X2 = ((input[i++].toInt() and 0xFF) or ((input[i++].toInt() and 0xFF) shl 8
                ) or ((input[i++].toInt() and 0xFF) shl 16) or (input[i++].toInt() shl 24))
        val X3 = ((input[i++].toInt() and 0xFF) or ((input[i++].toInt() and 0xFF) shl 8
                ) or ((input[i++].toInt() and 0xFF) shl 16) or (input[i++].toInt() shl 24))
        val X4 = ((input[i++].toInt() and 0xFF) or ((input[i++].toInt() and 0xFF) shl 8
                ) or ((input[i++].toInt() and 0xFF) shl 16) or (input[i++].toInt() shl 24))
        val X5 = ((input[i++].toInt() and 0xFF) or ((input[i++].toInt() and 0xFF) shl 8
                ) or ((input[i++].toInt() and 0xFF) shl 16) or (input[i++].toInt() shl 24))
        val X6 = ((input[i++].toInt() and 0xFF) or ((input[i++].toInt() and 0xFF) shl 8
                ) or ((input[i++].toInt() and 0xFF) shl 16) or (input[i++].toInt() shl 24))
        val X7 = ((input[i++].toInt() and 0xFF) or ((input[i++].toInt() and 0xFF) shl 8
                ) or ((input[i++].toInt() and 0xFF) shl 16) or (input[i++].toInt() shl 24))
        val X8 = ((input[i++].toInt() and 0xFF) or ((input[i++].toInt() and 0xFF) shl 8
                ) or ((input[i++].toInt() and 0xFF) shl 16) or (input[i++].toInt() shl 24))
        val X9 = ((input[i++].toInt() and 0xFF) or ((input[i++].toInt() and 0xFF) shl 8
                ) or ((input[i++].toInt() and 0xFF) shl 16) or (input[i++].toInt() shl 24))
        val X10 = ((input[i++].toInt() and 0xFF) or ((input[i++].toInt() and 0xFF) shl 8
                ) or ((input[i++].toInt() and 0xFF) shl 16) or (input[i++].toInt() shl 24))
        val X11 = ((input[i++].toInt() and 0xFF) or ((input[i++].toInt() and 0xFF) shl 8
                ) or ((input[i++].toInt() and 0xFF) shl 16) or (input[i++].toInt() shl 24))
        val X12 = ((input[i++].toInt() and 0xFF) or ((input[i++].toInt() and 0xFF) shl 8
                ) or ((input[i++].toInt() and 0xFF) shl 16) or (input[i++].toInt() shl 24))
        val X13 = ((input[i++].toInt() and 0xFF) or ((input[i++].toInt() and 0xFF) shl 8
                ) or ((input[i++].toInt() and 0xFF) shl 16) or (input[i++].toInt() shl 24))
        val X14 = ((input[i++].toInt() and 0xFF) or ((input[i++].toInt() and 0xFF) shl 8
                ) or ((input[i++].toInt() and 0xFF) shl 16) or (input[i++].toInt() shl 24))
        val X15 = ((input[i++].toInt() and 0xFF) or ((input[i++].toInt() and 0xFF) shl 8
                ) or ((input[i++].toInt() and 0xFF) shl 16) or (input[i].toInt() shl 24))

        var A = h0
        var B = h1
        var C = h2
        var D = h3

        // hex constants are from md5.c in FSF Gnu Privacy Guard 0.9.2
        // round 1
        A += ((B and C) or (B.inv() and D)) + X0 + -0x28955b88
        A = B + (A shl 7 or (A ushr -7))
        D += ((A and B) or (A.inv() and C)) + X1 + -0x173848aa
        D = A + (D shl 12 or (D ushr -12))
        C += ((D and A) or (D.inv() and B)) + X2 + 0x242070DB
        C = D + (C shl 17 or (C ushr -17))
        B += ((C and D) or (C.inv() and A)) + X3 + -0x3e423112
        B = C + (B shl 22 or (B ushr -22))

        A += ((B and C) or (B.inv() and D)) + X4 + -0xa83f051
        A = B + (A shl 7 or (A ushr -7))
        D += ((A and B) or (A.inv() and C)) + X5 + 0x4787C62A
        D = A + (D shl 12 or (D ushr -12))
        C += ((D and A) or (D.inv() and B)) + X6 + -0x57cfb9ed
        C = D + (C shl 17 or (C ushr -17))
        B += ((C and D) or (C.inv() and A)) + X7 + -0x2b96aff
        B = C + (B shl 22 or (B ushr -22))

        A += ((B and C) or (B.inv() and D)) + X8 + 0x698098D8
        A = B + (A shl 7 or (A ushr -7))
        D += ((A and B) or (A.inv() and C)) + X9 + -0x74bb0851
        D = A + (D shl 12 or (D ushr -12))
        C += ((D and A) or (D.inv() and B)) + X10 + -0xa44f
        C = D + (C shl 17 or (C ushr -17))
        B += ((C and D) or (C.inv() and A)) + X11 + -0x76a32842
        B = C + (B shl 22 or (B ushr -22))

        A += ((B and C) or (B.inv() and D)) + X12 + 0x6B901122
        A = B + (A shl 7 or (A ushr -7))
        D += ((A and B) or (A.inv() and C)) + X13 + -0x2678e6d
        D = A + (D shl 12 or (D ushr -12))
        C += ((D and A) or (D.inv() and B)) + X14 + -0x5986bc72
        C = D + (C shl 17 or (C ushr -17))
        B += ((C and D) or (C.inv() and A)) + X15 + 0x49B40821
        B = C + (B shl 22 or (B ushr -22))

        // round 2
        A += ((B and D) or (C and D.inv())) + X1 + -0x9e1da9e
        A = B + (A shl 5 or (A ushr -5))
        D += ((A and C) or (B and C.inv())) + X6 + -0x3fbf4cc0
        D = A + (D shl 9 or (D ushr -9))
        C += ((D and B) or (A and B.inv())) + X11 + 0x265E5A51
        C = D + (C shl 14 or (C ushr -14))
        B += ((C and A) or (D and A.inv())) + X0 + -0x16493856
        B = C + (B shl 20 or (B ushr -20))

        A += ((B and D) or (C and D.inv())) + X5 + -0x29d0efa3
        A = B + (A shl 5 or (A ushr -5))
        D += ((A and C) or (B and C.inv())) + X10 + 0x02441453
        D = A + (D shl 9 or (D ushr -9))
        C += ((D and B) or (A and B.inv())) + X15 + -0x275e197f
        C = D + (C shl 14 or (C ushr -14))
        B += ((C and A) or (D and A.inv())) + X4 + -0x182c0438
        B = C + (B shl 20 or (B ushr -20))

        A += ((B and D) or (C and D.inv())) + X9 + 0x21E1CDE6
        A = B + (A shl 5 or (A ushr -5))
        D += ((A and C) or (B and C.inv())) + X14 + -0x3cc8f82a
        D = A + (D shl 9 or (D ushr -9))
        C += ((D and B) or (A and B.inv())) + X3 + -0xb2af279
        C = D + (C shl 14 or (C ushr -14))
        B += ((C and A) or (D and A.inv())) + X8 + 0x455A14ED
        B = C + (B shl 20 or (B ushr -20))

        A += ((B and D) or (C and D.inv())) + X13 + -0x561c16fb
        A = B + (A shl 5 or (A ushr -5))
        D += ((A and C) or (B and C.inv())) + X2 + -0x3105c08
        D = A + (D shl 9 or (D ushr -9))
        C += ((D and B) or (A and B.inv())) + X7 + 0x676F02D9
        C = D + (C shl 14 or (C ushr -14))
        B += ((C and A) or (D and A.inv())) + X12 + -0x72d5b376
        B = C + (B shl 20 or (B ushr -20))

        // round 3
        A += (B xor C xor D) + X5 + -0x5c6be
        A = B + (A shl 4 or (A ushr -4))
        D += (A xor B xor C) + X8 + -0x788e097f
        D = A + (D shl 11 or (D ushr -11))
        C += (D xor A xor B) + X11 + 0x6D9D6122
        C = D + (C shl 16 or (C ushr -16))
        B += (C xor D xor A) + X14 + -0x21ac7f4
        B = C + (B shl 23 or (B ushr -23))

        A += (B xor C xor D) + X1 + -0x5b4115bc
        A = B + (A shl 4 or (A ushr -4))
        D += (A xor B xor C) + X4 + 0x4BDECFA9
        D = A + (D shl 11 or (D ushr -11))
        C += (D xor A xor B) + X7 + -0x944b4a0
        C = D + (C shl 16 or (C ushr -16))
        B += (C xor D xor A) + X10 + -0x41404390
        B = C + (B shl 23 or (B ushr -23))

        A += (B xor C xor D) + X13 + 0x289B7EC6
        A = B + (A shl 4 or (A ushr -4))
        D += (A xor B xor C) + X0 + -0x155ed806
        D = A + (D shl 11 or (D ushr -11))
        C += (D xor A xor B) + X3 + -0x2b10cf7b
        C = D + (C shl 16 or (C ushr -16))
        B += (C xor D xor A) + X6 + 0x04881D05
        B = C + (B shl 23 or (B ushr -23))

        A += (B xor C xor D) + X9 + -0x262b2fc7
        A = B + (A shl 4 or (A ushr -4))
        D += (A xor B xor C) + X12 + -0x1924661b
        D = A + (D shl 11 or (D ushr -11))
        C += (D xor A xor B) + X15 + 0x1FA27CF8
        C = D + (C shl 16 or (C ushr -16))
        B += (C xor D xor A) + X2 + -0x3b53a99b
        B = C + (B shl 23 or (B ushr -23))

        // round 4
        A += (C xor (B or D.inv())) + X0 + -0xbd6ddbc
        A = B + (A shl 6 or (A ushr -6))
        D += (B xor (A or C.inv())) + X7 + 0x432AFF97
        D = A + (D shl 10 or (D ushr -10))
        C += (A xor (D or B.inv())) + X14 + -0x546bdc59
        C = D + (C shl 15 or (C ushr -15))
        B += (D xor (C or A.inv())) + X5 + -0x36c5fc7
        B = C + (B shl 21 or (B ushr -21))

        A += (C xor (B or D.inv())) + X12 + 0x655B59C3
        A = B + (A shl 6 or (A ushr -6))
        D += (B xor (A or C.inv())) + X3 + -0x70f3336e
        D = A + (D shl 10 or (D ushr -10))
        C += (A xor (D or B.inv())) + X10 + -0x100b83
        C = D + (C shl 15 or (C ushr -15))
        B += (D xor (C or A.inv())) + X1 + -0x7a7ba22f
        B = C + (B shl 21 or (B ushr -21))

        A += (C xor (B or D.inv())) + X8 + 0x6FA87E4F
        A = B + (A shl 6 or (A ushr -6))
        D += (B xor (A or C.inv())) + X15 + -0x1d31920
        D = A + (D shl 10 or (D ushr -10))
        C += (A xor (D or B.inv())) + X6 + -0x5cfebcec
        C = D + (C shl 15 or (C ushr -15))
        B += (D xor (C or A.inv())) + X13 + 0x4E0811A1
        B = C + (B shl 21 or (B ushr -21))

        A += (C xor (B or D.inv())) + X4 + -0x8ac817e
        A = B + (A shl 6 or (A ushr -6))
        D += (B xor (A or C.inv())) + X11 + -0x42c50dcb
        D = A + (D shl 10 or (D ushr -10))
        C += (A xor (D or B.inv())) + X2 + 0x2AD7D2BB
        C = D + (C shl 15 or (C ushr -15))
        B += (D xor (C or A.inv())) + X9 + -0x14792c6f
        B = C + (B shl 21 or (B ushr -21))

        h0 += A
        h1 += B
        h2 += C
        h3 += D
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
        // magic MD5/RIPEMD128 initialisation constants
        h0 = 0x67452301
        h1 = -0x10325477
        h2 = -0x67452302
        h3 = 0x10325476
    }

    public override fun selfTest(): Boolean {
        if (valid == null) {
            valid = DIGEST0 == UtilServices
                .toString(MD5().digest())
        }
        return valid!!
    }

    companion object {
        // Constants and variables
        // -------------------------------------------------------------------------
        private const val BLOCK_SIZE = 64 // inner block size in bytes

        private const val DIGEST0 = "D41D8CD98F00B204E9800998ECF8427E"

        /**
         * caches the result of the correctness test, once executed.
         */
        private var valid: Boolean? = null
    }
}