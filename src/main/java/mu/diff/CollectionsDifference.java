package mu.diff;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
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

  public interface ItemMatch {}
  public static class ItemMatchers {
    public static final ItemMatch Exact = new ItemMatchers.Exact();
    public static final ItemMatch None = new ItemMatchers.None();

    public static class Exact implements ItemMatch {}
    public static class None implements ItemMatch {}
    public static class Partial implements ItemMatch {
      private final int rank;

      public Partial() {
        this(0);
      }
      public Partial(int rank) {
        this.rank = rank;
      }

      public int getRank() {
        return rank;
      }

      @Override
      public String toString() {
        return MoreObjects.toStringHelper(this)
          .add("rank", rank)
          .toString();
      }

      @Override
      public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Partial partial = (Partial) o;
        return rank == partial.rank;
      }

      @Override
      public int hashCode() {

        return Objects.hash(rank);
      }
    }
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
      Optional<R> rightItem = getItemByExactMatch(leftItem, itemsToProcessOnRight, matcher);
      if (rightItem.isPresent()) {
        itemsToProcessOnLeft.remove(leftItem);
        itemsToProcessOnRight.remove(rightItem.get());
        itemsOnBothWithExactMatch.add(new ItemDifference<>(leftItem, rightItem.get()));
      }
    }
    for (L leftItem : Lists.newArrayList(itemsToProcessOnLeft)) {
      Optional<R> rightItem = getItemByPartialMatch(leftItem, itemsToProcessOnRight, matcher);
      if (rightItem.isPresent()) {
          itemsToProcessOnLeft.remove(leftItem);
          itemsToProcessOnRight.remove(rightItem.get());
          itemsOnBothWithPartialMatch.add(new ItemDifference<>(leftItem, rightItem.get()));
        }
    }
    itemsOnlyOnLeft.addAll(itemsToProcessOnLeft);
    itemsOnlyOnRight.addAll(itemsToProcessOnRight);
  }

  private static <R, L> Optional<R> getItemByExactMatch(L item, Collection<R> collection, ItemMatcher<L, R> matcher) {
    for (R itemToCompare : collection) {
      if (matcher.match(item, itemToCompare) == ItemMatchers.Exact) {
        return Optional.of(itemToCompare);
      }
    }
    return Optional.empty();
  }
  private static <R, L> Optional<R> getItemByPartialMatch(L item, Collection<R> collection, ItemMatcher<L, R> matcher) {
    int maxRank = Integer.MIN_VALUE;
    Optional<R> selectedItem = Optional.empty();
    for (R itemToCompare : collection) {
      ItemMatch matchResult = matcher.match(item, itemToCompare);
      if (matchResult instanceof ItemMatchers.Partial && ((ItemMatchers.Partial) matchResult).getRank() > maxRank) {
        maxRank = ((ItemMatchers.Partial) matchResult).getRank();
        selectedItem = Optional.of(itemToCompare);
      }
    }
    return selectedItem;
  }

}
