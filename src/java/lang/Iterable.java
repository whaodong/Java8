/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
package java.lang;

import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;

/**
 * 实现这个接口允许对象成为“for-each loop”语句的目标。 参见
 * <strong>
 * <a href="{@docRoot}/../technotes/guides/language/foreach.html">For-each Loop</a>
 * </strong>
 *
 * @param <T> 迭代器返回的元素类型
 *
 * @since 1.5
 * @jls 14.14.2 The enhanced for statement
 */
public interface Iterable<T> {
    /**
     * 返回类型为{@code T}的元素的迭代器。
     *
     * @return an Iterator.
     */
    Iterator<T> iterator();

    /**
     * 对{@code Iterable}的每个元素执行给定的操作，直到所有元素都被处理完毕或操作抛出异常。
     * 除非实现类另有指定，否则操作将按照迭代的顺序执行(如果指定了迭代顺序)。操作抛出的异常被转发给调用者。
     *
     * @implSpec
     * <p>默认实现的行为如下:
     * <pre>{@code
     *     for (T t : this)
     *         action.accept(t);
     * }</pre>
     *
     * @param action 为每个元素执行的操作
     * @throws NullPointerException 如果指定的操作为空
     * @since 1.8
     */
    default void forEach(Consumer<? super T> action) {
        Objects.requireNonNull(action);
        for (T t : this) {
            action.accept(t);
        }
    }

    /**
     * 在这个{@code Iterable}所描述的元素上创建一个{@link Spliterator}。
     *
     * @implSpec
     * 默认实现创建
     * <em><a href="Spliterator.html#binding">early-binding</a></em>
     * 从可迭代对象的{@code Iterator}中分离迭代器。拆分迭代器继承可迭代对象的迭代器的<em>fail-fast<em>属性。
     *
     * @implNote
     * 默认实现通常应该被覆盖。  由默认实现返回的拆分器具有较差的拆分功能，没有大小，并且不报告任何拆分器特征。
     * 实现类几乎总是可以提供更好的实现。
     *
     * @return a {@code Spliterator}在此描述的元素上
     * {@code Iterable}.
     * @since 1.8
     */
    default Spliterator<T> spliterator() {
        return Spliterators.spliteratorUnknownSize(iterator(), 0);
    }
}
