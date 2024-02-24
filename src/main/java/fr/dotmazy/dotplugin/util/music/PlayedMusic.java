package fr.dotmazy.dotplugin.util.music;

public class PlayedMusic {
    private Music music;
    private double time;

    public PlayedMusic(Music music){
        this.music = music;
        this.time = 0;
    }

    public Music getMusic() {
        return music;
    }

    public PlayedMusic setMusic(Music music) {
        this.music = music;
        return this;
    }

    public double getTime() {
        return time;
    }

    public String getFormattedTime(){
        int seconds = (int) (time/20);

        int minutes = seconds/60;
        int secondsR = seconds%60;

        return minutes+":"+(String.valueOf(secondsR).length()==1?"0":"")+secondsR;
    }

    public boolean isFinished(Music music){
        return time > music.getTime()*20;
    }

    public String getPercentage(){
        return (int)((time/(music.getTime()*20))*100)+"%";
    }

    public PlayedMusic addTime(double time) {
        this.time += time;

        return this;
    }

    public boolean isActive(int pos){
        double t = ((time/20)/music.getTime())*7;
        return Math.round(t)>=pos;
    }
}
