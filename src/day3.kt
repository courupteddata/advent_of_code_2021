// import java.io.File
import java.util.*

/*
    Day 3: Binary Diagnostic
    https://adventofcode.com/2021/day/3

    Big lesson learned... \r can be your enemy on Windows
*/
class BinaryDiagnostic(input: String) {
    private val lines = input.split("\n")
    private val lineLength = lines[0].trim('\r', ' ').length
    private val columns = Array(lineLength) {BitSet()} // Column Counts

    private val gammaRate: Int
    private val epsilonRate: Int
    private val powerConsumption: Int
    private val oxygenGeneratorRating: Int
    private val co2ScrubberRating: Int
    private val lifeSupportRating: Int

    init {
        // Need to transpose the matrix
        for ((index, line) in lines.withIndex()) {
            var columnIndex = 0
            for (item in line.trim('\r', ' ')) {
                columns[columnIndex].set(index, item == '1')
                columnIndex++
            }
        }
        var gammaRateStr = ""
        var epsilonRateStr = ""
        val mostCommon = BitSet() // Keeps track of indexes of interest using a bit set
        val leastCommon = BitSet()
        mostCommon.flip(0, lines.size) // Sets everything to being considered
        leastCommon.flip(0, lines.size)
        for (column in this.columns){ // Looking over everything a column at a time
            val popCount = column.cardinality() // Counts number of set bits, ie. pop-count.
            val oneMostCommon = popCount * 2 >= lines.size // Doing times by two to avoid integer division rounding

            gammaRateStr += if (oneMostCommon) "1" else "0"
            epsilonRateStr += if (oneMostCommon) "0" else "1"

            val mostCommonIntersection = BitSet().also {
                // Count set bits
                var oneCount = 0
                for(index in mostCommon.stream()){
                    oneCount += if (column.get(index)) 1 else 0
                }
                // Looking for ones if they are the most common
                val lookingForOnes = oneCount *  2 >= mostCommon.cardinality()
                for(index in mostCommon.stream()){
                    it.set(index, column.get(index) == lookingForOnes)
                }
            }

            val leastCommonIntersection = BitSet().also {
                // Count most common and then store the opposite
                var oneCount = 0
                for(index in leastCommon.stream()){
                    oneCount += if (column.get(index)) 1 else 0
                }
                val lookingForOnes = oneCount * 2 >= leastCommon.cardinality()
                for(index in leastCommon.stream()){
                    it.set(index, column.get(index) != lookingForOnes)
                }
            }

            if (mostCommon.cardinality() > 1) { // Only remove items if there are more options
                mostCommon.and(mostCommonIntersection)
            }
            if (leastCommon.cardinality() > 1) { // Only remove items if there are more options
                leastCommon.and(leastCommonIntersection)
            }
        }
        gammaRate = Integer.parseInt(gammaRateStr, 2)
        epsilonRate = Integer.parseInt(epsilonRateStr, 2)
        powerConsumption = gammaRate * epsilonRate
        oxygenGeneratorRating = Integer.parseInt(lines[mostCommon.nextSetBit(0)].trim(), 2)
        co2ScrubberRating = Integer.parseInt(lines[leastCommon.nextSetBit(0)].trim(), 2)
        lifeSupportRating = oxygenGeneratorRating * co2ScrubberRating
    }

    override fun toString(): String {
        return "GammaRate = $gammaRate, EpsilonRate = $epsilonRate, PowerConsumption = $powerConsumption\n" +
               "OxygenGeneratorRating = $oxygenGeneratorRating, CO2ScrubberRating = $co2ScrubberRating, " +
                "LifeSupportRating = $lifeSupportRating"
    }

}

fun main() {
    val testData =
            "00100\n" +
            "11110\n" +
            "10110\n" +
            "10111\n" +
            "10101\n" +
            "01111\n" +
            "00111\n" +
            "11100\n" +
            "10000\n" +
            "11001\n" +
            "00010\n" +
            "01010"

    val binaryDiagnosticInstance = BinaryDiagnostic(testData)
    println(binaryDiagnosticInstance)

    //binaryDiagnosticInstance = BinaryDiagnostic(File("test_data.txt").readText())
    //(binaryDiagnosticInstance)

}