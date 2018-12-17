package com.base.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;

import org.junit.Test;

/**
 * Object工具测试类
 */
public class ObjectUtilTest {
    /**
     * 测试defaultValue方法
     */
    @Test
    public void testDefaultValue() {
        Assert.assertTrue(ObjectUtil.defaultValue(byte.class) == 0);
        Assert.assertTrue(ObjectUtil.defaultValue(short.class) == 0);
        Assert.assertTrue(ObjectUtil.defaultValue(int.class) == 0);
        Assert.assertTrue(ObjectUtil.defaultValue(long.class) == 0L);
        Assert.assertTrue(ObjectUtil.defaultValue(float.class) == 0f);
        Assert.assertTrue(ObjectUtil.defaultValue(double.class) == 0d);
        Assert.assertTrue(ObjectUtil.defaultValue(char.class) == '\u0000');
        Assert.assertTrue(ObjectUtil.defaultValue(Object.class) == null);
        Assert.assertTrue(ObjectUtil.defaultValue(null) == null);
    }

    /**
     * 测试isEqual方法
     */
    @Test
    public void testIsEqual() {
        Object obj1 = new Object();
        Object obj2 = new Object();

        // 1
        Assert.assertTrue(ObjectUtil.isEqual(obj1, obj1));
        Assert.assertTrue(ObjectUtil.isEqual(null, null));
        Assert.assertFalse(ObjectUtil.isEqual(obj1, obj2));
        Assert.assertFalse(ObjectUtil.isEqual(obj1, null));
        Assert.assertFalse(ObjectUtil.isEqual(null, obj2));

        // 2
        Assert.assertTrue(ObjectUtil.isEqual(obj1, obj1, true));
        Assert.assertTrue(ObjectUtil.isEqual(obj1, obj1, false));
        Assert.assertTrue(ObjectUtil.isEqual(null, null, true)); // 都为null表示相同
        Assert.assertFalse(ObjectUtil.isEqual(null, null, false)); // 都为null表示不相同
        Assert.assertFalse(ObjectUtil.isEqual(obj1, obj2, true));
        Assert.assertFalse(ObjectUtil.isEqual(obj1, obj2, false));
        Assert.assertFalse(ObjectUtil.isEqual(obj1, null, true));
        Assert.assertFalse(ObjectUtil.isEqual(obj1, null, false));
        Assert.assertFalse(ObjectUtil.isEqual(null, obj2, true));
        Assert.assertFalse(ObjectUtil.isEqual(null, obj2, false));
    }

    /**
     * 测试isNotEqual方法
     */
    @Test
    public void testIsNotEqual() {
        Object obj1 = new Object();
        Object obj2 = new Object();

        // 1
        Assert.assertFalse(ObjectUtil.isNotEqual(obj1, obj1));
        Assert.assertFalse(ObjectUtil.isNotEqual(null, null));
        Assert.assertTrue(ObjectUtil.isNotEqual(obj1, obj2));
        Assert.assertTrue(ObjectUtil.isNotEqual(obj1, null));
        Assert.assertTrue(ObjectUtil.isNotEqual(null, obj2));

        // 2
        Assert.assertFalse(ObjectUtil.isNotEqual(obj1, obj1, true));
        Assert.assertFalse(ObjectUtil.isNotEqual(obj1, obj1, false));
        Assert.assertFalse(ObjectUtil.isNotEqual(null, null, true)); // 都为null表示相同
        Assert.assertTrue(ObjectUtil.isNotEqual(null, null, false)); // 都为null表示不相同
        Assert.assertTrue(ObjectUtil.isNotEqual(obj1, obj2, true));
        Assert.assertTrue(ObjectUtil.isNotEqual(obj1, obj2, false));
        Assert.assertTrue(ObjectUtil.isNotEqual(obj1, null, true));
        Assert.assertTrue(ObjectUtil.isNotEqual(obj1, null, false));
        Assert.assertTrue(ObjectUtil.isNotEqual(null, obj2, true));
        Assert.assertTrue(ObjectUtil.isNotEqual(null, obj2, false));
    }

    /**
     * 测试isEnqualAny方法
     */
    @Test
    public void testIsEqualAny() {
        final Animal a1 = new Animal(1);
        final Animal a2 = new Animal(1);
        final Animal a3 = new Animal(2);

        Assert.assertTrue(ObjectUtil.isEqualAny(a1, a3, null, a2));
        Assert.assertFalse(ObjectUtil.isEqualAny(a1, a3, null));
        Assert.assertTrue(ObjectUtil.isEqualAny("abc", "a", "b", null, "abc"));
        Assert.assertFalse(ObjectUtil.isEqualAny("abc", "d", null));
        Assert.assertFalse(ObjectUtil.isEqualAny("abc", (String) null));
        Assert.assertFalse(ObjectUtil.isEqualAny("abc"));
        Assert.assertFalse(ObjectUtil.isEqualAny(null));
        Assert.assertFalse(ObjectUtil.isEqualAny(null, "d"));
        Assert.assertTrue(ObjectUtil.isEqualAny(null, "a", (Object) null, "1"));
    }

    /**
     * 测试isEnqualAll方法
     */
    @Test
    public void testIsEqualAll() {
        final Animal a1 = new Animal(1);
        final Animal a2 = new Animal(1);
        final Animal a3 = new Animal(2);

        Assert.assertTrue(ObjectUtil.isEqualAll(a1, a1, a2));
        Assert.assertFalse(ObjectUtil.isEqualAll(a1, a1, a2, a3));
        Assert.assertTrue(ObjectUtil.isEqualAll("abc", "abc", new String("abc")));
        Assert.assertFalse(ObjectUtil.isEqualAll("abc", "a", "d"));
        Assert.assertFalse(ObjectUtil.isEqualAll("abc", "a", (String) null));
        Assert.assertFalse(ObjectUtil.isEqualAll("abc"));
        Assert.assertFalse(ObjectUtil.isEqualAll(null));
        Assert.assertFalse(ObjectUtil.isEqualAll(null, "d"));
        Assert.assertTrue(ObjectUtil.isEqualAll(null, (Object) null, (String) null));
    }

    /**
     * 测试toString方法
     */
    @Test
    public void testToString() {
        Assert.assertEquals("abc", ObjectUtil.toString("abc"));
        Assert.assertNotNull(ObjectUtil.toString(new Object()));
        Assert.assertNull(ObjectUtil.toString(null));
    }

    /**
     * 测试isSame方法
     */
    @Test
    public void testIsSame() {
        Person p1 = new Person("Mr.X", 30);
        Person p2 = new Person("Mr.X", 60);
        Animal a1 = new Animal(30);
        Animal a2 = new Animal(10);

        // exact
        // 1
        Assert.assertTrue(ObjectUtil.isSame(p1, p1));
        Assert.assertTrue(ObjectUtil.isSame(p1, p1, false));
        Assert.assertTrue(ObjectUtil.isSame(p1, p1, "name"));
        Assert.assertTrue(ObjectUtil.isSame(p1, p1, "name", "age"));
        Assert.assertFalse(ObjectUtil.isSame(p1, p1, "name", "age", "sex"));

        // 2
        Assert.assertFalse(ObjectUtil.isSame(p1, p2));
        Assert.assertFalse(ObjectUtil.isSame(p1, p2, false));
        Assert.assertTrue(ObjectUtil.isSame(p1, p2, "name"));
        Assert.assertFalse(ObjectUtil.isSame(p1, p2, "name", "age"));
        Assert.assertFalse(ObjectUtil.isSame(p1, p2, "name", "age", "sex"));
        Assert.assertFalse(ObjectUtil.isSame(p1, p2, "name", "sex"));
        Assert.assertFalse(ObjectUtil.isSame(p1, p1, "???"));

        // 3
        Assert.assertFalse(ObjectUtil.isSame(p1, a1));
        Assert.assertFalse(ObjectUtil.isSame(p1, a1, false));
        Assert.assertFalse(ObjectUtil.isSame(p1, a1, "name"));
        Assert.assertTrue(ObjectUtil.isSame(p1, a1, "age"));
        Assert.assertFalse(ObjectUtil.isSame(p1, a1, "???"));

        // 4
        Assert.assertFalse(ObjectUtil.isSame(a1, a2));
        Assert.assertFalse(ObjectUtil.isSame(a1, a2, false));
        Assert.assertFalse(ObjectUtil.isSame(a1, a2, "name"));

        // 5
        Assert.assertFalse(ObjectUtil.isSame(p1, null));
        Assert.assertFalse(ObjectUtil.isSame(null, p2));
        Assert.assertFalse(ObjectUtil.isSame(null, null));

        // lenient
        // 1
        Assert.assertTrue(ObjectUtil.isSame(p1, p1, true));
        Assert.assertTrue(ObjectUtil.isSame(p1, p1, true, "name"));
        Assert.assertTrue(ObjectUtil.isSame(p1, p1, true, "name", "age"));
        Assert.assertTrue(ObjectUtil.isSame(p1, p1, true, "name", "age", "sex"));
        Assert.assertTrue(ObjectUtil.isSame(p1, p1, true, "name", "sex"));
        Assert.assertTrue(ObjectUtil.isSame(p1, p1, true, "???"));

        // 2
        Assert.assertFalse(ObjectUtil.isSame(p1, p2, true));
        Assert.assertTrue(ObjectUtil.isSame(p1, p2, true, "name"));
        Assert.assertFalse(ObjectUtil.isSame(p1, p2, true, "name", "age"));
        Assert.assertFalse(ObjectUtil.isSame(p1, p2, true, "name", "age", "sex"));

        // 3
        Assert.assertTrue(ObjectUtil.isSame(p1, a1, true));
        Assert.assertTrue(ObjectUtil.isSame(p1, a1, true, "name"));
        Assert.assertTrue(ObjectUtil.isSame(p1, a1, true, "age"));
        Assert.assertTrue(ObjectUtil.isSame(p1, a1, true, "???"));

        // 4
        Assert.assertFalse(ObjectUtil.isSame(a1, a2, true));
        Assert.assertTrue(ObjectUtil.isSame(a1, a2, true, "name"));

        // 5
        Assert.assertFalse(ObjectUtil.isSame(p1, null, true));
        Assert.assertFalse(ObjectUtil.isSame(null, p2, true));
        Assert.assertTrue(ObjectUtil.isSame(null, null, true));
    }

    /**
     * 测试toMap方法
     */
    @Test
    public void testToMap() {
        List<Person> persons = new ArrayList<Person>();
        persons.add(new Person("A", 1));
        persons.add(new Person("B", 2));
        persons.add(new Person("C", 3));
        persons.add(new Person("C", 4));

        // toMap
        Map<String, Person> map = ObjectUtil.toMap(persons, "name", String.class);
        Assert.assertEquals(3, map.size());
        Assert.assertEquals(persons.get(0), map.get("A"));
        Assert.assertEquals(persons.get(1), map.get("B"));
        Assert.assertEquals(persons.get(3), map.get("C")); // overwrite
        Assert.assertNull(map.get("D"));

        // toMapWithListValue
        Map<String, List<Person>> map2 = ObjectUtil.toMapWithListValue(persons, "name", String.class);
        Assert.assertEquals(3, map.size());
        Assert.assertEquals(persons.get(0), map2.get("A").get(0));
        Assert.assertEquals(persons.get(1), map2.get("B").get(0));
        Assert.assertEquals(persons.get(2), map2.get("C").get(0));
        Assert.assertEquals(persons.get(3), map2.get("C").get(1));
        Assert.assertNull(map.get("D"));
    }

    /**
     * 测试getPropSet/List方法
     */
    @Test
    public void testGetPropSetOrList() {
        List<Person> persons = new ArrayList<Person>();
        persons.add(new Person("A", 1));
        persons.add(new Person("B", 2));
        persons.add(new Person("C", 3));
        persons.add(new Person("C", 4));

        // getPropSet
        Set<String> propSet = ObjectUtil.getPropSet(persons, "name");
        Iterator<String> i = propSet.iterator();

        Assert.assertEquals(3, propSet.size());
        Assert.assertEquals("A", i.next());
        Assert.assertEquals("B", i.next());
        Assert.assertEquals("C", i.next());

        // getPropList
        List<String> propList = ObjectUtil.getPropList(persons, "name");
        i = propList.iterator();

        Assert.assertEquals(4, propList.size());
        Assert.assertEquals("A", i.next());
        Assert.assertEquals("B", i.next());
        Assert.assertEquals("C", i.next());
        Assert.assertEquals("C", i.next());
    }

    /**
     *
     */
    static class Person extends Animal {
        private String name;

        public Person() {
        }

        public Person(String name, int age) {
            super(age);

            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean equals(Object obj) {
            if ((obj instanceof Person) == false) {
                return false;
            }

            return (this.getAge() == ((Person) obj).getAge() && ObjectUtil.isEqual(name, ((Person) obj).name));
        }
    }

    /**
     *
     */
    static class Animal {
        private int age;

        public Animal() {
        }

        public Animal(int age) {
            this.age = age;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public boolean equals(Object obj) {
            if ((obj instanceof Animal) == false) {
                return false;
            }

            return (this.age == ((Animal) obj).age);
        }
    }
}
