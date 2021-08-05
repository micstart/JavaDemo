package demo.java.util;

import java.util.*;

public class CollectionDemo {

    public static void main(String[] args) {
        /* 固定长度列表 */
        // List<Integer> oldList = Arrays.asList(1, 2, 3, 4, 5, 6);
        /* 可变列表1 */
        List<Integer> oldList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        oldList.add(null);

        /* 可变列表2 */
        List<Integer> newList = new ArrayList<>();
        Collections.addAll(newList, 4, 5, 6, 7, 8, 9);
        newList.add(null);

        Collection[] result = compare(oldList, newList, true);
        System.out.println("oldList=" + oldList + ", newList=" + newList);
        System.out.println("result=" + Arrays.toString(result));
    }

    /**
     * 比较新旧值返回差异部分：result[0]新增值，result[1]删除值，result[2]相同部分
     *
     * @param oldCollection 旧数据集
     * @param newCollection 新数据集
     * @param needSame      是否需要相同部分
     * @return
     */
    public static Collection[] compare(final Collection oldCollection, final Collection newCollection, boolean needSame) {
        Collection onlyInOld = new ArrayList(oldCollection);
        Collection onlyInNew = new ArrayList(newCollection);
        onlyInOld.removeAll(newCollection);
        onlyInNew.removeAll(oldCollection);
        if (!needSame) {
            return new Collection[]{onlyInNew, onlyInOld};
        }

        Collection sameCollection = new ArrayList(oldCollection);
        sameCollection.retainAll(newCollection);
        return new Collection[]{onlyInNew, onlyInOld, sameCollection};
    }

}
