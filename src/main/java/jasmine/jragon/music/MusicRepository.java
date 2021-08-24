package jasmine.jragon.music;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v1Tag;
import com.mpatric.mp3agic.ID3v24Tag;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MusicRepository {
    private final List<Mp3File> currentMusicList;
    private int index;

    public MusicRepository() {
        currentMusicList = new ArrayList<>();
    }

    public void loadMusic(List<File> fileList) throws IOException, InvalidDataException, UnsupportedTagException {
        index = 0;
        currentMusicList.clear();
        addMusic(fileList);
    }

    public void addMusic(List<File> fileList) throws IOException, InvalidDataException, UnsupportedTagException {
        for (File file : fileList) {
            currentMusicList.add(new Mp3File(file));
        }
    }

    public boolean isOnFirstSong(){
        return index == 0;
    }

    public boolean isOnLastSong(){
        return index == currentMusicList.size() - 1;
    }

    public void toNextSong(){
        if (!isOnLastSong())
            index++;
    }

    public void toPreviousSong() {
        if (!isOnFirstSong())
            index--;
    }
}
