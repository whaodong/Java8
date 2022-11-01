/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.util;

import java.util.function.Consumer;

/**
 *集合上的迭代器。
 * 在Java集合框架中，{@code Iterator}取代了{@link Enumeration}。
 * 迭代器与枚举有两个不同之处:
 *
 * <ul>
 *      <li> 迭代器允许调用者在迭代期间使用定义良好的语义从底层集合中删除元素。
 *      <li> 方法名得到了改进。
 * </ul>
 *
 * <p>This interface is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @param <E> 迭代器返回的元素类型
 *
 * @author  Josh Bloch
 * @see Collection
 * @see ListIterator
 * @see Iterable
 * @since 1.2
 */
public interface Iterator<E> {
    /**
     * 如果迭代有更多的元素，返回{@code true}。
     * (换句话说，如果{@link #next}将返回一个元素而不是抛出异常，则返回{@code true}。)
     *
     * @return {@code true}如果迭代有更多的元素
     */
    boolean hasNext();

    /**
     * 返回迭代中的下一个元素。
     *
     * @return 迭代中的下一个元素
     * @throws NoSuchElementException 如果迭代没有更多的元素
     */
    E next();

    /**
     * 从基础集合中移除此迭代器返回的最后一个元素(可选操作)。
     * 这个方法在每次调用{@link #next}时只能被调用一次。
     * 如果在迭代进行时，以调用此方法以外的任何方式修改了底层集合，则迭代器的行为将不指定。
     *
     * @implSpec
     * 默认实现抛出 {@link UnsupportedOperationException} 的实例，不执行其他操作。
     *
     * @throws UnsupportedOperationException 如果该迭代器不支持{@code remove}操作
     *
     * @throws IllegalStateException 如果 {@code next} 方法尚未被调用，或者 {@code remove} 方法在最后一次调用 {@code next} 方法之后已经被调用
     */
    default void remove() {
        throw new UnsupportedOperationException("remove");
    }

    /**
     * 对每个剩余的元素执行给定的操作，直到处理完所有元素或操作抛出异常。
     * 如果指定了迭代的顺序，则按迭代的顺序执行操作。
     * 操作抛出的异常被转发给调用者。
     *
     * @implSpec
     * <p>默认实现的行为如下:
     * <pre>{@code
     *     while (hasNext())
     *         action.accept(next());
     * }</pre>
     *
     * @param action 为每个元素执行的操作
     * @throws NullPointerException 如果指定的操作为空
     * @since 1.8
     */
    default void forEachRemaining(Consumer<? super E> action) {
        Objects.requireNonNull(action);
        while (hasNext())
            action.accept(next());
    }
}
