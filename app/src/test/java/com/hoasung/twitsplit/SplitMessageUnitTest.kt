package com.hoasung.twitsplit

import android.util.Log
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.math.max

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class SplitMessageUnitTest {

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
                    throw BigSpanOfNonWhitespace(words[i], maxLengthOnSegment)
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

    class BigSpanOfNonWhitespace(word: String, maxLength: Int) : Throwable() {
    }

    @Test
    fun oneSegment() {
        val text = "I can't believe Tweeter now supports chunking"
        val segments = splitMessage(text, 50)

        for (seg in segments) {
            System.out.println(seg)
        }

        assertEquals(segments.size, 1)

        assertEquals(segments[0], text)
    }


    @Test
    fun twoSegment() {
        val text = "I can't believe Tweeter now supports chunking my messages, so I don't have to do it myself."
        val segments = splitMessage(text, 50)

        for (seg in segments) {
            System.out.println(seg)
        }

        assertEquals(segments.size, 2)

        val seg1 = "1/2 I can't believe Tweeter now supports chunking"
        val seg2 = "2/2 my messages, so I don't have to do it myself."
        assertEquals(segments[0], seg1)
        assertEquals(segments[1], seg2)
    }
}
