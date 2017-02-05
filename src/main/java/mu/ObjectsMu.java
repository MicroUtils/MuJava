package mu;

import javax.annotation.Nullable;

public class ObjectsMu {

  /**
   * null safe equals
   */
  public static boolean equals(@Nullable Object left, @Nullable Object right) {
    if (left == null) {
      return right == null;
    }
    return left.equals(right);
  }
}
