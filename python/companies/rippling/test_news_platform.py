"""
Comprehensive test suite for news_platform.py
Tests all bug fixes and edge cases to ensure good coverage.
"""

import unittest
from io import StringIO
import sys
from news_platform import NewsPlatform


class TestNewsPlatform(unittest.TestCase):
    
    def setUp(self):
        """Set up test fixtures before each test method."""
        self.platform = NewsPlatform()
        # Add some test articles
        self.platform.add_article("Article 1")
        self.platform.add_article("Article 2") 
        self.platform.add_article("Article 3")
        self.platform.add_article("Article 4")
        self.platform.add_article("Article 5")

    def test_basic_functionality(self):
        """Test basic platform functionality."""
        # Test article creation
        self.assertEqual(len(self.platform.articles), 5)
        self.assertEqual(self.platform.articles[1].name, "Article 1")
        
        # Test user creation and voting
        self.platform.upvote_article(1, 1)
        self.assertEqual(self.platform.users[1].article_votes[1], 0)  # 0 = upvote
        
        self.platform.downvote_article(1, 1)
        self.assertEqual(self.platform.users[1].article_votes[1], 1)  # 1 = downvote

    def test_heap_management_bug_fix(self):
        """Test the heap management bug fix - ensuring articles are properly refilled."""
        # Set up initial state with k=3
        self.platform.k = 3
        
        # Add votes to make articles have different scores
        self.platform.upvote_article(1, 1)  # Article 1: +1
        self.platform.upvote_article(1, 2)  # Article 1: +2
        self.platform.upvote_article(2, 1)  # Article 2: +1
        self.platform.upvote_article(3, 1)  # Article 3: +1
        self.platform.upvote_article(4, 1)  # Article 4: +1
        self.platform.downvote_article(5, 1)  # Article 5: -1
        
        # Get initial analytics to build heap
        captured_output = StringIO()
        sys.stdout = captured_output
        self.platform.print_analytics_summary(3)
        sys.stdout = sys.__stdout__
        
        # Verify heap has 3 articles
        self.assertEqual(len(self.platform.top_k_heap), 3)
        self.assertEqual(len(self.platform.article_in_heap), 3)
        
        # Now reduce score of a top article (should trigger refill logic)
        self.platform.downvote_article(1, 1)  # Article 1: +1 (was +2)
        self.platform.downvote_article(1, 2)  # Article 1: -1 (was +1)
        
        # Make Article 5 better than the heap minimum to test replacement
        self.platform.upvote_article(5, 1)  # Article 5: 0 (was -1)
        self.platform.upvote_article(5, 2)  # Article 5: +1 (was 0)
        self.platform.upvote_article(5, 3)  # Article 5: +2 (was +1)
        
        # Verify heap still has 3 articles and includes new candidates
        self.assertEqual(len(self.platform.top_k_heap), 3)
        self.assertEqual(len(self.platform.article_in_heap), 3)
        
        # Verify Article 5 (score 3) is now in heap since it's better than the heap minimum
        heap_article_ids = {aid for _, aid, _ in self.platform.top_k_heap}
        self.assertIn(5, heap_article_ids)

    def test_heap_size_management_bug_fix(self):
        """Test the heap size management bug fix."""
        # Test that k is properly tracked
        self.platform.print_analytics_summary(3)
        self.assertEqual(self.platform.k, 3)
        
        self.platform.print_analytics_summary(5)
        self.assertEqual(self.platform.k, 5)
        
        # Test that heap size matches k
        self.assertEqual(len(self.platform.top_k_heap), 5)
        self.assertEqual(len(self.platform.article_in_heap), 5)

    def test_performance_improvement_bug_fix(self):
        """Test that analytics summary doesn't rebuild heap unnecessarily."""
        # First call should build heap
        captured_output = StringIO()
        sys.stdout = captured_output
        self.platform.print_analytics_summary(3)
        sys.stdout = sys.__stdout__
        
        heap_before = self.platform.top_k_heap.copy()
        
        # Second call with same k should not rebuild
        sys.stdout = captured_output
        self.platform.print_analytics_summary(3)
        sys.stdout = sys.__stdout__
        
        # Heap should be the same (not rebuilt)
        self.assertEqual(len(heap_before), len(self.platform.top_k_heap))
        
        # Test that changing k rebuilds heap
        sys.stdout = captured_output
        self.platform.print_analytics_summary(2)
        sys.stdout = sys.__stdout__
        
        self.assertEqual(self.platform.k, 2)
        self.assertEqual(len(self.platform.top_k_heap), 2)

    def test_edge_cases(self):
        """Test edge cases and error handling."""
        # Test k <= 0
        captured_output = StringIO()
        sys.stdout = captured_output
        self.platform.print_analytics_summary(0)
        sys.stdout = sys.__stdout__
        self.assertIn("k must be positive", captured_output.getvalue())
        
        # Test k < 0
        captured_output = StringIO()
        sys.stdout = captured_output
        self.platform.print_analytics_summary(-1)
        sys.stdout = sys.__stdout__
        self.assertIn("k must be positive", captured_output.getvalue())
        
        # Test empty articles
        empty_platform = NewsPlatform()
        captured_output = StringIO()
        sys.stdout = captured_output
        empty_platform.print_analytics_summary(3)
        sys.stdout = sys.__stdout__
        self.assertIn("No articles available", captured_output.getvalue())

    def test_vote_flip_tracking(self):
        """Test vote flip tracking functionality."""
        # Test upvote -> downvote flip
        self.platform.upvote_article(1, 1)
        self.platform.downvote_article(1, 1)  # This should count as a flip
        
        user_flips = self.platform.users[1].get_last_three_flips()
        self.assertEqual(len(user_flips), 1)
        self.assertEqual(user_flips[0], 1)
        
        # Test downvote -> upvote flip
        self.platform.upvote_article(1, 1)  # This should count as another flip
        
        user_flips = self.platform.users[1].get_last_three_flips()
        self.assertEqual(len(user_flips), 2)
        self.assertEqual(user_flips[0], 1)  # First flip
        self.assertEqual(user_flips[1], 1)  # Second flip
        
        # Test multiple flips (should keep only last 3)
        self.platform.downvote_article(2, 1)
        self.platform.upvote_article(2, 1)
        self.platform.downvote_article(3, 1)
        self.platform.upvote_article(3, 1)
        
        user_flips = self.platform.users[1].get_last_three_flips()
        self.assertEqual(len(user_flips), 3)  # Should be limited to 3
        self.assertEqual(user_flips[-1], 3)  # Last flip should be article 3

    def test_score_calculation(self):
        """Test score calculation accuracy."""
        # Test single user voting
        self.platform.upvote_article(1, 1)
        score = self.platform._calculate_article_score(1)
        self.assertEqual(score, 1)
        
        self.platform.downvote_article(1, 1)
        score = self.platform._calculate_article_score(1)
        self.assertEqual(score, -1)
        
        # Test multiple users voting
        self.platform.upvote_article(1, 1)
        self.platform.upvote_article(1, 2)
        self.platform.downvote_article(1, 3)
        
        score = self.platform._calculate_article_score(1)
        self.assertEqual(score, 1)  # +1 +1 -1 = 1

    def test_analytics_summary_accuracy(self):
        """Test that analytics summary shows correct top-k articles."""
        # Set up known scores
        self.platform.upvote_article(1, 1)  # Article 1: +1
        self.platform.upvote_article(1, 2)  # Article 1: +2
        self.platform.upvote_article(2, 1)  # Article 2: +1
        self.platform.downvote_article(3, 1)  # Article 3: -1
        self.platform.downvote_article(3, 2)  # Article 3: -2
        
        # Test top 3 articles
        captured_output = StringIO()
        sys.stdout = captured_output
        self.platform.print_analytics_summary(3)
        sys.stdout = sys.__stdout__
        
        output = captured_output.getvalue()
        
        # Should contain the top 3 articles in descending order
        self.assertIn('"Article 1" has a score of 2', output)
        self.assertIn('"Article 2" has a score of 1', output)
        self.assertIn('"Article 4" has a score of 0', output)  # Default score
        
        # Should not contain the lowest scoring article
        self.assertNotIn('"Article 3" has a score of -2', output)

    def test_article_not_found_error(self):
        """Test error handling for invalid article IDs."""
        with self.assertRaises(ValueError):
            self.platform.upvote_article(999, 1)
        
        with self.assertRaises(ValueError):
            self.platform.downvote_article(999, 1)

    def test_user_not_found_error(self):
        """Test error handling for print_last_three_flips with invalid user."""
        with self.assertRaises(ValueError):
            self.platform.print_last_three_flips(999)

    def test_large_k_scenario(self):
        """Test scenario where k is larger than number of articles."""
        # Only 5 articles, but k=10
        captured_output = StringIO()
        sys.stdout = captured_output
        self.platform.print_analytics_summary(10)
        sys.stdout = sys.__stdout__
        
        # Should only have 5 articles in heap
        self.assertEqual(len(self.platform.top_k_heap), 5)
        self.assertEqual(len(self.platform.article_in_heap), 5)

    def test_heap_maintenance_under_vote_changes(self):
        """Test that heap is properly maintained when votes change frequently."""
        # Set up initial state
        self.platform.k = 2
        
        # Make Article 1 the top scorer
        self.platform.upvote_article(1, 1)
        self.platform.upvote_article(1, 2)
        self.platform.upvote_article(1, 3)  # Article 1: +3
        
        # Make Article 2 second
        self.platform.upvote_article(2, 1)
        self.platform.upvote_article(2, 2)  # Article 2: +2
        
        # Get initial analytics
        captured_output = StringIO()
        sys.stdout = captured_output
        self.platform.print_analytics_summary(2)
        sys.stdout = sys.__stdout__
        
        # Verify heap has correct articles
        heap_article_ids = {aid for _, aid, _ in self.platform.top_k_heap}
        self.assertIn(1, heap_article_ids)
        self.assertIn(2, heap_article_ids)
        
        # Now make Article 3 better than Article 2
        self.platform.upvote_article(3, 1)
        self.platform.upvote_article(3, 2)
        self.platform.upvote_article(3, 3)  # Article 3: +3
        
        # Vote should trigger heap update
        self.platform.upvote_article(3, 4)  # Article 3: +4
        
        # Verify heap is updated correctly
        heap_article_ids = {aid for _, aid, _ in self.platform.top_k_heap}
        self.assertIn(1, heap_article_ids)  # Still top
        self.assertIn(3, heap_article_ids)  # Now in heap
        self.assertNotIn(2, heap_article_ids)  # Should be pushed out


if __name__ == '__main__':
    unittest.main()
