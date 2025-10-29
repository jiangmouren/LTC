"""
We are building the vote management service for an online news platform, where users can upvote or downvote published articles.
We are interested in what makes users change their minds.
Let's start with the following functionality:
* `add_article (article_name [string])`
   - Each article is given an incremental integer ID when it's added, starting with 1.
* `upvote_article (article_id [integer], user_id [integer])`
   - Assume any user ID is valid, and that the given article ID will have been added.
* `downvote_article (article_id [integer], user_id [integer])`
   - Assume any user ID is valid, and that the given article ID will have been added.
* `print_last_three_flips (user_id [integer])`
   - The titles of the last three unique articles for which a user changed their vote, either from upvote to downvote or downvote to upvote.
For our MVP, consider performance as we will eventually support millions of articles and users.
However, let's not worry about thread safety or persistence for now - store data in memory.
Let's prioritize solving the problem for the last three articles for now.
We can tackle extensibility at a later stage.

library.add_article("An exclusive with Jerry Mander")
library.add_article("The rise of the far middle")
library.add_article("Florida man changes name")
library.add_article("Microplastics found in glitter")
library.upvote_article(1, 1)
library.downvote_article(1, 1)
library.upvote_article(1, 1)
library.downvote_article(2, 1)
library.upvote_article(3, 1)
library.upvote_article(3, 1)
library.upvote_article(3, 2)
library.upvote_article(4, 4)
library.downvote_article(4, 4)
library.upvote_article(3, 4)
library.downvote_article(3, 4)
library.upvote_article(2, 4)
library.downvote_article(2, 4)
library.upvote_article(1, 4)
library.downvote_article(1, 4)
library.downvote_article(1, 4)
library.print_last_three_flips(user_id)

Expected output for user 4 (in order):
An exclusive with Jerry Mander
The rise of the far middle
Florida man changes name


Q2:
Let's add a feature to create a report for our company's Analytics department.
We want to create a report once per day.

* `print_analytics_summary (k: [integer])`
   - The summary should show the top `k` articles, sorted (descending) by the score of an article (upvotes - downvotes).
   - Only count each user's latest vote
   - The summary should include the article titles, and the score for each article, but the formatting does not matter.

We can modify any of the existing functions, but all functions must perform better than `O(N log N)`.

Expected output:

For the case k = 3
"Florida man changes name" has a score of 1
"An exclusive with Jerry Mander" has a score of 0
"Microplastics found in glitter" has a score of -1
"""

from dataclasses import dataclass
from typing import List, Dict
from collections import deque
import heapq

@dataclass
class Article:
    id: int
    name: str
    score: int = 0  # Track current score (upvotes - downvotes)

class User:
    def __init__(self, user_id: int):
        self.user_id = user_id
        self.article_votes: Dict[int, int] = {} # article_id -> vote_count
        self.last_three_flips: deque = deque(maxlen=3) # article_id

    def upvote_article(self, article_id: int):
        """store 0 in the dict for upvote, check if the previous value is 1, meaning a downvote, then add the article_id to the last_three_flips"""
        if article_id not in self.article_votes:
            self.article_votes[article_id] = 0
        if self.article_votes[article_id] == 1:
            self.last_three_flips.append(article_id)
        self.article_votes[article_id] = 0
    
    def downvote_article(self, article_id: int):
        """store 1 in the dict for downvote, check if the previous value is 0, meaning an upvote, then add the article_id to the last_three_flips"""
        if article_id not in self.article_votes:
            self.article_votes[article_id] = 0
        if self.article_votes[article_id] == 0:
            self.last_three_flips.append(article_id)
        self.article_votes[article_id] = 1

    def get_last_three_flips(self) -> List[int]:
        """return the last three articles that have been flipped"""
        return list(self.last_three_flips)

class NewsPlatform:
    def __init__(self):
        self.articles: Dict[int, Article] = {}
        self.users: Dict[int, User] = {}
        self.article_id: int = 0
        self.top_k_heap = []  # Min-heap to track top K articles by score
        self.k = 0  # Current k value for top-k heap
        self.article_in_heap = set()  # Track which articles are in the heap
        self.article_scores = {}  # Cache article scores for efficiency

    def add_article(self, article_name: str):
        self.article_id += 1
        self.articles[self.article_id] = Article(self.article_id, article_name)

    def _update_heap(self, article_id: int, new_score: int):
        """Update the min-heap when an article's score changes"""
        article = self.articles[article_id]
        article.score = new_score
        self.article_scores[article_id] = new_score
        
        # If article is already in heap, we need to remove and re-add it
        if article_id in self.article_in_heap:
            # Find and remove the old entry (O(k) operation, but k is small)
            for i, (score, aid, _) in enumerate(self.top_k_heap):
                if aid == article_id:
                    self.top_k_heap.pop(i)
                    heapq.heapify(self.top_k_heap)  # Re-heapify
                    self.article_in_heap.remove(article_id)
                    break
        
        # Check if article should be in heap
        should_be_in_heap = (
            len(self.top_k_heap) < self.k or 
            (self.top_k_heap and new_score > self.top_k_heap[0][0])
        )
        
        if should_be_in_heap:
            heapq.heappush(self.top_k_heap, (new_score, article_id, article.name))
            self.article_in_heap.add(article_id)
            
            # If heap exceeds size, remove the minimum
            if len(self.top_k_heap) > self.k:
                _, removed_aid, _ = heapq.heappop(self.top_k_heap)
                self.article_in_heap.remove(removed_aid)
        
        # After removing an article, check if other articles should be added
        if self.k > 0:
            self._refill_heap()

    def _calculate_article_score(self, article_id: int) -> int:
        """Calculate the current score of an article based on latest votes from all users"""
        score = 0
        for user in self.users.values():
            if article_id in user.article_votes:
                # 0 = upvote, 1 = downvote
                if user.article_votes[article_id] == 0:
                    score += 1
                else:
                    score -= 1
        return score
    
    def _refill_heap(self):
        """Refill the heap with articles that should be in top-k but aren't currently"""
        # If heap is full, check if we need to replace any articles
        if len(self.top_k_heap) >= self.k and self.k > 0:
            min_heap_score = self.top_k_heap[0][0]
            
            # Find articles not in heap that have higher scores than current minimum
            for aid, score in self.article_scores.items():
                if aid not in self.article_in_heap and score > min_heap_score:
                    # Replace the minimum element
                    _, removed_aid, _ = heapq.heappop(self.top_k_heap)
                    self.article_in_heap.remove(removed_aid)
                    heapq.heappush(self.top_k_heap, (score, aid, self.articles[aid].name))
                    self.article_in_heap.add(aid)
                    # Update min_heap_score for next iteration
                    if self.top_k_heap:
                        min_heap_score = self.top_k_heap[0][0]
        elif len(self.top_k_heap) < self.k and self.k > 0:
            # Heap is not full, add articles until it's full
            min_heap_score = self.top_k_heap[0][0] if self.top_k_heap else float('-inf')
            
            # Find articles not in heap that could qualify
            candidates = []
            for aid, score in self.article_scores.items():
                if aid not in self.article_in_heap and score > min_heap_score:
                    candidates.append((score, aid, self.articles[aid].name))
            
            # Sort by score (descending) and add to heap until full
            candidates.sort(reverse=True)
            for score, aid, name in candidates:
                if len(self.top_k_heap) >= self.k:
                    break
                heapq.heappush(self.top_k_heap, (score, aid, name))
                self.article_in_heap.add(aid)
    
    def upvote_article(self, article_id: int, user_id: int):
        if article_id not in self.articles:
            raise ValueError(f"Article {article_id} not found")
        if user_id not in self.users:
            self.users[user_id] = User(user_id)
        
        # Update user's vote
        self.users[user_id].upvote_article(article_id)
        
        # Recalculate article score and update heap
        new_score = self._calculate_article_score(article_id)
        self._update_heap(article_id, new_score)

    def downvote_article(self, article_id: int, user_id: int):
        if article_id not in self.articles:
            raise ValueError(f"Article {article_id} not found")
        if user_id not in self.users:
            self.users[user_id] = User(user_id)
        
        # Update user's vote
        self.users[user_id].downvote_article(article_id)
        
        # Recalculate article score and update heap
        new_score = self._calculate_article_score(article_id)
        self._update_heap(article_id, new_score)

    def print_last_three_flips(self, user_id: int):
        if user_id not in self.users:
            raise ValueError(f"User {user_id} not found")
        last_three_flips = self.users[user_id].get_last_three_flips()
        for article_id in last_three_flips:
            print(self.articles[article_id].name)

    def print_analytics_summary(self, k: int):
        """Print the top k articles sorted by score (descending)"""
        if k <= 0:
            print("k must be positive")
            return
            
        if not self.articles:
            print("No articles available")
            return
        
        # Update k and rebuild heap if k has changed
        if k != self.k:
            self.k = k
            self.top_k_heap = []
            self.article_in_heap = set()
            
            # Recalculate all scores and build heap
            for article_id, article in self.articles.items():
                score = self._calculate_article_score(article_id)
                article.score = score
                self.article_scores[article_id] = score
                
                # Add to heap if it's one of the top K
                if len(self.top_k_heap) < k:
                    heapq.heappush(self.top_k_heap, (score, article_id, article.name))
                    self.article_in_heap.add(article_id)
                elif score > self.top_k_heap[0][0]:
                    # Replace the minimum element
                    _, removed_aid, _ = heapq.heappop(self.top_k_heap)
                    self.article_in_heap.remove(removed_aid)
                    heapq.heappush(self.top_k_heap, (score, article_id, article.name))
                    self.article_in_heap.add(article_id)
        
        # Extract and sort results (descending order)
        results = []
        heap_copy = self.top_k_heap.copy()
        heap_size = len(heap_copy)
        for i in range(heap_size):
            score, article_id, name = heapq.heappop(heap_copy)
            results.append((score, name))
        
        # Sort in descending order and print
        results.sort(reverse=True)
        for score, name in results:
            print(f'"{name}" has a score of {score}')

if __name__ == "__main__":
    library = NewsPlatform()
    library.add_article("An exclusive with Jerry Mander")
    library.add_article("The rise of the far middle")
    library.add_article("Florida man changes name")
    library.add_article("Microplastics found in glitter")
    library.upvote_article(1, 1)
    library.downvote_article(1, 1)
    library.upvote_article(1, 1)
    library.downvote_article(2, 1)
    library.upvote_article(3, 1)
    library.upvote_article(3, 1)
    library.upvote_article(3, 2)
    library.upvote_article(4, 4)
    library.downvote_article(4, 4)
    library.upvote_article(3, 4)
    library.downvote_article(3, 4)
    library.upvote_article(2, 4)
    library.downvote_article(2, 4)
    library.upvote_article(1, 4)
    library.downvote_article(1, 4)
    library.downvote_article(1, 4)
    
    print("Last three flips for user 4:")
    library.print_last_three_flips(4)
    
    print("\nAnalytics summary (top 3):")
    library.print_analytics_summary(3)
