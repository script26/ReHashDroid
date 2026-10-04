package com.example.rehashdroid.logic

import com.example.rehashdroid.logic.UtilServices.toString

/**
 *
 *
 * An implementation of the MD2 message digest algorithm.
 *
 *
 *
 *
 * MD2 is not widely used. Unless it is needed for compatibility with existing
 * systems, it is not recommended for use in new applications.
 *
 *
 *
 *
 * References:
 *
 *
 *
 *  1. The [MD2](http://www.ietf.org/rfc/rfc1319.txt) Message-Digest
 * Algorithm.<br></br>
 * B. Kaliski.
 *  1. The [RFC ERRATA PAGE](http://www.rfc-editor.org/errata.html)
 * under section RFC 1319.
 *
 */
class MD2  // Constructor(s)
// -------------------------------------------------------------------------
/**
 * Creates a new MD2 digest ready for use.
 */
    () : BaseHash("md2", DIGEST_LENGTH, BLOCK_LENGTH) {
    /**
     * The checksum computed so far.
     */
    private lateinit var checksum: ByteArray

    /**
     * Work array needed by encrypt method. First `BLOCK_LENGTH`
     * bytes are also used to store the running digest.
     */
    private lateinit var work: ByteArray

    /**
     *
     *
     * Private constructor used for cloning.
     *
     *
     * @param md2 the instance to clone.
     */
    private constructor(md2: MD2) : this() {
        // superclass field
        this.count = md2.count
        this.buffer = (md2.buffer.clone() as ByteArray?)!!

        // private field
        this.checksum = md2.checksum.clone()
        this.work = md2.work.clone()
    }

    // Class methods
    // -------------------------------------------------------------------------
    // Instance methods
    // -------------------------------------------------------------------------
    // java.lang.Cloneable interface implementation ----------------------------
    override fun clone(): Any {
        return MD2(this)
    }

    override val result: ByteArray
        // Implementation of abstract methods in BaseHash --------------------------
        get() {
            val result = ByteArray(DIGEST_LENGTH)

            // Encrypt checksum as last block.
            encryptBlock(checksum, 0)

            for (i in 0..<BLOCK_LENGTH) {
                result[i] = work[i]
            }

            return result
        }

    override fun resetContext() {
        checksum = ByteArray(BLOCK_LENGTH)
        work = ByteArray(BLOCK_LENGTH * 3)
    }

    public override fun selfTest(): Boolean {
        if (valid == null) {
            valid = DIGEST0 == toString(MD2().digest())
        }
        return valid!!
    }

    /**
     *
     *
     * Generates an array of padding bytes. The padding is defined as
     * `i` bytes of value `i`, where `i` is the
     * number of bytes to fill the last block of the message to
     * `BLOCK_LENGTH` bytes (or `BLOCK_LENGTH` bytes when
     * the last block was completely full).
     *
     *
     * @return the bytes to pad the remaining bytes in the buffer before
     * completing a hash operation.
     */
    override fun padBuffer(): ByteArray {
        var length = BLOCK_LENGTH - (count % BLOCK_LENGTH).toInt()
        if (length == 0) {
            length = BLOCK_LENGTH
        }
        val pad = ByteArray(length)
        for (i in 0..<length) {
            pad[i] = length.toByte()
        }
        return pad
    }

    /**
     *
     *
     * Adds `BLOCK_LENGTH` bytes to the running digest.
     *
     *
     * @param in  the byte array to take the `BLOCK_LENGTH` bytes
     * from.
     * @param off the offset to start from in the given byte array.
     */
    override fun transform(`in`: ByteArray, off: Int) {
        // encryptBlock(in, off);
        // updateCheckSum(in, off);
        updateCheckSumAndEncryptBlock(`in`, off)
    }

    // Private instance methods ------------------------------------------------
    /**
     * Updates the checksum with the `BLOCK_LENGTH` bytes from the
     * given array starting at `off`.
     */
    /*
     * private void updateCheckSum(byte[] in, int off) { byte l =
     * checksum[BLOCK_LENGTH-1]; for (int i = 0; i < BLOCK_LENGTH; i++) { byte b
     * = in[off+i]; // l = (byte)((checksum[i] & 0xFF) ^ (PI[((b & 0xFF) ^ (l &
     * 0xFF))] & 0xFF)); l = (byte)(checksum[i] ^ PI[(b ^ l) & 0xFF]);
     * checksum[i] = l; } }
     */
    /**
     * Adds a new block (`BLOCK_LENGTH` bytes) to the running digest
     * from the given byte array starting from the given offset.
     */
    private fun encryptBlock(`in`: ByteArray, off: Int) {
        for (i in 0..<BLOCK_LENGTH) {
            val b = `in`[off + i]
            work[BLOCK_LENGTH + i] = b
            work[BLOCK_LENGTH * 2 + i] = (work[i].toInt() xor b.toInt()).toByte()
        }

        var t: Byte = 0
        for (i in 0..17) {
            for (j in 0..<3 * BLOCK_LENGTH) {
                // t = (byte)((work[j] & 0xFF) ^ (PI[t & 0xFF] & 0xFF));
                t = (work[j].toInt() xor PI[t.toInt() and 0xFF].toInt()).toByte()
                work[j] = t
            }
            // t = (byte)((t + i) & 0xFF);
            t = (t + i).toByte()
        }
    }

    /**
     * Optimized method that combines a checksum update and encrypt of a block.
     */
    private fun updateCheckSumAndEncryptBlock(`in`: ByteArray, off: Int) {
        var l = checksum[BLOCK_LENGTH - 1]
        for (i in 0..<BLOCK_LENGTH) {
            val b = `in`[off + i]
            work[BLOCK_LENGTH + i] = b
            // work[BLOCK_LENGTH*2+i] = (byte)((work[i] & 0xFF) ^ (b & 0xFF));
            work[BLOCK_LENGTH * 2 + i] = (work[i].toInt() xor b.toInt()).toByte()
            // l = (byte)((checksum[i] & 0xFF) ^ (PI[((b & 0xFF) ^ (l & 0xFF))]
            // & 0xFF));
            l = (checksum[i].toInt() xor PI[(b.toInt() xor l.toInt()) and 0xFF].toInt()).toByte()
            checksum[i] = l
        }

        var t: Byte = 0
        for (i in 0..17) {
            for (j in 0..<3 * BLOCK_LENGTH) {
                // t = (byte)((work[j] & 0xFF) ^ (PI[t & 0xFF] & 0xFF));
                t = (work[j].toInt() xor PI[t.toInt() and 0xFF].toInt()).toByte()
                work[j] = t
            }
            // t = (byte)((t + i) & 0xFF);
            t = (t + i).toByte()
        }
    }

    companion object {
        // Constants and variables
        // -------------------------------------------------------------------------
        /**
         * An MD2 message digest is always 128-bits long, or 16 bytes.
         */
        private const val DIGEST_LENGTH = 16

        /**
         * The MD2 algorithm operates on 128-bit blocks, or 16 bytes.
         */
        private const val BLOCK_LENGTH = 16

        /**
         * 256 byte "random" permutation of the digits of pi.
         */
        private val PI = byteArrayOf(
            41, 46, 67, -55, -94, -40, 124, 1, 61,
            54, 84, -95, -20, -16, 6, 19, 98, -89, 5, -13, -64, -57, 115, -116,
            -104, -109, 43, -39, -68, 76, -126, -54, 30, -101, 87, 60, -3, -44,
            -32, 22, 103, 66, 111, 24, -118, 23, -27, 18, -66, 78, -60, -42,
            -38, -98, -34, 73, -96, -5, -11, -114, -69, 47, -18, 122, -87, 104,
            121, -111, 21, -78, 7, 63, -108, -62, 16, -119, 11, 34, 95, 33,
            -128, 127, 93, -102, 90, -112, 50, 39, 53, 62, -52, -25, -65, -9,
            -105, 3, -1, 25, 48, -77, 72, -91, -75, -47, -41, 94, -110, 42,
            -84, 86, -86, -58, 79, -72, 56, -46, -106, -92, 125, -74, 118, -4,
            107, -30, -100, 116, 4, -15, 69, -99, 112, 89, 100, 113, -121, 32,
            -122, 91, -49, 101, -26, 45, -88, 2, 27, 96, 37, -83, -82, -80,
            -71, -10, 28, 70, 97, 105, 52, 64, 126, 15, 85, 71, -93, 35, -35,
            81, -81, 58, -61, 92, -7, -50, -70, -59, -22, 38, 44, 83, 13, 110,
            -123, 40, -124, 9, -45, -33, -51, -12, 65, -127, 77, 82, 106, -36,
            55, -56, 108, -63, -85, -6, 36, -31, 123, 8, 12, -67, -79, 74, 120,
            -120, -107, -117, -29, 99, -24, 109, -23, -53, -43, -2, 59, 0, 29,
            57, -14, -17, -73, 14, 102, 88, -48, -28, -90, 119, 114, -8, -21,
            117, 75, 10, 49, 68, 80, -76, -113, -19, 31, 26, -37, -103, -115,
            51, -97, 17, -125, 20
        )

        /**
         * The output of this message digest when no data has been input.
         */
        private const val DIGEST0 = "8350E5A3E24C153DF2275C9F80692773"

        /**
         * caches the result of the correctness test, once executed.
         */
        private var valid: Boolean? = null
    }
}