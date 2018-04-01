package com.hoasung.twitsplit.mvvm.tweeter

class SplitMessageUtil {

    companion object {
        @Throws(BigSpanOfNonWhitespaceException::class)
        fun splitMessage(text: String, maxLengthOnSegment: Int): List<String> {

            //list of words in the text
            val words = text.split(Regex("\\s+"))

            //minimum number of segments we should try
            var minSeg = (text.length / maxLengthOnSegment)

            val div = (text.length % maxLengthOnSegment)

            if (div > 0) {
                minSeg += 1
            }

            //don't need to partition if just only one segment
            if (minSeg <= 1) {
                return listOf(text)
            }

            var currentSegment = ""
            var currentIndex = 1
            var tmp = ""
            val segments = arrayListOf<String>()
            var i = 0

            while (minSeg < words.size) {

                currentSegment = ""
                currentIndex = 1
                i = 0
                while (i < words.size) {
                    //build part indicator
                    if (currentSegment.isEmpty()) {
                        currentSegment = String.format("%d/%d", currentIndex, minSeg)
                    }

                    //try add word into the current segment
                    tmp = String.format("%s %s", currentSegment, words[i])

                    //if length of temp segment is valid
                    if (tmp.length <= maxLengthOnSegment) {
                        //use temp segment
                        currentSegment = tmp
                        //try next word
                        i++

                        //if is the last word, then add it into the segment list
                        if (i >= words.size) {
                            segments.add(currentSegment)
                        }
                    } else if (words[i].length > maxLengthOnSegment) {
                        throw BigSpanOfNonWhitespaceException(
                                words[i],
                                maxLengthOnSegment,
                                "Length of word is longer than " + maxLengthOnSegment)
                    } else {

                        segments.add(currentSegment)

                        //next segment
                        currentSegment = ""
                        currentIndex += 1
                    }
                }

                if (segments.size == minSeg) {
                    //minSeg is the best number of segments we should partition
                    break;
                } else {
                    segments.clear()

                    //try new number of segment
                    minSeg += 1
                }
            }

            return segments
        }
    }

}