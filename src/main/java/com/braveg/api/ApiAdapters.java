package com.braveg.api;

import com.braveg.api.adapters.BugsAPI;
import com.braveg.api.adapters.FloAPI;
import com.braveg.api.adapters.GenieAPI;
import com.braveg.api.adapters.Melon24API;
import com.braveg.api.adapters.MelonRealAPI;
import com.braveg.api.adapters.VibeAPI;
import com.braveg.api.adapters.YoutubeMusicAPI;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApiAdapters {
    public static List<RankInfo> melon24;
    public static List<RankInfo> melonReal;
    public static List<RankInfo> youtubeMusic;
    public static List<RankInfo> bugs;
    public static List<RankInfo> genie;
    public static List<RankInfo> flo;
    public static List<RankInfo> vibe;

    public static void storeMemoryList() {
        melon24      = null;
        melonReal    = null;
        youtubeMusic = null;
        bugs         = null;
        genie        = null;
        flo          = null;
        vibe         = null;
        // when socket connection failed -> try again only once...
        try{melon24      = Melon24API.get();     } catch (Exception e) {melon24      = Melon24API.get();     } finally{melon24      = Melon24API.get();     }
        try{melonReal    = MelonRealAPI.get();   } catch (Exception e) {melonReal    = MelonRealAPI.get();   } finally{melonReal    = MelonRealAPI.get();   }
        try{youtubeMusic = YoutubeMusicAPI.get();} catch (Exception e) {youtubeMusic = YoutubeMusicAPI.get();} finally{youtubeMusic = YoutubeMusicAPI.get();}
        try{bugs         = BugsAPI.get();        } catch (Exception e) {bugs         = BugsAPI.get();        } finally{bugs         = BugsAPI.get();        }
        try{genie        = GenieAPI.get();       } catch (Exception e) {genie        = GenieAPI.get();       } finally{genie        = GenieAPI.get();       }
        try{flo          = FloAPI.get();         } catch (Exception e) {flo          = FloAPI.get();         } finally{flo          = FloAPI.get();         }
        try{vibe         = VibeAPI.get();        } catch (Exception e) {vibe         = VibeAPI.get();        } finally{vibe         = VibeAPI.get();        }
    }

    public List<RankInfo> separator(String siteName) {
        List<RankInfo> result = new ArrayList<>();
        switch (siteName){
            case "melon24":        result = melon24     ; break;
            case "melonreal":      result = melonReal   ; break;
            case "youtubemusic":   result = youtubeMusic; break;
            case "bugs":           result = bugs        ; break;
            case "genie":          result = genie       ; break;
            case "flo":            result = flo         ; break;
            case "vibe":           result = vibe        ; break;
            default: return null;
        }
        return  result;
    }

    // TODO - 순위 끊기 stream
    public List<RankInfo> rankPicker(List<RankInfo> list, Integer start, Integer end) {
        return list.stream().filter(rankInfo -> (
                start <= rankInfo.getRank()) && (rankInfo.getRank() <= end)
        ).collect(Collectors.toList());
    }

    // TODO - song name stream
    public List<RankInfo> songPicker(List<RankInfo> list, String song) {
        return list.stream().filter(
                rankInfo -> rankInfo.getTitle()
                        .replaceAll(" ","")
                        .contains(song.replaceAll(" ",""))
        ).collect(Collectors.toList());
    }

    // TODO - name stream
    public List<RankInfo> artistPicker(List<RankInfo> list, String artist) {
        return list.stream().filter(
                rankInfo -> rankInfo.getArtist()
                        .replaceAll(" ","")
                        .contains(artist.replaceAll(" ",""))
        ).collect(Collectors.toList());
    }





}
