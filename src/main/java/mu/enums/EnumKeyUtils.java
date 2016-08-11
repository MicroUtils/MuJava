package mu.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This class contains functions to help working with enums. enums that use this class must implement
 * {@link IUniqueKeyEnum}
 *
 * @author zivry
 */
public class EnumKeyUtils {
  /**
   * This code is used to build a mapping from the enum key to the Enum itself
   *
   * @param <K>       the type of enum's key
   * @param <T>       the type of enum.
   * @param enumClass the class of the enum to be in the map
   * @return Map from key to Enum instance
   */
  public static <K, T extends Enum<T> & IUniqueKeyEnum<K>> Map<K, T> buildKeysMap(Class<T> enumClass) {
    final Map<K, T> keys = new HashMap<K, T>();
    for (T e : EnumSet.allOf(enumClass)) {
      keys.put(e.getKey(), e);
    }
    return keys;
  }

  /**
   * Returns enum instance associated with key
   *
   * @param <K> the type of enum's key
   * @param <T> the type of enum.
   * @return enum object from set or null if not found
   */
  public static <K, T extends Enum<T> & IUniqueKeyEnum<K>> T fromKey(K key, Set<T> set) {
    for (T e : set) {
      if (e.getKey().equals(key)) {
        return e;
      }
    }
    return null;
  }
}
