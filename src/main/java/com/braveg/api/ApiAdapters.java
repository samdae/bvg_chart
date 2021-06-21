package com.braveg.api;

import com.braveg.api.adapters.BugsAPI;
import com.braveg.api.adapters.FloAPI;
import com.braveg.api.adapters.GenieAPI;
import com.braveg.api.adapters.Melon24API;
import com.braveg.api.adapters.MelonRealAPI;
import com.braveg.api.adapters.VibeAPI;
import com.braveg.api.adapters.YoutubeMusicAPI;
import com.braveg.utils.DateUtils;
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
        // when socket connection failed -> try again only once...
        if(canIGo(melon24))     { try{melon24      = Melon24API.get();     } catch (Exception e) {melon24      = Melon24API.get();     } }
        if(canIGo(melonReal))   { try{melonReal    = MelonRealAPI.get();   } catch (Exception e) {melonReal    = MelonRealAPI.get();   } }
        if(canIGo(youtubeMusic)){ try{youtubeMusic = YoutubeMusicAPI.get();} catch (Exception e) {youtubeMusic = YoutubeMusicAPI.get();} }
        if(canIGo(bugs))        { try{bugs         = BugsAPI.get();        } catch (Exception e) {bugs         = BugsAPI.get();        } }
        if(canIGo(genie))       { try{genie        = GenieAPI.get();       } catch (Exception e) {genie        = GenieAPI.get();       } }
        if(canIGo(flo))         { try{flo          = FloAPI.get();         } catch (Exception e) {flo          = FloAPI.get();         } }
        if(canIGo(vibe))        { try{vibe         = VibeAPI.get();        } catch (Exception e) {vibe         = VibeAPI.get();        } }
    }
    private static boolean canIGo(List<RankInfo> t) {
        if( t == null || t.isEmpty() )
            return true; // 값이 없거나 ( null 이거나 인자가 없을 때 )
        else if( DateUtils.getCurrent("yyMMddHH") > t.get(0).getDateTime() )
            return true; // 있는데 체크시간이 inputdata 시간보다 크면 돌림.
        else
            return false; // 나머지는 안돌림
    }

    public List<RankInfo> separator(String siteName) {
        List<RankInfo> result = null;
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
