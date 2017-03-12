package mu.diff;

import com.google.common.base.MoreObjects;

import java.util.Collection;
import java.util.Objects;

public class CollectionsDifferenceImpl<L, R> {
  private final Collection<L> itemsOnlyOnLeft;
  private final Collection<R> itemsOnlyOnRight;
  private final Collection<ItemDifference<L, R>> itemsOnBothWithExactMatch;
  private final Collection<ItemDifference<L, R>> itemsOnBothWithPartialMatch;

  public CollectionsDifferenceImpl(Collection<L> itemsOnlyOnLeft, Collection<R> itemsOnlyOnRight, Collection<ItemDifference<L, R>> itemsOnBothWithExactMatch, Collection<ItemDifference<L, R>> itemsOnBothWithPartialMatch) {
    this.itemsOnlyOnLeft = itemsOnlyOnLeft;
    this.itemsOnlyOnRight = itemsOnlyOnRight;
    this.itemsOnBothWithExactMatch = itemsOnBothWithExactMatch;
    this.itemsOnBothWithPartialMatch = itemsOnBothWithPartialMatch;
  }

  public Collection<L> itemsOnlyOnLeft() {
    return itemsOnlyOnLeft;
  }

  public Collection<R> itemsOnlyOnRight() {
    return itemsOnlyOnRight;
  }

  public Collection<ItemDifference<L, R>> itemsOnBothWithExactMatch() {
    return itemsOnBothWithExactMatch;
  }

  public Collection<ItemDifference<L, R>> itemsOnBothWithPartialMatch() {
    return itemsOnBothWithPartialMatch;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
      .add("itemsOnlyOnLeft", itemsOnlyOnLeft)
      .add("itemsOnlyOnRight", itemsOnlyOnRight)
      .add("itemsOnBothWithExactMatch", itemsOnBothWithExactMatch)
      .add("itemsOnBothWithPartialMatch", itemsOnBothWithPartialMatch)
      .toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CollectionsDifferenceImpl<?, ?> that = (CollectionsDifferenceImpl<?, ?>) o;
    return Objects.equals(itemsOnlyOnLeft, that.itemsOnlyOnLeft) &&
      Objects.equals(itemsOnlyOnRight, that.itemsOnlyOnRight) &&
      Objects.equals(itemsOnBothWithExactMatch, that.itemsOnBothWithExactMatch) &&
      Objects.equals(itemsOnBothWithPartialMatch, that.itemsOnBothWithPartialMatch);
  }

  @Override
  public int hashCode() {
    return Objects.hash(itemsOnlyOnLeft, itemsOnlyOnRight, itemsOnBothWithExactMatch, itemsOnBothWithPartialMatch);
  }
}
