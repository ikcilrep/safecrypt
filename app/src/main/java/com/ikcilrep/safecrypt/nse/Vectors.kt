package com.ikcilrep.safecrypt.nse

@ExperimentalUnsignedTypes
val primes: UShortArray = ushortArrayOf(
    2u, 3u, 5u, 7u, 11u, 13u, 17u, 19u, 23u, 29u,
    31u, 37u, 41u, 43u, 47u, 53u, 59u, 61u, 67u, 71u,
    73u, 79u, 83u, 89u, 97u, 101u, 103u, 107u, 109u, 113u,
    127u, 131u, 137u, 139u, 149u, 151u, 157u, 163u, 167u, 173u,
    179u, 181u, 191u, 193u, 197u, 199u, 211u, 223u, 227u, 229u,
    233u, 239u, 241u, 251u, 257u, 263u, 269u, 271u, 277u, 281u,
    283u, 293u, 307u, 311u, 313u, 317u, 331u, 337u, 347u, 349u,
    353u, 359u, 367u, 373u, 379u, 383u, 389u, 397u, 401u, 409u,
    419u, 421u, 431u, 433u, 439u, 443u, 449u, 457u, 461u, 463u,
    467u, 479u, 487u, 491u, 499u, 503u, 509u, 521u, 523u, 541u,
    547u, 557u, 563u, 569u, 571u, 577u, 587u, 593u, 599u, 601u,
    607u, 613u, 617u, 619u, 631u, 641u, 643u, 647u, 653u, 659u,
    661u, 673u, 677u, 683u, 691u, 701u, 709u, 719u, 727u, 733u,
    739u, 743u, 751u, 757u, 761u, 769u, 773u, 787u, 797u, 809u,
    811u, 821u, 823u, 827u, 829u, 839u, 853u, 857u, 859u, 863u,
    877u, 881u, 883u, 887u, 907u, 911u, 919u, 929u, 937u, 941u,
    947u, 953u, 967u, 971u, 977u, 983u, 991u, 997u, 1009u, 1013u,
    1019u, 1021u, 1031u, 1033u, 1039u, 1049u, 1051u, 1061u, 1063u, 1069u,
    1087u, 1091u, 1093u, 1097u, 1103u, 1109u, 1117u, 1123u, 1129u, 1151u,
    1153u, 1163u, 1171u, 1181u, 1187u, 1193u, 1201u, 1213u, 1217u, 1223u,
    1229u, 1231u, 1237u, 1249u, 1259u, 1277u, 1279u, 1283u, 1289u, 1291u,
    1297u, 1301u, 1303u, 1307u, 1319u, 1321u, 1327u, 1361u, 1367u, 1373u,
    1381u, 1399u, 1409u, 1423u, 1427u, 1429u, 1433u, 1439u, 1447u, 1451u,
    1453u, 1459u, 1471u, 1481u, 1483u, 1487u, 1489u, 1493u, 1499u, 1511u,
    1523u, 1531u, 1543u, 1549u, 1553u, 1559u, 1567u, 1571u, 1579u, 1583u,
    1597u, 1601u, 1607u, 1609u, 1613u, 1619u
)

fun ByteArray.isZeroVector() = isEmpty() || filter { it == 0.toByte() }.size == size

fun ByteArray.dotProduct(other: ShortArray): Long =
    foldIndexed(0.toLong()) { index, acc, el -> acc + (el.toLong() * other[index].toLong()) }

@ExperimentalUnsignedTypes
fun UShortArray.dotProduct(other: ShortArray): Long =
    foldIndexed(0.toLong()) { index, acc, el -> acc + (el.toLong() * other[index].toLong()) }

@ExperimentalUnsignedTypes
fun UShortArray.dotProduct(other: ByteArray): Long =
    foldIndexed(0.toLong()) { index, acc, el -> acc + (el.toLong() * other[index].toLong()) }

@ExperimentalUnsignedTypes
fun UShortArray.dotProduct(other: UShortArray): Long =
    foldIndexed(0.toLong()) { index, acc, el -> acc + (el.toLong() * other[index].toLong()) }

@ExperimentalUnsignedTypes
fun UShortArray.dotProduct(other: LongArray): Long =
    foldIndexed(0.toLong()) { index, acc, el -> acc + (el.toLong() * other[index]) }

fun ByteArray.dotProduct(other: LongArray): Long =
    foldIndexed(0.toLong()) { index, acc, el -> acc + (el.toLong() * other[index]) }

@ExperimentalUnsignedTypes
fun ByteArray.mapPrimes(): UShortArray =
    map { index -> primes[index.toUByte().toInt()] }.toUShortArray()

operator fun ByteArray.minus(other: ByteArray): ShortArray {
    if (other.size != size)
        throw IllegalStateException("Vectors sizes are different.")
    return ShortArray(size) { (this[it] - other[it]).toShort() }
}

operator fun LongArray.minus(other: LongArray): LongArray {
    if (other.size != size)
        throw IllegalStateException("Vectors sizes are different.")
    return LongArray(size) { this[it] - other[it] }
}

fun LongArray.vectorSum(other: LongArray): LongArray {
    if (other.size != size)
        throw IllegalStateException("Vectors sizes are different.")
    return LongArray(size) { this[it] + other[it] }
}

operator fun ByteArray.times(multiplier: Long): LongArray =
    LongArray(size) { this[it] * multiplier }

operator fun LongArray.times(multiplier: Long): LongArray =
    LongArray(size) { this[it] * multiplier }

@ExperimentalUnsignedTypes
operator fun UShortArray.times(multiplier: Long): LongArray =
    LongArray(size) { this[it].toInt() * multiplier }

operator fun LongArray.div(divisor: Long): LongArray {
    if (divisor == 0.toLong())
        throw IllegalArgumentException("Divisor is 0.")
    return LongArray(size) { this[it] / divisor }
}

