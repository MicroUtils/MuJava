package mu.diff;

import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * compare two collections and compute items difference
 *
 * usage: <code>CollectionsDifference.difference(left, right, itemMatcher)</code>
 * ItemMatcher should return one of:
 * None - the item (left, right) do not match
 * Partial - the item match, but is not identical
 * Exact - the item is a perfect match
 *
 */
public class CollectionsDifference {

  public enum ItemMatch {
    Exact,
    Partial,
    None
  }

  public interface ItemMatcher<L, R> {
    ItemMatch match(L left, R right);
  }

  public static <L, R> CollectionsDifferenceImpl<L, R> difference(Collection<L> left, Collection<R> right, ItemMatcher<L, R> matcher) {
    List<L> itemsOnlyOnLeft = Lists.newArrayList();
    List<R> itemsOnlyOnRight = Lists.newArrayList();
    List<ItemDifference<L, R>> itemsOnBothWithExactMatch = Lists.newArrayList();
    List<ItemDifference<L, R>> itemsOnBothWithPartialMatch = Lists.newArrayList();
    doDifference(left, right, matcher, itemsOnlyOnLeft, itemsOnlyOnRight, itemsOnBothWithExactMatch, itemsOnBothWithPartialMatch);
    return new CollectionsDifferenceImpl<>(itemsOnlyOnLeft, itemsOnlyOnRight, itemsOnBothWithExactMatch, itemsOnBothWithPartialMatch);
  }

  private static <L, R> void doDifference(Collection<L> left, Collection<R> right, ItemMatcher<L, R> matcher, List<L> itemsOnlyOnLeft, List<R> itemsOnlyOnRight, List<ItemDifference<L, R>> itemsOnBothWithExactMatch, List<ItemDifference<L, R>> itemsOnBothWithPartialMatch) {
    List<L> itemsToProcessOnLeft = Lists.newArrayList(left);
    List<R> itemsToProcessOnRight = Lists.newArrayList(right);
    for (L leftItem : Lists.newArrayList(left)) {
      Optional<R> rightItem = getItemByMatch(leftItem, right, matcher, ItemMatch.Exact);
      if (rightItem.isPresent()) {
        itemsToProcessOnLeft.remove(leftItem);
        itemsToProcessOnRight.remove(rightItem.get());
        itemsOnBothWithExactMatch.add(new ItemDifference<>(leftItem, rightItem.get()));
      }
    }
    for (L leftItem : Lists.newArrayList(itemsToProcessOnLeft)) {
      Optional<R> rightItem = getItemByMatch(leftItem, itemsToProcessOnRight, matcher, ItemMatch.Partial);
      if (rightItem.isPresent()) {
          itemsToProcessOnLeft.remove(leftItem);
          itemsToProcessOnRight.remove(rightItem.get());
          itemsOnBothWithPartialMatch.add(new ItemDifference<>(leftItem, rightItem.get()));
        }
    }
    itemsOnlyOnLeft.addAll(itemsToProcessOnLeft);
    itemsOnlyOnRight.addAll(itemsToProcessOnRight);
  }

  private static <R, L> Optional<R> getItemByMatch(L item, Collection<R> collection, ItemMatcher<L, R> matcher, ItemMatch matchType) {
    for (R itemToCompare : collection) {
      if (matcher.match(item, itemToCompare) == matchType) {
        return Optional.of(itemToCompare);
      }
    }
    return Optional.empty();
  }

}
