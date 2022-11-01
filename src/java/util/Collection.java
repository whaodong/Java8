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

import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * <i>集合结构<i>根接口。 集合表示一组对象，称为<i>元素<i>。
 * 有些集合允许重复元素，有些则不允许。
 * 有些是有序的，有些是无序的。
 * JDK不提供该接口的任何<i>直接<i>实现: 它提供了更具体的子接口实现，如<tt>Set<tt>和<tt>List<tt>。
 * 该接口通常用于传递集合并在需要最大通用性的地方操作它们。
 *
 * <p><i>Bags<i>或<i>multisets<i>(可能包含重复元素的无序集合)应该直接实现这个接口。
 *
 * <p>所有通用的<tt>Collection<tt>实现类(通常通过它的一个子接口间接实现<tt>Collection<tt>)应该提供两个“标准”构造函数:
 * 一个void(无实参)构造函数，它创建一个空集合;
 * 一个构造函数只有一个参数类型<tt> collection <tt>，它创建一个具有与其实参相同元素的新集合。
 * 实际上，后一个构造函数允许用户复制任何集合，生成所需实现类型的等价集合。
 * 没有办法强制执行这个约定(因为接口不能包含构造函数)，但是Java平台库中的所有通用<tt>Collection<tt>实现都遵守这个约定。
 *
 * <p>此接口中包含的“destructive”方法，即修改它们所操作的集合的方法，如果该集合不支持该操作，则指定抛出<tt>UnsupportedOperationException<tt>。
 * 在这种情况下，如果调用对集合没有影响，这些方法可能(但不是必需)抛出<tt>UnsupportedOperationException<tt>。
 * 例如，如果要添加的集合为空，则在不可修改的集合上调用{@link #addAll(Collection)}方法可能(但不是必须)抛出异常。
 *
 * <p><a name="optional-restrictions">
 * 有些集合实现对它们可能包含的元素有限制。</a> 例如，有些实现禁止空元素，而有些则对其元素的类型有限制。
 * 尝试添加不符合条件的元素会抛出未检查的异常，通常为<tt>NullPointerException<tt>或<tt>ClassCastException<tt>。
 * 试图查询不符合条件的元素可能会抛出异常，或者它可能只是返回false; 有些实现将显示前一种行为，有些将显示后一种行为。
 * 更一般地说，尝试对不符合条件的元素进行操作，如果该操作的完成不会导致将不符合条件的元素插入到集合中，则可能引发异常，也可能成功，这由实现来决定。
 * 这种异常在该接口的规范中被标记为“optional”。
 *
 * <p>由每个集合决定自己的同步策略。
 * 在实现缺乏更强的保证的情况下，调用集合上的任何方法可能会导致未定义的行为，而该集合正被另一个线程突变;
 * 这包括直接调用，将集合传递给可能执行调用的方法，以及使用现有迭代器检查集合。
 *
 * <p>Collections Framework接口中的许多方法都是根据{@link Object#equals(Object) equals}方法定义的。
 * 例如，{@link #contains(Object) contains(Object o)}方法的规范说: "返回<tt>true<tt>当且仅当此集合包含至少一个元素<tt>e<tt>，使
 * <tt>(o==null ? e==null : o.equals(e))</tt>."
 * 这个规范应该<i>而不是<i>被解释为调用<tt>Collection。
 * 包含带有非空参数<tt>的<tt>o<tt>将导致对任何<tt>e<tt>的元素调用<tt>o.equals(e)<tt>。
 * 实现可以自由实现优化，从而避免<tt> equals <tt>调用，例如，首先比较两个元素的哈希码。
 * ({@link Object#hashCode()}规范保证两个哈希码不相等的对象不能相等。)
 * 更一般地说，各种集合框架接口的实现可以自由地利用底层{@link Object}方法的指定行为，只要实现者认为合适。
 *
 * <p>一些执行集合递归遍历的集合操作可能会失败，但对于直接或间接包含集合本身的自引用实例例外。
 * 这包括{@code clone()}、{@code equals()}、{@code hashCode()}和{@code toString()}方法。
 * 实现可以选择性地处理自引用场景，但是大多数当前的实现不这样做。
 *
 * <p>This interface is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @param <E> 此集合中元素的类型
 * @author Josh Bloch
 * @author Neal Gafter
 * @implSpec 默认的方法实现(继承或其他)不应用任何同步协议。
 * 如果一个{@code Collection}实现有一个特定的同步协议，那么它必须覆盖默认的实现来应用该协议。
 * @see Set
 * @see List
 * @see Map
 * @see SortedSet
 * @see SortedMap
 * @see HashSet
 * @see TreeSet
 * @see ArrayList
 * @see LinkedList
 * @see Vector
 * @see Collections
 * @see Arrays
 * @see AbstractCollection
 * @since 1.2
 */

public interface Collection<E> extends Iterable<E> {
    // 查询操作

    /**
     * 返回此集合中的元素个数。
     * 如果此集合包含<tt>Integer.MAX_VALUE<tt>元素，返回<tt>Integer.MAX_VALUE<tt>。
     *
     * @return 集合中元素的数量
     */
    int size();

    /**
     * 如果该集合不包含任何元素，则返回<tt>true<tt>。
     *
     * @return <tt>true<tt>如果这个集合不包含任何元素
     */
    boolean isEmpty();

    /**
     * 如果这个集合包含指定的元素，返回<tt>true<tt>。
     * 更正式的，返回<tt>true<tt>当且仅当这个集合包含至少一个元素<tt>e<tt>，使<tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))<tt>。
     *
     * @param o 元素，其在集合中的存在状态将被测试
     * @return <tt>true<tt>如果集合包含指定的
     * element
     * @throws ClassCastException   如果指定元素的类型与集合不兼容(<a href="#optional-restrictions">optional<a>)
     * @throws NullPointerException 如果指定的元素是空的，并且这个集合不允许空元素(<a href="#optional-restrictions">optional<a>)
     */
    boolean contains(Object o);

    /**
     * 返回此集合中元素的迭代器。
     * 对于元素返回的顺序没有保证(除非这个集合是提供保证的某个类的实例)。
     *
     * @return an <tt>Iterator<tt>在这个集合的元素
     */
    Iterator<E> iterator();

    /**
     * 返回包含此集合中所有元素的数组。
     * 如果该集合对其迭代器返回元素的顺序做出了任何保证，则此方法必须以相同的顺序返回元素。
     *
     * <p>返回的数组将是“safe”，因为这个集合不会维护对它的引用。
     * (换句话说，这个方法必须分配一个新数组，即使这个集合由一个数组支持)。
     * 因此，调用者可以自由修改返回的数组。
     *
     * <p>该方法充当基于数组和基于集合的api之间的桥梁。
     *
     * @return 包含此集合中所有元素的数组
     */
    Object[] toArray();

    /**
     * 返回包含此集合中所有元素的数组;
     * 返回数组的运行时类型为指定数组的运行时类型。
     * 如果集合适合指定的数组，则在其中返回。
     * 否则，将使用指定数组的运行时类型和此集合的大小分配一个新数组。
     *
     * <p>如果这个集合可以放入指定的数组，并且有剩余空间(即数组的元素比这个集合多)，
     * 集合结束后的数组中的元素被设置为<tt>null<tt>。
     * (如果调用者知道这个集合不包含任何<tt>null<tt>元素，这对于确定这个集合只<i> <i>的长度很有用。)
     *
     * <p>如果该集合对其迭代器返回元素的顺序做出了任何保证，则此方法必须以相同的顺序返回元素。
     *
     * <p>与{@link #toArray()}方法一样，该方法充当了基于数组和基于集合的api之间的桥梁。
     * 此外，该方法允许对输出数组的运行时类型进行精确控制，并且在某些情况下，可用于节省分配成本。
     *
     * <p>假设<tt>x<tt>是已知只包含字符串的集合。以下代码可用于将集合转储到新分配的<tt>String<tt>数组中:
     *
     * <pre>
     *     String[] y = x.toArray(new String[0]);</pre>
     * <p>
     * 注意<tt>toArray(new Object[0])</tt>在功能上与<tt>toArray()<tt>相同。
     *
     * @param <T> 要包含集合的数组的运行时类型
     * @param a   这个集合的元素要存储到的数组，如果它足够大的话;否则，将为此目的分配一个相同运行时类型的新数组。
     * @return 包含此集合中所有元素的数组
     * @throws ArrayStoreException  如果指定数组的运行时类型不是此集合中每个元素的运行时类型的超类型
     * @throws NullPointerException 如果指定的数组为空
     */
    <T> T[] toArray(T[] a);

    // 修改操作

    /**
     * 确保该集合包含指定的元素(可选操作)。
     * 如果此集合因调用而改变，则返回<tt>true<tt>。(如果这个集合不允许重复并且已经包含了指定的元素，则返回<tt>false<tt>。)<p>
     * <p>
     * 支持此操作的集合可能会限制向该集合添加什么元素。 特别是，一些集合将拒绝添加<tt>null<tt>元素，而另一些集合将对可添加的元素类型施加限制。
     * 集合类应该在它们的文档中明确指定可以添加什么元素的任何限制。<p>
     * <p>
     * 如果集合由于其他原因而拒绝添加特定的元素，而不是它已经包含了该元素，那么它<i>必须<i>抛出异常(而不是返回<tt>false<tt>)。
     * 这将保持在调用返回后集合始终包含指定元素的不变式。
     *
     * @param e 元素，其存在于此集合中
     * @return <tt>true<tt> 如果这个集合因为调用而改变
     * @throws UnsupportedOperationException 如果此集合不支持<tt>add<tt>操作
     * @throws ClassCastException            如果指定元素的类阻止将其添加到此集合
     * @throws NullPointerException          如果指定的元素为空且此集合不允许空元素
     * @throws IllegalArgumentException      如果元素的某些属性阻止将其添加到此集合
     * @throws IllegalStateException         如果此时由于插入限制而无法添加元素
     */
    boolean add(E e);

    /**
     * 从该集合中删除指定元素的单个实例(如果它存在的话)(可选操作)。
     * 更正式地说，如果这个集合包含一个或多个这样的元素，则删除一个<tt>e<tt>的元素，使<tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))<tt>。
     * 如果集合包含指定的元素，则返回<tt>true<tt>(或者等效地，如果集合因调用而发生更改)。
     *
     * @param o 元素要从此集合中删除(如果存在)
     * @return <tt>true<tt>如果该调用的结果是删除了一个元素
     * @throws ClassCastException            如果指定元素的类型与集合不兼容(<a href="#optional-restrictions">optional<a>)
     * @throws NullPointerException          如果指定的元素是空的，并且这个集合不允许空元素(<a href="#optional-restrictions">optional<a>)
     * @throws UnsupportedOperationException 如果此集合不支持<tt>remove<tt>操作
     */
    boolean remove(Object o);


    // Bulk Operations

    /**
     * 如果该集合包含指定集合中的所有元素，则返回<tt>true<tt>。
     *
     * @param c 集合中要检查的包含
     * @return <tt>true<tt>如果该集合包含指定集合中的所有元素
     * @throws ClassCastException   如果指定集合中的一个或多个元素的类型与该集合不兼容(<a href="#optional-restrictions">optional<a>)
     * @throws NullPointerException 如果指定的集合包含一个或多个空元素，并且该集合不允许空元素(<a href="#optional-restrictions">optional<a>)，
     *                              或者如果指定的集合是空的。
     * @see #contains(Object)
     */
    boolean containsAll(Collection<?> c);

    /**
     * 将指定集合中的所有元素添加到此集合(可选操作)。
     * 如果在操作进行时修改了指定的集合，则此操作的行为未定义。
     * (这意味着如果指定的集合是此集合，且此集合非空，则此调用的行为未定义。)
     *
     * @param c 集合，包含要添加到此集合的元素
     * @return <tt>true<tt>如果这个集合因为调用而改变
     * @throws UnsupportedOperationException 如果此集合不支持<tt>addAll<tt>操作
     * @throws ClassCastException            如果指定集合的元素的类阻止将其添加到此集合
     * @throws NullPointerException          如果指定的集合包含空元素，而该集合不允许空元素，或者指定的集合为空
     * @throws IllegalArgumentException      如果指定集合的某个元素的某些属性阻止将其添加到此集合
     * @throws IllegalStateException         如果由于插入限制，此时不能添加所有元素
     * @see #add(Object)
     */
    boolean addAll(Collection<? extends E> c);

    /**
     * 删除此集合中也包含在指定集合中的所有元素(可选操作)。 此调用返回后，此集合将不包含与指定集合相同的元素。
     *
     * @param c 包含要从此集合中删除的元素的集合
     * @return <tt>true<tt>如果这个集合因为调用而改变
     * @throws UnsupportedOperationException 如果这个集合不支持<tt>removeAll<tt>方法
     * @throws ClassCastException            如果集合中一个或多个元素的类型与指定集合不兼容(<a href="#optional-restrictions">optional<a>)
     * @throws NullPointerException          如果该集合包含一个或多个空元素，并且指定的集合不支持空元素(<a href="#optional-restrictions">optional<a>)，
     *                                       或者指定的集合为空
     * @see #remove(Object)
     * @see #contains(Object)
     */
    boolean removeAll(Collection<?> c);

    /**
     * 删除此集合中满足给定谓词的所有元素。
     * 迭代期间或谓词抛出的错误或运行时异常被转发给调用者。
     *
     * @param filter 一个谓词，为要删除的元素返回{@code true}
     * @return {@code true}如果删除了任何元素
     * @throws NullPointerException          如果指定的筛选器为空
     * @throws UnsupportedOperationException 如果不能从此集合中删除元素。如果不能删除匹配的元素，或者通常不支持删除，则实现可能抛出此异常。
     * @implSpec 默认实现使用
     * its {@link #iterator}.  使用{@link Iterator#remove()}删除每个匹配元素。
     * 如果集合的迭代器不支持删除，则将在第一个匹配的元素上抛出{@code UnsupportedOperationException}。
     * @since 1.8
     */
    default boolean removeIf(Predicate<? super E> filter) {
        Objects.requireNonNull(filter);
        boolean removed = false;
        final Iterator<E> each = iterator();
        while (each.hasNext()) {
            if (filter.test(each.next())) {
                each.remove();
                removed = true;
            }
        }
        return removed;
    }

    /**
     * 只保留此集合中包含在指定集合中的元素(可选操作)。  换句话说，从该集合中删除指定集合中不包含的所有元素。
     *
     * @param c 包含要在此集合中保留的元素的集合
     * @return <tt>true<tt>如果这个集合因为调用而改变
     * @throws UnsupportedOperationException 如果这个集合不支持<tt>retainAll<tt>操作
     * @throws ClassCastException            如果集合中一个或多个元素的类型与指定集合不兼容(<a href="#optional-restrictions">optional<a>)
     * @throws NullPointerException          如果该集合包含一个或多个空元素，
     *                                       并且指定的集合不允许空元素(<a href="#optional-restrictions">optional<a>)，或者指定的集合为空
     * @see #remove(Object)
     * @see #contains(Object)
     */
    boolean retainAll(Collection<?> c);

    /**
     * 从该集合中删除所有元素(可选操作)。该方法返回后，集合将为空。
     *
     * @throws UnsupportedOperationException 如果此集合不支持<tt>clear<tt>操作
     */
    void clear();


    // 比较和散列

    /**
     * 通常我们在这里会去重写eq和hashCode
     * 比较指定对象与此集合是否相等。 <p>
     * <p>
     * 而<tt>集合<tt>接口没有在<tt>对象的总契约中添加任何规定。
     * 如果程序员“直接”实现<tt>Collection<tt>接口(换句话说，创建一个类，它是<tt>Collection<tt>，但不是<tt>Set<tt>或<tt>List<tt>)，
     * 那么如果他们选择覆盖<tt>Object.equals<tt>，他们必须小心。
     * 没有必要这样做，最简单的做法是依赖<tt>Object<tt>的实现，但是实现者可能希望实现一个“值比较”来代替默认的“引用比较”。
     * (<tt>List<tt>和<tt>Set<tt>接口要求这样的值比较)<p>
     * <p>
     * <tt>Object.equals</tt>方法规定Equals必须是对称的(换句话说，<tt>a.equals(b)</tt>当且仅当<tt>b.equals(a)</tt>)。
     * <tt>List.equals</tt>和<tt>Set.equals</tt>表示列表只等于其他列表，集合只等于其他集合。
     * 因此，对于既不实现<tt>List<tt>也不实现<tt>Set<tt>接口的集合类，自定义<tt>equals<tt>方法在与任何列表或集合比较时必须返回<tt>false<tt>。
     * (根据同样的逻辑，不可能编写一个能够正确实现<tt>Set<tt>和<tt>List<tt>接口的类。)
     *
     * @param o 要与此集合比较是否相等的对象
     * @return <tt>true<tt>如果指定的对象等于这个
     * collection
     * @see Object#equals(Object)
     * @see Set#equals(Object)
     * @see List#equals(Object)
     */
    boolean equals(Object o);

    /**
     * 返回此集合的哈希码值。 而<tt>Collection<tt>接口没有在<tt>Object.hashCode<tt>方法时，
     * 程序员应该注意任何覆盖<tt>Object.equals<tt>方法还必须覆盖<tt>Object.hashCode<tt>方法，以满足<tt>Object.hashCode<tt>。
     * 特别是<tt>c1.equals(c2)<tt>意味着<tt>c1.hashCode()==c2.hashcode()<tt>。
     *
     * @return 此集合的哈希码值
     * @see Object#hashCode()
     * @see Object#equals(Object)
     */
    int hashCode();

    /**
     * 分割迭代器
     * 在此集合中的元素上创建一个{@link Spliterator}。
     * <p>
     * 实现应该记录由拆分器报告的特征值。
     * 如果拆分器报告{@link Spliterator#SIZED}并且这个集合不包含任何元素，则不需要报告这样的特征值。
     *
     * <p>默认实现应该被可以返回更有效的拆分器的子类所覆盖。
     * 为了保持{@link #stream()}和{@link #parallelStream()}方法预期的惰性行为，
     * 拆分器应该要么具有{@code IMMUTABLE}或{@code CONCURRENT}的特征，要么是<em><a href="Spliterator.html#binding">late-binding<a><em>。
     * 如果这些都不实际，覆盖类应该描述拆分器的绑定和结构干扰的文档化策略，
     * 并应该覆盖{@link #stream()}和{@link #parallelStream()}方法，使用拆分器的{@code Supplier}创建流，如下所示:
     * <pre>{@code
     *     Stream<E> s = StreamSupport.stream(() -> spliterator(), spliteratorCharacteristics)
     * }</pre>
     * <p>这些要求确保由{@link #stream()}和{@link #parallelStream()}方法产生的流将反映终端流操作启动时集合的内容。
     *
     * @return a {@code Spliterator}在这个集合的元素上
     * @implSpec 默认实现从集合的{@code迭代器}创建一个<em><a href=" spliterator .html#binding">late-binding<a><em>拆分器。
     * 拆分器继承集合迭代器的<em>fail-fast<em>属性。
     * 创建的{@code Spliterator}报告{@link Spliterator#SIZED}。
     * @implNote 创建的{@code Spliterator}额外报告{@link Spliterator#SUBSIZED}.
     *
     * <p>如果拆分器不包含任何元素，那么除了{@code SIZED}和{@code SUBSIZED}之外的附加特征值的报告就不能帮助客户端控制、专门化或简化计算。
     * 然而，这确实允许共享使用一个不可变的空拆分器实例(参见{@link Spliterators#emptySpliterator()})用于空集合，并允许客户端确定这样的拆分器是否包含任何元素。
     * @since 1.8
     */
    @Override
    default Spliterator<E> spliterator() {
        return Spliterators.spliterator(this, 0);
    }

    /**
     * 返回以此集合为源的顺序{@code Stream}
     *
     * <p>当{@link #spliterator()}方法不能返回{@code IMMUTABLE}、
     * {@code CONCURRENT}或<em>后期绑定<em>的拆分器时，应该重写此方法。(详见{@link #spliterator()})
     *
     * @return 在这个集合的元素上使用顺序的{@代码流}
     * @implSpec 默认实现从集合的{@code Spliterator}创建一个顺序的{@code Stream}
     * @since 1.8
     */
    default Stream<E> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    /**
     * 返回一个可能是并行的{@code Stream}，并将此集合作为其源。 允许此方法返回顺序流。
     *
     * <p>当{@link #spliterator()}方法不能返回{@code IMMUTABLE}、
     * {@code CONCURRENT}或<em>后期绑定<em>的拆分器时，应该重写此方法。(详见{@link #spliterator()})
     *
     * @return 在这个集合的元素上可能并行{@code Stream}
     * @implSpec 默认实现从集合的{@code Spliterator}创建一个并行{@code Stream}
     * @since 1.8
     */
    default Stream<E> parallelStream() {
        return StreamSupport.stream(spliterator(), true);
    }
}
