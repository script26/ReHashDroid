package com.example.rehashdroid.logic

/**
 *
 *
 * An implementation of Ron Rivest's MD4 message digest algorithm.
 *
 *
 *
 *
 * MD4 was the precursor to the stronger [gnu.crypto.hash.MD5] algorithm,
 * and while not considered cryptograpically secure itself, MD4 is in use in
 * various applications. It is slightly faster than MD5.
 *
 *
 *
 *
 * References:
 *
 *
 *
 *  1. The [MD4](http://www.ietf.org/rfc/rfc1320.txt) Message-Digest
 * Algorithm.<br></br>
 * R. Rivest.
 *
 *
 * @author Casey Marshall (rsdio@metastatic.org)
 */
class MD4  // Constructor(s)
// -------------------------------------------------------------------------
/**
 *
 *
 * Public constructor. Initializes the chaining variables, sets the byte
 * count to `0`, and creates a new block of `512`
 * bits.
 *
 */
    () : BaseHash("md4", DIGEST_LENGTH, BLOCK_LENGTH) {
    private var a = 0
    private var b = 0
    private var c = 0
    private var d = 0

    /**
     *
     *
     * Trivial private constructor for cloning purposes.
     *
     *
     * @param that the instance to clone.
     */
    private constructor(that: MD4) : this() {
        this.a = that.a
        this.b = that.b
        this.c = that.c
        this.d = that.d
        this.count = that.count
        this.buffer = (that.buffer.clone() as ByteArray?)!!
    }

    // Class methods
    // -------------------------------------------------------------------------
    // Instance methods
    // -------------------------------------------------------------------------
    // java.lang.Cloneable interface implementation ----------------------------
    override fun clone(): Any {
        return MD4(this)
    }

    override val result: ByteArray
        // Implementation of abstract methods in BashHash --------------------------
        get() {
            val digest = byteArrayOf(
                a.toByte(), (a ushr 8).toByte(), (a ushr 16).toByte(),
                (a ushr 24).toByte(), b.toByte(), (b ushr 8).toByte(),
                (b ushr 16).toByte(), (b ushr 24).toByte(), c.toByte(),
                (c ushr 8).toByte(), (c ushr 16).toByte(), (c ushr 24).toByte(),
                d.toByte(), (d ushr 8).toByte(), (d ushr 16).toByte(),
                (d ushr 24).toByte()
            )
            return digest
        }

    override fun resetContext() {
        a = A
        b = B
        c = C
        d = D
    }

    public override fun selfTest(): Boolean {
        if (valid == null) {
            valid = DIGEST0 == UtilServices
                .toString(MD4().digest())
        }
        return valid!!
    }

    override fun padBuffer(): ByteArray {
        val n = (count % BLOCK_LENGTH).toInt()
        var padding = if (n < 56) (56 - n) else (120 - n)
        val pad = ByteArray(padding + 8)

        pad[0] = 0x80.toByte()
        val bits = count shl 3
        pad[padding++] = bits.toByte()
        pad[padding++] = (bits ushr 8).toByte()
        pad[padding++] = (bits ushr 16).toByte()
        pad[padding++] = (bits ushr 24).toByte()
        pad[padding++] = (bits ushr 32).toByte()
        pad[padding++] = (bits ushr 40).toByte()
        pad[padding++] = (bits ushr 48).toByte()
        pad[padding] = (bits ushr 56).toByte()

        return pad
    }

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

        var aa: Int
        var bb: Int
        var cc: Int
        var dd: Int

        aa = a
        bb = b
        cc = c
        dd = d

        aa += ((bb and cc) or ((bb.inv()) and dd)) + X0
        aa = aa shl 3 or (aa ushr -3)
        dd += ((aa and bb) or ((aa.inv()) and cc)) + X1
        dd = dd shl 7 or (dd ushr -7)
        cc += ((dd and aa) or ((dd.inv()) and bb)) + X2
        cc = cc shl 11 or (cc ushr -11)
        bb += ((cc and dd) or ((cc.inv()) and aa)) + X3
        bb = bb shl 19 or (bb ushr -19)
        aa += ((bb and cc) or ((bb.inv()) and dd)) + X4
        aa = aa shl 3 or (aa ushr -3)
        dd += ((aa and bb) or ((aa.inv()) and cc)) + X5
        dd = dd shl 7 or (dd ushr -7)
        cc += ((dd and aa) or ((dd.inv()) and bb)) + X6
        cc = cc shl 11 or (cc ushr -11)
        bb += ((cc and dd) or ((cc.inv()) and aa)) + X7
        bb = bb shl 19 or (bb ushr -19)
        aa += ((bb and cc) or ((bb.inv()) and dd)) + X8
        aa = aa shl 3 or (aa ushr -3)
        dd += ((aa and bb) or ((aa.inv()) and cc)) + X9
        dd = dd shl 7 or (dd ushr -7)
        cc += ((dd and aa) or ((dd.inv()) and bb)) + X10
        cc = cc shl 11 or (cc ushr -11)
        bb += ((cc and dd) or ((cc.inv()) and aa)) + X11
        bb = bb shl 19 or (bb ushr -19)
        aa += ((bb and cc) or ((bb.inv()) and dd)) + X12
        aa = aa shl 3 or (aa ushr -3)
        dd += ((aa and bb) or ((aa.inv()) and cc)) + X13
        dd = dd shl 7 or (dd ushr -7)
        cc += ((dd and aa) or ((dd.inv()) and bb)) + X14
        cc = cc shl 11 or (cc ushr -11)
        bb += ((cc and dd) or ((cc.inv()) and aa)) + X15
        bb = bb shl 19 or (bb ushr -19)

        aa += ((bb and (cc or dd)) or (cc and dd)) + X0 + 0x5a827999
        aa = aa shl 3 or (aa ushr -3)
        dd += ((aa and (bb or cc)) or (bb and cc)) + X4 + 0x5a827999
        dd = dd shl 5 or (dd ushr -5)
        cc += ((dd and (aa or bb)) or (aa and bb)) + X8 + 0x5a827999
        cc = cc shl 9 or (cc ushr -9)
        bb += ((cc and (dd or aa)) or (dd and aa)) + X12 + 0x5a827999
        bb = bb shl 13 or (bb ushr -13)
        aa += ((bb and (cc or dd)) or (cc and dd)) + X1 + 0x5a827999
        aa = aa shl 3 or (aa ushr -3)
        dd += ((aa and (bb or cc)) or (bb and cc)) + X5 + 0x5a827999
        dd = dd shl 5 or (dd ushr -5)
        cc += ((dd and (aa or bb)) or (aa and bb)) + X9 + 0x5a827999
        cc = cc shl 9 or (cc ushr -9)
        bb += ((cc and (dd or aa)) or (dd and aa)) + X13 + 0x5a827999
        bb = bb shl 13 or (bb ushr -13)
        aa += ((bb and (cc or dd)) or (cc and dd)) + X2 + 0x5a827999
        aa = aa shl 3 or (aa ushr -3)
        dd += ((aa and (bb or cc)) or (bb and cc)) + X6 + 0x5a827999
        dd = dd shl 5 or (dd ushr -5)
        cc += ((dd and (aa or bb)) or (aa and bb)) + X10 + 0x5a827999
        cc = cc shl 9 or (cc ushr -9)
        bb += ((cc and (dd or aa)) or (dd and aa)) + X14 + 0x5a827999
        bb = bb shl 13 or (bb ushr -13)
        aa += ((bb and (cc or dd)) or (cc and dd)) + X3 + 0x5a827999
        aa = aa shl 3 or (aa ushr -3)
        dd += ((aa and (bb or cc)) or (bb and cc)) + X7 + 0x5a827999
        dd = dd shl 5 or (dd ushr -5)
        cc += ((dd and (aa or bb)) or (aa and bb)) + X11 + 0x5a827999
        cc = cc shl 9 or (cc ushr -9)
        bb += ((cc and (dd or aa)) or (dd and aa)) + X15 + 0x5a827999
        bb = bb shl 13 or (bb ushr -13)

        aa += (bb xor cc xor dd) + X0 + 0x6ed9eba1
        aa = aa shl 3 or (aa ushr -3)
        dd += (aa xor bb xor cc) + X8 + 0x6ed9eba1
        dd = dd shl 9 or (dd ushr -9)
        cc += (dd xor aa xor bb) + X4 + 0x6ed9eba1
        cc = cc shl 11 or (cc ushr -11)
        bb += (cc xor dd xor aa) + X12 + 0x6ed9eba1
        bb = bb shl 15 or (bb ushr -15)
        aa += (bb xor cc xor dd) + X2 + 0x6ed9eba1
        aa = aa shl 3 or (aa ushr -3)
        dd += (aa xor bb xor cc) + X10 + 0x6ed9eba1
        dd = dd shl 9 or (dd ushr -9)
        cc += (dd xor aa xor bb) + X6 + 0x6ed9eba1
        cc = cc shl 11 or (cc ushr -11)
        bb += (cc xor dd xor aa) + X14 + 0x6ed9eba1
        bb = bb shl 15 or (bb ushr -15)
        aa += (bb xor cc xor dd) + X1 + 0x6ed9eba1
        aa = aa shl 3 or (aa ushr -3)
        dd += (aa xor bb xor cc) + X9 + 0x6ed9eba1
        dd = dd shl 9 or (dd ushr -9)
        cc += (dd xor aa xor bb) + X5 + 0x6ed9eba1
        cc = cc shl 11 or (cc ushr -11)
        bb += (cc xor dd xor aa) + X13 + 0x6ed9eba1
        bb = bb shl 15 or (bb ushr -15)
        aa += (bb xor cc xor dd) + X3 + 0x6ed9eba1
        aa = aa shl 3 or (aa ushr -3)
        dd += (aa xor bb xor cc) + X11 + 0x6ed9eba1
        dd = dd shl 9 or (dd ushr -9)
        cc += (dd xor aa xor bb) + X7 + 0x6ed9eba1
        cc = cc shl 11 or (cc ushr -11)
        bb += (cc xor dd xor aa) + X15 + 0x6ed9eba1
        bb = bb shl 15 or (bb ushr -15)

        a += aa
        b += bb
        c += cc
        d += dd
    }

    companion object {
        // Constants and variables
        // -------------------------------------------------------------------------
        /**
         * An MD4 message digest is always 128-bits long, or 16 bytes.
         */
        private const val DIGEST_LENGTH = 16

        /**
         * The MD4 algorithm operates on 512-bit blocks, or 64 bytes.
         */
        private const val BLOCK_LENGTH = 64

        private const val A = 0x67452301

        private const val B = -0x10325477

        private const val C = -0x67452302

        private const val D = 0x10325476

        /**
         * The output of this message digest when no data has been input.
         */
        private const val DIGEST0 = "31D6CFE0D16AE931B73C59D7E0C089C0"

        /**
         * caches the result of the correctness test, once executed.
         */
        private var valid: Boolean? = null
    }
}