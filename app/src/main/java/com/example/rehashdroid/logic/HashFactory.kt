package com.example.rehashdroid.logic

/**
 *
 *
 * A *Factory* to instantiate message digest algorithm instances.
 *
 */
object HashFactory {
    // Class methods
    // -------------------------------------------------------------------------
    /**
     *
     *
     * Return an instance of a hash algorithm given its name.
     *
     *
     * @param name the name of the hash algorithm.
     * @return an instance of the hash algorithm, or null if none found.
     * @throws InternalError if the implementation does not pass its self- test.
     */
    fun getInstance(name: String?): IMessageDigest? {
        var name = name
        if (name == null) {
            return null
        }

        name = name.trim { it <= ' ' }
        var result: IMessageDigest? = null
        if (name.equals("haval-128", ignoreCase = true)) result = Haval()
        else if (name.equals("md2", ignoreCase = true)) result = MD2()
        else if (name.equals("md4", ignoreCase = true)) result = MD4()
        else if (name.equals("md5", ignoreCase = true)) result = MD5()
        else if (name.equals("ripemd-128", ignoreCase = true)) result = RipeMD128()
        else if (name.equals("ripemd-160", ignoreCase = true)) result = RipeMD160()
        else if (name.equals("sha-1", ignoreCase = true)) result = Sha160()
        else if (name.equals("sha-256", ignoreCase = true)) result = Sha256()
        else if (name.equals("sha-384", ignoreCase = true)) result = Sha384()
        else if (name.equals("sha-512", ignoreCase = true)) result = Sha512()
        else if (name.equals("tiger", ignoreCase = true)) result = Tiger()
        else if (name.equals("whirlpool", ignoreCase = true)) result = Whirlpool()

        return result
    }
}