package mu.enums;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

/**
 * This code is used to check that the enum does not contain duplicate keys. Implementors
 * should call the check() method to perform this check. The check method will throw
 * and Error if there is a duplication. Enumerations which uses this class must implement
 * <code>IUniqueKeyEnum</code> interface
 *
 * @author nambar
 * @see IUniqueKeyEnum
 */
public class EnumKeyChecker {

  public static <K, T extends Enum<T> & IUniqueKeyEnum<K>> void check(Class<T> enumClass) {
    final Set<K> keys = new HashSet<K>();
    for (IUniqueKeyEnum<K> e : EnumSet.allOf(enumClass)) {
      if (!keys.add(e.getKey())) {
        throw new IllegalStateException("Duplicate key in enum " + e.getKey());
      }
    }
  }
}
