package mu.diff;

import com.google.common.base.MoreObjects;

import java.util.Objects;

public class ItemDifference<L, R> {
  private final L left;
  private final R right;

  public ItemDifference(L left, R right) {
    this.left = left;
    this.right = right;
  }

  public L getLeft() {
    return left;
  }

  public R getRight() {
    return right;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ItemDifference<?, ?> that = (ItemDifference<?, ?>) o;
    return Objects.equals(left, that.left) &&
      Objects.equals(right, that.right);
  }

  @Override
  public int hashCode() {
    return Objects.hash(left, right);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
      .add("left", left)
      .add("right", right)
      .toString();
  }
}
