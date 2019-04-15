#include "queue.h"

// Initialize the
void init_queue(queue_t *queue) {
  queue->head = 0;
  queue->tail = 0;
}

// Adds an int to the buffer. This function needs to return 1 when the character was successfully added and 0 when the buffer did not have room to add the character.
int enqueue(queue_t *queue, int num) { 
  if ((queue->tail + 1) % QUEUE_SIZE == queue->head)
    return 0;
  queue->buffer[queue->tail++] = num;
  queue->tail %= QUEUE_SIZE;
  return 1;
}

// Remove a character from the buffer. If the buffer is empty, 0 should be returned.
int dequeue(queue_t *queue) { 
  if (queue_empty(queue))
    return 0;
  int data = queue->buffer[queue->head++];
  queue->head %= QUEUE_SIZE;
  return data;
}

// Returns 1 if the buffer is empty and 0 otherwise.
int queue_empty(queue_t *queue) { 
  return (queue->tail == queue->head) ? 1 : 0;
}
