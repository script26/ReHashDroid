package com.example.rehashdroid.logic

import com.example.rehashdroid.logic.HashFactory.getInstance
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.util.zip.Adler32
import java.util.zip.CRC32
import java.util.zip.Checksum
import kotlin.ByteArray
import kotlin.Int
import kotlin.String
import kotlin.also


class HashFunctionOperator {
    private var _sAlgo = "md5"

    fun SetAlgorithm(sAlgo: String) {
        _sAlgo = sAlgo
    }

    private fun PrependValue(iStr: String, NbDigits: Int): String {
        var sReturnedStr = iStr
        while (sReturnedStr.length < NbDigits) sReturnedStr = "0" + sReturnedStr
        return sReturnedStr
    }

    private fun CreateHashString(messageDigest: ByteArray?): String {
        val hexString = StringBuffer()
        for (i in messageDigest!!.indices) {
            val h = Integer.toHexString(0xFF and messageDigest[i].toInt())
            val StrComplete = PrependValue(h, 2)
            hexString.append(StrComplete)
        }
        return hexString.toString()
    }

    fun StringToHash(s: String): String {
        var sReturnedStr = ""
        if (_sAlgo == "CRC-32" || _sAlgo == "Adler-32") {
            var value: Long = 0L
            var checksumv: Checksum? = null
            if (_sAlgo == "CRC-32") {
                val crc32v = CRC32()
                crc32v.update(s.toByteArray())
                checksumv = crc32v
            } else if (_sAlgo == "Adler-32") {
                val adler32v = Adler32()
                adler32v.update(s.toByteArray())
                checksumv = adler32v
            }
            if (checksumv != null) {
                value = checksumv.getValue()
                val strHex = value.toString(16)
                sReturnedStr = PrependValue(strHex, 8) // 32 bits (8 digits in
                // hexadecimal)
            }
        } else {
            val MessageDig = getInstance(_sAlgo)
            if (MessageDig != null) {
                val input = s.toByteArray()
                MessageDig.update(input, 0, input.size)
                val messageDigest = MessageDig.digest()
                sReturnedStr = CreateHashString(messageDigest)
            }
        }

        return sReturnedStr
    }

    fun FileToHash(inputstream: InputStream?): String {
        var sReturnedStr = ""
        if (null != inputstream) {
            if (_sAlgo == "CRC-32" || _sAlgo == "Adler-32") {
                try {
                    val databytes = ByteArray(1024)
                    var nread = 0
                    var value: Long = 0L
                    var checksumv: Checksum? = null
                    if (_sAlgo == "CRC-32") {
                        val crc32v = CRC32()
                        checksumv = crc32v
                    } else if (_sAlgo == "Adler-32") {
                        val adler32v = Adler32()
                        checksumv = adler32v
                    }
                    if (checksumv != null) {
                        while ((inputstream.read(databytes).also { nread = it }) > 0) {
                            checksumv.update(databytes, 0, nread)
                        }
                        value = checksumv.getValue()
                        val strHex = value.toString(16)
                        sReturnedStr = PrependValue(strHex, 8) // 32 bits (8 digits
                        // in hexadecimal)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            } else {
                try {
                    val MessageDig = getInstance(_sAlgo)
                    if (MessageDig != null) {
                        val databytes = ByteArray(1024)
                        var nread = 0
                        while ((inputstream.read(databytes).also { nread = it }) != -1) {
                            MessageDig.update(databytes, 0, nread)
                        }
                        val messageDigest = MessageDig.digest()
                        sReturnedStr = CreateHashString(messageDigest)
                    }
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

        return sReturnedStr
    }
}