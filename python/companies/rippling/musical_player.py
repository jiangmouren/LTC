"""
Cloud-based Music Player System - Object-Oriented Implementation

Part 1:
We are building a cloud-based music player, like Spotify.
Start with the following functionality:
`add_song(song_title: str]) -> int`
    - A song is given an incremental integer ID when it's added, starting with 1
`play_song (song_id:int, user_id:int)`
    - Assume any user ID is valid, and that the given song ID has been added
`print_analytics_summary ()`
    - This is used for a report, created once per day for our company's Analytics department.
    - The summary should be sorted (descending) by the number of unique users who have played each song.
    - The summary should include the song titles, and the number of unique users, but the formatting does not matter.

Part 2:
We now want to allow users to see their most recently played songs.
Implement the following function:
`last_three_played_song_titles(user_id:int)` 
    - Returns the titles of the last three unique played songs for the given user(ordered, most recent first)

Prioritize solving the problem for the last three played songs for now.
We can tackle extensibility at a later stage.

For our MVP, consider performance as we will eventually support millions of songs and users.
However, let's not worry about thread safety or persistence for now - store data in memory.

TODO:
Part III
​print_top_k_analytics_summary()
"""

from collections import OrderedDict
from typing import List, Dict


class Song:
    """Represents a song in the music player system."""
    
    def __init__(self, song_id: int, title: str):
        self.song_id = song_id
        self.title = title
        self.unique_users = set()  # Track unique users who played this song
    
    def add_user_play(self, user_id: int) -> None:
        """Record that a user played this song."""
        self.unique_users.add(user_id)
    
    def get_unique_user_count(self) -> int:
        """Get the number of unique users who played this song."""
        return len(self.unique_users)


class User:
    """Represents a user in the music player system."""
    
    def __init__(self, user_id: int):
        self.user_id = user_id
        # Use OrderedDict to maintain insertion order (most recent first)
        # Key: song_id, Value: song_title
        self.recent_songs = OrderedDict()
    
    def play_song(self, song_id: int, song_title: str) -> None:
        """Record that this user played a song."""
        # If song already exists, move it to the beginning (most recent)
        if song_id in self.recent_songs:
            self.recent_songs.move_to_end(song_id, last=False)
        else:
            # Add new song to the end first
            self.recent_songs[song_id] = song_title
            # Then move it to the beginning (most recent)
            self.recent_songs.move_to_end(song_id, last=False)
            
            # Keep only last 3 unique songs - remove oldest if needed
            if len(self.recent_songs) > 3:
                self.recent_songs.popitem(last=True)  # Remove the oldest (last item)
    
    def get_last_three_song_titles(self) -> List[str]:
        """Get the titles of the last three unique played songs (most recent first)."""
        return list(self.recent_songs.values())


class MusicPlayer:
    """Main music player system that orchestrates songs, users, and analytics."""
    
    def __init__(self):
        self.songs: Dict[int, Song] = {}  # song_id -> Song object
        self.users: Dict[int, User] = {}  # user_id -> User object
        self.next_song_id = 1
    
    def add_song(self, song_title: str) -> int:
        """
        Add a new song to the system.
        
        Args:
            song_title: The title of the song to add
            
        Returns:
            The incremental integer ID assigned to the song
        """
        song_id = self.next_song_id
        self.songs[song_id] = Song(song_id, song_title)
        self.next_song_id += 1
        return song_id
    
    def play_song(self, song_id: int, user_id: int) -> None:
        """
        Record that a user played a song.
        
        Args:
            song_id: The ID of the song being played
            user_id: The ID of the user playing the song
        """
        # Get or create user
        if user_id not in self.users:
            self.users[user_id] = User(user_id)
        
        user = self.users[user_id]
        song = self.songs[song_id]
        
        # Record the play
        song.add_user_play(user_id)
        user.play_song(song_id, song.title)
    
    def print_analytics_summary(self) -> None:
        """
        Print analytics summary sorted by number of unique users (descending).
        Shows song titles and unique user counts.
        """
        # Sort songs by unique user count (descending)
        sorted_songs = sorted(
            self.songs.values(),
            key=lambda song: song.get_unique_user_count(),
            reverse=True
        )
        
        print("Analytics Summary (sorted by unique users, descending):")
        print("-" * 60)
        for song in sorted_songs:
            print(f"'{song.title}': {song.get_unique_user_count()} unique users")
    
    def last_three_played_song_titles(self, user_id: int) -> List[str]:
        """
        Get the titles of the last three unique played songs for a user.
        
        Args:
            user_id: The ID of the user
            
        Returns:
            List of song titles (most recent first)
        """
        if user_id not in self.users:
            return []
        
        return self.users[user_id].get_last_three_song_titles()


# Example usage and testing
if __name__ == "__main__":
    # Create music player instance
    player = MusicPlayer()
    
    # Add some songs
    song1_id = player.add_song("Bohemian Rhapsody")
    song2_id = player.add_song("Stairway to Heaven")
    song3_id = player.add_song("Hello, Goodbye")
    song4_id = player.add_song("Imagine")
    
    print(f"Added songs with IDs: {song1_id}, {song2_id}, {song3_id}, {song4_id}")
    
    # Simulate some plays
    print("\n--- Simulating song plays ---")
    player.play_song(song1_id, 1)  # User 1 plays Bohemian Rhapsody
    player.play_song(song2_id, 1)  # User 1 plays Stairway to Heaven
    player.play_song(song1_id, 2)  # User 2 plays Bohemian Rhapsody
    player.play_song(song3_id, 1)  # User 1 plays Hello, Goodbye
    player.play_song(song1_id, 3)  # User 3 plays Bohemian Rhapsody
    player.play_song(song2_id, 2)  # User 2 plays Stairway to Heaven
    
    # Print analytics summary
    print("\n--- Analytics Summary ---")
    player.print_analytics_summary()
    
    # Test last three played songs for user 1
    print("\n--- Last three played songs for User 1 ---")
    last_three = player.last_three_played_song_titles(1)
    for i, title in enumerate(last_three, 1):
        print(f"{i}. {title}")
    
    # Test with more plays to verify ordering
    print("\n--- Additional plays to test ordering ---")
    player.play_song(song2_id, 1)  # User 1 plays Stairway to Heaven again (should move to front)
    player.play_song(song4_id, 1)  # User 1 plays Imagine
    
    last_three_updated = player.last_three_played_song_titles(1)
    print("Updated last three songs for User 1:")
    for i, title in enumerate(last_three_updated, 1):
        print(f"{i}. {title}")