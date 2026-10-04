package com.example.rehashdroid.logic

import com.example.rehashdroid.logic.UtilServices.toString

/**
 *
 *
 * The *HAVAL* message-digest algorithm is a variable output length, with
 * variable number of rounds. By default, this implementation allows
 * *HAVAL* to be used as a drop-in replacement for *MD5*.
 *
 *
 *
 *
 * References:
 *
 *
 *
 *  1. HAVAL - A One-Way Hashing Algorithm with Variable Length of Output<br></br>
 * Advances in Cryptology - AUSCRYPT'92, Lecture Notes in Computer Science,<br></br>
 * Springer-Verlag, 1993; <br></br>
 * Y. Zheng, J. Pieprzyk and J. Seberry.
 *
 */
class Haval private constructor(size: Int, rounds: Int) : BaseHash("haval", size, BLOCK_SIZE) {
    /**
     * Number of HAVAL rounds. Allowed values are integers in the range `3
     * .. 5`. The default is `3`.
     */
    private var rounds = HAVAL_3_ROUND

    /**
     * 128-bit interim result.
     */
    private var h0 = 0
    private var h1 = 0
    private var h2 = 0
    private var h3 = 0
    private var h4 = 0
    private var h5 = 0
    private var h6 = 0
    private var h7 = 0
    private var t0 = 0
    private var t1 = 0
    private var t2 = 0
    private var t3 = 0
    private var t4 = 0
    private var t5 = 0
    private var t6 = 0
    private var t7 = 0
    private var X0 = 0
    private var X1 = 0
    private var X2 = 0
    private var X3 = 0
    private var X4 = 0
    private var X5 = 0
    private var X6 = 0
    private var X7 = 0
    private var X8 = 0
    private var X9 = 0
    private var X10 = 0
    private var X11 = 0
    private var X12 = 0
    private var X13 = 0
    private var X14 = 0
    private var X15 = 0
    private var X16 = 0
    private var X17 = 0
    private var X18 = 0
    private var X19 = 0
    private var X20 = 0
    private var X21 = 0
    private var X22 = 0
    private var X23 = 0
    private var X24 = 0
    private var X25 = 0
    private var X26 = 0
    private var X27 = 0
    private var X28 = 0
    private var X29 = 0
    private var X30 = 0
    private var X31 = 0

    // Constructor(s)
    // -------------------------------------------------------------------------
    /**
     *
     *
     * Calls the constructor with two argument using [.HAVAL_128_BIT] as
     * the value for the output size (i.e. `128` bits, and
     * [.HAVAL_3_ROUND] for the value of number of rounds.
     *
     */
    constructor() : this(HAVAL_128_BIT, HAVAL_3_ROUND)

    /**
     *
     *
     * Calls the constructor with two arguments using the designated output
     * size, and [.HAVAL_3_ROUND] for the value of number of rounds.
     *
     *
     * @param size the output size in bytes of this instance.
     * @throws IllegalArgumentException if the designated output size is invalid.
     * @see .HAVAL_128_BIT
     *
     * @see .HAVAL_160_BIT
     *
     * @see .HAVAL_192_BIT
     *
     * @see .HAVAL_224_BIT
     *
     * @see .HAVAL_256_BIT
     */
    constructor(size: Int) : this(size, HAVAL_3_ROUND)

    /**
     *
     *
     * Constructs a `Haval` instance with the designated output size
     * (in bytes). Valid output `size` values are `16`,
     * `20`, `24`, `28` and `32`.
     * Valid values for `rounds` are in the range `3..5`
     * inclusive.
     *
     *
     * @param size   the output size in bytes of this instance.
     * @param rounds the number of rounds to apply when transforming data.
     * @throws IllegalArgumentException if the designated output size is invalid, or if the number of
     * rounds is invalid.
     * @see .HAVAL_128_BIT
     *
     * @see .HAVAL_160_BIT
     *
     * @see .HAVAL_192_BIT
     *
     * @see .HAVAL_224_BIT
     *
     * @see .HAVAL_256_BIT
     *
     * @see .HAVAL_3_ROUND
     *
     * @see .HAVAL_4_ROUND
     *
     * @see .HAVAL_5_ROUND
     */
    init {
        require(!(size != HAVAL_128_BIT && size != HAVAL_160_BIT && size != HAVAL_192_BIT && size != HAVAL_224_BIT && size != HAVAL_256_BIT)) { "Invalid HAVAL output size" }

        require(!(rounds != HAVAL_3_ROUND && rounds != HAVAL_4_ROUND && rounds != HAVAL_5_ROUND)) { "Invalid HAVAL number of rounds" }

        this.rounds = rounds
    }

    /**
     *
     *
     * Private constructor for cloning purposes.
     *
     *
     * @param md the instance to clone.
     */
    private constructor(md: Haval) : this(md.hashSize, md.rounds) {
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

    // Constructor(s)
    // -------------------------------------------------------------------------
    // Class methods
    // -------------------------------------------------------------------------
    // Instance methods
    // -------------------------------------------------------------------------
    // java.lang.Cloneable interface implementation ----------------------------
    override fun clone(): Any {
        return Haval(this)
    }

    // Implementation of concrete methods in BaseHash --------------------------
    @Synchronized
    override fun transform(`in`: ByteArray, i: Int) {
        var i = i
        X0 =
            ((`in`[i++].toInt() and 0xFF) or ((`in`[i++].toInt() and 0xFF) shl 8) or ((`in`[i++].toInt() and 0xFF) shl 16
                    ) or ((`in`[i++].toInt() and 0xFF) shl 24))
        X1 =
            ((`in`[i++].toInt() and 0xFF) or ((`in`[i++].toInt() and 0xFF) shl 8) or ((`in`[i++].toInt() and 0xFF) shl 16
                    ) or ((`in`[i++].toInt() and 0xFF) shl 24))
        X2 =
            ((`in`[i++].toInt() and 0xFF) or ((`in`[i++].toInt() and 0xFF) shl 8) or ((`in`[i++].toInt() and 0xFF) shl 16
                    ) or ((`in`[i++].toInt() and 0xFF) shl 24))
        X3 =
            ((`in`[i++].toInt() and 0xFF) or ((`in`[i++].toInt() and 0xFF) shl 8) or ((`in`[i++].toInt() and 0xFF) shl 16
                    ) or ((`in`[i++].toInt() and 0xFF) shl 24))
        X4 =
            ((`in`[i++].toInt() and 0xFF) or ((`in`[i++].toInt() and 0xFF) shl 8) or ((`in`[i++].toInt() and 0xFF) shl 16
                    ) or ((`in`[i++].toInt() and 0xFF) shl 24))
        X5 =
            ((`in`[i++].toInt() and 0xFF) or ((`in`[i++].toInt() and 0xFF) shl 8) or ((`in`[i++].toInt() and 0xFF) shl 16
                    ) or ((`in`[i++].toInt() and 0xFF) shl 24))
        X6 =
            ((`in`[i++].toInt() and 0xFF) or ((`in`[i++].toInt() and 0xFF) shl 8) or ((`in`[i++].toInt() and 0xFF) shl 16
                    ) or ((`in`[i++].toInt() and 0xFF) shl 24))
        X7 =
            ((`in`[i++].toInt() and 0xFF) or ((`in`[i++].toInt() and 0xFF) shl 8) or ((`in`[i++].toInt() and 0xFF) shl 16
                    ) or ((`in`[i++].toInt() and 0xFF) shl 24))
        X8 =
            ((`in`[i++].toInt() and 0xFF) or ((`in`[i++].toInt() and 0xFF) shl 8) or ((`in`[i++].toInt() and 0xFF) shl 16
                    ) or ((`in`[i++].toInt() and 0xFF) shl 24))
        X9 =
            ((`in`[i++].toInt() and 0xFF) or ((`in`[i++].toInt() and 0xFF) shl 8) or ((`in`[i++].toInt() and 0xFF) shl 16
                    ) or ((`in`[i++].toInt() and 0xFF) shl 24))
        X10 =
            ((`in`[i++].toInt() and 0xFF) or ((`in`[i++].toInt() and 0xFF) shl 8) or ((`in`[i++].toInt() and 0xFF) shl 16
                    ) or ((`in`[i++].toInt() and 0xFF) shl 24))
        X11 =
            ((`in`[i++].toInt() and 0xFF) or ((`in`[i++].toInt() and 0xFF) shl 8) or ((`in`[i++].toInt() and 0xFF) shl 16
                    ) or ((`in`[i++].toInt() and 0xFF) shl 24))
        X12 =
            ((`in`[i++].toInt() and 0xFF) or ((`in`[i++].toInt() and 0xFF) shl 8) or ((`in`[i++].toInt() and 0xFF) shl 16
                    ) or ((`in`[i++].toInt() and 0xFF) shl 24))
        X13 =
            ((`in`[i++].toInt() and 0xFF) or ((`in`[i++].toInt() and 0xFF) shl 8) or ((`in`[i++].toInt() and 0xFF) shl 16
                    ) or ((`in`[i++].toInt() and 0xFF) shl 24))
        X14 =
            ((`in`[i++].toInt() and 0xFF) or ((`in`[i++].toInt() and 0xFF) shl 8) or ((`in`[i++].toInt() and 0xFF) shl 16
                    ) or ((`in`[i++].toInt() and 0xFF) shl 24))
        X15 =
            ((`in`[i++].toInt() and 0xFF) or ((`in`[i++].toInt() and 0xFF) shl 8) or ((`in`[i++].toInt() and 0xFF) shl 16
                    ) or ((`in`[i++].toInt() and 0xFF) shl 24))
        X16 =
            ((`in`[i++].toInt() and 0xFF) or ((`in`[i++].toInt() and 0xFF) shl 8) or ((`in`[i++].toInt() and 0xFF) shl 16
                    ) or ((`in`[i++].toInt() and 0xFF) shl 24))
        X17 =
            ((`in`[i++].toInt() and 0xFF) or ((`in`[i++].toInt() and 0xFF) shl 8) or ((`in`[i++].toInt() and 0xFF) shl 16
                    ) or ((`in`[i++].toInt() and 0xFF) shl 24))
        X18 =
            ((`in`[i++].toInt() and 0xFF) or ((`in`[i++].toInt() and 0xFF) shl 8) or ((`in`[i++].toInt() and 0xFF) shl 16
                    ) or ((`in`[i++].toInt() and 0xFF) shl 24))
        X19 =
            ((`in`[i++].toInt() and 0xFF) or ((`in`[i++].toInt() and 0xFF) shl 8) or ((`in`[i++].toInt() and 0xFF) shl 16
                    ) or ((`in`[i++].toInt() and 0xFF) shl 24))
        X20 =
            ((`in`[i++].toInt() and 0xFF) or ((`in`[i++].toInt() and 0xFF) shl 8) or ((`in`[i++].toInt() and 0xFF) shl 16
                    ) or ((`in`[i++].toInt() and 0xFF) shl 24))
        X21 =
            ((`in`[i++].toInt() and 0xFF) or ((`in`[i++].toInt() and 0xFF) shl 8) or ((`in`[i++].toInt() and 0xFF) shl 16
                    ) or ((`in`[i++].toInt() and 0xFF) shl 24))
        X22 =
            ((`in`[i++].toInt() and 0xFF) or ((`in`[i++].toInt() and 0xFF) shl 8) or ((`in`[i++].toInt() and 0xFF) shl 16
                    ) or ((`in`[i++].toInt() and 0xFF) shl 24))
        X23 =
            ((`in`[i++].toInt() and 0xFF) or ((`in`[i++].toInt() and 0xFF) shl 8) or ((`in`[i++].toInt() and 0xFF) shl 16
                    ) or ((`in`[i++].toInt() and 0xFF) shl 24))
        X24 =
            ((`in`[i++].toInt() and 0xFF) or ((`in`[i++].toInt() and 0xFF) shl 8) or ((`in`[i++].toInt() and 0xFF) shl 16
                    ) or ((`in`[i++].toInt() and 0xFF) shl 24))
        X25 =
            ((`in`[i++].toInt() and 0xFF) or ((`in`[i++].toInt() and 0xFF) shl 8) or ((`in`[i++].toInt() and 0xFF) shl 16
                    ) or ((`in`[i++].toInt() and 0xFF) shl 24))
        X26 =
            ((`in`[i++].toInt() and 0xFF) or ((`in`[i++].toInt() and 0xFF) shl 8) or ((`in`[i++].toInt() and 0xFF) shl 16
                    ) or ((`in`[i++].toInt() and 0xFF) shl 24))
        X27 =
            ((`in`[i++].toInt() and 0xFF) or ((`in`[i++].toInt() and 0xFF) shl 8) or ((`in`[i++].toInt() and 0xFF) shl 16
                    ) or ((`in`[i++].toInt() and 0xFF) shl 24))
        X28 =
            ((`in`[i++].toInt() and 0xFF) or ((`in`[i++].toInt() and 0xFF) shl 8) or ((`in`[i++].toInt() and 0xFF) shl 16
                    ) or ((`in`[i++].toInt() and 0xFF) shl 24))
        X29 =
            ((`in`[i++].toInt() and 0xFF) or ((`in`[i++].toInt() and 0xFF) shl 8) or ((`in`[i++].toInt() and 0xFF) shl 16
                    ) or ((`in`[i++].toInt() and 0xFF) shl 24))
        X30 =
            ((`in`[i++].toInt() and 0xFF) or ((`in`[i++].toInt() and 0xFF) shl 8) or ((`in`[i++].toInt() and 0xFF) shl 16
                    ) or ((`in`[i++].toInt() and 0xFF) shl 24))
        X31 =
            ((`in`[i++].toInt() and 0xFF) or ((`in`[i++].toInt() and 0xFF) shl 8) or ((`in`[i++].toInt() and 0xFF) shl 16
                    ) or ((`in`[i++].toInt() and 0xFF) shl 24))

        t0 = h0
        t1 = h1
        t2 = h2
        t3 = h3
        t4 = h4
        t5 = h5
        t6 = h6
        t7 = h7

        // Pass 1
        Pass1()

        // Pass 2
        Pass2()

        // Pass 3
        Pass3()

        // Pass 4
        Pass4()

        h7 += t7
        h6 += t6
        h5 += t5
        h4 += t4
        h3 += t3
        h2 += t2
        h1 += t1
        h0 += t0
    }

    private fun Pass1() {
        t7 = FF1(t7, t6, t5, t4, t3, t2, t1, t0, X0)
        t6 = FF1(t6, t5, t4, t3, t2, t1, t0, t7, X1)
        t5 = FF1(t5, t4, t3, t2, t1, t0, t7, t6, X2)
        t4 = FF1(t4, t3, t2, t1, t0, t7, t6, t5, X3)
        t3 = FF1(t3, t2, t1, t0, t7, t6, t5, t4, X4)
        t2 = FF1(t2, t1, t0, t7, t6, t5, t4, t3, X5)
        t1 = FF1(t1, t0, t7, t6, t5, t4, t3, t2, X6)
        t0 = FF1(t0, t7, t6, t5, t4, t3, t2, t1, X7)

        t7 = FF1(t7, t6, t5, t4, t3, t2, t1, t0, X8)
        t6 = FF1(t6, t5, t4, t3, t2, t1, t0, t7, X9)
        t5 = FF1(t5, t4, t3, t2, t1, t0, t7, t6, X10)
        t4 = FF1(t4, t3, t2, t1, t0, t7, t6, t5, X11)
        t3 = FF1(t3, t2, t1, t0, t7, t6, t5, t4, X12)
        t2 = FF1(t2, t1, t0, t7, t6, t5, t4, t3, X13)
        t1 = FF1(t1, t0, t7, t6, t5, t4, t3, t2, X14)
        t0 = FF1(t0, t7, t6, t5, t4, t3, t2, t1, X15)

        t7 = FF1(t7, t6, t5, t4, t3, t2, t1, t0, X16)
        t6 = FF1(t6, t5, t4, t3, t2, t1, t0, t7, X17)
        t5 = FF1(t5, t4, t3, t2, t1, t0, t7, t6, X18)
        t4 = FF1(t4, t3, t2, t1, t0, t7, t6, t5, X19)
        t3 = FF1(t3, t2, t1, t0, t7, t6, t5, t4, X20)
        t2 = FF1(t2, t1, t0, t7, t6, t5, t4, t3, X21)
        t1 = FF1(t1, t0, t7, t6, t5, t4, t3, t2, X22)
        t0 = FF1(t0, t7, t6, t5, t4, t3, t2, t1, X23)

        t7 = FF1(t7, t6, t5, t4, t3, t2, t1, t0, X24)
        t6 = FF1(t6, t5, t4, t3, t2, t1, t0, t7, X25)
        t5 = FF1(t5, t4, t3, t2, t1, t0, t7, t6, X26)
        t4 = FF1(t4, t3, t2, t1, t0, t7, t6, t5, X27)
        t3 = FF1(t3, t2, t1, t0, t7, t6, t5, t4, X28)
        t2 = FF1(t2, t1, t0, t7, t6, t5, t4, t3, X29)
        t1 = FF1(t1, t0, t7, t6, t5, t4, t3, t2, X30)
        t0 = FF1(t0, t7, t6, t5, t4, t3, t2, t1, X31)
    }

    private fun Pass2() {
        t7 = FF2(t7, t6, t5, t4, t3, t2, t1, t0, X5, 0x452821E6)
        t6 = FF2(t6, t5, t4, t3, t2, t1, t0, t7, X14, 0x38D01377)
        t5 = FF2(t5, t4, t3, t2, t1, t0, t7, t6, X26, -0x41ab9931)
        t4 = FF2(t4, t3, t2, t1, t0, t7, t6, t5, X18, 0x34E90C6C)
        t3 = FF2(t3, t2, t1, t0, t7, t6, t5, t4, X11, -0x3f53d649)
        t2 = FF2(t2, t1, t0, t7, t6, t5, t4, t3, X28, -0x3683af23)
        t1 = FF2(t1, t0, t7, t6, t5, t4, t3, t2, X7, 0x3F84D5B5)
        t0 = FF2(t0, t7, t6, t5, t4, t3, t2, t1, X16, -0x4ab8f6e9)

        t7 = FF2(t7, t6, t5, t4, t3, t2, t1, t0, X0, -0x6de92a27)
        t6 = FF2(t6, t5, t4, t3, t2, t1, t0, t7, X23, -0x768604e5)
        t5 = FF2(t5, t4, t3, t2, t1, t0, t7, t6, X20, -0x2ecef45a)
        t4 = FF2(t4, t3, t2, t1, t0, t7, t6, t5, X22, -0x67204a54)
        t3 = FF2(t3, t2, t1, t0, t7, t6, t5, t4, X1, 0x2FFD72DB)
        t2 = FF2(t2, t1, t0, t7, t6, t5, t4, t3, X10, -0x2fe52049)
        t1 = FF2(t1, t0, t7, t6, t5, t4, t3, t2, X4, -0x471e5013)
        t0 = FF2(t0, t7, t6, t5, t4, t3, t2, t1, X8, 0x6A267E96)

        t7 = FF2(t7, t6, t5, t4, t3, t2, t1, t0, X30, -0x45836fbb)
        t6 = FF2(t6, t5, t4, t3, t2, t1, t0, t7, X3, -0xed38067)
        t5 = FF2(t5, t4, t3, t2, t1, t0, t7, t6, X21, 0x24A19947)
        t4 = FF2(t4, t3, t2, t1, t0, t7, t6, t5, X9, -0x4c6e9309)
        t3 = FF2(t3, t2, t1, t0, t7, t6, t5, t4, X17, 0x0801F2E2)
        t2 = FF2(t2, t1, t0, t7, t6, t5, t4, t3, X24, -0x7a7103ea)
        t1 = FF2(t1, t0, t7, t6, t5, t4, t3, t2, X29, 0x636920D8)
        t0 = FF2(t0, t7, t6, t5, t4, t3, t2, t1, X6, 0x71574E69)

        t7 = FF2(t7, t6, t5, t4, t3, t2, t1, t0, X19, -0x5ba7015d)
        t6 = FF2(t6, t5, t4, t3, t2, t1, t0, t7, X12, -0xb6cc282)
        t5 = FF2(t5, t4, t3, t2, t1, t0, t7, t6, X15, 0x0D95748F)
        t4 = FF2(t4, t3, t2, t1, t0, t7, t6, t5, X13, 0x728EB658)
        t3 = FF2(t3, t2, t1, t0, t7, t6, t5, t4, X2, 0x718BCD58)
        t2 = FF2(t2, t1, t0, t7, t6, t5, t4, t3, X25, -0x7deab512)
        t1 = FF2(t1, t0, t7, t6, t5, t4, t3, t2, X31, 0x7B54A41D)
        t0 = FF2(t0, t7, t6, t5, t4, t3, t2, t1, X27, -0x3da5a64b)
    }

    private fun Pass3() {
        t7 = FF3(t7, t6, t5, t4, t3, t2, t1, t0, X19, -0x63cf2ac7)
        t6 = FF3(t6, t5, t4, t3, t2, t1, t0, t7, X9, 0x2AF26013)
        t5 = FF3(t5, t4, t3, t2, t1, t0, t7, t6, X4, -0x3a2e4fdd)
        t4 = FF3(t4, t3, t2, t1, t0, t7, t6, t5, X20, 0x286085F0)
        t3 = FF3(t3, t2, t1, t0, t7, t6, t5, t4, X28, -0x35be86e8)
        t2 = FF3(t2, t1, t0, t7, t6, t5, t4, t3, X17, -0x4724c711)
        t1 = FF3(t1, t0, t7, t6, t5, t4, t3, t2, X8, -0x71862350)
        t0 = FF3(t0, t7, t6, t5, t4, t3, t2, t1, X22, 0x603A180E)

        t7 = FF3(t7, t6, t5, t4, t3, t2, t1, t0, X29, 0x6C9E0E8B)
        t6 = FF3(t6, t5, t4, t3, t2, t1, t0, t7, X14, -0x4fe175c2)
        t5 = FF3(t5, t4, t3, t2, t1, t0, t7, t6, X25, -0x28ea883f)
        t4 = FF3(t4, t3, t2, t1, t0, t7, t6, t5, X12, -0x42ceb4d9)
        t3 = FF3(t3, t2, t1, t0, t7, t6, t5, t4, X24, 0x78AF2FDA)
        t2 = FF3(t2, t1, t0, t7, t6, t5, t4, t3, X30, 0x55605C60)
        t1 = FF3(t1, t0, t7, t6, t5, t4, t3, t2, X16, -0x19aada0d)
        t0 = FF3(t0, t7, t6, t5, t4, t3, t2, t1, X26, -0x55aa546c)

        t7 = FF3(t7, t6, t5, t4, t3, t2, t1, t0, X31, 0x57489862)
        t6 = FF3(t6, t5, t4, t3, t2, t1, t0, t7, X15, 0x63E81440)
        t5 = FF3(t5, t4, t3, t2, t1, t0, t7, t6, X7, 0x55CA396A)
        t4 = FF3(t4, t3, t2, t1, t0, t7, t6, t5, X3, 0x2AAB10B6)
        t3 = FF3(t3, t2, t1, t0, t7, t6, t5, t4, X1, -0x4b33a3cc)
        t2 = FF3(t2, t1, t0, t7, t6, t5, t4, t3, X0, 0x1141E8CE)
        t1 = FF3(t1, t0, t7, t6, t5, t4, t3, t2, X18, -0x5eab7951)
        t0 = FF3(t0, t7, t6, t5, t4, t3, t2, t1, X27, 0x7C72E993)

        t7 = FF3(t7, t6, t5, t4, t3, t2, t1, t0, X13, -0x4c11ebef)
        t6 = FF3(t6, t5, t4, t3, t2, t1, t0, t7, X6, 0x636FBC2A)
        t5 = FF3(t5, t4, t3, t2, t1, t0, t7, t6, X21, 0x2BA9C55D)
        t4 = FF3(t4, t3, t2, t1, t0, t7, t6, t5, X10, 0x741831F6)
        t3 = FF3(t3, t2, t1, t0, t7, t6, t5, t4, X23, -0x31a3c1ea)
        t2 = FF3(t2, t1, t0, t7, t6, t5, t4, t3, X11, -0x64786ce2)
        t1 = FF3(t1, t0, t7, t6, t5, t4, t3, t2, X5, -0x502945cd)
        t0 = FF3(t0, t7, t6, t5, t4, t3, t2, t1, X2, 0x6C24CF5C)
    }

    private fun Pass4() {
        if (rounds >= 4) {
            t7 = FF4(t7, t6, t5, t4, t3, t2, t1, t0, X24, 0x7A325381)
            t6 = FF4(t6, t5, t4, t3, t2, t1, t0, t7, X4, 0x28958677)
            t5 = FF4(t5, t4, t3, t2, t1, t0, t7, t6, X0, 0x3B8F4898)
            t4 = FF4(t4, t3, t2, t1, t0, t7, t6, t5, X14, 0x6B4BB9AF)
            t3 = FF4(t3, t2, t1, t0, t7, t6, t5, t4, X2, -0x3b4017e5)
            t2 = FF4(t2, t1, t0, t7, t6, t5, t4, t3, X7, 0x66282193)
            t1 = FF4(t1, t0, t7, t6, t5, t4, t3, t2, X28, 0x61D809CC)
            t0 = FF4(t0, t7, t6, t5, t4, t3, t2, t1, X23, -0x4de566f)
            t7 = FF4(t7, t6, t5, t4, t3, t2, t1, t0, X26, 0x487CAC60)
            t6 = FF4(t6, t5, t4, t3, t2, t1, t0, t7, X6, 0x5DEC8032)
            t5 = FF4(t5, t4, t3, t2, t1, t0, t7, t6, X30, -0x107ba2a3)
            t4 = FF4(t4, t3, t2, t1, t0, t7, t6, t5, X20, -0x167a8a4f)
            t3 = FF4(t3, t2, t1, t0, t7, t6, t5, t4, X18, -0x23d9dcfe)
            t2 = FF4(t2, t1, t0, t7, t6, t5, t4, t3, X25, -0x149ae478)
            t1 = FF4(t1, t0, t7, t6, t5, t4, t3, t2, X19, 0x23893E81)
            t0 = FF4(t0, t7, t6, t5, t4, t3, t2, t1, X3, -0x2c69533b)

            t7 = FF4(t7, t6, t5, t4, t3, t2, t1, t0, X22, 0x0F6D6FF3)
            t6 = FF4(t6, t5, t4, t3, t2, t1, t0, t7, X11, -0x7c0bbdc7)
            t5 = FF4(t5, t4, t3, t2, t1, t0, t7, t6, X31, 0x2E0B4482)
            t4 = FF4(t4, t3, t2, t1, t0, t7, t6, t5, X21, -0x5b7bdffc)
            t3 = FF4(t3, t2, t1, t0, t7, t6, t5, t4, X8, 0x69C8F04A)
            t2 = FF4(t2, t1, t0, t7, t6, t5, t4, t3, X27, -0x61e064a2)
            t1 = FF4(t1, t0, t7, t6, t5, t4, t3, t2, X12, 0x21C66842)
            t0 = FF4(t0, t7, t6, t5, t4, t3, t2, t1, X9, -0x9169366)
            t7 = FF4(t7, t6, t5, t4, t3, t2, t1, t0, X1, 0x670C9C61)
            t6 = FF4(t6, t5, t4, t3, t2, t1, t0, t7, X29, -0x542c7710)
            t5 = FF4(t5, t4, t3, t2, t1, t0, t7, t6, X5, 0x6A51A0D2)
            t4 = FF4(t4, t3, t2, t1, t0, t7, t6, t5, X15, -0x27abd098)
            t3 = FF4(t3, t2, t1, t0, t7, t6, t5, t4, X17, -0x69f058d8)
            t2 = FF4(t2, t1, t0, t7, t6, t5, t4, t3, X10, -0x54aecc5d)
            t1 = FF4(t1, t0, t7, t6, t5, t4, t3, t2, X16, 0x6EEF0B6C)
            t0 = FF4(t0, t7, t6, t5, t4, t3, t2, t1, X13, 0x137A3BE4)

            // Pass 5
            Pass5()
        }
    }

    private fun Pass5() {
        if (rounds == 5) {
            t7 = FF5(t7, t6, t5, t4, t3, t2, t1, t0, X27, -0x45c40fb0)
            t6 = FF5(t6, t5, t4, t3, t2, t1, t0, t7, X3, 0x7EFB2A98)
            t5 = FF5(t5, t4, t3, t2, t1, t0, t7, t6, X21, -0x5e0e9ae3)
            t4 = FF5(t4, t3, t2, t1, t0, t7, t6, t5, X26, 0x39AF0176)
            t3 = FF5(t3, t2, t1, t0, t7, t6, t5, t4, X17, 0x66CA593E)
            t2 = FF5(t2, t1, t0, t7, t6, t5, t4, t3, X11, -0x7dbcf178)
            t1 = FF5(t1, t0, t7, t6, t5, t4, t3, t2, X20, -0x731179e7)
            t0 = FF5(t0, t7, t6, t5, t4, t3, t2, t1, X29, 0x456F9FB4)

            t7 = FF5(t7, t6, t5, t4, t3, t2, t1, t0, X19, 0x7D84A5C3)
            t6 = FF5(t6, t5, t4, t3, t2, t1, t0, t7, X0, 0x3B8B5EBE)
            t5 = FF5(t5, t4, t3, t2, t1, t0, t7, t6, X12, -0x1f908a28)
            t4 = FF5(t4, t3, t2, t1, t0, t7, t6, t5, X7, -0x7a3edf8d)
            t3 = FF5(t3, t2, t1, t0, t7, t6, t5, t4, X13, 0x401A449F)
            t2 = FF5(t2, t1, t0, t7, t6, t5, t4, t3, X8, 0x56C16AA6)
            t1 = FF5(t1, t0, t7, t6, t5, t4, t3, t2, X31, 0x4ED3AA62)
            t0 = FF5(t0, t7, t6, t5, t4, t3, t2, t1, X10, 0x363F7706)

            t7 = FF5(t7, t6, t5, t4, t3, t2, t1, t0, X5, 0x1BFEDF72)
            t6 = FF5(t6, t5, t4, t3, t2, t1, t0, t7, X9, 0x429B023D)
            t5 = FF5(t5, t4, t3, t2, t1, t0, t7, t6, X14, 0x37D0D724)
            t4 = FF5(t4, t3, t2, t1, t0, t7, t6, t5, X30, -0x2ff5edb8)
            t3 = FF5(t3, t2, t1, t0, t7, t6, t5, t4, X18, -0x24f0152d)
            t2 = FF5(t2, t1, t0, t7, t6, t5, t4, t3, X6, 0x49F1C09B)
            t1 = FF5(t1, t0, t7, t6, t5, t4, t3, t2, X28, 0x075372C9)
            t0 = FF5(t0, t7, t6, t5, t4, t3, t2, t1, X24, -0x7f66e485)

            t7 = FF5(t7, t6, t5, t4, t3, t2, t1, t0, X2, 0x25D479D8)
            t6 = FF5(t6, t5, t4, t3, t2, t1, t0, t7, X23, -0x9172109)
            t5 = FF5(t5, t4, t3, t2, t1, t0, t7, t6, X16, -0x1c01afe6)
            t4 = FF5(t4, t3, t2, t1, t0, t7, t6, t5, X22, -0x4986b3c5)
            t3 = FF5(t3, t2, t1, t0, t7, t6, t5, t4, X4, -0x68931f43)
            t2 = FF5(t2, t1, t0, t7, t6, t5, t4, t3, X1, 0x04C006BA)
            t1 = FF5(t1, t0, t7, t6, t5, t4, t3, t2, X25, -0x3e56b04a)
            t0 = FF5(t0, t7, t6, t5, t4, t3, t2, t1, X15, 0x409F60C4)
        }
    }

    override fun padBuffer(): ByteArray {
        // pad out to 118 mod 128. other 10 bytes have special use.
        val n = (count % BLOCK_SIZE).toInt()
        var padding = if (n < 118) (118 - n) else (246 - n)
        val result = ByteArray(padding + 10)
        result[0] = 0x01.toByte()

        // save the version number (LSB 3), the number of rounds (3 bits in the
        // middle), the fingerprint length (MSB 2 bits and next byte) and the
        // number of bits in the unpadded message.
        val bl = hashSize * 8
        result[padding++] =
            (((bl and 0x03) shl 6) or ((rounds and 0x07) shl 3) or (HAVAL_VERSION and 0x07)).toByte()
        result[padding++] = (bl ushr 2).toByte()

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
            tailorDigestBits() // tailor context for the designated output size
            // cast enough top context values into an array of hashSize bytes
            val result = ByteArray(hashSize)
            if (hashSize >= HAVAL_256_BIT) {
                result[31] = (h7 ushr 24).toByte()
                result[30] = (h7 ushr 16).toByte()
                result[29] = (h7 ushr 8).toByte()
                result[28] = h7.toByte()
            }
            if (hashSize >= HAVAL_224_BIT) {
                result[27] = (h6 ushr 24).toByte()
                result[26] = (h6 ushr 16).toByte()
                result[25] = (h6 ushr 8).toByte()
                result[24] = h6.toByte()
            }
            if (hashSize >= HAVAL_192_BIT) {
                result[23] = (h5 ushr 24).toByte()
                result[22] = (h5 ushr 16).toByte()
                result[21] = (h5 ushr 8).toByte()
                result[20] = h5.toByte()
            }
            if (hashSize >= HAVAL_160_BIT) {
                result[19] = (h4 ushr 24).toByte()
                result[18] = (h4 ushr 16).toByte()
                result[17] = (h4 ushr 8).toByte()
                result[16] = h4.toByte()
            }
            result[15] = (h3 ushr 24).toByte()
            result[14] = (h3 ushr 16).toByte()
            result[13] = (h3 ushr 8).toByte()
            result[12] = h3.toByte()
            result[11] = (h2 ushr 24).toByte()
            result[10] = (h2 ushr 16).toByte()
            result[9] = (h2 ushr 8).toByte()
            result[8] = h2.toByte()
            result[7] = (h1 ushr 24).toByte()
            result[6] = (h1 ushr 16).toByte()
            result[5] = (h1 ushr 8).toByte()
            result[4] = h1.toByte()
            result[3] = (h0 ushr 24).toByte()
            result[2] = (h0 ushr 16).toByte()
            result[1] = (h0 ushr 8).toByte()
            result[0] = h0.toByte()

            return result
        }

    override fun resetContext() {
        h0 = 0x243F6A88
        h1 = -0x7a5cf72d
        h2 = 0x13198A2E
        h3 = 0x03707344
        h4 = -0x5bf6c7de
        h5 = 0x299F31D0
        h6 = 0x082EFA98
        h7 = -0x13b19377
    }

    public override fun selfTest(): Boolean {
        if (valid == null) {
            valid = DIGEST0 == toString(Haval().digest())
        }
        return valid!!
    }

    // helper methods ----------------------------------------------------------
    /**
     * Tailors the last output.
     */
    private fun tailorDigestBits() {
        var t: Int
        when (hashSize) {
            HAVAL_128_BIT -> {
                t = ((h7 and 0x000000FF) or (h6 and -0x1000000) or (h5 and 0x00FF0000)
                        or (h4 and 0x0000FF00))
                h0 += t ushr 8 or (t shl 24)
                t = ((h7 and 0x0000FF00) or (h6 and 0x000000FF) or (h5 and -0x1000000)
                        or (h4 and 0x00FF0000))
                h1 += t ushr 16 or (t shl 16)
                t = ((h7 and 0x00FF0000) or (h6 and 0x0000FF00) or (h5 and 0x000000FF)
                        or (h4 and -0x1000000))
                h2 += t ushr 24 or (t shl 8)
                t = ((h7 and -0x1000000) or (h6 and 0x00FF0000) or (h5 and 0x0000FF00)
                        or (h4 and 0x000000FF))
                h3 += t
            }

            HAVAL_160_BIT -> {
                t = (h7 and 0x3F) or (h6 and (0x7F shl 25)) or (h5 and (0x3F shl 19))
                h0 += t ushr 19 or (t shl 13)
                t = (h7 and (0x3F shl 6)) or (h6 and 0x3F) or (h5 and (0x7F shl 25))
                h1 += t ushr 25 or (t shl 7)
                t = (h7 and (0x7F shl 12)) or (h6 and (0x3F shl 6)) or (h5 and 0x3F)
                h2 += t
                t = (h7 and (0x3F shl 19)) or (h6 and (0x7F shl 12)) or (h5 and (0x3F shl 6))
                h3 += (t ushr 6)
                t = (h7 and (0x7F shl 25)) or (h6 and (0x3F shl 19)) or (h5 and (0x7F shl 12))
                h4 += (t ushr 12)
            }

            HAVAL_192_BIT -> {
                t = (h7 and 0x1F) or (h6 and (0x3F shl 26))
                h0 += t ushr 26 or (t shl 6)
                t = (h7 and (0x1F shl 5)) or (h6 and 0x1F)
                h1 += t
                t = (h7 and (0x3F shl 10)) or (h6 and (0x1F shl 5))
                h2 += (t ushr 5)
                t = (h7 and (0x1F shl 16)) or (h6 and (0x3F shl 10))
                h3 += (t ushr 10)
                t = (h7 and (0x1F shl 21)) or (h6 and (0x1F shl 16))
                h4 += (t ushr 16)
                t = (h7 and (0x3F shl 26)) or (h6 and (0x1F shl 21))
                h5 += (t ushr 21)
            }

            HAVAL_224_BIT -> {
                h0 += ((h7 ushr 27) and 0x1F)
                h1 += ((h7 ushr 22) and 0x1F)
                h2 += ((h7 ushr 18) and 0x0F)
                h3 += ((h7 ushr 13) and 0x1F)
                h4 += ((h7 ushr 9) and 0x0F)
                h5 += ((h7 ushr 4) and 0x1F)
                h6 += (h7 and 0x0F)
            }
        }
    }

    /**
     * Permutations phi_{i,j}, i=3,4,5, j=1,...,i.
     *
     *
     * rounds = 3: 6 5 4 3 2 1 0 | | | | | | | (replaced by) phi_{3,1}: 1 0 3 5
     * 6 2 4 phi_{3,2}: 4 2 1 0 5 3 6 phi_{3,3}: 6 1 2 3 4 5 0
     *
     *
     * rounds = 4: 6 5 4 3 2 1 0 | | | | | | | (replaced by) phi_{4,1}: 2 6 1 4
     * 5 3 0 phi_{4,2}: 3 5 2 0 1 6 4 phi_{4,3}: 1 4 3 6 0 2 5 phi_{4,4}: 6 4 0
     * 5 2 1 3
     *
     *
     * rounds = 5: 6 5 4 3 2 1 0 | | | | | | | (replaced by) phi_{5,1}: 3 4 1 0
     * 5 2 6 phi_{5,2}: 6 2 1 0 3 4 5 phi_{5,3}: 2 6 0 4 3 1 5 phi_{5,4}: 1 5 3
     * 2 0 4 6 phi_{5,5}: 2 5 0 6 4 3 1
     */
    private fun FF1(
        x7: Int, x6: Int, x5: Int, x4: Int, x3: Int, x2: Int, x1: Int,
        x0: Int, w: Int
    ): Int {
        val t: Int
        when (rounds) {
            3 -> t = f1(x1, x0, x3, x5, x6, x2, x4)
            4 -> t = f1(x2, x6, x1, x4, x5, x3, x0)
            else -> t = f1(x3, x4, x1, x0, x5, x2, x6)
        }
        return (t ushr 7 or (t shl 25)) + (x7 ushr 11 or (x7 shl 21)) + w
    }

    private fun FF2(
        x7: Int, x6: Int, x5: Int, x4: Int, x3: Int, x2: Int, x1: Int,
        x0: Int, w: Int, c: Int
    ): Int {
        val t: Int
        when (rounds) {
            3 -> t = f2(x4, x2, x1, x0, x5, x3, x6)
            4 -> t = f2(x3, x5, x2, x0, x1, x6, x4)
            else -> t = f2(x6, x2, x1, x0, x3, x4, x5)
        }
        return (t ushr 7 or (t shl 25)) + (x7 ushr 11 or (x7 shl 21)) + w + c
    }

    private fun FF3(
        x7: Int, x6: Int, x5: Int, x4: Int, x3: Int, x2: Int, x1: Int,
        x0: Int, w: Int, c: Int
    ): Int {
        val t: Int
        when (rounds) {
            3 -> t = f3(x6, x1, x2, x3, x4, x5, x0)
            4 -> t = f3(x1, x4, x3, x6, x0, x2, x5)
            else -> t = f3(x2, x6, x0, x4, x3, x1, x5)
        }
        return (t ushr 7 or (t shl 25)) + (x7 ushr 11 or (x7 shl 21)) + w + c
    }

    private fun FF4(
        x7: Int, x6: Int, x5: Int, x4: Int, x3: Int, x2: Int, x1: Int,
        x0: Int, w: Int, c: Int
    ): Int {
        val t: Int
        when (rounds) {
            4 -> t = f4(x6, x4, x0, x5, x2, x1, x3)
            else -> t = f4(x1, x5, x3, x2, x0, x4, x6)
        }
        return (t ushr 7 or (t shl 25)) + (x7 ushr 11 or (x7 shl 21)) + w + c
    }

    private fun FF5(
        x7: Int, x6: Int, x5: Int, x4: Int, x3: Int, x2: Int, x1: Int,
        x0: Int, w: Int, c: Int
    ): Int {
        val t = f5(x2, x5, x0, x6, x4, x3, x1)
        return (t ushr 7 or (t shl 25)) + (x7 ushr 11 or (x7 shl 21)) + w + c
    }

    private fun f1(x6: Int, x5: Int, x4: Int, x3: Int, x2: Int, x1: Int, x0: Int): Int {
        return x1 and (x0 xor x4) xor (x2 and x5) xor (x3 and x6) xor x0
    }

    private fun f2(x6: Int, x5: Int, x4: Int, x3: Int, x2: Int, x1: Int, x0: Int): Int {
        return (x2 and (x1 and x3.inv() xor (x4 and x5) xor x6 xor x0) xor (x4 and (x1 xor x5)) xor (x3 and x5
                ) xor x0)
    }

    private fun f3(x6: Int, x5: Int, x4: Int, x3: Int, x2: Int, x1: Int, x0: Int): Int {
        return x3 and (x1 and x2 xor x6 xor x0) xor (x1 and x4) xor (x2 and x5) xor x0
    }

    private fun f4(x6: Int, x5: Int, x4: Int, x3: Int, x2: Int, x1: Int, x0: Int): Int {
        return x4 and (x5 and x2.inv() xor (x3 and x6.inv()) xor x1 xor x6 xor x0) xor (x3
                and (x1 and x2 xor x5 xor x6)) xor (x2 and x6) xor x0
    }

    private fun f5(x6: Int, x5: Int, x4: Int, x3: Int, x2: Int, x1: Int, x0: Int): Int {
        return x0 and (x1 and x2 and x3 xor x5.inv()) xor (x1 and x4) xor (x2 and x5) xor (x3 and x6)
    }

    companion object {
        // Constants and variables
        // -------------------------------------------------------------------------
        const val HAVAL_VERSION: Int = 1

        const val HAVAL_128_BIT: Int = 16

        const val HAVAL_160_BIT: Int = 20

        const val HAVAL_192_BIT: Int = 24

        const val HAVAL_224_BIT: Int = 28

        const val HAVAL_256_BIT: Int = 32

        const val HAVAL_3_ROUND: Int = 3

        const val HAVAL_4_ROUND: Int = 4

        const val HAVAL_5_ROUND: Int = 5

        private const val BLOCK_SIZE = 128 // inner block size in bytes

        private const val DIGEST0 = "C68F39913F901F3DDF44C707357A7D70"

        /**
         * caches the result of the correctness test, once executed.
         */
        private var valid: Boolean? = null
    }
}