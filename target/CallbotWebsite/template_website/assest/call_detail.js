$(document).ready(function() {
        var audioPlayer = $('#audio-player')[0];
        console.log(audioPlayer);
        // audioPlayer.play();
        $(".playAudio").click(function() {
            var start_time = $(this).attr('start_time');
            audioPlayer.currentTime = start_time;
            audioPlayer.play();
            console.log("Play from " + start_time + "s");
        });
});   
