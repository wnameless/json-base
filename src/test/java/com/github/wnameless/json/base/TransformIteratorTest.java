package com.github.wnameless.json.base;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransformIteratorTest {

  TransformIterator<String, Integer> iter;

  @BeforeEach
  public void init() {
    iter = new TransformIterator<>(Arrays.asList("1", "2", "3").iterator(),
        Integer::valueOf);
  }

  @Test
  public void testConstructorException() {
    assertThrows(NullPointerException.class,
        () -> new TransformIterator<String, Integer>(
            Arrays.asList("1", "2", "3").iterator(), null));
    assertThrows(NullPointerException.class,
        () -> new TransformIterator<String, Integer>(null, Integer::valueOf));
  }

  @Test
  public void testTransformResult() {
    List<Integer> ints = new ArrayList<>();
    iter.forEachRemaining(i -> ints.add(i));
    assertEquals(3, ints.size());
    assertEquals(Arrays.asList(1, 2, 3), ints);
  }

}
