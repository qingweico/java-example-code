package algorithm.queue;

import algorithm.heap.MaxHeap;

/**
 * @author zqw
 * @date 2021/10/29
 */
public class PriorityQueue<E extends Comparable<E>> implements Queue<E>{

   private final MaxHeap<E> heap;
   public PriorityQueue() {
      heap =  new MaxHeap<>();
   }
   @Override
   public int size() {
      return heap.size();
   }

   @Override
   public boolean isEmpty() {
      return heap.isEmpty();
   }

   @Override
   public void enqueue(E e) {
      heap.add(e);
   }

   @Override
   public E dequeue() {
      return heap.extractMax();
   }

   @Override
   public E front() {
      return heap.top();
   }
}
