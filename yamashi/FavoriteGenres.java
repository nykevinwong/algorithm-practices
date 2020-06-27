
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

class FavoriteGenres implements IInterviewQuestion
{
    public Map<String, List<String>> favoriteGenres(Map<String, List<String>> userSongs, Map<String, List<String>> songGenres)
    {
        Map<String, Integer> genreCount = new HashMap<>();
        Map<String, String> song2Genre = new HashMap<>();
        Map<String, List<String>> m = new HashMap<>();

        for(Map.Entry<String, List<String>> e : songGenres.entrySet())
        {
            for(String song: e.getValue())
            {
                song2Genre.put(song, e.getKey());
            }
        }
         
        System.out.println(song2Genre);

        for(Map.Entry<String, List<String>> e : userSongs.entrySet())
        {
            List<String> songs = e.getValue();
            String singer = e.getKey();
            genreCount.clear();

            int max = Integer.MIN_VALUE;
            for(String song: songs)
            {
                String genre = song2Genre.get(song);
                genreCount.put(genre, genreCount.getOrDefault(genre,0) +1 );
                max = Math.max( max, genreCount.get(genre));
            }

            for(Map.Entry<String,Integer> gc: genreCount.entrySet())
            {
                if(max==gc.getValue()) 
                {
                    m.put(singer, m.getOrDefault(singer, new ArrayList<String>()));
                    m.get(singer).add(gc.getKey());
                }
            }

            genreCount.clear();
        }

        return m;
    }

    public void performTest()
    {
        Map<String, List<String>> userSongs = new HashMap<>();
        Map<String, List<String>> songGenres = new HashMap<>();

        userSongs.put("David", Arrays.asList("song1", "song2", "song3", "song4", "song8"));
        userSongs.put("Emma", Arrays.asList("song5", "song6", "song7"));
        songGenres.put("Rock", Arrays.asList("song1", "song3"));
        songGenres.put("Dubstep", Arrays.asList("song7"));
        songGenres.put("Techno", Arrays.asList("song2", "song4"));
        songGenres.put("Pop", Arrays.asList("song5", "song6"));
        songGenres.put("Jazz", Arrays.asList("song8", "song9"));

        Map<String, List<String>> m = favoriteGenres(userSongs, songGenres);
        System.out.println(m);
    }

    public String toString() { return "Favorite Genres ([N]**) [    https://leetcode.com/discuss/interview-question/373006]: ";}

}
