/**
 *
 */
package mu.enums;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

/**
 * This class contains functions to help working with enums.
 * enums that use this class must implement {@link INamedEnum}
 *
 * @author zivry
 */
public class EnumNameUtils {
  /**
   * This code is used to build a mapping from the enum name to the Enum itself
   *
   * @param <T>  the type of enum.
   * @param name the name of the enum,
   * @param set  the set of all the enums instances to be in the map.
   * @return Enum instance represented by name
   */
  public static <T extends Enum<T> & INamedEnum> T fromName(String name, Set<T> set) {

    for (T e : set) {
      if (e.getName().equals(name)) {
        return e;
      }
    }
    return null;
  }

  /**
   * This code is used to build a mapping from the enum name to the Enum itself
   *
   * @param <T>  the type of enum.
   * @param name the name of the enum,
   * @param set  the set of all the enums instances to be in the map.
   * @return Enum instance represented by name ignored case.
   */
  public static <T extends Enum<T> & INamedEnum> T fromNameIgnoreCase(String name, Set<T> set) {
    for (T e : set) {
      if (e.getName().equalsIgnoreCase(name)) {
        return e;
      }
    }
    return null;
  }

  /**
   * This code is used to check that the enum does not contain duplicate names. Implementors
   * should call the check() method to perform this check. The check method will throw
   * and Error if there is a duplication.
   *
   * @param <T> the type of enum.
   * @param set the set to check for duplications.
   */
  public static <T extends Enum<T> & INamedEnum> void check(EnumSet<T> set) {
    final Set<String> names = new HashSet<String>();
    for (INamedEnum e : set) {
      if (!names.add(e.getName())) {
        throw new Error("Duplicate name in enum " + e.getName());
      }
    }
  }
}
