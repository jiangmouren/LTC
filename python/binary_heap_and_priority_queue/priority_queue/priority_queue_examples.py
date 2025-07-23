import heapq

pq = []
heapq.heappush(pq, (1, "taskA"))
heapq.heappush(pq, (1, "taskB"))
heapq.heappush(pq, (2, "taskC"))

print(pq)
print(heapq.heappop(pq))  # (1, 'taskA')
print(heapq.heappop(pq))  # (1, 'taskB')
print(heapq.heappop(pq))  # (2, 'taskC')