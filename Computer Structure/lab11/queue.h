
#define QUEUE_SIZE 32

typedef struct queue {
  int head;
  int tail;
  int buffer[QUEUE_SIZE];
} queue_t;

void init_queue(queue_t *); // Initialize the
int enqueuestruct (queue_t *, int); // Adds an int to the buffer. This function needs to return 1 when the character was successfully added and 0 when the buffer did not have room to add the character.
int dequeue(queue_t *); // Remove a character from the buffer. If the buffer is empty, 0 should be returned.
int queue_empty(queue_t *); // Returns 1 if the buffer is empty and 0 otherwise.
