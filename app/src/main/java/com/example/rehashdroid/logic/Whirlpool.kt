package com.example.rehashdroid.logic

/**
 * Whirlpool, a new 512-bit hashing function operating on messages less than 2
 * ** 256 bits in length. The function structure is designed according to the
 * Wide Trail strategy and permits a wide variety of implementation trade-offs.
 *
 *
 * This implementation is of Whirlpool Version 3, described in [1] last revised
 * on May 24th, 2003.
 *
 *
 * **IMPORTANT**: This implementation is not thread-safe.
 *
 *
 * References:
 *
 *  1. [
 * The WHIRLPOOL Hashing Function](http://planeta.terra.com.br/informatica/paulobarreto/WhirlpoolPage.html).<br></br>
 * [Paulo S.L.M. Barreto](mailto:paulo.barreto@terra.com.br) and [Vincent Rijmen](mailto:vincent.rijmen@iaik.tugraz.at).
 *
 */
class Whirlpool  // Constructor(s)
// -------------------------------------------------------------------------
/**
 * Trivial 0-arguments constructor.
 */
    () : BaseHash("whirlpool", 20, BLOCK_SIZE) {
    /**
     * The 512-bit context as 8 longs.
     */
    private var H0: Long = 0
    private var H1: Long = 0
    private var H2: Long = 0
    private var H3: Long = 0
    private var H4: Long = 0
    private var H5: Long = 0
    private var H6: Long = 0
    private var H7: Long = 0

    /**
     * Work area for computing the round key schedule.
     */
    private var k00: Long = 0
    private var k01: Long = 0
    private var k02: Long = 0
    private var k03: Long = 0
    private var k04: Long = 0
    private var k05: Long = 0
    private var k06: Long = 0
    private var k07: Long = 0
    private var Kr0: Long = 0
    private var Kr1: Long = 0
    private var Kr2: Long = 0
    private var Kr3: Long = 0
    private var Kr4: Long = 0
    private var Kr5: Long = 0
    private var Kr6: Long = 0
    private var Kr7: Long = 0

    /**
     * work area for transforming the 512-bit buffer.
     */
    private var n0: Long = 0
    private var n1: Long = 0
    private var n2: Long = 0
    private var n3: Long = 0
    private var n4: Long = 0
    private var n5: Long = 0
    private var n6: Long = 0
    private var n7: Long = 0
    private var nn0: Long = 0
    private var nn1: Long = 0
    private var nn2: Long = 0
    private var nn3: Long = 0
    private var nn4: Long = 0
    private var nn5: Long = 0
    private var nn6: Long = 0
    private var nn7: Long = 0

    /**
     * work area for holding block cipher's intermediate values.
     */
    private var w0: Long = 0
    private var w1: Long = 0
    private var w2: Long = 0
    private var w3: Long = 0
    private var w4: Long = 0
    private var w5: Long = 0
    private var w6: Long = 0
    private var w7: Long = 0

    /**
     *
     *
     * Private constructor for cloning purposes.
     *
     *
     * @param md the instance to clone.
     */
    private constructor(md: Whirlpool) : this() {
        this.H0 = md.H0
        this.H1 = md.H1
        this.H2 = md.H2
        this.H3 = md.H3
        this.H4 = md.H4
        this.H5 = md.H5
        this.H6 = md.H6
        this.H7 = md.H7
        this.count = md.count
        this.buffer = (md.buffer.clone() as ByteArray?)!!
    }

    // Class methods
    // -------------------------------------------------------------------------
    // Instance methods
    // -------------------------------------------------------------------------
    // java.lang.Cloneable interface implementation ----------------------------
    override fun clone(): Any {
        return (Whirlpool(this))
    }

    // Implementation of concrete methods in BaseHash --------------------------
    override fun transform(input: ByteArray, offset: Int) {
        // apply mu to the input
        var offset = offset
        n0 =
            ((input[offset++].toLong() and 0xFFL) shl 56 or ((input[offset++].toLong() and 0xFFL) shl 48
                    ) or ((input[offset++].toLong() and 0xFFL) shl 40) or ((input[offset++].toLong() and 0xFFL) shl 32
                    ) or ((input[offset++].toLong() and 0xFFL) shl 24) or ((input[offset++].toLong() and 0xFFL) shl 16
                    ) or ((input[offset++].toLong() and 0xFFL) shl 8) or (input[offset++].toLong() and 0xFFL))
        n1 =
            ((input[offset++].toLong() and 0xFFL) shl 56 or ((input[offset++].toLong() and 0xFFL) shl 48
                    ) or ((input[offset++].toLong() and 0xFFL) shl 40) or ((input[offset++].toLong() and 0xFFL) shl 32
                    ) or ((input[offset++].toLong() and 0xFFL) shl 24) or ((input[offset++].toLong() and 0xFFL) shl 16
                    ) or ((input[offset++].toLong() and 0xFFL) shl 8) or (input[offset++].toLong() and 0xFFL))
        n2 =
            ((input[offset++].toLong() and 0xFFL) shl 56 or ((input[offset++].toLong() and 0xFFL) shl 48
                    ) or ((input[offset++].toLong() and 0xFFL) shl 40) or ((input[offset++].toLong() and 0xFFL) shl 32
                    ) or ((input[offset++].toLong() and 0xFFL) shl 24) or ((input[offset++].toLong() and 0xFFL) shl 16
                    ) or ((input[offset++].toLong() and 0xFFL) shl 8) or (input[offset++].toLong() and 0xFFL))
        n3 =
            ((input[offset++].toLong() and 0xFFL) shl 56 or ((input[offset++].toLong() and 0xFFL) shl 48
                    ) or ((input[offset++].toLong() and 0xFFL) shl 40) or ((input[offset++].toLong() and 0xFFL) shl 32
                    ) or ((input[offset++].toLong() and 0xFFL) shl 24) or ((input[offset++].toLong() and 0xFFL) shl 16
                    ) or ((input[offset++].toLong() and 0xFFL) shl 8) or (input[offset++].toLong() and 0xFFL))
        n4 =
            ((input[offset++].toLong() and 0xFFL) shl 56 or ((input[offset++].toLong() and 0xFFL) shl 48
                    ) or ((input[offset++].toLong() and 0xFFL) shl 40) or ((input[offset++].toLong() and 0xFFL) shl 32
                    ) or ((input[offset++].toLong() and 0xFFL) shl 24) or ((input[offset++].toLong() and 0xFFL) shl 16
                    ) or ((input[offset++].toLong() and 0xFFL) shl 8) or (input[offset++].toLong() and 0xFFL))
        n5 =
            ((input[offset++].toLong() and 0xFFL) shl 56 or ((input[offset++].toLong() and 0xFFL) shl 48
                    ) or ((input[offset++].toLong() and 0xFFL) shl 40) or ((input[offset++].toLong() and 0xFFL) shl 32
                    ) or ((input[offset++].toLong() and 0xFFL) shl 24) or ((input[offset++].toLong() and 0xFFL) shl 16
                    ) or ((input[offset++].toLong() and 0xFFL) shl 8) or (input[offset++].toLong() and 0xFFL))
        n6 =
            ((input[offset++].toLong() and 0xFFL) shl 56 or ((input[offset++].toLong() and 0xFFL) shl 48
                    ) or ((input[offset++].toLong() and 0xFFL) shl 40) or ((input[offset++].toLong() and 0xFFL) shl 32
                    ) or ((input[offset++].toLong() and 0xFFL) shl 24) or ((input[offset++].toLong() and 0xFFL) shl 16
                    ) or ((input[offset++].toLong() and 0xFFL) shl 8) or (input[offset++].toLong() and 0xFFL))
        n7 =
            ((input[offset++].toLong() and 0xFFL) shl 56 or ((input[offset++].toLong() and 0xFFL) shl 48
                    ) or ((input[offset++].toLong() and 0xFFL) shl 40) or ((input[offset++].toLong() and 0xFFL) shl 32
                    ) or ((input[offset++].toLong() and 0xFFL) shl 24) or ((input[offset++].toLong() and 0xFFL) shl 16
                    ) or ((input[offset++].toLong() and 0xFFL) shl 8) or (input[offset++].toLong() and 0xFFL))

        // transform K into the key schedule Kr; 0 <= r <= R
        k00 = H0
        k01 = H1
        k02 = H2
        k03 = H3
        k04 = H4
        k05 = H5
        k06 = H6
        k07 = H7

        nn0 = n0 xor k00
        nn1 = n1 xor k01
        nn2 = n2 xor k02
        nn3 = n3 xor k03
        nn4 = n4 xor k04
        nn5 = n5 xor k05
        nn6 = n6 xor k06
        nn7 = n7 xor k07

        // intermediate cipher output
        w7 = 0L
        w6 = w7
        w5 = w6
        w4 = w5
        w3 = w4
        w2 = w3
        w1 = w2
        w0 = w1

        for (r in 0..<R) {
            // 1. compute intermediate round key schedule by applying ro[rc]
            // to the previous round key schedule --rc being the round constant
            Kr0 = (T0[((k00 shr 56) and 0xFFL).toInt()]
                    xor T1[((k07 shr 48) and 0xFFL).toInt()]
                    xor T2[((k06 shr 40) and 0xFFL).toInt()]
                    xor T3[((k05 shr 32) and 0xFFL).toInt()]
                    xor T4[((k04 shr 24) and 0xFFL).toInt()]
                    xor T5[((k03 shr 16) and 0xFFL).toInt()]
                    xor T6[((k02 shr 8) and 0xFFL).toInt()] xor T7[(k01 and 0xFFL).toInt()]
                    xor rc[r])
            Kr1 = (T0[((k01 shr 56) and 0xFFL).toInt()]
                    xor T1[((k00 shr 48) and 0xFFL).toInt()]
                    xor T2[((k07 shr 40) and 0xFFL).toInt()]
                    xor T3[((k06 shr 32) and 0xFFL).toInt()]
                    xor T4[((k05 shr 24) and 0xFFL).toInt()]
                    xor T5[((k04 shr 16) and 0xFFL).toInt()]
                    xor T6[((k03 shr 8) and 0xFFL).toInt()] xor T7[(k02 and 0xFFL).toInt()])
            Kr2 = (T0[((k02 shr 56) and 0xFFL).toInt()]
                    xor T1[((k01 shr 48) and 0xFFL).toInt()]
                    xor T2[((k00 shr 40) and 0xFFL).toInt()]
                    xor T3[((k07 shr 32) and 0xFFL).toInt()]
                    xor T4[((k06 shr 24) and 0xFFL).toInt()]
                    xor T5[((k05 shr 16) and 0xFFL).toInt()]
                    xor T6[((k04 shr 8) and 0xFFL).toInt()] xor T7[(k03 and 0xFFL).toInt()])
            Kr3 = (T0[((k03 shr 56) and 0xFFL).toInt()]
                    xor T1[((k02 shr 48) and 0xFFL).toInt()]
                    xor T2[((k01 shr 40) and 0xFFL).toInt()]
                    xor T3[((k00 shr 32) and 0xFFL).toInt()]
                    xor T4[((k07 shr 24) and 0xFFL).toInt()]
                    xor T5[((k06 shr 16) and 0xFFL).toInt()]
                    xor T6[((k05 shr 8) and 0xFFL).toInt()] xor T7[(k04 and 0xFFL).toInt()])
            Kr4 = (T0[((k04 shr 56) and 0xFFL).toInt()]
                    xor T1[((k03 shr 48) and 0xFFL).toInt()]
                    xor T2[((k02 shr 40) and 0xFFL).toInt()]
                    xor T3[((k01 shr 32) and 0xFFL).toInt()]
                    xor T4[((k00 shr 24) and 0xFFL).toInt()]
                    xor T5[((k07 shr 16) and 0xFFL).toInt()]
                    xor T6[((k06 shr 8) and 0xFFL).toInt()] xor T7[(k05 and 0xFFL).toInt()])
            Kr5 = (T0[((k05 shr 56) and 0xFFL).toInt()]
                    xor T1[((k04 shr 48) and 0xFFL).toInt()]
                    xor T2[((k03 shr 40) and 0xFFL).toInt()]
                    xor T3[((k02 shr 32) and 0xFFL).toInt()]
                    xor T4[((k01 shr 24) and 0xFFL).toInt()]
                    xor T5[((k00 shr 16) and 0xFFL).toInt()]
                    xor T6[((k07 shr 8) and 0xFFL).toInt()] xor T7[(k06 and 0xFFL).toInt()])
            Kr6 = (T0[((k06 shr 56) and 0xFFL).toInt()]
                    xor T1[((k05 shr 48) and 0xFFL).toInt()]
                    xor T2[((k04 shr 40) and 0xFFL).toInt()]
                    xor T3[((k03 shr 32) and 0xFFL).toInt()]
                    xor T4[((k02 shr 24) and 0xFFL).toInt()]
                    xor T5[((k01 shr 16) and 0xFFL).toInt()]
                    xor T6[((k00 shr 8) and 0xFFL).toInt()] xor T7[(k07 and 0xFFL).toInt()])
            Kr7 = (T0[((k07 shr 56) and 0xFFL).toInt()]
                    xor T1[((k06 shr 48) and 0xFFL).toInt()]
                    xor T2[((k05 shr 40) and 0xFFL).toInt()]
                    xor T3[((k04 shr 32) and 0xFFL).toInt()]
                    xor T4[((k03 shr 24) and 0xFFL).toInt()]
                    xor T5[((k02 shr 16) and 0xFFL).toInt()]
                    xor T6[((k01 shr 8) and 0xFFL).toInt()] xor T7[(k00 and 0xFFL).toInt()])

            k00 = Kr0
            k01 = Kr1
            k02 = Kr2
            k03 = Kr3
            k04 = Kr4
            k05 = Kr5
            k06 = Kr6
            k07 = Kr7

            // 2. incrementally compute the cipher output
            w0 = (T0[((nn0 shr 56) and 0xFFL).toInt()]
                    xor T1[((nn7 shr 48) and 0xFFL).toInt()]
                    xor T2[((nn6 shr 40) and 0xFFL).toInt()]
                    xor T3[((nn5 shr 32) and 0xFFL).toInt()]
                    xor T4[((nn4 shr 24) and 0xFFL).toInt()]
                    xor T5[((nn3 shr 16) and 0xFFL).toInt()]
                    xor T6[((nn2 shr 8) and 0xFFL).toInt()] xor T7[(nn1 and 0xFFL).toInt()]
                    xor Kr0)
            w1 = (T0[((nn1 shr 56) and 0xFFL).toInt()]
                    xor T1[((nn0 shr 48) and 0xFFL).toInt()]
                    xor T2[((nn7 shr 40) and 0xFFL).toInt()]
                    xor T3[((nn6 shr 32) and 0xFFL).toInt()]
                    xor T4[((nn5 shr 24) and 0xFFL).toInt()]
                    xor T5[((nn4 shr 16) and 0xFFL).toInt()]
                    xor T6[((nn3 shr 8) and 0xFFL).toInt()] xor T7[(nn2 and 0xFFL).toInt()]
                    xor Kr1)
            w2 = (T0[((nn2 shr 56) and 0xFFL).toInt()]
                    xor T1[((nn1 shr 48) and 0xFFL).toInt()]
                    xor T2[((nn0 shr 40) and 0xFFL).toInt()]
                    xor T3[((nn7 shr 32) and 0xFFL).toInt()]
                    xor T4[((nn6 shr 24) and 0xFFL).toInt()]
                    xor T5[((nn5 shr 16) and 0xFFL).toInt()]
                    xor T6[((nn4 shr 8) and 0xFFL).toInt()] xor T7[(nn3 and 0xFFL).toInt()]
                    xor Kr2)
            w3 = (T0[((nn3 shr 56) and 0xFFL).toInt()]
                    xor T1[((nn2 shr 48) and 0xFFL).toInt()]
                    xor T2[((nn1 shr 40) and 0xFFL).toInt()]
                    xor T3[((nn0 shr 32) and 0xFFL).toInt()]
                    xor T4[((nn7 shr 24) and 0xFFL).toInt()]
                    xor T5[((nn6 shr 16) and 0xFFL).toInt()]
                    xor T6[((nn5 shr 8) and 0xFFL).toInt()] xor T7[(nn4 and 0xFFL).toInt()]
                    xor Kr3)
            w4 = (T0[((nn4 shr 56) and 0xFFL).toInt()]
                    xor T1[((nn3 shr 48) and 0xFFL).toInt()]
                    xor T2[((nn2 shr 40) and 0xFFL).toInt()]
                    xor T3[((nn1 shr 32) and 0xFFL).toInt()]
                    xor T4[((nn0 shr 24) and 0xFFL).toInt()]
                    xor T5[((nn7 shr 16) and 0xFFL).toInt()]
                    xor T6[((nn6 shr 8) and 0xFFL).toInt()] xor T7[(nn5 and 0xFFL).toInt()]
                    xor Kr4)
            w5 = (T0[((nn5 shr 56) and 0xFFL).toInt()]
                    xor T1[((nn4 shr 48) and 0xFFL).toInt()]
                    xor T2[((nn3 shr 40) and 0xFFL).toInt()]
                    xor T3[((nn2 shr 32) and 0xFFL).toInt()]
                    xor T4[((nn1 shr 24) and 0xFFL).toInt()]
                    xor T5[((nn0 shr 16) and 0xFFL).toInt()]
                    xor T6[((nn7 shr 8) and 0xFFL).toInt()] xor T7[(nn6 and 0xFFL).toInt()]
                    xor Kr5)
            w6 = (T0[((nn6 shr 56) and 0xFFL).toInt()]
                    xor T1[((nn5 shr 48) and 0xFFL).toInt()]
                    xor T2[((nn4 shr 40) and 0xFFL).toInt()]
                    xor T3[((nn3 shr 32) and 0xFFL).toInt()]
                    xor T4[((nn2 shr 24) and 0xFFL).toInt()]
                    xor T5[((nn1 shr 16) and 0xFFL).toInt()]
                    xor T6[((nn0 shr 8) and 0xFFL).toInt()] xor T7[(nn7 and 0xFFL).toInt()]
                    xor Kr6)
            w7 = (T0[((nn7 shr 56) and 0xFFL).toInt()]
                    xor T1[((nn6 shr 48) and 0xFFL).toInt()]
                    xor T2[((nn5 shr 40) and 0xFFL).toInt()]
                    xor T3[((nn4 shr 32) and 0xFFL).toInt()]
                    xor T4[((nn3 shr 24) and 0xFFL).toInt()]
                    xor T5[((nn2 shr 16) and 0xFFL).toInt()]
                    xor T6[((nn1 shr 8) and 0xFFL).toInt()] xor T7[(nn0 and 0xFFL).toInt()]
                    xor Kr7)

            nn0 = w0
            nn1 = w1
            nn2 = w2
            nn3 = w3
            nn4 = w4
            nn5 = w5
            nn6 = w6
            nn7 = w7
        }

        // apply the Miyaguchi-Preneel hash scheme
        H0 = H0 xor (w0 xor n0)
        H1 = H1 xor (w1 xor n1)
        H2 = H2 xor (w2 xor n2)
        H3 = H3 xor (w3 xor n3)
        H4 = H4 xor (w4 xor n4)
        H5 = H5 xor (w5 xor n5)
        H6 = H6 xor (w6 xor n6)
        H7 = H7 xor (w7 xor n7)
    }

    override fun padBuffer(): ByteArray {
        // [WHIRLPOOL] p. 6:
        // "...padded with a 1-bit, then with as few 0-bits as necessary to
        // obtain a bit string whose length is an odd multiple of 256, and
        // finally with the 256-bit right-justified binary representation of L."
        // in this implementation we use 'count' as the number of bytes hashed
        // so far. hence the minimal number of bytes added to the message proper
        // are 33 (1 for the 1-bit followed by the 0-bits and the encoding of
        // the count framed in a 256-bit block). our formula is then:
        // count + 33 + padding = 0 (mod BLOCK_SIZE)
        val n = ((count + 33) % BLOCK_SIZE).toInt()
        val padding = if (n == 0) 33 else BLOCK_SIZE - n + 33

        val result = ByteArray(padding)

        // padding is always binary 1 followed by binary 0s
        result[0] = 0x80.toByte()

        // save (right justified) the number of bits hashed
        val bits = count * 8
        var i = padding - 8
        result[i++] = (bits ushr 56).toByte()
        result[i++] = (bits ushr 48).toByte()
        result[i++] = (bits ushr 40).toByte()
        result[i++] = (bits ushr 32).toByte()
        result[i++] = (bits ushr 24).toByte()
        result[i++] = (bits ushr 16).toByte()
        result[i++] = (bits ushr 8).toByte()
        result[i] = bits.toByte()

        return result
    }

    override val result: ByteArray
        get() {
            // apply inverse mu to the context
            val result = byteArrayOf(
                (H0 ushr 56).toByte(), (H0 ushr 48).toByte(),
                (H0 ushr 40).toByte(), (H0 ushr 32).toByte(), (H0 ushr 24).toByte(),
                (H0 ushr 16).toByte(), (H0 ushr 8).toByte(), H0.toByte(),
                (H1 ushr 56).toByte(), (H1 ushr 48).toByte(), (H1 ushr 40).toByte(),
                (H1 ushr 32).toByte(), (H1 ushr 24).toByte(), (H1 ushr 16).toByte(),
                (H1 ushr 8).toByte(), H1.toByte(), (H2 ushr 56).toByte(),
                (H2 ushr 48).toByte(), (H2 ushr 40).toByte(), (H2 ushr 32).toByte(),
                (H2 ushr 24).toByte(), (H2 ushr 16).toByte(), (H2 ushr 8).toByte(),
                H2.toByte(), (H3 ushr 56).toByte(), (H3 ushr 48).toByte(),
                (H3 ushr 40).toByte(), (H3 ushr 32).toByte(), (H3 ushr 24).toByte(),
                (H3 ushr 16).toByte(), (H3 ushr 8).toByte(), H3.toByte(),
                (H4 ushr 56).toByte(), (H4 ushr 48).toByte(), (H4 ushr 40).toByte(),
                (H4 ushr 32).toByte(), (H4 ushr 24).toByte(), (H4 ushr 16).toByte(),
                (H4 ushr 8).toByte(), H4.toByte(), (H5 ushr 56).toByte(),
                (H5 ushr 48).toByte(), (H5 ushr 40).toByte(), (H5 ushr 32).toByte(),
                (H5 ushr 24).toByte(), (H5 ushr 16).toByte(), (H5 ushr 8).toByte(),
                H5.toByte(), (H6 ushr 56).toByte(), (H6 ushr 48).toByte(),
                (H6 ushr 40).toByte(), (H6 ushr 32).toByte(), (H6 ushr 24).toByte(),
                (H6 ushr 16).toByte(), (H6 ushr 8).toByte(), H6.toByte(),
                (H7 ushr 56).toByte(), (H7 ushr 48).toByte(), (H7 ushr 40).toByte(),
                (H7 ushr 32).toByte(), (H7 ushr 24).toByte(), (H7 ushr 16).toByte(),
                (H7 ushr 8).toByte(), H7.toByte()
            )

            return result
        }

    override fun resetContext() {
        H7 = 0L
        H6 = H7
        H5 = H6
        H4 = H5
        H3 = H4
        H2 = H3
        H1 = H2
        H0 = H1
    }

    public override fun selfTest(): Boolean {
        if (valid == null) valid = DIGEST0 == UtilServices
            .toString(Whirlpool().digest()!!)

        return valid!!
    }

    companion object {
        // Constants and variables
        // -------------------------------------------------------------------------
        private const val BLOCK_SIZE = 64 // inner block size in bytes

        /**
         * The digest of the 0-bit long message.
         */
        private val DIGEST0 = ("19FA61D75522A4669B44E39C1D2E1726C530232130D407F89AFEE0964997F7A7"
                + "3E83BE698B288FEBCF88E3E03C4F0757EA8964E59B63D93708B138CC42A66EB3")

        /**
         * Default number of rounds.
         */
        private const val R = 10

        /**
         * Whirlpool S-box; p. 19.
         */
        private val S_box =  // p. 19 [WHIRLPOOL]
            ("\u1823\uc6E8\u87B8\u014F\u36A6\ud2F5\u796F\u9152"
                    + "\u60Bc\u9B8E\uA30c\u7B35\u1dE0\ud7c2\u2E4B\uFE57"
                    + "\u1577\u37E5\u9FF0\u4AdA\u58c9\u290A\uB1A0\u6B85"
                    + "\uBd5d\u10F4\ucB3E\u0567\uE427\u418B\uA77d\u95d8"
                    + "\uFBEE\u7c66\udd17\u479E\ucA2d\uBF07\uAd5A\u8333"
                    + "\u6302\uAA71\uc819\u49d9\uF2E3\u5B88\u9A26\u32B0"
                    + "\uE90F\ud580\uBEcd\u3448\uFF7A\u905F\u2068\u1AAE"
                    + "\uB454\u9322\u64F1\u7312\u4008\uc3Ec\udBA1\u8d3d"
                    + "\u9700\ucF2B\u7682\ud61B\uB5AF\u6A50\u45F3\u30EF"
                    + "\u3F55\uA2EA\u65BA\u2Fc0\udE1c\uFd4d\u9275\u068A"
                    + "\uB2E6\u0E1F\u62d4\uA896\uF9c5\u2559\u8472\u394c"
                    + "\u5E78\u388c\ud1A5\uE261\uB321\u9c1E\u43c7\uFc04"
                    + "\u5199\u6d0d\uFAdF\u7E24\u3BAB\ucE11\u8F4E\uB7EB"
                    + "\u3c81\u94F7\uB913\u2cd3\uE76E\uc403\u5644\u7FA9"
                    + "\u2ABB\uc153\udc0B\u9d6c\u3174\uF646\uAc89\u14E1"
                    + "\u163A\u6909\u70B6\ud0Ed\ucc42\u98A4\u285c\uF886")

        /**
         * The 64-bit lookup tables; section 7.1 p. 13.
         */
        private val T0 = LongArray(256)
        private val T1 = LongArray(256)
        private val T2 = LongArray(256)
        private val T3 = LongArray(256)
        private val T4 = LongArray(256)
        private val T5 = LongArray(256)
        private val T6 = LongArray(256)
        private val T7 = LongArray(256)

        /**
         * The round constants.
         */
        private val rc = LongArray(R)

        /**
         * caches the result of the correctness test, once executed.
         */
        private var valid: Boolean? = null

        // Static code - to intialise lookup tables --------------------------------
        init {
            var time = System.currentTimeMillis()

            val ROOT = 0x11D // para. 2.1 [WHIRLPOOL]
            var i: Int
            var r: Int
            var s1: Long
            var s2: Long
            var s4: Long
            var s5: Long
            var s8: Long
            var s9: Long
            var t: Long
            var c: Char
            i = 0
            while (i < 256) {
                c = S_box.get(i ushr 1)

                s1 = (if ((i and 1) == 0) c.code ushr 8 else c.code).toLong() and 0xFFL
                s2 = s1 shl 1
                if (s2 > 0xFFL) s2 = s2 xor ROOT.toLong()

                s4 = s2 shl 1
                if (s4 > 0xFFL) s4 = s4 xor ROOT.toLong()

                s5 = s4 xor s1
                s8 = s4 shl 1
                if (s8 > 0xFFL) s8 = s8 xor ROOT.toLong()

                s9 = s8 xor s1

                t = (s1 shl 56 or (s1 shl 48) or (s4 shl 40) or (s1 shl 32) or (s8 shl 24
                        ) or (s5 shl 16) or (s2 shl 8) or s9)
                T0[i] = t
                T1[i] = t ushr 8 or (t shl 56)
                T2[i] = t ushr 16 or (t shl 48)
                T3[i] = t ushr 24 or (t shl 40)
                T4[i] = t ushr 32 or (t shl 32)
                T5[i] = t ushr 40 or (t shl 24)
                T6[i] = t ushr 48 or (t shl 16)
                T7[i] = t ushr 56 or (t shl 8)
                i++
            }

            r = 0
            i = 0
            while (r < R) {
                rc[r++] = ((T0[i++] and -0x100000000000000L)
                        xor (T1[i++] and 0x00FF000000000000L)
                        xor (T2[i++] and 0x0000FF0000000000L)
                        xor (T3[i++] and 0x000000FF00000000L)
                        xor (T4[i++] and 0x00000000FF000000L)
                        xor (T5[i++] and 0x0000000000FF0000L)
                        xor (T6[i++] and 0x000000000000FF00L)
                        xor (T7[i++] and 0x00000000000000FFL))
            }

            time = System.currentTimeMillis() - time
        }
    }
}