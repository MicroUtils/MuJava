package mu.diff;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.Collections;
import java.util.Objects;

import static org.junit.Assert.*;

@SuppressWarnings("unchecked")
public class CollectionsDifferenceTest {

  @Test
  public void testEmptyReturnEmpty() {
    CollectionsDifferenceImpl<Integer, Integer> diffResult = CollectionsDifference.difference(Lists.newArrayList(new Integer[]{}), Lists.newArrayList(new Integer[]{}), intParityMatcher);
    assertEquals(Collections.emptyList(), diffResult.itemsOnlyOnLeft());
    assertEquals(Collections.emptyList(), diffResult.itemsOnlyOnRight());
    assertEquals(Collections.emptyList(), diffResult.itemsOnBothWithExactMatch());
    assertEquals(Collections.emptyList(), diffResult.itemsOnBothWithPartialMatch());
    assertTrue(diffResult.isEquals());
  }

  @Test
  public void testItemOnlyInLeft() {
    CollectionsDifferenceImpl<Integer, Integer> diffResult = CollectionsDifference.difference(Lists.newArrayList(1), Lists.newArrayList(new Integer[]{}), intParityMatcher);
    assertEquals(Lists.newArrayList(1), diffResult.itemsOnlyOnLeft());
    assertEquals(Collections.emptyList(), diffResult.itemsOnlyOnRight());
    assertEquals(Collections.emptyList(), diffResult.itemsOnBothWithExactMatch());
    assertEquals(Collections.emptyList(), diffResult.itemsOnBothWithPartialMatch());
    assertFalse(diffResult.isEquals());
  }

  @Test
  public void testItemOnlyInRight() {
    CollectionsDifferenceImpl<Integer, Integer> diffResult = CollectionsDifference.difference(Lists.newArrayList(new Integer[]{}), Lists.newArrayList(1), intParityMatcher);
    assertEquals(Collections.emptyList(), diffResult.itemsOnlyOnLeft());
    assertEquals(Lists.newArrayList(1), diffResult.itemsOnlyOnRight());
    assertEquals(Collections.emptyList(), diffResult.itemsOnBothWithExactMatch());
    assertEquals(Collections.emptyList(), diffResult.itemsOnBothWithPartialMatch());
    assertFalse(diffResult.isEquals());
  }

  @Test
  public void testItemsWithExactMatch() {
    CollectionsDifferenceImpl<Integer, Integer> diffResult = CollectionsDifference.difference(Lists.newArrayList(1), Lists.newArrayList(1), intParityMatcher);
    assertEquals(Collections.emptyList(), diffResult.itemsOnlyOnLeft());
    assertEquals(Collections.emptyList(), diffResult.itemsOnlyOnRight());
    assertEquals(Lists.newArrayList(new ItemDifference[]{new ItemDifference<>(1, 1)}), diffResult.itemsOnBothWithExactMatch());
    assertEquals(Collections.emptyList(), diffResult.itemsOnBothWithPartialMatch());
    assertTrue(diffResult.isEquals());
  }

  @Test
  public void testItemsWithPartialMatch() {
    CollectionsDifferenceImpl<Integer, Integer> diffResult = CollectionsDifference.difference(Lists.newArrayList(1), Lists.newArrayList(3), intParityMatcher);
    assertEquals(Collections.emptyList(), diffResult.itemsOnlyOnLeft());
    assertEquals(Collections.emptyList(), diffResult.itemsOnlyOnRight());
    assertEquals(Collections.emptyList(), diffResult.itemsOnBothWithExactMatch());
    assertEquals(Lists.newArrayList(new ItemDifference<>(1, 3)), diffResult.itemsOnBothWithPartialMatch());
    assertFalse(diffResult.isEquals());
  }

  @Test
  public void testWithManyItemsOnBothLists() {
    CollectionsDifferenceImpl<Integer, Integer> diffResult = CollectionsDifference.difference(
      Lists.newArrayList(0, 1, 2, 3, 4, 5, 6), Lists.newArrayList(3, 4, 5, 6, 7), intParityMatcher);
    assertEquals(Lists.newArrayList(0, 2), diffResult.itemsOnlyOnLeft());
    assertEquals(Collections.emptyList(), diffResult.itemsOnlyOnRight());
    assertEquals(Lists.newArrayList(new ItemDifference<>(3, 3), new ItemDifference<>(4, 4), new ItemDifference(5, 5), new ItemDifference<>(6, 6)),
      diffResult.itemsOnBothWithExactMatch());
    assertEquals(Lists.newArrayList(new ItemDifference<>(1, 7)), diffResult.itemsOnBothWithPartialMatch());
  }

  @Test
  public void testWithManyItemsOnBothLists2() {
    CollectionsDifferenceImpl<Integer, Integer> diffResult = CollectionsDifference.difference(
      Lists.newArrayList(4, 5, 6), Lists.newArrayList(3, 4, 5, 6, 7, 8), intParityMatcher);
    assertEquals(Collections.emptyList(), diffResult.itemsOnlyOnLeft());
    assertEquals(Lists.newArrayList(3, 7, 8), diffResult.itemsOnlyOnRight());
    assertEquals(Lists.newArrayList(new ItemDifference<>(4, 4), new ItemDifference<>(5, 5), new ItemDifference(6, 6)), diffResult
      .itemsOnBothWithExactMatch());
    assertEquals(Collections.emptyList(), diffResult.itemsOnBothWithPartialMatch());
  }

  @Test
  public void testWithExactMatchInMoreThanOneItem() {
    CollectionsDifferenceImpl<Integer, Integer> diffResult = CollectionsDifference.difference(
      Lists.newArrayList(1, 1), Lists.newArrayList(1, 1), intParityMatcher);
    assertEquals(Collections.emptyList(), diffResult.itemsOnlyOnLeft());
    assertEquals(Collections.emptyList(), diffResult.itemsOnlyOnRight());
    assertEquals(Lists.newArrayList(new ItemDifference<>(1, 1), new ItemDifference<>(1, 1)), diffResult.itemsOnBothWithExactMatch());
    assertEquals(Collections.emptyList(), diffResult.itemsOnBothWithPartialMatch());
  }

  @Test
  public void testWithPartialMatchInMoreThanOneItem() {
    CollectionsDifferenceImpl<Integer, Integer> diffResult = CollectionsDifference.difference(
      Lists.newArrayList(1, 3), Lists.newArrayList(5, 7), intParityMatcher);
    assertEquals(Collections.emptyList(), diffResult.itemsOnlyOnLeft());
    assertEquals(Collections.emptyList(), diffResult.itemsOnlyOnRight());
    assertEquals(Collections.emptyList(), diffResult.itemsOnBothWithExactMatch());
    assertEquals(Lists.newArrayList(new ItemDifference<>(1, 5), new ItemDifference<>(3, 7)),
      diffResult.itemsOnBothWithPartialMatch());
  }
  @Test
  public void testWithPartialMatchInMoreThanOneItemReturnMaxRank() {
    CollectionsDifferenceImpl<Integer, Integer> diffResult = CollectionsDifference.difference(
      Lists.newArrayList(7, 5), Lists.newArrayList(3, 1), intParityMatcher);
    assertEquals(Collections.emptyList(), diffResult.itemsOnlyOnLeft());
    assertEquals(Collections.emptyList(), diffResult.itemsOnlyOnRight());
    assertEquals(Collections.emptyList(), diffResult.itemsOnBothWithExactMatch());
    assertEquals(Lists.newArrayList(new ItemDifference<>(7, 1), new ItemDifference<>(5, 3)),
      diffResult.itemsOnBothWithPartialMatch());
  }

  /**
   * this function match exact on same number and partial on even/odd match
   */
  private CollectionsDifference.ItemMatcher<Integer, Integer> intParityMatcher = (left, right) -> {
    if (Objects.equals(left, right)) {
      return CollectionsDifference.ItemMatchers.Exact;
    } else if (left % 2 == right % 2) {
      return new CollectionsDifference.ItemMatchers.Partial(left - right);
    } else {
      return CollectionsDifference.ItemMatchers.None;
    }
  };

}
