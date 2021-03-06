package com.hoasung.twitsplit

import com.hoasung.twitsplit.mvvm.tweeter.BigSpanOfNonWhitespaceException
import com.hoasung.twitsplit.mvvm.tweeter.SplitMessageUtil
import org.junit.Assert.assertEquals
import org.junit.Test


/**
 * This class will do unit test for the function splitMessage
 * it is in the class SplitMessageUtil
 *
 */
class SplitMessageUnitTest {

    @Test
    fun oneSegment() {
        val text = "I can't believe Tweeter now supports chunking"
        val segments = SplitMessageUtil.splitMessage(text, 50)

        for (seg in segments) {
            System.out.println(seg)
        }

        assertEquals(segments.size, 1)

        assertEquals(segments[0], text)
    }


    @Test
    fun twoSegment() {
        val text = "I can't believe Tweeter now supports chunking my messages, so I don't have to do it myself."
        val segments = SplitMessageUtil.splitMessage(text, 50)

        for (seg in segments) {
            System.out.println(seg)
        }

        assertEquals(segments.size, 2)

        val seg1 = "1/2 I can't believe Tweeter now supports chunking"
        val seg2 = "2/2 my messages, so I don't have to do it myself."
        assertEquals(segments[0], seg1)
        assertEquals(segments[1], seg2)
    }

    @Test(expected = BigSpanOfNonWhitespaceException::class)
    fun bigSpanOfNonWhitespaceException() {
        val text = "I can't believe Tweeternowsupportschunkingmymessages,soIdon'thavetodo it myself."
        val messages = SplitMessageUtil.splitMessage(text, 50)
        assertEquals(messages, null)
    }

    @Test
    fun noBigSpanOfNonWhitespaceException() {
        val text = "I can't believe,soIdon'thavetodo it myself."
        val messages = SplitMessageUtil.splitMessage(text, 50)
        assertEquals(messages.size, 1)
    }
}
