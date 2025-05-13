package zjt.projects.util.crypto

import java.security.MessageDigest
import java.security.SecureRandom

val random = SecureRandom()

fun String.sha256(): String =
    MessageDigest
        .getInstance("SHA-256")
        .digest(this.toByteArray())
        .fold("", { str, it -> str + "%02x".format(it) })

fun generateSalt(): String {
    val salt = ByteArray(16)
    random.nextBytes(salt)
    return salt.contentToString()
}