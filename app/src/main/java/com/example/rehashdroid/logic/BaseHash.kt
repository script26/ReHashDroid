package com.example.rehashdroid.logic

/**
 *
 *
 * A base abstract class to facilitate hash implementations.
 *
 */
abstract class BaseHash protected constructor(
    /**
     * The canonical name prefix of the hash.
     */
    protected var name: String?,
    /**
     * The hash (output) size in bytes.
     */
    protected var hashSize: Int,
    /**
     * The hash (inner) block size in bytes.
     */
    protected var blockSize: Int
) : IMessageDigest, Cloneable {
    // Constants and variables
    // -------------------------------------------------------------------------

    /**
     * Number of bytes processed so far.
     */
    protected var count: Long = 0

    /**
     * Temporary input buffer.
     */
    protected var buffer: ByteArray

    // Constructor(s)
    // -------------------------------------------------------------------------
    /**
     *
     *
     * Trivial constructor for use by concrete subclasses.
     *
     *
     * @param name      the canonical name prefix of this instance.
     * @param hashSize  the block size of the output in bytes.
     * @param blockSize the block size of the internal transform.
     */
    init {
        this.buffer = ByteArray(blockSize)

        resetContext()
    }

    // Class methods
    // -------------------------------------------------------------------------
    // Instance methods
    // -------------------------------------------------------------------------
    // IMessageDigest interface implementation ---------------------------------
    override fun name(): String? {
        return name
    }

    override fun hashSize(): Int {
        return hashSize
    }

    override fun blockSize(): Int {
        return blockSize
    }

    override fun update(b: Byte) {
        // compute number of bytes still unhashed; ie. present in buffer
        val i = (count % blockSize).toInt()
        count++
        buffer[i] = b
        if (i == (blockSize - 1)) {
            transform(buffer, 0)
        }
    }

    override fun update(b: ByteArray) {
        update(b, 0, b.size)
    }

    override fun update(b: ByteArray, offset: Int, len: Int) {
        var n = (count % blockSize).toInt()
        count += len.toLong()
        val partLen = blockSize - n
        var i = 0

        if (len >= partLen) {
            System.arraycopy(b, offset, buffer, n, partLen)
            transform(buffer, 0)
            i = partLen
            while (i + blockSize - 1 < len) {
                transform(b, offset + i)
                i += blockSize
            }
            n = 0
        }

        if (i < len) {
            System.arraycopy(b, offset + i, buffer, n, len - i)
        }
    }

    override fun digest(): ByteArray? {
        val tail = padBuffer() // pad remaining bytes in buffer
        update(tail, 0, tail.size) // last transform of a message
        val result = this.result // make a result out of context

        reset() // reset this instance for future re-use

        return result
    }

    override fun reset() { // reset this instance for future re-use
        count = 0L
        var i = 0
        while (i < blockSize) {
            buffer[i++] = 0
        }

        resetContext()
    }

    // methods to be implemented by concrete subclasses ------------------------
    public abstract override fun clone(): Any

    override abstract fun selfTest(): Boolean

    /**
     *
     *
     * Returns the byte array to use as padding before completing a hash
     * operation.
     *
     *
     * @return the bytes to pad the remaining bytes in the buffer before
     * completing a hash operation.
     */
    protected abstract fun padBuffer(): ByteArray

    /**
     *
     *
     * Constructs the result from the contents of the current context.
     *
     *
     * @return the output of the completed hash operation.
     */
    protected abstract val result: ByteArray?

    /**
     * Resets the instance for future re-use.
     */
    protected abstract fun resetContext()

    /**
     *
     *
     * The block digest transformation per se.
     *
     *
     * @param in     the *blockSize* long block, as an array of bytes to
     * digest.
     * @param offset the index where the data to digest is located within the input
     * buffer.
     */
    protected abstract fun transform(input: ByteArray, offset: Int)
}