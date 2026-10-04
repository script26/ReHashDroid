package com.example.rehashdroid.logic

/**
 *
 *
 * A collection of utility methods used throughout this project.
 *
 */
object UtilServices {
    // Constants and variables
    // -------------------------------------------------------------------------
    // Hex charset
    private val HEX_DIGITS = "0123456789ABCDEF".toCharArray()

    /**
     *
     *
     * Returns a string of hexadecimal digits from a byte array, starting at
     * `offset` and consisting of `length` bytes. Each
     * byte is converted to 2 hex symbols; zero(es) included.
     *
     *
     * @param ba     the byte array to convert.
     * @param offset the index from which to start considering the bytes to
     * convert.
     * @param length the count of bytes, starting from the designated offset to
     * convert.
     * @return a string of hexadecimal characters (two for each byte)
     * representing the designated input byte sub-array.
     */
    // Class methods
    // -------------------------------------------------------------------------
    /**
     *
     *
     * Returns a string of hexadecimal digits from a byte array. Each byte is
     * converted to 2 hex symbols; zero(es) included.
     *
     *
     *
     *
     * This method calls the method with same name and three arguments as:
     *
     *
     * <pre>
     * toString(ba, 0, ba.length);
    </pre> *
     *
     * @param ba the byte array to convert.
     * @return a string of hexadecimal characters (two for each byte)
     * representing the designated input byte array.
     */
    @JvmOverloads
    fun toString(ba: ByteArray?, offset: Int = 0, length: Int = ba!!.size): String {
        val buf = CharArray(length * 2)
        var i = 0
        var j = 0
        var k: Int
        while (i < length) {
            k = ba!![offset + i++].toInt()
            buf[j++] = HEX_DIGITS[(k ushr 4) and 0x0F]
            buf[j++] = HEX_DIGITS[k and 0x0F]
        }
        return String(buf)
    }
}