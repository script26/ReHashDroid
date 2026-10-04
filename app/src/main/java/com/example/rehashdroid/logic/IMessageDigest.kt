package com.example.rehashdroid.logic

/**
 *
 *
 * The basic visible methods of any hash algorithm.
 *
 *
 *
 *
 * A hash (or message digest) algorithm produces its output by iterating a basic
 * compression function on blocks of data.
 *
 */
interface IMessageDigest : Cloneable {
    // Constants
    // -------------------------------------------------------------------------
    // Methods
    // -------------------------------------------------------------------------
    /**
     *
     *
     * Returns the canonical name of this algorithm.
     *
     *
     * @return the canonical name of this instance.
     */
    fun name(): String?

    /**
     *
     *
     * Returns the output length in bytes of this message digest algorithm.
     *
     *
     * @return the output length in bytes of this message digest algorithm.
     */
    fun hashSize(): Int

    /**
     *
     *
     * Returns the algorithm's (inner) block size in bytes.
     *
     *
     * @return the algorithm's inner block size in bytes.
     */
    fun blockSize(): Int

    /**
     *
     *
     * Continues a message digest operation using the input byte.
     *
     *
     * @param b the input byte to digest.
     */
    fun update(b: Byte)

    /**
     *
     *
     * Continues a message digest operation, by filling the buffer, processing
     * data in the algorithm's HASH_SIZE-bit block(s), updating the context and
     * count, and buffering the remaining bytes in buffer for the next
     * operation.
     *
     *
     * @param in the input block.
     */
    fun update(input: ByteArray)

    /**
     *
     *
     * Continues a message digest operation, by filling the buffer, processing
     * data in the algorithm's HASH_SIZE-bit block(s), updating the context and
     * count, and buffering the remaining bytes in buffer for the next
     * operation.
     *
     *
     * @param in     the input block.
     * @param offset start of meaningful bytes in input block.
     * @param length number of bytes, in input block, to consider.
     */
    fun update(input: ByteArray, offset: Int, length: Int)

    /**
     *
     *
     * Completes the message digest by performing final operations such as
     * padding and resetting the instance.
     *
     *
     * @return the array of bytes representing the hash value.
     */
    fun digest(): ByteArray?

    /**
     *
     *
     * Resets the current context of this instance clearing any eventually
     * cached intermediary values.
     *
     */
    fun reset()

    /**
     *
     *
     * A basic test. Ensures that the digest of a pre-determined message is
     * equal to a known pre-computed value.
     *
     *
     * @return <tt>true</tt> if the implementation passes a basic self-test.
     * Returns <tt>false</tt> otherwise.
     */
    fun selfTest(): Boolean

    /**
     *
     *
     * Returns a clone copy of this instance.
     *
     *
     * @return a clone copy of this instance.
     */
    public override fun clone(): Any
}