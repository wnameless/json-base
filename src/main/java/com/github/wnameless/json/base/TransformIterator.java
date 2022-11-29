/*
 *
 * Copyright 2022 Wei-Ming Wu
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 */
package com.github.wnameless.json.base;

import java.util.Iterator;
import java.util.function.Function;

/**
 * An iterator wrapper is made to transform each element inside an iterator.
 * 
 * @author Wei-Ming Wu
 *
 * @param <I>
 *          the type of the element in input iterator
 * @param <O>
 *          the type of the element in output iterator
 */
public final class TransformIterator<I, O> implements Iterator<O> {

  private final Iterator<I> iterator;
  private final Function<I, O> transformer;

  /**
   * Creates a {@link TransformIterator}.
   * 
   * @param iterator
   *          an {@link Iterator}
   * @param transformer
   *          is used to transform each element of the input iterator
   */
  public TransformIterator(Iterator<I> iterator, Function<I, O> transformer) {
    if (iterator == null) throw new NullPointerException();
    if (transformer == null) throw new NullPointerException();
    this.iterator = iterator;
    this.transformer = transformer;
  }

  @Override
  public boolean hasNext() {
    return iterator.hasNext();
  }

  @Override
  public O next() {
    return transformer.apply(iterator.next());
  }

}
