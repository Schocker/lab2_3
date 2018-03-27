package edu.iis.mto.similarity;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SimilarityFinderTest {
    SequenceSearcherStub sequenceSearcherStub;
    SimilarityFinder similarityFinder;

    @Before
    public void initialize() {
        sequenceSearcherStub = new SequenceSearcherStub();
        similarityFinder = new SimilarityFinder(sequenceSearcherStub);
    }

    @Test
    public void calculateJackardSimilarityReturns1WhenSequencesAreEmptyTest() {
        int[] seq1 = new int[0];
        int[] seq2 = new int[0];
        assertThat(similarityFinder.calculateJackardSimilarity(seq1, seq2), is(1.0d));
    }


    @Test
    public void calculateJackardSimilarityReturns0WhenOneSequenceIsEmptyTest() {
        int[] seq1 = new int[0];
        int[] seq2 = new int[]{0, 1, 2};
        assertThat(similarityFinder.calculateJackardSimilarity(seq1, seq2), is(0.0));
    }

    @Test
    public void calculateJackardSimilarityReturns0WhenSecondSequenceIsEmptyTest() {
        int[] seq1 = new int[]{0, 1, 2};
        int[] seq2 = new int[0];
        sequenceSearcherStub.setFoundBooleanArray(new boolean[]{false, false, false});
        assertThat(similarityFinder.calculateJackardSimilarity(seq1, seq2), is(0.0));
    }

    @Test
    public void calculateJackardSimilarityForSameSequencesTest() {
        int[] seq1 = new int[]{0, 1, 2};
        int[] seq2 = new int[]{0, 1, 2};
        sequenceSearcherStub.setFoundBooleanArray(new boolean[]{true, true, true});
        assertThat(similarityFinder.calculateJackardSimilarity(seq1, seq2), is(1.0));
    }

    @Test
    public void calculateJackardSimilarityForDifferentSequencesTest() {
        int[] seq1 = new int[]{5, 6, 0, 1, 2};
        int[] seq2 = new int[]{0, 1, 2, 11};
        sequenceSearcherStub.setFoundBooleanArray(new boolean[]{false, false, true, true, true});
        assertThat(similarityFinder.calculateJackardSimilarity(seq1, seq2), is(0.5));
    }

    @Test
    public void calculateJackardUsesSearchMethodTest() {
        int[] seq1 = new int[]{5, 6, 0, 1, 2};
        int[] seq2 = new int[]{0, 1, 2, 11};
        sequenceSearcherStub.setFoundBooleanArray(new boolean[]{false, false, true, true, true});
        similarityFinder.calculateJackardSimilarity(seq1, seq2);
        assertThat(sequenceSearcherStub.getSearchInvokeCounter(), is(5));
        assertThat(sequenceSearcherStub.getKeyArgArray(), is(seq1));
        assertThat(sequenceSearcherStub.getSeqArgArray(), is(seq2));
    }
}